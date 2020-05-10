package com.state.event;

import org.springframework.context.ApplicationEvent;
/**
 * @author xiangwei
 * @date 2020-05-10 8:31 下午
 */
public class TaskFinishEvent extends ApplicationEvent {

    public TaskFinishEvent(Object source) {
        super(source);
    }
}
