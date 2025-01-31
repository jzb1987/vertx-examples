/*
 * Copyright 2017 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.examples.spring.verticlefactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * @author Thomas Segismont
 */
public class ExampleApplication {

  private static final Logger logger = LoggerFactory.getLogger(ExampleApplication.class);

  public static void main(String[] args) {

    //获取容器对象
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ExampleApplication.class.getPackage().getName());

    //打印容器里的Bean名称
    Arrays.stream(context.getBeanDefinitionNames()).forEach(s -> {
      logger.info("Bean名称:" + s);
    });

    logger.info("Bean总计:" + context.getBeanDefinitionCount());

   /* String[] beanNamesForType = context.getBeanNamesForType(GreetingVerticle.class);
    Arrays.stream(beanNamesForType).forEach(s->{
      logger.info("GreetingVerticle.class Bean名称:" + s);
    });*/

  }

}
