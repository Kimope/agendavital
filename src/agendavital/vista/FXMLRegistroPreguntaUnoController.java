/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLRegistroPreguntaUnoController implements Initializable {
    @FXML
    private AnchorPane anclaje;
    @FXML
    private Circle circulomin;
    @FXML
    private Line lineamin;
    @FXML
    private Circle circulocerr;
    @FXML
    private Line lineacerrar1;
    @FXML
    private Line lineacerrar2;
    @FXML
    private Label contraseña2label;
    @FXML
    private Label contraseña1label;
    @FXML
    private Label correolabel;
    @FXML
    private Label ciudadlabel;
    @FXML
    private Label apellidolabel;
    @FXML
    private Label nombrelabel;
    @FXML
    private ImageView imgImagen; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File file = new File("imagenes/bebe.jpg");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLRegistroPreguntaUnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        double width = 191;
        double heigth = 167;
        Image imagen = new Image(is,width,heigth,false,true);
        imgImagen.setImage(imagen);
    }    
    
    @FXML
    public void anadir()
    {
        FileChooser chooser = new FileChooser();

        File file = chooser.showOpenDialog(new Stage());
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLRegistroPreguntaUnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        double width = 191;
        double heigth = 167;
        Image imagen = new Image(is,width,heigth,false,true);
        imgImagen.setImage(imagen);
    }

    @FXML
    private void minimizarSalida(MouseEvent event) {
    }

    @FXML
    private void minimizarEncima(MouseEvent event) {
    }

    @FXML
    private void minimizar(MouseEvent event) {
    }

    @FXML
    private void cerrarSalida(MouseEvent event) {
    }

    @FXML
    private void cerrarEncima(MouseEvent event) {
    }

    @FXML
    private void cerrar(MouseEvent event) {
    }

    @FXML
    private void acceso_principal(ActionEvent event) {
    }

    @FXML
    private void moverPantalla2(MouseEvent event) {
    }

    @FXML
    private void moverPantalla(MouseEvent event) {
    }
    
}
