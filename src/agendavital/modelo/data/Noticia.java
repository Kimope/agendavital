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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author rr
 */
public class Noticia {

    int id;
    String titulo;
    String fecha;
    String categoria;
    String link;
    String cuerpo;
    ArrayList<Integer> tags;

    public Noticia(int _id) throws SQLException {
        id = _id;
        tags = new ArrayList<>();
        Connection conexion = null;
        ResultSet rs = null;
        try {
            conexion = ConfigBD.conectar();
            rs = conexion.createStatement().executeQuery(String.format("SELECT * FROM noticias WHERE id_noticia = %d;", id));
            rs.next();
            this.setTitulo(rs.getString("titulo"));
            this.setFecha(rs.getString("fecha"));
            this.setLink(rs.getString("link"));
            this.setCategoria(rs.getString("categoria"));
            this.setCuerpo(rs.getString("cuerpo"));
            rs = conexion.createStatement().executeQuery(String.format("SELECT id_etiqueta from momentos_noticias_etiquetas WHERE id_noticia = %d", id));
            while(rs.next()){
                tags.add(rs.getInt("id_etiqueta"));
            }
            
        } catch (SQLException ee) {
            throw ee;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public static ArrayList<Pair<LocalDate, String>> getNoticiasFecha() throws SQLException {

        Connection connection = null;
        ResultSet rs = null;
        ArrayList<Pair<LocalDate, String>> fechas = new ArrayList<>();
        try (Connection conexion = ConfigBD.conectar()) {
            String consulta = String.format("SELECT fecha, categoria from Noticias;");
            rs = conexion.createStatement().executeQuery(consulta);
            while (rs.next()) {
                String fecha = rs.getString("fecha");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(fecha, formatter);
                String categoria = rs.getString("categoria");
                fechas.add(new Pair<>(date, categoria));
            }
        } catch (SQLException e) {
            throw e;
        }
        return fechas;
    }

    public static Noticia Insert(String titulo, String link, String fecha, String categoria, String cuerpo, ArrayList<String> tags) throws SQLException {
        int nuevoId = 0;
        try (Connection conexion = ConfigBD.conectar()) {
            String insert = "INSERT INTO noticias (titulo, link, fecha, categoria, cuerpo)";
            insert += String.format(" VALUES (%s, %s, %s, %s, %s)", ConfigBD.String2Sql(titulo, false),
                    ConfigBD.String2Sql(link, false), ConfigBD.String2Sql((fecha), false), ConfigBD.String2Sql(categoria, false),
                    ConfigBD.String2Sql(cuerpo, false));
            int executeUpdate = conexion.createStatement().executeUpdate(insert);
            nuevoId = ConfigBD.LastId("noticias");
            for (int i = 0; i < tags.size(); i++) {
                String consultaTag = String.format("SELECT id_etiqueta from etiquetas WHERE Nombre = %s;", ConfigBD.String2Sql(tags.get(i), false));
                ResultSet rs = conexion.createStatement().executeQuery(consultaTag);
                rs.next();
                int idTag = (rs.getRow()==1)?rs.getInt("id_etiqueta"):-1;
                if (idTag == -1) {
                    String insertTag = String.format("INSERT INTO etiquetas (nombre) VALUES (%s);", ConfigBD.String2Sql(tags.get(i), false));
                    conexion.createStatement().executeUpdate(insertTag);
                    idTag = ConfigBD.LastId("etiquetas");
                }
                String insertNoticiaEtiqueta = String.format("INSERT INTO momentos_noticias_etiquetas (id_noticia, id_etiqueta) VALUES(%d, %d);", nuevoId, idTag);
                conexion.createStatement().executeUpdate(insertNoticiaEtiqueta);
            }
        } catch (SQLException e) {
            throw e;
        }
        return new Noticia(nuevoId);
    }

    public void Update() throws SQLException {
        try (Connection conexion = ConfigBD.conectar()) {
            String update = String.format("UPDATE noticias SET titulo = %s, link = %s, fecha = %s, categoria = %s, cuerpo = %s WHERE id_noticia = %d;", ConfigBD.String2Sql(getTitulo(), false), ConfigBD.String2Sql(getLink(), false), ConfigBD.String2Sql(getFecha(), false), ConfigBD.String2Sql(getCategoria(), false), ConfigBD.String2Sql(getCuerpo(), false), getId());
            conexion.createStatement().executeUpdate(update);
        } catch (SQLException e) {
            throw e;
        }
    }

    public boolean Delete() throws SQLException {
        try (Connection conexion = ConfigBD.conectar()) {
            String delete = String.format("Delete from noticias WHERE id_noticia = %d;", getId());
            conexion.createStatement().executeUpdate(delete);
            String deleteTag = String.format("Delete from momentos_noticias_etiquetas WHERE id_noticia = %d;", getId());
            conexion.createStatement().executeUpdate(deleteTag);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

}
