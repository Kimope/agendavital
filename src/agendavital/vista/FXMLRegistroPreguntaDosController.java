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
import javafx.scene.control.TextField;
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
public class FXMLRegistroPreguntaDosController implements Initializable {
    static Stage ventanaTerceraPregunta;
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
    @FXML
    private TextField txtTitulo;
    private Momento momento;
    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private File file = null;

    /**
     * Initializes the controller class.
     */
    
    
            ///////////////////Menu de botones esquina superior derecha///////////////////
    @FXML
    public void minimizar() throws IOException {
        FXMLRegistroPreguntaUnoController.ventanaSegundaPregunta.setIconified(true);
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
        FXMLRegistroPreguntaUnoController.ventanaSegundaPregunta.close();
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
        file = new File("imagenes/queen.jpg");
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

        file = chooser.showOpenDialog(new Stage());
         chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Todas las imagenes", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLRegistroPreguntaUnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        double width = 191;
        double heigth = 167;
        Image imagen = new Image(is,width,heigth,false,false);
        imgImagen.setImage(imagen);
    }


    @FXML
    private void acceso_principal(ActionEvent event) {
    }

    @FXML
    public void anadirmomento() throws ConexionBDIncorrecta, IOException{
        String fecha = dateFormatter.format(dpFecha.getValue());
        ArrayList<String> tags = new ArrayList<>();
        tags.add("Concierto");
        tags.add(txtTitulo.getText());
        momento = Momento.insert("Concierto de "+txtTitulo.getText(), fecha,txtDescripcion.getText(), "-fx-background-color: blue", tags);
        momento.asociarDocumento(file);
        tercerapregunta();
    }
    
    @FXML
    public void tercerapregunta() throws IOException
    {
                Parent root = null;
                ventanaTerceraPregunta = new Stage();
                Image icon = new Image(getClass().getResourceAsStream("imagenes_interfaz/logo.png"));
                ventanaTerceraPregunta.getIcons().add(icon);
                ventanaTerceraPregunta.setTitle("Primeros Pasos");

                try {
                    root = FXMLLoader.load(getClass().getResource("FXMLRegistroPreguntaTres.fxml"));
                } catch (IOException e) {
                    System.out.println("No se puede encontrar el fichero FXML");
                }

                Scene escenaTerceraPregunta = new Scene(root);
                ventanaTerceraPregunta.setScene(escenaTerceraPregunta);
                ventanaTerceraPregunta.initStyle(StageStyle.UNDECORATED);
                ventanaTerceraPregunta.show();
                FXMLRegistroPreguntaUnoController.ventanaSegundaPregunta.close();
    }
    
            /////////////////////Métodos para mover la pantalla clickando en cualquier lugar/////////////////////
    @FXML
    public void moverPantalla() throws IOException {
        anclaje.setOnMousePressed((MouseEvent me) -> {
            initX = me.getScreenX() - FXMLRegistroPreguntaUnoController.ventanaSegundaPregunta.getX();
            initY = me.getScreenY() - FXMLRegistroPreguntaUnoController.ventanaSegundaPregunta.getY();
        });
    }

    @FXML
    public void moverPantalla2() throws IOException {
        anclaje.setOnMouseDragged((MouseEvent me) -> {
            FXMLRegistroPreguntaUnoController.ventanaSegundaPregunta.setX(me.getScreenX() - initX);
            FXMLRegistroPreguntaUnoController.ventanaSegundaPregunta.setY(me.getScreenY() - initY);
        });
    }
    //-----------------------------------------------------------------------------------------------//
    
}
