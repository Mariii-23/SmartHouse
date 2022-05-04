package smart_house.smart_devices.bulb;

public enum Tone {
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
