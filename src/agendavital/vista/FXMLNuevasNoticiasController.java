/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import agendavital.modelo.data.Noticia;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import agendavital.modelo.excepciones.ErrorConexionFeedzilla;
//import static agendavital.vista.FXMLPrincipalController.ventanaNoticia;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLNuevasNoticiasController implements Initializable {
    public static Stage ventanaPrincipal;
    //////////////Variables de la ventana de registro//////////////
    public static final double ANCHO = 425;
    public static final double ALTO = 473;
    private double initX = ANCHO / 2;
    private double initY = ALTO / 2;
        //------------------------------------------------------------//

    @FXML
    private CheckBox cb1;
    @FXML
    private CheckBox cb2;
    @FXML
    private CheckBox cb3;
    @FXML
    private CheckBox cb4;
    @FXML
    private CheckBox cb5;
    @FXML
    private CheckBox cb6;
    @FXML
    private CheckBox cb7;
    @FXML
    private CheckBox cb8;
    @FXML
    private CheckBox cb9;
    @FXML
    private CheckBox cb10;
    @FXML
    private CheckBox cb11;
    @FXML
    private CheckBox cb12;
    @FXML
    private CheckBox cb13;
    
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
    ArrayList<Noticia> noticias = null;
    

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

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            noticias = Noticia.getNoticiasFeedZilla();
        } catch (ParseException ex) {
            Logger.getLogger(FXMLNuevasNoticiasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ErrorConexionFeedzilla ex) {
            Logger.getLogger(FXMLNuevasNoticiasController.class.getName()).log(Level.SEVERE, null, ex);
        }
       cb1.setText(noticias.get(0).getTitulo());
       cb2.setText(noticias.get(1).getTitulo());
       cb3.setText(noticias.get(2).getTitulo());
       cb4.setText(noticias.get(3).getTitulo());
       cb5.setText(noticias.get(4).getTitulo());
       cb6.setText(noticias.get(5).getTitulo());
       cb7.setText(noticias.get(6).getTitulo());
       cb8.setText(noticias.get(7).getTitulo());
       cb9.setText(noticias.get(8).getTitulo());
       cb10.setText(noticias.get(9).getTitulo());
       cb11.setText(noticias.get(10).getTitulo());
       cb12.setText(noticias.get(11).getTitulo());
       cb13.setText(noticias.get(12).getTitulo());
    }
    
    @FXML
    public void nuevas_noticias() throws IOException, ConexionBDIncorrecta, SQLException
    {
        if(cb1.isSelected()){
            Noticia insertar = noticias.get(0);
            Noticia.Insert(insertar.getTitulo(), insertar.getLink(), insertar.getFecha(), insertar.getCategoria(), insertar.getCuerpo(), new ArrayList<>());
        }
        if(cb2.isSelected()){
            Noticia insertar = noticias.get(1);
            Noticia.Insert(insertar.getTitulo(), insertar.getLink(), insertar.getFecha(), insertar.getCategoria(), insertar.getCuerpo(), new ArrayList<>());
        }
        if(cb3.isSelected()){
            Noticia insertar = noticias.get(2);
            Noticia.Insert(insertar.getTitulo(), insertar.getLink(), insertar.getFecha(), insertar.getCategoria(), insertar.getCuerpo(), new ArrayList<>());
        }
        if(cb4.isSelected()){
            Noticia insertar = noticias.get(3);
            Noticia.Insert(insertar.getTitulo(), insertar.getLink(), insertar.getFecha(), insertar.getCategoria(), insertar.getCuerpo(), new ArrayList<>());
        }
        if(cb5.isSelected()){
            Noticia insertar = noticias.get(4);
            Noticia.Insert(insertar.getTitulo(), insertar.getLink(), insertar.getFecha(), insertar.getCategoria(), insertar.getCuerpo(), new ArrayList<>());
        }
        if(cb6.isSelected()){
            Noticia insertar = noticias.get(5);
            Noticia.Insert(insertar.getTitulo(), insertar.getLink(), insertar.getFecha(), insertar.getCategoria(), insertar.getCuerpo(), new ArrayList<>());
        }
        if(cb7.isSelected()){
            Noticia insertar = noticias.get(6);
            Noticia.Insert(insertar.getTitulo(), insertar.getLink(), insertar.getFecha(), insertar.getCategoria(), insertar.getCuerpo(), new ArrayList<>());
        }
        if(cb8.isSelected()){
            Noticia insertar = noticias.get(7);
            Noticia.Insert(insertar.getTitulo(), insertar.getLink(), insertar.getFecha(), insertar.getCategoria(), insertar.getCuerpo(), new ArrayList<>());
        }
        if(cb9.isSelected()){
            Noticia insertar = noticias.get(8);
            Noticia.Insert(insertar.getTitulo(), insertar.getLink(), insertar.getFecha(), insertar.getCategoria(), insertar.getCuerpo(), new ArrayList<>());
        }
        if(cb10.isSelected()){
            Noticia insertar = noticias.get(9);
            Noticia.Insert(insertar.getTitulo(), insertar.getLink(), insertar.getFecha(), insertar.getCategoria(), insertar.getCuerpo(), new ArrayList<>());
        }
        if(cb11.isSelected()){
            Noticia insertar = noticias.get(10);
            Noticia.Insert(insertar.getTitulo(), insertar.getLink(), insertar.getFecha(), insertar.getCategoria(), insertar.getCuerpo(), new ArrayList<>());
        }
        if(cb12.isSelected()){
            Noticia insertar = noticias.get(11);
            Noticia.Insert(insertar.getTitulo(), insertar.getLink(), insertar.getFecha(), insertar.getCategoria(), insertar.getCuerpo(), new ArrayList<>());
        }
        if(cb13.isSelected()){
            Noticia insertar = noticias.get(12);
            Noticia.Insert(insertar.getTitulo(), insertar.getLink(), insertar.getFecha(), insertar.getCategoria(), insertar.getCuerpo(), new ArrayList<>());
        }
            Parent root = null; //Creamos el parent
            ventanaPrincipal = new Stage(); //Creamos la ventana que tendr� la vista Principal de la aplicaci�n
            Image icon= new Image(getClass().getResourceAsStream("imagenes_interfaz/logo.png"));
            ventanaPrincipal.getIcons().add(icon);
            
            try{
                root = FXMLLoader.load(getClass().getResource("FXMLPrincipal.fxml"));
            }catch(IOException e)
            {
                System.out.println("No se puede encontrar el fichero FXML");
            }
            
            //ventanaPrincipal.setResizable(false); //No se puede modificar el tama�o de la ventana
            ventanaPrincipal.setTitle("Bienvenido a su Agenda Vital"); //Ponemos un t�tulo para el panel de Windows
            Scene escenaPrincipal = new Scene(root,1300,680); //Creamos la escena
            ventanaPrincipal.setScene(escenaPrincipal); //Cargamos la escena
            ventanaPrincipal.initStyle(StageStyle.UNDECORATED);
            //AgendaVital.ventanaLogin.close(); // Cerramos la pantalla del Login
            ventanaPrincipal.show(); //Mostramos la pantalla principal
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
