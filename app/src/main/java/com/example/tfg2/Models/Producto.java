package com.example.tfg2.Models;

public class Producto {

    public Integer id;
    public String title;
    public String price;
    public String category;
    public String description;
    public String image;
//-----------------------------------mis productos y subasta---------------------------------------
    String nombre;
    String idProducto;

    public Producto(String nombre,String title, String price, String category, String description, String image, String idProducto) {
        this.nombre = nombre;
        this.title = title;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }
    //general un constructor con los datos de inserccion



//--------------------------------------------------------------------------------------------------------

    public Producto() {
    }

    public Producto(String title, String price, String category, String description, String image) {
        this.title = title;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
    }

    //generar los getter y setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}