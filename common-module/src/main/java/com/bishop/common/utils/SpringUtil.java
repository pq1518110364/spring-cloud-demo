package com.bishop.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring获取bean工具类
 *
 * @Author: bishop
 * Create by Poseidon on 2019-04-19
 */
@SuppressWarnings("unused")
@Slf4j
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        SpringUtil.applicationContext = applicationContext;
    }


    /**
      * 功能描述:取得存储在静态变量中的ApplicationContext
      *
      * @Author: bishop
      * @Description:
      * @Date: 2019-04-19
      * @return: org.springframework.context.ApplicationContext
      **/
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }


    /**
      * 功能描述:从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
      *        按名字获取
      * @Author: bishop
      * @Description:
      * @Date: 2019-04-19
      * @param name:
      * @return: T
      **/
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }
    /**
      * 功能描述:从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
      *         按class类型获取
      * @Author: bishop
      * @Description:
      * @Date: 2019-04-19
      * @param clazz:
      * @return: T
      **/
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return (T) applicationContext.getBeansOfType(clazz);
    }

    /**
      * 功能描述:多类型获取
      *
      * @Author: bishop
      * @Description:
      * @Date: 2019-04-19
      * @param name:
      * @param clazz:
      * @return: T
      **/
    public static <T> T getBean(String name,Class<T> clazz) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name,clazz);
    }
    /**
     * 清除applicationContext静态变量.
     */
    public static void cleanApplicationContext() {
        applicationContext = null;
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException(
                    "applicationContext未注入,请在applicationContext.xml中定义本SpringUtil--------<bean class='xxx.SpringUtil' />");
        }
    }
}
