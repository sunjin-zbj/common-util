package com.sunjin.boot.commonutil.databind;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: bhh
 * @Mail: sunjin@sudiyi.cn
 * @Date: 2020/6/18
 * 数据绑定
 */
@Component
public class CustomerConfigurationPropertiesBindingPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if(!beanClass.isAnnotationPresent(EnableConfigurationProperties.class)){
            return bean;
        }
        EnableConfigurationProperties enableConfigurationProperties =
                beanClass.getAnnotation(EnableConfigurationProperties.class);

        bind(bean, enableConfigurationProperties);


        return null;
    }

    private void bind(Object bean, EnableConfigurationProperties enableConfigurationProperties) {
        CustomerConfigurationPropertiesBinder binder;
        try {
            binder = applicationContext.getBean(
                    CustomerConfigurationPropertiesBinder.class.getName(),
                    CustomerConfigurationPropertiesBinder.class);
            if (binder == null) {
                binder = new CustomerConfigurationPropertiesBinder(applicationContext);
            }

        }
        catch (Exception e) {
            binder = new CustomerConfigurationPropertiesBinder(applicationContext);
        }

        binder.bind(bean, enableConfigurationProperties);
    }


}
