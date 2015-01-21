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
 * @author Ramón
 */
public class Etiqueta {
    
    public static ArrayList<String> getEtiqueta(){
        ArrayList<String> etiquetas = null;
        try(Connection conexion = ConfigBD.conectar()){
            etiquetas = new ArrayList<>();
            ResultSet rs = null;
            String consulta = "SELECT nombre from etiquetas ORDER BY id_etiqueta DESC LIMIT 6";
            rs = conexion.createStatement().executeQuery(consulta);
            while(rs.next()){
                etiquetas.add(rs.getString("nombre"));
            }
        } catch(SQLException e){
        }
        return etiquetas;
    }
    
}
