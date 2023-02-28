package ar.com.tpfinal.rang_store.fragments.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ar.com.tpfinal.rang_store.MainActivity;
import ar.com.tpfinal.rang_store.R;
import ar.com.tpfinal.rang_store.databinding.FragmentLogInBinding;

public class LogInFragment extends Fragment {

    private FragmentLogInBinding binding;
    private FirebaseAuth mAuth;
    private NavController navHost;

    public LogInFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        navHost = NavHostFragment.findNavController(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(requireActivity(), MainActivity.class));
            requireActivity().finish();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogInBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.buttonIniciarSesion.setOnClickListener(view1 -> {
            String email = binding.editTextEmailAddress.getText().toString().trim();
            String password = binding.editTextPassword.getText().toString().trim();
            logIn(email,password);

        });

        binding.buttonIniciarSesionGoogle.setOnClickListener(view1 -> Toast.makeText(requireContext(), "Not implemented", Toast.LENGTH_SHORT).show());

        binding.buttonRegistrarse.setOnClickListener( view1 -> navHost.navigate(R.id.action_logInFragment_to_registerUserFragment));

        binding.buttonForgotPassword.setOnClickListener( view1 -> Toast.makeText(requireContext(), "Not implemented", Toast.LENGTH_SHORT).show());

    }

    private void logIn(String email, String password){

        progressBarOn();

        if(email.isEmpty()){
            binding.editTextEmailAddress.setError("Email obligatorio");
            binding.editTextEmailAddress.requestFocus();

            progressBarOff();

            return;
        }
        else if(password.isEmpty()) {
            binding.editTextPassword.setError("Contraseña obligatoria");
            binding.editTextPassword.requestFocus();

            progressBarOff();

            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            progressBarOff();

                            startActivity(new Intent(requireActivity(), MainActivity.class));

                            Toast.makeText(requireContext(), "Bienvenido", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(requireContext(),"Error al inciar sesion",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressBarOff();

                        e.printStackTrace();
                    }
                });
    }

    private void progressBarOn(){
        binding.buttonIniciarSesion.setVisibility(View.GONE);
        binding.buttonIniciarSesionGoogle.setVisibility(View.GONE);
        binding.buttonRegistrarse.setVisibility(View.GONE);
        binding.buttonForgotPassword.setVisibility(View.GONE);
        binding.progressBarLogIn.setVisibility(View.VISIBLE);
    }

    private void progressBarOff(){
        binding.buttonIniciarSesion.setVisibility(View.VISIBLE);
        binding.buttonIniciarSesionGoogle.setVisibility(View.VISIBLE);
        binding.buttonRegistrarse.setVisibility(View.VISIBLE);
        binding.buttonForgotPassword.setVisibility(View.VISIBLE);
        binding.progressBarLogIn.setVisibility(View.GONE);
    }

}