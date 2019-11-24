package com.example.paymentreminderapp.model;

import java.util.UUID;

public class Invoice {
    private String uuid;
    private String username;
    private Double money;
    private String currency;
    private String deadlineDate;
    private String receiverAccountNumber;
    private boolean isPaid;
    private boolean isConfirmedByUser;
    private boolean isDeleted;
    private String receiverComapnyName;
    private String referenceToPDF;
    private String invoiceSavedTimestamp;

    public Invoice(String uuid, String username, Double money, String currency, String deadlineDate, String receiverAccountNumber, boolean isPaid, boolean isConfirmedByUser, boolean isDeleted, String receiverComapnyName, String referenceToPDF, String invoiceSavedTimestamp) {
        this.uuid = uuid;
        this.username = username;
        this.money = money;
        this.currency = currency;
        this.deadlineDate = deadlineDate;
        this.receiverAccountNumber = receiverAccountNumber;
        this.isPaid = isPaid;
        this.isConfirmedByUser = isConfirmedByUser;
        this.isDeleted = isDeleted;
        this.receiverComapnyName = receiverComapnyName;
        this.referenceToPDF = referenceToPDF;
        this.invoiceSavedTimestamp = invoiceSavedTimestamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isConfirmedByUser() {
        return isConfirmedByUser;
    }

    public void setConfirmedByUser(boolean confirmedByUser) {
        isConfirmedByUser = confirmedByUser;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getReceiverComapnyName() {
        return receiverComapnyName;
    }

    public void setReceiverComapnyName(String receiverComapnyName) {
        this.receiverComapnyName = receiverComapnyName;
    }

    public String getReferenceToPDF() {
        return referenceToPDF;
    }

    public void setReferenceToPDF(String referenceToPDF) {
        this.referenceToPDF = referenceToPDF;
    }

    public String getInvoiceSavedTimestamp() {
        return invoiceSavedTimestamp;
    }

    public void setInvoiceSavedTimestamp(String invoiceSavedTimestamp) {
        this.invoiceSavedTimestamp = invoiceSavedTimestamp;
    }
}