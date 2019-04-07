package com.github.ompc.robot.hexapod.dragoon.component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvCom implements Com {

    @Value("${dragoon.env}")
    private String env;

    public boolean isProd() {
        return StringUtils.equalsIgnoreCase("PROD", env);
    }

    public boolean isTest() {
        return StringUtils.equalsIgnoreCase("TEST", env);
    }

}
