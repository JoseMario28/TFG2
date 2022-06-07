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
import android.widget.ImageView;

import com.example.tfg2.Models.Producto;
import com.example.tfg2.Models.User;
import com.example.tfg2.fragments.CartFragment;
import com.example.tfg2.fragments.HomeFragment;
import com.example.tfg2.fragments.InvoiceHistoryFragment;
import com.example.tfg2.fragments.LoginFragment;
import com.example.tfg2.fragments.MyProductsFragment;
import com.example.tfg2.fragments.ProfileFragment;
import com.example.tfg2.fragments.SegundaManoFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.nikartm.support.ImageBadgeView;

public class HomeActivity extends AppCompatActivity {

    public static List<Producto> productoList_cart = new ArrayList<>();


    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    ImageView cart_image_view;

    public static ImageBadgeView imageBadgeView;
    public static String nombre;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseUser logged_user;
    FirebaseAuth auth;

    Button bt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        myRef = firebaseDatabase.getReference("User");
        logged_user = auth.getCurrentUser();


        nombre_usuario();
        //Menu derecha //--------------------------------------------------------------- //---------------------------------------------------------------
        imageBadgeView = findViewById(R.id.cart_badge_img);

        imageBadgeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar.setTitle("Cesta");
                CartFragment pf = new CartFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,pf)
                        .addToBackStack(null)
                        .commit();

            }
        });

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
                        toolbar.setTitle("Productos");
                        break;
                    case R.id.menu_setting:
                        temp = new ProfileFragment();
                        toolbar.setTitle("Perfil");
                        break;
                    case R.id.menu_logout:
                        sign_out();
                        temp = new LoginFragment();
                        break;
                    case R.id.menu_invoice_history:
                        temp = new InvoiceHistoryFragment();
                        toolbar.setTitle("Facturas");
                        break;
                    case R.id.menu_subasta:
                        temp = new SegundaManoFragment();
                        toolbar.setTitle("Segunda Mano");
                        break;
                    case R.id.menu_myProducts:
                        temp = new MyProductsFragment();
                        toolbar.setTitle("Mis prodcutos");
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

    public void nombre_usuario(){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Log.d("facil", "Hooooolaa " + snapshot.getChildrenCount());
                for(DataSnapshot ds : snapshot.getChildren()){
                    // Log.d("facil", "Valor ds "  + ds.getKey() );

                    //Log.d("facil", "Valor ds "  + ds.getValu );

                    if(ds.getKey().equals(logged_user.getUid())){


                        nombre = ds.child("nombre").getValue().toString();





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