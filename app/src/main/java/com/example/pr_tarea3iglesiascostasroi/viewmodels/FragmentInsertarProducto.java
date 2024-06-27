package com.example.pr_tarea3iglesiascostasroi.viewmodels;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.pr_tarea3iglesiascostasroi.databinding.FragmentInsertarProductoBinding;
import com.example.pr_tarea3iglesiascostasroi.room.Producto;
import com.example.pr_tarea3iglesiascostasroi.room.ProductoDatabase;


public class FragmentInsertarProducto extends Fragment {

    private ProductoDatabase.ProductoDao productoDao;
    private FragmentInsertarProductoBinding binding;
    private InputMethodManager imm;

    public FragmentInsertarProducto() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtenemos el objeto ProductoDao
        productoDao = ProductoDatabase.getInstance(requireContext()).productoDao();
        imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInsertarProductoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = binding.editTextNombre.getText().toString();
                String ingredientes = binding.editTextIngredientes.getText().toString();
                String precioString = binding.editTextPrecio.getText().toString();
                String grString = binding.editTextGr.getText().toString();
                String url = binding.editTextUrl.getText().toString();
                boolean disponible = binding.checkBoxDisponible.isChecked();

                if (nombre.isEmpty() || ingredientes.isEmpty() || precioString.isEmpty() || grString.isEmpty() || url.isEmpty()) {
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
                    }
                    // Si hay campos vacíos mostramos mensaje
                    Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Convertimos a números
                    double precio = Double.parseDouble(precioString);
                    double gr = Double.parseDouble(grString);

                    Producto nuevoProducto = new Producto(0, nombre, ingredientes, precio, gr, url, disponible);

                    insertarProducto(nuevoProducto);
                    // Esconder el teclado después de guardar
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
                    }

                    Toast.makeText(requireContext(), "Producto Guardado", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                }
            }
        });

        return view;
    }

    private void insertarProducto(Producto producto) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                productoDao.insertProducto(producto);
            }
        }).start();
    }

    private void limpiarCampos() {
        binding.editTextNombre.getText().clear();
        binding.editTextIngredientes.getText().clear();
        binding.editTextPrecio.getText().clear();
        binding.editTextGr.getText().clear();
        binding.editTextUrl.getText().clear();
        binding.checkBoxDisponible.setChecked(false);
    }
}