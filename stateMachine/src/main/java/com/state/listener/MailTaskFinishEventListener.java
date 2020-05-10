package com.state.listener;

import com.state.event.TaskFinishEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 邮件时间监听
 * @author xiangwei
 * @date 2020-05-10 8:42 下午
 */

@Component
@Slf4j
public class MailTaskFinishEventListener implements ApplicationListener<TaskFinishEvent> {

    private String emial="takumiCX@163.com";


    @Override
    public void onApplicationEvent(TaskFinishEvent taskFinishEvent) {
        log.info(emial+":"+taskFinishEvent.getSource());
    }
}
