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

import com.example.tfg2.HomeActivity;
import com.example.tfg2.Models.Factura;
import com.example.tfg2.Models.Producto;
import com.example.tfg2.R;
import com.example.tfg2.adapters.InvoiceAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class InvoiceHistoryFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    InvoiceAdapter invoiceAdapter;

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference database;

    ArrayList<Factura> invoice = new ArrayList<Factura>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference("facturas");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_invoice_history, container, false);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //Log.i("factura", "onDataChange: " + ds);
                    if (ds.getKey().equals(user.getUid())) {
                        for (DataSnapshot nap : ds.getChildren()) {
                            //Log.i("factura", "onDataChangeee: " + nap.getValue());
                            //Log.i("factura", "asds: " + nap.child("productos").getValue());
                            Map<String, Factura> nuevoValor = new HashMap<>();
                            nuevoValor.put(nap.getKey(), nap.getValue(Factura.class));

                            //Log.i("factura", "HASHMAP " + nuevoValor.values());
                            for (Factura f : nuevoValor.values()) {
                                Log.i("factura", "Factura: "+ f.toString());
                                invoice.add(f);
                            }

/*
                        for (DataSnapshot a: nap.child("productos").getChildren()){
                            ArrayList<Object> p =  new ArrayList<>();
                            Log.i("factura", "onDataChange: " + a);
                            Log.i("factura", "asdasdasfasfas: " + a.child("image").getValue());
                        }



                        //Factura f = new Factura(nap.child("date").getValue(),nap.child("precioTotal").getValue(),nap.child("productos"))
                    }
                        //Log.i("factura", "onDataChange: " + ds.getValue());


                        HashMap<String, Factura> facturas = new HashMap<String,Factura>();
                        facturas = ds.getValue(Factura.class);
                        Log.i("factura", "onDataChange: " + facturas);

                        for (String clave: facturas.keySet()) {
                            Log.i("factura", "onDataChange: " + clave);
                            for (Factura factura: facturas.values()) {
                                facturas.values().;
                                Log.i("factura", "onDataChange: "+ factura);

                                invoice.add(new Factura( clave,factura.getDate(),factura.getProductos(),factura.getPrecioTotal()));
                            }
                        }

                        Log.i("factura", "onDataChange: "+ invoice);



                        //facturas = (ArrayList<Factura>) ds.getValue();
                        Log.i("invoice", "onDataChange: " + facturas.values());
*/
                        }

                    }
                }


                //----------------------------------------RECYCLERVIEW--------------------------------------
                recyclerView = root.findViewById(R.id.invoice_history_rv);
                layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);

                invoiceAdapter = new InvoiceAdapter(invoice,getActivity(),new InvoiceAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(Factura item) {


                    }
                });
                recyclerView.setAdapter(invoiceAdapter);

                //-----------------------------------------------------------------------------------------
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        return root;
    }
}