package ar.com.tpfinal.rang_store.fragments.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ar.com.tpfinal.rang_store.adapters.PurchaseAdapter;
import ar.com.tpfinal.rang_store.databinding.FragmentPurchaseHistoryBinding;
import ar.com.tpfinal.rang_store.model.Order;

public class PurchaseHistory extends Fragment {

    private FragmentPurchaseHistoryBinding binding;

    public PurchaseHistory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPurchaseHistoryBinding.inflate(inflater,container,false);

        if(getArguments() != null){
            List<Order> orders = getArguments().getParcelableArrayList("orders");
            loadPurchases(orders);
        }

        return binding.getRoot();
    }

    private void loadPurchases(List<Order> dataList){
        RecyclerView recycler = binding.purchaseRV;
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        PurchaseAdapter purchaseAdapter = new PurchaseAdapter(dataList);
        recycler.setAdapter(purchaseAdapter);
    }
}