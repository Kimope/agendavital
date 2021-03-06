package agendavital.vista;

import agendavital.modelo.data.Etiqueta;
import agendavital.modelo.data.Momento;
import agendavital.modelo.data.Noticia;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import agendavital.modelo.util.UsuarioLogueado;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.util.StringConverter;

/**
 * @author Enrique
 */
public class FXMLPrincipalController implements Initializable {

    //Se declaran los atributos de FXMLPrincipal, con el nombre del #id que tienen en JavaFX
    public static Stage ventanaRegistro;
    public static Stage ventanaLogin;
    public static Stage ventanaAnadirNoticia;
    public static Stage ventanaAnadirMomento;
    public static Stage ventanaAcercaDe;
    public static Stage ventanaAdministracion;
    public static Stage ventanaDia;
    public static String fechaSeleccionada = null;
    @FXML
    private Line lineamenu1;
    @FXML
    private Line lineamenu2;
    @FXML
    private Line lineamenu3;
    @FXML
    private Line lineamenu4;
    @FXML
    private Line lineamenu5;
    @FXML
    private Line lineamenu6;
    @FXML
    private DatePicker cal;
    @FXML
    private AnchorPane panecentral;
    @FXML
    private Text logueado;
    @FXML 
    private Text cerrar_sesion;
    @FXML
    private Text t1;
    @FXML
    private Text t2;
    @FXML
    private Text t3;
    @FXML
    private Text t4;
    @FXML
    private Text t5;
    @FXML
    private Text t6;
    @FXML
    private TextField txtBuscar;
     TreeMap<LocalDate, String> fechas = null;
     TreeMap<LocalDate, String> momentos = null;
     ArrayList<File> imagenes = null;
    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    Pagination pagination = null;

    public int itemsPerPage() {
        return 1;
    }

