package model.energy_suppliers;

import model.energy_suppliers.energy_plans.BasePlan;
import model.energy_suppliers.energy_plans.EnergyPlan;

public class EnergySupplier {
    private final String name;
    private EnergyPlan energyPlan;

    public EnergySupplier(String name) {
        this.name = name;
        this.energyPlan = new BasePlan();
    }

    public float energyCost(int numDevices, float devicesConsumption) {
        return energyPlan.energyCost(numDevices, devicesConsumption);
    }
}
