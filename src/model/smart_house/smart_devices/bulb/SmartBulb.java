package model.smart_house.smart_devices.bulb;

import model.smart_house.smart_devices.SmartDevice;

import java.io.Serializable;

public class SmartBulb extends SmartDevice implements Serializable {
    private final float diameter;
    private Tone tone;

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

    public SmartBulb(SmartBulb that) {
        super(that);
        this.tone = that.getTone();
        this.diameter = that.getDiameter();
    }

    public Tone getTone() {
        return tone;
    }

    public void setTone(Tone tone) {
        this.tone = tone;
    }

    public float getDiameter() {
        return diameter;
    }

    @Override
    public String getSimpleName() {
        return "SmartBulb";
    }

    @Override
    public float getEnergyConsumption() {
        return isOn() ? fixedConsumption + tone.getEnergyFactor() : 0;
    }

    @Override
    public SmartBulb clone() {
        return new SmartBulb(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SmartBulb\n");
        sb.append("Tone :: ").append(tone.toString()).append("\n")
                .append("Diameter :: ").append(diameter).append("\n");
        sb.append(super.toString());
        return sb.toString();
    }
}
