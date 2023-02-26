package ar.com.tpfinal.rang_store.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.com.tpfinal.rang_store.MainActivity;
import ar.com.tpfinal.rang_store.PaymentActivity;
import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.adapters.ImageSliderAdapter;
import ar.com.tpfinal.rang_store.data.datasource.firebase.Cart;
import ar.com.tpfinal.rang_store.data.datasource.firebase.Favorites;
import ar.com.tpfinal.rang_store.databinding.ProductInfoBinding;
import ar.com.tpfinal.rang_store.model.ItemCart;
import ar.com.tpfinal.rang_store.model.Product;

public class ProductInfoFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private NavController navHost;
    private ProductInfoBinding binding;
    private Integer quantity = 1;

    public ProductInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = ProductInfoBinding.inflate(inflater,container,false);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        if(getArguments() != null){
            RecyclerView recyclerView = binding.recyclerViewProductInfo;
            Product product = getArguments().getParcelable("product");

            Cart.productInCart(product,binding);

            Favorites.productInFavorites(product,binding);

            binding.titleProductInfo.setText(product.getTitle());
            binding.categoryProductInfo.setText(product.getCategory().getName());
            binding.descriptionProductInfo.setText(product.getDescription());
            binding.priceProductInfo.setText("$"+product.getPrice());
            List<String> urls = product.getImages();
            ImageSliderAdapter adapter = new ImageSliderAdapter(urls);
            recyclerView.setAdapter(adapter);
            enableEditProduct();

            TextWatcher quantityWatcher = new TextWatcher(){

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {updateTotalPrice(product); updateQuantity();}

                @Override
                public void afterTextChanged(Editable editable) {}
            };

            binding.quantityEditText.addTextChangedListener(quantityWatcher);
        }


        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navHost = NavHostFragment.findNavController(this);

        if (getArguments() != null) {

            mAuth = FirebaseAuth.getInstance();
            mFirestore = FirebaseFirestore.getInstance();

            binding.editfloatingButton.setOnClickListener(view1 -> { navHost.navigate(R.id.action_productInfoFragment_to_productCreator,getArguments()); });

            Product product = getArguments().getParcelable("product");

            binding.buttonBuyProductInfo.setOnClickListener(view1 -> {

                List<ItemCart> cart = new ArrayList<>();
                cart.add(new ItemCart(product,quantity));
                Bundle args = new Bundle();
                args.putParcelableArrayList("single_product", (ArrayList<? extends Parcelable>) cart);

                navHost.navigate(R.id.action_global_buyOrderFragment, args);
            });

            binding.buttonAddToCartProductInfo.setOnClickListener(view1 -> {

                String uid = mAuth.getCurrentUser().getUid();

                ItemCart cartItem = new ItemCart(product,quantity);

                mFirestore.collection("users")
                        .document(uid)
                        .update("cart", FieldValue.arrayUnion(cartItem))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startActivity(new Intent(requireActivity(), MainActivity.class));
                        Toast.makeText(requireContext(),"Producto agregado con exito",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(),"No se pudo agregar el producto",Toast.LENGTH_SHORT).show();
                    }
                });
            });

            binding.buttonRemoveProductFromCartProductInfo.setOnClickListener(view1 -> {
                ItemCart itemCart = new ItemCart(product,quantity);
                Cart.removeProductFromCart(itemCart,binding.getRoot());
            });

        }
    }

    private void updateTotalPrice(Product product){
        Double price = product.getPrice();
        String quantityS = binding.quantityEditText.getText().toString();
        Integer quantity = 1;
        if(!quantityS.isEmpty()){ quantity = Integer.valueOf(quantityS); }
        String totalPrice = String.valueOf(price*quantity);
        binding.priceProductInfo.setText("$"+totalPrice);
    }

    private void updateQuantity(){
        String quantityS = binding.quantityEditText.getText().toString();
        if(!quantityS.isEmpty() && !quantityS.equals("0")) { quantity = Integer.valueOf(binding.quantityEditText.getText().toString()); }
        else{ quantity = 1; }
    }

    private void enableEditProduct(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseFirestore.getInstance().collection("users").document(currentUser.getUid()).get().addOnSuccessListener(document ->{
            if(document.exists()){
                String role = document.getString("role");
                if(role != null && role.equals("admin")){
                    binding.editfloatingButton.setVisibility(View.VISIBLE);
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