package model.smart_house;

public class Invoice {
    private final int numberOfDays;
    private final float dailyConsumption;
    private final float cost;

    public Invoice(int numberOfDays, float dailyConsumption, float cost) {
        this.numberOfDays = numberOfDays;
        this.dailyConsumption = dailyConsumption;
        this.cost = cost;
    }
}
