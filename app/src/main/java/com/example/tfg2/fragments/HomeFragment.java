package com.example.tfg2.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tfg2.Models.Producto;
import com.example.tfg2.R;
import com.example.tfg2.adapters.ApiListAdapter;
import com.example.tfg2.api.ApiCliente;
import com.example.tfg2.api.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    List<Producto> product_list = new ArrayList<>();
    List<String> categoriesList = new ArrayList<>();

    ApiListAdapter apiListAdapter;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root  = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        cargar_prodcutos_api();



        return root;
    }

    //Coger productos de la api y cargarlos en el recyclerView

    public void cargar_categorias_api(){
        ApiService apiService = ApiCliente.getCliente().create(ApiService.class);

        Call<List<String>> listCall = apiService.getAllCategories();

        listCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(), "Error en la conexion", Toast.LENGTH_SHORT).show();
                }
                //cargar la lista de elemento en la lista
                categoriesList = response.body();

                // apiListAdapter = new ApiListAdapter(product_list,getActivity());
                // recyclerView.setAdapter(apiListAdapter);


            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

                //ha fallado la conexion
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("info", "onFailure: "+t.getMessage());

            }
        });
    }
    
    public void cargar_prodcutos_api(){

        ApiService apiService = ApiCliente.getCliente().create(ApiService.class);

        Call<List<Producto>> listCall = apiService.getAllProducto();

        listCall.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(), "Error en la conexion", Toast.LENGTH_SHORT).show();
                }
                //cargar la lista de elemento en la lista
                product_list = response.body();

                apiListAdapter = new ApiListAdapter(product_list,getActivity());
                recyclerView.setAdapter(apiListAdapter);



            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {

                //ha fallado la conexion
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("info", "onFailure: "+t.getMessage());

            }
        });
    }
}