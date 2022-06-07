package com.example.tfg2.api;

import com.example.tfg2.Models.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Metodos para acceder Prueba la API
 * GET: consultar información de un recurso.
 * DELETE: eliminar un recurso determinado.
 * POST: crear un recurso nuevo.
 * PUT: modificar un recurso existente.
 * PATCH: modificar solamente un atributo de un recurso.
 */

public interface ApiService {

    //mostrar o listar todos los servicios
    //'https://fakestoreapi.com/products'
    @GET("products")
    Call<List<Producto>> getAllProducto();



    //obtener todas las categorias
    //'https://fakestoreapi.com/products/categories'
    @GET("products/categories")
    Call<List<String>> getAllCategories();


    //obtener una categoria especifica
    //'https://fakestoreapi.com/products/category/jewelery'
    @GET("products/category/{tipo}")
    Call<List<Producto>> getSelectCategories(@Path(value = "tipo") String tipo);






}
