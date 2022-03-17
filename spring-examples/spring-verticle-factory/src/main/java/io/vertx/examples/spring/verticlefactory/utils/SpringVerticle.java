package io.vertx.examples.spring.verticlefactory.utils;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加在Vert上，将Vertx注入Spring容器，需要绕开默认值，设置默认值无效
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
@Scope("prototype")// Prototype scope is needed as multiple instances of this verticle will be deployed
@DependsOn("springVerticleFactory")
public @interface SpringVerticle {

  /**
   * 工作Vertx最大允许执行时间
   * 1秒(s) = 1000000000纳秒(ns)
   * 默认为60秒
   */
  long DEFAULT_MAX_WORKER_EXECUTE_TIME = 60000000000L;

  /**
   * Vertx部署实例化的个数，默认为1
   *
   * @return
   */
  int value() default DeploymentOptions.DEFAULT_INSTANCES;

  /**
   * 是否使用HA，默认为false
   *
   * @return
   */
  boolean ha() default DeploymentOptions.DEFAULT_HA;

  /**
   * 是否让该Vertx成为工作Vertx，默认为false
   *
   * @return
   */
  boolean worker() default DeploymentOptions.DEFAULT_WORKER;

  /**
   * 工作Vertx默认名称，默认""相当于null使用，因为null表示不出来
   *
   * @return
   */
  String workerPoolName() default "";

  /**
   * 工作Vertx池的最大线程数量，默认为20
   *
   * @return
   */
  int workerPoolSize() default VertxOptions.DEFAULT_WORKER_POOL_SIZE;

  /**
   * 工作Vertx最大允许执行时间，默认为60000000000纳秒，也就是60秒
   *
   * @return
   */
  long maxWorkerExecuteTime() default DEFAULT_MAX_WORKER_EXECUTE_TIME;
}
