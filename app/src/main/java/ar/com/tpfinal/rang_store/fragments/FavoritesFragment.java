package ar.com.tpfinal.rang_store.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.tpfinal.rang_store.PaymentActivity;
import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.adapters.FavoritesAdapter;
import ar.com.tpfinal.rang_store.adapters.OrderAdapter;
import ar.com.tpfinal.rang_store.data.datasource.firebase.Favorites;
import ar.com.tpfinal.rang_store.databinding.FragmentBuyOrderBinding;
import ar.com.tpfinal.rang_store.databinding.FragmentFavoritesBinding;
import ar.com.tpfinal.rang_store.model.ItemCart;
import ar.com.tpfinal.rang_store.model.Product;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    private NavController navHost;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navHost = NavHostFragment.findNavController(this);
        binding = FragmentFavoritesBinding.inflate(inflater,container,false);

        if(getArguments() != null){
            List<Product> favorites = getArguments().getParcelableArrayList("favorites");
            if(favorites != null){
                loadFavorites(favorites);
            }
        }

        return binding.getRoot();
    }

    private void loadFavorites(List<Product> favorites){
        RecyclerView recycler = binding.favoritesRV;
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        FavoritesAdapter orderAdapter = new FavoritesAdapter(favorites);
        recycler.setAdapter(orderAdapter);
    }

}