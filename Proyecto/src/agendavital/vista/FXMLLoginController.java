package agendavital.vista;

import agendavital.AgendaVital;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import agendavital.modelo.data.Usuario;
import agendavital.modelo.excepciones.ContrasenaMalIntroducida;
import agendavital.modelo.excepciones.NickMalIntroducido;
import java.sql.SQLException;
import javafx.scene.control.Label;

/**
 * @author Enrique
 */
public class FXMLLoginController implements Initializable 
{
    //Ventanas//
    public static Stage ventanaPrincipal; //Declaramos la ventana de Principal como static
    public static Stage ventanaRegistro; //Declaramos la ventana de Registro como static
    
    //////////////Variables de la ventana de registro//////////////
    public static final double ANCHO = 325;
    public static final double ALTO= 365;
    private double initX=ANCHO/2;
    private double initY=ALTO/2;    
    
    @FXML private Pane anclaje;
    
    public static String userLogin = "";
    public static String passLogin = "";
    
    @FXML private TextField tfUsuario;
    @FXML private TextField tfContra;
    
    @FXML private PasswordField txtPassword;
    
    @FXML private Button btnLogin;
    @FXML private Button btnCancel;
    
    @FXML private Line lineacerrar1;
    @FXML private Line lineacerrar2;
    
    @FXML private Circle circulocerr;
    @FXML private Label error_login;
    //------------------------------------------------------------//

    @Override
    public void initialize(URL url, ResourceBundle rb) {}    
    
    @FXML
    public void cerrar() throws IOException
    {
        AgendaVital.ventanaLogin.close();
    }
    
    public void blanquearUsuario() throws IOException
    {
        tfUsuario.setStyle("-fx-background-color: white");
        tfContra.setStyle("-fx-background-color: #FCF0CC");          
    }
    
    public void blanquearContra() throws IOException
    {
        tfUsuario.setStyle("-fx-background-color: #FCF0CC");
        tfContra.setStyle("-fx-background-color: white");         
    }
    
    @FXML
    public void cerrarEncima() throws IOException
    {
        circulocerr.setFill(Color.web("#c97c5d"));
    }
    
    @FXML
    public void cerrarSalida() throws IOException
    {
        circulocerr.setFill(Color.TRANSPARENT);
    }
    
    //Método que sirve para realizar las acciones que ocurrirán al pulsar el botón de login
    @FXML
    public void login() throws IOException
    {
        try{
            Parent root = null; //Creamos el parent
            ventanaPrincipal = new Stage(); //Creamos la ventana que tendrá la vista Principal de la aplicación
            Image icon= new Image(getClass().getResourceAsStream("logo.png"));
            ventanaPrincipal.getIcons().add(icon);
            /*PUNTO CLAVE*/
            Usuario.usuarioExiste(tfUsuario.getText(), tfContra.getText());
            
            try{
                root = FXMLLoader.load(getClass().getResource("FXMLPrincipal.fxml"));
            }catch(IOException e)
            {
                System.out.println("No se puede encontrar el fichero FXML");
            }
            
            //ventanaPrincipal.setResizable(false); //No se puede modificar el tamaño de la ventana
            ventanaPrincipal.setTitle("Bienvenido a su Agenda Vital"); //Ponemos un título para el panel de Windows
            Scene escenaPrincipal = new Scene(root); //Creamos la escena
            ventanaPrincipal.setScene(escenaPrincipal); //Cargamos la escena
            AgendaVital.ventanaLogin.close(); // Cerramos la pantalla del Login
            ventanaPrincipal.show(); //Mostramos la pantalla principal
        }catch(SQLException ex)
        {
            
            //System.err.println("No se ha podido acceder a la BD"); //Pantalla de error con este texto
        } catch (NickMalIntroducido ex) {
            error_login.setText("Nombre de usuario incorrecto");
            tfUsuario.setStyle("-fx-background-color:#F6F740");
        } catch (ContrasenaMalIntroducida ex) {
            error_login.setText("Contraseña incorrecta");
            tfContra.setStyle("-fx-background-color:#F6F740");
            
        }
        /*Y cuando acabeis, quitad los System.err...*/
    }
    
    //Método que sirve para realizar las acciones que ocurrirán al pulsar el botón de Registrarme
    @FXML
    public void registro() throws IOException
    {
        Parent root = null;
        ventanaRegistro = new Stage();
        Image icon= new Image(getClass().getResourceAsStream("logo.png"));
        ventanaRegistro.getIcons().add(icon);

        try{
            root = FXMLLoader.load(getClass().getResource("FXMLRegistro.fxml"));
        }catch(IOException e)
        {
            System.out.println("No se puede encontrar el fichero FXML");
        }
 
        ventanaRegistro.initStyle(StageStyle.TRANSPARENT);
        ventanaRegistro.setTitle("Registrar nuevo usuario");
        Scene escenaRegistro = new Scene(root);
        escenaRegistro.setFill( Color.TRANSPARENT );
        ventanaRegistro.setScene(escenaRegistro);
        AgendaVital.ventanaLogin.close();
        ventanaRegistro.show();
    }
    
    /////////////////////Métodos para mover la pantalla clickando en cualquier lugar/////////////////////
    @FXML
    public void moverPantalla() throws IOException
    {
        anclaje.setOnMousePressed((MouseEvent me) -> {
            initX = me.getScreenX() - AgendaVital.ventanaLogin.getX();
            initY = me.getScreenY() - AgendaVital.ventanaLogin.getY();
        });
    }
    
    @FXML
    public void moverPantalla2() throws IOException
    {
        anclaje.setOnMouseDragged((MouseEvent me) -> {
            AgendaVital.ventanaLogin.setX(me.getScreenX() - initX);
            AgendaVital.ventanaLogin.setY(me.getScreenY() - initY);
        });
    }
    //-----------------------------------------------------------------------------------------------//
}
