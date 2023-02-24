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
import android.widget.ArrayAdapter;
import android.widget.SearchView;
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
import ar.com.tpfinal.rang_store.data.filter.FilterObject;
import ar.com.tpfinal.rang_store.data.repository.ProductRepository;
import ar.com.tpfinal.rang_store.databinding.ProductChartBinding;
import ar.com.tpfinal.rang_store.model.Category;
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

        //Se crea el spinner de categorias
        createCategoriesSpinnerAdapter();

        //Si somos admin mostramos el boton flotante
        enableProductCreator();
        progressBarOn();

        //Cargamos los productos desde la api(sin filtro para cada vez que se ingresa)
        FilterObject filter = FilterObject.getInstance();
        loadProducts(filter);

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

        binding.toggleFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.filterContainer.getVisibility() == View.GONE) {
                    binding.filterContainer.setVisibility(View.VISIBLE);
                }
                else {
                    binding.filterContainer.setVisibility(View.GONE);
                }
            }
        });

        binding.filterProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FilterObject filter = FilterObject.getInstance();
                filter.resetFilter();

                Category category = (Category)binding.categoriesFilterSpinner.getSelectedItem();
                String minPrice = binding.minPriceEditText.getText().toString();
                String maxPrice = binding.maxPriceEditText.getText().toString();

                if (category.equals("Seleccione una categoria") && minPrice.isEmpty() && maxPrice.isEmpty()) {
                    return;
                }

                filter.setCategoryId(category.getId());
                filter.setMinPriceFilter(minPrice);
                filter.setMaxPriceFilter(maxPrice);

                loadProducts(filter);

                binding.filterContainer.setVisibility(View.GONE);
            }
        });

        binding.resetFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.categoriesFilterSpinner.setSelection(0);
                binding.minPriceEditText.setText("");
                binding.maxPriceEditText.setText("");
            }
        });
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

    private void loadProducts(FilterObject filterObject){

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

        AppRetrofit.EXECUTOR_API.execute(()-> { pr.listProducts(callback, filterObject); });
    }

    private void progressBarOn(){
        binding.productChartRV.setVisibility(View.GONE);
        binding.progressBarProductChart.setVisibility(View.VISIBLE);
    }

    private void progressBarOff(){
        binding.progressBarProductChart.setVisibility(View.GONE);
        binding.productChartRV.setVisibility(View.VISIBLE);
    }

    void createCategoriesSpinnerAdapter() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(null, "Seleccione una categoria"));
        categories.add(new Category(1, "ropa", "clothes"));
        categories.add(new Category(2, "electronica", "electronics"));
        categories.add(new Category(3, "muebles", "furniture"));
        categories.add(new Category(4, "zapatillas", "shoes"));
        categories.add(new Category(5, "otros", "others"));
        ArrayAdapter<Category> categoriesAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,categories);
        binding.categoriesFilterSpinner.setAdapter(categoriesAdapter);
    }

}