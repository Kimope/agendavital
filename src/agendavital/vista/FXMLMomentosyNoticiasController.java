/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import agendavital.modelo.data.Momento;
import agendavital.modelo.data.Noticia;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
//import static agendavital.vista.FXMLPrincipalController.ventanaNoticia;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLMomentosyNoticiasController implements Initializable {

    public static Stage ventanaMomento = new Stage();
    public static Stage ventanaNoticia = new Stage();
    List<Text> let = new ArrayList<>();
    final ListView listView = new ListView();
    final ListView listView2 = new ListView();

    //////////////Variables de la ventana de registro//////////////
    public static final double ANCHO = 425;
    public static final double ALTO = 473;
    private double initX = ANCHO / 2;
    private double initY = ALTO / 2;
        //------------------------------------------------------------//

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
    private AnchorPane panecentral;
    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public FXMLPrincipalController controllerPrincipal;
    public boolean mostrandoTodo;

    public boolean isMostrandoTodo() {
        return mostrandoTodo;
    }

    public void setMostrandoTodo(boolean mostrandoTodo) {
        this.mostrandoTodo = mostrandoTodo;
    }

    ///////////////////Menu de botones esquina superior derecha///////////////////
    @FXML
    public void minimizar() throws IOException {
        FXMLPrincipalController.ventanaDia.setIconified(true);
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
    public void cerrar() throws IOException {
        FXMLPrincipalController.ventanaDia.close();
    }

    @FXML
    public void cerrarEncima() throws IOException {
        circulocerr.setFill(Color.web("#D7F2E8"));
    }

    @FXML
    public void cerrarSalida() throws IOException {
        circulocerr.setFill(Color.TRANSPARENT);
    }

    public void setControllerPrincipal(FXMLPrincipalController controllerPrincipal) {
        this.controllerPrincipal = controllerPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }
    public void mostrarTodo(){
        listView.getItems().clear();
        listView2.getItems().clear();
        ArrayList<Noticia> noticias = null;
        ArrayList<Momento> momentos = null;
        try {
            /*INICIAR VARIABLES*/
            noticias = Noticia.getTodasNoticias();
            momentos = Momento.getTodosMomentos();
        } catch (ConexionBDIncorrecta ex) {
            Logger.getLogger(FXMLMomentosyNoticiasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!noticias.isEmpty()) {
            for (Noticia noticia : noticias) {
                Text text = new Text("("+noticia.getFecha()+") " +noticia.getTitulo());
                text.setStyle("-fx-color:black");
                addLink(text.getText(), noticia);
            }
        } else {
            Text text = new Text("No hay registrada ninguna noticia en la Base de Datos.");
            text.setStyle("-fx-color:black");
            text.setStyle("-fx-background-color:red");
            addLink(text.getText(), null);
        }
        if (!momentos.isEmpty()) {
            for (Momento momento : momentos) {
                Text text = new Text("("+momento.getFecha()+") " +momento.getTitulo());
                text.setStyle("-fx-color:black");
                addLink2(text.getText(), momento);
            }
        } else {
            Text text = new Text("No hay registrada ningun momento en la Base de Datos");
            text.setStyle("-fx-color:black");
            addLink2(text.getText(), null);
        }

        VBox vBox = new VBox();
        VBox vBox2 = new VBox();
        vBox.getChildren().add(listView);
        vBox2.getChildren().add(listView2);
        panecentral.getChildren().add(vBox);
        panecentral.getChildren().add(vBox2);
        AnchorPane.setTopAnchor(vBox, 60.0);
        AnchorPane.setRightAnchor(vBox, 20.0);
        AnchorPane.setBottomAnchor(vBox, 320.0);
        AnchorPane.setLeftAnchor(vBox, 20.0);
        AnchorPane.setTopAnchor(vBox2, 325.0);
        AnchorPane.setRightAnchor(vBox2, 20.0);
        AnchorPane.setBottomAnchor(vBox2, 45.0);
        AnchorPane.setLeftAnchor(vBox2, 20.0);
    }
    
    public void cambiarDatos() {
        listView.getItems().clear();
        listView2.getItems().clear();
        
        
        ArrayList<Noticia> noticias = null;
        ArrayList<Momento> momentos = null;
        try {
            /*INICIAR VARIABLES*/
            noticias = Noticia.Select(FXMLPrincipalController.fechaSeleccionada);
            momentos = Momento.Select(FXMLPrincipalController.fechaSeleccionada);
        } catch (ConexionBDIncorrecta ex) {
            Logger.getLogger(FXMLMomentosyNoticiasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!noticias.isEmpty()) {
            for (Noticia noticia : noticias) {
                Text text = new Text(noticia.getTitulo());
                text.setStyle("-fx-color:black");
                addLink(text.getText(), noticia);
            }
        } else {
            Text text = new Text("No hay registrada ninguna noticia para esta fecha");
            text.setStyle("-fx-color:black");
            addLink(text.getText(), null);
        }
        if (!momentos.isEmpty()) {
            for (Momento momento : momentos) {
                Text text = new Text(momento.getTitulo());
                text.setStyle("-fx-color:black");
                addLink2(text.getText(), momento);
            }
        } else {
            Text text = new Text("No hay registrada ningun momento para esta fecha");
            text.setStyle("-fx-color:black");
            addLink2(text.getText(), null);
        }

        VBox vBox = new VBox();
        VBox vBox2 = new VBox();
        vBox.getChildren().add(listView);
        vBox2.getChildren().add(listView2);
        panecentral.getChildren().add(vBox);
        panecentral.getChildren().add(vBox2);
        AnchorPane.setTopAnchor(vBox, 60.0);
        AnchorPane.setRightAnchor(vBox, 10.0);
        AnchorPane.setBottomAnchor(vBox, 320.0);
        AnchorPane.setLeftAnchor(vBox, 10.0);
        AnchorPane.setTopAnchor(vBox2, 325.0);
        AnchorPane.setRightAnchor(vBox2, 10.0);
        AnchorPane.setBottomAnchor(vBox2, 45.0);
        AnchorPane.setLeftAnchor(vBox2, 10.0);
    }
    
    public void cambiarDatosBusqueda(String _busqueda) throws ConexionBDIncorrecta, SQLException{
         TreeMap<LocalDate, ArrayList<Noticia>> busquedaNoticia = Noticia.buscar(_busqueda);
         if(!busquedaNoticia.isEmpty()){
        busquedaNoticia.keySet().stream().map((date) -> {
            return date;
        }).forEach((date) -> {
            for(int i = 0; i < busquedaNoticia.get(date).size(); i++){
                Text text = new Text("("+dateFormatter.format(date)+")"+busquedaNoticia.get(date).get(i).getTitulo());
                text.setStyle("-fx-color:black");
                addLink(text.getText(), busquedaNoticia.get(date).get(i));
            }
        });
         }
         else{
            Text text = new Text("No hay registrada ninguna noticia para este criterio de busqueda");
            text.setStyle("-fx-color:black");
            addLink(text.getText(), null);
         }
        
        TreeMap<LocalDate, ArrayList<Momento>> busquedaMomento = Momento.buscar(_busqueda);
        if(!busquedaMomento.isEmpty()){
        busquedaMomento.keySet().stream().map((date) -> {
            return date;
        }).forEach((date) -> {
            for(int i = 0; i < busquedaMomento.get(date).size(); i++){
                Text text = new Text("("+dateFormatter.format(date)+")"+busquedaMomento.get(date).get(i).getTitulo());
                text.setStyle("-fx-color:black");
                addLink2(text.getText(), busquedaMomento.get(date).get(i));
            }
        });
        }
        else{
            Text text = new Text("No hay registrada ningun momento para este criterio de busqueda");
            text.setStyle("-fx-color:black");
            addLink(text.getText(), null);
         }
         VBox vBox = new VBox();
        VBox vBox2 = new VBox();
        vBox.getChildren().add(listView);
        vBox2.getChildren().add(listView2);
        panecentral.getChildren().add(vBox);
        panecentral.getChildren().add(vBox2);
        AnchorPane.setTopAnchor(vBox, 60.0);
        AnchorPane.setRightAnchor(vBox, 10.0);
        AnchorPane.setBottomAnchor(vBox, 320.0);
        AnchorPane.setLeftAnchor(vBox, 10.0);
        AnchorPane.setTopAnchor(vBox2, 325.0);
        AnchorPane.setRightAnchor(vBox2, 10.0);
        AnchorPane.setBottomAnchor(vBox2, 45.0);
        AnchorPane.setLeftAnchor(vBox2, 10.0);
        
    }

    private void addLink(final String url, Noticia noticia) {
        final Text link = new Text(url);

        link.setOnMouseClicked((MouseEvent t) -> {
            if (t.getButton().equals(MouseButton.PRIMARY)) {
                if (t.getClickCount() == 2) {
                    if (ventanaNoticia.isShowing()) {
                        ventanaNoticia.setIconified(false);
                        ventanaNoticia.toFront();
                    } else {
                        Parent root = null;
                        ventanaNoticia = new Stage();
                        Image icon = new Image(getClass().getResourceAsStream("imagenes_interfaz/logo.png"));
                        ventanaNoticia.getIcons().add(icon);
                        ventanaNoticia.setTitle("Acerca De");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLNoticia.fxml"));
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                        }
                        
                        Scene escenaNoticia = new Scene(root);
                        escenaNoticia.getStylesheets().add("textarea.css");
                        FXMLNoticiaController controller = loader.getController();
                        controller.setControllerMYN(FXMLMomentosyNoticiasController.this);
                        controller.setControllerPrincipal(controllerPrincipal);
                        controller.imprimir(noticia);
                        ventanaNoticia.setScene(escenaNoticia);
                        ventanaNoticia.initStyle(StageStyle.UNDECORATED);
                        ventanaNoticia.show();
                    }
                }
            }
        });
        listView.getItems().add(link);
    }

    private void addLink2(final String url, Momento momento) {
        final Text link = new Text(url);

        link.setOnMouseClicked((MouseEvent t) -> {
            if (t.getButton().equals(MouseButton.PRIMARY)) {
                if (t.getClickCount() == 2) {
                    if (ventanaMomento.isShowing()) {
                        ventanaMomento.setIconified(false);
                        ventanaMomento.toFront();
                    } else {
                        Parent root = null;
                        ventanaMomento = new Stage();
                        Image icon = new Image(getClass().getResourceAsStream("imagenes_interfaz/logo.png"));
                        ventanaMomento.getIcons().add(icon);
                        ventanaMomento.setTitle("Acerca De");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMomento.fxml"));
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                        }
                        
                        Scene escenaNoticia = new Scene(root);
                        FXMLMomentoController controller = loader.getController();
                        controller.setControllerMYN(FXMLMomentosyNoticiasController.this);
                        controller.setControllerPrincipal(controllerPrincipal);
                        try {
                            controller.imprimir(momento);
                        } catch (SQLException ex) {
                            Logger.getLogger(FXMLMomentosyNoticiasController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        ventanaMomento.setScene(escenaNoticia);
                        ventanaMomento.initStyle(StageStyle.UNDECORATED);
                        ventanaMomento.show();
                    }
                }
            }
        });
        listView2.getItems().add(link);
    }

    /////////////////////Métodos para mover la pantalla clickando en cualquier lugar/////////////////////
    @FXML
    public void moverPantalla() throws IOException {
        panecentral.setOnMousePressed((MouseEvent me) -> {
            initX = me.getScreenX() - FXMLPrincipalController.ventanaDia.getX();
            initY = me.getScreenY() - FXMLPrincipalController.ventanaDia.getY();
        });

    }

    @FXML
    public void moverPantalla2() throws IOException {
        panecentral.setOnMouseDragged((MouseEvent me) -> {
            FXMLPrincipalController.ventanaDia.setX(me.getScreenX() - initX);
            FXMLPrincipalController.ventanaDia.setY(me.getScreenY() - initY);
        });
    }
    //-----------------------------------------------------------------------------------------------//

}
