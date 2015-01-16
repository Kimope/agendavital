/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.data;

import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import agendavital.modelo.util.ConfigBD;
import agendavital.modelo.util.UsuarioLogueado;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author ramon
 */
public class Momento {

    private int id;
    private String titulo;
    private String fecha;
    private String descripcion;
    private int id_documento;
    private int id_noticia;
    private String color;
    private ArrayList<Integer> tags;

    /**Constructor
     * 
     * @param _id
     * @throws SQLException 
     * @throws agendavital.modelo.excepciones.ConexionBDIncorrecta 
     */
    public Momento(int _id) throws SQLException, ConexionBDIncorrecta {
        id = _id;
        tags = new ArrayList<>();
        Connection conexion = null;
        ResultSet rs = null;
        try {
            conexion = ConfigBD.conectar();
            rs = conexion.createStatement().executeQuery(String.format("SELECT * FROM momentos WHERE id_momento = %d;", id));
            rs.next();
            this.titulo = rs.getString("titulo");
            this.fecha = (rs.getString("fecha"));
            this.descripcion = (rs.getString("descripcion"));
            this.id_documento = (rs.getInt("id_documento"));
            this.id_noticia = (rs.getInt("id_noticia"));
            rs = conexion.createStatement().executeQuery(String.format("SELECT id_etiqueta from momentos_noticias_etiquetas WHERE id_momento = %d", id));
            while (rs.next()) {
                tags.add(rs.getInt("id_etiqueta"));
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
    
    
    public String getTitulo() {
        return titulo;
    }

    public ArrayList<Integer> getTags() {
        return tags;
    }
    
     public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setTags(ArrayList<Integer> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_documento() {
        return id_documento;
    }

    public void setId_documento(int id_documento) {
        this.id_documento = id_documento;
    }

    public int getId_noticia() {
        return id_noticia;
    }

    public void setId_noticia(int id_noticia) {
        this.id_noticia = id_noticia;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    /**Funcion que devuelves los momentos dada una fecha
     * 
     * @param fecha Fecha en formato DD-MM-AAAA
     * @return 
     * @throws agendavital.modelo.excepciones.ConexionBDIncorrecta 
     */
    public static ArrayList<Momento> Select(String fecha) throws ConexionBDIncorrecta {
        ResultSet rs = null;
        ArrayList<Momento> momentos = null;
        try (Connection conexion = ConfigBD.conectar()) {
            momentos = new ArrayList<>();
            String consulta = String.format("SELECT id_momento from momentos WHERE fecha = %s AND id_usuario = %s;", ConfigBD.String2Sql(fecha, false), ConfigBD.String2Sql(UsuarioLogueado.getLogueado().getNick(), false));
            rs = conexion.createStatement().executeQuery(consulta);
            while (rs.next()) {
                momentos.add(new Momento(rs.getInt("id_momento")));
            }
            return momentos;
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }
    }
    

    /**Funcion que inserta momentos
     * 
     * @param fecha
     * @param descripcion
     * @param color
     * @param id_noticia
     * @return
     * @throws agendavital.modelo.excepciones.ConexionBDIncorrecta 
     */
    public static Momento insert(String titulo, String fecha, String descripcion, String color, int id_noticia) throws ConexionBDIncorrecta {
        int nuevoId = 0;
        Momento nuevo = null;
        try (Connection conexion = ConfigBD.conectar()) {
            String insert = String.format("INSERT INTO momentos (titulo, fecha, descripcion, id_noticia, color, id_usuario) VALUES (%s, %s, %s, %d, %s, %s);", ConfigBD.String2Sql(titulo, false), ConfigBD.String2Sql(fecha, false), ConfigBD.String2Sql(descripcion, false), id_noticia, ConfigBD.String2Sql(color, false), ConfigBD.String2Sql(UsuarioLogueado.getLogueado().getNick(), false));
            int executeUpdate = conexion.createStatement().executeUpdate(insert);
            nuevoId = ConfigBD.LastId("momentos");
            String asociarConNoticia = String.format("UPDATE momentos_noticias_etiquetas SET id_momento = %d WHERE id_noticia = %d", nuevoId, id_noticia);
            conexion.createStatement().executeUpdate(asociarConNoticia);
            nuevo = new Momento(nuevoId);
            return nuevo;
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }
    }
    
     /**Funcion que inserta momentos
     * 
     * @param fecha
     * @param descripcion
     * @param color
     * @return
     * @throws agendavital.modelo.excepciones.ConexionBDIncorrecta 
     */
    public static Momento insert(String titulo, String fecha, String descripcion, String color) throws ConexionBDIncorrecta {
        int nuevoId = 0;
        Momento nuevo = null;
        try (Connection conexion = ConfigBD.conectar()) {
            String insert = String.format("INSERT INTO momentos (titulo, fecha, descripcion, color, id_usuario) VALUES (%s, %s, %s, %s, %s);", ConfigBD.String2Sql(titulo, false), ConfigBD.String2Sql(fecha, false), ConfigBD.String2Sql(descripcion, false), ConfigBD.String2Sql(color, false), ConfigBD.String2Sql(UsuarioLogueado.getLogueado().getNick(), false));
            int executeUpdate = conexion.createStatement().executeUpdate(insert);
            nuevoId = ConfigBD.LastId("momentos");
            nuevo = new Momento(nuevoId);
            return nuevo;
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }
    }

    /**Funcion modificadora
     * 
     * @throws agendavital.modelo.excepciones.ConexionBDIncorrecta
     */
    public void Update() throws ConexionBDIncorrecta {
        try (Connection conexion = ConfigBD.conectar()) {
            String update = String.format("UPDATE noticias SET titulo = %s, fecha = %s, descripcion = %s, color = %s WHERE id_momento = %d;", ConfigBD.String2Sql(getTitulo(), false), ConfigBD.String2Sql(getFecha(), false), ConfigBD.String2Sql(getDescripcion(), false), ConfigBD.String2Sql(getColor(), false), getId());
            conexion.createStatement().executeUpdate(update);
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }
    }
    
    /**Funcion que elimina el momento
     * 
     * @throws agendavital.modelo.excepciones.ConexionBDIncorrecta 
     */
    public void Delete() throws ConexionBDIncorrecta {
        try (Connection conexion = ConfigBD.conectar()) {
            String delete = String.format("Delete from momentos WHERE id_momento = %d;", getId());
            conexion.createStatement().executeUpdate(delete);
            String deleteTag = String.format("Delete from momentos_noticias_etiquetas WHERE id_momento = %d;", getId());
            conexion.createStatement().executeUpdate(deleteTag);
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }
    }
    
    /**Funcion que asocia un documento con un momento
     * 
     * @param Documento
     * @return
     * @throws IOException
     * @throws agendavital.modelo.excepciones.ConexionBDIncorrecta 
     */
    public boolean asociarDocumento(File Documento) throws IOException,  ConexionBDIncorrecta{
        File destino = new File("Momentos");
        if(!destino.exists()) destino.mkdir();
        File destino2 = new File("Momentos/"+UsuarioLogueado.getLogueado().getNick());
        if(!destino2.exists()) destino2.mkdir();
        System.out.println(destino.getAbsolutePath());
        File destino3 = new File("Momentos/"+UsuarioLogueado.getLogueado().getNick()+"/"+Documento.getName());
        ConfigBD.copyFile(Documento, destino3);
        ResultSet rs = null;
        try (Connection conexion = ConfigBD.conectar()) {
        String insertDocumento = String.format("INSERT INTO Documentos (ruta_doc) VALUES (%s);", ConfigBD.String2Sql(destino3.getCanonicalPath(), false));
        conexion.createStatement().executeUpdate(insertDocumento);
        int idDoc = ConfigBD.LastId("documentos");
        String update = String.format("UPDATE momentos SET id_documento = %d WHERE id_momento = %d;",  idDoc, getId());
        conexion.createStatement().executeUpdate(update);
        return true;
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }
    }
    
    /**Funcion coloreadora del calendario
     * 
     * @return
     * @throws agendavital.modelo.excepciones.ConexionBDIncorrecta 
     */
     public static ArrayList<Pair<LocalDate, String>> getNoticiasFecha() throws ConexionBDIncorrecta {
        ResultSet rs = null;
        ArrayList<Pair<LocalDate, String>> fechasMomentos = new ArrayList<>();
        try (Connection conexion = ConfigBD.conectar()) {
            String consulta = String.format("SELECT fecha, color from momentos WHERE id_usuario = %s;", ConfigBD.String2Sql(UsuarioLogueado.getLogueado().getNick(), false));
            rs = conexion.createStatement().executeQuery(consulta);
            while (rs.next()) {
                String fecha = rs.getString("fecha");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(fecha, formatter);
                String color = rs.getString("color");
                fechasMomentos.add(new Pair<>(date, color));
            }
        } catch (SQLException e) {
           throw new ConexionBDIncorrecta();
        }
        return fechasMomentos;
    }
     
      public static ArrayList<Momento> buscar(String _tag) throws ConexionBDIncorrecta {
        ArrayList<Momento> busqueda = null;
        try (Connection conexion = ConfigBD.conectar()){
            busqueda = new ArrayList<>();
            String buscar = String.format("SELECT id_Momento from momentos_noticias_etiquetas "
                    + "WHERE id_Etiqueta IN (SELECT id_Etiqueta from etiquetas "
                    + "WHERE nombre LIKE %s) AND id_Momento IN (SELECT id_Momento from momentos WHERE id_usuario = %s);", ConfigBD.String2Sql(_tag, true), ConfigBD.String2Sql(UsuarioLogueado.getLogueado().getNick(), false));
            ResultSet rs = conexion.createStatement().executeQuery(buscar);
            while (rs.next()) {
                busqueda.add(new Momento(rs.getInt("id_Momento")));
            }
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }
        return busqueda;
    }
}
