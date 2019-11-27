package com.example.paymentreminderapp.invoices;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.paymentreminderapp.R;
import com.example.paymentreminderapp.helpers.Utils;
import com.example.paymentreminderapp.model.Invoice;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.common.images.ImageManager;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InvoiceAdapter extends FirestoreRecyclerAdapter<Invoice, InvoiceAdapter.InvoiceHolder> {

    public InvoiceAdapter(@NonNull FirestoreRecyclerOptions<Invoice> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull InvoiceHolder holder, int position, @NonNull Invoice model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public InvoiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_item,
                parent, false);
        return new InvoiceHolder(v);
    }

    class InvoiceHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewDeadline) TextView textViewDeadline;
        @BindView(R.id.textViewIsPaid) TextView textViewIsPaid;
        @BindView(R.id.textViewMoney) TextView textViewMoney;
        @BindView(R.id.imageViewIsPaid) ImageView imageViewIsPaid;
        @BindView(R.id.btnChangeStatus) Button btnChangeStatus;

        public InvoiceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                System.out.println("CARD____CLICKED");
            });

/*
            itemView.setOnClickListener(v -> (
                    (AppCompatActivity)v.getContext())
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                            InvoiceDetailsFragment.newInstance(invoices.get(getAdapterPosition())))
                    .addToBackStack(null)
                    .commit());
*/
            btnChangeStatus.setOnClickListener(v -> {
                System.out.println("BUTTON_CHANGE_STATUS_CLICKED");
            });

        }

        public void bind(Invoice model) {
            String textDeadline = "Termin zapłaty: " + model.getDeadlineDate();
            textViewDeadline.setText(textDeadline);
            String textMoney = "Kwota: " + model.getMoney() + " " + model.getCurrency();
            textViewMoney.setText(textMoney);

            if(model.isPaid()) {
                Glide.with(itemView).load(R.drawable.ic_done_black_24dp).into(imageViewIsPaid);
            }
            else {
                Glide.with(itemView).load(R.drawable.ic_close_black_24dp).into(imageViewIsPaid);
            }

        }
    }
}

/*
    private List<Invoice> invoices = new ArrayList<>();

    @NonNull
    @Override
    public InvoiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invoice_item, parent, false);
        return new InvoiceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceHolder holder, int position) {
       holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
        notifyDataSetChanged();
    }

    class InvoiceHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewDeadline) TextView textViewDeadline;
        @BindView(R.id.textViewIsPaid) TextView textViewIsPaid;
        @BindView(R.id.textViewMoney) TextView textViewMoney;
        @BindView(R.id.imageViewIsPaid) ImageView imageViewIsPaid;
        @BindView(R.id.btnChangeStatus) Button btnChangeStatus;

        public InvoiceHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                System.out.println("CARD____CLICKED");
            });


            itemView.setOnClickListener(v -> (
                    (AppCompatActivity)v.getContext())
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                            InvoiceDetailsFragment.newInstance(invoices.get(getAdapterPosition())))
                    .addToBackStack(null)
                    .commit());

            btnChangeStatus.setOnClickListener(v -> {
                System.out.println("BUTTON_CHANGE_STATUS_CLICKED");
            });
        }

        public void bind(int position) {
            Invoice invoice = invoices.get(position);
            String textDeadline = "Termin zapłaty: " + invoice.getDeadlineDate();
            textViewDeadline.setText(textDeadline);
            String textMoney = "Kwota: " + invoice.getMoney() + " " + invoice.getCurrency();
            textViewMoney.setText(textMoney);

        }
    }

}*/