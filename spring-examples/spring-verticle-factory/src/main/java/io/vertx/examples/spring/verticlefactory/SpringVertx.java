package io.vertx.examples.spring.verticlefactory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加在Vert上，将Vertx注入Spring容器
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
@Scope("prototype")// Prototype scope is needed as multiple instances of this verticle will be deployed
public @interface SpringVertx {
  /**
   * Vertx部署实例化的个数，默认为1
   * @return int
   */
  int value() default 1;
}
