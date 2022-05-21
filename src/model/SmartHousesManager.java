package model;

import model.energy_suppliers.EnergySupplier;
import model.energy_suppliers.Invoice;
import model.energy_suppliers.energy_plans.EnergyPlan;
import model.smart_house.DeviceDoesNotExistException;
import model.smart_house.EnergySupplierAlreadyExistsException;
import model.smart_house.SmartHouse;
import model.smart_house.WrongTypeOfDeviceException;
import model.smart_house.proprietary.Proprietary;
import model.smart_house.smart_devices.SmartDevice;
import model.smart_house.smart_devices.bulb.Tone;
import utils.Pair;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SmartHousesManager implements Serializable, ISmartHousesManager {
    private final Map<String, SmartHouse> smartHousesByTIN;
    private final Map<String, EnergySupplier> energySuppliers;
    private LocalDate date;

    public SmartHousesManager() {
        smartHousesByTIN = new HashMap<>();
        energySuppliers = new HashMap<>();
        date = LocalDate.now();
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<String> allEnergySuppliersName() {
        return this.energySuppliers.keySet().stream().toList();
    }

    @Override
    public List<Proprietary> allProprietaries() {
        return this.smartHousesByTIN.values().stream().map(SmartHouse::getProprietary).collect(Collectors.toList());
    }

    private SmartHouse getSmartHouse(String tin) throws ProprietaryDoesNotExistException {
        SmartHouse smartHouse = smartHousesByTIN.get(tin);
        if (smartHouse == null) {
            throw new ProprietaryDoesNotExistException("Proprietary with tin \"" + tin + "\" does not exist");
        }
        return smartHouse;
    }

    private EnergySupplier getEnergySupplier(String name) throws EnergySupplierDoesNotExistException {
        EnergySupplier e = energySuppliers.get(name);
        if (e == null) {
            throw new EnergySupplierDoesNotExistException("Energy Supplier \"" +
                name + "\" does not exist");
        }
        return e;
    }


    @Override
    public boolean containsProprietary(String tin) {
        return this.smartHousesByTIN.containsKey(tin);
    }

    @Override
    public HashMap<String, List<SmartDevice>> allDevicesByTin(String tin)
        throws ProprietaryDoesNotExistException {
        SmartHouse smartHouse = this.getSmartHouse(tin);
        return smartHouse.getAllDevices();
    }

    @Override
    public List<SmartDevice> allDevicesByTinAndDivision(String tin, String division)
        throws ProprietaryDoesNotExistException, DivisionDoesNotExistException {
        SmartHouse smartHouse = this.getSmartHouse(tin);
        return smartHouse.getAllDevicesByDivision(division);
    }

    @Override
    public void addEnergySupplier(String energySupplierName) throws EnergySupplierAlreadyExistsException {
        if (energySuppliers.get(energySupplierName) != null) {
            throw new EnergySupplierAlreadyExistsException("Energy supplier [" + energySupplierName + "] already exists");
        }
        energySuppliers.put(energySupplierName, new EnergySupplier(energySupplierName));
    }

    @Override
    public String[] getAllEnergyPlans() {
        return EnergyPlan.getAllEnergyPlans();
    }

    @Override
    public void changeEnergyPlan(String energySupplierName, String energyPlanName)
        throws EnergySupplierDoesNotExistException, ClassNotFoundException {
        getEnergySupplier(energySupplierName).setEnergyPlan(energyPlanName);
    }

    @Override
    public void changeEnergySupplierDiscount(String energySupplierName, int discount)
        throws EnergySupplierDoesNotExistException {
        getEnergySupplier(energySupplierName).setDiscount(discount);
    }

    @Override
    public void addSmartHouse(SmartHouse smartHouse)
        throws EnergySupplierDoesNotExistException {
        // check if the energy supplier exists
        if (energySuppliers.get(smartHouse.getEnergySupplierName()) == null) {
            throw new EnergySupplierDoesNotExistException("Energy Supplier \"" +
                smartHouse.getEnergySupplierName() + "\" does not exist");
        }
        smartHousesByTIN.put(smartHouse.getProprietaryTin(), smartHouse.clone());
    }

    @Override
    public void addSmartDeviceToHouse(String tin, String division, SmartDevice smartDevice)
        throws ProprietaryDoesNotExistException {
        SmartHouse smartHouse = smartHousesByTIN.get(tin);
        if (smartHouse == null) {
            throw new ProprietaryDoesNotExistException("Proprietary with tin \"" + tin + "\" does not exist");
        }
        smartHouse.addSmartDevice(division, smartDevice.clone());
    }

    @Override
    public void skipDays(int numDays) {
        LocalDate newDate = date.plusDays(numDays);
        emitInvoices(newDate);
        date = newDate;
    }

    @Override
    public void skipToDate(LocalDate newDate) {
        emitInvoices(newDate);
        date = newDate;
    }

    @Override
    public void turnOnAllHouseDevices(String tin) throws ProprietaryDoesNotExistException {
        SmartHouse smartHouse = this.getSmartHouse(tin);
        smartHouse.turnOnAllDevices();
    }

    @Override
    public void turnOffAllHouseDevices(String tin) throws ProprietaryDoesNotExistException {
        SmartHouse smartHouse = this.getSmartHouse(tin);
        smartHouse.turnOffAllDevices();
    }

    @Override
    public void turnOnDeviceInDivision(String tin, String division, int id)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException, ProprietaryDoesNotExistException {
        SmartHouse smartHouse = this.getSmartHouse(tin);
        smartHouse.turnOnDeviceInDivision(division, id);
    }

    @Override
    public void smartBulbChangeTone(String tin, String division, int id, Tone tone)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException,
        ProprietaryDoesNotExistException, WrongTypeOfDeviceException {
        SmartHouse smartHouse = this.getSmartHouse(tin);
        smartHouse.smartBulbChangeTone(division, id, tone);
    }

    @Override
    public void smartSpeakerVolumeDown(String tin, String division, int id)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException,
        ProprietaryDoesNotExistException, WrongTypeOfDeviceException {
        SmartHouse smartHouse = this.getSmartHouse(tin);
        smartHouse.smartSpeakerVolumeDown(division, id);
    }

    @Override
    public void smartSpeakerVolumeUp(String tin, String division, int id)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException,
        ProprietaryDoesNotExistException, WrongTypeOfDeviceException {
        SmartHouse smartHouse = this.getSmartHouse(tin);
        smartHouse.smartSpeakerVolumeUp(division, id);
    }

    @Override
    public void turnOffDeviceInDivision(String tin, String division, int id)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException, ProprietaryDoesNotExistException {
        SmartHouse smartHouse = this.getSmartHouse(tin);
        smartHouse.turnOffDeviceInDivision(division, id);
    }

    private void emitInvoices(LocalDate newDate) {
        for (SmartHouse smartHouse : smartHousesByTIN.values()) {
            // we assume the energy supplier exists in the map because we assure it exists on house insertion.
            EnergySupplier energySupplier = energySuppliers.get(smartHouse.getEnergySupplierName());
            float dailyConsumption = smartHouse.getEnergyConsumption();
            float dailyEnergyCost = energySupplier.energyCost(smartHouse.getNumDevices(), dailyConsumption);
            energySupplier.addInvoice(
                new Invoice(energySupplier.getName(), dailyConsumption, dailyEnergyCost, date, newDate),
                smartHouse.getProprietaryTin()
            );
        }
    }

    @Override
    public Optional<Pair<String, Double>> highestProfitSupplier() {
        return energySuppliers.entrySet()
            .stream()
            .map(kv -> new Pair<>(kv.getKey(), kv.getValue().invoiceVolume()))
            .max(Comparator.comparing(Pair::getSecond));
    }

    @Override
    public Optional<Pair<String, Double>> mostCostlyHouseBetween(LocalDate startDate, LocalDate endDate) {
        return energySuppliers.values()
            .stream()
            .map(energySupplier -> energySupplier.mostCostlyHouseBetween(startDate, endDate))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .max(Comparator.comparing(Pair::getSecond));
    }

    @Override
    public List<Invoice> invoicesByEnergySupplier(String energySupplierName) throws EnergySupplierDoesNotExistException {
        EnergySupplier energySupplier = this.energySuppliers.get(energySupplierName);
        if (energySupplier == null)
            throw new EnergySupplierDoesNotExistException("Energy Supplier : " + energySupplierName + " does not exist.");
        return energySupplier.getInvoices();
    }

    @Override
    public List<Pair<String, Double>> energySuppliersRankedByInvoiceVolumeBetween(LocalDate startDate, LocalDate endDate) {
        return this.energySuppliers.values()
            .stream()
            .map(e -> new Pair<>(e.getName(), e.invoiceVolumeBetween(startDate, endDate)))
            .sorted((e1, e2) -> Double.compare(e2.getSecond(), e1.getSecond()))
            .collect(Collectors.toList());
    }

    @Override
    public List<Pair<Proprietary, Double>> proprietariesRankedByEnergyConsumptionBetween(LocalDate startDate, LocalDate endDate) {
        return this.energySuppliers.values()
            .stream()
            .map(e -> e.proprietariesEnergyConsumptionBetween(startDate, endDate))
            .flatMap(m -> m.entrySet().stream())
            .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingDouble(Map.Entry::getValue)))
            .entrySet()
            .stream()
            .map(kv -> new Pair<>(smartHousesByTIN.get(kv.getKey()).getProprietary(), kv.getValue()))
            .sorted((e1, e2) -> Double.compare(e2.getSecond(), e1.getSecond()))
            .collect(Collectors.toList());
    }

    public static SmartHousesManager readObjectFile(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        SmartHousesManager smartHousesManager = (SmartHousesManager) ois.readObject();
        ois.close();
        fis.close();
        return smartHousesManager;
    }

    @Override
    public void saveObjectFile(String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        fos.close();
        oos.close();
    }
}
