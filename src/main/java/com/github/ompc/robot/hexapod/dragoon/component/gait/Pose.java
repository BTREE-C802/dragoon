package com.github.ompc.robot.hexapod.dragoon.component.gait;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 姿态
 */
public class Pose {

    private Leg leg;
    private Joint joint;
    private float angle;

    public Pose() {

    }

    public Pose(final Leg leg,
                final Joint joint,
                final float angle) {
        this.leg = leg;
        this.joint = joint;
        this.angle = angle;
    }

    public Leg getLeg() {
        return leg;
    }

    public void setLeg(Leg leg) {
        this.leg = leg;
    }

    public Joint getJoint() {
        return joint;
    }

    public void setJoint(Joint joint) {
        this.joint = joint;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }


    public static Pose[] poses(final Leg[] legs,
                                         final Joint[] joints,
                                         final float angle) {
        final Collection<Pose> poses = new ArrayList<>();
        for (final Leg leg : legs) {
            for (final Joint joint : joints) {
                poses.add(new Pose(leg, joint, angle));
            }
        }
        return poses.toArray(new Pose[poses.size()]);
    }

    public static Pose[] poses(final Joint[] joints,
                                         final float angle) {
        return poses(Leg.values(), joints, angle);
    }

}
