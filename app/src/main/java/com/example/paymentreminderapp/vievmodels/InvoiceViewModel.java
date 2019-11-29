package com.example.paymentreminderapp.vievmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.paymentreminderapp.model.Invoice;
import com.example.paymentreminderapp.repository.InvoiceRepository;

import java.util.List;

public class InvoiceViewModel extends AndroidViewModel {
    private InvoiceRepository repository;
    private LiveData<Invoice> invoiceDetails;

    public InvoiceViewModel(@NonNull Application application) {
        super(application);
        repository = new InvoiceRepository();
//        invoiceDetails = repository.getInvoiceDetails();
    }

    public void insert(Invoice invoice) {
//        repository.insert(invoice);
    }

    public void update(Invoice invoice){
//        repository.update(invoice);
    }

    public void delete(Invoice invoice) {
//        repository.delete(invoice);
    }

    public void deleteAllNotes() {
//        repository.deleteAllInvoices();
    }

    public LiveData<Invoice> getInvoiceDetails() {
        return invoiceDetails;
    }
}
