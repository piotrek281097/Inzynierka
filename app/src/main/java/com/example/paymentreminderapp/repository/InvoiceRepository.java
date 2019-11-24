package com.example.paymentreminderapp.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.paymentreminderapp.model.Invoice;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class InvoiceRepository {

    private static final String COLLECTION_NAME = "Invoices";
    private final String TAG = "FIREBASE_REPOSITORY";
    private static final String INVOICE = "Invoice_";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void save(Invoice invoice) {
        db.collection(COLLECTION_NAME).document(INVOICE + invoice.getUuid()).set(invoice)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Invoice saved");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, e.toString());
                    }
                });
    }



}
