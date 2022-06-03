package controller;

import controller.parse.ParseEvents;
import controller.parse.Parser;
import controller.parse.exceptions.ParseEventException;
import model.*;
import model.energy_suppliers.Invoice;
import model.smart_house.DeviceDoesNotExistException;
import model.smart_house.EnergySupplierAlreadyExistsException;
import model.smart_house.WrongTypeOfDeviceException;
import model.smart_house.proprietary.Proprietary;
import model.smart_house.smart_devices.SmartDevice;
import model.smart_house.smart_devices.bulb.NoSuchToneException;
import model.smart_house.smart_devices.bulb.SmartBulb;
import model.smart_house.smart_devices.bulb.Tone;
import model.smart_house.smart_devices.camera.SmartCamera;
import model.smart_house.smart_devices.speaker.SmartSpeaker;
import utils.Pair;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class State implements IState {
    private ISmartHousesManager smartHousesManager;

    public State() {
        this.smartHousesManager = new SmartHousesManager();
    }

    public LocalDate simulationDate() {
        return this.smartHousesManager.getDate();
    }

    public void skipDays(int numDays) {
        this.smartHousesManager.skipDays(numDays);
    }

    public void skipToDate(LocalDate newDate) {
        this.smartHousesManager.skipToDate(newDate);
    }

    public void readFromObjectFile(final String filepath) throws IOException, ClassNotFoundException {
        this.smartHousesManager = SmartHousesManager.readObjectFile(filepath);
    }

    public void readFromFile(final String filepath) throws IOException, ProprietaryAlreadyExistException {
        this.smartHousesManager = Parser.parse(filepath);
    }

    public void readEventsFromFile(final String filepath)
            throws IOException, EnergySupplierDoesNotExistException, DeviceDoesNotExistException,
            ProprietaryDoesNotExistException, DivisionDoesNotExistException, ParseEventException,
            WrongTypeOfDeviceException, ClassNotFoundException, EnergySupplierAlreadyExistsException,
            ProprietaryAlreadyExistException {
        ParseEvents.fromFile(this.smartHousesManager, filepath);
    }

    public void saveObjectFile(final String filepath) throws IOException {
        this.smartHousesManager.saveObjectFile(filepath);
    }

    public List<Proprietary> allProprietaries() {
        return this.smartHousesManager.allProprietaries();
    }

    public List<String> allEnergySuppliersName() {
        return this.smartHousesManager.allEnergySuppliersName();
    }

    public HashMap<String, List<SmartDevice>> allDevicesByTin(String tin)
            throws ProprietaryDoesNotExistException {
        return this.smartHousesManager.allDevicesByTin(tin);
    }

    public List<SmartDevice> allDevicesByTinAndDivision(String tin, String division)
            throws ProprietaryDoesNotExistException, DivisionDoesNotExistException {
        return this.smartHousesManager.allDevicesByTinAndDivision(tin, division);
    }

    public String[] getAllEnergyPlans() {
        return this.smartHousesManager.getAllEnergyPlans();
    }

    public void changeEnergyPlan(String energySupplierName, String energyPlanName)
            throws EnergySupplierDoesNotExistException, ClassNotFoundException {
        this.smartHousesManager.changeEnergyPlan(energySupplierName, energyPlanName);
    }

    public void changeEnergySupplierDiscount(String energySupplierName, int discount)
            throws EnergySupplierDoesNotExistException {
        this.smartHousesManager.changeEnergySupplierDiscount(energySupplierName, discount);
    }

    public void addSmartHouse(String tin, String proprietaryName, String energySupplier)
            throws EnergySupplierDoesNotExistException, ProprietaryAlreadyExistException {
        this.smartHousesManager.addSmartHouse(proprietaryName, tin, energySupplier);
    }

    public void addSmartSpeaker(final String division, String proprietary, final float fixedConsumption,
                                final int volume, final String channel, final String brand)
            throws ProprietaryDoesNotExistException {
        SmartDevice device = new SmartSpeaker(fixedConsumption, volume, channel, brand);
        this.smartHousesManager.addSmartDeviceToHouse(proprietary, division, device);
    }

    public void addSmartCamera(final String division, String proprietary, final float fixedConsumption,
                               final int width, final int height, final float fileSize)
            throws ProprietaryDoesNotExistException {
        SmartDevice device = new SmartCamera(fixedConsumption, width, height, fileSize);
        this.smartHousesManager.addSmartDeviceToHouse(proprietary, division, device);
    }

    public void addSmartBulb(String division, String proprietary, float fixedConsumption,
                             String toneName, float diameter) throws ProprietaryDoesNotExistException, NoSuchToneException {
        Tone tone;
        try {
            tone = Tone.valueOf(toneName.toUpperCase());
        } catch (Exception e) {
            throw new NoSuchToneException("No shuch tone : " + toneName.toUpperCase());
        }
        SmartDevice device = new SmartBulb(fixedConsumption, tone, diameter);
        this.smartHousesManager.addSmartDeviceToHouse(proprietary, division, device);
    }

    public void addEnergySupplier(String energySupplierName) throws EnergySupplierAlreadyExistsException {
        this.smartHousesManager.addEnergySupplier(energySupplierName);
    }

    // control devices
    public void smartBulbChangeTone(String tin, String division, int id, String toneName)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException,
            ProprietaryDoesNotExistException, WrongTypeOfDeviceException, NoSuchToneException {
        Tone tone;
        try {
            tone = Tone.valueOf(toneName.toUpperCase());
        } catch (Exception e) {
            throw new NoSuchToneException("No shuch tone : " + toneName.toUpperCase());
        }
        this.smartHousesManager.smartBulbChangeTone(tin, division, id, tone);
    }

    public void smartSpeakerVolumeDown(String tin, String division, int id)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException,
            ProprietaryDoesNotExistException, WrongTypeOfDeviceException {
        this.smartHousesManager.smartSpeakerVolumeDown(tin, division, id);
    }

    public void smartSpeakerVolumeUp(String tin, String division, int id)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException,
            ProprietaryDoesNotExistException, WrongTypeOfDeviceException {
        this.smartHousesManager.smartSpeakerVolumeUp(tin, division, id);
    }

    public void turnOffAllDevicesByTin(String tin) throws ProprietaryDoesNotExistException {
        this.smartHousesManager.turnOffAllHouseDevices(tin);
    }

    public void turnOnAllDevicesByTin(String tin) throws ProprietaryDoesNotExistException {
        this.smartHousesManager.turnOnAllHouseDevices(tin);
    }

    public void turnOffDeviceInDivision(String tin, String division, int id)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException, ProprietaryDoesNotExistException {
        this.smartHousesManager.turnOffDeviceInDivision(tin, division, id);
    }

    public void turnOnDeviceInDivision(String tin, String division, int id)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException, ProprietaryDoesNotExistException {
        this.smartHousesManager.turnOnDeviceInDivision(tin, division, id);
    }

    public void turnOffAllHouseDevicesDivision(String tin, String division)
            throws ProprietaryDoesNotExistException, DivisionDoesNotExistException {
        this.smartHousesManager.turnOffAllHouseDevicesDivision(tin, division);
    }

    public void turnOnAllHouseDevicesDivision(String tin, String division)
            throws ProprietaryDoesNotExistException, DivisionDoesNotExistException {
        this.smartHousesManager.turnOnAllHouseDevicesDivision(tin, division);
    }

    public Optional<Pair<String, Double>> highestProfitSupplier() {
        return this.smartHousesManager.highestProfitSupplier();
    }

    public Optional<Pair<String, Double>> mostCostlyHouseBetween(LocalDate startDate, LocalDate endDate) {
        return this.smartHousesManager.mostCostlyHouseBetween(startDate, endDate);
    }

    public List<Invoice> invoicesByEnergySupplier(String energySupplierName) throws EnergySupplierDoesNotExistException {
        return smartHousesManager.invoicesByEnergySupplier(energySupplierName);
    }

    public List<Pair<String, Double>> energySuppliersRankedByInvoiceVolumeBetween(LocalDate startDate, LocalDate endDate) {
        return this.smartHousesManager.energySuppliersRankedByInvoiceVolumeBetween(startDate, endDate);
    }

    public List<Pair<Proprietary, Double>> proprietariesRankedByEnergyConsumptionBetween(LocalDate startDate, LocalDate endDate) {
        return this.smartHousesManager.proprietariesRankedByEnergyConsumptionBetween(startDate, endDate);
    }
}