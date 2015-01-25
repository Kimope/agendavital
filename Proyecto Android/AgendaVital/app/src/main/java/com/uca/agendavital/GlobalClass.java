package com.uca.agendavital;

/**
 * Created by DaniPera on 19/1/15.
 */

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GlobalClass extends Application {

    private String email="";
    private int id= 0;
    private String user="";


    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setUser(String user){
        this.user = user;
    }

    public String getUser(){
        return user;
    }

}
