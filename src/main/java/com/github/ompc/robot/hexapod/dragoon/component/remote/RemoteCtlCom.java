package com.github.ompc.robot.hexapod.dragoon.component.remote;

import com.github.ompc.robot.hexapod.dragoon.component.EnvCom;
import com.github.ompc.robot.hexapod.dragoon.component.gait.Gait;
import com.github.ompc.robot.hexapod.dragoon.component.gait.GaitCtlCom;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.Messenger;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.TextMessageListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 远程控制组件
 */
@Component
public class RemoteCtlCom extends TextMessageListener implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GaitCtlCom gaitCtlCom;

    @Autowired
    private Messenger messenger;

    @Value("${dragoon.mqtt.aliyun.product-key}")
    private String productKey;

    @Value("${dragoon.mqtt.aliyun.device-name}")
    private String deviceName;

    @Autowired
    private EnvCom envCom;

    private final Gson gson = new GsonBuilder()
            .create();

    @Override
    public void onMessage(String topic, String message) {
        logger.info("receive gait: {}", message);
        final Gait gait = gson.fromJson(message, Gait.class);

        gaitCtlCom.execute(gait, response -> {
            if (response.isSuccess()) {
                logger.info("gait execute success!");
            } else if (response.isInterrupted()) {
                logger.warn("gait execute interrupted!");
            } else {
                logger.warn("gait execute occur error!", response.getException());
            }
        });

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(envCom.isProd()) {
            messenger.subscribe(
                    String.format("/%s/%s/user/messenger/test/post", productKey, deviceName),
                    this
            );
        }
    }


}
