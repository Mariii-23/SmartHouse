package model.smart_house.smart_devices.camera;

import model.smart_house.smart_devices.SmartDevice;

import java.io.Serializable;

public class SmartCamera extends SmartDevice implements Serializable {
    private final int width;
    private final int height;
    private final float fileSize;

    public SmartCamera(float fixedConsumption, boolean on, int width, int height, float fileSize) {
        super(fixedConsumption, on);
        this.width = width;
        this.height = height;
        this.fileSize = fileSize;
    }

    public SmartCamera(float fixedConsumption, int width, int height, float fileSize) {
        super(fixedConsumption);
        this.width = width;
        this.height = height;
        this.fileSize = fileSize;
    }

    @Override
    public float getEnergyConsumption() {
        return isOn() ? fileSize * width * height : 0;
    }
}

