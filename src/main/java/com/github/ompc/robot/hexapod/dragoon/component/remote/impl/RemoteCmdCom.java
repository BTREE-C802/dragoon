package com.github.ompc.robot.hexapod.dragoon.component.remote.impl;

import com.github.ompc.robot.hexapod.dragoon.component.EnvCom;
import com.github.ompc.robot.hexapod.dragoon.component.gait.Gait;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.Messenger;
import com.github.ompc.robot.hexapod.dragoon.component.mqtt.messenger.TextMessageListener;
import com.github.ompc.robot.hexapod.dragoon.component.remote.RemoteCmd;
import com.github.ompc.robot.hexapod.dragoon.component.remote.RemoteCmdException;
import com.github.ompc.robot.hexapod.dragoon.component.remote.RemoteCmdHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class RemoteCmdCom extends TextMessageListener implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Messenger messenger;

    @Value("${dragoon.mqtt.aliyun.product-key}")
    private String productKey;

    @Value("${dragoon.mqtt.aliyun.device-name}")
    private String deviceName;

    @Autowired
    private EnvCom envCom;

    @Autowired
    private Collection<RemoteCmdHandler<?>> remoteCmdHandlers;

    private Map<RemoteCmd.Type, RemoteCmdHandler<?>> remoteCmdHandlerMap;

    private final Gson gson = new GsonBuilder()
            .create();

    private RemoteCmdHandler<?> getRemoteCmdHandler(RemoteCmd.Type remoteCmdType) {
        return remoteCmdHandlers.stream()
                .filter(e -> e.getRemoteCmdType().equals(remoteCmdType))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void onMessage(String topic, String message) {

        logger.info("receive RemoteCmd:{}", message);

        final JsonParser jsonParser = new JsonParser();
        final JsonElement jsonElement = jsonParser.parse(message);
        final RemoteCmd.Type remoteCmdType = gson.fromJson(jsonElement.getAsJsonObject().get("type"), RemoteCmd.Type.class);
        if (null == remoteCmdType) {
            logger.warn("unknow RemoteCmd.Type={}", jsonElement.getAsJsonObject().get("type"));
            return;
        }

        final RemoteCmdHandler<?> remoteCmdHandler = remoteCmdHandlerMap.get(remoteCmdType);
        if (null == remoteCmdHandler) {
            logger.warn("no handler for RemoteCmd.Type={}", remoteCmdType);
            return;
        }

        try {
            switch (remoteCmdType) {
                case GAIT: {
                    ((RemoteCmdHandler<Gait>) remoteCmdHandler).onCmd(
                            new RemoteCmd<>(
                                    RemoteCmd.Type.GAIT,
                                    gson.fromJson(jsonElement.getAsJsonObject().get("data"), Gait.class)
                            ));
                    break;
                }

                case GAIT_INTERRUPT: {
                    ((RemoteCmdHandler<Void>) remoteCmdHandler).onCmd(
                            new RemoteCmd<>(
                                    RemoteCmd.Type.GAIT_INTERRUPT,
                                    null
                            ));
                    break;
                }

                default: {
                    //
                }
            }
        } catch (RemoteCmdException cause) {
            logger.warn("remote-cmd handle occur error! type={}", cause.getRemoteCmdType(), cause);
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {

        remoteCmdHandlerMap = remoteCmdHandlers.stream()
                .collect(toMap(RemoteCmdHandler::getRemoteCmdType, identity(), (key1, key2) -> key2));

        if (envCom.isProd()) {
            messenger.subscribe(
                    String.format("/%s/%s/user/messenger/test/post", productKey, deviceName),
                    this
            );
        }
    }

}
