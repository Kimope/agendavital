/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import static agendavital.vista.FXMLMomentoController.imagen;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public void imprimir_imagen(String ruta){
        File fileImagen = new File(ruta);
            InputStream is = null;
            try {
                is = new FileInputStream(fileImagen);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLRegistroPreguntaUnoController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            imagen = new Image(is);
            im.setImage(imagen);
 }

    @FXML
    private void moverPantalla2(MouseEvent event) {
    }

    @FXML
    private void moverPantalla(MouseEvent event) {
    }
    
}
