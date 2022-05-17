package control;

import java.io.IOException;

public interface IState {
    void readFromObjectFile(final String filepath) throws IOException, ClassNotFoundException;
    void readFromFile(final String filepath) throws IOException;
    void saveObjectFile(final String filepath) throws IOException;

    //void createNewProprietary();
    //void createNewSmartDevice();
    //void createNewSmartHouse();
    //void createNewEnegySupplier();

    //void skipDays();

    //void changeSupplierToASmartHouse();
    //void changeValuesSupllier();

    //void turnOnAllDevicesSmartHouse();
    //void turnOffAllDevicesSmartHouse();
    //void turnOnDeviceSmartHouse();
    //void turnOffDeviceSmartHouse();

    //void mostExpensiveHouseBetween();
    //void mostExpensiveEnergySupplier();
    //void allInvoicesByEnergySupplier();
    //void energySupplierOrderBetween();
}