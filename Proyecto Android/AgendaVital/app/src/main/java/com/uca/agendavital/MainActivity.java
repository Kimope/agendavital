package com.uca.agendavital;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private Button btnComenzar;
    private Button btnConf;
    private LoginButton loginBtn;
    private Button btnResp;
    private Button btnSalir;

    private UiLifecycleHelper uiHelper;
    private TextView userName;


    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uiHelper = new UiLifecycleHelper(this, statusCallback);
        uiHelper.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();


        userName = (TextView) findViewById(R.id.user_name);
        loginBtn = (LoginButton) findViewById(R.id.fb_login_button);

        loginBtn.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
                if (user != null) {
                    userName.setText("¡Hola!, " + user.getName());
                    globalVariable.setUser(user.getName());
                    requestPermissions();
                } else {
                    userName.setText("No estás logueado en Facebook");
                }
            }
        });


        /*Buscamos el email en la BD al arrancar*/

        PreguntasSQLiteHelper usdbh =
                new PreguntasSQLiteHelper(this, "email", null, 1);

        SQLiteDatabase db = usdbh.getReadableDatabase();

        //Si hemos abierto correctamente la base de datos
        if(db != null)
        {

            Cursor c = db.rawQuery(" SELECT id, correo FROM email", null);

            if (c.moveToFirst()) {
                globalVariable.setId(c.getInt(0));
                globalVariable.setEmail(c.getString(1));
            }

            //Cerramos la base de datos
            db.close();
        }

        //Obtenemos la referencia del objeto
        btnComenzar = (Button)findViewById(R.id.BtnComenzar);
        btnConf = (Button)findViewById(R.id.BtnConf);
        btnResp = (Button)findViewById(R.id.BtnResp);
        btnSalir = (Button)findViewById(R.id.BtnSalir);

        //Implementamos el evento click del botón
        btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(MainActivity.this, Preguntas.class);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }
        });

        //Implementamos el evento click del botón
        btnConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(MainActivity.this, Configuracion.class);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }

        });

        btnResp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos el Intent
                Intent intent =
                        new Intent(MainActivity.this, Respuestas.class);

                //Iniciamos la nueva actividad
                startActivity(intent);
            }

        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Session.StatusCallback statusCallback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            if (state.isOpened()) {
                Log.d("FacebookSampleActivity", "Facebook session opened");
            } else if (state.isClosed()) {
                Log.d("FacebookSampleActivity", "Facebook session closed");
            }
        }
    };


    public boolean checkPermissions() {
        Session s = Session.getActiveSession();
        if (s != null) {
            return s.getPermissions().contains("publish_actions");
        } else
            return false;
    }

    public void requestPermissions() {
        Session s = Session.getActiveSession();
        if (s != null)
            s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
                    this, PERMISSIONS));
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
        //buttonsEnabled(Session.getActiveSession().isOpened());
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        uiHelper.onSaveInstanceState(savedState);
    }

}
