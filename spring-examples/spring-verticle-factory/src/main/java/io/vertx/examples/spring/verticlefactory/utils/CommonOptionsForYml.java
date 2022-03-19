package io.vertx.examples.spring.verticlefactory.utils;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component("commonOptionsForYml")
@DependsOn("springVerticleFactory")
@PropertySource(value = "classpath:commonOptions.yml", factory = YamlPropertySourceFactory.class)
/**
 * Vertx部署的共同选项，若Vertx上有非默认值的注解，则以注解的为准
 */
public class CommonOptionsForYml {

  @Value("${verticle.deploymentCommonOptions.clusteredVertx}")
  private boolean clusteredVertx = false;

  @Value("${verticle.deploymentCommonOptions.worker}")
  private boolean worker = DeploymentOptions.DEFAULT_WORKER;

  public boolean isClusteredVertx() {
    return clusteredVertx;
  }

  @Value("${verticle.deploymentCommonOptions.workerPoolName}")
  private String workerPoolName = null;

  @Value("${verticle.deploymentCommonOptions.workerPoolSize}")
  private int workerPoolSize = VertxOptions.DEFAULT_WORKER_POOL_SIZE;

  @Value("${verticle.deploymentCommonOptions.maxWorkerExecuteTime}")
  private long maxWorkerExecuteTime = VertxOptions.DEFAULT_MAX_WORKER_EXECUTE_TIME;

  @Value("${verticle.deploymentCommonOptions.ha}")
  private boolean ha = DeploymentOptions.DEFAULT_HA;

  @Value("${verticle.deploymentCommonOptions.instances}")
  private int instances = DeploymentOptions.DEFAULT_INSTANCES;

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
