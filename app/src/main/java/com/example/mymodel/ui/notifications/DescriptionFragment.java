package com.example.mymodel.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mymodel.R;
import com.example.mymodel.databinding.FragmentDescriptionBinding;
import com.example.mymodel.models.ModelM;

import java.util.ArrayList;

public class DescriptionFragment extends Fragment {
    FragmentDescriptionBinding binding;
    NavController navController;
    DescAdapter adapter;
    ArrayList<ModelM> d_list=  new ArrayList<>();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DescriptionViewModel notificationsViewModel =
                new ViewModelProvider(this).get(DescriptionViewModel.class);

        binding = FragmentDescriptionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
if(getArguments() !=null){
    d_list = getArguments().getParcelableArrayList("see more");
    adapter = new DescAdapter(requireActivity(),d_list);
    binding.rvDescription.setAdapter(adapter);
    Log.e("TAG","DATA GETTING !!!");


}else {
    Toast.makeText(requireActivity(), "There are nothing", Toast.LENGTH_SHORT).show();}
return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnBack.setOnClickListener(v->{navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_navigation_description_to_navigation_home);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}