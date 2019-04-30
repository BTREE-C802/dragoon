package com.github.ompc.robot.hexapod.dragoon.component.gait.builder;

import com.github.ompc.robot.hexapod.dragoon.component.gait.Joint;
import com.github.ompc.robot.hexapod.dragoon.component.gait.Limb;
import com.github.ompc.robot.hexapod.dragoon.component.gait.Pose;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import static com.github.ompc.robot.hexapod.dragoon.component.gait.Joint.JOINT_ALL;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Limb.LIMB_ALL;

/**
 * 姿势选择器
 */
public class PoseSelector {

    private final Collection<Pose> poses = new ArrayList<>();

    public SelectJoint limb(Limb... limbs) {
        return joints -> (SetRadian) computeRadian -> {
            for (final Limb limb : limbs) {
                for (final Joint joint : joints) {
                    poses.add(new Pose(limb, joint, computeRadian.compute(limb, joint)));
                }
            }
            return this;
        };
    }

    public SelectJoint limb() {
        return limb(LIMB_ALL);
    }

    public Pose[] selected() {
        return poses.toArray(new Pose[poses.size()]);
    }

    /**
     * 关节选择器
     */
    @FunctionalInterface
    public interface SelectJoint {

        /**
         * 选择：关节
         *
         * @param joints 期待选择的关节
         * @return 设置关节弧度
         */
        SetRadian joint(Joint... joints);

        /**
         * 选择：所有关节
         *
         * @return 设置关节弧度
         */
        default SetRadian joint() {
            return joint(JOINT_ALL);
        }

    }

    /**
     * 设置关节弧度
     */
    @FunctionalInterface
    public interface SetRadian {

        /**
         * 设置关节弧度
         *
         * @param computeRadian 计算关节弧度
         * @return 姿势集合
         */
        PoseSelector radian(ComputeRadian computeRadian);

        /**
         * 设置选定关节的弧度
         *
         * @param radian 选中关节的弧度
         * @return 姿势集合
         */
        default PoseSelector radian(BigDecimal radian) {
            return radian(radian.doubleValue());
        }

        /**
         * 设置选定关节的弧度
         *
         * @param radian 选中关节的弧度
         * @return 姿势集合
         */
        default PoseSelector radian(double radian) {
            return radian((limb, joint) -> radian);
        }

    }

    /**
     * 计算关节弧度
     */
    @FunctionalInterface
    public interface ComputeRadian {

        /**
         * 给出指定臂和关节，计算他的弧度
         *
         * @param limb  臂
         * @param joint 关节
         * @return 关节弧度
         */
        double compute(Limb limb, Joint joint);

    }

    public static PoseSelector select() {
        return new PoseSelector();
    }

}
