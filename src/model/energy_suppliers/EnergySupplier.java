package model.energy_suppliers;

import model.Pair;
import model.energy_suppliers.energy_plans.BasePlan;
import model.energy_suppliers.energy_plans.EnergyPlan;
import model.smart_house.Invoice;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EnergySupplier implements Serializable {
    private final String name;
    private EnergyPlan energyPlan;
    private final HashMap<String, List<Invoice>> invoicesByProprietaryTin;

    public EnergySupplier(String name) {
        this.name = name;
        this.energyPlan = new BasePlan();
        this.invoicesByProprietaryTin = new HashMap<>();
    }

    public String getName() {
        return this.name;
    }

    public float energyCost(int numDevices, float devicesConsumption) {
        return this.energyPlan.energyCost(numDevices, devicesConsumption);
    }

    public void addInvoice(Invoice invoice, String proprietaryTin) {
        List<Invoice> invoices = this.invoicesByProprietaryTin.computeIfAbsent(proprietaryTin, k -> new Stack<>());
        invoices.add(invoice.clone());
    }

    public double getAmountMoney() {
        return this.invoicesByProprietaryTin.values().stream()
                .mapToDouble(
                        e -> e.stream()
                                .mapToDouble(Invoice::getCost)
                                .sum()
                ).sum();
    }

    public double getAmountMoneyBetween(LocalDate startDate, LocalDate endDate) {
        double amount = 0;
        for (var proprietary : this.invoicesByProprietaryTin.entrySet()) {
            for (Invoice invoice : proprietary.getValue()) {
                LocalDate invoiceDate = invoice.getDate();
                if(invoiceDate.isBefore(startDate) || invoiceDate.isAfter(endDate))
                    break;
                amount += invoice.getCost();
            }
        }
        return amount;
    }

    public Set<Invoice> getAllInvoices() {
        return this.invoicesByProprietaryTin.values().stream()
                .flatMap(list -> list.stream().map(Invoice::clone))
                .collect(Collectors.toSet());
    }

    public Optional<Pair<String, Double>> mostExpensiveHouseBetween(LocalDate startDate, LocalDate endDate) {
        double maxMoney = -1;
        String proprietaryTin = "";
        for (var proprietary : this.invoicesByProprietaryTin.entrySet()) {
            double money = 0;
            for (Invoice invoice : proprietary.getValue()) {
                LocalDate invoiceDate = invoice.getDate();
                if(invoiceDate.isBefore(startDate) || invoiceDate.isAfter(endDate))
                    break;
                money += invoice.getCost();
            }
            if (maxMoney < money) {
                maxMoney = money;
                proprietaryTin = proprietary.getKey();
            }
        }
        if (maxMoney == -1)
            return Optional.empty();
        return Optional.of(new Pair<>(proprietaryTin, maxMoney));
    }
}
