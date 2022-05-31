package com.example.tfg2.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tfg2.Models.Producto;
import com.example.tfg2.R;
import com.example.tfg2.fragments.ProfileFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ApiListAdapter extends RecyclerView.Adapter<ApiListAdapter.ViewHolder> {

    List<Producto> productoList;
    Context context;



    public ApiListAdapter(List<Producto> productoList, Context context) {
        this.productoList = productoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inicializar el view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Producto item = productoList.get(position);

        holder.list_element_txt_titulo.setText(item.getTitle());
        holder.list_element_txt_precio.setText(item.getPrice() +" €");
        holder.list_element_txt_descripcion.setText(item.getCategory());

        holder.bt_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




        //tratamiento de la imagen con glide
        Glide.with(context)
                .load(item.getImage())
                .into(holder.list_element_img);

    }

    @Override
    public int getItemCount() {
        return productoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView list_element_txt_titulo, list_element_txt_descripcion, list_element_txt_precio;
        ImageView list_element_img;
        FloatingActionButton bt_add_cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bt_add_cart = itemView.findViewById(R.id.bt_add_cart);
            list_element_txt_titulo = itemView.findViewById(R.id.list_element_txt_titulo);
            list_element_txt_descripcion = itemView.findViewById(R.id.list_element_txt_descripcion);
            list_element_txt_precio = itemView.findViewById(R.id.list_element_txt_precio);
            list_element_img = itemView.findViewById(R.id.list_element_img);

        }




    }
}