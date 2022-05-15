package model;

import model.energy_suppliers.EnergySupplier;
import model.parse.Parser;
import model.smart_house.Invoice;
import model.smart_house.SmartHouse;
import model.smart_house.smart_devices.SmartDevice;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SmartHousesManager implements Serializable {
    private final Map<String, SmartHouse> smartHousesByTIN;
    private final Map<String, EnergySupplier> energySuppliers;

    public SmartHousesManager() {
        smartHousesByTIN = new HashMap<>();
        energySuppliers = new HashMap<>();
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

    public void emitInvoices(int numDays) {
        for (SmartHouse smartHouse : smartHousesByTIN.values()) {
            // we assume the energy supplier exists in the map because we assure it exists on house insertion.
            EnergySupplier energySupplier = energySuppliers.get(smartHouse.getEnergySupplierName());
            float dailyConsumption = smartHouse.getEnergyConsumption();
            float energyCost = energySupplier.energyCost(smartHouse.getNumDevices(), dailyConsumption) * numDays;
            // FIXME Aqui teremos q por a data na qual nos encontramos
            smartHouse.addInvoice(new Invoice(numDays, dailyConsumption, energyCost,
                    LocalDate.now(), energySupplier.getName()));
        }
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
