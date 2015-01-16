/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import static javafx.scene.paint.Color.BLUE;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLMomentosyNoticiasController implements Initializable {
 
    List<Text> let = new ArrayList<>();
    final ListView listView = new ListView();
    String textoamostrar="PACO" ;
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*   
@FXML
    private AnchorPane panecentral;
private final ArrayList<Hyperlink> let= new ArrayList<Hyperlink>();*/
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
/*
    public int itemsPerPage() {
        return 1;
    }
 
    public VBox createPage(int pageIndex) {
        VBox box = new VBox(5);
        int page = pageIndex * itemsPerPage();
        for (int i = page; i < page + itemsPerPage(); i++) {
            Hyperlink link = new Hyperlink(let.get(i).getText());       
            box.getChildren().add(link);
        }
        return box;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        
        Hyperlink l1=new Hyperlink("NOTICIA1");
        Hyperlink l2=new Hyperlink("NOTICIA2");
        Hyperlink l3=new Hyperlink("NOTICIA3");
        Hyperlink l4=new Hyperlink("NOTICIA4");
        let.add(l1);
        let.add(l2);
        let.add(l3);
        let.add(l4);
        
        
        Pagination pagination = new Pagination(let.size(), 0);
        pagination.setStyle("-fx-border-color:red;");
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });
        AnchorPane.setTopAnchor(pagination, 60.0);
        AnchorPane.setRightAnchor(pagination, 10.0);
        AnchorPane.setBottomAnchor(pagination, 320.0);
        AnchorPane.setLeftAnchor(pagination, 10.0);
        
        /////////////////////////////////////////////////////////////
        Pagination pagination2 = new Pagination(28, 0);
        pagination2.setStyle("-fx-border-color:red;");
        pagination2.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });
        AnchorPane.setTopAnchor(pagination2, 320.0);
        AnchorPane.setRightAnchor(pagination2, 10.0);
        AnchorPane.setBottomAnchor(pagination2, 25.0);
        AnchorPane.setLeftAnchor(pagination2, 10.0);
        
        
        
        panecentral.getChildren().addAll(pagination,pagination2);  
}*/
}
