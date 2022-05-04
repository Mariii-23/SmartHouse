package smart_house.smart_devices.camera;

import smart_house.smart_devices.SmartDevice;

public class SmartCamera extends SmartDevice {
    private final float resolution;
    private float fileSize;

    public SmartCamera(float installationCost, boolean on, float resolution, float fileSize) {
        super(installationCost, on);
        this.resolution = resolution;
        this.fileSize = fileSize;
    }

    @Override
    public float getPowerConsumption() {
        return isOn() ? fileSize * resolution : 0;
    }
}

