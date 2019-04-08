package com.github.ompc.robot.hexapod.dragoon.component.gait;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 姿态
 */
public class Pose {

    private Limb limb;
    private Joint joint;
    private float angle;

    public Pose() {

    }

    private Pose(final Limb limb,
                 final Joint joint,
                 final float angle) {
        this.limb = limb;
        this.joint = joint;
        this.angle = angle;
    }

    public Limb getLimb() {
        return limb;
    }

    public Joint getJoint() {
        return joint;
    }

    public float getAngle() {
        return angle;
    }

    /**
     * 姿态
     *
     * @param limbs  肢
     * @param joints 关节
     * @param angle  角度
     * @return 姿态集合
     */
    public static Pose[] poses(final Limb[] limbs, final Joint[] joints, final float angle) {
        final Collection<Pose> poses = new ArrayList<>();
        for (final Limb limb : limbs) {
            for (final Joint joint : joints) {
                poses.add(new Pose(limb, joint, angle));
            }
        }
        return poses.toArray(new Pose[poses.size()]);
    }

}
