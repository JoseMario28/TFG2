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
 * Metodos para acceder a la API
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

    //buscar y mostrar un producto
    //'https://fakestoreapi.com/products/1'
    @GET("products/{id}")
    Call<Producto> getProduct(@Path("id") int id);

    //mostrar una cantidad de productos
    //'https://fakestoreapi.com/products?limit=5'
    @GET("products")
    Call<List<Producto>> getLimit(
            @Query("limit") Integer limit);

    //mostrar los productos ordenados
    //'https://fakestoreapi.com/products?sort=desc'
    @GET("products")
    Call<List<Producto>> getProductoOrdenado(
            @Query("sort") String sort);


    //obtener todas las categorias
    //'https://fakestoreapi.com/products/categories'
    @GET("products/categories")
    Call<List<String>> getAllCategories();


    //obtener una categoria especifica
    //'https://fakestoreapi.com/products/category/jewelery'
    //este no esta realizado
    @GET("products/categories")
    Call<List<Producto>> getSelectCategories(@Query("categories") String categories);

    //añadir un producto
    //https://fakestoreapi.com/products'
    @POST("products")
    Call<Producto> createProducto(@Body Producto producto);

    //otra forma de añadir, es indicando los campos a rellenar
    @FormUrlEncoded
    @POST("products")
    Call<Producto> createProductoForm(
            @Field("title") String title,
            @Field("price") String price,
            @Field("description") String description
    );

    //modificar un producto
    //'https://fakestoreapi.com/products/7'
    @PUT("products/{id}")
    Call<Producto> putProducto(@Path("id") int id, @Body Producto producto);

    @PATCH("products/{id}")
    Call<Producto> patchProducto(@Path("id") int id, @Body Producto producto);

    //borrar producto
    @DELETE("products/{id}")
    Call<Void> deleteProducto(@Path("id") int id);




}
