package com.example.luisangel.appsqllite.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ManageOpenHelper extends SQLiteOpenHelper {
    private static final String BASE_DATOS = "bdidat.db";
    private static final int VERSION = 1;

    public ManageOpenHelper(Context context) {
        super(context, BASE_DATOS, null , VERSION);
    }


    //Sirve para crear la estructura de la BD
    //por primera vez.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table tb_persona(id integer primary key autoincrement," +
                        "nombre varchar(100)," +
                        "apellido varchar(100)," +
                        "genero varchar(100))"
        );
        db.execSQL(
                "insert into tb_persona(nombre, apellido, genero)" +
                        "values " +
                        "('Jose', 'Monterola', 'Masculino')," +
                        "('Rosa','Aquino','Femenino')"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion <= 1) {

            oldVersion++;
        }
        if (oldVersion <= 2) {

            oldVersion++;
        }
    }
}
