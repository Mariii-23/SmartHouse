package smart_house.smart_devices.speaker;

import smart_house.smart_devices.SmartDevice;

public class SmartSpeaker extends SmartDevice {
    public static final int MAX = 100;
    public static final int MIN = 0;

    private static final float fixedCost = 0;

    private int volume;
    private String channel;

    public SmartSpeaker(final float installationCost, final boolean on, final int volume,
                        final String channel) {
        super(installationCost, on);
        this.volume = volume;
        this.channel = channel;
    }

    public SmartSpeaker(final float installationCost, final int volume, final String channel) {
        super(installationCost);
        this.volume = volume;
        this.channel = channel;
    }

    public SmartSpeaker(final float installationCost, final String channel) {
        super(installationCost);
        this.volume = (MAX - MIN) / 2;
        this.channel = channel;
    }

    public void volumeUp() {
        if (this.volume < MAX) this.volume++;
    }

    public void volumeDown() {
        if (this.volume > MIN) this.volume--;
    }

    public int getVolume() {
        return this.volume;
    }

    public void setChannel(String newChannel) {
        this.channel = newChannel;
    }

    public String getChannel() {
        return this.channel;
    }

    // FIXME
    public float getEnergyFactor() {
        return (160 * this.volume) / 100.f;
    }

    @Override
    public float getPowerConsumption() {
        return isOn() ? fixedCost + getEnergyFactor() : 0;
    }
}