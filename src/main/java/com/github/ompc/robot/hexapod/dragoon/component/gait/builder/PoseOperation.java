package com.github.ompc.robot.hexapod.dragoon.component.gait.builder;

import com.github.ompc.robot.hexapod.dragoon.component.gait.Pose;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 姿态集合预算
 */
@FunctionalInterface
public interface PoseOperation {

    PoseOperation merge = new PoseOperationMerge();
    PoseOperation addition = new PoseOperationAddition();

    /**
     * 矩阵运算
     *
     * @param arguments 运算参数
     * @return 结果
     */
    Pose[] operate(Pose[]... arguments);

    /**
     * 相加：arguments所有姿态合并，相同的(limb,joint)的弧度进行相加
     */
    class PoseOperationAddition implements PoseOperation {

        @Override
        public Pose[] operate(Pose[]... arguments) {
            final Map<Pose.Key, Pose> uniqueMap = new LinkedHashMap<>();
            Arrays.stream(arguments).forEach(argument -> {
                Arrays.stream(argument).forEach(pose -> {
                    final Pose.Key poseKey = pose.getPoseKey();
                    if (uniqueMap.containsKey(poseKey)) {
                        final Pose exist = uniqueMap.get(poseKey);
                        uniqueMap.put(
                                poseKey,
                                new Pose(
                                        pose.getLimb(),
                                        pose.getJoint(),
                                        BigDecimal.valueOf(exist.getRadian())
                                                .add(BigDecimal.valueOf(pose.getRadian()))
                                                .doubleValue()
                                )
                        );
                    } else {
                        uniqueMap.put(poseKey, pose);
                    }
                });
            });
            return uniqueMap.values().toArray(new Pose[0]);
        }
    }

    /**
     * 合并运算：arguments的所有姿态进行合并，相同的(limb,joint)以最后一次出现的为准
     */
    class PoseOperationMerge implements PoseOperation {

        @Override
        public Pose[] operate(Pose[]... arguments) {
            final Map<Pose.Key, Pose> uniqueMap = new LinkedHashMap<>();
            Arrays.stream(arguments).forEach(argument -> {
                Arrays.stream(argument).forEach(pose -> {
                    uniqueMap.put(pose.getPoseKey(), pose);
                });
            });
            return uniqueMap.values().toArray(new Pose[0]);
        }

    }

}
