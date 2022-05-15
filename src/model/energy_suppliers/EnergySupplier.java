package model.energy_suppliers;

import model.energy_suppliers.energy_plans.BasePlan;
import model.energy_suppliers.energy_plans.EnergyPlan;

import java.io.Serializable;

public class EnergySupplier implements Serializable {
    private final String name;
    private EnergyPlan energyPlan;

    public EnergySupplier(String name) {
        this.name = name;
        this.energyPlan = new BasePlan();
    }

    public String getName() {
        return name;
    }

    public float energyCost(int numDevices, float devicesConsumption) {
        return energyPlan.energyCost(numDevices, devicesConsumption);
    }
}
