/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ramón
 */
public class ConfigBD {

    private static final String ruta = "BD/agenda.db";

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
    
    public static int LastId() throws SQLException{
         Connection conexion = null;
         ResultSet rs = null;
        try{
            conexion = conectar();
            rs = conexion.createStatement().executeQuery("select last_insert_rowid();");
            rs.next();
        }catch(SQLException e){
            throw e;
        }
        finally{
            if(conexion != null) conexion.close();
        }
        return rs.getByte(1);
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
    
}
