package com.example.mymodel.ui.dashboard;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mymodel.R;

public class OrderDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_dialog, container, false);

        EditText nameEditText = view.findViewById(R.id.et_name);
        EditText addressEditText = view.findViewById(R.id.et_address);
        EditText phoneEditText = view.findViewById(R.id.et_phone);
        Button submitButton = view.findViewById(R.id.btn_submit);

        submitButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String address = addressEditText.getText().toString();
            String phone = phoneEditText.getText().toString();

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(requireActivity(), "Заполни нужно бланки!", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(requireActivity(), "Successfully!", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        return view;
    }
}
