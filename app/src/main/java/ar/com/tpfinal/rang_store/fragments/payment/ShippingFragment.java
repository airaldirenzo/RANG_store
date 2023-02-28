package ar.com.tpfinal.rang_store.fragments.payment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.databinding.FragmentShippingBinding;

public class ShippingFragment extends Fragment {

    private FragmentShippingBinding binding;
    private NavController navHost;

    public ShippingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.setArguments(requireActivity().getIntent().getExtras());
        binding = FragmentShippingBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navHost = NavHostFragment.findNavController(this);

        binding.shippingContinueButton.setOnClickListener(view1 -> {
            navHost.navigate(R.id.action_shippingFragment_to_purchaseDataFragment,getArguments());
        });



    }
}