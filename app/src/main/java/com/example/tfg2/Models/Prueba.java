package com.example.tfg2.Models;

import java.io.Serializable;
import java.util.List;

public class Prueba implements Serializable {

    String id;
    List<Factura> data;

    public Prueba(String id, List<Factura> data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Prueba{" +
                "id='" + id + '\'' +
                ", data=" + data +
                '}';
    }

    public Prueba() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Factura> getData() {
        return data;
    }

    public void setData(List<Factura> data) {
        this.data = data;
    }
}
