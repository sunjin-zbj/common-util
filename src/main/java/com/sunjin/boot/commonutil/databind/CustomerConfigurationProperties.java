package com.sunjin.boot.commonutil.databind;

import java.lang.annotation.*;

@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomerConfigurationProperties {

    String prefix();
}
