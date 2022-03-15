package io.vertx.examples.spring.verticlefactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将Vertx注入Spring容器
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface VertxInject {
  //Vertx部署实例化的个数
  int size();
}
