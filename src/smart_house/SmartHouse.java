package smart_house;

import proprietary.Proprietary;
import smart_house.smart_devices.SmartDevice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class SmartHouse {
    private final Proprietary proprietary;
    private final Map<Division, SmartDevice> smartDevicesPerDivision;
    private final List<Invoice> invoices;

    public SmartHouse(Proprietary proprietary) {
        this.proprietary = proprietary;
        this.smartDevicesPerDivision = new HashMap<>();
        this.invoices = new Stack<>();
    }

    public void addSmartDevice(String division_name, SmartDevice smartDevice) {
        smartDevicesPerDivision.put(new Division(division_name), smartDevice);
    }

    public float getEnergyConsumption() {
        return smartDevicesPerDivision
            .values()
            .stream()
            .map(SmartDevice::getPowerConsumption)
            .reduce(0.0f, Float::sum);
    }
}
