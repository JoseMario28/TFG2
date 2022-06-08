package com.example.tfg2.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tfg2.HomeActivity;
import com.example.tfg2.LoadingDialog;
import com.example.tfg2.Models.Producto;
import com.example.tfg2.R;
import com.example.tfg2.adapters.AdapterFragment;
import com.example.tfg2.adapters.Adapter_segunda_mano;
import com.example.tfg2.adapters.ApiListAdapter;
import com.example.tfg2.adapters.ApiListCategory;
import com.example.tfg2.api.ApiCliente;
import com.example.tfg2.api.ApiService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    List<Producto> product_list = new ArrayList<>();
    public static List<String> categoriesList = new ArrayList<>();

    String category;
    ApiListCategory apiListCategory;
    ApiListAdapter apiListAdapter;

    LinearLayoutManager layoutManager;
    LinearLayoutManager layoutManagerCategory;

    RecyclerView recyclerView;
    RecyclerView categoryRecyclerView;

    LoadingDialog dialog;

    DatabaseReference database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new LoadingDialog(getActivity());
        database = FirebaseDatabase.getInstance().getReference("products_second_hand");
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
        cargar_productos_segundamano();

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

    public void cargar_productos_segundamano(){
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SegundaManoFragment.productoList_second_hand.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //Log.i("factura", "onDataChange: " + ds);
                    Map<String, Producto> nuevoValor = new HashMap<>();
                    nuevoValor.put(ds.getKey(), ds.getValue(Producto.class));

                    //Log.i("factura", "HASHMAP " + nuevoValor.values());
                    for (Producto p : nuevoValor.values()) {
                        Log.i("carrito", "Base de datos " + p);
                        if (HomeActivity.productoList_cart.size() > 0) {
                            for (Producto carrito : HomeActivity.productoList_cart) {
                                Log.i("carrito", "onDataChange: " + carrito);
                                if (p.getIdProducto() == carrito.getIdProducto()) {
                                    Log.i("else", "onDataChange: asdasd");

                                } else {

                                    SegundaManoFragment.productoList_second_hand.add(p);
                                    break;
                                }
                            }
                        } else {
                            SegundaManoFragment.productoList_second_hand.add(p);
                        }

                    }

                }


                //-------------------------------------------------------------------------------------------
               // Log.i("jabali", "onCreateView: " + adapter_segunda_mano.getItemCount());
                //Log.i("carrito", "CARRITO " + HomeActivity.productoList_cart);
                //Log.i("aaaaaaaaaaaaaaaaaaaa", "PRODUCTOS " + productoList_second_hand);
                //Log.i("aaaaaaaaaaaaaaaaaaaaaaa", "onDataChange: " + Adapter_segunda_mano.second_hand_productoList);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void cargar_productos_por_categoria(String category){

        if(category.equals("Todas")){

            cargar_prodcutos_api();



        }else {
            dialog.startLoadingDialog();
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

                    dialog.dismissDialog();
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

        dialog.startLoadingDialog();


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
                        moveToDescription(item);
                    }
                });
                recyclerView.setAdapter(apiListAdapter);

                dialog.dismissDialog();


            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {

                //ha fallado la conexion
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("info", "onFailure: "+t.getMessage());

            }
        });


    }

    public void moveToDescription(Producto item){
        Bundle datosAEnviar = new Bundle();

        datosAEnviar.putSerializable("producto",item);
        ProductDetailsFragment pf = new ProductDetailsFragment();

        pf.setArguments(datosAEnviar);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, pf);
        fragmentTransaction.addToBackStack(null);

// Terminar transición y nos vemos en el fragmento de destino
        fragmentTransaction.commit();

    }
}