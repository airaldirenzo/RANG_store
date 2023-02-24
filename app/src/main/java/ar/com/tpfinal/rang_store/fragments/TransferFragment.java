package ar.com.tpfinal.rang_store.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.com.tpfinal.rang_store.databinding.FragmentTransferBinding;


public class TransferFragment extends Fragment {

    private FragmentTransferBinding binding;

    public TransferFragment() {
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
        binding = FragmentTransferBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}