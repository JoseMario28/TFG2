package com.example.tfg2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tfg2.Models.User;
import com.example.tfg2.fragments.HomeFragment;
import com.example.tfg2.fragments.LoginFragment;
import com.example.tfg2.fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    FirebaseUser logged_user;
    FirebaseAuth auth;

    Button bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        logged_user = auth.getCurrentUser();

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("usuario_logueado");
        Log.d("intent","aaaaaaaa " + user);

        //Menu derecha //--------------------------------------------------------------- //---------------------------------------------------------------
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.menu);
        drawerLayout = findViewById(R.id.drawer);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
        navigationView.setCheckedItem(R.id.menu_home);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment temp;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        temp = new HomeFragment();
                        break;
                    case R.id.menu_setting:
                        temp = new ProfileFragment();
                        break;
                    case R.id.menu_logout:
                        sign_out();
                        temp = new LoginFragment();
                        Log.d("logueado", "onNavigationItemSelected: " + logged_user);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,temp).commit();
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
    }

    public void sign_out(){
        auth.signOut();
        Intent intent = new Intent(navigationView.getContext(),MainActivity.class);
        startActivity(intent);
    }
    //--------------------------------------------------------------- //---------------------------------------------------------------

        /*
        bt = findViewById(R.id.btn_cerrar);
        auth = FirebaseAuth.getInstance();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrar_sesion();

            }
        });
    }

    public void cerrar_sesion(){
        auth.signOut();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

         */
}