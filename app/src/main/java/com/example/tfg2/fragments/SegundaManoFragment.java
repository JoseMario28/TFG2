package com.example.tfg2.fragments;

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

import com.example.tfg2.HomeActivity;
import com.example.tfg2.LoadingDialog;
import com.example.tfg2.Models.Producto;
import com.example.tfg2.R;
import com.example.tfg2.adapters.AdapterMyProducts;
import com.example.tfg2.adapters.Adapter_segunda_mano;
import com.example.tfg2.adapters.CartAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SegundaManoFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    Adapter_segunda_mano adapter_segunda_mano;
    public static List<Producto> productoList_second_hand = new ArrayList<>();

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference database;




    LoadingDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference("products_second_hand");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_segunda_mano, container, false);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productoList_second_hand.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //Log.i("factura", "onDataChange: " + ds);
                    Map<String, Producto> nuevoValor = new HashMap<>();
                    nuevoValor.put(ds.getKey(), ds.getValue(Producto.class));

                    //Log.i("factura", "HASHMAP " + nuevoValor.values());
                    for (Producto p : nuevoValor.values()) {
                        Log.i("carrito", "Base de datos " + p);
                        if (HomeActivity.productoList_cart.size() > 0) {
                            for (Producto carrito : HomeActivity.productoList_cart) {
                                Log.i("carrito", "onDataChange: " + carrito);
                                if (p.getIdProducto() == carrito.getIdProducto()) {
                                    Log.i("else", "onDataChange: asdasd");

                                } else {

                                    productoList_second_hand.add(p);
                                    break;
                                }
                            }
                        } else {
                            productoList_second_hand.add(p);
                        }

                    }

                }

                //----------------------------------------RECYCLERVIEW--------------------------------------
                recyclerView = root.findViewById(R.id.segunda_mano_rv);
                layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);

                adapter_segunda_mano = new Adapter_segunda_mano(productoList_second_hand,getActivity(),new Adapter_segunda_mano.OnItemClickListener(){
                    @Override
                    public void onItemClick(Producto item) {


                    }
                });
                recyclerView.setAdapter(adapter_segunda_mano);
                //-------------------------------------------------------------------------------------------
                Log.i("jabali", "onCreateView: " + adapter_segunda_mano.getItemCount());
                Log.i("carrito", "CARRITO " + HomeActivity.productoList_cart);
                Log.i("aaaaaaaaaaaaaaaaaaaa", "PRODUCTOS " + productoList_second_hand);
                //Log.i("aaaaaaaaaaaaaaaaaaaaaaa", "onDataChange: " + Adapter_segunda_mano.second_hand_productoList);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return root;
    }
}