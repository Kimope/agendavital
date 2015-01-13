package agendavital.vista;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Enrique
 */
public class FXMLRegistroCompletadoController implements Initializable 
{
        static Stage ventanaPrimeraPregunta;
        //Ventana que sale al completarse el registro
        @FXML private AnchorPane anclaje;
        @FXML private Line lineamin;
        @FXML private Line lineacerrar1;
        @FXML private Line lineacerrar2;
        @FXML private Circle circulomin;
        @FXML private Circle circulocerr;
        
        //////////////Variables de la ventana de registro//////////////
        public static final double ANCHO = 596;
	public static final double ALTO= 488;
        private double initX=ANCHO/2;
        private double initY=ALTO/2;    
        //------------------------------------------------------------//
    @FXML
    private Text text1;
    @FXML
    private Text text2;
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
    }   
    /////////////////////Métodos para mover la pantalla clickando en cualquier lugar/////////////////////
    @FXML
    public void moverPantalla() throws IOException
    {
        anclaje.setOnMousePressed(new EventHandler<MouseEvent>() 
        {
                public void handle(MouseEvent me) 
                {
                    initX = me.getScreenX() - FXMLLoginController.ventanaRegistro.getX();
                    initY = me.getScreenY() - FXMLLoginController.ventanaRegistro.getY();
                }
        });
    }
    
@FXML
    public void minimizar() throws IOException
    {
        FXMLRegistroController.ventanaRegistroCompletado.setIconified(true);
    }
    
    @FXML
    public void minimizarEncima() throws IOException
    {
        circulomin.setFill(Color.web("#c97c5d"));
    }
    @FXML
    public void minimizarSalida() throws IOException
    {
        circulomin.setFill(Color.TRANSPARENT);
    }
    @FXML
    public void cerrar() throws IOException
    {
        FXMLRegistroController.ventanaRegistroCompletado.close();
    }
    @FXML
    public void cerrarEncima() throws IOException
    {
        circulocerr.setFill(Color.web("#c97c5d"));
    }
    @FXML
    public void cerrarSalida() throws IOException
    {
        circulocerr.setFill(Color.TRANSPARENT);
    }
    @FXML
    public void acceso_principal() throws IOException
    {
    }
    @FXML
    public void moverPantalla2() throws IOException
    {
        anclaje.setOnMouseDragged(new EventHandler<MouseEvent>() 
        {
                public void handle(MouseEvent me) 
                {
                    FXMLLoginController.ventanaRegistro.setX(me.getScreenX() - initX);
                    FXMLLoginController.ventanaRegistro.setY(me.getScreenY() - initY);
                }
        });
    }
    //-----------------------------------------------------------------------------------------------//
    @FXML
    public void primerapregunta() throws IOException
    {
                Parent root = null;
                ventanaPrimeraPregunta = new Stage();
                Image icon = new Image(getClass().getResourceAsStream("logo.png"));
                ventanaPrimeraPregunta.getIcons().add(icon);
                ventanaPrimeraPregunta.setTitle("Primeros Pasos");

                try {
                    root = FXMLLoader.load(getClass().getResource("FXMLRegistroPreguntaUno.fxml"));
                } catch (IOException e) {
                    System.out.println("No se puede encontrar el fichero FXML");
                }

                Scene escenaPrimeraPregunta = new Scene(root);
                ventanaPrimeraPregunta.setScene(escenaPrimeraPregunta);
                ventanaPrimeraPregunta.initStyle(StageStyle.UNDECORATED);
                ventanaPrimeraPregunta.show();
                FXMLRegistroController.ventanaRegistroCompletado.close();
    }
}