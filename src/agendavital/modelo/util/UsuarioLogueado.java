/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.util;

import agendavital.modelo.data.Usuario;

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

    }
    public UsuarioLogueado(Usuario _usuario){
        logueado = _usuario;
    }
    
}
