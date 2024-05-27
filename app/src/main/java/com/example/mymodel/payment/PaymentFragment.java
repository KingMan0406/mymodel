package com.example.mymodel.payment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mymodel.R;
import com.example.mymodel.databinding.FragmentPaymentBinding;

public class PaymentFragment extends Fragment {

    FragmentPaymentBinding binding;
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cardVisa.setOnClickListener(v -> {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://visa.com/"));
            startActivity(myIntent);
        });

        binding.cardPaypal.setOnClickListener(v1 -> {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://paypal.com/"));
            startActivity(myIntent);
        });

        binding.cardMBank.setOnClickListener(v2 -> {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mbank.kg/"));
            startActivity(myIntent);
        });

        binding.cardODengi.setOnClickListener(v3 -> {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/derails?id=kg.o.nurtelecom"));
            startActivity(myIntent);
        });

        binding.btnBack.setOnClickListener(v4 -> {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_navigation_payment_to_navigation_home);
        });

        binding.btnSubmit.setOnClickListener(v -> {
            // Here you can collect the data from the EditText fields if needed
            String name = binding.etName.getText().toString();
            String address = binding.etAddress.getText().toString();
            String phone = binding.etPhone.getText().toString();

            // Navigate to the confirmation fragment
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_navigation_payment_to_navigation_confirmation);
        });
    }

}

