/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.data;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import agendavital.modelo.util.ConfigBD;

/**
 *
 * @author Ramón
 */
public class InicializarBD {

    public static void cargarXMLS() throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        File xmlFolder = new File("Noticias");
        File[] xmlFile = xmlFolder.listFiles();
        for (File xmlFile1 : xmlFile) {
            Document document = (Document) builder.build(xmlFile1);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("Noticia");
            for (Object list1 : list) {
                Element noticia = (Element) list1;
                List noticiaCampos = noticia.getChildren();
                String titulo = noticia.getChildTextTrim("titulo");
                System.out.println("Titulo: " + titulo);
                String fecha = noticia.getChildTextTrim("fecha");
                System.out.println("Fecha: " + fecha);
                String link = noticia.getChildTextTrim("link");
                System.out.println("Link: " + link);
                List categorias = noticia.getChildren("categoria");
                for (Object categorias1 : categorias) {
                    Element categoria = (Element) categorias1;
                    String categ = categoria.getTextTrim();
                    System.out.println("Categoria: " + categ);
                }
                List tags = noticia.getChildren("tag");
                for (Object tags1 : tags) {
                    Element tag = (Element) tags1;
                    String Tag = tag.getTextTrim();
                    System.out.println("Categoria: " + Tag);
                }
            }
        }
    }
}
