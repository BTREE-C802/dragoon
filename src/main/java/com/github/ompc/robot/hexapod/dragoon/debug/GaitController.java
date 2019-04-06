package com.github.ompc.robot.hexapod.dragoon.debug;

import com.github.ompc.robot.hexapod.dragoon.component.gait.GaitBuilder;
import com.github.ompc.robot.hexapod.dragoon.component.gait.GaitCtlCom;
import com.github.ompc.robot.hexapod.dragoon.component.gait.Joint;
import com.github.ompc.robot.hexapod.dragoon.component.gait.Pose;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.github.ompc.robot.hexapod.dragoon.component.gait.Joint.*;
import static com.github.ompc.robot.hexapod.dragoon.component.gait.Pose.poses;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class GaitController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GaitCtlCom gaitCtlCom;

    @RequestMapping(method = GET, value = "/gait/reset")
    @ResponseBody
    public String reset(final @RequestParam("duration") long durationMs) {

        gaitCtlCom.execute(
                new GaitBuilder()
                        .append(
                                durationMs,
                                poses(new Joint[]{HIP, KNE}, 90),
                                poses(new Joint[]{ANK}, 0)
                        )
                        .build(),
                isInterrupted -> logger.info("gait reset completed!")
        );

        return "GAIT-RESET-SUCCESS!\n";
    }

}
