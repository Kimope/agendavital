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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLVerImagenController implements Initializable {
    @FXML
    private AnchorPane anclaje;
    @FXML
    private ImageView im;

    /**
     * Initializes the controller class.
     */
    @Override
            public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void imprimir_imagen(Image imagen){
        
        im.setImage(imagen);
    }

    @FXML
    private void moverPantalla2(MouseEvent event) {
    }

    @FXML
    private void moverPantalla(MouseEvent event) {
    }
    
}
