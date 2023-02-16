package ar.com.tpfinal.rang_store.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.databinding.RegisterFragmentBinding;

public class UserRegister extends Fragment {

    private RegisterFragmentBinding binding;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegisterFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    private boolean register(){

        if(binding.editTextEmailAddress.getText().toString().isEmpty()){
            binding.editTextEmailAddress.setError("Email obligatorio");
            binding.editTextEmailAddress.requestFocus();
            return false;
        }
        else if(binding.editTextPassword.getText().toString().isEmpty()) {
            binding.editTextPassword.setError("Contraseña obligatoria");
            binding.editTextPassword.requestFocus();
            return false;
        }
        else if(binding.editTextPassword.length() < 5){
            binding.editTextPassword.setError("La contraseña debe tener al menos 6 caracteres");
            binding.editTextPassword.requestFocus();
            return false;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth
        //verificar mail disponible
        //verificar que no se repita dni
        //email not found
        //else if la contraseña es incorrecta

        return true;

    }


}