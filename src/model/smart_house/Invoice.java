package model.smart_house;

import model.energy_suppliers.EnergySupplier;

import java.io.Serializable;
import java.time.LocalDate;

public class Invoice implements Serializable {
    private final int numberOfDays;
    private final float dailyConsumption;
    private final float cost;
    private final LocalDate date;
    private final String company;

    public Invoice(int numberOfDays, float dailyConsumption, float cost, LocalDate date, String company) {
        this.numberOfDays = numberOfDays;
        this.dailyConsumption = dailyConsumption;
        this.cost = cost;
        this.date = date;
        this.company = company;
    }
}
