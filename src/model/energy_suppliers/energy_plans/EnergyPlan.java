package model.energy_suppliers.energy_plans;

import java.io.Serializable;
import java.util.Random;

public abstract class EnergyPlan implements Serializable {
    protected static float baseCost;
    protected static float vat;

    public abstract float energyCost(int numDevices, float devicesConsumption);

    public static String[] getAllEnergyPlans() {
        return new String[]{"Base", "BigHouse"};
    }

    public static EnergyPlan getEnergyPlanFromName(String name) throws ClassNotFoundException {
        return switch (name) {
            case "Base" -> new BasePlan();
            case "BigHouse" -> new BigHousePlan();
            default -> throw new ClassNotFoundException();
        };
    }

    public static EnergyPlan randomEnergyPlan() {
        try {
            String[] energyPlans = getAllEnergyPlans();
            Random rand = new Random();

            return getEnergyPlanFromName(energyPlans[rand.nextInt(energyPlans.length)]);
        } catch (ClassNotFoundException _e) {
            // this should never happen
            return new BasePlan();
        }
    }

    public abstract String getSimpleName();

}
