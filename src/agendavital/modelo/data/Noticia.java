/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.data;

import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import agendavital.modelo.excepciones.ErrorConexionFeedzilla;
import agendavital.modelo.util.ConfigBD;
import agendavital.modelo.util.UsuarioLogueado;
import agendavital.modelo.util.UtilidadesBusqueda;
import agendavital.modelo.util.UtilidadesNoticia;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import javafx.util.Pair;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    ArrayList<String> tags;

    /**
     * Constructor de noticia.
     *
     * @param _id ID de la noticia en la BD
     * @throws agendavital.modelo.excepciones.ConexionBDIncorrecta
     * @throws java.sql.SQLException
     */
    public Noticia(int _id) throws ConexionBDIncorrecta, SQLException {
        id = _id;
        tags = new ArrayList<>();
        Connection conexion = null;
        ResultSet rs = null;
        try {
            conexion = ConfigBD.conectar();
            rs = conexion.createStatement().executeQuery(String.format("SELECT * FROM noticias WHERE id_noticia = %d;", id));
            rs.next();
            this.titulo = rs.getString("titulo");
            this.fecha = rs.getString("fecha");
            this.link = rs.getString("link");
            this.categoria = rs.getString("categoria");
            this.cuerpo = rs.getString("cuerpo");
            rs = conexion.createStatement().executeQuery(String.format("SELECT nombre from etiquetas WHERE id_etiqueta IN (SELECT id_etiqueta from momentos_noticias_etiquetas WHERE id_noticia = %d)", id));
            while (rs.next()) {
                tags.add(rs.getString("nombre"));
            }

        } catch (SQLException ee) {
            throw new ConexionBDIncorrecta();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    Noticia() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getTags() {
        return tags;
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

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
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

    public static ArrayList<Noticia> getTodasNoticias() throws ConexionBDIncorrecta {
        ResultSet rs = null;
        ArrayList<Noticia> noticias = null;
        try (Connection conexion = ConfigBD.conectar()) {
            noticias = new ArrayList<>();
            String consulta = String.format("SELECT id_noticia from Noticias;");
            rs = conexion.createStatement().executeQuery(consulta);
            while (rs.next()) {
                noticias.add(new Noticia(rs.getInt("id_noticia")));
            }
            return noticias;
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }
    }

    /**
     * Funcion que devuelve las noticias en una fecha
     *
     * @param fecha La fecha en formato DD-MM-AAAA
     * @return
     * @throws agendavital.modelo.excepciones.ConexionBDIncorrecta
     */
    public static ArrayList<Noticia> Select(String fecha) throws ConexionBDIncorrecta {
        ResultSet rs = null;
        ArrayList<Noticia> noticias = null;
        try (Connection conexion = ConfigBD.conectar()) {
            noticias = new ArrayList<>();
            String consulta = String.format("SELECT id_noticia from Noticias WHERE fecha = %s;", ConfigBD.String2Sql(fecha, false));
            rs = conexion.createStatement().executeQuery(consulta);
            while (rs.next()) {
                noticias.add(new Noticia(rs.getInt("id_noticia")));
            }
            return noticias;
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }
    }

    /**
     * Funcion coloreadora del calendario
     *
     * @return
     * @throws agendavital.modelo.excepciones.ConexionBDIncorrecta
     *
     */
    public static TreeMap<LocalDate, String> getNoticiasFecha() throws ConexionBDIncorrecta {
        ResultSet rs = null;
        TreeMap<LocalDate, String> noticias = new TreeMap<>();
        try (Connection conexion = ConfigBD.conectar()) {
            String consulta = String.format("SELECT categoria, fecha from Noticias;");
            rs = conexion.createStatement().executeQuery(consulta);
            while (rs.next()) {
                String categoria = rs.getString("categoria");
                String fecha = rs.getString("fecha");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(fecha, formatter);
                noticias.put(date, categoria);
            }
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }
        return noticias;
    }

    /**
     * Funcion que inserta la noticia en la BD
     *
     * @param titulo
     * @param link
     * @param fecha
     * @param categoria
     * @param cuerpo
     * @param tags
     * @return
     * @throws agendavital.modelo.excepciones.ConexionBDIncorrecta
     * @throws java.sql.SQLException
     */
    public static Noticia Insert(String titulo, String link, String fecha, String categoria, String cuerpo, ArrayList<String> tags) throws ConexionBDIncorrecta, SQLException {
        int nuevoId = 0;
        try (Connection conexion = ConfigBD.conectar()) {
            String insert = "INSERT INTO noticias (titulo, link, fecha, categoria, cuerpo)";
            insert += String.format(" VALUES (%s, %s, %s, %s, %s)", ConfigBD.String2Sql(titulo, false),
                    ConfigBD.String2Sql(link, false), ConfigBD.String2Sql((fecha), false), ConfigBD.String2Sql(categoria, false),
                    ConfigBD.String2Sql(cuerpo, false));
            int executeUpdate = conexion.createStatement().executeUpdate(insert);
            nuevoId = ConfigBD.LastId("noticias");
            for (String tag : tags) {
                String consultaTag = String.format("SELECT id_etiqueta from etiquetas WHERE Nombre = %s;", ConfigBD.String2Sql(tag, false));
                ResultSet rs = conexion.createStatement().executeQuery(consultaTag);
                rs.next();
                int idTag = (rs.getRow() == 1) ? rs.getInt("id_etiqueta") : -1;
                if (idTag == -1) {
                    String insertTag = String.format("INSERT INTO etiquetas (nombre) VALUES (%s);", ConfigBD.String2Sql(tag, false));
                    conexion.createStatement().executeUpdate(insertTag);
                    idTag = ConfigBD.LastId("etiquetas");
                }
                String insertNoticiaEtiqueta = String.format("INSERT INTO momentos_noticias_etiquetas (id_noticia, id_etiqueta) VALUES(%d, %d);", nuevoId, idTag);
                conexion.createStatement().executeUpdate(insertNoticiaEtiqueta);
            }
        } catch (SQLException e) {
        }
        return new Noticia(nuevoId);
    }

    /**
     * Funcion modificadora de noticias
     *
     * @throws agendavital.modelo.excepciones.ConexionBDIncorrecta
     */
    public void Update() throws ConexionBDIncorrecta {
        try (Connection conexion = ConfigBD.conectar()) {
            String update = String.format("UPDATE noticias SET titulo = %s, link = %s, fecha = %s, categoria = %s, cuerpo = %s WHERE id_noticia = %d;", ConfigBD.String2Sql(getTitulo(), false), ConfigBD.String2Sql(getLink(), false), ConfigBD.String2Sql(getFecha(), false), ConfigBD.String2Sql(getCategoria(), false), ConfigBD.String2Sql(getCuerpo(), false), getId());
            conexion.createStatement().executeUpdate(update);
            String eliminaMNE = String.format("DELETE from momentos_noticias_etiquetas WHERE id_noticia = %d", id);
            conexion.createStatement().executeUpdate(eliminaMNE);
            for (String tag : tags) {
                String consultaTag = String.format("SELECT id_etiqueta from etiquetas WHERE Nombre = %s;", ConfigBD.String2Sql(tag, false));
                ResultSet rs = conexion.createStatement().executeQuery(consultaTag);
                rs.next();
                int idTag = (rs.getRow() == 1) ? rs.getInt("id_etiqueta") : -1;
                if (idTag == -1) {
                    String insertTag = String.format("INSERT INTO etiquetas (nombre) VALUES (%s);", ConfigBD.String2Sql(tag, false));
                    conexion.createStatement().executeUpdate(insertTag);
                    idTag = ConfigBD.LastId("etiquetas");
                }
                String insertaMNE = String.format("INSERT INTO momentos_noticias_etiquetas (id_noticia, id_etiqueta) VALUES(%d, %d)", id, idTag);
                conexion.createStatement().executeUpdate(insertaMNE);
            }
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }
    }

    /**
     * Funcion que elimina las noticias
     *
     * @throws agendavital.modelo.excepciones.ConexionBDIncorrecta
     */
    public void Delete() throws ConexionBDIncorrecta {
        try (Connection conexion = ConfigBD.conectar()) {
            String deleteMNE = String.format("UPDATE momentos_noticias_etiquetas SET id_noticia=NULL WHERE id_noticia = %d;", getId());
            conexion.createStatement().executeUpdate(deleteMNE);
            String delete = String.format("Delete from noticias WHERE id_noticia = %d;", getId());
            conexion.createStatement().executeUpdate(delete);
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }
    }

    public static ArrayList<Noticia> getNoticiasFeedZilla() throws java.text.ParseException, ErrorConexionFeedzilla {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://api.feedzilla.com/v1/categories/100/articles.json?count=13");
        httpGet.setHeader("Content-Type", "application/json");
        String respStr = null;
        ArrayList<Noticia> arrayNoticias = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            respStr = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(respStr);
            JSONArray noticias = jsonObject.getJSONArray("articles");
            arrayNoticias = new ArrayList<>();
            for (int i = 0; i < noticias.length(); i++) {
                Noticia noticia = new Noticia();
                JSONObject jsonNoticia = noticias.getJSONObject(i);
                noticia.setTitulo(jsonNoticia.getString("title"));
                noticia.setCategoria("Noticias Internacionales");
                noticia.setCuerpo(jsonNoticia.getString("summary"));
                noticia.setLink(jsonNoticia.getString("url"));
                noticia.setFecha(UtilidadesNoticia.formatearFecha(jsonNoticia.getString("publish_date")));
                arrayNoticias.add(noticia);
            }

        } catch (IOException | ParseException | JSONException e) {
            throw new ErrorConexionFeedzilla();
        }
        return arrayNoticias;
    }

   public static TreeMap<LocalDate, ArrayList<Noticia>> buscar(String _parametro) throws ConexionBDIncorrecta, SQLException {
       final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
       ArrayList<String> _tags = UtilidadesBusqueda.separarPalabras(_parametro);
        TreeMap<LocalDate, ArrayList<Noticia>> busqueda = null;
        try (Connection conexion = ConfigBD.conectar()) {
            busqueda = new TreeMap<>();
            for (String _tag : _tags) {
                String tag = ConfigBD.String2Sql(_tag, true);
                String buscar = String.format("SELECT id_Noticia, fecha from noticias "
                        + "WHERE id_noticia IN (SELECT id_noticia from momentos_noticias_etiquetas "
                        + "WHERE id_etiqueta IN (SELECT id_etiqueta from etiquetas WHERE nombre LIKE %s)) "
                        + "OR titulo LIKE %s "
                        + "OR cuerpo LIKE %s "
                        + "OR categoria LIKE %s "
                        + "OR fecha LIKE %s; " , tag, tag, tag, tag,tag);
                ResultSet rs = conexion.createStatement().executeQuery(buscar);
                while (rs.next()) {
                    LocalDate date = LocalDate.parse(rs.getString("fecha"), dateFormatter);
                    Noticia insertarNoticia = new Noticia(rs.getInt("id_noticia"));
                    if(busqueda.containsKey(date)){
                    boolean encontrado = false;
                    for(int i = 0; i < busqueda.get(date).size() && !encontrado; i++)
                        if(busqueda.get(date).get(i).getId() == insertarNoticia.getId()) encontrado = true;
                    if(!encontrado) busqueda.get(date).add(insertarNoticia);
                    }
                    else{
                        busqueda.put(date, new ArrayList<>());
                        busqueda.get(date).add(insertarNoticia);
                    }
                }
                
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        Iterator it = busqueda.keySet().iterator();
        return busqueda;
    }

}
