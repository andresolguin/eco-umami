package frgp.utn.edu.ar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<ProductoResponse> productos;

    public ProductoAdapter(List<ProductoResponse> productos) {
        this.productos = productos;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);

        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        ProductoResponse producto = productos.get(position);

        holder.txtNombreProducto.setText(producto.getNombre());
        holder.txtPrecioOriginal.setText(
                "Precio original: $" + producto.getPrecioOriginal()
        );
        holder.txtPrecioReducido.setText(
                "Precio Eco Umami: $" + producto.getPrecioReducido()
        );
        holder.txtFechaVencimiento.setText(
                "Vence: " + producto.getFechaVencimiento()
        );
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombreProducto;
        TextView txtPrecioOriginal;
        TextView txtPrecioReducido;
        TextView txtFechaVencimiento;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombreProducto = itemView.findViewById(R.id.txtNombreProducto);
            txtPrecioOriginal = itemView.findViewById(R.id.txtPrecioOriginal);
            txtPrecioReducido = itemView.findViewById(R.id.txtPrecioReducido);
            txtFechaVencimiento = itemView.findViewById(R.id.txtFechaVencimiento);
        }
    }
}