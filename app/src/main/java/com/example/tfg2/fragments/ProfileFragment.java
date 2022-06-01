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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfg2.HomeActivity;
import com.example.tfg2.LoadingDialog;
import com.example.tfg2.Models.User;
import com.example.tfg2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {
    TextView profile_name,txt_password;
    EditText profile_name2, profile_last_name, profile_email;
    ImageView profile_img;
    Button btn_edit_profile,btn_save_profile;

    String pass;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseUser user;
    FirebaseAuth auth;

    LoadingDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        myRef = firebaseDatabase.getReference("User");

        dialog = new LoadingDialog(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        btn_save_profile = root.findViewById(R.id.btn_save_profile);
        btn_edit_profile = root.findViewById(R.id.btn_edit_profile);
        profile_name = root.findViewById(R.id.profile_name);
        profile_name2 = root.findViewById(R.id.profile_name2);
        profile_email = root.findViewById(R.id.profile_email);
        profile_last_name = root.findViewById(R.id.profile_last_name);
        profile_img = root.findViewById(R.id.profile_img);
        txt_password = root.findViewById(R.id.txt_password);

        Log.d("actuliza", "Actualizar " + pass);

        coger_datos_usuario_logueado();

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                E01_edit_profile();


            }
        });

        btn_save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                E02_save_profile();

                String model_profile_name2 = profile_name2.getText().toString();
                String model_profilelast_name = profile_last_name.getText().toString();
                String model_profileEmail = profile_email.getText().toString();
                String model_profile_pass = txt_password.getText().toString();


                User userModel = new User(model_profile_name2,model_profilelast_name,model_profileEmail,model_profile_pass);

                Map<String, Object> updateProfile = new HashMap<String,Object>();
                updateProfile.put(user.getUid(),userModel);

                myRef.updateChildren(updateProfile);

            }
        });

        return root;
    }


    public void coger_datos_usuario_logueado(){

dialog.startLoadingDialog();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Log.d("facil", "Hooooolaa " + snapshot.getChildrenCount());
                for(DataSnapshot ds : snapshot.getChildren()){
                    // Log.d("facil", "Valor ds "  + ds.getKey() );

                    //Log.d("facil", "Valor ds "  + ds.getValu );

                    if(ds.getKey().equals(user.getUid())){
                        profile_name.setText(ds.child("nombre").getValue().toString());
                        profile_last_name.setText(ds.child("apellidos").getValue().toString());
                        profile_name2.setText(ds.child("nombre").getValue().toString());
                        profile_email.setText(ds.child("email").getValue().toString());
                        txt_password.setText(ds.child("password").getValue().toString());

                        Picasso.get().load("https://www.w3schools.com/howto/img_avatar.png").into(profile_img);

                        dialog.dismissDialog();

                        // Log.d("facil", "Dentro if si se cumple "  + logged_user.toString() );
                        break;
                    }else{
                        Log.d("facil", "no entro");

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // return logged_user;
    }


    public void E01_edit_profile(){
        profile_last_name.setEnabled(true);
        profile_name2.setEnabled(true);
        profile_email.setEnabled(true);
        btn_edit_profile.setVisibility(View.INVISIBLE);
        btn_save_profile.setVisibility(View.VISIBLE);
    }

    public void E02_save_profile() {
        profile_last_name.setEnabled(false);
        profile_name2.setEnabled(false);
        profile_email.setEnabled(false);
        btn_edit_profile.setVisibility(View.VISIBLE);
        btn_save_profile.setVisibility(View.INVISIBLE);
    }
}