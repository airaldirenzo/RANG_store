package ar.com.tpfinal.rang_store.data.datasource.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import ar.com.tpfinal.rang_store.MainActivity;
import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.model.ItemCart;
import ar.com.tpfinal.rang_store.model.Order;
import ar.com.tpfinal.rang_store.model.Product;

public class Purchase {

    public static void savePurchase(List<ItemCart> itemCartList, View view){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        double totalPrice = Order.getTotalPrice(itemCartList);
        int uniqueProducts = Order.getUniqueProducts(itemCartList);

        Order order = new Order(itemCartList,uniqueProducts, Calendar.getInstance().getTime(),totalPrice);

        FirebaseFirestore.getInstance().collection("buyOrders").add(order).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                if(itemCartList.size() > 1){
                    Cart.removeCart();
                }
                Toast.makeText(view.getContext(),"Compra procesada con exito",Toast.LENGTH_SHORT).show();
                //TODO NAVEGAR A FACTURA o intent a mainActivity?
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(view.getContext(),"No se pudo procesar la compra",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void goToPurchases(Fragment currentFragment){
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("buyOrders")
                .whereEqualTo("userUid",userUid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            List<Order> orders = new ArrayList<>();

                            for(QueryDocumentSnapshot document : task.getResult()){
                               Order order = OrderMapper.entityOrderToOrder((HashMap) document.getData(),document.getId());
                               orders.add(order);
                            }

                            Bundle args = new Bundle();
                            args.putParcelableArrayList("orders", (ArrayList<? extends Parcelable>) orders);

                            NavController navController = NavHostFragment.findNavController(currentFragment);
                            navController.navigate(R.id.action_global_purchaseHistory, args);

                        } else {
                            Toast.makeText(currentFragment.requireContext(),"No se pudo completar la tarea",Toast.LENGTH_SHORT).show();
                            Log.d("TASK FAILED", "failed with ", task.getException());
                        }
                    }
                });
    }

}
