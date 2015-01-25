package com.uca.agendavital;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DaniPera on 17/1/15.
 */
public class PreguntasSQLiteHelper extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla de Preguntas
    String sqlCreate1 = "CREATE TABLE preguntas (id_pregunta integer PRIMARY KEY, pregunta text, respuesta text, contestada integer DEFAULT 0)";
    String sqlCreate2 = "CREATE TABLE email (id integer, correo TEXT)";
    String[] preguntas = new String[] {
         "¿Qué hacía cuando se produjo el impacto de dos aviones contra las Torres Gemelas? (11-09-2001)",
         "¿Dónde te encontrabas cuando el Papa Benedicto XVI renunción de su cargo? (11-02-2013)",
         "¿Qué hacías cuando ocurrió el accidente del tren que descarriló en Santiago en el que hubo al menos 80 muertos? (24-07-2013)",
         "¿Dónde estaba cuando ocurrió el tiroteo en la sede de Charlie Hebdo? (07-01-2015)",
         "¿Qué hacía cuando la enfermera Teresa Romero fue infectada por el virus del Ébola? (07-08-2014)",
         "¿Qué sentí cuando murió el Papa Juan Pablo II? (02-04-2005)",
         "¿Qué pensé cuando el congreso aprobó la ley de matrimonio homosexual (30-06-2005)",
         "¿Cómo afronté cuando en 2008 estalló la crisis económica? (2008)",
         "¿Dónde estaba cuando se produjo el atentado suicida en el metro de Moscú que dejo 39 muertos? (29-03-2010)",
         "¿Qué sentí al escuchar la muerte de Freddie Mercury? (24-11-1991)"
 };

    public PreguntasSQLiteHelper(Context contexto, String nombre,
                                SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate1);
        db.execSQL(sqlCreate2);
        for(int i = 0;i<preguntas.length;i++){
            db.execSQL("INSERT INTO preguntas (pregunta) VALUES ('"+preguntas[i]+"')");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
