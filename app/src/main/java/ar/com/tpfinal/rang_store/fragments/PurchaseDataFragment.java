package ar.com.tpfinal.rang_store.fragments;

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
import ar.com.tpfinal.rang_store.databinding.FragmentPurchaseDataBinding;

public class PurchaseDataFragment extends Fragment {

    private FragmentPurchaseDataBinding binding;
    private NavController navHost;

    public PurchaseDataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPurchaseDataBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navHost = NavHostFragment.findNavController(this);

        binding.radioButtonCredit.setOnClickListener(view1 -> showFields());
        binding.radioButtonDebit.setOnClickListener(view1 -> showFields());
        binding.radioButtonTransfer.setOnClickListener(view1 -> showFields());
        binding.buyButton.setOnClickListener(view1 -> {
            if(binding.radioButtonCredit.isChecked() || binding.radioButtonDebit.isChecked()){
                //TODO NAVEGAR A FACTURA?
            }
            else if(binding.radioButtonTransfer.isChecked()){
                navHost.navigate(R.id.action_purchaseDataFragment_to_transferFragment);
            }
        });

    }

    private void showFields(){
        if(binding.radioButtonCredit.isChecked() || binding.radioButtonDebit.isChecked()){
            binding.textViewDepositTitle.setVisibility(View.GONE);

            binding.textViewCardNumber.setVisibility(View.VISIBLE);
            binding.editTextCardNumber.setVisibility(View.VISIBLE);
            binding.textViewExpirationDate.setVisibility(View.VISIBLE);
            binding.editTextExpirationDate.setVisibility(View.VISIBLE);
            binding.textViewSecurityCode.setVisibility(View.VISIBLE);
            binding.editTextSecurityCode.setVisibility(View.VISIBLE);
            binding.textViewCardName.setVisibility(View.VISIBLE);
            binding.editTextCardName.setVisibility(View.VISIBLE);
            binding.buyButton.setVisibility(View.VISIBLE);
        }
        else{
            binding.textViewCardNumber.setVisibility(View.GONE);
            binding.editTextCardNumber.setVisibility(View.GONE);
            binding.textViewExpirationDate.setVisibility(View.GONE);
            binding.editTextExpirationDate.setVisibility(View.GONE);
            binding.textViewSecurityCode.setVisibility(View.GONE);
            binding.editTextSecurityCode.setVisibility(View.GONE);
            binding.textViewCardName.setVisibility(View.GONE);
            binding.editTextCardName.setVisibility(View.GONE);

            binding.textViewDepositTitle.setVisibility(View.VISIBLE);
            binding.buyButton.setVisibility(View.VISIBLE);

        }
    }
}
