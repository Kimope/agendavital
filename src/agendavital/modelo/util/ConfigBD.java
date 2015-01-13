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

    private static String ruta = "";
    private static final String SO = System.getProperty("os.name").toLowerCase();
    
    public static boolean inicializarEstructura() throws IOException{
        File origen = new File("BD/agenda.db");
        File destino = null;
        if(SO.contains("win")){
            File Windows = new File(System.getenv("APPDATA")+"/AgendaVital");
            Windows.mkdir();
            destino = new File(System.getenv("APPDATA")+"/AgendaVital/agenda.db");
        }
        else if(SO.contains("nix")|| SO.contains("nux") || SO.contains("aix") || SO.contains("mac")){
            File Linux = new File(System.getProperty( "user.home" )+"/.AgendaVital");
            Linux.mkdir();
            destino = new File(System.getProperty( "user.home" )+"/.AgendaVital/agenda.db");
        }
        else return false;  
        copyFile(origen, destino);
        ruta = destino.getAbsolutePath();
        return true;
    }
    
    public static boolean estructuraInicializada(){
        File BD = null;
        System.out.println(System.getenv("APPDATA")+"/AgendaVital/agenda.db");
        if(SO.contains("win")){
            BD = new File(System.getenv("APPDATA")+"/AgendaVital/agenda.db");
        }
        else if(SO.contains("nix")|| SO.contains("nux") || SO.contains("aix") || SO.contains("mac")){
            BD = new File(System.getProperty( "user.home" )+"/.AgendaVital/agenda.db");
        }
        else return false;
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
        }
        return conexion;
    }
    
    public static int LastId(String _tabla) throws SQLException{
         Connection conexion = null;
         ResultSet rs = null;
        try{
            conexion = conectar();
            rs = conexion.createStatement().executeQuery(String.format("select seq from sqlite_sequence where name=%s;", ConfigBD.String2Sql(_tabla, false)));
            rs.next();
            return rs.getInt(1);
        }catch(SQLException e){
            throw e;
        }
        finally{
            if(conexion != null) conexion.close();
        }
    }
    
     /**Metodo convertidor de cadena a formato SQL
   * 
   * @param sValue Cadena
   * @param bAddWildcards true o false en función de si es necesario añadir comillas en los extremos de la cadena
   * @return Cadena convertida
   */
  public static String String2Sql(String sValue, boolean bAddWildcards)
  {
	  String sCadena = sValue.trim().replace("\'", "\''");
	  if(bAddWildcards) return "'%%"+sCadena+"%%'";
	  else return "'"+sCadena+"'";
  }
  
  @SuppressWarnings("empty-statement")
    public static void copyFile(File sourceFile, File destFile) throws IOException {
    if(!destFile.exists()) {
        destFile.createNewFile();
    }
 
    FileChannel origen = null;
    FileChannel destino = null;
    try {
        origen = new FileInputStream(sourceFile).getChannel();
        destino = new FileOutputStream(destFile).getChannel();
 
        long count = 0;
        long size = origen.size();             
        while((count += destino.transferFrom(origen, count, size-count))<size);
    }
    finally {
        if(origen != null) {
            origen.close();
        }
        if(destino != null) {
            destino.close();
        }
    }
}
    
}
