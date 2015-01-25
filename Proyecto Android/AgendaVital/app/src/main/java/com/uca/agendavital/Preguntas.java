package com.uca.agendavital;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;


public class Preguntas extends ActionBarActivity {

    private TextView txtPregunta;
    private EditText editResp;
    private Button btnSiguiente;
    private Button btnEnviarEmail;
    private TextView txtNPregunta;
    private Button btnCompartir;
    private Button btnMenuPreg;

    private int nPregunta=0;

    private Vector<String> preguntas = new Vector<String>();
    private Vector<Integer> ids = new Vector<Integer>();

    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);

        txtPregunta = (TextView)findViewById(R.id.TxtPregunta);
        editResp = (EditText)findViewById(R.id.EditResp);
        btnSiguiente = (Button)findViewById(R.id.BtnSiguiente);
        txtNPregunta = (TextView)findViewById(R.id.TxtNPregunta);
        btnEnviarEmail = (Button)findViewById(R.id.BtnEnviarEmail);
        btnCompartir = (Button)findViewById(R.id.BtnCompartir);
        btnMenuPreg = (Button)findViewById(R.id.BtnMenuPreg);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();


        //Abrimos la base de datos 'preguntas' en modo lectura
        PreguntasSQLiteHelper usdbh =
                new PreguntasSQLiteHelper(this, "preguntas", null, 1);

        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {

            Cursor c = db.rawQuery(" SELECT id_pregunta, pregunta FROM preguntas WHERE contestada=0 ", null);

            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do{
                    ids.add(c.getInt(0));
                    preguntas.add(c.getString(1));
                }while(c.moveToNext());
            }
            //Cerramos la base de datos
            db.close();
        }


        if(preguntas.size()==0){
            txtPregunta.setText("No quedan preguntas por contestar");
            editResp.setVisibility(View.INVISIBLE);
            btnCompartir.setVisibility(View.INVISIBLE);
            btnEnviarEmail.setVisibility(View.INVISIBLE);
            btnSiguiente.setVisibility(View.INVISIBLE);
        }else{
            txtPregunta.setText(preguntas.elementAt(nPregunta));
            txtNPregunta.setText("Pregunta "+(nPregunta+1));
            btnEnviarEmail.setEnabled(true);
            btnEnviarEmail.setVisibility(View.VISIBLE);
            btnSiguiente.setEnabled(true);
            btnSiguiente.setVisibility(View.VISIBLE);

            if(globalVariable.getUser()==""){ btnCompartir.setEnabled(false); btnCompartir.setVisibility(View.INVISIBLE);}
            else{ btnCompartir.setEnabled(true); btnCompartir.setVisibility(View.VISIBLE);}

        }


        //Implementamos el evento click del botón
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String txtVacio = editResp.getText().toString();

                if(txtVacio.equals("")) {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Respuesta vacía", Toast.LENGTH_SHORT);
                    toast1.show();
                }
                else{

                    //Abrimos la base de datos 'preguntas' en modo escritura
                    PreguntasSQLiteHelper usdbh =

                            new PreguntasSQLiteHelper(getApplicationContext(), "preguntas", null, 1);

                    SQLiteDatabase db = usdbh.getWritableDatabase();

                    //Si hemos abierto correctamente la base de datos
                    if(db != null)
                    {
                        String sqlUpdate = "UPDATE preguntas SET respuesta='"+txtVacio+"', contestada=1 WHERE id_pregunta="+ids.elementAt(nPregunta);
                        db.execSQL(sqlUpdate);

                        Toast toast2 =
                                Toast.makeText(getApplicationContext(),
                                        "Respuesta añadida", Toast.LENGTH_SHORT);
                        toast2.show();
                        //Cerramos la base de datos
                        db.close();

                        nPregunta++;
                        if(preguntas.size()>nPregunta){
                            txtPregunta.setText(preguntas.elementAt(nPregunta));
                            txtNPregunta.setText("Pregunta "+(nPregunta+1));
                            editResp.setText("");
                        }else{
                            txtPregunta.setText("No quedan preguntas por contestar");
                            editResp.setVisibility(View.INVISIBLE);
                            btnCompartir.setVisibility(View.INVISIBLE);
                            btnEnviarEmail.setVisibility(View.INVISIBLE);
                            btnCompartir.setVisibility(View.INVISIBLE);
                            btnSiguiente.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        });

        //Implementamos el evento click del botón
        btnEnviarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String txtVacio = editResp.getText().toString();

                if(txtVacio.equals("")) {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Respuesta vacía", Toast.LENGTH_SHORT);
                    toast1.show();
                }else {
                    new SendEmailAsyncTask(preguntas.elementAt(nPregunta),txtVacio).execute();
                }
            }
        });


        btnCompartir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String txtVacio = editResp.getText().toString();

                if(txtVacio.equals("")) {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Respuesta vacía. No se puede compartir", Toast.LENGTH_SHORT);
                    toast1.show();
                }
                else {
                    postStatusMessage(preguntas.elementAt(nPregunta) + "\n" + txtVacio);
                }
            }
        });

        btnMenuPreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });
    }


    public void postStatusMessage(String message) {
        MainActivity m = new MainActivity();

        try{
            if (checkPermissions()) {
                Request request = Request.newStatusUpdateRequest(
                        Session.getActiveSession(), message,
                        new Request.Callback() {
                            @Override
                            public void onCompleted(Response response) {
                                if (response.getError() == null)
                                    Toast.makeText(getApplicationContext(),
                                            "Estado actualizado correctamente",
                                            Toast.LENGTH_LONG).show();
                            }
                        });
                request.executeAsync();
            } else {
                requestPermissions();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),
                    "Hay problemas con Facebook. Pruebe más tarde.",
                    Toast.LENGTH_LONG).show();
        }
    }

    //Clase necesaria para enviar el email
    class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
        Mail m = new Mail("proyectoagendavital@gmail.com", "carlosrioja");

        public SendEmailAsyncTask(String pregunta, String txtVacio) {
            if (BuildConfig.DEBUG)
                Log.v(SendEmailAsyncTask.class.getName(), "SendEmailAsyncTask()");

            final GlobalClass globalVariable = (GlobalClass) getApplicationContext();

            if(globalVariable.getEmail()!=""){

                String[] toArr = {globalVariable.getEmail()};

                m.setTo(toArr);
                m.setFrom("proyectoagendavital@gmail.com");
                m.setSubject(pregunta);
                m.setBody(txtVacio);

                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Enviando email...", Toast.LENGTH_SHORT);
                toast1.show();

            }else{
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "No hay un email asignado\nMirar configuración", Toast.LENGTH_SHORT);
                toast1.show();
            }

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (BuildConfig.DEBUG) Log.v(SendEmailAsyncTask.class.getName(), "doInBackground()");
            try {
                //m.addAttachment("/sdcard/filelocation");

                if(m.send()) {
                    // Toast.makeText(getApplicationContext(), "Email was sent successfully.", Toast.LENGTH_LONG).show();
                    //this.publishProgress();
                    return true;
                } else {
                    //   Toast.makeText(getApplicationContext(), "Email was not sent.", Toast.LENGTH_LONG).show();
                    return false;
                }
            } catch(Exception e) {
                //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                Log.e("AgendaVital", "No se puedo enviar el email", e);
                return false;
            }
        }

    }

    public void requestPermissions() {
        Session s = Session.getActiveSession();
        if (s != null)
            s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
                    this, PERMISSIONS));
    }

    public boolean checkPermissions() {
        Session s = Session.getActiveSession();
        if (s != null) {
            return s.getPermissions().contains("publish_actions");
        } else
            return false;
    }

}
