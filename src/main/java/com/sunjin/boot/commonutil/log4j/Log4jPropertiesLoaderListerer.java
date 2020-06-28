package com.sunjin.boot.commonutil.log4j;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

/**
 * @Description: log4j.xml 占位符替换 通过系统参数
 * @Author: bhh
 * @Mail: sunjin@sudiyi.cn
 * @Date: 2020/6/28
 */
@WebListener
public class Log4jPropertiesLoaderListerer implements ServletContextListener {

    private  final String CONFIGURATION_PROPERTIES_LOCATION = "classpath*:log4jAppendedKafka.properties";
    private  final String KAFKA_SERVER_LIST = "kafka.server.list";

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try{
            PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();

            Properties properties = new Properties();
            Resource[] resources = patternResolver.getResources(CONFIGURATION_PROPERTIES_LOCATION);
            for (Resource resource : resources) {
                Properties propertie = PropertiesLoaderUtils.loadProperties(resource);
                properties.putAll(propertie);
            }
            String propertiesParamValue = properties.getProperty(KAFKA_SERVER_LIST);
            System.setProperty(KAFKA_SERVER_LIST,propertiesParamValue);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }
}
