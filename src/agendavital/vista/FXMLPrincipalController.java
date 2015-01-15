package agendavital.vista;

import agendavital.modelo.data.Noticia;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import java.io.IOException;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
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
    private Menu menuInicio;
    @FXML
    private Button myButton;
    @FXML
    private Text textPrueba;
    @FXML
    private DatePicker cal;
     final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
                //FXMLNoticiaController controller = loader.getController();
                ventanaNoticia.setScene(escenaNoticia);
                ventanaNoticia.initStyle(StageStyle.UNDECORATED);
                ventanaNoticia.show();
                //Muestra ventana
            }
            
            
            
            
}