package model.smart_house.smart_devices.bulb;

import java.io.Serializable;

public enum Tone implements Serializable {
    WARM(1),
    NEUTRAL(2),
    COLD(3);

    private final int energyFactor;

    Tone(int energyFactor) {
        this.energyFactor = energyFactor;
    }

    public int getEnergyFactor() {
        return energyFactor;
    }
}
