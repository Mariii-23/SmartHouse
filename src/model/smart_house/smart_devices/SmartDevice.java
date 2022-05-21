package model.smart_house.smart_devices;

import java.io.Serializable;

public abstract class SmartDevice implements Serializable {
    protected final float fixedConsumption; // kWh / day
    private boolean on;

    public SmartDevice(float fixedConsumption, boolean on) {
        this.fixedConsumption = fixedConsumption;
        this.on = on;
    }

    public SmartDevice(float fixedConsumption) {
        this.fixedConsumption = fixedConsumption;
        this.on = false;
    }

    public SmartDevice(SmartDevice that) {
        this.fixedConsumption = that.getFixedConsumption();
        this.on = that.isOn();
    }

    public float getFixedConsumption() {
        return fixedConsumption;
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

    public abstract SmartDevice clone();

    public abstract String getSimpleName();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fixed Consumption :: ").append(fixedConsumption).append("kWh / day\n");
        sb.append("ON :: ").append(on).append("\n");
        return sb.toString();
    }
}
