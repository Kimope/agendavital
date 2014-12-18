/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLAnadirNoticiaController implements Initializable {

    @FXML private ComboBox cbcategoria;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //Se le dan los valores a los campos de selección
        cbcategoria.getItems().addAll("Noticias Nacionales", "Noticias Internacionales", "Mis Noticias");
        cbcategoria.getSelectionModel().selectFirst();
    }      
}
