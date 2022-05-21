package controller;

import controller.parse.Parser;
import model.*;
import model.energy_suppliers.Invoice;
import model.smart_house.DeviceDoesNotExistException;
import model.smart_house.SmartHouse;
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

    public LocalDate todaysDate() {
        return this.smartHousesManager.getDate();
    }

    public void skipDays(int numDays) {
        this.smartHousesManager.skipDays(numDays);
    }

    public void readFromObjectFile(final String filepath) throws IOException, ClassNotFoundException {
        this.smartHousesManager = SmartHousesManager.readObjectFile(filepath);
    }

    public void readFromFile(final String filepath) throws IOException {
        this.smartHousesManager = Parser.parse(filepath);
    }

    public void saveObjectFile(final String filepath) throws IOException {
        this.smartHousesManager.saveObjectFile(filepath);
    }

    public List<Proprietary> allProprietaries() {
        return this.smartHousesManager.allProprietaries();
    }

    public HashMap<String, List<String>> allDevicesByTin(String tin)
        throws ProprietaryDoesNotExistException {
        return this.smartHousesManager.allDevicesByTin(tin);
    }

    public List<String> allDevicesByTinAndDivision(String tin, String division)
        throws ProprietaryDoesNotExistException, DivisionDoesNotExistException {
        return this.smartHousesManager.allDevicesByTinAndDivision(tin, division);
    }

    public void addSmartHouse( String tin, String proprietaryName, String energySupplier)
            throws EnergySupplierDoesNotExistException, ProprietaryAlreadyExistException {
        if (this.smartHousesManager.containsProprietary(tin))
            throw new ProprietaryAlreadyExistException(
                "Proprietary with tin " + tin + " already exists"
            );
        Proprietary proprietary = new Proprietary(proprietaryName,tin);
        SmartHouse smartHouse = new SmartHouse(proprietary, energySupplier);
        this.smartHousesManager.addSmartHouse(smartHouse);
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
        SmartDevice device = new SmartCamera(fixedConsumption,width,height,fileSize);
        this.smartHousesManager.addSmartDeviceToHouse(proprietary, division, device);
    }

    public void addSmartBulb( String division, String proprietary, float fixedConsumption,
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

    public List<Pair<Proprietary, Double>> proprietariesRankedByEnergyConsumptionBetween(LocalDate startDate, LocalDate endDate){
        return this.smartHousesManager.proprietariesRankedByEnergyConsumptionBetween(startDate, endDate);
    }
}