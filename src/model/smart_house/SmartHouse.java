package model.smart_house;

import model.proprietary.Proprietary;
import model.smart_house.smart_devices.SmartDevice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class SmartHouse {
    private final Proprietary proprietary;
    private final Map<String, Division> divisionsByName;
    private String energySupplier;
    private final List<Invoice> invoices;

    public SmartHouse(Proprietary proprietary, String energySupplier) {
        this.proprietary = proprietary;
        this.divisionsByName = new HashMap<>();
        this.invoices = new Stack<>();
        this.energySupplier = energySupplier;
    }

    public String getProprietaryTin() {
        return proprietary.getTin();
    }

    public void addSmartDevice(String divisionName, SmartDevice smartDevice) {
        Division division = divisionsByName.get(divisionName);
        if (division == null) {
            division = new Division(divisionName);
            divisionsByName.put(divisionName, division);
        }
        division.addSmartDevice(smartDevice);
    }

    public void addDivision(Division division) {
        divisionsByName.put(division.getName(), division);
    }

    public float getEnergyConsumption() {
        return (float) divisionsByName
            .values()
            .stream()
            .mapToDouble(Division::getEnergyConsumption)
            .sum();
    }

    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
    }

    public int getNumDevices() {
        return divisionsByName
            .values()
            .stream()
            .mapToInt(Division::getNumDevices)
            .sum();
    }

    public String getEnergySupplierName() {
        return energySupplier;
    }
}
