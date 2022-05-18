package model.energy_suppliers;

import model.energy_suppliers.energy_plans.BasePlan;
import model.energy_suppliers.energy_plans.EnergyPlan;
import utils.Pair;

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

    public List<Invoice> getInvoices() {
        return this.invoicesByProprietaryTin.values().stream()
            .flatMap(list -> list.stream().map(Invoice::clone))
            .collect(Collectors.toList());
    }

    public double invoiceVolume() {
        return this.invoicesByProprietaryTin
            .values()
            .stream()
            .flatMap(Collection::stream)
            .mapToDouble(Invoice::getCost)
            .sum();
    }

    public double invoiceVolumeBetween(LocalDate startDate, LocalDate endDate) {
        double amount = 0;
        for (List<Invoice> proprietaryInvoices : this.invoicesByProprietaryTin.values()) {
            for (Invoice invoice : proprietaryInvoices) {
                if (invoice.getStartDate().isAfter(endDate)) {
                    break;
                }
                amount += invoice.getCostBetween(startDate, endDate);
            }
        }
        return amount;
    }

    public Optional<Pair<String, Double>> mostCostlyHouseBetween(LocalDate startDate, LocalDate endDate) {
        double maxMoney = -1;
        String proprietaryTin = "";
        for (Map.Entry<String, List<Invoice>> proprietaryInvoices : this.invoicesByProprietaryTin.entrySet()) {
            double money = 0;
            for (Invoice invoice : proprietaryInvoices.getValue()) {
                if (invoice.getStartDate().isAfter(endDate)) {
                    break;
                }
                money += invoice.getCostBetween(startDate, endDate);
            }
            if (maxMoney < money) {
                maxMoney = money;
                proprietaryTin = proprietaryInvoices.getKey();
            }
        }
        if (maxMoney == -1)
            return Optional.empty();
        return Optional.of(new Pair<>(proprietaryTin, maxMoney));
    }

    public Map<String, Double> proprietariesEnergyConsumptionBetween(LocalDate startDate, LocalDate endDate) {
        Map<String, Double> res = new HashMap<>();
        for (Map.Entry<String, List<Invoice>> proprietaryInvoices : this.invoicesByProprietaryTin.entrySet()) {
            double consumption = 0;
            for (Invoice invoice : proprietaryInvoices.getValue()) {
                if (invoice.getStartDate().isAfter(endDate)) {
                    break;
                }
                consumption += invoice.getConsumptionBetween(startDate, endDate);
            }
            res.put(proprietaryInvoices.getKey(), consumption);
        }
        return res;
    }
}
