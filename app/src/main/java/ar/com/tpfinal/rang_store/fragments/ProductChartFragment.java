package ar.com.tpfinal.rang_store.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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

        //Si somos admin mostramos el boton flotante
        enableProductCreator();

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navHost = NavHostFragment.findNavController(this);

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navHost.navigate(R.id.action_productChartFragment_to_productCreator);
            }
        });


        ArrayList<Product> products = new ArrayList<>();

        if (getArguments() != null) {
            products = getArguments().getParcelableArrayList("products_found");
        }

        //TODO BORRAR o COMENTAR, es para testing
        Product product1 = new Product(1,"producto1","descripcion1",1.0,null,null);
        Product product2 = new Product(2,"producto2","descripcion2",2.0,null,null);
        Product product3 = new Product(3,"producto3","descripcion3",3.0,null,null);
        Product product4 = new Product(4,"producto4","descripcion4",4.0,null,null);
        Product product5 = new Product(5,"producto5","descripcion5",5.0,null,null);
        Product product6 = new Product(6,"producto6","descripcion6",6.0,null,null);
        Product product7 = new Product(7,"producto7","descripcion7",7.0,null,null);
        Product product8 = new Product(8,"producto8","descripcion8",8.0,null,null);
        Product product9 = new Product(9,"producto9","descripcion9",9.0,null,null);
        Product product10 = new Product(10,"producto10","descripcion10",10.0,null,null);

        products.add(product1);products.add(product2);products.add(product3);products.add(product4);products.add(product5);
        products.add(product6);products.add(product7);products.add(product8);products.add(product9);products.add(product10);

        //HASTA ACA

        RecyclerView recycler = binding.productChartRV;
        int numberOfColumns = 2;
        recycler.setLayoutManager(new GridLayoutManager(this.getContext(), numberOfColumns));
        ProductAdapter productAdapter = new ProductAdapter(products);
        recycler.setAdapter(productAdapter);

    }

    private void enableProductCreator(){

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        //TODO puede llegar a tirar nullPointer si se nos corta el internet en medio de la app
        FirebaseFirestore.getInstance().collection("users").document(currentUser.getUid()).get().addOnSuccessListener(document ->{
            if(document.exists()){
                String role = document.getString("role");
                if(role != null && role.equals("admin")){
                    binding.floatingActionButton.setVisibility(View.VISIBLE);
                }
            }
            else{
                try {
                    throw new Exception("No se encontro un documento valido");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}