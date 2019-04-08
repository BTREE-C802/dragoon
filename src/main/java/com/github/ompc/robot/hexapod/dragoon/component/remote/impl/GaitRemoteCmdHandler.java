package com.github.ompc.robot.hexapod.dragoon.component.remote.impl;

import com.github.ompc.robot.hexapod.dragoon.component.gait.Gait;
import com.github.ompc.robot.hexapod.dragoon.component.gait.GaitCtlCom;
import com.github.ompc.robot.hexapod.dragoon.component.remote.RemoteCmd;
import com.github.ompc.robot.hexapod.dragoon.component.remote.RemoteCmdException;
import com.github.ompc.robot.hexapod.dragoon.component.remote.RemoteCmdHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.ompc.robot.hexapod.dragoon.component.remote.RemoteCmd.Type.GAIT;

@Component
public class GaitRemoteCmdHandler implements RemoteCmdHandler<Gait> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GaitCtlCom gaitCtlCom;

    @Override
    public RemoteCmd.Type getRemoteCmdType() {
        return GAIT;
    }

    @Override
    public void onCmd(RemoteCmd<Gait> remoteCmd) throws RemoteCmdException {

        if (null == remoteCmd.getData()) {
            throw new RemoteCmdException(GAIT, "DATA is null!");
        }

        gaitCtlCom.execute(remoteCmd.getData(), response -> {
            if (response.isSuccess()) {
                logger.info("gait execute success!");
            } else if (response.isInterrupted()) {
                logger.warn("gait execute interrupted!");
            } else {
                logger.warn("gait execute occur error!", response.getException());
            }
        });

    }

}
