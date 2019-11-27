package com.example.paymentreminderapp.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.paymentreminderapp.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

public class ChangePaymentStatusDialog extends DialogFragment {

    private AlertDialogListener listener;

    public interface AlertDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Objects.requireNonNull(getActivity()));
        builder.setMessage(R.string.ensure_change_payment_status)
                .setPositiveButton(R.string.confirm, (dialog, id) -> listener.onDialogPositiveClick(this))
                .setNegativeButton(R.string.cancel, (dialog, id) -> listener.onDialogNegativeClick(this));
        return builder.create();
    }


    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (AlertDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

}

