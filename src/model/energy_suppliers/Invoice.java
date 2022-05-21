package model.energy_suppliers;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Invoice implements Serializable {
    private final String companyName;
    private final float dailyConsumption;
    private final float dailyCost;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Invoice(String companyName, float dailyConsumption, float dailyCost, LocalDate startDate, LocalDate endDate) {
        this.companyName = companyName;
        this.dailyConsumption = dailyConsumption;
        this.dailyCost = dailyCost;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Invoice(Invoice that) {
        this.companyName = that.getCompanyName();
        this.dailyConsumption = that.getDailyConsumption();
        this.dailyCost = that.getDailyCost();
        this.startDate = that.getStartDate();
        this.endDate = that.getEndDate();
    }

    public float getDailyCost() {
        return dailyCost;
    }

    public float getCost() {
        return dailyCost * ChronoUnit.DAYS.between(startDate, endDate);
    }

    public float getCostBetween(LocalDate start, LocalDate end) {
        long numDays = numDaysBetween(start, end);
        if (numDays > 0) {
            return numDays * dailyCost;
        }
        return 0;
    }

    public float getConsumptionBetween(LocalDate start, LocalDate end) {
        long numDays = numDaysBetween(start, end);
        if (numDays > 0) {
            return numDays * dailyConsumption;
        }
        return 0;
    }

    private long numDaysBetween(LocalDate start, LocalDate end) {
        LocalDate sd = startDate.isAfter(start) ? startDate : start;
        LocalDate ed = endDate.isBefore(endDate) ? endDate : end;
        return ChronoUnit.DAYS.between(sd, ed);
    }

    public float getDailyConsumption() {
        return dailyConsumption;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Invoice clone() {
        return new Invoice(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Company Name :: ").append(companyName).append("\n");
        sb.append("Daily Consumption :: ").append(getDailyConsumption()).append("\n");
        sb.append("Daily Cost :: ").append(getCost()).append("\n");
        sb.append("Start Date :: ").append(startDate).append("\n");
        sb.append("End Date :: ").append(endDate).append("\n");
        return sb.toString();
    }
}
