package model;

import model.energy_suppliers.EnergySupplier;
import model.energy_suppliers.Invoice;
import model.parse.Parser;
import model.proprietary.Proprietary;
import model.smart_house.DeviceDoesNotExistException;
import model.smart_house.SmartHouse;
import model.smart_house.smart_devices.SmartDevice;
import utils.Pair;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SmartHousesManager implements Serializable, ISmartHouseManager {
    private final Map<String, SmartHouse> smartHousesByTIN;
    private final Map<String, EnergySupplier> energySuppliers;
    private LocalDate date;

    public SmartHousesManager() {
        smartHousesByTIN = new HashMap<>();
        energySuppliers = new HashMap<>();
        date = LocalDate.now();
    }

    public static SmartHousesManager fromFile(String filepath) throws IOException {
        return Parser.parse(filepath);
    }

    public LocalDate todaysDate() {
        return date;
    }

    public List<Proprietary> allProprietaries(){
        return this.smartHousesByTIN.values().stream().map(SmartHouse::getProprietary).collect(Collectors.toList());
    }

    private SmartHouse getSmartHouse(String tin) throws ProprietaryDoesNotExistException {
        SmartHouse smartHouse = smartHousesByTIN.get(tin);
        if (smartHouse == null) {
            throw new ProprietaryDoesNotExistException("Proprietary with tin \"" + tin + "\" does not exist");
        }
        return smartHouse;
    }

    public boolean containsProprietary(String tin) {
        return this.smartHousesByTIN.containsKey(tin);
    }

    public HashMap<String, List<String>> allDevicesByTin(String tin )
            throws ProprietaryDoesNotExistException {
        SmartHouse smartHouse = this.getSmartHouse(tin);
        return smartHouse.getAllDevices();
    }

    public List<String> allDevicesByTinAndDivision(String tin, String division)
            throws ProprietaryDoesNotExistException, DivisionDoesNotExistException {
        SmartHouse smartHouse = this.getSmartHouse(tin);
        return smartHouse.getAllDevicesByDivision(division);
    }

    public void addEnergySupplier(String energySupplierName) {
        energySuppliers.put(energySupplierName, new EnergySupplier(energySupplierName));
    }

    public void addSmartHouse(SmartHouse smartHouse)
        throws EnergySupplierDoesNotExistException {
        // check if the energy supplier exists
        if (energySuppliers.get(smartHouse.getEnergySupplierName()) == null) {
            throw new EnergySupplierDoesNotExistException("Energy Supplier \"" +
                smartHouse.getEnergySupplierName() + "\" does not exist");
        }
        smartHousesByTIN.put(smartHouse.getProprietaryTin(), smartHouse.clone());
    }

    public void addSmartDeviceToHouse(String tin, String division, SmartDevice smartDevice)
        throws ProprietaryDoesNotExistException {
        SmartHouse smartHouse = smartHousesByTIN.get(tin);
        if (smartHouse == null) {
            throw new ProprietaryDoesNotExistException("Proprietary with tin \"" + tin + "\" does not exist");
        }
        smartHouse.addSmartDevice(division, smartDevice.clone());
    }

    public void skipDays(int numDays) {
        LocalDate newDate = date.plusDays(numDays);
        emitInvoices(newDate);
        date = newDate;
    }

    public void turnOnAllDevicesByTin(String tin) throws ProprietaryDoesNotExistException {
        SmartHouse smartHouse = this.getSmartHouse(tin);
        smartHouse.turnOnAllDevices();
    }

    public void turnOffAllDevicesByTin(String tin) throws ProprietaryDoesNotExistException {
        SmartHouse smartHouse = this.getSmartHouse(tin);
        smartHouse.turnOffAllDevices();
    }

    public void turnOnDeviceInDivision(String tin, String division, int id)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException, ProprietaryDoesNotExistException {
        SmartHouse smartHouse = this.getSmartHouse(tin);
        smartHouse.turnOnDeviceInDivision(division, id);
    }

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

    public Optional<Pair<String, Double>> highestProfitSupplier() {
        return energySuppliers.entrySet()
            .stream()
            .map(kv -> new Pair<>(kv.getKey(), kv.getValue().invoiceVolume()))
            .max(Comparator.comparing(Pair::getSecond));
    }

    public Optional<Pair<String, Double>> mostCostlyHouseBetween(LocalDate startDate, LocalDate endDate) {
        return energySuppliers.values()
            .stream()
            .map(energySupplier -> energySupplier.mostCostlyHouseBetween(startDate, endDate))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .max(Comparator.comparing(Pair::getSecond));
    }

    public List<Invoice> invoicesByEnergySupplier(String energySupplierName) throws EnergySupplierDoesNotExistException {
        EnergySupplier energySupplier = this.energySuppliers.get(energySupplierName);
        if (energySupplier == null)
            throw new EnergySupplierDoesNotExistException("Energy Supplier : " + energySupplierName + " does not exist.");
        return energySupplier.getInvoices();
    }

    public List<Pair<String, Double>> energySuppliersRankedByInvoiceVolumeBetween(LocalDate startDate, LocalDate endDate) {
        return this.energySuppliers.values()
            .stream()
            .map(e -> new Pair<>(e.getName(), e.invoiceVolumeBetween(startDate, endDate)))
            .sorted((e1, e2) -> (int) (e1.getSecond() - e2.getSecond()))
            .collect(Collectors.toList());
    }

    public List<Pair<Proprietary, Double>> proprietariesRankedByEnergyConsumptionBetween(LocalDate startDate, LocalDate endDate) {
        return this.energySuppliers.values()
            .stream()
            .map(e -> e.proprietariesEnergyConsumptionBetween(startDate, endDate))
            .flatMap(m -> m.entrySet().stream())
            .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingDouble(Map.Entry::getValue)))
            .entrySet()
            .stream()
            .map(kv -> new Pair<>(smartHousesByTIN.get(kv.getKey()).getProprietary(), kv.getValue()))
            .sorted((e1, e2) -> (int) (e1.getSecond() - e2.getSecond()))
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

    public void saveObjectFile(String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        fos.close();
        oos.close();
    }
}
