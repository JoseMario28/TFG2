package com.example.tfg2.fragments;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tfg2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {

   Button register_button;
   EditText name_register, email_register, password_register;
   TextView singin;
   FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user =  auth.getCurrentUser();
        Log.d("TAG", "User " + user);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View root = inflater.inflate(R.layout.fragment_register, container, false);
        register_button =root.findViewById(R.id.register_button);
        name_register = root.findViewById(R.id.name_register);
        email_register = root.findViewById(R.id.email_register);
        password_register = root.findViewById(R.id.password_register);
        singin = root.findViewById(R.id.sign_in_button);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar_usuario();
            }
        });

        return root;
    }

    private void registrar_usuario(){

        String user_name = name_register.getText().toString();
        String user_email = email_register.getText().toString().trim();
        String user_password = password_register.getText().toString().trim();

        Log.d("user", user_name + " password " + user_password);

        if(user_name.isEmpty()){
            name_register.setError("El campo no puede estar vacio");
            return;
        }
        if(user_email.isEmpty()){
            email_register.setError("El campo no puede estar vacio");
            return;
        }
        if(user_password.isEmpty()){
            password_register.setError("El campo no puede estar vacio");
            return;
        }
        if(user_password.length() < 4){
            password_register.setError("La contraseña debe tener al menos 4 caracteres");
            return;
        }

        //Creacion de Usuario
        auth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(),"Registro Completado con exito", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getActivity(), "ERROR ", Toast.LENGTH_SHORT).show();
                    Log.e("ERRRROR", "onComplete: ", task.getException() );
                }
            }

        });

    }
}