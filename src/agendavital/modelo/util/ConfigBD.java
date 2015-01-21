/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ramón
 */
public class ConfigBD {

    private static String ruta = "BD/agenda.db";
    private static final String SO = System.getProperty("os.name").toLowerCase();

    public static boolean inicializarEstructura() throws IOException{
        File origen = new File("BD2/agenda.db");
        File destino = null;
        File carpetaMomentos = null;
        if(SO.contains("win")){
            File Windows = new File(System.getenv("APPDATA")+"/AgendaVital");
            Windows.mkdir();
            destino = new File(System.getenv("APPDATA")+"/AgendaVital/agenda.db");
           carpetaMomentos = new File(System.getenv("APPDATA")+"/AgendaVital/Momentos");
        }
        else if(SO.contains("nix")|| SO.contains("nux") || SO.contains("aix") || SO.contains("mac")){
            File Linux = new File(System.getProperty( "user.home" )+"/.AgendaVital");
            Linux.mkdir();
            destino = new File(System.getProperty( "user.home" )+"/.AgendaVital/agenda.db");
            carpetaMomentos = new File(System.getProperty( "user.home" )+"/.AgendaVital/Momentos");
        }
        else return false;  
        copyFile(origen, destino);
        ruta = destino.getAbsolutePath();
        carpetaMomentos.mkdir();
        return true;
    }
    
    public static void crearTablas() throws SQLException {
        Connection conexion = conectar();
        String tabla = "CREATE TABLE documentos(";
        tabla += "id_documento INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,";
        tabla += "ruta_doc TEXT);";
        tabla += ";";
        tabla += "CREATE TABLE momentos_noticias_etiquetas(";
        tabla += "id_momento_noticia_etiqueta INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,";
        tabla += "id_momento INT,";
        tabla += "id_noticia INT,";
        tabla += "id_etiqueta INT,";
        tabla += "FOREIGN KEY(id_momento) REFERENCES momentos(id_momento),";
        tabla += "FOREIGN KEY(id_noticia) REFERENCES noticias(id_noticia),";
        tabla += "FOREIGN KEY(id_etiqueta) REFERENCES etiquetas(id_etiqueta));";
        tabla += ";";
        tabla += "CREATE TABLE usuarios(";
        tabla += "nick VARCHAR(50) PRIMARY KEY,";
        tabla += "nombre VARCHAR(100),";
        tabla += "apellido VARCHAR(200),";
        tabla += "contrasena VARCHAR(50),";
        tabla += "fecha_nac TEXT, ciudad varchar(50), correo varchar(70));";
        tabla += ";";
        tabla += "CREATE TABLE momentos(";
        tabla += "id_momento INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,";
        tabla += "fecha TEXT,";
        tabla += "descripcion TEXT,";
        tabla += "id_documento INT,";
        tabla += "id_noticia INT,";
        tabla += "FOREIGN KEY(id_documento) REFERENCES documentos(id_documento),";
        tabla += "FOREIGN KEY(id_noticia) REFERENCES noticias(id_noticia));";
        tabla += ";";
        tabla += "CREATE TABLE noticias (";
        tabla += "id_noticia INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,";
        tabla += "titulo TEXT,";
        tabla += "link TEXT,";
        tabla += "cuerpo TEXT,";
        tabla += "categoria TEXT,";
        tabla += "fecha TEXT";
        tabla += ");";
        tabla += "CREATE TABLE etiquetas (";
        tabla += "id_etiqueta INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,";
        tabla += "nombre TEXT";
        tabla += ");";
        conexion.createStatement().executeUpdate(tabla);
    }

    public static boolean estructuraInicializada() {
        File BD = null;
        if (SO.contains("win")) {
            BD = new File(System.getenv("APPDATA") + "/AgendaVital/agenda.db");
        } else if (SO.contains("nix") || SO.contains("nux") || SO.contains("aix") || SO.contains("mac")) {
            BD = new File(System.getProperty("user.home") + "/.AgendaVital/agenda.db");
        } else {
            return false;
        }
        ruta = BD.getAbsolutePath();
        return (BD.exists());
    }

    public static Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
        }
        try {
            conexion = DriverManager.getConnection("jdbc:sqlite:" + ruta);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    private static String listaIDS(String _tabla) {
        switch (_tabla) {
            case "etiquetas":
                return "id_etiqueta";
            case "noticias":
                return "id_noticia";
            case "momentos_noticias_etiquetas":
                return "id_momento_noticia_etiqueta";
            case "preguntas":
                return "id_pregunta";
            case "momentos":
                return "id_momento";
            case "documentos":
                return "id_documento";
            case "sesiones":
                return "id_sesion";
        }
        return "";
    }

    public static int LastId(String _tabla) throws SQLException {
        Connection conexion = null;
        ResultSet rs = null;
        try {
            conexion = conectar();
            rs = conexion.createStatement().executeQuery(String.format("select MAX(%s) from %s;", listaIDS(_tabla), ConfigBD.String2Sql(_tabla, false)));
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    /**
     * Metodo convertidor de cadena a formato SQL
     *
     * @param sValue Cadena
     * @param bAddWildcards true o false en función de si es necesario añadir
     * comillas en los extremos de la cadena
     * @return Cadena convertida
     */
    public static String String2Sql(String sValue, boolean bAddWildcards) {
        String sCadena = sValue.trim().replace("\'", "\''");
        if (bAddWildcards) {
            return "'%%" + sCadena + "%%'";
        } else {
            return "'" + sCadena + "'";
        }
    }

    @SuppressWarnings("empty-statement")
    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel origen = null;
        FileChannel destino = null;
        try {
            origen = new FileInputStream(sourceFile).getChannel();
            destino = new FileOutputStream(destFile).getChannel();

            long count = 0;
            long size = origen.size();
            while ((count += destino.transferFrom(origen, count, size - count)) < size);
        } finally {
            if (origen != null) {
                origen.close();
            }
            if (destino != null) {
                destino.close();
            }
        }
    }

}
