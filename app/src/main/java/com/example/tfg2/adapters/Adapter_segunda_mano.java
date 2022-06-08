package com.example.tfg2.adapters;

import static com.example.tfg2.utilidades.ImagenesBlobBitmap.decodeSampledBitmapFrombyteArray;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tfg2.HomeActivity;
import com.example.tfg2.Models.Producto;
import com.example.tfg2.R;
import com.example.tfg2.utilidades.Configuracion;
import com.example.tfg2.utilidades.ImagenesFirebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Adapter_segunda_mano extends RecyclerView.Adapter<Adapter_segunda_mano.ViewHolder>{

    ImageView delete_cart;
    List<Producto> second_hand_productoList;
    Context context;

    final Adapter_segunda_mano.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Producto item);
    }


    public Adapter_segunda_mano(List<Producto> productoList, Context context,Adapter_segunda_mano.OnItemClickListener listener) {
        this.second_hand_productoList = productoList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Adapter_segunda_mano.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inicializar el view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_second_hand_products,parent,false);

        return new Adapter_segunda_mano.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_segunda_mano.ViewHolder holder, int position) {

        Producto item = second_hand_productoList.get(position);
        //Log.i("firebasedb", "onBindViewHolder: "+ item.getImage());
        holder.list_element_txt_nombre_usuario_second_hand.setText(item.getNombre());
        holder.txt_second_hand_name.setText(item.getTitle());
        holder.list_element_txt_my_second_hand_price.setText(item.getPrice() +" €");
        holder.list_element_txt_second_hand_category.setText(item.getCategory());
        // holder.img_my_product.setImageBitmap();
        if (!item.getImage().equals("")) {
            new ImagenesFirebase().descargarFoto(new ImagenesFirebase.FotoStatus() {
                @Override
                public void FotoIsDownload(byte[] bytes) {
                    if(bytes != null) {
                        Log.i("firebasedb","foto descargada correctamente");
                        Bitmap fotob = decodeSampledBitmapFrombyteArray(bytes, Configuracion.ALTO_IMAGENES_BITMAP, Configuracion.ANCHO_IMAGENES_BITMAP);
                        holder.img_second_hand_product.setImageBitmap(fotob);
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

        holder.bt_add_second_hand_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getNombre().equals(HomeActivity.nombre)){
                    Toast.makeText(context, "No puedes comprar tus propios productos", Toast.LENGTH_LONG).show();
                }else{
                    HomeActivity.productoList_cart.add(item);
                    HomeActivity.imageBadgeView.setBadgeValue(HomeActivity.productoList_cart.size());
                    second_hand_productoList.remove(item);
                }



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
        return second_hand_productoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView list_element_txt_nombre_usuario_second_hand, list_element_txt_second_hand_category, list_element_txt_my_second_hand_price,txt_second_hand_name;
        ImageView img_second_hand_product;
        FloatingActionButton bt_add_second_hand_product;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bt_add_second_hand_product = itemView.findViewById(R.id.bt_add_second_hand_product);
            list_element_txt_nombre_usuario_second_hand = itemView.findViewById(R.id.list_element_txt_nombre_usuario_second_hand);
            list_element_txt_second_hand_category = itemView.findViewById(R.id.list_element_txt_second_hand_category);
            list_element_txt_my_second_hand_price = itemView.findViewById(R.id.list_element_txt_my_second_hand_price);
            txt_second_hand_name = itemView.findViewById(R.id.txt_second_hand_name);
            img_second_hand_product = itemView.findViewById(R.id.img_second_hand_product);

        }




    }


}
