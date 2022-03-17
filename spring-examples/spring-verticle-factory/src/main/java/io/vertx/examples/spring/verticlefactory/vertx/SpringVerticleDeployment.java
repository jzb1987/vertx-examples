package io.vertx.examples.spring.verticlefactory.vertx;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.examples.spring.verticlefactory.utils.CommonOptions;
import io.vertx.examples.spring.verticlefactory.utils.SpringVerticle;
import io.vertx.examples.spring.verticlefactory.utils.SpringVerticleFactory;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@DependsOn({"springVerticleFactory", "commonOptions"})
public class SpringVerticleDeployment {

  private final Logger logger = LoggerFactory.getLogger(GreetingVerticle.class);

  public SpringVerticleDeployment(SpringVerticleFactory verticleFactory, CommonOptions commonOptions) {
    Vertx vertx = Vertx.vertx();

    // The verticle factory is registered manually because it is created by the Spring container
    vertx.registerVerticleFactory(verticleFactory);

    //需要扫描的包名
    Reflections f = new Reflections(this.getClass().getPackage().getName());

    //获取目标注解类
    Set<Class<?>> set = f.getTypesAnnotatedWith(SpringVerticle.class);

    //先设置共同值选项
    //部署Vertx
    DeploymentOptions commonDeploymentOptions = new DeploymentOptions();
    //设置YML配置的值
    //实例个数
    if (commonOptions.getInstances() > 0 && commonOptions.getInstances() != DeploymentOptions.DEFAULT_INSTANCES) {
      commonDeploymentOptions.setInstances(commonOptions.getInstances());
    }
    //工作Vertx
    commonDeploymentOptions.setWorker(commonOptions.isWorker());
    commonDeploymentOptions.setMaxWorkerExecuteTime(commonOptions.getMaxWorkerExecuteTime());
    commonDeploymentOptions.setWorkerPoolSize(commonOptions.getWorkerPoolSize());
    if (null != commonOptions.getWorkerPoolName() && !"".equals(commonOptions.getWorkerPoolName())) {
      commonDeploymentOptions.setWorkerPoolName(commonOptions.getWorkerPoolName());
    }
    //HA设置
    commonDeploymentOptions.setHa(commonOptions.isHa());

    logger.info("共同值 instances = " + commonDeploymentOptions.getInstances());
    logger.info("共同值 ha = " + commonDeploymentOptions.isHa());
    logger.info("共同值 worker = " + commonDeploymentOptions.isWorker());
    logger.info("共同值 maxWorkerExecuteTime = " + commonDeploymentOptions.getMaxWorkerExecuteTime());
    logger.info("共同值 workerPoolName = " + commonDeploymentOptions.getWorkerPoolName());
    if (commonDeploymentOptions.getWorkerPoolName() == null) {
      logger.info("共同值 workerPoolName为null");
    } else if (commonDeploymentOptions.getWorkerPoolName().equals("")) {
      logger.info("共同值 workerPoolName为空");
    }
    logger.info("共同值 workerPoolSize = " + commonDeploymentOptions.getWorkerPoolSize());

    //部署所有加有"SpringVerticle"注解的Vertx
    for (Class<?> aClass : set) {
      //取出注解的属性值
      SpringVerticle classAnnotation = aClass.getAnnotation(SpringVerticle.class);
      int instances = classAnnotation.value();
      logger.info("获取的注解值 instances = " + instances);
      boolean worker = classAnnotation.worker();
      logger.info("获取的注解值 worker = " + worker);
      long maxWorkerExecuteTime = classAnnotation.maxWorkerExecuteTime();
      logger.info("获取的注解值 maxWorkerExecuteTime = " + maxWorkerExecuteTime);
      String workerPoolName = classAnnotation.workerPoolName();
      logger.info("获取的注解值 workerPoolName = " + workerPoolName);
      int workerPoolSize = classAnnotation.workerPoolSize();
      logger.info("获取的注解值 workerPoolSize = " + workerPoolSize);
      boolean ha = classAnnotation.ha();
      logger.info("获取的注解值 ha = " + ha);

      //单独配置，若为默认值，则不设置，使用共同配置，若不为默认值，说明需要改变
      DeploymentOptions myDeploymentOptions = new DeploymentOptions(commonDeploymentOptions);
      //设置实例个数
      if (instances > 0 && instances != DeploymentOptions.DEFAULT_INSTANCES) {
        myDeploymentOptions.setInstances(instances);
      }
      //设置工作Vertx
      if (worker) {
        myDeploymentOptions.setWorker(true);
      }

      if (maxWorkerExecuteTime > 0 && maxWorkerExecuteTime != VertxOptions.DEFAULT_MAX_WORKER_EXECUTE_TIME) {
        myDeploymentOptions.setMaxWorkerExecuteTime(maxWorkerExecuteTime);
      }

      if (workerPoolSize > 0 && workerPoolSize != VertxOptions.DEFAULT_WORKER_POOL_SIZE) {
        myDeploymentOptions.setWorkerPoolSize(workerPoolSize);
      }

      if (!workerPoolName.equals("")) {
        myDeploymentOptions.setWorkerPoolName(workerPoolName);
      }

      //设置HA
      if (ha) {
        myDeploymentOptions.setHa(true);
      }

      //获取注解类名
      String name = aClass.getName();
      logger.info("name = " + name);

      logger.info("最终部署 instances = " + myDeploymentOptions.getInstances());
      logger.info("最终部署 worker = " + myDeploymentOptions.isWorker());
      logger.info("最终部署 ha = " + myDeploymentOptions.isHa());
      logger.info("最终部署 maxWorkerExecuteTime = " + myDeploymentOptions.getMaxWorkerExecuteTime());
      logger.info("最终部署 workerPoolName = " + myDeploymentOptions.getWorkerPoolName());
      logger.info("最终部署 workerPoolSize = " + myDeploymentOptions.getWorkerPoolSize());

      vertx.deployVerticle(verticleFactory.prefix() + ":" + name, myDeploymentOptions).onSuccess(ss -> {
        logger.info("部署成功");
        logger.info("name = " + name);
        logger.info("instances = " + myDeploymentOptions.getInstances());
      }).onFailure(ff -> {
        logger.info("部署失败");
        logger.info(ff.getLocalizedMessage());
      });
    }
    logger.info(this + "创建了");
  }
}
