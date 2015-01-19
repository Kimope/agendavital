/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import agendavital.modelo.data.Momento;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLAnadirMomentoController implements Initializable {

    public static Stage ventanaAnadido;

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
    private ComboBox<?> cbcategoria;
    @FXML
    private Button butregistrarse;
    @FXML
    private TextField t1;
    @FXML
    private TextField t2;
    @FXML
    private TextField t3;
    @FXML
    private TextField t4;
    @FXML
    private TextArea descripcion;
    @FXML
    private TextField titular;
    @FXML
    private DatePicker cal;
    @FXML
    private ColorPicker color;
    @FXML
    private ImageView imgImagen;
    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public File imagenFile = null;
    public File modificarFile = null;
    public FXMLMomentoController controllerMomento;
    public FXMLPrincipalController controllerPrincipal;
    public FXMLMomentosyNoticiasController controllerMYN;
    public Momento modificarMomento = null;

    public void setControllerMomento(FXMLMomentoController controllerMomento) {
        this.controllerMomento = controllerMomento;
    }

    public void setControllerMYN(FXMLMomentosyNoticiasController controllerMYN) {
        this.controllerMYN = controllerMYN;
    }

    public void setModificarMomento(Momento modificarMomento) {
        this.modificarMomento = modificarMomento;
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
        cal.setValue(LocalDate.now());
        Callback<DatePicker, DateCell> dayCellFactory = (DatePicker dp) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isAfter(LocalDate.now())) {
                    setDisable(true);
                }
            }
        };
        cal.setDayCellFactory(dayCellFactory);
    }

    public void inicializarVentana() throws SQLException {
        if (modificarMomento == null) {
            cal.setValue(LocalDate.now());
            cbcategoria.getSelectionModel().selectFirst();
        } else {
            cal.setValue(LocalDate.parse(modificarMomento.getFecha(), dateFormatter));
            titular.setText(modificarMomento.getTitulo());
            descripcion.setText(modificarMomento.getDescripcion());

            /*  if (modificarMomento.getCategoria().equals("Noticias Nacionales")) {
             cbcategoria.getSelectionModel().selectFirst();
             } else {
             cbcategoria.getSelectionModel().selectLast();
             }*/
            ArrayList<String> tags = modificarMomento.getTags();
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
            if (tags.size() == 4) {
                t1.setText(tags.get(0));
                t2.setText(tags.get(1));
                t3.setText(tags.get(2));
                t4.setText(tags.get(3));
            }
            String _color = modificarMomento.getColor().replace("-fx-background-color: ", "");
            _color = _color.replace("#", "0x");
            color.setValue(Color.web(_color));
            if (modificarMomento.getId_documento() != 0) {
                imagenFile = new File(modificarMomento.getRutaDocumento());
                InputStream is = null;
                try {
                    is = new FileInputStream(imagenFile);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FXMLRegistroPreguntaUnoController.class.getName()).log(Level.SEVERE, null, ex);
                }
                double width = 191;
                double heigth = 167;
                Image imagen = new Image(is, width, heigth, false, true);
                imgImagen.setImage(imagen);
            }
        }
    }

    public void anadir_imagen() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Todas las imagenes", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        InputStream is = null;
        if (modificarMomento == null) {
            imagenFile = chooser.showOpenDialog(new Stage());
            try {
                is = new FileInputStream(imagenFile);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLRegistroPreguntaUnoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            modificarFile = chooser.showOpenDialog(new Stage());
            try {
                is = new FileInputStream(modificarFile);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLRegistroPreguntaUnoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        double width = 191;
        double heigth = 167;
        Image imagen = new Image(is, width, heigth, false, false);
        imgImagen.setImage(imagen);

    }

    ///////////////////Menu de botones esquina superior derecha///////////////////
    @FXML
    public void minimizar() throws IOException {
        FXMLPrincipalController.ventanaAnadirMomento.setIconified(true);
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
        FXMLPrincipalController.ventanaAnadirMomento.close();
    }

    @FXML
    public void cerrarEncima() throws IOException {
        circulocerr.setFill(Color.web("#D7F2E8"));
    }

    @FXML
    public void cerrarSalida() throws IOException {
        circulocerr.setFill(Color.TRANSPARENT);
    }

    @FXML
    public void registra_momento() throws ConexionBDIncorrecta, IOException, SQLException, InterruptedException {
        String _titular = titular.getText();
        String _descripcion = descripcion.getText();
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
        if (!t4.getText().isEmpty()) {
            tags.add(t4.getText());
        }

        String _fecha = dateFormatter.format(cal.getValue());
        String _color = color.getValue().toString();
        _color = _color.replace("0x", "#");
        if (modificarMomento == null) {
            Momento momento = Momento.insert(_titular, _fecha, _descripcion, "-fx-background-color: " + _color, tags);
            if (imagenFile != null) {
                momento.asociarDocumento(imagenFile);
                controllerPrincipal.mostrarImagenes();
            }
        } else {
            modificarMomento.setTitulo(_titular);
            modificarMomento.setDescripcion(_descripcion);
            modificarMomento.setFecha(_fecha);
            modificarMomento.setColor("-fx-background-color: " + _color);
            modificarMomento.setTags(tags);
            modificarMomento.Update();
            if (modificarFile != null) {
                if(imagenFile != null){
                    System.out.println("Bien");
                    System.out.println(imagenFile.getName());
                    imagenFile.deleteOnExit();
                    imagenFile.delete();
                    imagenFile = null;
                }
                modificarMomento.asociarDocumento(modificarFile);
            } else if (imagenFile != null) {
                modificarMomento.asociarDocumento(imagenFile);
            }
            controllerMomento.imprimir(modificarMomento);
            controllerMYN.cambiarDatos();
            controllerPrincipal.mostrarImagenes();
            if(controllerMYN.isMostrandoTodo()) controllerMYN.mostrarTodo();
            else controllerMYN.cambiarDatos();
        }
        controllerPrincipal.colorearFechas();
        
        //Enseñar momento añadido
        Parent root = null; //Creamos el parent
            ventanaAnadido = new Stage(); //Creamos la ventana que tendrá la vista Principal de la aplicación
            Image icon= new Image(getClass().getResourceAsStream("logo.png"));
            ventanaAnadido.getIcons().add(icon);
            
            try{
                root = FXMLLoader.load(getClass().getResource("FXMLMomentoAñadido.fxml"));
            }catch(IOException e)
            {
                System.out.println("No se puede encontrar el fichero FXML");
            }
            
            ventanaAnadido.setResizable(false); //No se puede modificar el tamaño de la ventana
            ventanaAnadido.setTitle("Añadido"); //Ponemos un título para el panel de Windows
            ventanaAnadido.initStyle(StageStyle.TRANSPARENT);
            Scene escenaAnadido = new Scene(root); //Creamos la escena
            escenaAnadido.setFill( Color.TRANSPARENT );
            ventanaAnadido.setScene(escenaAnadido); //Cargamos la escena
            ventanaAnadido.show();
    }

    @FXML
    public void moverPantalla2() throws IOException {
        anclaje.setOnMouseDragged((MouseEvent me) -> {
            FXMLPrincipalController.ventanaAnadirMomento.setX(me.getScreenX() - initX);
            FXMLPrincipalController.ventanaAnadirMomento.setY(me.getScreenY() - initY);
        });
    }

    @FXML
    public void moverPantalla() throws IOException {
        anclaje.setOnMousePressed((MouseEvent me) -> {
            initX = me.getScreenX() - FXMLPrincipalController.ventanaAnadirMomento.getX();
            initY = me.getScreenY() - FXMLPrincipalController.ventanaAnadirMomento.getY();
        });
    }
}
