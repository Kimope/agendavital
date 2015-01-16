package agendavital.vista;

import agendavital.modelo.data.Noticia;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import agendavital.modelo.util.UsuarioLogueado;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.text.Text;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
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
    public static Stage ventanaMes;
    public static Stage ventanaNoticia;
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
    
     final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

     public int itemsPerPage() {
        return 1;
    }
     public VBox createPage(int pageIndex) {
        VBox box = new VBox();
            ImageView im= new ImageView();
            File file = filesJpg[pageIndex];
            InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLRegistroPreguntaUnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        double width = 350;
        double heigth = 300;
        Image imagen = new Image(is,width,heigth,false,true);
        im.setImage(imagen);
        box.getChildren().add(im);
        
        return box;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        String s="imagenes";
        //String s= "Momentos/"+UsuarioLogueado.getLogueado().getNick();
        File f=new File(s);
        System.out.print(f.getPath());
        if (f.exists()){
        filesJpg = f.listFiles();
        }
        else{System.out.print("ERROR NO SE HA ENCONTRADO EL DIRECTORIO");}
        
        int numOfPage = filesJpg.length;
        Pagination pagination = new Pagination(numOfPage);
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });
        AnchorPane.setTopAnchor(pagination, 30.0);
        AnchorPane.setRightAnchor(pagination, 25.0);
        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        panecentral.getChildren().add(pagination);
        
        
        ////////////COLOREADO DE FECHAS/////////////
        cal.setValue(LocalDate.now());
        cal.setStyle("-fx-font: 16pt Arial;");


        Callback<DatePicker, DateCell> dayCellFactory =( DatePicker dp) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                ArrayList<Pair<LocalDate, Noticia>> fechas = null;
                try {
                    fechas = Noticia.getNoticiasFecha();
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
             }
        };
          
        

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

                cal.setDayCellFactory(dayCellFactory);
                cal.setConverter(converter);
                cal.setPromptText("dd-MM-yyyy");
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
                Parent root = null;
                ventanaNoticia = new Stage();
                Image icon = new Image(getClass().getResourceAsStream("logo.png"));
                ventanaNoticia.getIcons().add(icon);
                ventanaNoticia.setTitle("Nueva noticia");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLNoticia.fxml"));
                try {
                    root = loader.load();
                } catch (IOException e) {
                    System.out.println("No se puede encontrar el fichero FXML");
                }

                Scene escenaNoticia = new Scene(root);
                FXMLNoticiaController controller = loader.getController();
                ventanaNoticia.setScene(escenaNoticia);
                ventanaNoticia.initStyle(StageStyle.UNDECORATED);
                ventanaNoticia.show();
            }
            public void botonmomento() throws IOException {
                Parent root = null;
                ventanaNoticia = new Stage();
                Image icon = new Image(getClass().getResourceAsStream("logo.png"));
                ventanaNoticia.getIcons().add(icon);
                ventanaNoticia.setTitle("Nuevo Momento");
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
            }
            public void botonacerca() throws IOException {
                Parent root = null;
                ventanaNoticia = new Stage();
                Image icon = new Image(getClass().getResourceAsStream("logo.png"));
                ventanaNoticia.getIcons().add(icon);
                ventanaNoticia.setTitle("Acerca De");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAcercaDe.fxml"));
                try {
                    root = loader.load();
                } catch (IOException e) {
                    System.out.println("No se puede encontrar el fichero FXML");
                }

                Scene escenaNoticia = new Scene(root);
                //FXMLAcercaDeController controller = loader.getController();
                ventanaNoticia.setScene(escenaNoticia);
                ventanaNoticia.initStyle(StageStyle.UNDECORATED);
                ventanaNoticia.show();
            }
            public void botonayuda() throws IOException {
                Parent root = null;
                ventanaNoticia = new Stage();
                Image icon = new Image(getClass().getResourceAsStream("logo.png"));
                ventanaNoticia.getIcons().add(icon);
                ventanaNoticia.setTitle("Ayuda");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAyuda.fxml"));
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
            }
            public void botonaadmin() throws IOException {
                Parent root = null;
                ventanaNoticia = new Stage();
                Image icon = new Image(getClass().getResourceAsStream("logo.png"));
                ventanaNoticia.getIcons().add(icon);
                ventanaNoticia.setTitle("Ayuda");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAdministracion.fxml"));
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
            }
            
            
            
            public void consultar_dia() throws IOException {
                Parent root = null;
                ventanaNoticia = new Stage();
                Image icon = new Image(getClass().getResourceAsStream("logo.png"));
                ventanaNoticia.getIcons().add(icon);
                ventanaNoticia.setTitle("Noticia");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLNoticia.fxml"));
                try {
                    root = loader.load();
                } catch (IOException e) {
                    System.out.println("No se puede encontrar el fichero FXML");
                }

                Scene escenaNoticia = new Scene(root);
                ventanaNoticia.initStyle(StageStyle.TRANSPARENT);
                //FXMLNoticiaController controller = loader.getController();
                escenaNoticia.setFill(Color.TRANSPARENT);
                ventanaNoticia.setScene(escenaNoticia);
                ventanaNoticia.show();
                //Muestra ventana
            }
            public void momentosynoticias() throws IOException {
                Parent root = null;
                ventanaNoticia = new Stage();
                Image icon = new Image(getClass().getResourceAsStream("logo.png"));
                ventanaNoticia.getIcons().add(icon);
                ventanaNoticia.setTitle("Noticia");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMomentosyNoticias.fxml"));
                try {
                    root = loader.load();
                } catch (IOException e) {
                    System.out.println("No se puede encontrar el fichero FXML");
                }

                Scene escenaNoticia = new Scene(root);
                ventanaNoticia.initStyle(StageStyle.TRANSPARENT);
                //FXMLNoticiaController controller = loader.getController();
                escenaNoticia.setFill(Color.TRANSPARENT);
                ventanaNoticia.setScene(escenaNoticia);
                ventanaNoticia.show();
                //Muestra ventana
            }

    
            
            
            
            
}
