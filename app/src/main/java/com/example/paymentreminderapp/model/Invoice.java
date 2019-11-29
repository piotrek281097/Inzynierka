package com.example.paymentreminderapp.model;

import java.io.Serializable;

public class Invoice implements Serializable {
    private String uuid;
    private String username;
    private Double money;
    private String currency;
    private String deadlineDate;
    private String receiverAccountNumber;
    private boolean isPaid;
    private boolean isConfirmedByUser;
    private boolean isDeleted;
    private String receiverCompanyName;
    private String referenceToPDF;
    private String invoiceSavedTimestamp;
    private String addedBy;
    private String invoiceSaveDate;

    public Invoice() {

    }

    public Invoice(String uuid, String username, Double money, String currency, String deadlineDate, String receiverAccountNumber, boolean isPaid, boolean isConfirmedByUser, boolean isDeleted, String receiverCompanyName, String referenceToPDF, String invoiceSavedTimestamp, String addedBy, String invoiceSaveDate) {
        this.uuid = uuid;
        this.username = username;
        this.money = money;
        this.currency = currency;
        this.deadlineDate = deadlineDate;
        this.receiverAccountNumber = receiverAccountNumber;
        this.isPaid = isPaid;
        this.isConfirmedByUser = isConfirmedByUser;
        this.isDeleted = isDeleted;
        this.receiverCompanyName = receiverCompanyName;
        this.referenceToPDF = referenceToPDF;
        this.invoiceSavedTimestamp = invoiceSavedTimestamp;
        this.addedBy = addedBy;
        this.invoiceSaveDate = invoiceSaveDate;
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

    public String getReceiverCompanyName() {
        return receiverCompanyName;
    }

    public void setReceiverCompanyName(String receiverCompanyName) {
        this.receiverCompanyName = receiverCompanyName;
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

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getInvoiceSaveDate() {
        return invoiceSaveDate;
    }

    public void setInvoiceSaveDate(String invoiceSaveDate) {
        this.invoiceSaveDate = invoiceSaveDate;
    }
}