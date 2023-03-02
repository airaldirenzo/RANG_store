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

import ar.com.tpfinal.rang_store.adapters.FavoritesAdapter;
import ar.com.tpfinal.rang_store.databinding.FragmentFavoritesBinding;
import ar.com.tpfinal.rang_store.model.Product;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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