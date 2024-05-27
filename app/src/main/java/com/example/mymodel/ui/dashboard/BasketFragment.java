package com.example.mymodel.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mymodel.R;
import com.example.mymodel.databinding.FragmentBasketBinding;
import com.example.mymodel.models.ModelM;
import com.example.mymodel.ui.home.JemAdapter;

import java.util.ArrayList;

public class BasketFragment extends Fragment implements JemAdapter.OnQuantityChangeListener {

    private FragmentBasketBinding binding;
    private ArrayList<ModelM> basket_products;
    private JemAdapter adapter;
    private NavController navController;
    private Double total_sum = 0.0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentBasketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        if (getArguments() != null) {
            basket_products = getArguments().getParcelableArrayList("keysss_basket");
        }

        if (basket_products != null && !basket_products.isEmpty()) {
            binding.placeHolder.setVisibility(View.GONE);
            adapter = new JemAdapter(requireActivity(), basket_products, this,true);
            binding.rvBasket.setAdapter(adapter);
            calculateTotalPrice();
        } else {
            binding.placeHolder.setVisibility(View.VISIBLE);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBack.setOnClickListener(v -> {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_navigation_basket_to_navigation_home);
        });

        binding.btnOrder.setOnClickListener(v -> showOrderDialog());
    }

    private void calculateTotalPrice() {
        total_sum = 0.0;
        for (ModelM product : basket_products) {
            total_sum += product.getModelPrice() * product.getQuantity();
        }
        binding.basketTotalCount.setText(String.format("%.2f", total_sum));
    }

    private void showOrderDialog() {
        OrderDialogFragment orderDialogFragment = new OrderDialogFragment();
        orderDialogFragment.setTargetFragment(this, 0);
        orderDialogFragment.show(getFragmentManager(), "OrderDialogFragment");
    }

    @Override
    public void onQuantityChanged() {
        calculateTotalPrice();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

