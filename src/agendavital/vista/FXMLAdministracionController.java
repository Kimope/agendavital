/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import agendavital.modelo.data.Usuario;
import agendavital.modelo.util.UsuarioLogueado;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Enrique
 */
public class FXMLAdministracionController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @FXML
    private TextField campo_usuario;
    @FXML
    private TextField campo_nombre;
    @FXML
    private TextField campo_apellido;
    @FXML
    private TextField campo_contraseña;
    @FXML
    private TextField campo_contraseña_nueva;
    @FXML
    private TextField campo_repetir_contraseña_nueva;
    @FXML
    private ImageView edit_usuario;
    @FXML
    private ImageView edit_nombre;
    @FXML
    private ImageView edit_apellido;
    @FXML
    private ImageView edit_contraseña;
    @FXML
    private Pane ayuda;
    @FXML
    private Label error;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Usuario usuario = UsuarioLogueado.getLogueado();
        campo_usuario.setText(usuario.getNick());
        campo_nombre.setText(usuario.getNombre());
        campo_apellido.setText(usuario.getApellido());
        campo_contraseña.setText(usuario.getContrasena());
    }   
    public void edita_usuario() throws IOException {
        edit_usuario.setVisible(false);
        campo_usuario.setDisable(false);
        campo_contraseña.setDisable(false);
    }
    public void edita_nombre() throws IOException {
        edit_nombre.setVisible(false);
        campo_nombre.setDisable(false);
        campo_contraseña.setDisable(false);
    }  
    public void edita_apellido() throws IOException {
        edit_apellido.setVisible(false);
        campo_apellido.setDisable(false);
        campo_contraseña.setDisable(false);
    }  
    public void edita_contraseña() throws IOException {
        edit_contraseña.setVisible(false);
        campo_contraseña.setDisable(false);
        campo_contraseña_nueva.setDisable(false);
        campo_repetir_contraseña_nueva.setDisable(false);
    }
    public void abrir_nota() throws IOException {
        ayuda.setVisible(true);
    }  
    public void cerrar_nota() throws IOException {
        ayuda.setVisible(false);
    }
    public void update() throws IOException {
        Usuario usuario = UsuarioLogueado.getLogueado();
        if(campo_contraseña.getText().equals(usuario.getContrasena()))    //si la contraseña actual es bien introducida
        {
            usuario.setNick(campo_usuario.getText());
            usuario.setNombre(campo_nombre.getText());
            usuario.setApellido(campo_apellido.getText());
            if(!edit_contraseña.isDisable())  //si no se quiere modificar la contraseña
            {
                usuario.setContrasena(campo_contraseña.getText());
            }
            else
            {
                if(campo_contraseña_nueva.getText().equals(campo_repetir_contraseña_nueva.getText())) //si los campos contraseña nueva coinciden
                {
                    usuario.setContrasena(campo_contraseña_nueva.getText());
                }
                else
                {
                    error.setText("Las contraseñas no coinciden");
                }
            }
        }
        else
        {
            campo_contraseña.setText("CONTRASEÑA INCORRECTA");
        }
    }
}
