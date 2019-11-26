package com.example.paymentreminderapp.invoices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paymentreminderapp.R;
import com.example.paymentreminderapp.model.Invoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllInvoicesFragment extends Fragment {

    @BindView(R.id.recycler_view_invoices) RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_invoices, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        final InvoiceAdapter adapter = new InvoiceAdapter();
        recyclerView.setAdapter(adapter);


        Invoice invoice1 = new Invoice(
                UUID.randomUUID().toString(),
                "user1",
                100.00,
                "ZŁ",
                new Date().toString(),
                "1234567890",
                false,
                false,
                false,
                "Orange1",
                "link",
                "2214356720192411"
        );

        Invoice invoice2 = new Invoice(
                UUID.randomUUID().toString(),
                "user2",
                200.00,
                "ZŁ",
                new Date().toString(),
                "2234567890",
                false,
                false,
                false,
                "Orange2",
                "link",
                "2214356720192411"
        );

        Invoice invoice3 = new Invoice(
                UUID.randomUUID().toString(),
                "user3",
                300.00,
                "ZŁ",
                new Date().toString(),
                "3234567890",
                false,
                false,
                false,
                "Orange3",
                "link",
                "2214356720192411"
        );

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);

        adapter.setInvoices(invoices);

        return view;
    }
}
