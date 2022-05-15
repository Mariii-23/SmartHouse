package model.energy_suppliers.energy_plans;

import java.io.Serializable;

public class BasePlan extends EnergyPlan implements Serializable {

    public float energyCost(int numDevices, float devicesConsumption) {
        return (EnergyPlan.baseCost * devicesConsumption * (1 + EnergyPlan.vat)) * 0.90f;
    }
}
