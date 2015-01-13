/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.excepciones;

/**
 *
 * @author ramon
 */
public class ConexionBDIncorrecta extends Exception{

    private static final String mensaje = "La contraseña contiene caracteres no permitidos";
   
    /*
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }
}
