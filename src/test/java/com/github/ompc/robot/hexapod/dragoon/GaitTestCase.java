package com.github.ompc.robot.hexapod.dragoon;

import com.github.ompc.robot.hexapod.dragoon.component.gait.Gait;
import com.github.ompc.robot.hexapod.dragoon.component.gait.GaitBuilder;
import com.github.ompc.robot.hexapod.dragoon.component.gait.Joint;
import com.github.ompc.robot.hexapod.dragoon.component.gait.Leg;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import static com.github.ompc.robot.hexapod.dragoon.component.gait.Joint.*;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Leg.R_H;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Pose.poses;

public class GaitTestCase {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @Test
    public void test() {

        final Gait gait = new GaitBuilder()
                .append(
                        500,
                        poses(new Leg[]{R_H}, new Joint[]{ANK}, 270)
                )
                .append(
                        1000,
                        poses(new Joint[]{HIP, KNE}, 90),
                        poses(new Joint[]{ANK}, 0)
                )
                .build();


        final String json = gson.toJson(gait);

        final Gait cloneOfGait = gson.fromJson(json, Gait.class);
        System.out.println(cloneOfGait);


    }

}
