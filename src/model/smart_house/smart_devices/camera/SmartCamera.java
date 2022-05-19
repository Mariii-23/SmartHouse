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

    public SmartCamera(SmartCamera that) {
        super(that);
        this.width = that.getWidth();
        this.height = that.getHeight();
        this.fileSize = that.getFileSize();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getFileSize() {
        return fileSize;
    }

    @Override
    public float getEnergyConsumption() {
        return isOn() ? fileSize * width * height : 0;
    }

    @Override
    public String getSimpleName() {
        return "SmartCamera";
    }

    @Override
    public SmartCamera clone() {
        return new SmartCamera(this);
    }
}

