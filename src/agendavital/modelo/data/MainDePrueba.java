/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.data;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ramon
 */
public class MainDePrueba {
    public static void main(String [] args) throws SQLException{
        String titulo = "EY";
        String link = "http://";
        String categoria = "Noticias Internacionales";
        String fecha = "28-09-1992";
        String cuerpo = "lllll";
        String tag = "ramon";
        String tag2 = "ramirez";
        ArrayList<String> tags = new ArrayList<String>();
        tags.add(tag);
        tags.add(tag2);
       Noticia.Insert(titulo, link, fecha, categoria, cuerpo, tags);
        
        
    }
}
