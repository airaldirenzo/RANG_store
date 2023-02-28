package ar.com.tpfinal.rang_store.fragments.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ar.com.tpfinal.rang_store.PaymentActivity;
import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.adapters.OrderAdapter;
import ar.com.tpfinal.rang_store.databinding.FragmentBuyOrderBinding;
import ar.com.tpfinal.rang_store.model.ItemCart;
import ar.com.tpfinal.rang_store.model.Order;

public class BuyOrderFragment extends Fragment {

    private FragmentBuyOrderBinding binding;
    private NavController navHost;

    public BuyOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navHost = NavHostFragment.findNavController(this);
        binding = FragmentBuyOrderBinding.inflate(inflater,container,false);

        if(getArguments() != null){

            List<ItemCart> cart = getArguments().getParcelableArrayList("cart");
            boolean cartOrder = getArguments().getBoolean("cartOrder");

            if(cart != null){

                loadOrder(cart);

                double totalPrice = Order.getTotalPrice(cart);
                binding.totalPriceOrder.setText("Precio total: $" + totalPrice);
            }

            Boolean readOnly = getArguments().getBoolean("readOnly");
            if(readOnly){
                binding.continuePurchaseButton.setVisibility(View.GONE);
            }

            binding.continuePurchaseButton.setOnClickListener(view -> {
                if(binding.orderRV.getChildCount() == 0){
                    navHost.navigate(R.id.action_buyOrderFragment_to_productChartFragment);
                    Toast.makeText(requireContext(),"No hay productos que comprar",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(requireActivity(), PaymentActivity.class);
                    if(cart != null){
                        intent.putParcelableArrayListExtra("purchase", (ArrayList<? extends Parcelable>) cart);
                        if(cartOrder) intent.putExtra("cartOrder",cartOrder);
                    }

                    startActivity(intent);
                }

            });
        }

        return binding.getRoot();
    }

    private void loadOrder(List<ItemCart> cart){
        RecyclerView recycler = binding.orderRV;
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        OrderAdapter orderAdapter = new OrderAdapter(cart);
        recycler.setAdapter(orderAdapter);
    }
}