package com.example.tfg2.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfg2.HomeActivity;
import com.example.tfg2.Models.Factura;
import com.example.tfg2.Models.Producto;
import com.example.tfg2.R;
import com.example.tfg2.adapters.ApiListCategory;
import com.example.tfg2.adapters.CartAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    CartAdapter cartAdapter;

    ExtendedFloatingActionButton bt_finalizar_compra;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;

    Map<String, Object> updateProduct = new HashMap<String,Object>();


    LocalDate todaysDate;
    double precio_total_factura = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        bt_finalizar_compra = root.findViewById(R.id.bt_finalizar_compra);
        bt_finalizar_compra.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                precio_total_factura = calcular_precio_total();
                String fecha = String.valueOf(todaysDate = LocalDate.now());
                String id= database.getReference().push().getKey();

                Factura factura = new Factura(id,fecha,HomeActivity.productoList_cart,precio_total_factura);

                database.getReference().child("facturas").child(user.getUid()).child(id).setValue(factura);
                Log.i("factura", "onClick: " + todaysDate);


                for (Producto p : HomeActivity.productoList_cart) {

                    if (p.getIdProducto() != null){
                        database.getReference().child("products_second_hand").child(p.getIdProducto()).removeValue();
                        for (Producto my_p : MyProductsFragment.myproductoList) {
                            my_p.setVendido(true);
                        }
                        //updateProduct.put(p.getIdProducto(),p);
                        //database.getReference().child("myproducts").child(user.getUid()).updateChildren(updateProduct);

                    }

                }

                if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 1);
                }

               // SmsManager smsManager = SmsManager.getDefault();
               // smsManager.sendTextMessage("687581839",null,"Eres un bobo, estoy probando lo del sms de la factura btw <3",null,null);


                Intent intent = new Intent(getActivity(),HomeActivity.class);
                startActivity(intent);

                HomeActivity.productoList_cart.clear();


            }
        });


        //----------------------------------------RECYCLERVIEW--------------------------------------
        recyclerView = root.findViewById(R.id.cart_recyclerview);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        cartAdapter = new CartAdapter(HomeActivity.productoList_cart,getActivity(),new CartAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Producto item) {


            }
        });
        recyclerView.setAdapter(cartAdapter);
        //-------------------------------------------------------------------------------------------


        return root;
    }

    public double calcular_precio_total(){
        double precioTotal = 0;

        for (Producto p :HomeActivity.productoList_cart) {

            precioTotal = precioTotal + Double.valueOf(p.getPrice());
            Math.round(precioTotal);
        }

        return  precioTotal;
    }


}