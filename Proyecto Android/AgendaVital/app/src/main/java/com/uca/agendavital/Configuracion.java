package com.uca.agendavital;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Session;

import java.util.Arrays;
import java.util.List;


public class Configuracion extends ActionBarActivity {

    private EditText editEmail;
    private String email;
    private int id_email;
    private Button btnAceptar;
    private Button btnCancelar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

        editEmail = (EditText)findViewById(R.id.EditEmail);
        btnAceptar = (Button)findViewById(R.id.BtnAceptarConf);
        btnCancelar = (Button)findViewById(R.id.BtnCancelar);

        id_email = globalVariable.getId();
        email = globalVariable.getEmail();

        editEmail.setText(email);

        final String emailEdit = editEmail.getText().toString();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Comprobamos que hay algún valor en el campo del email
                if (emailEdit != "") {

                    final String emailEdit = editEmail.getText().toString();

                    //Abrimos la base de datos 'email' en modo escritura
                    PreguntasSQLiteHelper usdbh =

                            new PreguntasSQLiteHelper(getApplicationContext(), "email", null, 1);

                    SQLiteDatabase db = usdbh.getWritableDatabase();

                    //Si hemos abierto correctamente la base de datos

                    if (db != null) {
                        //Hay datos, tenemos en la base de datos un email, pero es diferente al del edit
                        if (id_email == 1 && email != emailEdit) {
                            String sqlUpdate = "UPDATE email SET correo='" + emailEdit + "' WHERE id=1";
                            db.execSQL(sqlUpdate);

                            Toast toast2 =
                                    Toast.makeText(getApplicationContext(),
                                            "Correo modificado", Toast.LENGTH_SHORT);
                            toast2.show();

                            globalVariable.setEmail(emailEdit);

                            //Cerramos la base de datos
                            db.close();
                        }//No tenemos email almacenado. Guardamos el nuevo
                        else if (id_email == 0) {
                            String sqlInsert = "INSERT INTO email VALUES(1,'"+emailEdit+"')";
                            db.execSQL(sqlInsert);

                            Toast toast2 =
                                    Toast.makeText(getApplicationContext(),
                                            "Correo añadido", Toast.LENGTH_SHORT);
                            toast2.show();

                            globalVariable.setId(1);
                            globalVariable.setEmail(emailEdit);

                            //Cerramos la base de datos
                            db.close();
                        }

                    }
                }
                finish();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });
    }


}