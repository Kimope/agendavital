/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.vista;

import agendavital.modelo.data.Usuario;
import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import agendavital.modelo.excepciones.ContrasenaCaracteresRaros;
import agendavital.modelo.excepciones.ContrasenaMalIntroducida;
import agendavital.modelo.excepciones.ContrasenaMalRepetida;
import agendavital.modelo.excepciones.ContrasenaMuyCorta;
import agendavital.modelo.excepciones.ContrasenaSinMayuscula;
import agendavital.modelo.excepciones.ContrasenaSinNumeros;
import agendavital.modelo.excepciones.NickMuyCorto;
import agendavital.modelo.excepciones.NickYaExiste;
import agendavital.modelo.util.UsuarioLogueado;
import agendavital.modelo.util.UtilidadesAdministracion;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     *
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
    Usuario usuario = UsuarioLogueado.getLogueado();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        campo_usuario.setText(usuario.getNick());
        campo_nombre.setText(usuario.getNombre());
        campo_apellido.setText(usuario.getApellido());
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

    public void update() throws IOException, ConexionBDIncorrecta {
        try {
            UtilidadesAdministracion.comprobarDatos(campo_usuario.getText(), campo_nombre.getText(), campo_apellido.getText(), campo_contraseña.getText(), campo_contraseña_nueva.getText(), campo_contraseña_nueva.getText());
            usuario.setNick(campo_usuario.getText());
            usuario.setNombre(campo_nombre.getText());
            usuario.setApellido(campo_apellido.getText());
            usuario.setContrasena(campo_contraseña_nueva.getText());
            usuario.Update();
        } catch (NickMuyCorto ex) {
            error.setText(ex.getMensaje());
        } catch (ContrasenaMalIntroducida ex) {
            error.setText(ex.getMensaje());
        } catch (ContrasenaMalRepetida ex) {
            error.setText(ex.getMensaje());
        } catch (SQLException ex) {

        } catch (NickYaExiste ex) {
            error.setText(ex.getMensaje());
        } catch (ContrasenaMuyCorta ex) {
            error.setText(ex.getMensaje());
        } catch (ContrasenaCaracteresRaros ex) {
            error.setText(ex.getMensaje());
        } catch (ContrasenaSinMayuscula ex) {
            error.setText(ex.getMensaje());
        } catch (ContrasenaSinNumeros ex) {
            error.setText(ex.getMensaje());
        }

    }
}
