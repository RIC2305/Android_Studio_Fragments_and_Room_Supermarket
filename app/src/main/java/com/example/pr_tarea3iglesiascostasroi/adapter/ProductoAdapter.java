package com.example.pr_tarea3iglesiascostasroi.adapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.pr_tarea3iglesiascostasroi.R;
import com.example.pr_tarea3iglesiascostasroi.room.Producto;
import com.example.pr_tarea3iglesiascostasroi.viewmodels.ViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {
    private List<Producto> productos;
    private ViewModel viewModel;
    private OnModifyClickListener modifyClickListener; // Listener para los clics en "Modificar"

    public ProductoAdapter(List<Producto> productos) {
        this.productos = productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
        notifyDataSetChanged();
    }

    //El siguiente método es llamado desde el fragmento que use el adaptador (FragmentListadoProducto)
    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    // Método para establecer el listener desde el Fragment
    public void setOnModifyClickListener(OnModifyClickListener listener) {
        this.modifyClickListener = listener;
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView, precioTextView, disponibleTextView;
        ImageView imagenProducto;
        Button modificarButton;
        Button eliminarButton;
        View cvDisponible;
        public ProductoViewHolder(View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.text_view_nombre);
            precioTextView = itemView.findViewById(R.id.text_view_precio);
            imagenProducto = itemView.findViewById(R.id.image_view_producto);
            modificarButton = itemView.findViewById(R.id.button_modificar);
            disponibleTextView = itemView.findViewById(R.id.text_view_disponible);
            eliminarButton = itemView.findViewById(R.id.button_eliminar);
            cvDisponible = itemView.findViewById(R.id.colorview_disponible);

        }
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        Producto producto = productos.get(position);
        holder.nombreTextView.setText(producto.getNombre());
        holder.precioTextView.setText("Precio: " + String.valueOf(producto.getPrecio()));
        holder.disponibleTextView.setText("Disponible: " );





            // Lógica para configurar el color de fondo según el valor booleano de "DISPONIBLE"
            if (producto.isDisponible()) {
                holder.cvDisponible.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.md_theme_light_tertiary));  // Color verde para true
            } else {
                holder.cvDisponible.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.md_theme_light_error)); // Color rojo para false
            }

            Glide.with(holder.itemView.getContext())
                .load(producto.getUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imagenProducto);

        // Código para el botón "Modificar"
        holder.modificarButton.setOnClickListener(v -> {
            if (modifyClickListener != null) {
                modifyClickListener.onModifyClick(producto);
            }
        });

        // Código para eliminar el producto al hacer clic en el botón "ELIMINAR"
        holder.eliminarButton.setOnClickListener(v -> {
            if (viewModel != null) {
                // Snackbar de confirmación antes de eliminar
                Snackbar.make(v, "Confirme para eliminar..", Snackbar.LENGTH_LONG)
                        .setAction("Eliminar", view -> {
                            viewModel.deleteProducto(producto);
                        })
                        .show();
            }
        });
    }

    public interface OnModifyClickListener {
        void onModifyClick(Producto producto);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }
}