package ar.com.tpfinal.rang_store.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import ar.com.tpfinal.rang_store.MainActivity;
import ar.com.tpfinal.rang_store.databinding.RegisterBinding;
import ar.com.tpfinal.rang_store.model.Product;
import ar.com.tpfinal.rang_store.model.User;

public class RegisterUser extends Fragment {

    private RegisterBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    public RegisterUser() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = RegisterBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editTextEmailAddress.getText().toString().trim();
                String password = binding.editTextPassword.getText().toString().trim();
                progressBarOn();
                createAccount(email,password);
                progressBarOff();
            }
        });

    }


    private void createAccount(String email,String password){

        boolean flag = false;

        flag = checkEmptyFields();
        if(flag) return;
        flag = checkPasswords(password,binding.editTextPasswordConfirm.getText().toString());
        if(flag) return;
//        flag = checkExistingEmail(email);
//        if(flag) return;

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String uid;
                            try {
                                uid = mAuth.getCurrentUser().getUid();
                            }catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(requireContext(),"El email se encuentra en uso",Toast.LENGTH_SHORT).show();
                                return;
                            };

                            User user = new User(uid,
                                    binding.editTextName.getText().toString(),
                                    binding.editTextLastName.getText().toString(),
                                    email, new ArrayList<>());

                            mFirestore.collection("users").document(uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    startActivity(new Intent(requireActivity(), MainActivity.class));
                                    Toast.makeText(requireContext(),"Usuario creado con exito",Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(requireContext(),"No se pudo crear el usuario",Toast.LENGTH_SHORT).show();
                                    }
                            });
                    }
                });
        }

    private boolean checkPasswords(String pass1, String pass2){
        boolean flag = false;

        if(binding.editTextPassword.length() < 6){
            binding.editTextPassword.setError("La contraseña debe tener al menos 6 caracteres");
            binding.editTextPassword.requestFocus();

            flag = true;
        }
        if(flag) return flag;

        flag = !(pass1.equals(pass2));

        if(flag){
            binding.editTextPassword.setError("Las contraseñas no coinciden");
            binding.editTextPassword.requestFocus();
        }

        return flag;
    }

    private boolean checkEmptyFields(){

        boolean flag = false;

        if(binding.editTextName.getText().toString().isEmpty()){
            binding.editTextName.setError("Nombre obligatorio");
            binding.editTextName.requestFocus();

            flag = true;
        }

        if(binding.editTextLastName.getText().toString().isEmpty()){
            binding.editTextLastName.setError("Apellido obligatorio");
            binding.editTextLastName.requestFocus();

            flag = true;
        }

        if(binding.editTextEmailAddress.getText().toString().isEmpty()){
            binding.editTextEmailAddress.setError("Email obligatorio");
            binding.editTextEmailAddress.requestFocus();

            flag = true;
        }
        if(binding.editTextPassword.getText().toString().isEmpty()) {
            binding.editTextPassword.setError("Contraseña obligatoria");
            binding.editTextPassword.requestFocus();

            flag = true;
        }

        return flag;
    }

    private void progressBarOn(){
        binding.buttonRegistrarse.setVisibility(View.GONE);
        binding.buttonRegistrarseGoogle.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void progressBarOff(){
        binding.progressBar.setVisibility(View.GONE);
        binding.buttonRegistrarse.setVisibility(View.VISIBLE);
        binding.buttonRegistrarseGoogle.setVisibility(View.VISIBLE);
    }

}