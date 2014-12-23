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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;

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

    public static ArrayList<Pair<LocalDate, String>> getNoticiasFecha() throws SQLException {

        Connection connection = null;
        ResultSet rs = null;
        ArrayList<Pair<LocalDate, String>> fechas = new ArrayList<>();
        try (Connection conexion = ConfigBD.conectar()) {
            String insert = String.format("SELECT * from Noticias;");
            rs = conexion.createStatement().executeQuery(insert);
            while (rs.next()) {
                String fecha = rs.getString("fecha");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(fecha, formatter);
                String categoria = rs.getString("categoria");
                fechas.add(new Pair<>(date, categoria));
            }
        } catch (SQLException e) {
            throw e;
        }
        return fechas;
    }

}
