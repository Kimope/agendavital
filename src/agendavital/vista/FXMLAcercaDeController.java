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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLAcercaDeController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    //////////////Variables de la ventana de registro//////////////
        public static final double ANCHO = 377;
	public static final double ALTO= 349;
        private double initX=ANCHO/2;
        private double initY=ALTO/2;   
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

        //------------------------------------------------------------//
        
                                   ///////////////////Menu de botones esquina superior derecha///////////////////
    @FXML
    public void minimizar() throws IOException {
        FXMLPrincipalController.ventanaNoticia.setIconified(true);
    }

    @FXML
    public void minimizarEncima() throws IOException {
        circulomin.setFill(Color.web("#D7F2E8"));
    }

    @FXML
    public void minimizarSalida() throws IOException {
        circulomin.setFill(Color.TRANSPARENT);
    }

    @FXML
    public void cerrar() throws IOException
    {
        FXMLPrincipalController.ventanaNoticia.close();
    }
    
    @FXML
    public void cerrarEncima() throws IOException
    {
       circulocerr.setFill(Color.web("#D7F2E8"));
    }
    
    @FXML
    public void cerrarSalida() throws IOException
    {
        circulocerr.setFill(Color.TRANSPARENT);
    }
    
    
    @FXML 
    private AnchorPane anclaje;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
            /////////////////////Métodos para mover la pantalla clickando en cualquier lugar/////////////////////
   @FXML
    public void moverPantalla() throws IOException {
        anclaje.setOnMousePressed((MouseEvent me) -> {
            initX = me.getScreenX() - FXMLPrincipalController.ventanaNoticia.getX();
            initY = me.getScreenY() - FXMLPrincipalController.ventanaNoticia.getY();
        });
     
    }

    @FXML
    public void moverPantalla2() throws IOException {
        anclaje.setOnMouseDragged((MouseEvent me) -> {
            FXMLPrincipalController.ventanaNoticia.setX(me.getScreenX() - initX);
            FXMLPrincipalController.ventanaNoticia.setY(me.getScreenY() - initY);
        });
    }
    //-----------------------------------------------------------------------------------------------//
}
