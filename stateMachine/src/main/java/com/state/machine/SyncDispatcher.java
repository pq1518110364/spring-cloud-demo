package com.state.machine;

import com.state.event.EventHandler;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 同步事件分发器
 */
@Component
public class SyncDispatcher implements Dispatcher {

    Map<Class<? extends Enum>, EventHandler> eventHandlerMap;

    public SyncDispatcher() {
        eventHandlerMap = new ConcurrentHashMap<>();
    }

    @Override
    public void register(Class<? extends Enum> eventType, EventHandler handler) {
        eventHandlerMap.put(eventType, handler);
    }

    public void dispatch(final ApplicationEvent event) {
        final EventHandler eventHandler =new StateManager(); //= eventHandlerMap.get(event.getType().getClass());


        try {
            System.out.println("SyncDispatcher handle event start;event=" + event);
            eventHandler.handle(event);
            System.out.println("SyncDispatcher handle event end;event=" + event);
        } catch (Exception e) {
            System.out.println("SyncDispatcher handle event exception;event=" + event);
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {

    }
}
