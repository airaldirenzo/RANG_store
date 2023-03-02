package ar.com.tpfinal.rang_store.data.datasource.firebase;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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

import ar.com.tpfinal.rang_store.MainActivity;
import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.databinding.ProductInfoBinding;
import ar.com.tpfinal.rang_store.model.ItemCart;
import ar.com.tpfinal.rang_store.model.Product;

public class Favorites {
    public static void goToFavorites(Fragment currentFragment){

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

                                List<HashMap> favoritesEntity = (List<HashMap>) document.get("favorites");
                                List<Product> favorites = new ArrayList<>();

                                for(int i = 0; i < favoritesEntity.size(); i++){
                                    Product product = ProductMapper.entityProductToProduct((HashMap) favoritesEntity.get(i));
                                    favorites.add(product);
                                }

                                Bundle args = new Bundle();
                                args.putParcelableArrayList("favorites", (ArrayList<? extends Parcelable>) favorites);

                                NavController navController = NavHostFragment.findNavController(currentFragment);
                                navController.navigate(R.id.action_global_favoritesFragment, args);

                            } else {
                                Toast.makeText(currentFragment.requireContext(), "No se encontró la lista de favoritos",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(currentFragment.requireContext(),"No se pudo completar la tarea",Toast.LENGTH_SHORT).show();
                            Log.d("TASK FAILED", "failed with ", task.getException());
                        }
                    }
                });
    }

    public static void removeFavorites(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .update("favorites",new ArrayList<>());
    }

    public static void removeProductFromFavorites(Product product, View view){

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("users")
                .document(uid).
                update("favorites", FieldValue.arrayRemove(product)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Fragment fragment = FragmentManager.findFragment(view);
                        Bundle args = new Bundle();
                        args.putParcelable("product", product);

                        NavController navController = NavHostFragment.findNavController(fragment);
                        navController.navigate(R.id.productInfoFragment, args);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Fragment fragment = FragmentManager.findFragment(view);
                        Toast.makeText(fragment.requireContext(),"No se pudo eliminar el producto",Toast.LENGTH_LONG).show();
                    }
                });
    }

    public static void productInFavorites(Product product, ProductInfoBinding binding){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<HashMap> userFavorites = (ArrayList<HashMap>) document.get("favorites");
                        int position = 0;
                        boolean isFavorite = false;
                        while(position < userFavorites.size() && !isFavorite) {

                            Product productFavorite = ProductMapper.entityProductToProduct(userFavorites.get(position));
                            if(productFavorite.getId().toString().equals(product.getId().toString())){

                                binding.favouriteButtonProductInfo.setColorFilter(Color.RED);
                                isFavorite = true;
                            }
                            position++;
                        }

                        if (isFavorite) {
                            binding.favouriteButtonProductInfo.setOnClickListener(view1 -> {
                                Favorites.removeProductFromFavorites(product, view1);
                            });
                        }
                        else {
                            binding.favouriteButtonProductInfo.setOnClickListener(view1 -> {
                                Favorites.addProductToFavorites(product, view1);
                            });
                        }
                    }
                } else {
                    Toast.makeText(binding.getRoot().getContext(),"No se pudo completar la tarea",Toast.LENGTH_SHORT).show();
                    Log.d("TASK FAILED", "failed with ", task.getException());
                }
            }
        });
    }

    public static void addProductToFavorites(Product product, View view) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .update("favorites", FieldValue.arrayUnion(product))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Fragment fragment = FragmentManager.findFragment(view);
                        Bundle args = new Bundle();
                        args.putParcelable("product", product);

                        NavController navController = NavHostFragment.findNavController(fragment);
                        navController.navigate(R.id.productInfoFragment, args);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Fragment fragment = FragmentManager.findFragment(view);
                        Toast.makeText(fragment.requireContext(),"No se pudo agregar el producto",Toast.LENGTH_SHORT).show();
                    }
                });
    };
}
