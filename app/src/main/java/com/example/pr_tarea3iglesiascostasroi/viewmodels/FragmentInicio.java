package com.example.pr_tarea3iglesiascostasroi.viewmodels;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.pr_tarea3iglesiascostasroi.R;
import com.example.pr_tarea3iglesiascostasroi.databinding.FragmentInicioBinding;

import org.jetbrains.annotations.Nullable;


public class FragmentInicio extends Fragment {
    // El nombre de la variable es el nombre del fragmento añadiéndole Binding.
    // debes haber añadido en el gradle el binding.
    FragmentInicioBinding binding;


    public FragmentInicio() {
        // Debe contener constructor vacío
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnIrAListadoProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_fragmentInicio_to_fragment_listado_productos);

            }
        });
    }

}