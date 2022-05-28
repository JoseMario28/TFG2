package com.example.tfg2.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tfg2.HomeActivity;
import com.example.tfg2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    EditText email_login,password_login;
    FirebaseAuth auth;
    Button login_button;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() == null){
            auth.signOut();

        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        email_login = root.findViewById(R.id.email_login);
        password_login = root.findViewById(R.id.password_login);
        login_button = root.findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loguearse();
            }
        });

        return root;
    }

    public void loguearse(){

        String login_email = email_login.getText().toString().trim();
        String login_password = password_login.getText().toString().trim();

        if(login_email.isEmpty()){
            email_login.setError("El email tiene que esta lleno");
            return;
        }
        if(login_password.isEmpty()){
            password_login.setError("la contraseña tiene que estar llena");
            return;
        }

        auth.signInWithEmailAndPassword(login_email,login_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                }else{

                }
            }
        });


    }

}