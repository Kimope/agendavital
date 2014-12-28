/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.data;

import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import java.util.ArrayList;

/**
 *
 * @author Ramón
 */
public class InicializarBD {

    public static void cargarXMLS() throws JDOMException, IOException, SQLException, ConexionBDIncorrecta {
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
                String fecha = noticia.getChildTextTrim("fecha");
                String link = noticia.getChildTextTrim("link");
                String categorias = noticia.getChildTextTrim("categoria"); 
                List tags = noticia.getChildren("tag");
                ArrayList<String> etiquetas = new ArrayList<>();
                for (Object tags1 : tags) {
                    Element tag = (Element) tags1;
                    etiquetas.add(tag.getTextTrim());
                }
                Noticia.Insert(titulo, link, fecha, categorias, fecha, etiquetas);
            }
        }
    }
}
