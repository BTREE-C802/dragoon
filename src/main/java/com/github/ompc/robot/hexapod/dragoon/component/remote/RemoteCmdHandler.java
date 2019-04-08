package com.github.ompc.robot.hexapod.dragoon.component.remote;

/**
 * 远程命令处理器
 *
 * @param <T> 命令数据
 */
public interface RemoteCmdHandler<T> {

    /**
     * 获取期待处理的远程命令
     *
     * @return 期待处理的远程命令
     */
    RemoteCmd.Type getRemoteCmdType();

    /**
     * 处理远程命令
     *
     * @param remoteCmd
     */
    void onCmd(RemoteCmd<T> remoteCmd) throws RemoteCmdException;

}
