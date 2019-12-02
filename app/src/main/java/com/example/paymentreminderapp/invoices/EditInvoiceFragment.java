package com.example.paymentreminderapp.invoices;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.paymentreminderapp.R;
import com.example.paymentreminderapp.model.Invoice;
import com.example.paymentreminderapp.repository.InvoiceRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditInvoiceFragment extends Fragment {

    @BindView(R.id.spinner_is_paid) Spinner spinnerIsPaid;
    @BindView(R.id.spinner_is_confirmed) Spinner spinnerIsConfirmed;
    @BindView(R.id.deadlineDate) EditText editTextDeadlineDate;
    @BindView(R.id.editMoney) EditText editTextMoney;
    @BindView(R.id.editAccountNumber) EditText editTextAccountNumber;
    @BindView(R.id.editReceiverName) EditText editTextReceiverName;
    @BindView(R.id.saveEdition) Button btnSave;

    private Invoice invoice;
    private InvoiceRepository invoiceRepository;

    private final Calendar myCalendar = Calendar.getInstance();
    String myFormat = "dd/MM/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    private String chosenDate;
    private double money;
    private boolean isDataCorrect = true;
    private String errorMessage = "";

    private static final String ERROR_MESSAGE_MONEY = "Niepoprawna kwota! ";
    private static final String ERROR_MESSAGE_NUMBER = "Niepoprawny numer konta!";
    private String accountNumber;
    private String receiverName;
    private boolean isPaid;
    private boolean isConfirmed;



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
        View view = inflater.inflate(R.layout.fragment_edit_invoice, container, false);
        ButterKnife.bind(this, view);

        if(getArguments() != null) {
            invoice = (Invoice) getArguments().getSerializable("invoiceToEdit");
        }

        System.out.println("MONEY W EDIT " + invoice.getMoney());

        invoiceRepository = new InvoiceRepository();


        ArrayAdapter<CharSequence> staticIsPaidAdapter = ArrayAdapter.createFromResource(getContext(), R.array.yes_no_array,
                android.R.layout.simple_spinner_item);

        staticIsPaidAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIsPaid.setAdapter(staticIsPaidAdapter);

        spinnerIsPaid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                System.out.println("item " + parent.getItemAtPosition(position));

                if(parent.getItemAtPosition(position).equals("Tak")) {
                    isPaid = true;
                }else if(parent.getItemAtPosition(position).equals("Nie")) {
                    isPaid = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        //-----------------------------------------------------------------------------

        ArrayAdapter<CharSequence> staticIsConfirmedAdapter =  ArrayAdapter.createFromResource(getContext(), R.array.yes_no_array,
                android.R.layout.simple_spinner_item);

        staticIsConfirmedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIsConfirmed.setAdapter(staticIsConfirmedAdapter);

        spinnerIsConfirmed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                System.out.println("item " + parent.getItemAtPosition(position));

                if(parent.getItemAtPosition(position).equals("Tak")) {
                    isConfirmed = true;
                } else if(parent.getItemAtPosition(position).equals("Nie")) {
                    isConfirmed = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        //-------------------------------------------------------------------------------

        chosenDate = sdf.format(new Date());
        editTextDeadlineDate.setText(chosenDate);

        DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        editTextDeadlineDate.setOnClickListener(v -> new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        //------------------------------------------------------------------------------

        editTextAccountNumber.setText(invoice.getReceiverAccountNumber());
        editTextMoney.setText(invoice.getMoney().toString());
        editTextReceiverName.setText(invoice.getReceiverCompanyName());





        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkingMoney();
                checkingAccountNumber();

                if(isDataCorrect) {
                    update();
                }
                else {
                    Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                    errorMessage = "";
                    isDataCorrect = true;
                }
            }
        });



        return view;
    }

    private void update() {
        receiverName = editTextReceiverName.getText().toString();

        invoiceRepository.updateDeadlineDate(chosenDate, invoice.getUuid());
        invoiceRepository.updateMoney(money, invoice.getUuid());
        invoiceRepository.updatereceiverAccountNumber(accountNumber, invoice.getUuid());
        invoiceRepository.updatereceiverCompanyName(receiverName, invoice.getUuid());
        invoiceRepository.updatePaidStatus(isPaid, invoice.getUuid());
        invoiceRepository.updateConfirmedByUserStatus(isConfirmed, invoice.getUuid());

        //update
    }

    private void checkingMoney() {
        try {
            money = Double.parseDouble(editTextMoney.getText().toString());
            System.out.println("money CHECK " + money);
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
            isDataCorrect = false;
            errorMessage = ERROR_MESSAGE_MONEY;
        }
    }

    private void checkingAccountNumber() {
        try {
            accountNumber = editTextAccountNumber.getText().toString();
            System.out.println("numer konta CHECK " + money);
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
            isDataCorrect = false;
            errorMessage = ERROR_MESSAGE_NUMBER;
        }
    }

    private void updateLabel() {
        chosenDate = sdf.format(myCalendar.getTime());
        editTextDeadlineDate.setText(chosenDate);
    }
}
