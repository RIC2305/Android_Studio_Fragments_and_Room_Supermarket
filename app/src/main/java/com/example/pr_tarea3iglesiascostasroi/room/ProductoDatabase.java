package com.example.pr_tarea3iglesiascostasroi.room;


import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.List;

@Database(entities = {Producto.class},version = 1,exportSchema = false)
public abstract class ProductoDatabase extends RoomDatabase {


    //debe devolver un objeto que implemente la interfaz ProductoDao
    //devuelve un objeto de tipo ProductoDao, que es la interfaz
    //que contiene las operaciones para interactuar con la base de datos
    public abstract ProductoDao productoDao();

    //Patron Singleton-------------------------------
    private static volatile ProductoDatabase INSTANCE;
    public static ProductoDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ProductoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ProductoDatabase.class, "producto_database") //Room ya le crea la extensión .db
                            .build();
                }
            }
        }
        return INSTANCE; //nos aseguramos que sólo creamos una instancia de la base de datos
    }
    //-----------------------------------------------
    @Dao
    public interface ProductoDao{


        //Para insertar
        @Insert
        void insertProducto(Producto producto);

        //Para modificar
        @Update
        void updateProducto(Producto producto);

        //Para borrar
        @Delete
        void deleteProducto(Producto producto);

        //Busquedas para disponibles o no
        @Query("SELECT * FROM Producto")
        LiveData<List<Producto>> getAllProduct();

        @Query("SELECT * FROM Producto WHERE disponible = 1")
        LiveData<List<Producto>> getAllAvailableProduct();

        //Consulta para borrar la base de datos
        @Query("DELETE FROM producto")
        void borrarTodosLosProductos();


    }
}
