package com.example.pr_tarea3iglesiascostasroi.viewmodels;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.pr_tarea3iglesiascostasroi.R;
import com.example.pr_tarea3iglesiascostasroi.room.Producto;
import com.example.pr_tarea3iglesiascostasroi.room.ProductoRepository;
import com.google.android.material.snackbar.Snackbar;


public class FragmentFichaProducto extends Fragment {

    private EditText nombreEditText;
    private EditText ingredientesEditText;
    private EditText precioEditText;
    private EditText grEditText;
    private EditText urlEditText;
    private CheckBox disponibleCheckBox;
    private Button guardarButton;
    private ImageView imagenProducto;

    public FragmentFichaProducto() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ficha_producto, container, false);

        // Cogemos todas las referencias de las vistas
        //Ene ste caso no lo hice con Binding para practicar las dos formas
        nombreEditText = view.findViewById(R.id.editTextNombrefragmentModificar);
        ingredientesEditText = view.findViewById(R.id.editTextIngredientesfragmentModificar);
        precioEditText = view.findViewById(R.id.editTextPreciofragmentModificar);
        grEditText = view.findViewById(R.id.editTextGrfragmentModificar);
        urlEditText = view.findViewById(R.id.editTextUrlfragmentModificar);
        disponibleCheckBox = view.findViewById(R.id.checkBoxDisponiblefragmentModificar);
        guardarButton = view.findViewById(R.id.buttonGuardarfragmentModificar);
        imagenProducto = view.findViewById(R.id.imageViewUrlHeader);

        // Obtener el producto del Bundle
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("producto")) {
            Producto producto = (Producto) bundle.getSerializable("producto");
            if (producto != null) {
                // Establecer los valores en los campos de la vista
                nombreEditText.setText(producto.getNombre());
                ingredientesEditText.setText(producto.getIngredientes());
                precioEditText.setText(String.valueOf(producto.getPrecio()));
                grEditText.setText(String.valueOf(producto.getGr()));
                urlEditText.setText(producto.getUrl());
                disponibleCheckBox.setChecked(producto.isDisponible());

                // Glide para cargar la imagen desde URL (recuerda que debes dar permiso en el manifest para internet
                // en Android.Manifest.xml añade línea  <uses-permission android:name="android.permission.INTERNET" />
                Glide.with(requireContext())
                        .load(producto.getUrl()) // URL del producto
                        .placeholder(R.drawable.ic_launcher_background) // Imagen de relleno mientras se carga
                        .error(R.drawable.ic_launcher_background) // Imagen de error si no se puede cargar
                        .into(imagenProducto);

                guardarButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Guardamos loas modificaciones de los campos de texto en variables
                        String nombre = nombreEditText.getText().toString();
                        String ingredientes = ingredientesEditText.getText().toString();
                        double precio = Double.parseDouble(precioEditText.getText().toString());
                        double gr = Double.parseDouble(grEditText.getText().toString());
                        String url = urlEditText.getText().toString();
                        boolean disponible = disponibleCheckBox.isChecked();

                        // Modificamos los valores del producto
                        producto.setNombre(nombre);
                        producto.setIngredientes(ingredientes);
                        producto.setPrecio(precio);
                        producto.setGr(gr);
                        producto.setUrl(url);
                        producto.setDisponible(disponible);

                        // Preguntamos confirmación
                        Snackbar.make(requireView(), "¿Actualizar producto?", Snackbar.LENGTH_LONG)
                                .setAction("Confirmar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // Llamar al método de ProductoRepository para actualizar el producto
                                        ProductoRepository productoRepository = new ProductoRepository(requireContext());
                                        productoRepository.updateProducto(producto);

                                        // Mostramos mensaje de operación realizada
                                        Snackbar.make(requireView(), "Producto actualizado correctamente", Snackbar.LENGTH_SHORT).show();
                                    }
                                })
                                .show();
                    }
                });
            }
        }

        return view;
    }
}