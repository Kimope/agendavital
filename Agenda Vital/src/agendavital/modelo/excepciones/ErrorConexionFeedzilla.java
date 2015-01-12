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
public class ErrorConexionFeedzilla extends Exception {
     private static final String mensaje = "Imposible conectarse a FeedZilla";
     
       public String getMensaje() {
        return mensaje;
    }
}
