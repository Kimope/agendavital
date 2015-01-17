/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import agendavital.modelo.data.Noticia;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import static agendavital.vista.FXMLPrincipalController.ventanaNoticia;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLNoticiaController implements Initializable {
    
    //////////////Variables de la ventana de registro//////////////
        public static final double ANCHO = 783;
	public static final double ALTO= 609;
        private double initX=ANCHO/2;
        private double initY=ALTO/2;    
        //------------------------------------------------------------//
    @FXML 
    private AnchorPane anclaje;
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
    @FXML
    private ImageView prueba;
    @FXML
    private Pane paneluno;
    Noticia _noticia;
    FXMLMomentosyNoticiasController controllerMYN = null;
    public FXMLPrincipalController controllerPrincipal;

    public void setControllerPrincipal(FXMLPrincipalController controllerPrincipal) {
        this.controllerPrincipal = controllerPrincipal;
    }

    public void setControllerMYN(FXMLMomentosyNoticiasController controllerMYN) {
        this.controllerMYN = controllerMYN;
    }
    


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
                        ///////////////////Menu de botones esquina superior derecha///////////////////
    @FXML
    public void minimizar() throws IOException {
        FXMLPrincipalController.ventanaNoticia.setIconified(true);
    }

    @FXML
    public void minimizarEncima() throws IOException {
        circulomin.setFill(Color.web("#D8D8D8"));
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
       circulocerr.setFill(Color.web("#D8D8D8"));
    }
    
    @FXML
    public void cerrarSalida() throws IOException
    {
        circulocerr.setFill(Color.TRANSPARENT);
    }
///////////////////////////////////////////////////////////
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }    
    public void imprimir(Noticia noticia){
        tfTags.getChildren().clear();
        _noticia = noticia;
        txtTitular.setText(noticia.getTitulo());
        txtCategoria.setText(noticia.getCategoria());
        txtLink.setText(noticia.getLink());
        txtCuerpo.setText(noticia.getCuerpo());
        ArrayList<String> tags = noticia.getTags();
            for (String tag : tags) {
                Text text = new Text(tag);
                text.setStyle("-fx-color:black");
                tfTags.getChildren().add(text);
            }
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
    public void modificar_noticia(){
        Parent root = null;
        ventanaNoticia = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("logo.png"));
        ventanaNoticia.getIcons().add(icon);
        ventanaNoticia.setTitle("Modificar Noticia");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAnadirNoticia.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene escenaNoticia = new Scene(root);
        FXMLAnadirNoticiaController controller = loader.getController();
        controller.setModificarNoticia(_noticia);
        controller.setControllerMYN(this);
        controller.setControllerMYN(controllerMYN);
        controller.setControllerPrincipal(controllerPrincipal);
        controller.inicializarVentana();
        ventanaNoticia.setScene(escenaNoticia);
        ventanaNoticia.initStyle(StageStyle.UNDECORATED);
        ventanaNoticia.show();
    }
   public void borrar_noticia() throws ConexionBDIncorrecta{
       _noticia.Delete();
       _noticia = null;
       controllerMYN.cambiarDatos();
       controllerPrincipal.colorearFechas();
   }
}
