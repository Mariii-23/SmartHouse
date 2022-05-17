package model;

import model.energy_suppliers.EnergySupplier;
import model.parse.Parser;
import model.smart_house.Invoice;
import model.smart_house.SmartHouse;
import model.smart_house.smart_devices.SmartDevice;

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

    public void addEnergySupplier(String energySupplierName) {
        energySuppliers.put(energySupplierName, new EnergySupplier(energySupplierName));
    }

    public void addSmartHouse(SmartHouse smartHouse)
        throws EnergySupplierDoesNotExistException {
        // check if the energy supplier exists
        if (energySuppliers.get(smartHouse.getEnergySupplierName()) == null) {
            throw new EnergySupplierDoesNotExistException();
        }
        smartHousesByTIN.put(smartHouse.getProprietaryTin(), smartHouse);
    }

    public void addSmartDeviceToHouse(String tin, String division, SmartDevice smartDevice)
        throws ProprietaryDoesNotExistException {
        SmartHouse smartHouse = smartHousesByTIN.get(tin);
        if (smartHouse == null) {
            throw new ProprietaryDoesNotExistException();
        }
        smartHouse.addSmartDevice(division, smartDevice);
    }

    public void skipDays(int numDays) {
        date = date.plusDays(numDays);
        emitInvoices(numDays);
    }

    private void emitInvoices(int numDays) {
        for (SmartHouse smartHouse : smartHousesByTIN.values()) {
            // we assume the energy supplier exists in the map because we assure it exists on house insertion.
            EnergySupplier energySupplier = energySuppliers.get(smartHouse.getEnergySupplierName());
            float dailyConsumption = smartHouse.getEnergyConsumption();
            float energyCost = energySupplier.energyCost(smartHouse.getNumDevices(), dailyConsumption) * numDays;
            energySupplier.addInvoice(new Invoice(numDays, dailyConsumption, energyCost, date, energySupplier.getName()),
                    smartHouse.getProprietaryTin());
        }
    }

    public Optional<Pair<String, Double>> highestProfitSupplier() {
        return energySuppliers.entrySet()
                .stream()
                .map(kv -> new Pair<>(kv.getKey(), kv.getValue().getAmountMoney()))
                .max(Comparator.comparing(Pair::getSecond));
        //return energySuppliers.entrySet()
        //        .stream()
        //        .max(Comparator.comparing(kv -> kv.getValue().getAmountMoney()))
        //        .map(kv -> new Pair<>(kv.getKey(), kv.getValue().getAmountMoney()));
    }

    public Optional<Pair<String, Double>> mostExpensiveHouseBetween(LocalDate startDate, LocalDate endDate) {
        return energySuppliers.values()
                .stream()
                .map(energySupplier -> energySupplier.mostExpensiveHouseBetween(startDate, endDate))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .max(Comparator.comparing(Pair::getSecond));
    }

    public Set<Invoice> invoicesByEnergySupplier(String energySupplierName) throws EnergySupplierDoesNotExistException {
        EnergySupplier energySupplier = this.energySuppliers.get(energySupplierName);
        if (energySupplier == null)
            throw new EnergySupplierDoesNotExistException("Energy Supplier : " + energySupplierName + " does not exist.");
        return energySupplier.getAllInvoices();
    }

    public Set<Pair<String, Double>> energySupplierOrderBetween(LocalDate startDate, LocalDate endDate) {
        return this.energySuppliers.values()
                .stream()
                .map(e-> new Pair<>(e.getName(), e.getAmountMoneyBetween(startDate,endDate)))
                .sorted((e1, e2) -> (int) (e1.getSecond() - e2.getSecond()))
                .collect(Collectors.toCollection(LinkedHashSet::new))
                ;
    }

    public static SmartHousesManager readObjectFile(String filename) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        SmartHousesManager smartHousesManager =  (SmartHousesManager) ois.readObject();
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
