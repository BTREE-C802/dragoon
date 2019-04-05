package com.github.ompc.robot.hexapod.dragoon.debug;

import com.github.ompc.robot.hexapod.dragoon.component.PiComException;
import com.github.ompc.robot.hexapod.dragoon.component.ServoCtlPiCom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class DebugController {

    @Autowired
    private ServoCtlPiCom servoCtlPiCom;

    @RequestMapping(method = GET, value = "/servo/{idx}/{angle}")
    @ResponseBody
    public String servoCtl(final @PathVariable("idx") int index,
                           final @PathVariable("angle") int angle,
                           final @RequestParam("duration") long durationMs) throws PiComException {
        servoCtlPiCom.control(durationMs, new ServoCtlPiCom.ServoCmd(index, angle));
        return String.format("SUCCESS:idx=%d;angle=%d;duration=%d;", index, angle, durationMs);
    }

}
