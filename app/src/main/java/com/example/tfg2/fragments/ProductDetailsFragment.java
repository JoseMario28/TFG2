package com.example.tfg2.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tfg2.Models.Producto;
import com.example.tfg2.R;


public class ProductDetailsFragment extends Fragment {

    TextView product_details_nombre,product_details_category,product_details_price,product_details_descripcion;
    ImageView product_details_img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product_details, container, false);

        product_details_nombre = root.findViewById(R.id.product_details_nombre);
        product_details_category = root.findViewById(R.id.product_details_category);
        product_details_price = root.findViewById(R.id.product_details_price);
        product_details_descripcion = root.findViewById(R.id.product_details_descripcion);
        product_details_img = root.findViewById(R.id.product_details_img);


        Bundle datosRecuperaos= getArguments();

        if (datosRecuperaos == null){
            Toast.makeText(getActivity(), "No hay datos ", Toast.LENGTH_SHORT).show();
        }

        Producto p = (Producto) datosRecuperaos.getSerializable("producto");

        product_details_nombre.setText(p.getTitle());
        product_details_category.setText(p.getCategory());
        product_details_price.setText(p.getPrice());
        product_details_descripcion.setText(p.getDescription());

        Glide.with(this)
                .load(p.getImage())
                .into(product_details_img);

        Log.i("bundle", "onCreateView: " + p);

        return root;
    }
}