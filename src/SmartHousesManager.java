import energy_suppliers.EnergySupplier;
import proprietary.TIN;
import smart_house.SmartHouse;

import java.util.List;
import java.util.Map;

public class SmartHousesManager {
    private Map<TIN, List<SmartHouse>> smartHouses;
    private Map<String, EnergySupplier> energySuppliers;
}
