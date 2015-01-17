/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import agendavital.AgendaVital;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLAnadirNoticiaController implements Initializable {

    @FXML
    private ComboBox cbcategoria;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //Se le dan los valores a los campos de selección
        cbcategoria.getItems().addAll("Noticias Nacionales", "Noticias Internacionales", "Mis Noticias");
        cbcategoria.getSelectionModel().selectFirst();
    } 
     @FXML
    public void cerrar() throws IOException
    {
       
    }
  
    public void registra_noticia(){
        
    }
}
