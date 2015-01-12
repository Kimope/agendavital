package agendavital.vista;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import agendavital.modelo.data.Usuario;
import agendavital.modelo.excepciones.ContrasenaCaracteresRaros;
import agendavital.modelo.excepciones.ContrasenaMalRepetida;
import agendavital.modelo.excepciones.ContrasenaMuyCorta;
import agendavital.modelo.excepciones.ContrasenaSinMayuscula;
import agendavital.modelo.excepciones.ContrasenaSinNumeros;
import agendavital.modelo.excepciones.NickMuyCorto;
import agendavital.modelo.excepciones.NickYaExiste;
import java.sql.SQLException;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Enrique
 */
public class FXMLRegistroController implements Initializable {

    //Ventana que sale al completarse el registro

    static Stage ventanaRegistroCompletado;

    //////////////Variables de la ventana de registro//////////////
    public static final double ANCHO = 596;
    public static final double ALTO = 488;
    private double initX = ANCHO / 2;
    private double initY = ALTO / 2;

    @FXML
    private AnchorPane anclaje;

    @FXML
    private TextField tfnombre;
    @FXML
    private TextField tfapellido;
    @FXML
    private TextField tfnick;
    @FXML
    private Text textError;
    @FXML
    private TextFlow tfError;

    @FXML
    private PasswordField tfcontraseña;
    @FXML
    private PasswordField tfcontraseña2;

    @FXML
    private ComboBox cbdia;
    @FXML
    private ComboBox cbmes;
    @FXML
    private ComboBox cbaño;

    @FXML
    private Button butregistrarse;

    @FXML
    private Line lineamin;
    @FXML
    private Line lineacerrar1;
    @FXML
    private Line lineacerrar2;

    @FXML
    private Circle circulomin;
    @FXML
    private Circle circulocerr;
        //------------------------------------------------------------//

    ///////////////////Menu de botones esquina superior derecha///////////////////
    @FXML
    public void minimizar() throws IOException {
        FXMLLoginController.ventanaRegistro.setIconified(true);
    }

    @FXML
    public void minimizarEncima() throws IOException {
        circulomin.setFill(Color.web("#c97c5d"));
    }

    @FXML
    public void minimizarSalida() throws IOException {
        circulomin.setFill(Color.TRANSPARENT);
    }

    @FXML
    public void cerrar() throws IOException {
        FXMLLoginController.ventanaRegistro.close();
    }

    @FXML
    public void cerrarEncima() throws IOException {
        circulocerr.setFill(Color.web("#c97c5d"));
    }

    @FXML
    public void cerrarSalida() throws IOException {
        circulocerr.setFill(Color.TRANSPARENT);
    }

    // Maneja los colores de los fondos del formulario

    @FXML
    public void blanquearnombre() throws IOException {
        tfnombre.setStyle("-fx-background-color: white");
        tfapellido.setStyle("-fx-background-color: #FCF0CC");
        tfnick.setStyle("-fx-background-color: #FCF0CC");
        tfcontraseña.setStyle("-fx-background-color: #FCF0CC");
        tfcontraseña2.setStyle("-fx-background-color: #FCF0CC");
    }

    @FXML
    public void blanquearapellido() throws IOException {
        tfapellido.setStyle("-fx-background-color: white");
        tfnombre.setStyle("-fx-background-color: #FCF0CC");
        tfnick.setStyle("-fx-background-color: #FCF0CC");
        tfcontraseña.setStyle("-fx-background-color: #FCF0CC");
        tfcontraseña2.setStyle("-fx-background-color: #FCF0CC");
    }

    @FXML
    public void blanquearnick() throws IOException {
        tfnick.setStyle("-fx-background-color: white");
        tfnombre.setStyle("-fx-background-color: #FCF0CC");
        tfapellido.setStyle("-fx-background-color: #FCF0CC");
        tfcontraseña.setStyle("-fx-background-color: #FCF0CC");
        tfcontraseña2.setStyle("-fx-background-color: #FCF0CC");
    }

    @FXML
    public void blanquearcontraseña() throws IOException {
        tfcontraseña.setStyle("-fx-background-color: white");
        tfnombre.setStyle("-fx-background-color: #FCF0CC");
        tfapellido.setStyle("-fx-background-color: #FCF0CC");
        tfnick.setStyle("-fx-background-color: #FCF0CC");
        tfcontraseña2.setStyle("-fx-background-color: #FCF0CC");
    }

    @FXML
    public void blanquearcontraseña2() throws IOException {
        tfcontraseña2.setStyle("-fx-background-color: white");
        tfnombre.setStyle("-fx-background-color: #FCF0CC");
        tfapellido.setStyle("-fx-background-color: #FCF0CC");
        tfnick.setStyle("-fx-background-color: #FCF0CC");
        tfcontraseña.setStyle("-fx-background-color: #FCF0CC");
    }
    //---------------------------------------------------------------------------------//

