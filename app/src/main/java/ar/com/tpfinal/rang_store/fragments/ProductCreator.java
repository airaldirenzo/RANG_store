package ar.com.tpfinal.rang_store.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.data.OnResult;
import ar.com.tpfinal.rang_store.data.datasource.retrofit.AppRetrofit;
import ar.com.tpfinal.rang_store.data.factory.ProductRepositoryFactory;
import ar.com.tpfinal.rang_store.data.repository.ProductRepository;
import ar.com.tpfinal.rang_store.databinding.FragmentProductCreatorBinding;
import ar.com.tpfinal.rang_store.model.Category;
import ar.com.tpfinal.rang_store.model.Product;

public class ProductCreator extends Fragment {

    private FragmentProductCreatorBinding binding;

    private NavController navHost;

    public ProductCreator() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductCreatorBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navHost = NavHostFragment.findNavController(this);

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(null, "Seleccione una categoria"));
        categories.add(new Category(1, "ropa", "clothes"));
        categories.add(new Category(2, "electronica", "electronics"));
        categories.add(new Category(3, "muebles", "furniture"));
        categories.add(new Category(4, "zapatillas", "shoes"));
        categories.add(new Category(5, "otros", "others"));
        ArrayAdapter<Category> categoriesAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,categories);
        binding.categoriesSpinner.setAdapter(categoriesAdapter);
        binding.buttonCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.editTextProductTitle.getText().toString();
                String description = binding.editTextProductDescription.getText().toString();
                String priceString = binding.editTextProductPrice.getText().toString();
                Category category = (Category) binding.categoriesSpinner.getSelectedItem();

                if (title.isEmpty() || description.isEmpty() || priceString.isEmpty() || category.getName().equals("Seleccione una categoria")) {
                    Toast.makeText(requireContext(), "Complete los campos faltantes para continuar", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<String> imgArray = new ArrayList<>();
                imgArray.add("https://api.lorem.space/image?w=640&h=480&r=5435");
                imgArray.add("https://api.lorem.space/image?w=640&h=480&r=5435");
                Product product = new Product(title, description, Double.valueOf(priceString), imgArray, category);

                OnResult<Product> callback = new OnResult<Product>() {
                    @Override
                    public void onSuccess(Product result) {
                        requireActivity().runOnUiThread(() ->Toast.makeText(requireContext(), "Producto creado con exito", Toast.LENGTH_LONG).show());
                        Bundle args = new Bundle();
                        //TODO cambiar KEY
//                        args.putParcelable("product_selected", result);
//                        navHost.navigate(R.id.action_productCreator_to_productInfoFragment, args);
                    }

                    @Override
                    public void onError(Throwable exception) {
                        exception.printStackTrace();
                    }
                };

                ProductRepository pr = ProductRepositoryFactory.create();

                AppRetrofit.EXECUTOR_API.execute(()-> { pr.createProduct(product, callback); });
            }
        });

        binding.buttonUploadProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(requireContext(), "NO IMPLEMENTADO", Toast.LENGTH_LONG).show();
            }
        });
    }
}