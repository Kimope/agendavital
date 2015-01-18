/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import agendavital.modelo.data.Momento;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLRegistroPreguntaUnoController implements Initializable {

    static Stage ventanaSegundaPregunta;
            //////////////Variables de la ventana de registro//////////////
        public static final double ANCHO = 596;
	public static final double ALTO= 488;
        private double initX=ANCHO/2;
        private double initY=ALTO/2;    
        //------------------------------------------------------------//
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
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private DatePicker dpFecha;
    private Momento momento = null;
    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private File file = null;

    /**
     * Initializes the controller class.
     */
    
    
        ///////////////////Menu de botones esquina superior derecha///////////////////
    @FXML
    public void minimizar() throws IOException {
        FXMLRegistroCompletadoController.ventanaPrimeraPregunta.setIconified(true);
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
        FXMLRegistroCompletadoController.ventanaPrimeraPregunta.close();
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
///////////////////////////////////////////////////////////
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dpFecha.setValue(LocalDate.now());
        file = new File("imagenes/bebe.jpg");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLRegistroPreguntaUnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        double width = 191;
        double heigth = 167;
        Image imagen = new Image(is, width, heigth, false, true);
        imgImagen.setImage(imagen);
    }

    @FXML
    public void anadir() {
        FileChooser chooser = new FileChooser();
         chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Todas las imagenes", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        file = chooser.showOpenDialog(new Stage());
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLRegistroPreguntaUnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        double width = 191;
        double heigth = 167;
        Image imagen = new Image(is, width, heigth, false, false);
        imgImagen.setImage(imagen);
    }

    @FXML
    public void anadirmomento() throws ConexionBDIncorrecta, IOException{
        String fecha = dateFormatter.format(dpFecha.getValue());
        ArrayList<String> tags = new ArrayList<>();
        tags.add("Escuela");
        momento = Momento.insert("De cuando empece la escuela...", fecha, txtDescripcion.getText(), "-fx-background-color: blue", tags);
        momento.asociarDocumento(file);
        segundapregunta();
    }
    
    @FXML
    public void segundapregunta() throws IOException {
        Parent root = null;
        ventanaSegundaPregunta = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("logo.png"));
        ventanaSegundaPregunta.getIcons().add(icon);
        ventanaSegundaPregunta.setTitle("Primeros Pasos");

        try {
            root = FXMLLoader.load(getClass().getResource("FXMLRegistroPreguntaDos.fxml"));
        } catch (IOException e) {
            System.out.println("No se puede encontrar el fichero FXML");
        }

        Scene escenaSegundaPregunta = new Scene(root);
        ventanaSegundaPregunta.setScene(escenaSegundaPregunta);
        ventanaSegundaPregunta.initStyle(StageStyle.UNDECORATED);
        ventanaSegundaPregunta.show();
        FXMLRegistroCompletadoController.ventanaPrimeraPregunta.close();
    }
    
        /////////////////////Métodos para mover la pantalla clickando en cualquier lugar/////////////////////
    @FXML
    public void moverPantalla() throws IOException {
        anclaje.setOnMousePressed((MouseEvent me) -> {
            initX = me.getScreenX() - FXMLRegistroCompletadoController.ventanaPrimeraPregunta.getX();
            initY = me.getScreenY() - FXMLRegistroCompletadoController.ventanaPrimeraPregunta.getY();
        });
    }

    @FXML
    public void moverPantalla2() throws IOException {
        anclaje.setOnMouseDragged((MouseEvent me) -> {
            FXMLRegistroCompletadoController.ventanaPrimeraPregunta.setX(me.getScreenX() - initX);
            FXMLRegistroCompletadoController.ventanaPrimeraPregunta.setY(me.getScreenY() - initY);
        });
    }
    //-----------------------------------------------------------------------------------------------//

}
