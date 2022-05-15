package model.energy_suppliers.energy_plans;

import java.io.Serializable;

public abstract class EnergyPlan implements Serializable {
    protected static float baseCost;
    protected static float vat;

    public abstract float energyCost(int numDevices, float devicesConsumption);
}
