package ar.com.tpfinal.rang_store.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDateTime;
import java.util.ArrayList;

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.adapters.ProductAdapter;
import ar.com.tpfinal.rang_store.databinding.ProductChartBinding;
import ar.com.tpfinal.rang_store.model.Product;

public class ProductChartFragment extends Fragment {

    private NavController navHost;
    private ProductChartBinding binding;

    public ProductChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ProductChartBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navHost = NavHostFragment.findNavController(this);
        ArrayList<Product> products = new ArrayList<>();

        if (getArguments() != null) {
            products = getArguments().getParcelableArrayList("products_found");
        }

        //TODO BORRAR o COMENTAR, es para testing
        Product product1 = new Product(1,"producto1","descripcion1",1.0,1, LocalDateTime.now(),LocalDateTime.now());
        Product product2 = new Product(2,"producto2","descripcion2",2.0,2, LocalDateTime.now(),LocalDateTime.now());
        Product product3 = new Product(3,"producto3","descripcion3",3.0,3, LocalDateTime.now(),LocalDateTime.now());
        Product product4 = new Product(4,"producto4","descripcion4",4.0,4, LocalDateTime.now(),LocalDateTime.now());
        Product product5 = new Product(5,"producto5","descripcion5",5.0,5, LocalDateTime.now(),LocalDateTime.now());
        Product product6 = new Product(6,"producto6","descripcion6",6.0,6, LocalDateTime.now(),LocalDateTime.now());
        Product product7 = new Product(7,"producto7","descripcion7",7.0,7, LocalDateTime.now(),LocalDateTime.now());
        Product product8 = new Product(8,"producto8","descripcion8",8.0,8, LocalDateTime.now(),LocalDateTime.now());
        Product product9 = new Product(9,"producto9","descripcion9",9.0,9, LocalDateTime.now(),LocalDateTime.now());
        Product product10 = new Product(10,"producto10","descripcion10",10.0,10, LocalDateTime.now(),LocalDateTime.now());

        products.add(product1);products.add(product2);products.add(product3);products.add(product4);products.add(product5);
        products.add(product6);products.add(product7);products.add(product8);products.add(product9);products.add(product10);

        //HASTA ACA

        RecyclerView recycler = binding.productChartRV;
        int numberOfColumns = 2;
        recycler.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));
        ProductAdapter productAdapter = new ProductAdapter(products);
        recycler.setAdapter(productAdapter);
    }

}