/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLConfirmarBorrarNoticiaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void confirmar(ActionEvent event) throws ConexionBDIncorrecta {
        
        FXMLNoticiaController.ventanaConfirmarBorrarr.close();
        FXMLNoticiaController.borrar();
        FXMLMomentosyNoticiasController.ventanaNoticia.close();
        
    }
    
     @FXML
    private void cancelar(ActionEvent event)
    {
        FXMLNoticiaController.ventanaConfirmarBorrarr.close();
    }
}
