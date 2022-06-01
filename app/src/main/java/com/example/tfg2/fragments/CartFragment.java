package com.example.tfg2.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tfg2.HomeActivity;
import com.example.tfg2.Models.Producto;
import com.example.tfg2.R;
import com.example.tfg2.adapters.ApiListCategory;
import com.example.tfg2.adapters.CartAdapter;


public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    CartAdapter cartAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = root.findViewById(R.id.cart_recyclerview);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        cartAdapter = new CartAdapter(HomeActivity.productoList_cart,getActivity(),new CartAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Producto item) {


            }
        });
        recyclerView.setAdapter(cartAdapter);



        return root;
    }
}