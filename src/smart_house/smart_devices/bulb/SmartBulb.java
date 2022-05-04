package smart_house.smart_devices.bulb;

import smart_house.smart_devices.SmartDevice;

public class SmartBulb extends SmartDevice {
    private static final float fixedCost = 0;

    private Tone tone;

    public SmartBulb(float installationCost, boolean on, Tone tone) {
        super(installationCost, on);
        this.tone = tone;
    }

    public SmartBulb(float installationCost, Tone tone) {
        super(installationCost);
        this.tone = tone;
    }

    public void setTone(Tone tone) {
        this.tone = tone;
    }

    @Override
    public float getPowerConsumption() {
        return isOn() ? fixedCost + tone.getEnergyFactor() : 0;
    }
}
