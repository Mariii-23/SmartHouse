package model.energy_suppliers.energy_plans;

public class BasePlan extends EnergyPlan {

    public float energyCost(int numDevices, float devicesConsumption) {
        return (EnergyPlan.baseCost * devicesConsumption * (1 + EnergyPlan.vat)) * 0.90f;
    }
}
