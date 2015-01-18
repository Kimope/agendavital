package agendavital.vista;

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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
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
    public static Stage ventanaLogin=new Stage();
    public static Stage ventanaAnadirNoticia=new Stage();
    public static Stage ventanaAnadirMomento=new Stage();
    public static Stage ventanaAcercaDe=new Stage();
    public static Stage ventanaAdministracion=new Stage();
    public static Stage ventanaDia=new Stage();
    public boolean desdeRegistro;

    public void setDesdeRegistro(boolean desdeRegistro) {
        this.desdeRegistro = desdeRegistro;
    }
    
     
     
    public static String fechaSeleccionada = null;
    File filesJpg[];
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

    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public int nRecargas = 0;
    Pagination pagination = null;

    public int itemsPerPage() {
        return 1;
    }

    public VBox createPage(int pageIndex) {
        VBox box = new VBox();
        ImageView im = new ImageView();
        File file = filesJpg[pageIndex];
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
            Image icon = new Image(getClass().getResourceAsStream("logo.png"));
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
        ArrayList<File> imagenes = UsuarioLogueado.getTodasImagenes();
        filesJpg = new File[imagenes.size()];
        for(int i = 0; i < imagenes.size(); i++){
            filesJpg[i] = imagenes.get(i);
        }
        int numOfPage = filesJpg.length;
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
                if(item.isAfter(LocalDate.now())) setDisable(true);
                ArrayList<Pair<LocalDate, Noticia>> fechas = null;
                ArrayList<Pair<LocalDate, String>> momentos = null;
                try {
                    fechas = Noticia.getNoticiasFecha();
                    momentos = Momento.getNoticiasFecha();
                } catch (ConexionBDIncorrecta ex) {
                    Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Pair<LocalDate, Noticia> fecha : fechas) {
                    if (item.equals(fecha.getKey())) {
                        switch (fecha.getValue().getCategoria()) {
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
                 for (Pair<LocalDate, String> momento : momentos) {
                     if(item.equals(momento.getKey())){
                         setStyle(momento.getValue());
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
        if(ventanaAnadirNoticia.isShowing()){
            ventanaAnadirNoticia.setIconified(false);
            ventanaAnadirNoticia.toFront();
            
        }else{
        Parent root = null;
        ventanaAnadirNoticia = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("logo.png"));
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
        if(ventanaAnadirMomento.isShowing()){
            ventanaAnadirMomento.setIconified(false);
            ventanaAnadirMomento.toFront();
            
        }else{
        Parent root = null;
        ventanaAnadirMomento = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("logo.png"));
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
        if(ventanaAcercaDe.isShowing()){
            ventanaAcercaDe.setIconified(false);
            ventanaAcercaDe.toFront();
            
        }else{
        Parent root = null;
        ventanaAcercaDe = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("logo.png"));
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

   /* public void botonayuda() throws IOException {
        
        Parent root = null;
        ventanaNoticia = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("logo.png"));
        ventanaNoticia.getIcons().add(icon);
        ventanaNoticia.setTitle("Ayuda");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMomento.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("No se puede encontrar el fichero FXML");
        }

        Scene escenaNoticia = new Scene(root);
        //FXMLNoticiaController controller = loader.getController();
        ventanaNoticia.setScene(escenaNoticia);
        ventanaNoticia.initStyle(StageStyle.UNDECORATED);
        ventanaNoticia.show();
        //Muestra ventana
    }*/

    public void botonaadmin() throws IOException {
        if(ventanaAdministracion.isShowing()){
            ventanaAdministracion.setIconified(false);
            ventanaAdministracion.toFront();
            
        }else{
        Parent root = null;
        ventanaAdministracion = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("logo.png"));
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
        if(ventanaDia.isShowing()){
            ventanaDia.setIconified(false);
            ventanaDia.toFront();
            
        }else{
        Parent root = null;
        ventanaDia = new Stage();
        fechaSeleccionada = dateFormatter.format(cal.getValue());
        Image icon = new Image(getClass().getResourceAsStream("logo.png"));
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
        if(ventanaDia.isShowing()){
            ventanaDia.setIconified(false);
            ventanaDia.toFront();
            
        }else{
        Parent root = null;
        ventanaDia = new Stage();
        fechaSeleccionada = dateFormatter.format(cal.getValue());
        Image icon = new Image(getClass().getResourceAsStream("logo.png"));
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

}
