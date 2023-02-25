package ar.com.tpfinal.rang_store.data.datasource.firebase;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.util.List;

import ar.com.tpfinal.rang_store.MainActivity;
import ar.com.tpfinal.rang_store.model.ItemCart;
import ar.com.tpfinal.rang_store.model.Order;

public class Purchase {

    public static void savePurchase(List<ItemCart> itemCartList, View view){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Order order = new Order(itemCartList,uid, LocalDateTime.now());

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


}
