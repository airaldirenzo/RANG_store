package ar.com.tpfinal.rang_store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.databinding.LogInBinding;

public class LogIn extends AppCompatActivity{

    private NavController navHost;
    private LogInBinding binding;

    public LogIn() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            reload();
//        }
//    }

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

                logIn();
                //navHost.navigate(R.id.action_logInFragment_to_productChartFragment);

            }

        });

        binding.buttonIniciarSesionGoogle.setOnClickListener(view1 -> {


        });

        binding.buttonRegistrarse.setOnClickListener( view1 -> {
            navHost.navigate(R.id.action_logInFragment_to_registerUser3);
        });

        binding.buttonForgotPassword.setOnClickListener( view1 -> {
            Toast.makeText(getContext(), "tenes amnesia", Toast.LENGTH_SHORT).show();
        });

    }

    private void logIn(){

        if(binding.editTextEmailAddress.getText().toString().isEmpty()){
            binding.editTextEmailAddress.setError("Email obligatorio");
            binding.editTextEmailAddress.requestFocus();
            return;
        }
        else if(binding.editTextPassword.getText().toString().isEmpty()) {
            binding.editTextPassword.setError("Contraseña obligatoria");
            binding.editTextPassword.requestFocus();
            return;
        }
        //email not found
        //else if la contraseña es incorrecta

//        mAuth.signInWithEmailAndPassword(binding.editTextEmailAddress.getText().toString(), binding.editTextPassword.getText().toString())
//                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
//                    //TODO Ojo con este warning
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Toast.makeText(getContext(), "Inicio de sesion exitoso", Toast.LENGTH_LONG).show();
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            navHost.navigate(R.id.action_logInFragment_to_productChartFragment);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(getContext(), "No se pudo iniciar sesion", Toast.LENGTH_LONG).show();
//                            //updateUI(null);
//                        }
//                    }
//
//                });

    }

}