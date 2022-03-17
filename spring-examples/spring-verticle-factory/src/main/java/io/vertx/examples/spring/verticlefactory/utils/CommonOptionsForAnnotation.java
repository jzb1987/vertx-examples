package io.vertx.examples.spring.verticlefactory.utils;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;

/**
 * Vertx部署的单独选项，若Vertx上有注解并且不是默认值，则覆盖共同选项
 */
public class CommonOptionsForAnnotation {

  private boolean worker = DeploymentOptions.DEFAULT_WORKER;

  private String workerPoolName = null;

  private int workerPoolSize = VertxOptions.DEFAULT_WORKER_POOL_SIZE;

  private long maxWorkerExecuteTime = VertxOptions.DEFAULT_MAX_WORKER_EXECUTE_TIME;

  private boolean ha = DeploymentOptions.DEFAULT_HA;

  private int instances = DeploymentOptions.DEFAULT_INSTANCES;

  public CommonOptionsForAnnotation setWorker(boolean worker) {
    this.worker = worker;
    return this;
  }

  public CommonOptionsForAnnotation setWorkerPoolName(String workerPoolName) {
    this.workerPoolName = workerPoolName;
    return this;
  }

  public CommonOptionsForAnnotation setWorkerPoolSize(int workerPoolSize) {
    this.workerPoolSize = workerPoolSize;
    return this;
  }

  public CommonOptionsForAnnotation setMaxWorkerExecuteTime(long maxWorkerExecuteTime) {
    this.maxWorkerExecuteTime = maxWorkerExecuteTime;
    return this;
  }

  public CommonOptionsForAnnotation setHa(boolean ha) {
    this.ha = ha;
    return this;
  }

  public CommonOptionsForAnnotation setInstances(int instances) {
    this.instances = instances;
    return this;
  }

  public boolean isWorker() {
    return worker;
  }

  public String getWorkerPoolName() {
    return workerPoolName;
  }

  public int getWorkerPoolSize() {
    return workerPoolSize;
  }

  public long getMaxWorkerExecuteTime() {
    return maxWorkerExecuteTime;
  }

  public boolean isHa() {
    return ha;
  }

  public int getInstances() {
    return instances;
  }
}
