package com.example.pr_tarea3iglesiascostasroi.viewmodels;


import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.pr_tarea3iglesiascostasroi.R;
import com.example.pr_tarea3iglesiascostasroi.adapter.ProductoAdapter;
import com.example.pr_tarea3iglesiascostasroi.databinding.FragmentListadoProductosBinding;
import com.example.pr_tarea3iglesiascostasroi.room.Producto;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;



public class FragmentListadoProductos extends Fragment {

    FragmentListadoProductosBinding binding;
    ViewModel viewModel;
    ProductoAdapter adapter;
    NavController navController; // Agregamos el NavController

    public FragmentListadoProductos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        adapter = new ProductoAdapter(new ArrayList<>()); // Inicializar el adaptador con una lista vacía

        // Establecer el listener para el botón "Modificar"
        adapter.setOnModifyClickListener(new ProductoAdapter.OnModifyClickListener() {
            @Override
            public void onModifyClick(Producto producto) {
                // Crear un Bundle para enviar datos al FragmentFichaProducto
                Bundle bundle = new Bundle();
                bundle.putSerializable("producto", producto); // Envía el producto seleccionado

                // Navegar al FragmentFichaProducto y pasar el Bundle
                navController.navigate(R.id.fragmentFichaProducto, bundle);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListadoProductosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view); // Inicializar NavController

        //Asignamos el recyclerView de la vista xml
        RecyclerView recyclerView = binding.recyclerViewProductos;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        //Le asignamos el adaptador
        recyclerView.setAdapter(adapter);

        // Toolbar / Botones
        ImageButton settingsButton = view.findViewById(R.id.action_settings);
        Button addButton = view.findViewById(R.id.action_add);


        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.preferenceFragment);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.fragmentInsertarProducto);
            }
        });



        // Obtenemos datos de la pantalla de preferencias usando los métodos específicos
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        boolean mostrarTodos = sharedPreferences.getBoolean("mostrar_productos", false);

        //Mostramos según filtro del fragmento de settings establecido en un checkBox Preference
        if (mostrarTodos) {
            viewModel.getAllProductos().observe(getViewLifecycleOwner(), productos -> {
                if (productos != null) {
                    adapter.setProductos(productos);
                }
            });
        } else {
            viewModel.getAvailableProductos().observe(getViewLifecycleOwner(), productos -> {
                if (productos != null) {
                    adapter.setProductos(productos);
                }
            });
        }
        //Le pasamos la referencia al adaptador del viewModel
        adapter.setViewModel(viewModel);
    }
}