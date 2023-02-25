package ar.com.tpfinal.rang_store.fragments;

import android.content.ClipData;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import ar.com.tpfinal.rang_store.PaymentActivity;
import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.adapters.OrderAdapter;
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

            binding.continuePurchaseButton.setOnClickListener(view -> {
                if(binding.orderRV.getChildCount() == 0){
                    navHost.navigate(R.id.action_buyOrderFragment_to_productChartFragment);
                    Toast.makeText(requireContext(),"No hay productos que comprar",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(requireActivity(), PaymentActivity.class);
                    if(singleProduct != null){
                        intent.putParcelableArrayListExtra("single_purchase", (ArrayList<? extends Parcelable>) singleProduct);
                    }
                    else if(getArguments().getParcelableArrayList("cart") != null){
                        intent.putParcelableArrayListExtra("cart_purchase", getArguments().getParcelableArrayList("cart"));
                    }
                    startActivity(intent);
                }

            });
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