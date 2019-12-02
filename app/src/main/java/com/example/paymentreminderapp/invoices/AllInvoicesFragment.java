package com.example.paymentreminderapp.invoices;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.paymentreminderapp.R;
import com.example.paymentreminderapp.model.Invoice;
import com.example.paymentreminderapp.repository.InvoiceRepository;
import com.example.paymentreminderapp.vievmodels.InvoiceViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllInvoicesFragment extends Fragment {

    @BindView(R.id.recycler_view_invoices) RecyclerView recyclerView;
    @BindView(R.id.buttonAddInvoice) FloatingActionButton buttonAddInvoice;


    private InvoiceAdapter adapter;
    private InvoiceRepository invoiceRepository;
    private String username;

    public static AllInvoicesFragment newInstance(String username) {
        AllInvoicesFragment fragment = new AllInvoicesFragment();
        Bundle args = new Bundle();
        args.putString("username", username);

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_invoices, container, false);
        ButterKnife.bind(this, view);

        if(getArguments() != null) {
            username = getArguments().getString("username");
        }

        invoiceRepository = new InvoiceRepository();

        setUpRecyclerView(view);

        buttonAddInvoice.setOnClickListener(v -> Objects.requireNonNull(getActivity())
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new AddInvoiceFragment())
                .addToBackStack(null)
                .commit());

        return view;
    }

    private void setUpRecyclerView(View view) {
        Query query = invoiceRepository.getAllInvoices(username);

        FirestoreRecyclerOptions<Invoice> options = new FirestoreRecyclerOptions.Builder<Invoice>()
                .setQuery(query, Invoice.class)
                .build();

        adapter = new InvoiceAdapter(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new InvoiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Invoice invoice = documentSnapshot.toObject(Invoice.class);

                Fragment fragment = InvoiceDetailsFragment.newInstance(invoice);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
    /*
    @BindView(R.id.recycler_view_invoices) RecyclerView recyclerView;

    private InvoiceViewModel invoiceViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_invoices, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);

        final InvoiceAdapter adapter = new InvoiceAdapter();
        recyclerView.setAdapter(adapter);

        invoiceViewModel = ViewModelProviders.of(this).get(InvoiceViewModel.class);
        invoiceViewModel.getAllInvoices().observe(this, new Observer<List<Invoice>>() {
            @Override
            public void onChanged(@Nullable List<Invoice> invoices) {
                adapter.setInvoices(invoices);
                //update RecyclerView
//                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });


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
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);

        adapter.setInvoices(invoices);

        return view;
    }


}

     */
