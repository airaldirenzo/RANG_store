package ar.com.tpfinal.rang_store.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import ar.com.tpfinal.rang_store.adapters.OrderAdapter;
import ar.com.tpfinal.rang_store.adapters.ProductAdapter;
import ar.com.tpfinal.rang_store.databinding.FragmentBuyOrderBinding;
import ar.com.tpfinal.rang_store.model.Product;

public class BuyOrderFragment extends Fragment {

    private FragmentBuyOrderBinding binding;

    public BuyOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBuyOrderBinding.inflate(inflater,container,false);
        this.setArguments(requireActivity().getIntent().getExtras());

        if(getArguments() != null){
            Product product = getArguments().getParcelable("product");
            int quantity = getArguments().getInt("quantity");
            Log.i("QUANTITY", "onCreateView: "+quantity);
            loadOrder(product,quantity);
        }

        return binding.getRoot();
    }

    private void loadOrder(Product product, int quantity){
        RecyclerView recycler = binding.orderRV;
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<Pair<Product,Integer>> dataList = new ArrayList<>();
        dataList.add(new Pair<>(product,quantity));
        OrderAdapter orderAdapter = new OrderAdapter(dataList);
        recycler.setAdapter(orderAdapter);
    }

}