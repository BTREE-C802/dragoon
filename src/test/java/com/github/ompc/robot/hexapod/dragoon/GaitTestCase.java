package com.github.ompc.robot.hexapod.dragoon;

import com.github.ompc.robot.hexapod.dragoon.component.gait.*;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.MessageComException;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.Messenger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static com.github.ompc.robot.hexapod.dragoon.component.gait.Joint.*;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Leg.*;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Pose.poses;

public class GaitTestCase extends SpringBootSupportTestCase {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @Value("${dragoon.mqtt.aliyun.product-key}")
    private String productKey;

    @Value("${dragoon.mqtt.aliyun.device-name}")
    private String deviceName;

    @Autowired
    private Messenger messenger;

    @Autowired
    private GaitCtlCom gaitCtlCom;

    @Test
    public void test() throws MessageComException, InterruptedException {

        final Gait gait = new GaitBuilder()

//                // TEST
//                .append(
//                        2000,
//                        Pose.poses(new Leg[]{R_F, L_F}, new Joint[]{ANK}, 45)
//                )
//                .append(
//                        2000,
//                        Pose.poses(new Leg[]{R_F, L_F}, new Joint[]{ANK}, 135)
//                )
//                .append(
//                        2000,
//                        Pose.poses(new Leg[]{R_F, L_F}, new Joint[]{ANK}, 90)
//                )

                // HIGH
                .append(
                        2000,
                        poses(new Joint[]{KNE,ANK}, 45),
                        poses(new Joint[]{HIP}, 90)
                )

                // RESET
                .append(
                        2000,
                        poses(new Joint[]{HIP, KNE}, 90),
                        poses(new Joint[]{ANK}, 0)
                )
                .build();

        final String json = gson.toJson(gait);

        messenger.publish(
                Messenger.PublishMode.AT_LEAST_ONCE,
                String.format("/%s/%s/user/messenger/test/post", productKey, deviceName),
                json.getBytes()
        );

    }

}
