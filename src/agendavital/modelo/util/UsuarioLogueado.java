/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.util;

import agendavital.modelo.data.Usuario;
import java.io.File;

/**
 *
 * @author ramon
 */
public class UsuarioLogueado {
    public static Usuario logueado;

    public static Usuario getLogueado() {
        return logueado;
    }

    public static void setLogueado(Usuario logueado) {
        UsuarioLogueado.logueado = logueado;
        File destino = new File("Momentos");
        if (!destino.exists()) {
            destino.mkdir();
        }
        File destino2 = new File("Momentos/" + UsuarioLogueado.getLogueado().getNick());
        if (!destino2.exists()) {
            destino2.mkdir();
        }

    }
    public UsuarioLogueado(Usuario _usuario){
        logueado = _usuario;
    }

    public UsuarioLogueado() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
