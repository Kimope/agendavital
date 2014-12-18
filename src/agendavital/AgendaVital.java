//////////////////////////////////////////////////////////////////////////////////////////////////////
//  Este archivo es la base de la aplicación y llamará a Login. Después de esto, cada archivo tiene //
//  dos partes: el FXML y su controlador. El FXMLNombre será la vista y el FXMLNombreController el  //
//  controlador de dicha vista. En algunos casos también tendrá su CSS que será Nombre.css.         //
//  Si hay algún tipo de error, pulsar CONTROL+SHIFT+I para importar paquetes necesarios, o para    //
//  quitar aquellos que no están siendo usados                                                      //
//////////////////////////////////////////////////////////////////////////////////////////////////////

package agendavital;

import javafx.scene.image.Image;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Enrique
 */
public class AgendaVital extends Application 
{
    public static Stage ventanaLogin; //Declaramos el stage como static para poderlo usar en el Controller
    private double xOffset=0; //Esto sirve para mover las pantallas
    private double yOffset=0; //Esto sirve para mover las pantallas
    
    //Método para iniciar el programa
    @Override
    public void start(Stage stage) throws Exception 
    {
        ventanaLogin = stage; //Igualamos el stage del start() a nuestra ventana static
        inicializar(ventanaLogin); //La inicializamos
        ventanaLogin.show(); //La mostramos por pantalla
    }
    
    //Método para inicializar el programa
    public void inicializar(Stage stage)
    {
        Parent root = null; //Creamos un parent, que es una clase que se encarga del escenario gráfico, que tendrá sus hijos, que serán las escenas
        
        try{
            root = FXMLLoader.load(getClass().getResource("vista/FXMLLogin.fxml")); //Cargamos el fichero FXML relacionado con este archivo, que es el que tiene los paneles, textos, etc, es decir, lo que se muestra por pantalla
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        
        
        stage.initStyle( StageStyle.TRANSPARENT ); //No se muestra el menú de Windows
        stage.setAlwaysOnTop(true);  //Siempre va a estar visible, no se puede minimizar
        Scene scene = new Scene(root); //Creamos una escena en el root creado, que es una clase contenedora del escenario gráfico, en este caso con la ventana que se muestra por pantalla
        scene.setFill( Color.TRANSPARENT ); //Para que no se vea la escena (lo usamos para que no se vean los picos de los bordes
        scene.getStylesheets().add("prueba.css");
        stage.setScene(scene); //Cargamos la escena
        Image icon = new Image(getClass().getResourceAsStream("vista/logo.png")); //Creamos y cargamos un icono de la carpeta
        stage.getIcons().add(icon); //Cargamos la imagen como icono de la app
        stage.centerOnScreen();
        
        //ResponsiveHandler.addResponsiveToWindow(stage);
        
        //scene.getStylesheets().add(getClass().getResource( "Login.css" ).toString()); //Cargamos el CSS a la escena
        //stage.setFullScreen(true); //Hace que se vea a pantalla completa
        //stage.setResizable(false); //Hace que no se pueda modificar el tamaño de la pantalla
        //stage.setMaximize(true); //Hace que se pueda maximizar o no 
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
