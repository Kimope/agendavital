/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.data;

import agendavital.modelo.util.ConfigBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author rr
 */
public class Noticia {
    public int id;
    public String titulo;
    public String fecha;
    public String categoria;

    public Noticia() {
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public ArrayList<Noticia> getNoticias(int dia_, int mes_, int ano_) throws SQLException{
        String fecha = "";
        if(dia_ < 10) fecha +="0";
        fecha+=dia_;
        if(mes_ < 10) fecha +="0";
        fecha+=mes_;
        fecha+=ano_;
        Connection connection = null;
        ResultSet rs = null;
        ArrayList<Noticia> noticias = new ArrayList<Noticia>();
        try (Connection conexion = ConfigBD.conectar()) {
            String insert = String.format("SELECT * from Noticias WHERE fecha = %s;", ConfigBD.String2Sql(fecha, false));
            rs = conexion.createStatement().executeQuery(insert);
            Noticia noticia = new Noticia();
            noticia.setId(rs.getInt("id"));
            noticia.setTitulo(rs.getString("titulo"));
            noticia.setFecha(rs.getString("fecha"));
            noticia.setCategoria(rs.getString("categoria"));
            noticias.add(noticia);
        } catch (SQLException e) {
            throw e;
        }
        return noticias;
    }
    
}
