package model.smart_house.smart_devices.bulb;

import model.smart_house.smart_devices.SmartDevice;

import java.io.Serializable;

public class SmartBulb extends SmartDevice implements Serializable {
    private Tone tone;
    private float diameter;

    public SmartBulb(float fixedConsumption, boolean on, Tone tone, float diameter) {
        super(fixedConsumption, on);
        this.tone = tone;
        this.diameter = diameter;
    }

    public SmartBulb(float fixedConsumption, Tone tone, float diameter) {
        super(fixedConsumption);
        this.tone = tone;
        this.diameter = diameter;
    }

    public void setTone(Tone tone) {
        this.tone = tone;
    }

    @Override
    public float getEnergyConsumption() {
        return isOn() ? fixedConsumption + tone.getEnergyFactor() : 0;
    }
}
