package ar.com.tpfinal.rang_store.fragments;

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

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.databinding.FragmentLogInBinding;

public class LogInFragment extends Fragment {

    private NavController navHost;
    private FragmentLogInBinding binding;

    public LogInFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLogInBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navHost = NavHostFragment.findNavController(this);

        binding.buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Iniciando sesion", Toast.LENGTH_SHORT).show();


                navHost.navigate(R.id.action_logInFragment_to_productChartFragment);
            }

        });


        binding.buttonRegistrarse.setOnClickListener( view1 -> {
            Toast.makeText(getContext(), "Registrar", Toast.LENGTH_SHORT).show();
        });

        binding.buttonForgotPassword.setOnClickListener( view1 -> {
            Toast.makeText(getContext(), "tenes amnesia", Toast.LENGTH_SHORT).show();
        });

    }

//    private boolean logIn(){
//
//        if(binding.editTextEmailAddress.getText().toString().isEmpty()) return false;
//        else if(binding.editTextPassword.getText().toString().isEmpty()) return false;
//        //TODO
//        //else if el mail no existe en la base de datos
//        //else if la contraseña es incorrecta
//
//        return true;
//    }
//
//    private boolean register(){
//        //TODO navegar a otra pantalla, pedir datos, no creo que lo implementemos
//        //verificar que el mail este disponible y no se repita el dni
//
//        return true;
//    }
}
