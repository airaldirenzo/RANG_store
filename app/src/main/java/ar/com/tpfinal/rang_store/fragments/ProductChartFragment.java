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
import java.util.List;

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.adapters.ProductAdapter;
import ar.com.tpfinal.rang_store.data.OnResult;
import ar.com.tpfinal.rang_store.data.datasource.retrofit.AppRetrofit;
import ar.com.tpfinal.rang_store.data.factory.ProductRepositoryFactory;
import ar.com.tpfinal.rang_store.data.repository.ProductRepository;
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
        progressBarOn();
        loadProducts();

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

        if (getArguments() != null) {
            //TODO? products = getArguments().getParcelableArrayList("products_found");
        }


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

    private void loadProducts(){

        OnResult<List<Product>> callback = new OnResult<>() {
            @Override
            public void onSuccess(List<Product> result) {

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView recycler = binding.productChartRV;
                        int numberOfColumns = 2;
                        recycler.setLayoutManager(new GridLayoutManager(requireContext(), numberOfColumns));
                        ProductAdapter productAdapter = new ProductAdapter(result);
                        recycler.setAdapter(productAdapter);
                        progressBarOff();
                    }
                });

            }

            @Override
            public void onError(Throwable exception) {
                exception.printStackTrace();
            }
        };

        ProductRepository pr = ProductRepositoryFactory.create();

        AppRetrofit.EXECUTOR_API.execute(()-> { pr.listProducts(callback); });

    }

    private void progressBarOn(){
        binding.productChartRV.setVisibility(View.GONE);
        binding.progressBarProductChart.setVisibility(View.VISIBLE);
    }

    private void progressBarOff(){
        binding.progressBarProductChart.setVisibility(View.GONE);
        binding.productChartRV.setVisibility(View.VISIBLE);
    }

}