package com.github.ompc.robot.hexapod.dragoon.component.gait;

/**
 * 肢
 */
public enum Limb {

    L_F,
    L_M,
    L_H,
    R_F,
    R_M,
    R_H;


    /**
     * 所有肢
     */
    public static Limb[] LIMB_ALL = Limb.values();

    public static Limb[] LIMB_ALL_L = {L_F,L_M,L_H};
    public static Limb[] LIMB_ALL_R = {R_F,R_M,R_H};

}
