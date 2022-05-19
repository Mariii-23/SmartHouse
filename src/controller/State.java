package controller;

import model.*;
import model.energy_suppliers.Invoice;
import model.parse.Parser;
import model.proprietary.Proprietary;
import model.smart_house.DeviceDoesNotExistException;
import model.smart_house.SmartHouse;
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
    private ISmartHouseManager smartHouseManager;

    public State() {
        this.smartHouseManager = new SmartHousesManager();
    }

    public LocalDate todaysDate() {
        return this.smartHouseManager.todaysDate();
    }

    public void skipDays(int numDays) {
        this.smartHouseManager.skipDays(numDays);
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

    public List<Proprietary> allProprietarys(){
        return this.smartHouseManager.allProprietaries();
    }

    public HashMap<String, List<String>> allDevicesByTin(String tin)
            throws ProprietaryDoesNotExistException {
        return this.smartHouseManager.allDevicesByTin(tin);
    }

    public List<String> allDevicesByTinAndDivision(String tin, String division)
            throws ProprietaryDoesNotExistException, DivisionDoesNotExistException {
        return this.smartHouseManager.allDevicesByTinAndDivision(tin,division);
    }

    public void addSmartHouse( String tin, String proprietaryName, String energySupplier)
            throws EnergySupplierDoesNotExistException, ProprietaryAlreadyExistException {
        if (this.smartHouseManager.containsProprietary(tin))
            throw new ProprietaryAlreadyExistException(
                    "Proprietary with tin " + tin + " already exists"
            );
        Proprietary proprietary = new Proprietary(proprietaryName,tin);
        SmartHouse smartHouse = new SmartHouse(proprietary, energySupplier);
        this.smartHouseManager.addSmartHouse(smartHouse);
    }

    public void addSmartSpeaker(final String division, String proprietary, final float fixedConsumption,
                                final int volume, final String channel, final String brand)
            throws ProprietaryDoesNotExistException {
        SmartDevice device = new SmartSpeaker(fixedConsumption, volume, channel, brand);
        this.smartHouseManager.addSmartDeviceToHouse(proprietary, division, device);
    }

    public void addSmartCamera(final String division, String proprietary, final float fixedConsumption,
                                final int width, final int height, final float fileSize)
            throws ProprietaryDoesNotExistException {
        SmartDevice device = new SmartCamera(fixedConsumption,width,height,fileSize);
        this.smartHouseManager.addSmartDeviceToHouse(proprietary, division, device);
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
        this.smartHouseManager.addSmartDeviceToHouse(proprietary, division, device);
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

    public Optional<Pair<String, Double>> highestProfitSupplier() {
        return this.smartHouseManager.highestProfitSupplier();
    }

    public Optional<Pair<String, Double>> mostCostlyHouseBetween(LocalDate startDate, LocalDate endDate) {
        return this.smartHouseManager.mostCostlyHouseBetween(startDate,endDate);
    }

    public List<Invoice> invoicesByEnergySupplier(String energySupplierName) throws EnergySupplierDoesNotExistException {
        return smartHouseManager.invoicesByEnergySupplier(energySupplierName);
    }

    public List<Pair<String, Double>> energySuppliersRankedByInvoiceVolumeBetween(LocalDate startDate, LocalDate endDate) {
        return this.smartHouseManager.energySuppliersRankedByInvoiceVolumeBetween(startDate, endDate);
    }

    public List<Pair<Proprietary, Double>> proprietariesRankedByEnergyConsumptionBetween(LocalDate startDate, LocalDate endDate){
        return this.smartHouseManager.proprietariesRankedByEnergyConsumptionBetween(startDate,endDate);
    }
}