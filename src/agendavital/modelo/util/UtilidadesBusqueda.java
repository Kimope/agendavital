/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.util;

import java.util.ArrayList;

/**
 *
 * @author Ramón
 */
public class UtilidadesBusqueda {
    public static ArrayList<String> separarPalabras(String _parametro){
        ArrayList<String> palabras = new ArrayList<>();
        String palabra = "";
       for(int i = 0; i < _parametro.length(); i++){
           if(Character.isWhitespace(_parametro.charAt(i))){
               if(!palabra.isEmpty()) palabras.add(palabra);
               palabra = "";
           }
           else palabra +=_parametro.charAt(i);
       }
       if(palabras.isEmpty()) palabras.add(_parametro);
       return palabras;
    }
}
