package com.github.ompc.robot.hexapod.dragoon.device;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class PiSerialFactoryBean implements FactoryBean<Serial>, DisposableBean {

    @Override
    public Serial getObject() {
        return SerialFactory.createInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return Serial.class;
    }

    @Override
    public void destroy() {
        SerialFactory.shutdown();
    }

}
