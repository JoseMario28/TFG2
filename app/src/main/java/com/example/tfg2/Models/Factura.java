package com.example.tfg2.Models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factura implements Serializable {

    String date;
    List<Producto> productos;
    double precioTotal;

    public Factura(String date, List<Producto> productos, double precioTotal) {
        this.date = date;
        this.productos = productos;
        this.precioTotal = precioTotal;
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
}
