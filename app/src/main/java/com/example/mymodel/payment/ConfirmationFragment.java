package com.example.mymodel.payment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mymodel.R;
import com.example.mymodel.databinding.FragmentConfirmationBinding;

public class ConfirmationFragment extends Fragment {

    FragmentConfirmationBinding binding;
    NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConfirmationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        if (getArguments() != null) {
            String name = getArguments().getString("name");
            String address = getArguments().getString("address");
            String phone = getArguments().getString("phone");
            String paymentMethod = getArguments().getString("payment_method");

            binding.nameTextView.setText("Name: " + name);
            binding.addressTextView.setText("Address: " + address);
            binding.phoneTextView.setText("Phone: " + phone);
            binding.paymentMethodTextView.setText("Payment Method: " + paymentMethod);
        }

        binding.confirmButton.setOnClickListener(v -> {
            // Логика подтверждения оплаты
        });

        binding.btnBack.setOnClickListener(v -> navController.navigateUp());
    }
}
