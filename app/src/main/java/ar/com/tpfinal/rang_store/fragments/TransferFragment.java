package ar.com.tpfinal.rang_store.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ar.com.tpfinal.rang_store.MainActivity;
import ar.com.tpfinal.rang_store.data.datasource.firebase.Purchase;
import ar.com.tpfinal.rang_store.databinding.FragmentTransferBinding;
import ar.com.tpfinal.rang_store.model.ItemCart;


public class TransferFragment extends Fragment {

    private FragmentTransferBinding binding;

    public TransferFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTransferBinding.inflate(inflater,container,false);

        if (getArguments() != null) {
            List<ItemCart> purchase = getArguments().getParcelableArrayList("purchase");

            binding.uploadReceipt.setOnClickListener(view -> {
                if(purchase != null){
                    Purchase.savePurchase(purchase,binding.getRoot());
                }

                startActivity(new Intent(requireActivity(), MainActivity.class));

            });


        }


        return binding.getRoot();
    }
}