package com.github.ompc.robot.hexapod.dragoon;

import com.github.ompc.robot.hexapod.dragoon.component.gait.Gait;
import com.github.ompc.robot.hexapod.dragoon.component.gait.GaitBuilder;
import com.github.ompc.robot.hexapod.dragoon.component.gait.GaitCtlCom;
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
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Pose.poses;
import static com.github.ompc.robot.hexapod.dragoon.component.remote.RemoteCmd.Type.GAIT_INTERRUPT;
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

//                // TEST
//                .changeTo(
//                        2000,
//                        Pose.poses(new Limb[]{R_F, L_F}, new Joint[]{ANK}, 45)
//                )
//                .changeTo(
//                        2000,
//                        Pose.poses(new Limb[]{R_F, L_F}, new Joint[]{ANK}, 135)
//                )
//                .changeTo(
//                        2000,
//                        Pose.poses(new Limb[]{R_F, L_F}, new Joint[]{ANK}, 90)
//                )

//                // HIGH
//                .changeTo(
//                        2000,
//                        poses(new Joint[]{KNE,ANK}, 45),
//                        poses(new Joint[]{HIP}, 90)
//                )
//
                // RESET
                .changeTo(
                        2000,
                        poses(LIMB_ALL, toArray(HIP, KNE), 90),
                        poses(LIMB_ALL, toArray(ANK), 120)
                )

                .changeTo(200, poses(toArray(L_M, R_M), toArray(ANK), 0))
                .changeTo(200, poses(toArray(L_M, R_M), toArray(KNE), 120))
                .changeTo(200, poses(toArray(L_M, R_M), toArray(KNE), 90))
                .changeTo(200, poses(toArray(L_M, R_M), toArray(KNE), 120))
                .changeTo(200, poses(toArray(L_M, R_M), toArray(KNE), 90))
                .changeTo(200, poses(toArray(L_M, R_M), toArray(KNE), 120))
                .changeTo(200, poses(toArray(L_M, R_M), toArray(KNE), 90))
                .changeTo(200, poses(toArray(L_M, R_M), toArray(KNE), 120))
                .changeTo(200, poses(toArray(L_M, R_M), toArray(KNE), 90))

                // RESET
                .changeTo(
                        2000,
                        poses(LIMB_ALL, toArray(HIP, KNE), 90),
                        poses(LIMB_ALL, toArray(ANK), 120)
                )

                .changeTo(200, poses(toArray(L_F), toArray(ANK), 45)).changeTo(200, poses(toArray(L_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_F), toArray(ANK), 45)).changeTo(200, poses(toArray(L_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_F), toArray(ANK), 45)).changeTo(200, poses(toArray(L_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_M), toArray(ANK), 45)).changeTo(200, poses(toArray(L_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_M), toArray(ANK), 45)).changeTo(200, poses(toArray(L_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_M), toArray(ANK), 45)).changeTo(200, poses(toArray(L_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_H), toArray(ANK), 45)).changeTo(200, poses(toArray(L_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_H), toArray(ANK), 45)).changeTo(200, poses(toArray(L_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_H), toArray(ANK), 45)).changeTo(200, poses(toArray(L_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_F), toArray(ANK), 45)).changeTo(200, poses(toArray(R_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_F), toArray(ANK), 45)).changeTo(200, poses(toArray(R_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_F), toArray(ANK), 45)).changeTo(200, poses(toArray(R_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_M), toArray(ANK), 45)).changeTo(200, poses(toArray(R_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_M), toArray(ANK), 45)).changeTo(200, poses(toArray(R_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_M), toArray(ANK), 45)).changeTo(200, poses(toArray(R_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_H), toArray(ANK), 45)).changeTo(200, poses(toArray(R_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_H), toArray(ANK), 45)).changeTo(200, poses(toArray(R_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_H), toArray(ANK), 45)).changeTo(200, poses(toArray(R_H), toArray(ANK), 120))

                .changeTo(200, poses(toArray(L_F), toArray(ANK), 45)).changeTo(200, poses(toArray(L_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_F), toArray(ANK), 45)).changeTo(200, poses(toArray(L_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_F), toArray(ANK), 45)).changeTo(200, poses(toArray(L_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_M), toArray(ANK), 45)).changeTo(200, poses(toArray(L_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_M), toArray(ANK), 45)).changeTo(200, poses(toArray(L_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_M), toArray(ANK), 45)).changeTo(200, poses(toArray(L_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_H), toArray(ANK), 45)).changeTo(200, poses(toArray(L_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_H), toArray(ANK), 45)).changeTo(200, poses(toArray(L_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_H), toArray(ANK), 45)).changeTo(200, poses(toArray(L_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_F), toArray(ANK), 45)).changeTo(200, poses(toArray(R_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_F), toArray(ANK), 45)).changeTo(200, poses(toArray(R_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_F), toArray(ANK), 45)).changeTo(200, poses(toArray(R_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_M), toArray(ANK), 45)).changeTo(200, poses(toArray(R_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_M), toArray(ANK), 45)).changeTo(200, poses(toArray(R_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_M), toArray(ANK), 45)).changeTo(200, poses(toArray(R_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_H), toArray(ANK), 45)).changeTo(200, poses(toArray(R_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_H), toArray(ANK), 45)).changeTo(200, poses(toArray(R_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_H), toArray(ANK), 45)).changeTo(200, poses(toArray(R_H), toArray(ANK), 120))

                .changeTo(200, poses(toArray(L_F), toArray(ANK), 45)).changeTo(200, poses(toArray(L_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_F), toArray(ANK), 45)).changeTo(200, poses(toArray(L_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_F), toArray(ANK), 45)).changeTo(200, poses(toArray(L_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_M), toArray(ANK), 45)).changeTo(200, poses(toArray(L_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_M), toArray(ANK), 45)).changeTo(200, poses(toArray(L_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_M), toArray(ANK), 45)).changeTo(200, poses(toArray(L_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_H), toArray(ANK), 45)).changeTo(200, poses(toArray(L_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_H), toArray(ANK), 45)).changeTo(200, poses(toArray(L_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_H), toArray(ANK), 45)).changeTo(200, poses(toArray(L_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_F), toArray(ANK), 45)).changeTo(200, poses(toArray(R_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_F), toArray(ANK), 45)).changeTo(200, poses(toArray(R_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_F), toArray(ANK), 45)).changeTo(200, poses(toArray(R_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_M), toArray(ANK), 45)).changeTo(200, poses(toArray(R_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_M), toArray(ANK), 45)).changeTo(200, poses(toArray(R_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_M), toArray(ANK), 45)).changeTo(200, poses(toArray(R_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_H), toArray(ANK), 45)).changeTo(200, poses(toArray(R_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_H), toArray(ANK), 45)).changeTo(200, poses(toArray(R_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_H), toArray(ANK), 45)).changeTo(200, poses(toArray(R_H), toArray(ANK), 120))

                .changeTo(200, poses(toArray(L_F), toArray(ANK), 45)).changeTo(200, poses(toArray(L_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_F), toArray(ANK), 45)).changeTo(200, poses(toArray(L_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_F), toArray(ANK), 45)).changeTo(200, poses(toArray(L_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_M), toArray(ANK), 45)).changeTo(200, poses(toArray(L_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_M), toArray(ANK), 45)).changeTo(200, poses(toArray(L_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_M), toArray(ANK), 45)).changeTo(200, poses(toArray(L_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_H), toArray(ANK), 45)).changeTo(200, poses(toArray(L_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_H), toArray(ANK), 45)).changeTo(200, poses(toArray(L_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_H), toArray(ANK), 45)).changeTo(200, poses(toArray(L_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_F), toArray(ANK), 45)).changeTo(200, poses(toArray(R_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_F), toArray(ANK), 45)).changeTo(200, poses(toArray(R_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_F), toArray(ANK), 45)).changeTo(200, poses(toArray(R_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_M), toArray(ANK), 45)).changeTo(200, poses(toArray(R_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_M), toArray(ANK), 45)).changeTo(200, poses(toArray(R_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_M), toArray(ANK), 45)).changeTo(200, poses(toArray(R_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_H), toArray(ANK), 45)).changeTo(200, poses(toArray(R_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_H), toArray(ANK), 45)).changeTo(200, poses(toArray(R_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_H), toArray(ANK), 45)).changeTo(200, poses(toArray(R_H), toArray(ANK), 120))

                .changeTo(200, poses(toArray(L_F), toArray(ANK), 45)).changeTo(200, poses(toArray(L_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_F), toArray(ANK), 45)).changeTo(200, poses(toArray(L_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_F), toArray(ANK), 45)).changeTo(200, poses(toArray(L_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_M), toArray(ANK), 45)).changeTo(200, poses(toArray(L_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_M), toArray(ANK), 45)).changeTo(200, poses(toArray(L_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_M), toArray(ANK), 45)).changeTo(200, poses(toArray(L_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_H), toArray(ANK), 45)).changeTo(200, poses(toArray(L_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_H), toArray(ANK), 45)).changeTo(200, poses(toArray(L_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(L_H), toArray(ANK), 45)).changeTo(200, poses(toArray(L_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_F), toArray(ANK), 45)).changeTo(200, poses(toArray(R_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_F), toArray(ANK), 45)).changeTo(200, poses(toArray(R_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_F), toArray(ANK), 45)).changeTo(200, poses(toArray(R_F), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_M), toArray(ANK), 45)).changeTo(200, poses(toArray(R_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_M), toArray(ANK), 45)).changeTo(200, poses(toArray(R_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_M), toArray(ANK), 45)).changeTo(200, poses(toArray(R_M), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_H), toArray(ANK), 45)).changeTo(200, poses(toArray(R_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_H), toArray(ANK), 45)).changeTo(200, poses(toArray(R_H), toArray(ANK), 120))
                .changeTo(200, poses(toArray(R_H), toArray(ANK), 45)).changeTo(200, poses(toArray(R_H), toArray(ANK), 120))

                .build();

        final String json = gson.toJson(new RemoteCmd<>(RemoteCmd.Type.GAIT, gait));


        for (int i = 0; i < 10; i++) {
            messenger.publish(
                    Messenger.PublishMode.AT_LEAST_ONCE,
                    String.format("/%s/%s/user/messenger/test/post", productKey, deviceName),
                    json.getBytes(),
                    true
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
