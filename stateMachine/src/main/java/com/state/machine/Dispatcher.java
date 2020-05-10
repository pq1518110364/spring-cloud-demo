package com.state.machine;

import com.state.event.EventHandler;
import org.springframework.context.ApplicationEvent;

/**
 * 事件分发器，负责维护事件与事件处理器和分发事件
 */
public interface Dispatcher {

    void register(Class<? extends Enum> eventType, EventHandler handler);

    void dispatch(final ApplicationEvent event);

    void shutdown();
}
