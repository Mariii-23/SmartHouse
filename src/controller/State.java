package controller;

import model.*;
import model.parse.Parser;
import model.smart_house.DeviceDoesNotExistException;
import model.smart_house.SmartHouse;
import model.smart_house.smart_devices.SmartDevice;

import java.io.IOException;

public class State implements IState {
    private ISmartHouseManager smartHouseManager;

    public State() {
        this.smartHouseManager = new SmartHousesManager();
    }

    public void readFromObjectFile(final String filepath) throws IOException, ClassNotFoundException {
        this.smartHouseManager = SmartHousesManager.readObjectFile(filepath);
    }

    public void readFromFile(final String filepath) throws IOException {
        this.smartHouseManager = Parser.parse(filepath);
    }

    public void saveObjectFile(final String filepath) throws IOException {
        this.smartHouseManager.saveObjectFile(filepath);
    }

    public void addSmartHouse(SmartHouse smartHouse)
            throws EnergySupplierDoesNotExistException {
        this.smartHouseManager.addSmartHouse(smartHouse);
    }

    public void addSmartDevice(SmartDevice device, String division, String tinProprietary)
            throws ProprietaryDoesNotExistException {
        this.smartHouseManager.addSmartDeviceToHouse(tinProprietary, division, device);
    }

    public void turnOffAllDevicesByTin(String tin) throws ProprietaryDoesNotExistException {
        this.smartHouseManager.turnOffAllDevicesByTin(tin);
    }

    public void turnOnAllDevicesByTin(String tin) throws ProprietaryDoesNotExistException {
        this.smartHouseManager.turnOnAllDevicesByTin(tin);
    }

    public void turnOffDeviceInDivision(String tin, String division, int id)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException, ProprietaryDoesNotExistException {
        this.smartHouseManager.turnOffDeviceInDivision(tin, division, id);
    }

    public void turnOnDeviceInDivision(String tin, String division, int id)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException, ProprietaryDoesNotExistException {
        this.smartHouseManager.turnOnDeviceInDivision(tin, division, id);
    }
}