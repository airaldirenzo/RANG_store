package ar.com.airaldirenzo.rang_store.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ar.com.airaldirenzo.rang_store.R;
import ar.com.airaldirenzo.rang_store.databinding.LogInBinding;

public class LogIn extends Fragment {

    private NavController navHost;
    private LogInBinding binding;

    public LogIn() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = LogInBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navHost = NavHostFragment.findNavController(this);

        binding.buttonIniciarSesion.setOnClickListener( view1 -> {

            Toast.makeText(getContext(),"HOLA?", Toast.LENGTH_SHORT).show();

                navHost.navigate(R.id.action_logIn_to_productChartFragment);

                if (logIn()) ;
            /*TODO se podria agregar un bundle con args que
               identifiquen al usuario (email o dni) para relacionarle las compras, etc*/

        });

        binding.buttonRegistrarse.setOnClickListener( view1 -> {
            //register()
        });

        binding.buttonForgotPassword.setOnClickListener( view1 -> {
            //passwordChange()
        });



    }

    private boolean logIn(){

        if(binding.editTextEmailAddress.getText().toString().isEmpty()) return false;
        else if(binding.editTextPassword.getText().toString().isEmpty()) return false;
        //TODO
        //else if el mail no existe en la base de datos
        //else if la contraseña es incorrecta

        return true;
    }

    private boolean register(){
        //TODO navegar a otra pantalla, pedir datos, no creo que lo implementemos
        //verificar que el mail este disponible y no se repita el dni

        return true;
    }
}
