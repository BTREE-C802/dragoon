package com.github.ompc.robot.hexapod.dragoon.component.remote;

import com.github.ompc.robot.hexapod.dragoon.component.ComException;

import static com.github.ompc.robot.hexapod.dragoon.component.ComException.Type.REMOTE_CMD;

/**
 * 远程命令异常
 */
public class RemoteCmdException extends ComException {

    private RemoteCmd.Type remoteCmdType;

    public RemoteCmdException(RemoteCmd.Type remoteCmdType, String message, Exception cause) {
        super(REMOTE_CMD, message, cause);
        this.remoteCmdType = remoteCmdType;
    }

    public RemoteCmdException(RemoteCmd.Type remoteCmdType, String message) {
        super(REMOTE_CMD, message);
        this.remoteCmdType = remoteCmdType;
    }

    public RemoteCmd.Type getRemoteCmdType() {
        return remoteCmdType;
    }

}
