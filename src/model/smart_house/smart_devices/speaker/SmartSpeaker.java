package model.smart_house.smart_devices.speaker;

import model.smart_house.smart_devices.SmartDevice;

import java.io.Serializable;

public class SmartSpeaker extends SmartDevice implements Serializable {
    public static final int MAX = 100;
    public static final int MIN = 0;

    private int volume;
    private String channel;
    private String brand;

    public SmartSpeaker(final float fixedConsumption, final boolean on, final int volume,
                        final String channel, final String brand) {
        super(fixedConsumption, on);
        this.volume = volume;
        this.channel = channel;
        this.brand = brand;
    }

    public SmartSpeaker(final float fixedConsumption, final int volume, final String channel, final String brand) {
        super(fixedConsumption);
        this.volume = volume;
        this.channel = channel;
        this.brand = brand;
    }

    // public SmartSpeaker(final float installationCost, final String channel) {
    //     super(installationCost);
    //     this.volume = (MAX - MIN) / 2;
    //     this.channel = channel;
    // }

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
    public float getEnergyConsumption() {
        return isOn() ? fixedConsumption + getEnergyFactor() : 0;
    }
}