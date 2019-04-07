package com.github.ompc.robot.hexapod.dragoon;

import com.github.ompc.robot.hexapod.dragoon.DragoonLauncher;
import com.github.ompc.robot.hexapod.dragoon.device.PiDevException;
import com.github.ompc.robot.hexapod.dragoon.device.ServoCtlPiDev;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DragoonLauncher.class)
public class SpringBootSupportTestCase {

    @Autowired
    private ServoCtlPiDev servoCtlPiCom;

    @Test
    public void test() throws PiDevException {
        servoCtlPiCom.control(
                1000,
                new ServoCtlPiDev.ServoCmd(1, 2000)
        );
    }

}
