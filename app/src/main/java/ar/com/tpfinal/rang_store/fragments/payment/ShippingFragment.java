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
            if(checkEmptyFields()) return;
            navHost.navigate(R.id.action_shippingFragment_to_purchaseDataFragment,getArguments());
        });

    }

    private boolean checkEmptyFields(){

        boolean flag = false;

        if(binding.countryEditText.getText().toString().isEmpty()){
            binding.countryEditText.setError("Pais obligatorio");
            binding.countryEditText.requestFocus();

            flag = true;
        }

        if(binding.provinceEditText.getText().toString().isEmpty()){
            binding.provinceEditText.setError("Provincia obligatoria");
            binding.provinceEditText.requestFocus();

            flag = true;
        }

        if(binding.streetEditText.getText().toString().isEmpty()){
            binding.streetEditText.setError("Calle obligatoria");
            binding.streetEditText.requestFocus();

            flag = true;
        }
        if(binding.streetNumberEditText.getText().toString().isEmpty()) {
            binding.streetNumberEditText.setError("Numero de calle obligatorio");
            binding.streetNumberEditText.requestFocus();

            flag = true;
        }
        if(binding.phoneNumberEditText.getText().toString().isEmpty()) {
            binding.phoneNumberEditText.setError("Telefono obligatorio");
            binding.phoneNumberEditText.requestFocus();

            flag = true;
        }

        return flag;
    }
}