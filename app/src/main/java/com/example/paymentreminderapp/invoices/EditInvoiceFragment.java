package com.example.paymentreminderapp.invoices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.paymentreminderapp.R;
import com.example.paymentreminderapp.model.Invoice;
import com.example.paymentreminderapp.repository.InvoiceRepository;

import butterknife.ButterKnife;

public class EditInvoiceFragment extends Fragment {

    private Invoice invoice;
    private InvoiceRepository invoiceRepository;

    public static EditInvoiceFragment newInstance(Invoice invoice) {
        EditInvoiceFragment fragment = new EditInvoiceFragment();
        Bundle args = new Bundle();
        args.putSerializable("invoiceToEdit", invoice);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frament_invoice_details, container, false);
        ButterKnife.bind(this, view);

        if(getArguments() != null) {
            invoice = (Invoice) getArguments().getSerializable("invoiceObject");
        }

        invoiceRepository = new InvoiceRepository();


        return view;
    }
}
