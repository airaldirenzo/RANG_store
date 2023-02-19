package ar.com.tpfinal.rang_store.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.adapters.ImageSliderAdapter;
import ar.com.tpfinal.rang_store.databinding.ProductInfoBinding;
import ar.com.tpfinal.rang_store.model.Product;

public class ProductInfoFragment extends Fragment {

    private NavController navHost;
    private ProductInfoBinding binding;

    public ProductInfoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ProductInfoBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navHost = NavHostFragment.findNavController(this);

        if (getArguments() != null) {
            RecyclerView recyclerView = binding.recyclerViewProductInfo;
            Product product = getArguments().getParcelable("product_selected");
            binding.titleProductInfo.setText(product.getTitle());
            binding.descriptionProductInfo.setText(product.getDescription());
            binding.priceProductInfo.setText("$"+String.valueOf(product.getPrice()));
            List<String> urls = product.getImages();
            Log.i(null, urls.toString());
            ImageSliderAdapter adapter = new ImageSliderAdapter(urls);
            recyclerView.setAdapter(adapter);
        }

        binding.buttonBuyProductInfo.setOnClickListener(view1 -> { navHost.navigate(R.id.action_productInfoFragment_to_purchaseDataFragment); });

    }
}