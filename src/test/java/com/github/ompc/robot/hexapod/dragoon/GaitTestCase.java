package com.github.ompc.robot.hexapod.dragoon;

import com.github.ompc.robot.hexapod.dragoon.component.gait.*;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.MessageComException;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.Messenger;
import com.github.ompc.robot.hexapod.dragoon.component.remote.RemoteCmd;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static com.github.ompc.robot.hexapod.dragoon.component.gait.Joint.*;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Limb.*;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Pose.pose;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Pose.poses;
import static org.apache.commons.lang3.ArrayUtils.toArray;

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
                .changeTo(Gaits.reset(1000))
                .changeTo(
                        2000,
                        poses(toArray(L_F,R_M,L_H), toArray(KNE), 180),
                        poses(toArray(L_F,R_M,L_H), toArray(HIP), 45)
                )
//                .changeTo(
//                        2000,
//                        poses(toArray(L_F,R_M,L_H), toArray(KNE), 90)
//                )

                .build();

        final String json = gson.toJson(new RemoteCmd<>(RemoteCmd.Type.GAIT, gait));


        for (int i = 0; i < 1; i++) {
            messenger.publish(
                    Messenger.PublishMode.AT_LEAST_ONCE,
                    String.format("/%s/%s/user/messenger/test/post", productKey, deviceName),
                    json.getBytes(),
                    false
            );
        }

//        Thread.sleep(5 * 1000);
//        messenger.publish(
//                Messenger.PublishMode.AT_LEAST_ONCE,
//                String.format("/%s/%s/user/messenger/test/post", productKey, deviceName),
//                gson.toJson(new RemoteCmd<Void>(GAIT_INTERRUPT, null)).getBytes(),
//                true
//        );


    }

}
