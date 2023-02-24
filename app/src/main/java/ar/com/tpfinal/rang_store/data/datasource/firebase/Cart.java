package ar.com.tpfinal.rang_store.data.datasource.firebase;

import android.content.Context;
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
import java.util.List;

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.model.ItemCart;

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

                        List<ItemCart> cart = (List<ItemCart>) document.get("cart");

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

}
