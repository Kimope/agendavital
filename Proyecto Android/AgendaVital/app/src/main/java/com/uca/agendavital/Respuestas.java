package com.uca.agendavital;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Vector;


public class Respuestas extends ActionBarActivity {

    private Vector<String> preguntas = new Vector<String>();
    private Vector<String> respuestas = new Vector<String>();

    private TextView txtPreguntaRes;
    private TextView txtRes;
    private Button btnSiguienteResp;
    private Button btnAtrasResp;
    private Button btnMenuResp;

    private int nPregunta=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuestas);

        txtPreguntaRes = (TextView)findViewById(R.id.TxtPreguntaRes);
        txtRes = (TextView)findViewById(R.id.TxtRes);
        btnAtrasResp = (Button)findViewById(R.id.BtnAtrasResp);
        btnSiguienteResp = (Button)findViewById(R.id.BtnSiguienteResp);
        btnMenuResp = (Button)findViewById(R.id.BtnMenuResp);

        PreguntasSQLiteHelper usdbh =
                new PreguntasSQLiteHelper(this, "preguntas", null, 1);

        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {
            Cursor c = db.rawQuery(" SELECT pregunta, respuesta FROM preguntas WHERE contestada=1", null);

            if (c.moveToFirst()) {
                do {
                   preguntas.add(c.getString(0));
                   respuestas.add(c.getString(1));
                } while(c.moveToNext());
            }

            //Cerramos la base de datos
            db.close();
        }

        if(nPregunta==0){
            btnAtrasResp.setEnabled(false);
            btnAtrasResp.setVisibility(View.INVISIBLE);
        }

        if(preguntas.size()>nPregunta+1){
            btnSiguienteResp.setEnabled(true);
            btnSiguienteResp.setVisibility(View.VISIBLE);
        }else{
            btnSiguienteResp.setEnabled(false);
            btnSiguienteResp.setVisibility(View.INVISIBLE);

        }

        if(preguntas.size()==0){
            txtPreguntaRes.setText("No hay preguntas contestadas");
        }else{
            txtPreguntaRes.setText(preguntas.elementAt(nPregunta));
            txtRes.setText(respuestas.elementAt(nPregunta));
        }

        btnSiguienteResp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nPregunta++;
                if(preguntas.size()>nPregunta){
                    txtPreguntaRes.setText(preguntas.elementAt(nPregunta));
                    txtRes.setText(respuestas.elementAt(nPregunta));
                }
                if(preguntas.size()>nPregunta+1){
                    btnSiguienteResp.setEnabled(true);
                    btnSiguienteResp.setVisibility(View.VISIBLE);
                }else{
                    btnSiguienteResp.setEnabled(false);
                    btnSiguienteResp.setVisibility(View.INVISIBLE);

                }
                btnAtrasResp.setEnabled(true);
                btnAtrasResp.setVisibility(View.VISIBLE);
            }
        });

        btnAtrasResp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nPregunta--;
                    txtPreguntaRes.setText(preguntas.elementAt(nPregunta));
                    txtRes.setText(respuestas.elementAt(nPregunta));

                if(nPregunta-1>=0){
                    btnAtrasResp.setEnabled(true);
                    btnAtrasResp.setVisibility(View.VISIBLE);
                }else{
                    btnAtrasResp.setEnabled(false);
                    btnAtrasResp.setVisibility(View.INVISIBLE);
                }
                btnSiguienteResp.setEnabled(true);
                btnSiguienteResp.setVisibility(View.VISIBLE);

            }
        });

        btnMenuResp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });
    }


}
