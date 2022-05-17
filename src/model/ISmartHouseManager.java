package model;

import model.smart_house.Invoice;
import model.smart_house.SmartHouse;
import model.smart_house.smart_devices.SmartDevice;

import java.io.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public interface ISmartHouseManager {

    void addEnergySupplier(String energySupplierName);

    void addSmartHouse(SmartHouse smartHouse)
            throws EnergySupplierDoesNotExistException;

    void addSmartDeviceToHouse(String tin, String division, SmartDevice smartDevice)
            throws ProprietaryDoesNotExistException;

    void skipDays(int numDays);

    Optional<Pair<String, Double>> highestProfitSupplier();

    Optional<Pair<String, Double>> mostExpensiveHouseBetween(LocalDate startDate, LocalDate endDate);

    Set<Invoice> invoicesByEnergySupplier(String energySupplierName) throws EnergySupplierDoesNotExistException;

    Set<Pair<String, Double>> energySupplierOrderBetween(LocalDate startDate, LocalDate endDate);

    void saveObjectFile(String filename) throws IOException;
}
