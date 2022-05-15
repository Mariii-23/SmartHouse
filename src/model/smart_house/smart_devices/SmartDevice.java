package model.smart_house.smart_devices;

public abstract class SmartDevice {
    protected final float fixedConsumption;
    private boolean on;

    public SmartDevice(float fixedConsumption, boolean on) {
        this.fixedConsumption = fixedConsumption;
        this.on = on;
    }

    public SmartDevice(float fixedConsumption) {
        this.fixedConsumption = fixedConsumption;
        this.on = false;
    }

    public boolean isOn() {
        return on;
    }

    public void switchDevice() {
        on = !on;
    }

    public void switchOn() {
        on = true;
    }

    public void switchOff() {
        on = false;
    }

    public abstract float getEnergyConsumption();
}