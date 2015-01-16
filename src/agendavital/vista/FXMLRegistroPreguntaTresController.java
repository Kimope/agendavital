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
public class FXMLRegistroPreguntaTresController implements Initializable {
    static Stage ventanaPrincipal;
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
    private Momento momento = null;
    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private File file = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          dpFecha.setValue(LocalDate.now());
        file = new File("imagenes/londres.jpg");
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
    public void anadir()
    {
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
    public void anadirmomento() throws ConexionBDIncorrecta, IOException{
        String fecha = dateFormatter.format(dpFecha.getValue());
        momento = Momento.insert(fecha, "Mi viaje a "+txtTitulo.getText()+". "+txtDescripcion.getText(), "-fx-background-color: blue");
        momento.asociarDocumento(file);
        principal();
    }
    
    @FXML
    public void principal() throws IOException
    {
                Parent root = null;
                ventanaPrincipal = new Stage();
                Image icon = new Image(getClass().getResourceAsStream("logo.png"));
                ventanaPrincipal.getIcons().add(icon);
                ventanaPrincipal.setTitle("Agenda Vital");

                try {
                    root = FXMLLoader.load(getClass().getResource("FXMLPrincipal.fxml"));
                } catch (IOException e) {
                    System.out.println("No se puede encontrar el fichero FXML");
                }

                Scene escenaPrincipal = new Scene(root);
                ventanaPrincipal.setScene(escenaPrincipal);
                //ventanaPrincipal.initStyle(StageStyle.UNDECORATED);
                ventanaPrincipal.show();
                FXMLRegistroPreguntaDosController.ventanaTerceraPregunta.close();
    }
    
                /////////////////////Métodos para mover la pantalla clickando en cualquier lugar/////////////////////
    @FXML
    public void moverPantalla() throws IOException {
        anclaje.setOnMousePressed((MouseEvent me) -> {
            initX = me.getScreenX() - FXMLRegistroPreguntaDosController.ventanaTerceraPregunta.getX();
            initY = me.getScreenY() - FXMLRegistroPreguntaDosController.ventanaTerceraPregunta.getY();
        });
    }

    @FXML
    public void moverPantalla2() throws IOException {
        anclaje.setOnMouseDragged((MouseEvent me) -> {
            FXMLRegistroPreguntaDosController.ventanaTerceraPregunta.setX(me.getScreenX() - initX);
            FXMLRegistroPreguntaDosController.ventanaTerceraPregunta.setY(me.getScreenY() - initY);
        });
    }
    //-----------------------------------------------------------------------------------------------//
}
