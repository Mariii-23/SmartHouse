package model.smart_house;

import model.DivisionDoesNotExistException;
import model.smart_house.proprietary.Proprietary;
import model.smart_house.smart_devices.SmartDevice;
import model.smart_house.smart_devices.bulb.Tone;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        this.energySupplier = that.energySupplier;
    }

    public Proprietary getProprietary() {
        return proprietary.clone();
    }

    public Map<String, Division> getDivisionsByName() {
        return divisionsByName
                .values()
                .stream()
                .collect(Collectors.toMap(Division::getName, Division::clone));
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
        divisionsByName.put(division.getName(), division.clone());
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

    public void turnOffAllDevicesDivision(String division)
            throws DivisionDoesNotExistException {
        Division div = this.divisionsByName.get(division);
        if (div == null)
            throw new DivisionDoesNotExistException("Division \"" + division + "\" does not exist");
        div.turnOffAllDevices();
    }

    public void turnOnAllDevicesDivision(String division)
            throws DivisionDoesNotExistException {
        Division div = this.divisionsByName.get(division);
        if (div == null)
            throw new DivisionDoesNotExistException("Division \"" + division + "\" does not exist");
        div.turnOnAllDevices();
    }

    public void turnOnDeviceInDivision(String division, int id)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException {
        Division div = this.divisionsByName.get(division);
        if (div == null)
            throw new DivisionDoesNotExistException("Division \"" + division + "\" does not exist");
        div.switchDeviceOn(id);
    }

    public void turnOffDeviceInDivision(String division, int id)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException {
        Division div = this.divisionsByName.get(division);
        if (div == null)
            throw new DivisionDoesNotExistException("Division \"" + division + "\" does not exist");
        div.switchDeviceOff(id);
    }

    public void smartBulbChangeTone(String division, int id, Tone tone)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException, WrongTypeOfDeviceException {
        Division div = this.divisionsByName.get(division);
        if (div == null)
            throw new DivisionDoesNotExistException("Division \"" + division + "\" does not exist");
        div.smartBulbChangeTone(id, tone);
    }

    public void smartSpeakerVolumeUp(String division, int id)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException, WrongTypeOfDeviceException {
        Division div = this.divisionsByName.get(division);
        if (div == null)
            throw new DivisionDoesNotExistException("Division \"" + division + "\" does not exist");
        div.smartSpeakerVolumeUp(id);
    }

    public void smartSpeakerVolumeDown(String division, int id)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException, WrongTypeOfDeviceException {
        Division div = this.divisionsByName.get(division);
        if (div == null)
            throw new DivisionDoesNotExistException("Division \"" + division + "\" does not exist");
        div.smartSpeakerVolumeDown(id);
    }

    public HashMap<String, List<SmartDevice>> getAllDevices() {
        HashMap<String, List<SmartDevice>> result = new HashMap<>();
        this.divisionsByName.forEach((key, value) -> result.put(key, value.getAllDevices()));
        return result;
    }

    public List<SmartDevice> getAllDevicesByDivision(String divisionName) throws DivisionDoesNotExistException {
        Division division = this.divisionsByName.get(divisionName);
        if (division == null)
            throw new DivisionDoesNotExistException("Division does not exist : " + divisionName);
        return division.getAllDevices();
    }

    @Override
    public SmartHouse clone() {
        return new SmartHouse(this);
    }
}
