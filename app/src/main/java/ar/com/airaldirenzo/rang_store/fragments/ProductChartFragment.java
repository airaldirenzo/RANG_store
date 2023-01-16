package ar.com.airaldirenzo.rang_store.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.airaldirenzo.rang_store.R;
import ar.com.airaldirenzo.rang_store.databinding.ProductChartBinding;

public class ProductChartFragment extends Fragment {

    private NavController navHost;
    private ProductChartBinding binding;

    public ProductChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ProductChartBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navHost = NavHostFragment.findNavController(this);


    }
}