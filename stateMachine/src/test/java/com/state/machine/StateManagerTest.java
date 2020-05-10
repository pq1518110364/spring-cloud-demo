package com.state.machine;

import com.state.MachineApp;
import com.state.event.TaskFinishEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author xiangwei
 * @date 2020-05-10 9:10 下午
 */
@SpringBootTest(classes=MachineApp.class)
@RunWith(SpringRunner.class)
public class StateManagerTest {

    @Autowired
    StateManager stateManager;
    @Test
    public void publishEvent() {


        stateManager.publishEvent(new TaskFinishEvent("test"));
    }
}