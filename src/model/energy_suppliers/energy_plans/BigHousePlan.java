package model.energy_suppliers.energy_plans;

public class BigHousePlan extends EnergyPlan {

    public float energyCost(int numDevices, float devicesConsumption) {
        return numDevices > 10
            ? (baseCost * devicesConsumption * (1 + vat)) * 0.90f
            : (baseCost * devicesConsumption * (1 + vat)) * 0.75f;
    }
}
