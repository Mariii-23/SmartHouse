package controller;

import model.DivisionDoesNotExistException;
import model.EnergySupplierDoesNotExistException;
import model.ProprietaryAlreadyExistException;
import model.ProprietaryDoesNotExistException;
import model.energy_suppliers.Invoice;
import model.smart_house.DeviceDoesNotExistException;
import model.smart_house.EnergySupplierAlreadyExistsException;
import model.smart_house.WrongTypeOfDeviceException;
import model.smart_house.proprietary.Proprietary;
import model.smart_house.smart_devices.SmartDevice;
import model.smart_house.smart_devices.bulb.NoSuchToneException;
import utils.Pair;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface IState {
    void readFromObjectFile(final String filepath) throws IOException, ClassNotFoundException;

    void readFromFile(final String filepath) throws IOException;

    void saveObjectFile(final String filepath) throws IOException;

    HashMap<String, List<SmartDevice>> allDevicesByTin(String tin)
        throws ProprietaryDoesNotExistException;

    List<SmartDevice> allDevicesByTinAndDivision(String tin, String division)
        throws ProprietaryDoesNotExistException, DivisionDoesNotExistException;

    List<Proprietary> allProprietaries();
    List<String> allEnergySuppliersName();

    void addSmartHouse(String tin, String proprietaryName, String energySupplier)
        throws EnergySupplierDoesNotExistException, ProprietaryAlreadyExistException;

    void addSmartSpeaker(final String division, String proprietary, final float fixedConsumption,
                         final int volume, final String channel, final String brand)
        throws ProprietaryDoesNotExistException;

    void addSmartCamera(final String division, String proprietary, final float fixedConsumption,
                        final int width, final int height, final float fileSize)
        throws ProprietaryDoesNotExistException;

    void addSmartBulb(String division, String proprietary, float fixedConsumption,
                              String toneName, float diameter)
            throws ProprietaryDoesNotExistException, NoSuchToneException;

    void addEnergySupplier(String energySupplierName) throws EnergySupplierAlreadyExistsException;

    // control devices
    void smartBulbChangeTone(String tin, String division, int id, String toneName)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException,
            ProprietaryDoesNotExistException, WrongTypeOfDeviceException, NoSuchToneException;

    void smartSpeakerVolumeDown(String tin, String division, int id)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException,
            ProprietaryDoesNotExistException, WrongTypeOfDeviceException;

    void smartSpeakerVolumeUp(String tin, String division, int id)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException,
            ProprietaryDoesNotExistException, WrongTypeOfDeviceException;



    String[] getAllEnergyPlans();
    void changeEnergyPlan(String energySupplierName, String energyPlanName)
            throws EnergySupplierDoesNotExistException, ClassNotFoundException;
    void changeEnergySupplierDiscount(String energySupplierName, int discount)
            throws EnergySupplierDoesNotExistException;

    LocalDate todaysDate();
    void skipDays(int numdays);
    void skipToDate(LocalDate newDate);

    void turnOffAllDevicesByTin(String tin) throws ProprietaryDoesNotExistException;
    void turnOnAllDevicesByTin(String tin) throws ProprietaryDoesNotExistException;
    void turnOffDeviceInDivision(String tin, String division, int id)
        throws DivisionDoesNotExistException, ProprietaryDoesNotExistException, DeviceDoesNotExistException;
    void turnOnDeviceInDivision(String tin, String division, int id)
        throws DivisionDoesNotExistException, ProprietaryDoesNotExistException, DeviceDoesNotExistException;

    Optional<Pair<String, Double>> highestProfitSupplier();
    Optional<Pair<String, Double>> mostCostlyHouseBetween(LocalDate startDate, LocalDate endDate);
    List<Invoice> invoicesByEnergySupplier(String energySupplierName) throws EnergySupplierDoesNotExistException;
    List<Pair<String, Double>> energySuppliersRankedByInvoiceVolumeBetween(LocalDate startDate, LocalDate endDate);
    List<Pair<Proprietary, Double>> proprietariesRankedByEnergyConsumptionBetween(LocalDate startDate, LocalDate endDate);
}