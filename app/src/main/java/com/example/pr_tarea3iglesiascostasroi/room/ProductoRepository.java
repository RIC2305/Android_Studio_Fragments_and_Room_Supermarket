package com.example.pr_tarea3iglesiascostasroi.room;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ProductoRepository {
    private final ProductoDatabase.ProductoDao productoDao;
    private final LiveData<List<Producto>> allProductos;
    private final LiveData<List<Producto>> availableProductos;
    private final Executor executor;;

    public ProductoRepository(Context context) {
        ProductoDatabase productoDatabase = ProductoDatabase.getInstance(context);
        productoDao = productoDatabase.productoDao();
        allProductos = productoDao.getAllProduct();
        availableProductos = productoDao.getAllAvailableProduct();
        executor = Executors.newSingleThreadExecutor(); //Sólo un hilo
        // Usamos Executor.newSingleThreadExecutor() para crear un Executor. Distintos hilos
    }

    public LiveData<List<Producto>> getAllProductos() {
        return allProductos;
    }

    public LiveData<List<Producto>> getAvailableProductos() {
        return availableProductos;
    }

    public void insertProducto(Producto producto) {
        executor.execute(new Runnable() { // Ejecutamos la tarea usando el Executor
            @Override
            public void run() {
                productoDao.insertProducto(producto);
            }
        });
    }

    public void updateProducto(Producto producto) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                productoDao.updateProducto(producto);
            }
        });
    }

    public void deleteProducto(Producto producto) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                productoDao.deleteProducto(producto);
            }
        });
    }

    //OJO CON LO SIGUIENTE..PRUEBAS
    public void insertarDatosIniciales() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                limpiarBaseDeDatos();
                // Inserta los datos iniciales aquí
                 Producto producto1 = new Producto(0, "Jamón Bellota Ibérico", "Jamón Curado", 12.99, 200.00, "https://cdn.grupoelcorteingles.es/SGFM/dctm/MEDIA03/201702/22/00113300700302____12__640x640.jpg", true);
                Producto producto2 = new Producto(0, "Estuche Lomo", "Lomo,Chorizo,Salchichón", 59.95, 500.00, "https://cdn.grupoelcorteingles.es/SGFM/dctm/MEDIA03/202001/31/00113302801082____3__640x640.jpg", true);
                Producto producto3 = new Producto(0, "Joselito", "Lomo Media Caña, Estuche", 59.99, 500.00, "https://cdn.grupoelcorteingles.es/SGFM/dctm/MEDIA03/202211/15/00113302800795____16__640x640.jpg", false);
                Producto producto4 = new Producto(0, "Cofre Gourmet", "Jamón,Presa, Lomo", 49.99, 0.1, "https://cdn.grupoelcorteingles.es/SGFM/dctm/MEDIA03/202310/26/00113302800944____16__640x640.jpg", true);
                Producto producto5 = new Producto(0, "Maletín Jamón de Bellota", "Jamón 5 Jotas, Cuchillo, Delantal", 549.99, 7500.00, "https://cdn.grupoelcorteingles.es/SGFM/dctm/MEDIA03/202311/10/00113302800589____19__640x640.jpg", false);
                Producto producto6 = new Producto(0, "Pack Special 5Jotas", "Jamón 5 Jotas, Morcón Bellota, Vino Monty,", 299.99, 5500.00, "https://cdn.grupoelcorteingles.es/SGFM/dctm/MEDIA03/202308/24/00113302801421____1__640x640.jpg", true);

                insertProducto(producto1);
                insertProducto(producto2);
                insertProducto(producto3);
                insertProducto(producto4);
                insertProducto(producto5);
                insertProducto(producto6);
            }
        });
    }

    // Llamamos a consulta para borrar la base de datos.
    public void limpiarBaseDeDatos() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                productoDao.borrarTodosLosProductos();
            }
        });
    }
}
