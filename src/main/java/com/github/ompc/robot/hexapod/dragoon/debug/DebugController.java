package com.github.ompc.robot.hexapod.dragoon.debug;

import com.github.ompc.robot.hexapod.dragoon.device.PiDevException;
import com.github.ompc.robot.hexapod.dragoon.device.ServoCtlPiDev;
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
    private ServoCtlPiDev servoCtlPiCom;

    @RequestMapping(method = GET, value = "/servo/{idx}/{angle}")
    @ResponseBody
    public String servoCtl(final @PathVariable("idx") int index,
                           final @PathVariable("angle") float angle,
                           final @RequestParam("duration") long durationMs) throws PiDevException {
        servoCtlPiCom.control(durationMs, new ServoCtlPiDev.ServoCmd(index, angle));
        return String.format("SUCCESS:idx=%d;angle=%.2f;duration=%d;\n", index, angle, durationMs);
    }

}
