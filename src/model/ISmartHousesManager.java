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
    // events
    // energy supplier

    /**
     * @param energySupplierName name of the energy supplier to add
     * @throws EnergySupplierAlreadyExistsException if the name correspond to an already existing energy supplier
     */
    void addEnergySupplier(String energySupplierName) throws EnergySupplierAlreadyExistsException;

    /**
     * @return all energy plans available
     */
    String[] getAllEnergyPlans();

    /**
     * @param energySupplierName name of the energy supplier to change the energy plan
     * @param energyPlanName     name of the new energy plan
     * @throws EnergySupplierDoesNotExistException if the name does not correspond to an already existing energy supplier
     * @throws ClassNotFoundException              if the energy plan name does not correspond to a valid energy plan
     */
    void changeEnergyPlan(String energySupplierName, String energyPlanName)
            throws EnergySupplierDoesNotExistException, ClassNotFoundException;

    /**
     * @param energySupplierName name of the energy supplier to change the discount
     * @param discount           new value of the discount
     * @throws EnergySupplierDoesNotExistException if the name does not correspond to an already existing energy supplier
     */
    void changeEnergySupplierDiscount(String energySupplierName, int discount)
            throws EnergySupplierDoesNotExistException;

    // smart house

    /**
     * @param smartHouse the new smart house to be added
     * @throws EnergySupplierDoesNotExistException if the name does not correspond to an already existing energy supplier
     * @throws ProprietaryAlreadyExistException    if the proprietary already has a smart house in the system
     */
    void addSmartHouse(SmartHouse smartHouse)
            throws EnergySupplierDoesNotExistException, ProprietaryAlreadyExistException;

    /**
     * @param name           name of the proprietary of the new smart house
     * @param tin            TIN of the proprietary of the new smart house
     * @param energySupplier energy supplier of the new smart house
     * @throws EnergySupplierDoesNotExistException if the name does not correspond to an already existing energy supplier
     * @throws ProprietaryAlreadyExistException    if the proprietary already has a smart house in the system
     */
    void addSmartHouse(String name, String tin, String energySupplier)
            throws EnergySupplierDoesNotExistException, ProprietaryAlreadyExistException;

    /**
     * @param tin         TIN of the proprietary of the smart house
     * @param division    division where the new device is located
     * @param smartDevice new smart device to be added
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     */
    void addSmartDeviceToHouse(String tin, String division, SmartDevice smartDevice) throws ProprietaryDoesNotExistException;

    /**
     * @param tin TIN of the proprietary of the smart house
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     */
    void turnOffAllHouseDevices(String tin) throws ProprietaryDoesNotExistException;

    /**
     * @param tin TIN of the proprietary of the smart house
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     */
    void turnOnAllHouseDevices(String tin) throws ProprietaryDoesNotExistException;

    /**
     * @param tin      TIN of the proprietary of the smart house
     * @param division division where the device is located
     * @throws DivisionDoesNotExistException    if the division does not exist within the house
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     */
    void turnOnAllHouseDevicesDivision(String tin, String division)
            throws ProprietaryDoesNotExistException, DivisionDoesNotExistException;

    /**
     * @param tin      TIN of the proprietary of the smart house
     * @param division division where the device is located
     * @throws DivisionDoesNotExistException    if the division does not exist within the house
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     */
    void turnOffAllHouseDevicesDivision(String tin, String division)
            throws ProprietaryDoesNotExistException, DivisionDoesNotExistException;

    /**
     * @param tin      TIN of the proprietary of the smart house
     * @param division division where the device is located
     * @param id       id of the device within the division
     * @throws DeviceDoesNotExistException      if the device does not exist in the division
     * @throws DivisionDoesNotExistException    if the division does not exist within the house
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     */
    void turnOffDeviceInDivision(String tin, String division, int id)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException, ProprietaryDoesNotExistException;

    /**
     * @param tin      TIN of the proprietary of the smart house
     * @param division division where the device is located
     * @param id       id of the device within the division
     * @throws DeviceDoesNotExistException      if the device does not exist in the division
     * @throws DivisionDoesNotExistException    if the division does not exist within the house
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     */
    void turnOnDeviceInDivision(String tin, String division, int id)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException, ProprietaryDoesNotExistException;

    // control devices

    /**
     * @param tin      TIN of the proprietary of the smart house
     * @param division division where the smart bulb is located
     * @param id       id of the smart bulb within the division
     * @param tone     tone to change the bulb to
     * @throws DeviceDoesNotExistException      if the device does not exist in the division
     * @throws DivisionDoesNotExistException    if the division does not exist within the house
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     * @throws WrongTypeOfDeviceException       if the device is not a smart bulb
     */
    void smartBulbChangeTone(String tin, String division, int id, Tone tone)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException,
            ProprietaryDoesNotExistException, WrongTypeOfDeviceException;

    /**
     * @param tin      TIN of the proprietary of the smart house
     * @param division division where the smart speaker is located
     * @param id       id of the smart speaker within the division
     * @throws DeviceDoesNotExistException      if the device does not exist in the division
     * @throws DivisionDoesNotExistException    if the division does not exist within the house
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     * @throws WrongTypeOfDeviceException       if the device is not a smart speaker
     */
    void smartSpeakerVolumeDown(String tin, String division, int id)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException,
            ProprietaryDoesNotExistException, WrongTypeOfDeviceException;

    /**
     * @param tin      TIN of the proprietary of the smart house
     * @param division division where the smart speaker is located
     * @param id       id of the smart speaker within the division
     * @throws DeviceDoesNotExistException      if the device does not exist in the division
     * @throws DivisionDoesNotExistException    if the division does not exist within the house
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     * @throws WrongTypeOfDeviceException       if the device is not a smart speaker
     */
    void smartSpeakerVolumeUp(String tin, String division, int id)
            throws DeviceDoesNotExistException, DivisionDoesNotExistException,
            ProprietaryDoesNotExistException, WrongTypeOfDeviceException;


    /**
     * @return gets the current date of the simulation
     */
    LocalDate getDate();

    /**
     * @param numDays number of days to be skipped
     */
    void skipDays(int numDays);

    /**
     * @param newDate new date to skip to
     */
    void skipToDate(LocalDate newDate);

    // queries

    /**
     * @return a pair of the name of the most profitable supplier and it's earnings
     */
    Optional<Pair<String, Double>> highestProfitSupplier();

    /**
     * @param startDate the start of the time interval
     * @param endDate   the end of the time interval
     * @return a pair of the id of the most costly house, and it's energy cost
     */
    Optional<Pair<String, Double>> mostCostlyHouseBetween(LocalDate startDate, LocalDate endDate);

    /**
     * @param energySupplierName the name of the energy supplier
     * @return a list of all the invoices emitted by the energy supplier
     * @throws EnergySupplierDoesNotExistException if the energy supplier does not exist
     */
    List<Invoice> invoicesByEnergySupplier(String energySupplierName) throws EnergySupplierDoesNotExistException;

    List<Pair<String, Double>> energySuppliersRankedByInvoiceVolumeBetween(LocalDate startDate, LocalDate endDate);

    List<Pair<Proprietary, Double>> proprietariesRankedByEnergyConsumptionBetween(LocalDate startDate, LocalDate endDate);

    HashMap<String, List<SmartDevice>> allDevicesByTin(String tin) throws ProprietaryDoesNotExistException;

    List<SmartDevice> allDevicesByTinAndDivision(String tin, String division)
            throws ProprietaryDoesNotExistException, DivisionDoesNotExistException;

    /**
     * @return a list of all the proprietaries
     */
    List<Proprietary> allProprietaries();

    /**
     * @return a list of the names of all the energy suppliers
     */
    List<String> allEnergySuppliersName();

    /**
     * @param filename the path to the file to write the object to
     * @throws IOException if an error occurs while writing the file
     */
    void saveObjectFile(String filename) throws IOException;
}
