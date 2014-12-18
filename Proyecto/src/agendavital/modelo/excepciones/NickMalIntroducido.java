/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.excepciones;

/**
 *
 * @author Ramón
 */
public class NickMalIntroducido extends Exception {
    private static final String mensaje = "Usuario no encontrado (posiblemente no introdujo su nombre de usuario correctamente)";

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }
}
