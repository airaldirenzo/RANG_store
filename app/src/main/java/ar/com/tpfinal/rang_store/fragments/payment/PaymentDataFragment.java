package ar.com.tpfinal.rang_store.fragments.payment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ar.com.tpfinal.rang_store.MainActivity;
import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.data.datasource.firebase.Purchase;
import ar.com.tpfinal.rang_store.databinding.FragmentPaymentDataBinding;
import ar.com.tpfinal.rang_store.model.ItemCart;
import ar.com.tpfinal.rang_store.notifications.Notification;

public class PaymentDataFragment extends Fragment {

    private FragmentPaymentDataBinding binding;
    private NavController navHost;
    private List<ItemCart> purchase;
    private Boolean cartOrder;

    public PaymentDataFragment() {
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
        binding = FragmentPaymentDataBinding.inflate(inflater,container,false);


        if(getArguments() != null){
            purchase = getArguments().getParcelableArrayList("purchase");
            cartOrder = getArguments().getBoolean("cartOrder");
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navHost = NavHostFragment.findNavController(this);

        binding.radioButtonCredit.setOnClickListener(view1 -> showFields());
        binding.radioButtonDebit.setOnClickListener(view1 -> showFields());
        binding.radioButtonTransfer.setOnClickListener(view1 -> showFields());

        binding.buyButton.setOnClickListener(view1 -> {
            if(binding.radioButtonCredit.isChecked() || binding.radioButtonDebit.isChecked()){

                if(checkEmptyFields()) { return; }

                //TODO PARSEAR Y VERIFICAR FECHA VALIDA, FECHA MAYOR A LA ACTUAL, Y QUE NO PONGAN 30 DE FEBRERO, ETC

                if(purchase != null){
                    Purchase.savePurchase(purchase,cartOrder,binding.getRoot());
                    makeNotification();
                }

                startActivity(new Intent(requireActivity(), MainActivity.class));
                requireActivity().finish();
            }
            else if(binding.radioButtonTransfer.isChecked()){
                navHost.navigate(R.id.action_purchaseDataFragment_to_transferFragment,getArguments());
            }
        });
    }

    private boolean checkEmptyFields(){

        boolean flag = false;

        if(binding.editTextCardNumber.getText().toString().isEmpty()){
            binding.editTextCardNumber.setError("Numero de tarjeta obligatorio");
            binding.editTextCardNumber.requestFocus();

            flag = true;
        }

        if(binding.editTextExpirationDate.getText().toString().isEmpty()){
            binding.editTextExpirationDate.setError("Fecha de expiracion obligatorio");
            binding.editTextExpirationDate.requestFocus();

            flag = true;
        }

        if(binding.editTextSecurityCode.getText().toString().isEmpty()){
            binding.editTextSecurityCode.setError("Codigo de seguridad obligatorio");
            binding.editTextSecurityCode.requestFocus();

            flag = true;
        }
        if(binding.editTextCardName.getText().toString().isEmpty()) {
            binding.editTextCardName.setError("Nombre de la tarjeta obligatorio");
            binding.editTextCardName.requestFocus();

            flag = true;
        }

        return flag;
    }

    private void showFields(){
        if(binding.radioButtonCredit.isChecked() || binding.radioButtonDebit.isChecked()){
            binding.textViewDepositTitle.setVisibility(View.GONE);

            binding.textViewCardNumber.setVisibility(View.VISIBLE);
            binding.editTextCardNumber.setVisibility(View.VISIBLE);
            binding.textViewExpirationDate.setVisibility(View.VISIBLE);
            binding.editTextExpirationDate.setVisibility(View.VISIBLE);
            binding.textViewSecurityCode.setVisibility(View.VISIBLE);
            binding.editTextSecurityCode.setVisibility(View.VISIBLE);
            binding.textViewCardName.setVisibility(View.VISIBLE);
            binding.editTextCardName.setVisibility(View.VISIBLE);
            binding.buyButton.setVisibility(View.VISIBLE);
        }
        else{
            binding.textViewCardNumber.setVisibility(View.GONE);
            binding.editTextCardNumber.setVisibility(View.GONE);
            binding.textViewExpirationDate.setVisibility(View.GONE);
            binding.editTextExpirationDate.setVisibility(View.GONE);
            binding.textViewSecurityCode.setVisibility(View.GONE);
            binding.editTextSecurityCode.setVisibility(View.GONE);
            binding.textViewCardName.setVisibility(View.GONE);
            binding.editTextCardName.setVisibility(View.GONE);

            binding.textViewDepositTitle.setVisibility(View.VISIBLE);
            binding.buyButton.setVisibility(View.VISIBLE);
        }
    }

    private void makeNotification() {
        String CHANNEL_ID = "NOTIFICATION";
        String name = "notification";

        Notification.createNotificationChannel(requireActivity(), CHANNEL_ID, name);
        Notification.createNotification(requireActivity(), CHANNEL_ID);
    }
}
