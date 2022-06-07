package com.example.tfg2.adapters;

import static com.example.tfg2.utilidades.ImagenesBlobBitmap.decodeSampledBitmapFrombyteArray;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg2.Models.Producto;
import com.example.tfg2.R;
import com.example.tfg2.utilidades.Configuracion;
import com.example.tfg2.utilidades.ImagenesFirebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdapterMyProducts extends RecyclerView.Adapter<AdapterMyProducts.ViewHolder>{

    ImageView delete_cart;
    List<Producto> myproductoList;
    Context context;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("myproducts");
    DatabaseReference database2 = FirebaseDatabase.getInstance().getReference("products_second_hand");


    final AdapterMyProducts.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Producto item);
    }


    public AdapterMyProducts(List<Producto> productoList, Context context,AdapterMyProducts.OnItemClickListener listener) {
        this.myproductoList = productoList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterMyProducts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inicializar el view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_my_products,parent,false);

        return new AdapterMyProducts.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMyProducts.ViewHolder holder, int position) {
        Producto item = myproductoList.get(position);



        //Log.i("firebasedb", "onBindViewHolder: "+ item.getImage());

        holder.list_element_txt_id_myproduct.setText(item.getIdProducto());
        holder.txt_my_product_name.setText(item.getTitle());
        holder.list_element_txt_my_product_price.setText(item.getPrice() +" €");
        holder.list_element_txt_myproduct_category.setText(item.getCategory());
       // holder.img_my_product.setImageBitmap();
        if (!item.getImage().equals("")) {
            new ImagenesFirebase().descargarFoto(new ImagenesFirebase.FotoStatus() {
                @Override
                public void FotoIsDownload(byte[] bytes) {
                    if(bytes != null) {
                        Log.i("firebasedb","foto descargada correctamente");
                        Bitmap fotob = decodeSampledBitmapFrombyteArray(bytes, Configuracion.ALTO_IMAGENES_BITMAP, Configuracion.ANCHO_IMAGENES_BITMAP);
                        holder.img_my_product.setImageBitmap(fotob);
                    }
                    else{
                        Log.i("firebasedb","foto no descargada correctamente");
                    }
                }
                @Override
                public void FotoIsUpload() {
                }
                @Override
                public void FotoIsDelete() {
                }
            },item.getImage());

        }
        else{
            Log.i("firebasedb", "onBindViewHolder: no entro" );
        }

        holder.bt_delete_my_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myproductoList.remove(item);
                database.child(user.getUid()).child(item.getIdProducto()).removeValue();
                database2.child(item.getIdProducto()).removeValue();
                notifyDataSetChanged();
            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(item);
            }
        });




    }




    @Override
    public int getItemCount() {
        return myproductoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView list_element_txt_id_myproduct, list_element_txt_myproduct_category, list_element_txt_my_product_price,txt_my_product_name;
        ImageView img_my_product;
        FloatingActionButton bt_delete_my_product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bt_delete_my_product = itemView.findViewById(R.id.bt_delete_my_product);
            list_element_txt_id_myproduct = itemView.findViewById(R.id.list_element_txt_id_myproduct);
            list_element_txt_myproduct_category = itemView.findViewById(R.id.list_element_txt_myproduct_category);
            list_element_txt_my_product_price = itemView.findViewById(R.id.list_element_txt_my_product_price);
            txt_my_product_name = itemView.findViewById(R.id.txt_my_product_name);
            img_my_product = itemView.findViewById(R.id.img_my_product);

        }




    }
}
