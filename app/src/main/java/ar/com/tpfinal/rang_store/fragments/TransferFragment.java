package ar.com.tpfinal.rang_store.fragments;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ar.com.tpfinal.rang_store.MainActivity;
import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.data.datasource.firebase.Purchase;
import ar.com.tpfinal.rang_store.databinding.FragmentTransferBinding;
import ar.com.tpfinal.rang_store.model.ItemCart;


public class TransferFragment extends Fragment {

    private FragmentTransferBinding binding;

    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICATION_ID = 0;


    public TransferFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTransferBinding.inflate(inflater,container,false);

        if (getArguments() != null) {
            List<ItemCart> purchase = getArguments().getParcelableArrayList("purchase");
            Boolean cart = getArguments().getBoolean("cartOrder");

            binding.uploadReceipt.setOnClickListener(view -> {
                if(purchase != null){
                    Purchase.savePurchase(purchase,cart,binding.getRoot());
                    createNotificationChannel();
                    createNotification();
                }
                startActivity(new Intent(requireActivity(), MainActivity.class));
            });
        }
        return binding.getRoot();
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notification";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) requireActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireActivity().getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.baseline_shopping_cart_24);
        builder.setContentTitle("La orden de compra ha sido realizada!");
        builder.setContentText("La orden de compra ha sido realizada. Se enviara una notificacion cuando la misma sea aprobada");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(requireActivity().getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}