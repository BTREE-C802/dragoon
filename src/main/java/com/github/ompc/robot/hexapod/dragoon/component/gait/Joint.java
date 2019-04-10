package com.github.ompc.robot.hexapod.dragoon.component.gait;

/**
 * 关节
 */
public enum Joint {

    HIP(0f),
    KNE(0f),
    ANK(45f);

    private final float fix;

    Joint(float fix) {
        this.fix = fix;
    }

    /**
     * 获取夹角修正值
     *
     * @return 夹角修正值
     */
    public float getFix() {
        return fix;
    }

    /**
     * 所有关节
     */
    public static Joint[] JOINT_ALL = Joint.values();


}
