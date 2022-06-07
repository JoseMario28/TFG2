package com.example.tfg2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tfg2.HomeActivity;
import com.example.tfg2.Models.Producto;
import com.example.tfg2.R;
import com.example.tfg2.fragments.SegundaManoFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    ImageView delete_cart;
    List<Producto> productoList_cart;
    Context context;
    final CartAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Producto item);
    }


    public CartAdapter(List<Producto> productoList, Context context,CartAdapter.OnItemClickListener listener) {
        this.productoList_cart = productoList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inicializar el view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_element,parent,false);
        delete_cart = view.findViewById(R.id.bt_delete_products_cart);
        view.findViewById(R.id.bt_add_cart).setVisibility(View.INVISIBLE);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {

        Producto item = productoList_cart.get(position);

        holder.list_element_txt_titulo.setText(item.getTitle());
        holder.list_element_txt_precio.setText(item.getPrice() +" €");
        holder.list_element_txt_descripcion.setText(item.getCategory());

        holder.bt_delete_product_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.productoList_cart.remove(item);
                HomeActivity.imageBadgeView.setBadgeValue(HomeActivity.productoList_cart.size());
                if(item.getIdProducto() != null){
                    SegundaManoFragment.productoList_second_hand.add(item);
                }
                Log.i("carrito", "onClick: " + HomeActivity.productoList_cart.toString());
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(item);
            }
        });


        //tratamiento de la imagen con glide
        Glide.with(context)
                .load(item.getImage())
                .into(holder.list_element_img);

    }

    @Override
    public int getItemCount() {
        return productoList_cart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView list_element_txt_titulo, list_element_txt_descripcion, list_element_txt_precio;
        ImageView list_element_img;
        FloatingActionButton bt_delete_product_cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bt_delete_product_cart = itemView.findViewById(R.id.bt_delete_products_cart);
            list_element_txt_titulo = itemView.findViewById(R.id.list_element_txt_titulo);
            list_element_txt_descripcion = itemView.findViewById(R.id.list_element_txt_descripcion);
            list_element_txt_precio = itemView.findViewById(R.id.list_element_txt_precio);
            list_element_img = itemView.findViewById(R.id.list_element_img);


        }




    }

}
