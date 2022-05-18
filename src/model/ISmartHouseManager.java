package model;

import model.energy_suppliers.Invoice;
import model.proprietary.Proprietary;
import model.smart_house.DeviceDoesNotExistException;
import model.smart_house.SmartHouse;
import model.smart_house.smart_devices.SmartDevice;
import utils.Pair;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ISmartHouseManager {

    void addEnergySupplier(String energySupplierName);

    void addSmartHouse(SmartHouse smartHouse)
            throws EnergySupplierDoesNotExistException;

    void addSmartDeviceToHouse(String tin, String division, SmartDevice smartDevice)
            throws ProprietaryDoesNotExistException;


    void turnOffAllDevicesByTin(String tin) throws ProprietaryDoesNotExistException;

    void turnOnAllDevicesByTin(String tin) throws ProprietaryDoesNotExistException;

    void turnOffDeviceInDivision(String tin, String division, int id)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException, ProprietaryDoesNotExistException;

    void turnOnDeviceInDivision(String tin, String division, int id)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException, ProprietaryDoesNotExistException;

    void skipDays(int numDays);

    Optional<Pair<String, Double>> highestProfitSupplier();

    Optional<Pair<String, Double>> mostCostlyHouseBetween(LocalDate startDate, LocalDate endDate);

    List<Invoice> invoicesByEnergySupplier(String energySupplierName) throws EnergySupplierDoesNotExistException;

    List<Pair<String, Double>> energySuppliersRankedByInvoiceVolumeBetween(LocalDate startDate, LocalDate endDate);

    List<Pair<Proprietary, Double>> proprietariesRankedByEnergyConsumptionBetween(LocalDate startDate, LocalDate endDate);

    void saveObjectFile(String filename) throws IOException;
}
