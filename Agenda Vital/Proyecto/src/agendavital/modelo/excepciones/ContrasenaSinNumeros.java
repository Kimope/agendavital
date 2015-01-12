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
public class ContrasenaSinNumeros extends Exception{
     private static final String mensaje = "La contraseña no contiene ningún carácter numérico";

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }
     
}
