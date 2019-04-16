package com.github.ompc.robot.hexapod.dragoon.component.gait;

import java.util.ArrayList;
import java.util.Collection;

import static com.github.ompc.robot.hexapod.dragoon.component.gait.Joint.JOINT_ALL;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Limb.LIMB_ALL;
import static org.apache.commons.lang3.ArrayUtils.toArray;

/**
 * 姿态
 */
public class Pose {

    private Limb limb;
    private Joint joint;
    private double radian;

    public Pose() {

    }

    /**
     * 构建姿态
     *
     * @param limb   肢
     * @param joint  关节
     * @param radian 弧度
     */
    public Pose(final Limb limb,
                 final Joint joint,
                 final double radian) {
        this.limb = limb;
        this.joint = joint;
        this.radian = radian;
    }

    /**
     * 获取姿态所控制的肢
     *
     * @return 姿态所控制的肢
     */
    public Limb getLimb() {
        return limb;
    }

    /**
     * 获取姿态所控制的关节
     *
     * @return 姿态所控制的关节
     */
    public Joint getJoint() {
        return joint;
    }

    /**
     * 获取姿态所描述的弧度
     *
     * @return 姿态所描述的弧度
     */
    public double getRadian() {
        return radian;
    }

    /**
     * 姿态
     *
     * @param limb   肢
     * @param joint  关节
     * @param radian 弧度
     * @return 姿态
     */
    public static Pose pose(final Limb limb, final Joint joint, final double radian) {
        return new Pose(limb, joint, radian);
    }

    /**
     * 姿态集合
     *
     * @param limbs  肢
     * @param joints 关节
     * @param radian 弧度
     * @return 姿态集合
     */
    public static Pose[] poses(final Limb[] limbs, final Joint[] joints, final double radian) {
        final Collection<Pose> poses = new ArrayList<>();
        for (final Limb limb : limbs) {
            for (final Joint joint : joints) {
                poses.add(new Pose(limb, joint, radian));
            }
        }
        return poses.toArray(new Pose[poses.size()]);
    }

    /**
     * 姿态集合
     *
     * @param limb   肢
     * @param joints 关节
     * @param radian 弧度
     * @return 姿态集合
     */
    public static Pose[] poses(final Limb limb, final Joint[] joints, final double radian) {
        return poses(toArray(limb), joints, radian);
    }

    /**
     * 姿态集合
     *
     * @param joint  关节
     * @param radian 弧度
     * @return 姿态集合
     */
    public static Pose[] poses(final Joint joint, final double radian) {
        return poses(LIMB_ALL, toArray(joint), radian);
    }

    /**
     * 姿态集合
     *
     * @param joints 关节
     * @param radian 弧度
     * @return 姿态集合
     */
    public static Pose[] poses(final Joint[] joints, final double radian) {
        return poses(LIMB_ALL, joints, radian);
    }

    /**
     * 姿态集合
     *
     * @param limbs  肢
     * @param joint  关节
     * @param radian 弧度
     * @return 姿态集合
     */
    public static Pose[] poses(final Limb[] limbs, final Joint joint, final double radian) {
        return poses(limbs, toArray(joint), radian);
    }

    /**
     * 姿态集合
     *
     * @param limbs  肢
     * @param radian 弧度
     * @return 姿态集合
     */
    public static Pose[] poses(final Limb[] limbs, final double radian) {
        return poses(limbs, JOINT_ALL, radian);
    }

}
