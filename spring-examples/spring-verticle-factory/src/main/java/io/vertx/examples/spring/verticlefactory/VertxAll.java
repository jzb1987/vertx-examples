package io.vertx.examples.spring.verticlefactory;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class VertxAll {

  @Autowired
  private SpringVerticleFactory verticleFactory;

  public void deployment() {

    Vertx vertx = Vertx.vertx();

//    VerticleFactory verticleFactory = context.getBean(SpringVerticleFactory.class);

    // The verticle factory is registered manually because it is created by the Spring container
    vertx.registerVerticleFactory(verticleFactory);

    //需要扫描的包名
    Reflections f = new Reflections(VertxAll.class.getPackage().getName());

    //获取目标注解类
    Set<Class<?>> set = f.getTypesAnnotatedWith(VertxInject.class);

    //部署所有加有"VertxInject"注解的Vertx
    for (Class<?> aClass : set) {
      //取出注解的size属性值
      int size = aClass.getAnnotation(VertxInject.class).size();
      System.out.println("size = " + size);
      //获取注解类名
      String name = aClass.getName();
      System.out.println("name = " + name);
      //部署Vertx
      vertx.deployVerticle(verticleFactory.prefix() + ":" + name, new DeploymentOptions().setInstances(size)).onSuccess(ss -> System.out.println("部署成功\n" + "name = " + name + "\n" + "size = " + size)).onFailure(ff -> System.out.println("部署失败" + ff.getLocalizedMessage()));
    }
  }
}
