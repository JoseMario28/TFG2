package com.example.tfg2.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    CartAdapter cartAdapter;
    ExtendedFloatingActionButton bt_finalizar_compra;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;

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


                Factura factura = new Factura(fecha,HomeActivity.productoList_cart,precio_total_factura);

                database.getReference().child("facturas").child(user.getUid()).push().setValue(factura);
                Log.i("factura", "onClick: " + todaysDate);

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