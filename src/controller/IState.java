package controller;

import controller.parse.exceptions.ParseEventException;
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
    /**
     * @param filepath the path of the object file
     * @throws IOException            if an error occurs while reading the file
     * @throws ClassNotFoundException when the object in the file is not the expected one
     */
    void readFromObjectFile(final String filepath) throws IOException, ClassNotFoundException;

    /**
     * @param filepath the path of the logs file
     * @throws IOException                      if an error occurs while reading the file
     * @throws ProprietaryAlreadyExistException if the proprietary already has a smart house in the system
     */
    void readFromFile(final String filepath) throws IOException, ProprietaryAlreadyExistException;

    /**
     * @param filepath the path of the events file
     * @throws IOException                         if an error occurs while reading the file
     * @throws EnergySupplierDoesNotExistException if the energy supplier does not exist
     * @throws DeviceDoesNotExistException         if the device does not exist
     * @throws ProprietaryDoesNotExistException    if the proprietary does not have a house in the system
     * @throws DivisionDoesNotExistException       if the division does not exist within the house
     * @throws ParseEventException                 if an error occurs while parsing each line
     * @throws WrongTypeOfDeviceException          if the device is not the correct smart device
     * @throws ClassNotFoundException              if the energy plan name does not correspond to a valid energy plan
     * @throws EnergySupplierDoesNotExistException if the name does not correspond to an already existing energy supplier
     * @throws ProprietaryAlreadyExistException    if the proprietary already has a smart house in the system
     */
    void readEventsFromFile(final String filepath) throws IOException,
        EnergySupplierDoesNotExistException, DeviceDoesNotExistException,
        ProprietaryDoesNotExistException, DivisionDoesNotExistException,
        ParseEventException, WrongTypeOfDeviceException, ClassNotFoundException,
        EnergySupplierAlreadyExistsException, ProprietaryAlreadyExistException;

    /**
     * @param filepath the path to the file to write the object to
     * @throws IOException if an error occurs while writing the file
     */
    void saveObjectFile(final String filepath) throws IOException;

    /**
     * @param tin TIN of the proprietary of the smart house
     * @return a map of all the devices in a smart house, aggregated by division
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     */
    HashMap<String, List<SmartDevice>> allDevicesByTin(String tin)
        throws ProprietaryDoesNotExistException;

    /**
     * @param tin      TIN of the proprietary of the smart house
     * @param division division where the smart speaker is located
     * @return a list of all the devices in a division of a smart house
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     * @throws DivisionDoesNotExistException    if the division does not exist within the house
     */
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

    // smart house

    /**
     * @param proprietaryName name of the proprietary of the new smart house
     * @param tin             TIN of the proprietary of the new smart house
     * @param energySupplier  energy supplier of the new smart house
     * @throws EnergySupplierDoesNotExistException if the name does not correspond to an already existing energy supplier
     * @throws ProprietaryAlreadyExistException    if the proprietary already has a smart house in the system
     */
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

    /**
     * @param energySupplierName name of the energy supplier to add
     * @throws EnergySupplierAlreadyExistsException if a supplier with the same name already exists
     */
    void addEnergySupplier(String energySupplierName) throws EnergySupplierAlreadyExistsException;

    // control devices

    /**
     * @param tin      TIN of the proprietary of the smart house
     * @param division division where the smart bulb is located
     * @param id       id of the smart bulb within the division
     * @param toneName tone to change the bulb to
     * @throws DeviceDoesNotExistException      if the device does not exist in the division
     * @throws DivisionDoesNotExistException    if the division does not exist within the house
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     * @throws WrongTypeOfDeviceException       if the device is not a smart bulb
     */
    void smartBulbChangeTone(String tin, String division, int id, String toneName)
        throws DeviceDoesNotExistException, DivisionDoesNotExistException,
        ProprietaryDoesNotExistException, WrongTypeOfDeviceException, NoSuchToneException;

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

    /**
     * @return gets the current date of the simulation
     */
    LocalDate simulationDate();

    /**
     * @param numDays number of days to be skipped
     */
    void skipDays(int numDays);

    /**
     * @param newDate new date to skip to
     */
    void skipToDate(LocalDate newDate);

    /**
     * @param tin TIN of the proprietary of the smart house
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     */
    void turnOffAllDevicesByTin(String tin) throws ProprietaryDoesNotExistException;

    /**
     * @param tin TIN of the proprietary of the smart house
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     */
    void turnOnAllDevicesByTin(String tin) throws ProprietaryDoesNotExistException;

    /**
     * @param tin      TIN of the proprietary of the smart house
     * @param division division where the device is located
     * @param id       id of the device within the division
     * @throws DeviceDoesNotExistException      if the device does not exist in the division
     * @throws DivisionDoesNotExistException    if the division does not exist within the house
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     */
    void turnOffDeviceInDivision(String tin, String division, int id)
        throws DivisionDoesNotExistException, ProprietaryDoesNotExistException, DeviceDoesNotExistException;

    /**
     * @param tin      TIN of the proprietary of the smart house
     * @param division division where the device is located
     * @param id       id of the device within the division
     * @throws DeviceDoesNotExistException      if the device does not exist in the division
     * @throws DivisionDoesNotExistException    if the division does not exist within the house
     * @throws ProprietaryDoesNotExistException if the proprietary does not have a house in the system
     */
    void turnOnDeviceInDivision(String tin, String division, int id)
        throws DivisionDoesNotExistException, ProprietaryDoesNotExistException, DeviceDoesNotExistException;

    void turnOffAllHouseDevicesDivision(String tin, String division)
        throws ProprietaryDoesNotExistException, DivisionDoesNotExistException;

    void turnOnAllHouseDevicesDivision(String tin, String division)
        throws ProprietaryDoesNotExistException, DivisionDoesNotExistException;

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

    /**
     * @param startDate the start of the time interval
     * @param endDate   the end of the time interval
     * @return a list of pairs containing the name of the energy supplier alongside it's invoice volume, ranked by most volume
     */
    List<Pair<String, Double>> energySuppliersRankedByInvoiceVolumeBetween(LocalDate startDate, LocalDate endDate);

    /**
     * @param startDate the start of the time interval
     * @param endDate   the end of the time interval
     * @return a list of pairs containing the proprietary alongside it's energy consumption, ranked by most consumption
     */
    List<Pair<Proprietary, Double>> proprietariesRankedByEnergyConsumptionBetween(LocalDate startDate, LocalDate endDate);
}