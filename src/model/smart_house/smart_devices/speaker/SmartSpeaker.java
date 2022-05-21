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

    public SmartSpeaker(SmartSpeaker that) {
        super(that);
        this.volume = that.getVolume();
        this.channel = that.getChannel();
        this.brand = that.getBrand();
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

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String newChannel) {
        this.channel = newChannel;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public String getSimpleName() {
        return "SmartSpeaker";
    }

    @Override
    public float getEnergyConsumption() {
        return isOn() ? fixedConsumption * this.volume / 100.f : 0;
    }

    @Override
    public SmartSpeaker clone() {
        return new SmartSpeaker(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SmartSpeaker\n");
        sb.append("Volume :: ").append(volume).append("\n");
        sb.append("Channel  :: ").append(channel).append("\n");
        sb.append("Brand :: ").append(brand).append("\n");
        sb.append(super.toString());
        return sb.toString();
    }
}