    public VBox createPage(int pageIndex) {
        VBox box = new VBox();
        ImageView im = new ImageView();
        File file = imagenes.get(pageIndex);
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLRegistroPreguntaUnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        double width = 350;
        double heigth = 300;
        Image imagen = new Image(is, width, heigth, false, true);
        im.setImage(imagen);
        box.getChildren().add(im);

        return box;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ////////////COLOREADO DE FECHAS/////////////
       
        try {
                    fechas = Noticia.getNoticiasFecha();
                    momentos = Momento.getNoticiasFecha();
                } catch (ConexionBDIncorrecta ex) {
                    Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }
        ArrayList<String> etiquetas = Etiqueta.getEtiqueta();
        if (etiquetas.size() == 1){
            t1.setText(etiquetas.get(0));
        }
       else if (etiquetas.size() == 2){
            t1.setText(etiquetas.get(0));
            t2.setText(etiquetas.get(1));
        }
       else if (etiquetas.size() == 3){
            t1.setText(etiquetas.get(0));
            t2.setText(etiquetas.get(1));
            t3.setText(etiquetas.get(2));
        }
       else if (etiquetas.size() == 4){
            t1.setText(etiquetas.get(0));
            t2.setText(etiquetas.get(1));
            t3.setText(etiquetas.get(2));
            t4.setText(etiquetas.get(3));
        }
       else if (etiquetas.size() == 5){
           t1.setText(etiquetas.get(0));
            t2.setText(etiquetas.get(1));
            t3.setText(etiquetas.get(2));
            t4.setText(etiquetas.get(3));
            t5.setText(etiquetas.get(4));
        }
       else if (etiquetas.size() == 6){
            t1.setText(etiquetas.get(0));
            t2.setText(etiquetas.get(1));
            t3.setText(etiquetas.get(2));
            t4.setText(etiquetas.get(3));
            t5.setText(etiquetas.get(4));
            t6.setText(etiquetas.get(5));
        }
        cal.setValue(LocalDate.now());
        cal.setStyle("-fx-font: 16pt Arial;");
        StringConverter converter = new StringConverter<LocalDate>() {

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    LocalDate date = LocalDate.parse(string, dateFormatter);

                    if (date.isBefore(LocalDate.now()) || date.isAfter(LocalDate.now().plusYears(1))) {
                        return cal.getValue();
                    } else {
                        return date;
                    }
                } else {
                    return null;
                }
            }
        };
        try {
            mostrarImagenes();
        } catch (ConexionBDIncorrecta ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        colorearFechas();
        cal.setConverter(converter);
        cal.setPromptText("dd-MM-yyyy");
        logueado.setText("Logueado como "+UsuarioLogueado.getLogueado().getNick());
        cerrar_sesion.setOnMouseClicked((MouseEvent t) -> {
            Parent root = null; //Creamos un parent, que es una clase que se encarga del escenario gráfico, que tendrá sus hijos, que serán las escenas
            agendavital.AgendaVital.ventanaLogin= new Stage();
            Image icon = new Image(getClass().getResourceAsStream("imagenes_interfaz/logo.png"));
            agendavital.AgendaVital.ventanaLogin.getIcons().add(icon);
            agendavital.AgendaVital.ventanaLogin.setTitle("Login");
            try{
                root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml")); //Cargamos el fichero FXML relacionado con este archivo, que es el que tiene los paneles, textos, etc, es decir, lo que se muestra por pantalla
            }catch(IOException e)
            {
                e.printStackTrace();
            }
            
            
            agendavital.AgendaVital.ventanaLogin.initStyle( StageStyle.TRANSPARENT ); //No se muestra el menú de Windows
            agendavital.AgendaVital.ventanaLogin.setAlwaysOnTop(true);  //Siempre va a estar visible, no se puede minimizar
            Scene scene = new Scene(root); //Creamos una escena en el root creado, que es una clase contenedora del escenario gráfico, en este caso con la ventana que se muestra por pantalla
            scene.setFill( Color.TRANSPARENT ); //Para que no se vea la escena (lo usamos para que no se vean los picos de los bordes
            agendavital.AgendaVital.ventanaLogin.setScene(scene); //Cargamos la escena
            agendavital.AgendaVital.ventanaLogin.centerOnScreen();
            agendavital.AgendaVital.ventanaLogin.show(); //La mostramos por pantalla
            FXMLLoginController.ventanaPrincipal.close();
        });
        
    }
    //
    

    
    public void mostrarImagenes() throws ConexionBDIncorrecta{
        panecentral.getChildren().remove(pagination);
        imagenes = UsuarioLogueado.getTodasImagenes();
        int numOfPage = imagenes.size();
        if(numOfPage > 0){
         pagination = new Pagination(numOfPage);
        pagination.setPageFactory((Integer pageIndex) -> createPage(pageIndex));
        AnchorPane.setTopAnchor(pagination, 30.0);
        AnchorPane.setRightAnchor(pagination, 25.0);
        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        panecentral.getChildren().add(pagination);
        
        }
    }
    
    public void colorearFechas(){
        
         Callback<DatePicker, DateCell> dayCellFactory =( DatePicker dp) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if(momentos.containsKey(item)) setStyle(momentos.get(item));
                else if (fechas.containsKey(item)) {
                        switch (fechas.get(item)) {
                            case "Noticias Internacionales":
                                setStyle("-fx-background-color: #A8F9FF");
                                break;
                            case "Noticias Nacionales":
                                setStyle("-fx-background-color: red");
                                break;
                            default:
                                setStyle("-fx-background-color: blue");
                                break;
                        
                    }
                }
   
             }
            
        };
         cal.setDayCellFactory(dayCellFactory);
    }
    

    ////////////MENU///////////////////
    public void ini() throws IOException {
        lineamenu1.setVisible(true);
    }

    public void ini_salida() throws IOException {
        lineamenu1.setVisible(false);
    }

    public void moment() throws IOException {
        lineamenu2.setVisible(true);
    }

    public void moment_salida() throws IOException {
        lineamenu2.setVisible(false);
    }

    public void not() throws IOException {
        lineamenu3.setVisible(true);
    }

    public void not_salida() throws IOException {
        lineamenu3.setVisible(false);
    }

    public void adm() throws IOException {
        lineamenu4.setVisible(true);
    }

    public void adm_salida() throws IOException {
        lineamenu4.setVisible(false);
    }

    public void ayu() throws IOException {
        lineamenu5.setVisible(true);
    }

    public void ayu_salida() throws IOException {
        lineamenu5.setVisible(false);
    }

    public void acerca() throws IOException {
        lineamenu6.setVisible(true);
    }

    public void acerca_salida() throws IOException {
        lineamenu6.setVisible(false);
    }

    public void botonnoticia() throws IOException {
        ventanaAnadirNoticia = new Stage();
        if(ventanaAnadirNoticia.isShowing()){
            ventanaAnadirNoticia.setIconified(false);
            ventanaAnadirNoticia.toFront();
            
        }else{
        Parent root = null;

        Image icon = new Image(getClass().getResourceAsStream("imagenes_interfaz/logo.png"));
        ventanaAnadirNoticia.getIcons().add(icon);
        ventanaAnadirNoticia.setTitle("Nueva Noticia");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAnadirNoticia.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("No se puede encontrar el fichero FXML");
        }

        Scene escenaNoticia = new Scene(root);
        FXMLAnadirNoticiaController controller = loader.getController();
        controller.setControllerPrincipal(this);
        controller.inicializarVentana();
        ventanaAnadirNoticia.setScene(escenaNoticia);
        ventanaAnadirNoticia.initStyle(StageStyle.UNDECORATED);
        ventanaAnadirNoticia.show();}
    }

    public void botonmomento() throws IOException {
        ventanaAnadirMomento = new Stage();
        if(ventanaAnadirMomento.isShowing()){
            ventanaAnadirMomento.setIconified(false);
            ventanaAnadirMomento.toFront();
            
        }else{
        Parent root = null;
        Image icon = new Image(getClass().getResourceAsStream("imagenes_interfaz/logo.png"));
        ventanaAnadirMomento.getIcons().add(icon);
        ventanaAnadirMomento.setTitle("Nuevo Momento");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAnadirMomento.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("No se puede encontrar el fichero FXML");
        }

        Scene escenaNoticia = new Scene(root);
        FXMLAnadirMomentoController controller = loader.getController();
        controller.setControllerPrincipal(this);
        ventanaAnadirMomento.setScene(escenaNoticia);
        ventanaAnadirMomento.initStyle(StageStyle.UNDECORATED);
        ventanaAnadirMomento.show();
        }
    }

    public void botonacerca() throws IOException {
        ventanaAcercaDe = new Stage();
        if(ventanaAcercaDe.isShowing()){
            ventanaAcercaDe.setIconified(false);
            ventanaAcercaDe.toFront();
            
        }else{
        Parent root = null;
        Image icon = new Image(getClass().getResourceAsStream("imagenes_interfaz/logo.png"));
        ventanaAcercaDe.getIcons().add(icon);
        ventanaAcercaDe.setTitle("Acerca De");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAcercaDe.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("No se puede encontrar el fichero FXML");
        }

        Scene escenaNoticia = new Scene(root);
        //FXMLAcercaDeController controller = loader.getController();
        ventanaAcercaDe.setScene(escenaNoticia);
        ventanaAcercaDe.initStyle(StageStyle.UNDECORATED);
        ventanaAcercaDe.show();
        }
    }

    public void botonaadmin() throws IOException {
        ventanaAdministracion = new Stage();
        if(ventanaAdministracion.isShowing()){
            ventanaAdministracion.setIconified(false);
            ventanaAdministracion.toFront();
            
        }else{
        Parent root = null;
        Image icon = new Image(getClass().getResourceAsStream("imagenes_interfaz/logo.png"));
        ventanaAdministracion.getIcons().add(icon);
        ventanaAdministracion.setTitle("Ayuda");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAdministracion.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("No se puede encontrar el fichero FXML");
        }

        Scene escenaNoticia = new Scene(root);
        //FXMLNoticiaController controller = loader.getController();
        ventanaAdministracion.setScene(escenaNoticia);
        ventanaAdministracion.initStyle(StageStyle.UNDECORATED);
        ventanaAdministracion.show();
        }
    }

    public void consultar_todo() throws IOException {
        ventanaDia = new Stage();
        if(ventanaDia.isShowing()){
            ventanaDia.setIconified(false);
            ventanaDia.toFront();
            
        }else{
        Parent root = null;
        fechaSeleccionada = dateFormatter.format(cal.getValue());
        Image icon = new Image(getClass().getResourceAsStream("imagenes_interfaz/logo.png"));
        ventanaDia.getIcons().add(icon);
        ventanaDia.setTitle("Todas las noticias y momentos");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMomentosyNoticias.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("No se puede encontrar el fichero FXML");
        }

        Scene escenaNoticia = new Scene(root);
        ventanaDia.initStyle(StageStyle.TRANSPARENT);
        FXMLMomentosyNoticiasController controller = loader.getController();
        controller.setMostrandoTodo(true);
        controller.setControllerPrincipal(this);
        controller.mostrarTodo();
        escenaNoticia.setFill(Color.TRANSPARENT);
        ventanaDia.setScene(escenaNoticia);
        ventanaDia.show();
        }
    }

    public void momentosynoticias() throws IOException {
        ventanaDia = new Stage();
        if(ventanaDia.isShowing()){
            ventanaDia.setIconified(false);
            ventanaDia.toFront();
            
        }else{
        Parent root = null;
        fechaSeleccionada = dateFormatter.format(cal.getValue());
        Image icon = new Image(getClass().getResourceAsStream("imagenes_interfaz/logo.png"));
        ventanaDia.getIcons().add(icon);
        ventanaDia.setTitle("Noticia");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMomentosyNoticias.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("No se puede encontrar el fichero FXML");
        }

        Scene escenaNoticia = new Scene(root);
        ventanaDia.initStyle(StageStyle.TRANSPARENT);
        FXMLMomentosyNoticiasController controller = loader.getController();
        controller.setMostrandoTodo(false);
        controller.cambiarDatos();
        controller.setControllerPrincipal(this);
        escenaNoticia.setFill(Color.TRANSPARENT);
        ventanaDia.setScene(escenaNoticia);
        ventanaDia.show();
        }
    }
    @FXML
    public void buscar() throws ConexionBDIncorrecta, SQLException{
        ventanaDia = new Stage();
        if(ventanaDia.isShowing()){
            ventanaDia.setIconified(false);
            ventanaDia.toFront();
            
        }else{
        Parent root = null;
        Image icon = new Image(getClass().getResourceAsStream("imagenes_interfaz/logo.png"));
        ventanaDia.getIcons().add(icon);
        ventanaDia.setTitle("Noticia");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMomentosyNoticias.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("No se puede encontrar el fichero FXML");
        }

        Scene escenaNoticia = new Scene(root);
        ventanaDia.initStyle(StageStyle.TRANSPARENT);
        FXMLMomentosyNoticiasController controller = loader.getController();
        controller.setMostrandoTodo(false);
        controller.cambiarDatosBusqueda(txtBuscar.getText());
        controller.setControllerPrincipal(this);
        escenaNoticia.setFill(Color.TRANSPARENT);
        ventanaDia.setScene(escenaNoticia);
        ventanaDia.show();
    }
}
}
