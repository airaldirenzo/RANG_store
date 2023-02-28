package ar.com.tpfinal.rang_store.data.datasource.firebase;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.databinding.ProductInfoBinding;
import ar.com.tpfinal.rang_store.model.ItemCart;
import ar.com.tpfinal.rang_store.model.Product;

public class Cart {
    public static void goToCart(Fragment currentFragment){

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        List<HashMap> cartEntity = (List<HashMap>) document.get("cart");
                        List<ItemCart> cart = new ArrayList<>();

                        for(int i = 0; i < cartEntity.size(); i++){

                            Product product = ProductMapper.entityProductToProduct((HashMap) cartEntity.get(i).get("product"));
                            Integer quantity = (int)(long) cartEntity.get(i).get("quantity");

                            ItemCart itemCart = new ItemCart(product,quantity);
                            cart.add(itemCart);

                        }

                        Bundle args = new Bundle();
                        args.putParcelableArrayList("cart", (ArrayList<? extends Parcelable>) cart);

                        NavController navController = NavHostFragment.findNavController(currentFragment);
                        navController.navigate(R.id.action_global_buyOrderFragment, args);

                    } else {
                        Toast.makeText(currentFragment.requireContext(), "No se encontró el carrito",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(currentFragment.requireContext(),"No se pudo completar la tarea",Toast.LENGTH_SHORT).show();
                    Log.d("TASK FAILED", "failed with ", task.getException());
                }
            }
        });

    }

    public static void removeCart(){

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .update("cart",new ArrayList<>());

    }

    public static void removeProductFromCart(ItemCart cartItem, View view){

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("users")
                .document(uid).
                update("cart", FieldValue.arrayRemove(cartItem)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Fragment fragment = FragmentManager.findFragment(view);
                        Cart.goToCart(fragment);

                        Toast.makeText(fragment.requireContext(),"Producto eliminado con exito",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Fragment fragment = FragmentManager.findFragment(view);
                        Toast.makeText(fragment.requireContext(),"No se pudo eliminar el producto",Toast.LENGTH_LONG).show();
                    }
                });
    }

    public static void productInCart(Product product, ProductInfoBinding binding){

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<Object> userCart = (ArrayList<Object>) document.get("cart");
                        int position = 0;
                        boolean flag = true;
                        while(position < userCart.size() && flag){

                            HashMap productMap = (HashMap) ((HashMap)userCart.get(position)).get("product");
                            String quantity = ((HashMap) userCart.get(position)).get("quantity").toString();

                            if(productMap.get("id").toString().equals(product.getId().toString())){

                                binding.quantityEditText.setText(quantity);
                                binding.quantityEditText.setFocusable(false);
                                binding.quantityEditText.setEnabled(false);
                                binding.quantityEditText.setCursorVisible(false);
                                binding.quantityEditText.setKeyListener(null);

                                binding.buttonBuyProductInfo.setVisibility(View.GONE);
                                binding.buttonAddToCartProductInfo.setVisibility(View.GONE);
                                binding.buttonRemoveProductFromCartProductInfo.setVisibility(View.VISIBLE);

                                flag = false;
                            }
                            position++;
                        }
                    }
                } else {
                    Toast.makeText(binding.getRoot().getContext(),"No se pudo completar la tarea",Toast.LENGTH_SHORT).show();
                    Log.d("TASK FAILED", "failed with ", task.getException());
                }
            }
        });
    }
}
