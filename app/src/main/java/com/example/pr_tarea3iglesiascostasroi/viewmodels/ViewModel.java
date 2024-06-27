package com.example.pr_tarea3iglesiascostasroi.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.pr_tarea3iglesiascostasroi.room.Producto;
import com.example.pr_tarea3iglesiascostasroi.room.ProductoRepository;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    private ProductoRepository productoRepository;
    private MutableLiveData<List<Producto>> allProductos;
    private MutableLiveData<List<Producto>> availableProductos;




    public ViewModel(@NonNull Application application) {
        super(application);
        productoRepository = new ProductoRepository(application);
        allProductos = new MutableLiveData<>();
        availableProductos = new MutableLiveData<>();
        fetchAllProductos(); //recuperan datos del repositorio y actualizan LiveData
        fetchAvailableProductos(); //recuperan datos del repositorio y actualizan LiveData
    }

    public LiveData<List<Producto>> getAllProductos() {

        return allProductos;
    }

    public LiveData<List<Producto>> getAvailableProductos() {

        return availableProductos;
    }

    public void fetchAllProductos() {
        productoRepository.getAllProductos().observeForever(new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                allProductos.postValue(productos);
            }
        });
    }

    public void fetchAvailableProductos() {
        productoRepository.getAvailableProductos().observeForever(new Observer<List<Producto>>() {
            @Override
            public void onChanged(List<Producto> productos) {
                availableProductos.postValue(productos);
            }
        });
    }

    public void insertProducto(Producto producto) {
        productoRepository.insertProducto(producto);
        fetchAllProductos();
        fetchAvailableProductos();
    }

    public void updateProducto(Producto producto) {
        productoRepository.updateProducto(producto);
        fetchAllProductos();
        fetchAvailableProductos();
    }

    public void deleteProducto(Producto producto) {
        productoRepository.deleteProducto(producto);
        fetchAllProductos();
        fetchAvailableProductos();
    }



}

