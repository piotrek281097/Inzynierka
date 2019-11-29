package com.example.paymentreminderapp.invoices;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.paymentreminderapp.R;
import com.example.paymentreminderapp.model.Invoice;
import com.example.paymentreminderapp.repository.InvoiceRepository;
import com.example.paymentreminderapp.vievmodels.InvoiceViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InvoiceDetailsFragment extends Fragment {

    @BindView(R.id.textViewDeadline) TextView textViewDeadline;
    @BindView(R.id.textViewIsPaid) TextView textViewIsPaid;
    @BindView(R.id.textViewMoney) TextView textViewMoney;
    @BindView(R.id.imageViewIsPaid) ImageView imageViewIsPaid;
    @BindView(R.id.btnChangeStatus) Button btnChangeStatus;
    @BindView(R.id.textAccountReceiverNumber) TextView textAccountReceiverNumber;
    @BindView(R.id.textCompanyName) TextView textCompanyName;
    @BindView(R.id.imageViewIsConfirmed) ImageView imageViewIsConfirmed;
    @BindView(R.id.textAddByWho) TextView textAddByWho;
    @BindView(R.id.textAddedWhen) TextView textInvoiceSaveDate;
    @BindView(R.id.btnShowInvoicePDF) Button btnShowInvoicePDF;
    @BindView(R.id.btnConfirmInvoice) Button btnConfirmInvoice;
    @BindView(R.id.btnPay) Button btnPay;
    @BindView(R.id.buttonEditInvoice) Button buttonEditInvoice;


    private static final String COLLECTION_NAME = "Invoices";
    private static final String INVOICE = "Invoice_";

    Invoice invoice;

    InvoiceRepository invoiceRepository;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = "ERROR_GET_INVOICE";


    private InvoiceViewModel invoiceViewModel;
    private MutableLiveData<Invoice> mMutableLiveData;


    public static InvoiceDetailsFragment newInstance(Invoice invoice) {
        InvoiceDetailsFragment fragment = new InvoiceDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("invoiceObject", invoice);
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

//        setData(uuid, view);
        setData(invoice, view);

        System.out.println("Invoice MONEY in DETAILS " + invoice.getMoney());

        btnChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invoiceRepository.updatePaidStatus(!invoice.isPaid(), invoice.getUuid());
                invoice.setPaid(!invoice.isPaid());
                setIsPaid(invoice.getUuid(), view);
            }
        });

        btnConfirmInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invoiceRepository.updateConfirmedByUserStatus(!invoice.isConfirmedByUser(), invoice.getUuid());
                invoice.setConfirmedByUser(!invoice.isConfirmedByUser());
                setIsConfirmed(invoice.getUuid(), view);

                if(invoice.isConfirmedByUser()) {
                    btnConfirmInvoice.setText("Anuluj potwierdzenie");
                }
                else {
                    btnConfirmInvoice.setText("Potwierdź dane");
                }
            }
        });

        buttonEditInvoice.setOnClickListener(v -> Objects.requireNonNull(getActivity())
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new EditInvoiceFragment())
                .addToBackStack(null)
                .commit());

        return view;
    }

    private void setData(Invoice invoice, View view) {
        String deadline = "Termin zapłaty: " + invoice.getDeadlineDate();
        textViewDeadline.setText(deadline);

        String money = "Kwota: " + invoice.getMoney()
                + " " + invoice.getCurrency();
        textViewMoney.setText(money);

        boolean isPaid = invoice.isPaid();
        if(isPaid) {
            Glide.with(view).load(R.drawable.ic_done_black_24dp).into(imageViewIsPaid);
        }
        else {
            Glide.with(view).load(R.drawable.ic_close_black_24dp).into(imageViewIsPaid);
        }

        String companyName = "Odbiorca: " + invoice.getReceiverCompanyName();
        textCompanyName.setText(companyName);

        String accountReceiverNumber = "Numer konta: " + invoice.getReceiverAccountNumber();
        textAccountReceiverNumber.setText(accountReceiverNumber);

        boolean isConfirmed = invoice.isConfirmedByUser();
        if(isConfirmed) {
            Glide.with(view).load(R.drawable.ic_done_black_24dp).into(imageViewIsConfirmed);
        }
        else {
            Glide.with(view).load(R.drawable.ic_close_black_24dp).into(imageViewIsConfirmed);
        }

        String addedBy = "Dodane: " + invoice.getAddedBy();
        textAddByWho.setText(addedBy);

        String invoiceSaveDate = "Data zapisania: " + invoice.getInvoiceSaveDate();
        textInvoiceSaveDate.setText(invoiceSaveDate);
    }

    private void setIsPaid(String uuid, View view) {
        db.collection(COLLECTION_NAME).document(INVOICE + uuid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            boolean isPaid = documentSnapshot.getBoolean("paid");
                            if(isPaid) {
                                Glide.with(view).load(R.drawable.ic_done_black_24dp).into(imageViewIsPaid);
                            }
                            else {
                                Glide.with(view).load(R.drawable.ic_close_black_24dp).into(imageViewIsPaid);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    private void setIsConfirmed(String uuid, View view) {
        db.collection(COLLECTION_NAME).document(INVOICE + uuid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            boolean isConfirmed = documentSnapshot.getBoolean("confirmedByUser");
                            if(isConfirmed) {
                                Glide.with(view).load(R.drawable.ic_done_black_24dp).into(imageViewIsConfirmed);
                            }
                            else {
                                Glide.with(view).load(R.drawable.ic_close_black_24dp).into(imageViewIsConfirmed);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Nieznany błąd", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Nieznany błąd!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

/*
    private void setIsPaid(String uuid, View view) {
        db.collection(COLLECTION_NAME).document(INVOICE + uuid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            boolean isPaid = documentSnapshot.getBoolean("paid");
                            if(isPaid) {
                                Glide.with(view).load(R.drawable.ic_done_black_24dp).into(imageViewIsPaid);
                            }
                            else {
                                Glide.with(view).load(R.drawable.ic_close_black_24dp).into(imageViewIsPaid);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

    private void setData(String uuid, View view) {
        db.collection(COLLECTION_NAME).document(INVOICE + uuid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            setFields(documentSnapshot, view);
                        } else {
                            Toast.makeText(getActivity(), "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }


                    private void setFields(DocumentSnapshot documentSnapshot, View view) {
                        String deadline = "Termin zapłaty: " + documentSnapshot.getString("deadlineDate");
                        textViewDeadline.setText(deadline);

                        String money = "Kwota: " + documentSnapshot.getDouble("money")
                                + " " + documentSnapshot.getString("currency");
                        textViewMoney.setText(money);

                        boolean isPaid = documentSnapshot.getBoolean("paid");
                        if(isPaid) {
                            Glide.with(view).load(R.drawable.ic_done_black_24dp).into(imageViewIsPaid);
                        }
                        else {
                            Glide.with(view).load(R.drawable.ic_close_black_24dp).into(imageViewIsPaid);
                        }

                        String companyName = "Odbiorca: " + documentSnapshot.getString("receiverCompanyName");
                        textCompanyName.setText(companyName);

                        String accountReceiverNumber = "Numer konta: " + documentSnapshot.getString("receiverAccountNumber");
                        textAccountReceiverNumber.setText(accountReceiverNumber);

                        boolean isConfirmed = documentSnapshot.getBoolean("confirmedByUser");
                        if(isConfirmed) {
                            Glide.with(view).load(R.drawable.ic_done_black_24dp).into(imageViewIsConfirmed);
                        }
                        else {
                            Glide.with(view).load(R.drawable.ic_close_black_24dp).into(imageViewIsConfirmed);
                        }

                        String addedBy = "Dodane: " + documentSnapshot.getString("addedBy");
                        textAddByWho.setText(addedBy);

                        String invoiceSaveDate = "Data zapisania: " + documentSnapshot.getString("invoiceSaveDate");
                        textInvoiceSaveDate.setText(invoiceSaveDate);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
*/

}
