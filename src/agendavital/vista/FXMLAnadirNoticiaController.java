/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import agendavital.modelo.data.Noticia;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLAnadirNoticiaController implements Initializable {

    //////////////Variables de la ventana de registro//////////////
    public static final double ANCHO = 596;
    public static final double ALTO = 488;
    private double initX = ANCHO / 2;
    private double initY = ALTO / 2;
    //------------------------------------------------------------//
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
    private AnchorPane anclaje;
    @FXML
    private ComboBox cbcategoria;
    @FXML
    private Button butanadir;
    @FXML
    private TextField titular;
    @FXML
    private TextArea descripcion;
    @FXML
    private TextField t1;
    @FXML
    private TextField t2;
    @FXML
    private TextField t3;
    @FXML
    private TextField link;
    @FXML
    private DatePicker cal;

    public FXMLPrincipalController controllerPrincipal;
    public FXMLNoticiaController controllerNoticia;
    public FXMLMomentosyNoticiasController controllerMYN;
    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    Noticia modificarNoticia = null;
    
    
                    ///////////////////Menu de botones esquina superior derecha///////////////////
    @FXML
    public void minimizar() throws IOException {
        FXMLPrincipalController.ventanaAnadirNoticia.setIconified(true);
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
    public void cerrar() throws IOException
    {
        FXMLPrincipalController.ventanaAnadirNoticia.close();
    }
    
    @FXML
    public void cerrarEncima() throws IOException
    {
       circulocerr.setFill(Color.web("#D7F2E8"));
    }
    
    @FXML
    public void cerrarSalida() throws IOException
    {
        circulocerr.setFill(Color.TRANSPARENT);
    }
///////////////////////////////////////////////////////////
    public void setControllerMYN(FXMLMomentosyNoticiasController controllerMYN) {
        this.controllerMYN = controllerMYN;
    }
    
    public void setControllerMYN(FXMLNoticiaController controllerMYN) {
        this.controllerNoticia = controllerMYN;
    }

    public void setModificarNoticia(Noticia modificarNoticia) {
        this.modificarNoticia = modificarNoticia;
    }

    public void setControllerPrincipal(FXMLPrincipalController controllerPrincipal) {
        this.controllerPrincipal = controllerPrincipal;
    }


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Se le dan los valores a los campos de selección
        cbcategoria.getItems().addAll("Noticias Nacionales", "Noticias Internacionales");
         Callback<DatePicker, DateCell> dayCellFactory =( DatePicker dp) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if(item.isAfter(LocalDate.now())) setDisable(true);
            }
         };
         cal.setDayCellFactory(dayCellFactory);
    }

    public void inicializarVentana() {

        if (modificarNoticia == null) {
            cal.setValue(LocalDate.now());
            cbcategoria.getSelectionModel().selectFirst();
        } else {
            cal.setValue(LocalDate.parse(modificarNoticia.getFecha(), dateFormatter));
            titular.setText(modificarNoticia.getTitulo());
            descripcion.setText(modificarNoticia.getCuerpo());
            link.setText(modificarNoticia.getLink());
            if (modificarNoticia.getCategoria().equals("Noticias Nacionales")) {
                cbcategoria.getSelectionModel().selectFirst();
            } else {
                cbcategoria.getSelectionModel().selectLast();
            }
            ArrayList<String> tags = modificarNoticia.getTags();
            if (tags.size() == 1) {
                t1.setText(tags.get(0));
            }
            if (tags.size() == 2) {
                t1.setText(tags.get(0));
                t2.setText(tags.get(1));
            }
            if (tags.size() == 3) {
                t1.setText(tags.get(0));
                t2.setText(tags.get(1));
                t3.setText(tags.get(2));
            }

        }
    }

    public void registra_noticia() throws IOException, ConexionBDIncorrecta, SQLException {
        if (modificarNoticia == null) {
            String _titulo = titular.getText();
            String _cuerpo = descripcion.getText();
            ArrayList<String> tags = new ArrayList<>();
            if (!t1.getText().isEmpty()) {
                tags.add(t1.getText());
            }
            if (!t2.getText().isEmpty()) {
                tags.add(t2.getText());
            }
            if (!t3.getText().isEmpty()) {
                tags.add(t3.getText());
            }
            String _link = link.getText();
            String _fecha = dateFormatter.format(cal.getValue());
            String _categoria = (String) cbcategoria.getValue();
            Noticia noticia = Noticia.Insert(_titulo, _link, _fecha, _categoria, _cuerpo, tags);
            controllerPrincipal.colorearFechas();
        }
        else{
            boolean colorear = false;
            modificarNoticia.setTitulo(titular.getText());
            modificarNoticia.setCuerpo(descripcion.getText());
            modificarNoticia.setLink(link.getText());
            modificarNoticia.setCategoria((String) cbcategoria.getValue());
            if(!dateFormatter.format(cal.getValue()).equals(modificarNoticia.getFecha())) colorear = true;
            modificarNoticia.setFecha(dateFormatter.format(cal.getValue()));
            ArrayList<String> tags = new ArrayList<>();
            if (!t1.getText().isEmpty()) {
                tags.add(t1.getText());
            }
            if (!t2.getText().isEmpty()) {
                tags.add(t2.getText());
            }
            if (!t3.getText().isEmpty()) {
                tags.add(t3.getText());
            }
            modificarNoticia.setTags(tags);
            modificarNoticia.Update();
            controllerNoticia.imprimir(modificarNoticia);
            controllerMYN.cambiarDatos();
            if(colorear) controllerPrincipal.colorearFechas();
        }
    }

    /////////////////////Métodos para mover la pantalla clickando en cualquier lugar/////////////////////
    @FXML
    public void moverPantalla() throws IOException {
        anclaje.setOnMousePressed((MouseEvent me) -> {
            initX = me.getScreenX() - FXMLPrincipalController.ventanaAnadirNoticia.getX();
            initY = me.getScreenY() - FXMLPrincipalController.ventanaAnadirNoticia.getY();
        });
    }

    @FXML
    public void moverPantalla2() throws IOException {
        anclaje.setOnMouseDragged((MouseEvent me) -> {
            FXMLPrincipalController.ventanaAnadirNoticia.setX(me.getScreenX() - initX);
            FXMLPrincipalController.ventanaAnadirNoticia.setY(me.getScreenY() - initY);
        });
    }
    //-----------------------------------------------------------------------------------------------//
}
