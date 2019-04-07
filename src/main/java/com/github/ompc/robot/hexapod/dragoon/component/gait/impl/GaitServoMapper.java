package com.github.ompc.robot.hexapod.dragoon.component.gait.impl;

import com.github.ompc.robot.hexapod.dragoon.component.gait.Joint;
import com.github.ompc.robot.hexapod.dragoon.component.gait.Leg;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.github.ompc.robot.hexapod.dragoon.component.gait.Joint.*;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Leg.*;

/**
 * 步态舵机映射
 * {@code
 * <p>
 * +-----+-----+----+
 * | L_H | HIP | 01 |
 * | L_H | KNE | 02 |
 * | L_H | ANK | 03 |
 * +-----+-----+----+
 * | L_M | HIP | 04 |
 * | L_M | KNE | 05 |
 * | L_M | ANK | 06 |
 * +-----+-----+----+
 * | L_F | HIP | 07 |
 * | L_F | KNE | 08 |
 * | L_F | ANK | 09 |
 * +-----+-----+----+
 * | R_F | HIP | 16 |
 * | R_F | KNE | 17 |
 * | R_F | ANK | 18 |
 * +-----+-----+----+
 * | R_M | HIP | 13 |
 * | R_M | KNE | 14 |
 * | R_M | ANK | 15 |
 * +-----+-----+----+
 * | R_H | HIP | 10 |
 * | R_H | KNE | 11 |
 * | R_H | ANK | 12 |
 * +-----+-----+----+
 *
 * <p>
 * }
 */
class GaitServoMapper {

    private final Map<Key, Integer/*SERVO_IDX*/> gaitServoMap
            = new HashMap<>();

    public GaitServoMapper() {
        gaitServoMap.put(new Key(L_H, HIP), 1);
        gaitServoMap.put(new Key(L_H, KNE), 2);
        gaitServoMap.put(new Key(L_H, ANK), 3);
        gaitServoMap.put(new Key(L_M, HIP), 4);
        gaitServoMap.put(new Key(L_M, KNE), 5);
        gaitServoMap.put(new Key(L_M, ANK), 6);
        gaitServoMap.put(new Key(L_F, HIP), 7);
        gaitServoMap.put(new Key(L_F, KNE), 8);
        gaitServoMap.put(new Key(L_F, ANK), 9);
        gaitServoMap.put(new Key(R_F, HIP), 16);
        gaitServoMap.put(new Key(R_F, KNE), 17);
        gaitServoMap.put(new Key(R_F, ANK), 18);
        gaitServoMap.put(new Key(R_M, HIP), 13);
        gaitServoMap.put(new Key(R_M, KNE), 14);
        gaitServoMap.put(new Key(R_M, ANK), 15);
        gaitServoMap.put(new Key(R_H, HIP), 10);
        gaitServoMap.put(new Key(R_H, KNE), 11);
        gaitServoMap.put(new Key(R_H, ANK), 12);
    }

    public Integer getServoIndex(Leg leg, Joint joint) {
        return gaitServoMap.get(new Key(leg, joint));
    }


    class Key {

        final Leg leg;
        final Joint joint;

        Key(Leg leg, Joint joint) {
            this.leg = leg;
            this.joint = joint;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof Key
                    && ((Key) other).leg == leg
                    && ((Key) other).joint == joint;
        }

        @Override
        public int hashCode() {
            return Objects.hash(leg, joint);
        }
    }

}
