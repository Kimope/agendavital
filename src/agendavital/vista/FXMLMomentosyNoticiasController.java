/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import agendavital.modelo.data.Momento;
import agendavital.modelo.data.Noticia;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLMomentosyNoticiasController implements Initializable {
 
    List<Text> let = new ArrayList<>();
    final ListView listView = new ListView();
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
            addLink(text.getText());
        }
        }
        else{
            Text text = new Text("No hay registrada ninguna noticia para esta fecha");
            text.setStyle("-fx-color:black");
            addLink(text.getText());
        }
        VBox vBox = new VBox();
        vBox.getChildren().add(listView);
        panecentral.getChildren().add(vBox);
        
        AnchorPane.setTopAnchor(vBox, 60.0);
        AnchorPane.setRightAnchor(vBox, 10.0);
        AnchorPane.setBottomAnchor(vBox, 320.0);
        AnchorPane.setLeftAnchor(vBox, 10.0);
    }
    
    
private void addLink(final String url) {
        final Text link = new Text(url);
        link.setOnMouseClicked((MouseEvent t) -> {
            AnchorPane pane = new AnchorPane();
            Text txt = new Text(link.getText());
            pane.getChildren().add(txt);
        });
        listView.getItems().add(link);
    }

   
}