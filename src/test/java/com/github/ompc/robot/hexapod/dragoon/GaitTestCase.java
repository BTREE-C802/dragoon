package com.github.ompc.robot.hexapod.dragoon;

import com.github.ompc.robot.hexapod.dragoon.component.gait.Gait;
import com.github.ompc.robot.hexapod.dragoon.component.gait.GaitCtlCom;
import com.github.ompc.robot.hexapod.dragoon.component.gait.builder.GaitBuilder;
import com.github.ompc.robot.hexapod.dragoon.component.gait.builder.PoseOperations;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.MessageComException;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.Messenger;
import com.github.ompc.robot.hexapod.dragoon.component.remote.RemoteCmd;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
                .merge(
                        PoseOperations.stand(10)
                )
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
