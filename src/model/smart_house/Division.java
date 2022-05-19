package model.smart_house;

import model.smart_house.smart_devices.SmartDevice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Division implements Serializable {
    private final String name;
    private final List<SmartDevice> smartDevices;

    public Division(String name) {
        this.name = name;
        this.smartDevices = new ArrayList<>();
    }

    public Division(Division that) {
        this.name = that.getName();
        this.smartDevices = that.getSmartDevices();
    }

    public String getName() {
        return name;
    }

    public List<SmartDevice> getSmartDevices() {
        return smartDevices
            .stream()
            .map(SmartDevice::clone)
            .collect(Collectors.toList());
    }

    public void addSmartDevice(SmartDevice smartDevice) {
        smartDevices.add(smartDevice);
    }

    public void switchDeviceOn(int id) throws DeviceDoesNotExistException {
        try {
            smartDevices.get(id).switchOn();
        } catch (IndexOutOfBoundsException _e) {
            throw new DeviceDoesNotExistException("Device with id: " + id + " does not exist");
        }
    }

    public void switchDeviceOff(int id) throws DeviceDoesNotExistException {
        try {
            smartDevices.get(id).switchOff();
        } catch (IndexOutOfBoundsException _e) {
            throw new DeviceDoesNotExistException("Device with id: " + id + " does not exist");
        }
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

    public List<String> getAllDevicesName() {
        return this.smartDevices.stream().map(SmartDevice::getSimpleName).collect(Collectors.toList());
    }

    @Override
    public Division clone() {
        return new Division(this);
    }
}
