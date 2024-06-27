package com.example.pr_tarea3iglesiascostasroi.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//La hacemos Serializable para poder pasar datos entre fragmentos
@Entity
public class Producto implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String ingredientes;
    private double precio;
    private double gr;
    private String url;
    private boolean disponible;
    // Constructor
    public Producto(int id, String nombre, String ingredientes,double precio, double gr, String url, boolean disponible) {
        this.id = id;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.precio = precio;
        this.gr = gr;
        this.url = url;
        this.disponible = disponible;
    }

    // Getter--- Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public double getPrecio(){
        return precio;
    }

    public void setPrecio(double precio){
        this.precio = precio;
    }


    public double getGr() {
        return gr;
    }

    public void setGr(double gr) {
        this.gr = gr;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

}
