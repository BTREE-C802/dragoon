package com.github.ompc.robot.hexapod.dragoon.component.gait;

/**
 * 肢
 */
public enum Limb {
    L_F(50, 50),
    L_M(50, 50),
    L_H(50, 50),
    R_F(50, 50),
    R_M(50, 50),
    R_H(50, 50);

    private final int upperLength;
    private final int lowerLength;


    /**
     * 所有肢
     */
    public static Limb[] LIMB_ALL = Limb.values();

    Limb(int upperLength, int lowerLength) {
        this.upperLength = upperLength;
        this.lowerLength = lowerLength;
    }

    /**
     * 获取肢的上臂长度（毫米）
     *
     * @return 上臂长度（毫米）
     */
    public int getUpperLength() {
        return upperLength;
    }

    /**
     * 获取肢的下臂长度（毫米）
     *
     * @return 下臂长度（毫米）
     */
    public int getLowerLength() {
        return lowerLength;
    }

    /**
     * 获取臂总长度（毫米）
     *
     * @return 臂总长度（毫米）
     */
    public int getLength() {
        return getUpperLength() + getLowerLength();
    }

}
