/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import agendavital.modelo.data.Momento;
import agendavital.modelo.data.Noticia;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import static agendavital.vista.FXMLPrincipalController.ventanaNoticia;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;



/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLMomentosyNoticiasController implements Initializable {
 
    List<Text> let = new ArrayList<>();
    final ListView listView = new ListView();
    final ListView listView2 = new ListView();
    @FXML
    private AnchorPane panecentral;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
           ArrayList<Noticia> noticias = null;
         ArrayList<Momento> momentos = null;
        try {
            /*INICIAR VARIABLES*/
           noticias = Noticia.Select(FXMLPrincipalController.fechaSeleccionada);
           momentos = Momento.Select(FXMLPrincipalController.fechaSeleccionada);
        } catch (ConexionBDIncorrecta ex) {
            Logger.getLogger(FXMLMomentosyNoticiasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!noticias.isEmpty()){
        for (Noticia noticia : noticias) {
            Text text = new Text(noticia.getTitulo());
            text.setStyle("-fx-color:black");
            addLink(text.getText(), noticia);
        }
        }
        else{
            Text text = new Text("No hay registrada ninguna noticia para esta fecha");
            text.setStyle("-fx-color:black");
            addLink(text.getText(), null);
        }
        if(!momentos.isEmpty()){
            for (Momento momento : momentos) {
            Text text = new Text(momento.getDescripcion());
            text.setStyle("-fx-color:black");
            addLink2(text.getText(), momento);
        }
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

        link.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {     
                if(t.getButton().equals(MouseButton.PRIMARY))
                {
                    if(t.getClickCount() == 2)
                    {
                        Parent root = null;
                        ventanaNoticia = new Stage();
                        Image icon = new Image(getClass().getResourceAsStream("logo.png"));
                        ventanaNoticia.getIcons().add(icon);
                        ventanaNoticia.setTitle("Acerca De");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLNoticia.fxml"));
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            System.out.println("No se puede encontrar el fichero FXML");
                        }

                        Scene escenaNoticia = new Scene(root);
                        FXMLNoticiaController controller = loader.getController();
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

        link.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {     
                if(t.getButton().equals(MouseButton.PRIMARY))
                {
                    if(t.getClickCount() == 2)
                    {
                        Parent root = null;
                        ventanaNoticia = new Stage();
                        Image icon = new Image(getClass().getResourceAsStream("logo.png"));
                        ventanaNoticia.getIcons().add(icon);
                        ventanaNoticia.setTitle("Acerca De");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMomento.fxml"));
                        try {
                            root = loader.load();
                        } catch (IOException e) {
                            System.out.println("No se puede encontrar el fichero FXML");
                        }

                        Scene escenaNoticia = new Scene(root);
                        FXMLMomentoController controller = loader.getController();
                        controller.imprimir(momento);
                        ventanaNoticia.setScene(escenaNoticia);
                        ventanaNoticia.initStyle(StageStyle.UNDECORATED);
                        ventanaNoticia.show();
                    }
                }           
            }

        });
        listView2.getItems().add(link);
    }
   
}