package com.github.ompc.robot.hexapod.dragoon.component.gait;

/**
 * 关节
 */
public enum Joint {

    HIP,
    KNE,
    ANK;

    /**
     * 所有关节
     */
    public static Joint[] JOINT_ALL = Joint.values();


}
