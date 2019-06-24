package com.github.ompc.robot.hexapod.dragoon;

import com.github.ompc.robot.hexapod.dragoon.component.gait.Gait;
import com.github.ompc.robot.hexapod.dragoon.component.gait.GaitCtlCom;
import com.github.ompc.robot.hexapod.dragoon.component.gait.builder.GaitBuilder;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.MessageComException;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.Messenger;
import com.github.ompc.robot.hexapod.dragoon.component.remote.RemoteCmd;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static com.github.ompc.robot.hexapod.dragoon.component.gait.Limb.*;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.builder.PoseOperations.*;

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

        final Gait gait = new GaitBuilder(1000)
                .merge(stand(50))
                .addition(limbUp(L_F, Math.PI / 6))
                .addition(limbUp(R_M, Math.PI / 6))
                .addition(limbUp(L_H, Math.PI / 6))
                .addition(limbFront(L_F, Math.PI / 6))
                .addition(limbFront(R_M, Math.PI / 6))
                .addition(limbFront(L_H, Math.PI / 6))
                .newGait(1000)
                .merge(stand(50))
                .addition(limbDown(L_F, Math.PI / 6))
                .addition(limbDown(R_M, Math.PI / 6))
                .addition(limbDown(L_H, Math.PI / 6))
                .build();

        final String json = gson.toJson(new RemoteCmd<>(RemoteCmd.Type.GAIT, gait));


        messenger.publish(
                Messenger.PublishMode.AT_LEAST_ONCE,
                String.format("/%s/%s/user/messenger/test/post", productKey, deviceName),
                json.getBytes(),
                false
        );

    }

}
