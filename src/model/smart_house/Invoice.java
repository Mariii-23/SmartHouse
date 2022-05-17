package model.smart_house;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Invoice implements Serializable {
    private final int numberOfDays;
    private final float dailyConsumption;
    private final float cost;
    private final LocalDate date;
    private final String company;

    public Invoice(Invoice that) {
        this.numberOfDays = that.getNumberOfDays();
        this.dailyConsumption = that.getDailyConsumption();
        this.cost = that.getCost();
        this.date = that.getDate();
        this.company = that.getCompany();
    }

    public Invoice(int numberOfDays, float dailyConsumption, float cost, LocalDate date, String company) {
        this.numberOfDays = numberOfDays;
        this.dailyConsumption = dailyConsumption;
        this.cost = cost;
        this.date = date;
        this.company = company;
    }

    public float getCost() {
        return cost;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public float getDailyConsumption() {
        return dailyConsumption;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCompany() {
        return company;
    }

    public Invoice clone() {
        return new Invoice(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return numberOfDays == invoice.numberOfDays
                && invoice.dailyConsumption == dailyConsumption
                && invoice.cost == cost
                && Objects.equals(date, invoice.date)
                && Objects.equals(company, invoice.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfDays, dailyConsumption, cost, date, company);
    }
}
