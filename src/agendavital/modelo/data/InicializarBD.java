/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.data;

import agendavital.modelo.data.Noticia;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
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

    public static void cargarXMLS() throws JDOMException, IOException, SQLException, ConexionBDIncorrecta, URISyntaxException {
        SAXBuilder builder = new SAXBuilder();
        File xmlFolder = new File (InicializarBD.class.getResource("inicializarBD").toURI());
        System.out.println(xmlFolder.getAbsolutePath());
        File[] xmlFile = xmlFolder.listFiles();
        for(int i = 0; i < xmlFile.length; i++){
            System.out.println(xmlFile[i].getName());
        }
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
