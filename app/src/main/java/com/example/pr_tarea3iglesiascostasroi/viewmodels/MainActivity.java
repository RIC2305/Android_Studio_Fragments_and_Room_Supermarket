package com.example.pr_tarea3iglesiascostasroi.viewmodels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import com.example.pr_tarea3iglesiascostasroi.R;
import com.example.pr_tarea3iglesiascostasroi.databinding.ActivityMainBinding;
import com.example.pr_tarea3iglesiascostasroi.room.ProductoRepository;
//import android.view.Menu;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Preparamos el binding para localizar los componentes con facilidad.
        //Dejamos de usar findViewById...
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //--------------------------RELLENAR BASE DE DATOS CON DATOS INICIALES-----------
        //Creamos producto de tipo ProductoRepository
        ProductoRepository productoRepository = new ProductoRepository(this.getApplicationContext());
        // Llamamos al método que inserta los datos iniciales al iniciar la actividad
        productoRepository.insertarDatosIniciales();
        //-------------------------------------------------------------------------------

        // Al final se hará de otra forma...no necesito la siguiente línea.
        //setSupportActionBar(binding.toolbarContainer);

        // Se busca un NavHostFragment en el diseño (layout) de la actividad
        // Se obtiene el controlador de navegación (NavController) a partir de él.
        // Luego se configura appBarConfiguration con la configuración de la barra de aplicaciones
        // usando el gráfico de navegación obtenido del controlador.
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView_Principal);
        NavController navController = navHostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();


        //Finalmente, se configura la barra de herramientas (toolbarContainer)
        // para que funcione con el controlador de navegación y la configuración de la barra de aplicaciones.
        NavigationUI.setupWithNavController(binding.toolbarContainer, navController, appBarConfiguration);

    }
}
