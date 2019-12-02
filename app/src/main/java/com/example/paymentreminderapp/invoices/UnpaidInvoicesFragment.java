package com.example.paymentreminderapp.invoices;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paymentreminderapp.R;
import com.example.paymentreminderapp.model.Invoice;
import com.example.paymentreminderapp.repository.InvoiceRepository;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnpaidInvoicesFragment extends Fragment {

    @BindView(R.id.recycler_view_invoices_unpaid)
    RecyclerView recyclerView;

    private InvoiceAdapter adapter;
    private InvoiceRepository invoiceRepository;
    private String username;

    public static UnpaidInvoicesFragment newInstance(String username) {
        UnpaidInvoicesFragment fragment = new UnpaidInvoicesFragment();
        Bundle args = new Bundle();
        args.putString("username", username);

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unpaid_invoices, container, false);
        ButterKnife.bind(this, view);

        if(getArguments() != null) {
            username = getArguments().getString("username");
        }

        invoiceRepository = new InvoiceRepository();

        setUpRecyclerView(view);

        return view;
    }

    private void setUpRecyclerView(View view) {
        Query query = invoiceRepository.getUnpaidInvoices(username);

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
