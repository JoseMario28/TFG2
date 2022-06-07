package com.example.tfg2.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tfg2.Add_product_to_my_productsActivity;
import com.example.tfg2.HomeActivity;
import com.example.tfg2.LoadingDialog;
import com.example.tfg2.Models.Factura;
import com.example.tfg2.Models.Producto;
import com.example.tfg2.R;
import com.example.tfg2.adapters.AdapterMyProducts;
import com.example.tfg2.adapters.CartAdapter;
import com.example.tfg2.adapters.InvoiceAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyProductsFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    AdapterMyProducts adapterMyProducts;
    public static List<Producto> myproductoList = new ArrayList<>();

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference database;

    FloatingActionButton add_product_to_my_products;
    RecyclerView my_products_recyclerView;
    TextView txt_no_sell_products;

    LoadingDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference("myproducts");
        dialog = new LoadingDialog(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_products, container, false);


        add_product_to_my_products = root.findViewById(R.id.add_product_to_my_products);
        txt_no_sell_products = root.findViewById(R.id.txt_no_sell_products);

        add_product_to_my_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Add_product_to_my_productsActivity.class);
                startActivity(intent);
            }
        });

        dialog.startLoadingDialog();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //Log.i("factura", "onDataChange: " + ds);
                    if (ds.getKey().equals(user.getUid())) {
                        for (DataSnapshot nap : ds.getChildren()) {
                            //Log.i("factura", "onDataChangeee: " + nap.getValue());
                            //Log.i("factura", "asds: " + nap.child("productos").getValue());
                            Map<String, Producto> nuevoValor = new HashMap<>();
                            nuevoValor.put(nap.getKey(), nap.getValue(Producto.class));

                            //Log.i("factura", "HASHMAP " + nuevoValor.values());
                            for (Producto p : nuevoValor.values()) {
                                Log.i("factura", "Factura: "+ p.toString());
                                myproductoList.add(p);

                            }

/*

*/
                        }

                    }
                }

                //----------------------------------------RECYCLERVIEW--------------------------------------
                recyclerView = root.findViewById(R.id.my_products_recyclerView);
                layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                adapterMyProducts = new AdapterMyProducts(myproductoList,getActivity(),new AdapterMyProducts.OnItemClickListener(){
                    @Override
                    public void onItemClick(Producto item) {


                    }
                });
                recyclerView.setAdapter(adapterMyProducts);
                //-------------------------------------------------------------------------------------------


                dialog.dismissDialog();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        return root;

    }
}