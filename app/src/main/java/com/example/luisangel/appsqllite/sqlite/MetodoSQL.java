package com.example.luisangel.appsqllite.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.luisangel.appsqllite.modelos.Persona;

import java.util.ArrayList;

public class MetodoSQL {
    private ManageOpenHelper conexion;

    public MetodoSQL(Context context) {
        conexion = new ManageOpenHelper(context);
    }


    public ArrayList<Persona> listarTodo() {
        ArrayList<Persona> lista = new ArrayList<>();

        String consulta = "select * from tb_persona";
        SQLiteDatabase db = conexion.getReadableDatabase();
        Cursor cursor = db.rawQuery(consulta, null);

        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                String apellido = cursor.getString(cursor.getColumnIndex("apellido"));
                String genero = cursor.getString(cursor.getColumnIndex("genero"));
                Persona persona = new Persona(id, nombre, apellido,genero);
                lista.add(persona);

            } while (cursor.moveToNext());
        }

        return lista;
    }

    public void agregarPersona(Persona persona) {
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", persona.getNombre());
        contentValues.put("apellido", persona.getApellido());
        contentValues.put("genero", persona.getGenero());
        db.insert("tb_persona", null, contentValues);
    }

    public void actualizarPersona(Persona persona) {
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", persona.getNombre());
        contentValues.put("apellido", persona.getApellido());
        contentValues.put("genero", persona.getGenero());
        db.update("tb_persona",
                contentValues,
                "id=?", new String[]{String.valueOf(persona.getId())});
    }

    public void eliminarPersona(int idPersona) {
        SQLiteDatabase db = conexion.getWritableDatabase();
        db.delete("tb_persona",
                "id=?", new String[]{String.valueOf(idPersona)});
    }
}
