package com.sunjin.boot.commonutil.databind;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.DataBinder;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * @Description:
 * @Author: bhh
 * @Mail: sunjin@sudiyi.cn
 * @Date: 2020/6/18
 */
public class CustomerConfigurationPropertiesBinder {

    private ApplicationContext applicationContext;

    public CustomerConfigurationPropertiesBinder(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void bind(Object bean, EnableConfigurationProperties enableConfigurationProperties) {

        final MutablePropertyValues propertyValues = new MutablePropertyValues();
        Environment environment = applicationContext.getEnvironment();

        //prefix 不能为空
        String prefix = enableConfigurationProperties.prefix();

        ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                if (Collection.class.isAssignableFrom(field.getType())
                        || Map.class.isAssignableFrom(field.getType())) {
                    //不支持集合
                    return;
                }
                String key = prefix +"."+ field.getName();
                if(environment.containsProperty(key)){
                    propertyValues.add(field.getName(),environment.getProperty(key));
                }
            }
        });
        doBind(bean, propertyValues);
    }

    private void doBind(Object bean, MutablePropertyValues propertyValues) {
        DataBinder dataBinder = new DataBinder(bean);
        dataBinder.bind(propertyValues);
    }
}
