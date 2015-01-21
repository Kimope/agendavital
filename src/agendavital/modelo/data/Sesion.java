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
import java.util.Date;

/**
 *
 * @author Ramón
 */
public class Sesion {
    
    public static void setSesion(String fecha) throws SQLException{
        try(Connection conexion = ConfigBD.conectar()){
            String insert = "INSERT INTO sesiones (fecha) VALUES ("+ConfigBD.String2Sql(fecha, false)+");";
            conexion.createStatement().executeUpdate(insert);
        }
    }
    
    public static LocalDate getFechaSesion() throws SQLException{
        ResultSet rs = null;
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try(Connection conexion = ConfigBD.conectar()){
            String consulta = "SELECT fecha from sesiones WHERE id_sesion = "+ConfigBD.LastId("sesiones");
            rs = conexion.createStatement().executeQuery(consulta);
            rs.next();
            String fecha = rs.getString("fecha");
            return LocalDate.parse(fecha, dateFormatter);
        }
    }
}
