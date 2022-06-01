package com.example.tfg2.fragments;

import android.content.Intent;
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
import com.example.tfg2.adapters.AdapterFragment;
import com.example.tfg2.adapters.ApiListAdapter;
import com.example.tfg2.adapters.ApiListCategory;
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

    String category;
    ApiListCategory apiListCategory;
    ApiListAdapter apiListAdapter;

    LinearLayoutManager layoutManager;
    LinearLayoutManager layoutManagerCategory;

    RecyclerView recyclerView;
    RecyclerView categoryRecyclerView;


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
        categoryRecyclerView = root.findViewById(R.id.category_recyclerView);

        layoutManagerCategory = new LinearLayoutManager(getContext());
        categoryRecyclerView.setLayoutManager(layoutManagerCategory);
        layoutManagerCategory.setOrientation(RecyclerView.HORIZONTAL);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        cargar_prodcutos_api();
        cargar_categorias_api();


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
                categoriesList.add(new String("Todas"));
                apiListCategory = new ApiListCategory(categoriesList,getActivity(),new ApiListCategory.OnCategoryClickListener(){
                    @Override
                    public void onItemCategoryClick(String category) {



                                cargar_productos_por_categoria(category);





                    }
                });
                categoryRecyclerView.setAdapter(apiListCategory);


            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

                //ha fallado la conexion
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("productos", "onFailure: "+t.getMessage());

            }
        });
    }

    public void cargar_productos_por_categoria(String category){
        if(category.equals("Todas")){
            cargar_prodcutos_api();

        }else {

            ApiService apiService = ApiCliente.getCliente().create(ApiService.class);

            Call<List<Producto>> listCall = apiService.getSelectCategories(category);

            listCall.enqueue(new Callback<List<Producto>>() {
                @Override
                public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {

                    if (!response.isSuccessful()) {
                        Toast.makeText(getActivity(), "Error en la conexion", Toast.LENGTH_SHORT).show();
                        Log.i("producto", "onResponse: " + "errror");
                    }
                    //cargar la lista de elemento en la lista
                    product_list = response.body();
                    Log.i("productos", "DESPUES : " + product_list.toString());


                    apiListAdapter = new ApiListAdapter(product_list, getActivity(), new ApiListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Producto item) {

                        }
                    });
                    recyclerView.setAdapter(apiListAdapter);


                }

                @Override
                public void onFailure(Call<List<Producto>> call, Throwable t) {

                    //ha fallado la conexion
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("info", "onFailure: " + t.getMessage());

                }
            });
        }
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

                apiListAdapter = new ApiListAdapter(product_list, getActivity(), new ApiListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Producto item) {
                        moveToDescription();
                    }
                });
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

    public void moveToDescription(){

        ProfileFragment pf = new ProfileFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,pf)
                .addToBackStack(null)
                .commit();

    }
}