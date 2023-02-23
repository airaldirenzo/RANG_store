package ar.com.tpfinal.rang_store.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
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

        createCategoriesSpinnerAdapter();

        if(getArguments() != null){
            Product product = getArguments().getParcelable("product");

            binding.editTextProductTitle.setText(product.getTitle());
            binding.editTextProductDescription.setText(product.getDescription());
            binding.editTextProductPrice.setText(String.valueOf(product.getPrice()));
            binding.categoriesSpinner.setSelection(product.getCategory().getId());
            binding.createProductButton.setVisibility(View.GONE);
            binding.confirmChangesButton.setVisibility(View.VISIBLE);
            binding.deleteFloatingButton.setVisibility(View.VISIBLE);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Se obtiene el fragmento de navegacion
        navHost = NavHostFragment.findNavController(this);

        // Se obtiene el repositorio de productos
        ProductRepository pr = ProductRepositoryFactory.create();

        binding.createProductButton.setOnClickListener(new View.OnClickListener() {
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
                        requireActivity().runOnUiThread(() ->{
                            Toast.makeText(requireContext(), "Producto creado con exito", Toast.LENGTH_LONG).show();
                            Bundle args = new Bundle();
                            args.putParcelable("product", result);
                            navHost.navigate(R.id.action_productCreator_to_productInfoFragment, args);
                        });
                    }
                    @Override
                    public void onError(Throwable exception) {
                        requireActivity().runOnUiThread(() ->Toast.makeText(requireContext(), "No pudo crearse el producto", Toast.LENGTH_LONG).show());
                        exception.printStackTrace();
                    }
                };

                AppRetrofit.EXECUTOR_API.execute(()->pr.createProduct(product, callback));
            }
        });

        binding.confirmChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Product product = getArguments().getParcelable("product");

                product.setTitle(binding.editTextProductTitle.getText().toString());
                product.setDescription(binding.editTextProductDescription.getText().toString());
                product.setPrice(Double.valueOf(binding.editTextProductPrice.getText().toString()));
                product.setCategory((Category) binding.categoriesSpinner.getSelectedItem());

                OnResult<Product> callback = new OnResult<Product>() {
                    @Override
                    public void onSuccess(Product result) {
                        requireActivity().runOnUiThread(() ->{
                            Toast.makeText(requireContext(), "Producto modificado con exito", Toast.LENGTH_LONG).show();
                            Bundle args = new Bundle();
                            args.putParcelable("product", result);
                            navHost.navigate(R.id.action_productCreator_to_productInfoFragment, args);
                        });
                    }
                    @Override
                    public void onError(Throwable exception) {
                        requireActivity().runOnUiThread(() ->Toast.makeText(requireContext(), "No pudo modificarse el producto", Toast.LENGTH_LONG).show());
                        exception.printStackTrace();
                    }
                };
                AppRetrofit.EXECUTOR_API.execute(()->pr.updateProduct(product, callback));
            }
        });

        binding.deleteFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = getArguments().getParcelable("product");

                AlertDialog dialog = new AlertDialog
                        .Builder(requireActivity())
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                OnResult<Boolean> callback = new OnResult<Boolean>() {
                                    @Override
                                    public void onSuccess(Boolean result) {
                                        Log.i("ON SUCCESS", "llego");
                                        requireActivity().runOnUiThread(() ->{
                                            Toast.makeText(requireContext(), "Producto eliminado con exito", Toast.LENGTH_LONG).show();
                                            navHost.navigate(R.id.action_global_productChartFragment);
                                        });
                                    }
                                    @Override
                                    public void onError(Throwable exception) {
                                        requireActivity().runOnUiThread(() ->Toast.makeText(requireContext(), "No pudo modificarse el producto", Toast.LENGTH_LONG).show());
                                        exception.printStackTrace();
                                    }
                                };
                                AppRetrofit.EXECUTOR_API.execute(()->pr.deleteProduct(product, callback));
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Eliminar producto") // El título
                        .setMessage("¿Esta seguro de que desea eliminar el producto?")
                        .create();

                dialog.show();
            }
        });

        binding.uploadProductImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(requireContext(), "NO IMPLEMENTADO", Toast.LENGTH_LONG).show();
            }
        });
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
        binding.categoriesSpinner.setAdapter(categoriesAdapter);
    }
}