    ////////////////////Comprobación de campos y registro de usuarios////////////////////
    @FXML
    public void registra_usuario() throws IOException {
        //Comprueban si un campo está vacío o no
        boolean name = FormValidation.textFieldEmpty(tfnombre);
        boolean apellido = FormValidation.textFieldEmpty(tfapellido);
        boolean nick = FormValidation.textFieldEmpty(tfnick);
        boolean contraseña1 = FormValidation.textFieldEmpty(tfcontraseña);
        boolean contraseña2 = FormValidation.textFieldEmpty(tfcontraseña2);

        //Si no están vacíos, se insertan (BD) y se pasa a la ventana de registro completado
        if (name && apellido && nick && contraseña1 && contraseña2) {
            try {
                Parent root = null;
                String fechaNac = (String) cbaño.getValue() + "/"+ (String) cbmes.getValue() +"/"+ (String) cbdia.getValue() ;
                /*AQUI VIENE EL PUNTO CLAVE. Hacer solo una pantalla de error*/
                Usuario.Insert(tfnick.getText(), tfnombre.getText(), tfapellido.getText(), tfcontraseña.getText(), tfcontraseña2.getText(), fechaNac);
                ventanaRegistroCompletado = new Stage();
                Image icon = new Image(getClass().getResourceAsStream("logo.png"));
                ventanaRegistroCompletado.getIcons().add(icon);
                ventanaRegistroCompletado.setTitle("Registro Completo");

                try {
                    root = FXMLLoader.load(getClass().getResource("FXMLRegistroCompletado.fxml"));
                } catch (IOException e) {
                    System.out.println("No se puede encontrar el fichero FXML");
                }

                Scene escenaRegistroCompletado = new Scene(root);
                ventanaRegistroCompletado.setScene(escenaRegistroCompletado);
                ventanaRegistroCompletado.initStyle(StageStyle.UNDECORATED);
                ventanaRegistroCompletado.show();
                FXMLLoginController.ventanaRegistro.close();
            } catch (SQLException ex) {
                textError.setText("Fallo al acceder a la BD"); //Meter en la pantalla este texto
                textError.setFill(Color.YELLOW);
            } catch (NickMuyCorto ex) {
                textError.setText(ex.getMensaje());
                tfnick.setStyle("-fx-background-color:#F6F740");
                textError.setFill(Color.YELLOW);
            } catch (NickYaExiste ex) {
                textError.setText(ex.getMensaje());
                tfnick.setStyle("-fx-background-color:#F6F740");
                textError.setFill(Color.YELLOW);
            } catch (ContrasenaMalRepetida ex) {
                textError.setText(ex.getMensaje());
                tfcontraseña2.setStyle("-fx-background-color:#F6F740");
                textError.setFill(Color.YELLOW);
            } catch (ContrasenaMuyCorta ex) {
                textError.setText(ex.getMensaje());
                tfcontraseña.setStyle("-fx-background-color:#F6F740");
                textError.setFill(Color.YELLOW);
            } catch (ContrasenaCaracteresRaros ex) {
                textError.setText(ex.getMensaje());
                tfnombre.setStyle("-fx-background-color:#F6F740");
                textError.setFill(Color.YELLOW);
            } catch (ContrasenaSinMayuscula ex) {
                textError.setText(ex.getMensaje());
                tfcontraseña.setStyle("-fx-background-color:#F6F740");
                textError.setFill(Color.YELLOW);
            } catch (ContrasenaSinNumeros ex) {
                textError.setText(ex.getMensaje());
                tfcontraseña.setStyle("-fx-background-color:#F6F740");
                textError.setFill(Color.YELLOW);
            }
        }
        
    }

    //Inicializa la ventana dándole valores a los campos
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Se le dan los valores a los campos de selección
        cbdia.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
        cbmes.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        cbaño.getItems().addAll("1964", "1965", "1966", "1967", "1968", "1969", "1970", "1971", "1972", "1973", "1974", "1975", "1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004");
        //Se selecciona el primero para que sea el que se muestre
        cbdia.getSelectionModel().selectFirst();
        cbmes.getSelectionModel().selectFirst();
        cbaño.getSelectionModel().selectFirst();
        //anclaje.setBackground(Background.EMPTY);
    }

    /////////////////////Métodos para mover la pantalla clickando en cualquier lugar/////////////////////
    @FXML
    public void moverPantalla() throws IOException {
        anclaje.setOnMousePressed((MouseEvent me) -> {
            initX = me.getScreenX() - FXMLLoginController.ventanaRegistro.getX();
            initY = me.getScreenY() - FXMLLoginController.ventanaRegistro.getY();
        });
    }

    @FXML
    public void moverPantalla2() throws IOException {
        anclaje.setOnMouseDragged((MouseEvent me) -> {
            FXMLLoginController.ventanaRegistro.setX(me.getScreenX() - initX);
            FXMLLoginController.ventanaRegistro.setY(me.getScreenY() - initY);
        });
    }
    //-----------------------------------------------------------------------------------------------//
}
