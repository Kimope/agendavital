/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.util;

import agendavital.modelo.data.Momento;
import agendavital.modelo.data.Usuario;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ramon
 */
public class UsuarioLogueado {
    public static Usuario logueado;

    public static Usuario getLogueado() {
        return logueado;
    }

    public static void setLogueado(Usuario logueado) {
        UsuarioLogueado.logueado = logueado;
        File destino = new File("Momentos");
        if (!destino.exists()) {
            destino.mkdir();
        }
        File destino2 = new File("Momentos/" + UsuarioLogueado.getLogueado().getNick());
        if (!destino2.exists()) {
            destino2.mkdir();
        }

    }
    public UsuarioLogueado(Usuario _usuario){
        logueado = _usuario;
    }

    public UsuarioLogueado() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
    public static ArrayList<File> getTodasImagenes() throws ConexionBDIncorrecta{
       ResultSet rs = null;
        ArrayList<File> imagenes = null;
        try (Connection conexion = ConfigBD.conectar()) {
            imagenes = new ArrayList<>();
            String consulta = String.format("SELECT ruta_doc from documentos WHERE id_documento IN (SELECT id_documento from momentos WHERE id_usuario = %s);", ConfigBD.String2Sql(getLogueado().getNick(), false));
            rs = conexion.createStatement().executeQuery(consulta);
            while (rs.next()) {
                imagenes.add(new File(rs.getString("ruta_doc")));
            }
            return imagenes;
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }
    }
    
}
