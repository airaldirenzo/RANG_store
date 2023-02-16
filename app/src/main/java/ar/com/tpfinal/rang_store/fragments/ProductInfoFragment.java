package ar.com.tpfinal.rang_store.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.adapters.ImageSliderAdapter;
import ar.com.tpfinal.rang_store.databinding.ProductInfoBinding;

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

        //TODO buscar de la api las imagenes para ponerlas en el carousel
        //HARDCODED para probar
        RecyclerView recyclerView = binding.recyclerViewProductInfo;
        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.baseline_favorite_24);
        images.add(R.drawable.baseline_menu_24);
        images.add(R.drawable.baseline_delete_24);
        images.add(R.drawable.baseline_home_24);
        images.add(R.drawable.baseline_search_24);
        images.add(R.drawable.baseline_settings_24);
        images.add(R.drawable.ic_launcher_background);

        ImageSliderAdapter adapter = new ImageSliderAdapter(images);
        recyclerView.setAdapter(adapter);

    }
}