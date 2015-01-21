/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.data;

import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import agendavital.modelo.util.ConfigBD;
import agendavital.modelo.util.UsuarioLogueado;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import org.jdom2.JDOMException;

/**
 *
 * @author ramon
 */
public class MainDePrueba {
   

    public static void main(String[] args) throws SQLException, IOException, java.text.ParseException, ConexionBDIncorrecta, URISyntaxException, JDOMException {
        UsuarioLogueado.setLogueado(new Usuario ("rrgonzalez50"));
        TreeMap<LocalDate, ArrayList<Momento>> busqueda = Momento.buscar("a");
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        busqueda.keySet().stream().map((date) -> {
            System.out.println("FECHA: "+dateFormatter.format(date));
            return date;
        }).forEach((date) -> {
            for(int i = 0; i < busqueda.get(date).size(); i++){
                System.out.println("Titulo: "+busqueda.get(date).get(i).getTitulo());
            }
        });
        
    }

}
