package com.leorodriguezs.conversordemonedas.modelos;

public class Transaction {
    private String localCurrency;
    private String destinationCurrency;
    private double originalAmount;
    private double convertedAmount;

    public Transaction(String localCurrency, String destinationCurrency, double originalAmount, double convertedAmount) {
        this.localCurrency = localCurrency;
        this.destinationCurrency = destinationCurrency;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
    }

    public String getLocalCurrency() {
        return localCurrency;
    }

    public void setLocalCurrency(String localCurrency) {
        this.localCurrency = localCurrency;
    }

    public String getDestinationCurrency() {
        return destinationCurrency;
    }

    public void setDestinationCurrency(String destinationCurrency) {
        this.destinationCurrency = destinationCurrency;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }
}
