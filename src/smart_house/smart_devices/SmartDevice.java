package smart_house.smart_devices;

public abstract class SmartDevice {
    private final float installationCost;
    private boolean on;

    public SmartDevice(float installationCost, boolean on) {
        this.installationCost = installationCost;
        this.on = on;
    }

    public SmartDevice(float installationCost) {
        this.installationCost = installationCost;
        this.on = false;
    }

    // public UUID getId() {
    //     return id;
    // }

    // public float getInstallationCost() {
    //     return installationCost;
    // }

    public boolean isOn() {
        return on;
    }

    public void switchDevice() {
        on = !on;
    }

    public abstract float getPowerConsumption();
}
