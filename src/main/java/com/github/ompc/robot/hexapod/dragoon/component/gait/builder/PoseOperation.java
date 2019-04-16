package com.github.ompc.robot.hexapod.dragoon.component.gait.builder;

import com.github.ompc.robot.hexapod.dragoon.component.gait.Pose;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.System.arraycopy;
import static org.apache.commons.lang3.ArrayUtils.addAll;

/**
 * 姿态集合预算
 */
@FunctionalInterface
public interface PoseOperation {

    /**
     * 矩阵运算
     *
     * @param src 源头
     * @param dst 目标
     * @return 结果
     */
    Pose[] operate(Pose[] src, Pose[] dst);

    /**
     * 相加：src + dst
     */
    class PoseOperationAddition implements PoseOperation {

        @Override
        public Pose[] operate(Pose[] src, Pose[] dst) {
            final Map<String, Pose> uniqueMap = new LinkedHashMap<>();
            for (Pose pose : src) {
                final String key = "KEY$" + pose.getLimb() + "|" + pose.getJoint();
                uniqueMap.put(key, pose);
            }
            for (Pose pose : zip(dst)) {
                final String key = "KEY$" + pose.getLimb() + "|" + pose.getJoint();
                if (uniqueMap.containsKey(key)) {
                    final Pose exist = uniqueMap.get(key);
                    uniqueMap.put(
                            key,
                            new Pose(
                                    pose.getLimb(),
                                    pose.getJoint(),
                                    BigDecimal.valueOf(exist.getRadian())
                                            .add(BigDecimal.valueOf(pose.getRadian()))
                                            .doubleValue()
                            )
                    );
                } else {
                    uniqueMap.put(key, pose);
                }
            }
            return uniqueMap.values().toArray(new Pose[uniqueMap.size()]);
        }

    }

    /**
     * 并集：src + dst，若src已存在对应(limb,joint)则用dst的进行替换
     */
    class PoseOperationUnion implements PoseOperation {

        @Override
        public Pose[] operate(Pose[] src, Pose[] dst) {
            final Pose[] mix = new Pose[src.length + dst.length];
            arraycopy(src, 0, mix, 0, src.length);
            arraycopy(dst, 0, mix, src.length, dst.length);
            return zip(mix);
        }

    }

    /**
     * 替换：dst替换src
     */
    class PoseOperationReplace implements PoseOperation {

        @Override
        public Pose[] operate(Pose[] src, Pose[] dst) {
            return dst;
        }
    }

    /**
     * 压缩目标
     *
     * @param src 目标
     * @return 压缩结果
     */
    default Pose[] zip(Pose[] src) {
        final Map<String, Pose> uniqueMap = new LinkedHashMap<>();
        for (Pose pose : src) {
            final String key = "KEY$" + pose.getLimb() + "|" + pose.getJoint();
            uniqueMap.put(key, pose);
        }
        return uniqueMap.values().toArray(new Pose[uniqueMap.size()]);
    }

}
