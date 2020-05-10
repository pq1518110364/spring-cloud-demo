package com.state.machine;

import com.state.event.EventHandler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * @author xiangwei
 * @date 2020-05-10 9:02 下午
 */
@Component
public class StateManager<T extends ApplicationEvent> implements ApplicationContextAware, EventHandler<T> {

    private ApplicationContext applicationContext;

    @Autowired
    private Dispatcher dispatcher;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    //发布事件
    public void publishEvent(ApplicationEvent event){
        applicationContext.publishEvent(event);
    }

    @Override
    public void handle(T event) {

    }

    public void start(){
        //dispatcher.register();
       // dispatcher.dispatch();
        dispatcher.shutdown();
    }
}
