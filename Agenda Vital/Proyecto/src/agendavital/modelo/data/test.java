/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.data;

import agendavital.modelo.excepciones.ContrasenaCaracteresRaros;
import agendavital.modelo.excepciones.ContrasenaMalIntroducida;
import agendavital.modelo.excepciones.ContrasenaMalRepetida;
import agendavital.modelo.excepciones.ContrasenaMuyCorta;
import agendavital.modelo.excepciones.ContrasenaSinMayuscula;
import agendavital.modelo.excepciones.ContrasenaSinNumeros;
import agendavital.modelo.excepciones.NickMalIntroducido;
import agendavital.modelo.excepciones.NickYaExiste;
import java.sql.SQLException;

/**
 *
 * @author Ramón
 */
public class test {
/*
    public static Usuario insertar(String nick, String contrasena, String contrasena2, String nombre, String apellido, String fecha_nac) {
        Usuario ret = null;
        try {
            ret = Usuario.Insert(nick, nombre, apellido, contrasena, contrasena2, fecha_nac);
        } catch (SQLException ex) {
          System.err.println("Fallo al acceder a la BD");
        } catch (NickYaExiste ex) {
            System.err.println(ex.getMensaje());
        } catch (ContrasenaMalRepetida ex) {
            System.err.println(ex.getMensaje());
        } catch (ContrasenaMuyCorta ex) {
            System.err.println(ex.getMensaje());
        } catch (ContrasenaCaracteresRaros ex) {
            System.err.println(ex.getMensaje());
        } catch (ContrasenaSinMayuscula ex) {
            System.err.println(ex.getMensaje());
        } catch (ContrasenaSinNumeros ex) {
            System.err.println(ex.getMensaje());
        }
        return ret;

    }

    public static void consultaUsuario(String nick, String contrasena){
        try {
            boolean kk = Usuario.usuarioExiste(nick, contrasena);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NickMalIntroducido ex) {
            System.err.println(ex.getMensaje());
        } catch (ContrasenaMalIntroducida ex) {
            System.err.println(ex.getMensaje());
        }
    }
    
    public static void main(String[] args) {
        String nick = "rrgonzalez92";
        String contrasena = "Luisfernndo1";
        String contrasena2 = "Ramonkkkk";
        System.out.println(contrasena);
        String nombre = "Ramon";
        String apellido = "Ramirez";
        String fecha = "28091992";
        consultaUsuario(nick, contrasena);
    }
    */
}
