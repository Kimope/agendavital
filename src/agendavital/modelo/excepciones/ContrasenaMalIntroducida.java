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
public class ContrasenaMalIntroducida extends Exception{
    private static final String mensaje = "Usuario no encontrado (posiblemente no introdujo su contraseña correctamente)";

    public String getMensaje() {
        return mensaje;
    }
     
}
