/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.data;

import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import agendavital.modelo.util.ConfigBD;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import org.jdom2.JDOMException;

/**
 *
 * @author ramon
 */
public class MainDePrueba {
   

    public static void main(String[] args) throws SQLException, IOException, java.text.ParseException, ConexionBDIncorrecta, URISyntaxException, JDOMException {
        /*  String fecha = "01-11-2014";
         String descripcion = "HOLAAAALALALALAA";
         String color = "red";
         Momento momento = Momento.insert(fecha, descripcion, color, 1);
         File origen = new File("/home/ramon/Linuxdoc-Ejemplo.pdf");
         momento.asociarDocumento(origen);*/
     /*   ArrayList<String> tags = new ArrayList<>();
        tags.add("prueba");
        tags.add("marca");
        Noticia.Insert("Prueba 3", "http://www.as.com", "12-01-2015", "Noticias Nacionales", "Esto es una prueba", tags);
       */
        ConfigBD.inicializarEstructura();
        
    }

}
