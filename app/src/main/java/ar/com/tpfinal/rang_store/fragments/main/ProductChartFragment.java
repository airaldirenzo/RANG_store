package ar.com.tpfinal.rang_store.fragments.main;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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

    private boolean dataSaving = false;

    // Broadcast receiver
    private final BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    dataSaving = false;
                    //Cargamos los productos desde la api, dependiendo del dataSaving se mostraran las imagenes o una imagen default
                    FilterObject filter = FilterObject.getInstance();
                    loadProducts(filter, dataSaving);
                    break;

                case WifiManager.WIFI_STATE_DISABLED:
                    onWiFiDisabled();
                    break;
            }
        }
    };

    public ProductChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ProductChartBinding.inflate(inflater,container,false);

        //Se crea el spinner de categorias
        createCategoriesSpinnerAdapter();

        //Si somos admin mostramos el boton flotante
        enableProductCreator();
        progressBarOn();

        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        requireActivity().registerReceiver(wifiStateReceiver, intentFilter);

        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unregisterReceiver(wifiStateReceiver);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navHost = NavHostFragment.findNavController(this);

        binding.floatingActionButton.setOnClickListener(view1 -> navHost.navigate(R.id.action_productChartFragment_to_productCreator));

        binding.toggleFilterButton.setOnClickListener(view1 -> {
            if (binding.filterContainer.getVisibility() == View.GONE) {
                binding.filterContainer.setVisibility(View.VISIBLE);
            }
            else {
                binding.filterContainer.setVisibility(View.GONE);
            }
        });

        binding.filterProductButton.setOnClickListener(view1 -> {

            FilterObject filter = FilterObject.getInstance();
            filter.resetFilter();

            String title = binding.titleFilterEditText.getText().toString();
            Category category = (Category)binding.categoriesFilterSpinner.getSelectedItem();
            String minPrice = binding.minPriceEditText.getText().toString();
            String maxPrice = binding.maxPriceEditText.getText().toString();

            if (title.isEmpty() && category.getName().equals("Seleccione una categoria") && minPrice.isEmpty() && maxPrice.isEmpty()) {
                return;
            }

            filter.setTitleFilter(title);
            filter.setCategoryId(category.getId());
            filter.setMinPriceFilter(minPrice);
            filter.setMaxPriceFilter(maxPrice);

            loadProducts(filter, dataSaving);

            binding.filterContainer.setVisibility(View.GONE);
        });

        binding.resetFilterButton.setOnClickListener(view1 -> {
            binding.titleFilterEditText.setText("");
            binding.categoriesFilterSpinner.setSelection(0);
            binding.minPriceEditText.setText("");
            binding.maxPriceEditText.setText("");

            FilterObject filter = FilterObject.getInstance();
            filter.resetFilter();

            loadProducts(filter, dataSaving);
        });
    }

    private void enableProductCreator(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore.getInstance().collection("users").document(currentUser.getUid()).get().addOnSuccessListener(document ->{
            if(document.exists()){
                String role = document.getString("role");
                if(role != null && role.equals("admin")){
                    binding.floatingActionButton.setVisibility(View.VISIBLE);
                }
            }
            else{
                Toast.makeText(requireContext(),"No se encontro el usuario",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProducts(FilterObject filterObject, Boolean dataSaving){
        OnResult<List<Product>> callback = new OnResult<>() {
            @Override
            public void onSuccess(List<Product> result) {
                requireActivity().runOnUiThread(() -> {
                    RecyclerView recycler = binding.productChartRV;
                    int numberOfColumns = 2;
                    recycler.setLayoutManager(new GridLayoutManager(requireContext(), numberOfColumns));
                    ProductAdapter productAdapter = new ProductAdapter(result, dataSaving);
                    recycler.setAdapter(productAdapter);
                    progressBarOff();
                });
            }

            @Override
            public void onError(Throwable exception) {
                exception.printStackTrace();
            }
        };

        ProductRepository pr = ProductRepositoryFactory.create();

        AppRetrofit.EXECUTOR_API.execute(()-> pr.listProducts(callback, filterObject));
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

    void onWiFiDisabled() {
        FilterObject filter = FilterObject.getInstance();
        AlertDialog dialog = new AlertDialog
                .Builder(requireActivity())
                .setPositiveButton("Activar ahorro de datos", (dialog1, which) -> {
                    dataSaving = true;
                    loadProducts(filter, dataSaving);
                })
                .setNegativeButton("Cancelar", (dialog12, which) -> {
                    dataSaving = false;
                    loadProducts(filter, dataSaving);
                })
                .setTitle("Ahorro de datos") // El título
                .setMessage("El WI-FI se encuentra deshabilitado. ¿Desea activar el modo de ahorro de datos? (no se mostraran las imagenes de los productos)")
                .create();
        dialog.show();
    }
}