/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import agendavital.modelo.data.Momento;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import static agendavital.vista.FXMLPrincipalController.ventanaAnadirMomento;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLMomentoController implements Initializable {
    
        //////////////Variables de la ventana de registro//////////////
        public static final double ANCHO = 783;
	public static final double ALTO= 609;
        private double initX=ANCHO/2;
        private double initY=ALTO/2;    
        //------------------------------------------------------------//
    @FXML 
    private AnchorPane anclaje;
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
    private TextFlow tfTitular;
    @FXML
    private Text txtTitular;
    @FXML
    private TextFlow tfCuerpo;
    @FXML
    private TextArea txtCuerpo;
    @FXML
    private TextFlow tfLocalizacion;
    @FXML
    private Text txtLocalizacion;
    @FXML
    private ImageView ivImagen;
    @FXML
    private TextFlow tfCategoria;
    @FXML
    private Text txtCategoria;
    @FXML
    private TextFlow tfTags;
    @FXML
    private Text txtTag;
    @FXML
    private Button btnModificar;
    public static Momento _momento = null;
    public static FXMLPrincipalController controllerPrincipal;
    public static FXMLMomentosyNoticiasController controllerMYN;
    public static Stage ventanaConfirmarBorrar;
    public static Stage ventanaVer;
    public static Image imagen;
    public static Image imagenMostrar = null;
    public BufferedImage imagenreal;
    public int imagenwidth = 0;
    public int imagenheight = 0;
    

    public void setControllerMYN(FXMLMomentosyNoticiasController controllerMYN) {
        this.controllerMYN = controllerMYN;
    }
    

    public void setMomento(Momento _momento) {
        this._momento = _momento;
    }

    public void setControllerPrincipal(FXMLPrincipalController controllerPrincipal) {
        this.controllerPrincipal = controllerPrincipal;
    }

    /**
     * Initializes the controller class.
     * @throws java.io.IOException
     */
    
                        ///////////////////Menu de botones esquina superior derecha///////////////////
    @FXML
    public void minimizar() throws IOException {
        FXMLMomentosyNoticiasController.ventanaMomento.setIconified(true);
    }

    @FXML
    public void minimizarEncima() throws IOException {
        circulomin.setFill(Color.web("#93C6D6"));
    }

    @FXML
    public void minimizarSalida() throws IOException {
        circulomin.setFill(Color.TRANSPARENT);
    }

    @FXML
    public void cerrar() throws IOException
    {
        FXMLMomentosyNoticiasController.ventanaMomento.close();
    }
    
    @FXML
    public void cerrarEncima() throws IOException
    {
       circulocerr.setFill(Color.web("#93C6D6"));
    }
    
    @FXML
    public void cerrarSalida() throws IOException
    {
        circulocerr.setFill(Color.TRANSPARENT);
    }
///////////////////////////////////////////////////////////
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
     public void imprimir(Momento momento) throws SQLException, IOException{
         _momento = momento;
        txtTitular.setText(momento.getTitulo());
        txtCategoria.setText(momento.getFecha());
        txtCuerpo.setText(momento.getDescripcion());
        if (momento.getId_documento()!=0) {
            File fileImagen = new File(momento.getRutaDocumento());
            InputStream is = null;
            try {
                is = new FileInputStream(fileImagen);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLRegistroPreguntaUnoController.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            
            double width = 191;
            double heigth = 167;
            imagen = new Image(is, width, heigth, false, false);
            ivImagen.setImage(imagen);
            final BufferedImage bi = ImageIO.read(fileImagen);
            imagenwidth = bi.getWidth();
         imagenheight =  bi.getHeight();
      
                    }
    }
    

        /////////////////////Métodos para mover la pantalla clickando en cualquier lugar/////////////////////
   @FXML
    public void moverPantalla() throws IOException {
        anclaje.setOnMousePressed((MouseEvent me) -> {
            initX = me.getScreenX() - FXMLMomentosyNoticiasController.ventanaMomento.getX();
            initY = me.getScreenY() - FXMLMomentosyNoticiasController.ventanaMomento.getY();
        });
     
    }

    @FXML
    public void moverPantalla2() throws IOException {
        anclaje.setOnMouseDragged((MouseEvent me) -> {
            FXMLMomentosyNoticiasController.ventanaMomento.setX(me.getScreenX() - initX);
            FXMLMomentosyNoticiasController.ventanaMomento.setY(me.getScreenY() - initY);
        });
    }
    //-----------------------------------------------------------------------------------------------//
    public void modificar_momento(){
        if(ventanaAnadirMomento.isShowing()){
            ventanaAnadirMomento.setIconified(false);
            ventanaAnadirMomento.toFront();
            }else{
        Parent root = null;
        ventanaAnadirMomento = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("imagenes_interfaz/logo.png"));
        ventanaAnadirMomento.getIcons().add(icon);
        ventanaAnadirMomento.setTitle("Modificar Momento");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAnadirMomento.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene escenaNoticia = new Scene(root);
        FXMLAnadirMomentoController controller = loader.getController();
        controller.setModificarMomento(_momento);
        controller.setControllerMYN(controllerMYN);
        controller.setControllerMomento(this);        
        controller.setControllerPrincipal(controllerPrincipal);
        try{
            controller.inicializarVentana();
        } catch(SQLException e){
        }
        ventanaAnadirMomento.setScene(escenaNoticia);
        ventanaAnadirMomento.initStyle(StageStyle.UNDECORATED);
        ventanaAnadirMomento.show();
    }
    }
    public void borrar_momento() throws ConexionBDIncorrecta{
       
       Parent root = null; //Creamos el parent
            ventanaConfirmarBorrar = new Stage(); //Creamos la ventana que tendrá la vista Principal de la aplicación
            Image icon= new Image(getClass().getResourceAsStream("imagenes_interfaz/logo.png"));
            ventanaConfirmarBorrar.getIcons().add(icon);
            
            try{
                root = FXMLLoader.load(getClass().getResource("FXMLConfirmarBorrarMomento.fxml"));
            }catch(IOException e)
            {
                System.out.println("No se puede encontrar el fichero FXML");
            }
            
            ventanaConfirmarBorrar.setResizable(false); //No se puede modificar el tamaño de la ventana
            ventanaConfirmarBorrar.setTitle("Borrar"); //Ponemos un título para el panel de Windows
            ventanaConfirmarBorrar.initStyle(StageStyle.TRANSPARENT);
            Scene escenaConfirmarBorrar = new Scene(root); //Creamos la escena
            escenaConfirmarBorrar.setFill( Color.TRANSPARENT );
            ventanaConfirmarBorrar.setScene(escenaConfirmarBorrar); //Cargamos la escena
            ventanaConfirmarBorrar.show();
    }
    
    public static void borrar() throws ConexionBDIncorrecta
    {
        _momento.Delete();
        _momento = null;
        if(controllerMYN.isMostrandoTodo()) controllerMYN.mostrarTodo();
       else controllerMYN.cambiarDatos();
       controllerPrincipal.colorearFechas();
       controllerPrincipal.mostrarImagenes();
    }
    
    public void verimagen() throws SQLException
    {
            Parent root = null; //Creamos el parent
            ventanaVer = new Stage(); //Creamos la ventana que tendrá la vista Principal de la aplicación           
            ventanaVer.setResizable(false); //No se puede modificar el tamaño de la ventana
            ventanaVer.initStyle(StageStyle.TRANSPARENT);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLVerImagen.fxml"));
            try{
                root = loader.load();
            }catch(IOException e)
            {
                System.out.println("No se puede encontrar el fichero FXML");
            }          
            FXMLVerImagenController controller =  loader.getController();
            Scene escenaVer = new Scene(root,imagenwidth+10,imagenheight+10); //Creamos la escena
           
            escenaVer.setFill( Color.TRANSPARENT );
            ventanaVer.setScene(escenaVer); //Cargamos la escena
             controller.imprimir_imagen(_momento.getRutaDocumento());
            ventanaVer.show();
    }
}
