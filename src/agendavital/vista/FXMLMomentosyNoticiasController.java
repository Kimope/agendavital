/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
       /*INICIAR VARIABLES*/ 
        Text l1=new Text("NOTICIA1");
        Text l2=new Text("NOTICIA2");
        Text l3=new Text("NOTICIA3");
        Text l4=new Text("NOTICIA4");
        l1.setStyle("-fx-color:black");
        l2.setStyle("fx-color:black");
        l3.setStyle("fx-color:black");
        l4.setStyle("-fx-color:black");
        addLink(l1.getText());
        addLink(l2.getText());
        addLink(l3.getText());
        addLink(l4.getText());
        ///////////////////
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
        link.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {     
                AnchorPane pane = new AnchorPane();
                Text txt = new Text(link.getText());
                pane.getChildren().add(txt);            
            }

        });
        listView.getItems().add(link);
    }

   
}