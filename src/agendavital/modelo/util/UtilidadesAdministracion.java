/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.util;

import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import agendavital.modelo.excepciones.ContrasenaCaracteresRaros;
import agendavital.modelo.excepciones.ContrasenaMalIntroducida;
import agendavital.modelo.excepciones.ContrasenaMalRepetida;
import agendavital.modelo.excepciones.ContrasenaMuyCorta;
import agendavital.modelo.excepciones.ContrasenaSinMayuscula;
import agendavital.modelo.excepciones.ContrasenaSinNumeros;
import agendavital.modelo.excepciones.NickMuyCorto;
import agendavital.modelo.excepciones.NickYaExiste;
import java.sql.SQLException;

/**
 *
 * @author Ramón
 */
public class UtilidadesAdministracion {

    /**
     *
     * @param _nick
     * @param _nombre
     * @param _apellido
     * @param _contrasenaVieja
     * @param _contrasenaNueva
     * @param _contrasenaNueva2
     * @throws agendavital.modelo.excepciones.NickMuyCorto
     * @throws agendavital.modelo.excepciones.ContrasenaMalIntroducida
     * @throws agendavital.modelo.excepciones.ContrasenaMalRepetida
     * @throws agendavital.modelo.excepciones.ConexionBDIncorrecta
     * @throws java.sql.SQLException
     * @throws agendavital.modelo.excepciones.NickYaExiste
     * @throws agendavital.modelo.excepciones.ContrasenaMuyCorta
     * @throws agendavital.modelo.excepciones.ContrasenaCaracteresRaros
     * @throws agendavital.modelo.excepciones.ContrasenaSinNumeros
     * @throws agendavital.modelo.excepciones.ContrasenaSinMayuscula
     */
    public static void comprobarDatos(String _nick, String _nombre, String _apellido, String _contrasenaVieja, String _contrasenaNueva, String _contrasenaNueva2) throws NickMuyCorto, ContrasenaMalIntroducida, ContrasenaMalRepetida, ConexionBDIncorrecta, SQLException, NickYaExiste, ContrasenaMuyCorta, ContrasenaCaracteresRaros, ContrasenaSinMayuscula, ContrasenaSinNumeros {
        if (!UtilidadesRegistro.nickLongitudValida(_nick)) {
            throw new NickMuyCorto();
        }
        if (UtilidadesRegistro.nickYaExiste(_nick) && !UsuarioLogueado.getLogueado().getNick().equals(_nick)) {
            throw new NickYaExiste();
        } else {
        }
        if (!UsuarioLogueado.getLogueado().getContrasena().equals(_contrasenaVieja)) {
            throw new ContrasenaMalIntroducida();
        }
        if (!UtilidadesRegistro.IntroduccionContrasena(_contrasenaNueva, _contrasenaNueva2)) {
            throw new ContrasenaMalRepetida();
        }
        if (!UtilidadesRegistro.contrasenaLongitudValida(_contrasenaNueva)) {
            throw new ContrasenaMuyCorta();
        }
        if (UtilidadesRegistro.contrasenaHayCaracteresRaros(_contrasenaNueva)) {
            throw new ContrasenaCaracteresRaros();
        }
        if (!UtilidadesRegistro.contrasenaHayMayuscula(_contrasenaNueva)) {
            throw new ContrasenaSinMayuscula();
        }
        if (!UtilidadesRegistro.contrasenaHayNumeros(_contrasenaNueva)) {
            throw new ContrasenaSinNumeros();
        }
    }
}
