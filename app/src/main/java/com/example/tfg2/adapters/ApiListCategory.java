package com.example.tfg2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tfg2.R;

import java.util.List;

public class ApiListCategory extends RecyclerView.Adapter<ApiListCategory.ViewHolder2> {

    List<String> categoryList;
    Context context;
    final ApiListCategory.OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onItemCategoryClick(String category);
    }


    public ApiListCategory(List<String> categoryList, Context context,ApiListCategory.OnCategoryClickListener listener) {
        this.categoryList = categoryList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inicializar el view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_category,parent,false);
        return new ViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder2 holder, int position) {

        String item = categoryList.get(position);

        holder.item_category.setText(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemCategoryClick(item);
            }
        });







    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder  {

        TextView item_category;


        public ViewHolder2(@NonNull View itemView) {
            super(itemView);

            item_category = itemView.findViewById(R.id.item_category);


        }




    }
}
