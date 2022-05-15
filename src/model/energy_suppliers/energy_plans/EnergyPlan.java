package model.energy_suppliers.energy_plans;

public abstract class EnergyPlan {
    protected static float baseCost;
    protected static float vat;

    public abstract float energyCost(int numDevices, float devicesConsumption);
}
