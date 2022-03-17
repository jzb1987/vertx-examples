package io.vertx.examples.spring.verticlefactory.vertx;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.examples.spring.verticlefactory.utils.SpringVerticle;
import io.vertx.examples.spring.verticlefactory.utils.SpringVerticleFactory;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@DependsOn({"springVerticleFactory"})
public class SpringVerticleDeployment { //通过该类简介部署Vertx，需要将该类放在与vert同一包下

  private final Logger logger = LoggerFactory.getLogger(SpringVerticleDeployment.class);

  public SpringVerticleDeployment(SpringVerticleFactory verticleFactory) {
    Vertx vertx = Vertx.vertx();

    // The verticle factory is registered manually because it is created by the Spring container
    vertx.registerVerticleFactory(verticleFactory);

    //需要扫描的包名
    Reflections f = new Reflections(this.getClass().getPackage().getName());

    //获取目标注解类
    Set<Class<?>> set = f.getTypesAnnotatedWith(SpringVerticle.class);

    //部署所有加有"SpringVerticle"注解的Vertx
    for (Class<?> aClass : set) {
      //取出注解的size属性值
      int size = aClass.getAnnotation(SpringVerticle.class).value();
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
    logger.info(this + "创建了");
  }

}
