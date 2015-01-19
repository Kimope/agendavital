/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLNoticiaAñadidaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    public void aceptar() throws IOException {
        FXMLAnadirNoticiaController.ventanaAnadidoo.close();
        FXMLPrincipalController.ventanaAnadirNoticia.close();
    }
    
}
