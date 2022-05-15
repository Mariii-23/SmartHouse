package model.energy_suppliers.energy_plans;

import java.io.Serializable;

public class BigHousePlan extends EnergyPlan implements Serializable  {

    public float energyCost(int numDevices, float devicesConsumption) {
        return numDevices > 10
            ? (baseCost * devicesConsumption * (1 + vat)) * 0.90f
            : (baseCost * devicesConsumption * (1 + vat)) * 0.75f;
    }
}
