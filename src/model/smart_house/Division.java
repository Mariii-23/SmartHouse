package model.smart_house;

import model.smart_house.smart_devices.SmartDevice;

import java.util.ArrayList;
import java.util.List;

public class Division {
    private final String name;
    private final List<SmartDevice> smartDevices;

    public Division(String name) {
        this.name = name;
        this.smartDevices = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addSmartDevice(SmartDevice smartDevice) {
        smartDevices.add(smartDevice);
    }

    public void switchDevicesOn() {
        smartDevices.forEach(SmartDevice::switchOn);
    }

    public void switchDevicesOff() {
        smartDevices.forEach(SmartDevice::switchOff);
    }

    public float getEnergyConsumption() {
        return (float) smartDevices
            .stream()
            .mapToDouble(SmartDevice::getEnergyConsumption)
            .sum();
    }

    public int getNumDevices() {
        return smartDevices.size();
    }
}
