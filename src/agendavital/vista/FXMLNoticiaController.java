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
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLNoticiaController implements Initializable {
    @FXML
    private Line lineacerrar2;
    @FXML
    private Line lineacerrar1;
    @FXML
    private Circle circulocerr;
    @FXML
    private Line lineamin;
    @FXML
    private Circle circulomin;
    @FXML
    private TextFlow tfTitular;
    @FXML
    private Text txtTitular;
    @FXML
    private TextFlow tfCuerpo;
    @FXML
    private Text txtCuerpo;
    @FXML
    private TextFlow tfLink;
    @FXML
    private Text txtLink;
    @FXML
    private TextFlow tfCategoria;
    @FXML
    private Text txtCategoria;
    @FXML
    private TextFlow tfTags;
    @FXML
    private Text txtTag;
    @FXML
    private Button btnModificar;
    public String fechaSeleccionada;
    


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       fechaSeleccionada = FXMLPrincipalController.fechaSeleccionada;
       txtTitular.setText(fechaSeleccionada);
        
    }    

    @FXML
    private void cerrarEncima(MouseEvent event) {
    }

    @FXML
    private void cerrar(MouseEvent event) {
    }

    @FXML
    private void cerrarSalida(MouseEvent event) {
    }

    @FXML
    private void minimizarEncima(MouseEvent event) {
    }

    @FXML
    private void minimizar(MouseEvent event) {
    }

    @FXML
    private void minimizarSalida(MouseEvent event) {
    }
    
}
