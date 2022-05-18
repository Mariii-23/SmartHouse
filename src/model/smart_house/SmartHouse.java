package model.smart_house;

import model.DivisionDoesNotExistException;
import model.proprietary.Proprietary;
import model.smart_house.smart_devices.SmartDevice;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SmartHouse implements Serializable {
    private final Proprietary proprietary;
    private final Map<String, Division> divisionsByName;
    private String energySupplier;

    public SmartHouse(Proprietary proprietary, String energySupplier) {
        this.proprietary = proprietary;
        this.divisionsByName = new HashMap<>();
        this.energySupplier = energySupplier;
    }

    public SmartHouse(SmartHouse that) {
        this.proprietary = that.getProprietary();
        this.divisionsByName = that.getDivisionsByName();
    }

    public Proprietary getProprietary() {
        return proprietary.clone();
    }

    public Map<String, Division> getDivisionsByName() {
        return divisionsByName
            .values()
            .stream()
            .map(Division::clone)
            .collect(Collectors.toMap(Division::getName, Function.identity()));
    }

    public String getProprietaryTin() {
        return proprietary.getTin();
    }

    public void addSmartDevice(String divisionName, SmartDevice smartDevice) {
        Division division = divisionsByName.get(divisionName);
        if (division == null) {
            division = new Division(divisionName);
            divisionsByName.put(divisionName, division);
        }
        division.addSmartDevice(smartDevice);
    }

    public void addDivision(Division division) {
        divisionsByName.put(division.getName(), division);
    }

    public float getEnergyConsumption() {
        return (float) divisionsByName
            .values()
            .stream()
            .mapToDouble(Division::getEnergyConsumption)
            .sum();
    }

    public int getNumDevices() {
        return divisionsByName
            .values()
            .stream()
            .mapToInt(Division::getNumDevices)
            .sum();
    }

    public String getEnergySupplierName() {
        return energySupplier;
    }

    public void turnOnAllDevices() {
        this.divisionsByName.values().forEach(Division::switchDevicesOn);
    }

    public void turnOffAllDevices() {
        this.divisionsByName.values().forEach(Division::switchDevicesOff);
    }

    public void turnOnDeviceInDivision(String division, int id)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException {
        Division elem = this.divisionsByName.get(division);
        if (elem == null)
            throw new DivisionDoesNotExistException("Division \"" + division + "\" does not exist");
        elem.switchDeviceOn(id);
    }

    public void turnOffDeviceInDivision(String division, int id)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException {
        Division elem = this.divisionsByName.get(division);
        if (elem == null)
            throw new DivisionDoesNotExistException("Division \"" + division + "\" does not exist");
        elem.switchDeviceOff(id);
    }

    @Override
    public SmartHouse clone() {
        return new SmartHouse(this);
    }
}
