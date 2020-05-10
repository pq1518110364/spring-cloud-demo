package com.state.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author xiangwei
 * @date 2020-05-10 9:28 下午
 */
public interface EventHandler<T extends ApplicationEvent> {
    /**
     * 处理T类型的事件
     *
     * @param event
     */
    void handle(T event);
}
