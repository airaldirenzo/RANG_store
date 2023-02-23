package ar.com.tpfinal.rang_store.fragments;

import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
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

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.adapters.OrderAdapter;
import ar.com.tpfinal.rang_store.adapters.ProductAdapter;
import ar.com.tpfinal.rang_store.databinding.FragmentBuyOrderBinding;
import ar.com.tpfinal.rang_store.model.ItemCart;
import ar.com.tpfinal.rang_store.model.Product;

public class BuyOrderFragment extends Fragment {

    private FragmentBuyOrderBinding binding;
    private NavController navHost;

    public BuyOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navHost = NavHostFragment.findNavController(this);
        binding = FragmentBuyOrderBinding.inflate(inflater,container,false);
        this.setArguments(requireActivity().getIntent().getExtras());

        if(getArguments() != null){

            List<ItemCart> singleProduct = getArguments().getParcelableArrayList("single_product");
            List<Map<String, Object>> cart = new ArrayList<>();

            if(getArguments().getParcelableArrayList("cart") != null){
                cart.addAll(getArguments().getParcelableArrayList("cart"));
                loadOrder(cart);
            }
            else if(singleProduct != null){
                loadOrder(singleProduct.get(0).getProduct(),singleProduct.get(0).getQuantity());
            }

            binding.continuePurchaseButton.setOnClickListener(view -> { navHost.navigate(R.id.action_buyOrderFragment_to_purchaseDataFragment,getArguments()); });
        }

        return binding.getRoot();
    }

    private void loadOrder(Product product, int quantity){
        RecyclerView recycler = binding.orderRV;
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<Map<String,Object>> dataList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("product",product);
        map.put("quantity",quantity);
        dataList.add(map);
        OrderAdapter orderAdapter = new OrderAdapter(dataList);
        recycler.setAdapter(orderAdapter);
    }

    private void loadOrder(List<Map<String,Object>> cart){
        RecyclerView recycler = binding.orderRV;
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        OrderAdapter orderAdapter = new OrderAdapter(cart);
        recycler.setAdapter(orderAdapter);
    }

}