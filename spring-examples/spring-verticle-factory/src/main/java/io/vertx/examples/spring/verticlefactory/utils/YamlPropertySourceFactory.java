package io.vertx.examples.spring.verticlefactory.utils;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class YamlPropertySourceFactory implements PropertySourceFactory {

  /**
   * 自定义解析规则，引入了第三方yaml文件解析器
   */
  @Override
  public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
    //1.创建yaml文件解析工厂
    YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();
    //2.设置要解析的资源内容
    Resource resourceResource = resource.getResource();
    factoryBean.setResources(resourceResource);
    //3.把资源解析成properties文件
    Properties properties = factoryBean.getObject();
    assert properties != null;
    //4.返回PropertySource对象
    return (name != null ? new PropertiesPropertySource(name, properties)
      : new PropertiesPropertySource(Objects.requireNonNull(resourceResource.getFilename()), properties));
  }
}
