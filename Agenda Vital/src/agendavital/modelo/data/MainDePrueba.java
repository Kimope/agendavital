/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.data;

import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ramon
 */
public class MainDePrueba {
   

    public static void main(String[] args) throws SQLException, IOException, java.text.ParseException, ConexionBDIncorrecta {
        /*  String fecha = "01-11-2014";
         String descripcion = "HOLAAAALALALALAA";
         String color = "red";
         Momento momento = Momento.insert(fecha, descripcion, color, 1);
         File origen = new File("/home/ramon/Linuxdoc-Ejemplo.pdf");
         momento.asociarDocumento(origen);*/
        
        ArrayList<Noticia> momentos = Noticia.buscar("irez");
        for (Noticia noticia : momentos) {
            System.out.println(noticia.getTitulo());
        }
       
    }

}
