package com.example.tfg2.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tfg2.HomeActivity;
import com.example.tfg2.Models.Factura;
import com.example.tfg2.Models.Producto;
import com.example.tfg2.R;
import com.example.tfg2.fragments.FacturaDetailsFragment;
import com.example.tfg2.fragments.ProductDetailsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder>{


    List<Factura> invoiceList;
    Context context;

    final InvoiceAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Factura item);
    }


    public InvoiceAdapter(List<Factura> invoiceList, Context context,InvoiceAdapter.OnItemClickListener listener) {
        this.invoiceList = invoiceList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public InvoiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inicializar el view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_invoice_history,parent,false);

        return new InvoiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceAdapter.ViewHolder holder, int position) {

        Factura item = invoiceList.get(position);

        holder.list_element_txt_id_factura.setText(item.getId());
        holder.list_element_txt_fecha_factura.setText(item.getDate());
        holder.list_element_txt_precio_total.setText(item.getPrecioTotal()+" €");
        holder.list_element_txt_total_products.setText(String.valueOf(item.getProductos().size()));

        holder.bt_details_products_invoice_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle datosAEnviar = new Bundle();

                datosAEnviar.putSerializable("factura",item);

                FacturaDetailsFragment fd = new FacturaDetailsFragment();
                fd.setArguments(datosAEnviar);






            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




    }

    @Override
    public int getItemCount() {
        return invoiceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        TextView list_element_txt_id_factura, list_element_txt_fecha_factura, list_element_txt_precio_total,list_element_txt_total_products;

        FloatingActionButton bt_details_products_invoice_history;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bt_details_products_invoice_history = itemView.findViewById(R.id.bt_details_products_invoice_history);
            list_element_txt_id_factura = itemView.findViewById(R.id.list_element_txt_id_factura);
            list_element_txt_fecha_factura = itemView.findViewById(R.id.list_element_txt_fecha_factura);
            list_element_txt_precio_total = itemView.findViewById(R.id.list_element_txt_precio_total);
            list_element_txt_total_products = itemView.findViewById(R.id.list_element_txt_total_products);



        }




    }

}
