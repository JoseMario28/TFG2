package com.example.tfg2.Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factura implements Serializable {

    public String id;
    public String date;
    public List<Producto> productos;
    public double precioTotal;

    public Factura() {
    }

    public Factura(String id, String date, List<Producto> productos, double precioTotal) {
        this.id = id;
        this.date = date;
        this.productos = productos;
        this.precioTotal = precioTotal;
    }

    public Factura(String date, List<Producto> productos, double precioTotal) {
        this.date = date;
        this.productos = productos;
        this.precioTotal = precioTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", productos=" + productos +
                ", precioTotal=" + precioTotal +
                '}';
    }
}
