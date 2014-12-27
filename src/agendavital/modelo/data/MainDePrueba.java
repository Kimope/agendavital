/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.data;

import agendavital.modelo.util.UsuarioLogueado;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ramon
 */
public class MainDePrueba {
    public static void main(String [] args) throws SQLException, IOException{
      /*  String fecha = "01-11-2014";
        String descripcion = "HOLAAAALALALALAA";
        String color = "red";
       Momento momento = Momento.insert(fecha, descripcion, color, 1);
        File origen = new File("/home/ramon/Linuxdoc-Ejemplo.pdf");
        momento.asociarDocumento(origen);*/
        File file = new File(System.getProperty( "user.home" )+"/KK");
        System.out.println(file.getAbsolutePath());
        
        
    }
}
