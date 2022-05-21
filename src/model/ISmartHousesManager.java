package model;

import model.energy_suppliers.Invoice;
import model.smart_house.DeviceDoesNotExistException;
import model.smart_house.EnergySupplierAlreadyExistsException;
import model.smart_house.SmartHouse;
import model.smart_house.WrongTypeOfDeviceException;
import model.smart_house.proprietary.Proprietary;
import model.smart_house.smart_devices.SmartDevice;
import model.smart_house.smart_devices.bulb.Tone;
import utils.Pair;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface ISmartHousesManager {
    // FIXME: this method doesn't need to exist
    boolean containsProprietary(String tin);

    // events
    // energy supplier
    void addEnergySupplier(String energySupplierName) throws EnergySupplierAlreadyExistsException;

    String[] getAllEnergyPlans();

    void changeEnergyPlan(String energySupplierName, String energyPlanName)
        throws EnergySupplierDoesNotExistException, ClassNotFoundException;

    void changeEnergySupplierDiscount(String energySupplierName, int discount)
        throws EnergySupplierDoesNotExistException;

    // smart house
    void addSmartHouse(SmartHouse smartHouse) throws EnergySupplierDoesNotExistException;

    void addSmartDeviceToHouse(String tin, String division, SmartDevice smartDevice) throws ProprietaryDoesNotExistException;

    void turnOffAllHouseDevices(String tin) throws ProprietaryDoesNotExistException;

    void turnOnAllHouseDevices(String tin) throws ProprietaryDoesNotExistException;

    void turnOffDeviceInDivision(String tin, String division, int id)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException, ProprietaryDoesNotExistException;

    void turnOnDeviceInDivision(String tin, String division, int id)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException, ProprietaryDoesNotExistException;

    // control devices
    void smartBulbChangeTone(String tin, String division, int id, Tone tone)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException,
        ProprietaryDoesNotExistException, WrongTypeOfDeviceException;

    void smartSpeakerVolumeDown(String tin, String division, int id)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException,
        ProprietaryDoesNotExistException, WrongTypeOfDeviceException;

    void smartSpeakerVolumeUp(String tin, String division, int id)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException,
        ProprietaryDoesNotExistException, WrongTypeOfDeviceException;


    LocalDate getDate();

    void setDate(LocalDate date);

    void skipDays(int numDays);

    void skipToDate(LocalDate newDate);

    // queries
    Optional<Pair<String, Double>> highestProfitSupplier();

    Optional<Pair<String, Double>> mostCostlyHouseBetween(LocalDate startDate, LocalDate endDate);

    List<Invoice> invoicesByEnergySupplier(String energySupplierName) throws EnergySupplierDoesNotExistException;

    List<Pair<String, Double>> energySuppliersRankedByInvoiceVolumeBetween(LocalDate startDate, LocalDate endDate);

    List<Pair<Proprietary, Double>> proprietariesRankedByEnergyConsumptionBetween(LocalDate startDate, LocalDate endDate);

    HashMap<String, List<SmartDevice>> allDevicesByTin(String tin) throws ProprietaryDoesNotExistException;

    List<SmartDevice> allDevicesByTinAndDivision(String tin, String division)
        throws ProprietaryDoesNotExistException, DivisionDoesNotExistException;

    List<Proprietary> allProprietaries();
    List<String> allEnergySuppliersName();

    void saveObjectFile(String filename) throws IOException;
}
