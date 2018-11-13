package com.example.luisangel.appsqllite.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.luisangel.appsqllite.R;
import com.example.luisangel.appsqllite.RegistroActivity;
import com.example.luisangel.appsqllite.modelos.Persona;
import com.example.luisangel.appsqllite.sqlite.MetodoSQL;

import java.util.ArrayList;

public class PersonaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Persona> lista;
    private Context context;

    public PersonaAdapter() {
        lista = new ArrayList<>();
    }

    public void agregar(ArrayList<Persona> personas) {
        lista.clear();
        lista.addAll(personas);
        notifyDataSetChanged();
    }

    class PersonaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvApellido;
        LinearLayout contenedor;

        public PersonaViewHolder(View itemView) {
            super(itemView);
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombre);
            tvApellido = (TextView) itemView.findViewById(R.id.tvApellido);
            contenedor = (LinearLayout) itemView.findViewById(R.id.contenedor);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_persona, parent, false
        );
        context = parent.getContext();
        return new PersonaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        PersonaViewHolder viewHolder = (PersonaViewHolder) holder;

        final Persona persona = lista.get(position);

        viewHolder.tvNombre.setText(persona.getNombre());
        viewHolder.tvApellido.setText(persona.getApellido());
        viewHolder.contenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Acción");
                builder.setMessage("Que desea hacer?");
                builder.setPositiveButton("Actualizar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(context, RegistroActivity.class);
                                intent.putExtra("nombre", persona.getNombre());
                                intent.putExtra("apellido", persona.getApellido());
                                intent.putExtra("genero", persona.getGenero());
                                intent.putExtra("id", persona.getId());
                                context.startActivity(intent);
                            }
                        });
                builder.setNegativeButton("Eliminar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MetodoSQL metodoSQL = new MetodoSQL(context);
                                metodoSQL.eliminarPersona(persona.getId());
                                lista.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                builder.setNeutralButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
