package com.example.paymentreminderapp.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.paymentreminderapp.model.Invoice;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

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

    public void updatePaidStatus(boolean isPaid, String idInvoice) {
        db.collection(COLLECTION_NAME).document(idInvoice).update("paid", isPaid);
    }

    public Query getAllInvoices(String username) {
        return db.collection("Invoices")
                .whereEqualTo("username", username);
//                .orderBy("deadLine", Query.Direction.DESCENDING);
    }

    public Query getPaidInvoices(String username ) {
        return db.collection("Invoices")
                .whereEqualTo("username", username)
                .whereEqualTo("paid", true);
    }

    public Query getUnpaidInvoices(String username) {
        return db.collection("Invoices")
                .whereEqualTo("username", username)
                .whereEqualTo("paid", false);
    }
}
