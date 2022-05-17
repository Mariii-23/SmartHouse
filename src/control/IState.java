package control;

import model.DivisionDoesNotExistException;
import model.EnergySupplierDoesNotExistException;
import model.ProprietaryDoesNotExistException;
import model.smart_house.DeviceNotExistException;
import model.smart_house.SmartHouse;
import model.smart_house.smart_devices.SmartDevice;

import java.io.IOException;

public interface IState {
    void readFromObjectFile(final String filepath) throws IOException, ClassNotFoundException;
    void readFromFile(final String filepath) throws IOException;
    void saveObjectFile(final String filepath) throws IOException;

    void addSmartDevice(SmartDevice device, String division, String tinProprietary)
            throws ProprietaryDoesNotExistException;
    void addSmartHouse(SmartHouse smartHouse)
            throws EnergySupplierDoesNotExistException;
    //void createNewEnegySupplier();

    //void skipDays();

    //void changeSupplierToASmartHouse();
    //void changeValuesSupllier();

    void turnOffAllDevicesByTin(String tin) throws ProprietaryDoesNotExistException;
    void turnOnAllDevicesByTin(String tin) throws ProprietaryDoesNotExistException;
    void  turnOffDeviceInDivision(String tin, String division, int id)
            throws DeviceNotExistException, DivisionDoesNotExistException, ProprietaryDoesNotExistException;
    void  turnOnDeviceInDivision(String tin, String division, int id)
            throws DeviceNotExistException, DivisionDoesNotExistException, ProprietaryDoesNotExistException;

    //void mostExpensiveHouseBetween();
    //void mostExpensiveEnergySupplier();
    //void allInvoicesByEnergySupplier();
    //void energySupplierOrderBetween();
}