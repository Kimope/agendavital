/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import agendavital.modelo.data.Noticia;
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
    //-----------------------------------------------------------------------------------------------//
}
