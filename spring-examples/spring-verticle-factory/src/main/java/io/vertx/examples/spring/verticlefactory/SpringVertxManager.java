package io.vertx.examples.spring.verticlefactory;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Set;

public class SpringVertxManager { //通过该类简介部署Vertx，需要将该类放在与vert同一包下

  private final Logger logger = LoggerFactory.getLogger(SpringVertxManager.class);

  private final ApplicationContext applicationContext;

  public SpringVertxManager(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  public  void deployment() {//部署所有加了VertxSpring注解的Vertx

    Vertx vertx = Vertx.vertx();

    SpringVerticleFactory verticleFactory = applicationContext.getBean(SpringVerticleFactory.class);

    // The verticle factory is registered manually because it is created by the Spring container
    vertx.registerVerticleFactory(verticleFactory);

    //需要扫描的包名
    Reflections f = new Reflections(SpringVertxManager.class.getPackage().getName());

    //获取目标注解类
    Set<Class<?>> set = f.getTypesAnnotatedWith(SpringVertx.class);

    //部署所有加有"SpringVertx"注解的Vertx
    for (Class<?> aClass : set) {
      //取出注解的size属性值
      int size = aClass.getAnnotation(SpringVertx.class).value();
      logger.info("size = " + size);
      //获取注解类名
      String name = aClass.getName();
      logger.info("name = " + name);
      //部署Vertx
      vertx.deployVerticle(verticleFactory.prefix() + ":" + name, new DeploymentOptions().setInstances(size)).onSuccess(ss -> {
        logger.info("部署成功");
        logger.info("name = " + name);
        logger.info("size = " + size);
      }).onFailure(ff -> {
        logger.info("部署失败");
        logger.info(ff.getLocalizedMessage());
      });
    }
  }
}
