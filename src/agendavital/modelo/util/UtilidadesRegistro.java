/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.util;

import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ramón
 */
public class UtilidadesRegistro {

    /**
     * *
     * Convierte un arreglo de bytes a String usando valores hexadecimales
     *
     * @param digest arreglo de bytes a convertir
     * @return String creado a partir de <code>digest</code>
     */
    private static String toHexadecimal(byte[] digest) {
        String hash = "";
        for (byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) {
                hash += "0";
            }
            hash += Integer.toHexString(b);
        }
        return hash;
    }

    /**
     * *
     * Encripta un mensaje de texto mediante algoritmo de resumen de mensaje
     * usando MD5.
     *
     * @param contrasena contrasena a encriptar
     * @return mensaje encriptado
     */
    public static String getStringMessageDigest(String contrasena) {
        byte[] digest = null;
        byte[] buffer = contrasena.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error creando Digest");
        }
        return toHexadecimal(digest);
    }

    public static boolean nickYaExiste(String _nick) throws ConexionBDIncorrecta, SQLException {
        Connection conexion = null;
        ResultSet rs = null;
        try {
            conexion = ConfigBD.conectar();
            String consultaNick = String.format("SELECT (nick) from usuarios WHERE nick = %s;", ConfigBD.String2Sql(_nick, false));
            rs = conexion.createStatement().executeQuery(consultaNick);
            rs.next();
            return (rs.getRow() == 1);
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        } finally {
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    public static boolean nickLongitudValida(String _nick) {
        return _nick.length() >= 5;
    }

    public static int anyoBisiesto(int _anyo) {
        return (_anyo % 4 == 0 && (_anyo % 400 == 0 || _anyo % 100 != 0)) ? 1 : 0;
    }

    public static boolean contrasenaLongitudValida(String _contrasena) {
        return _contrasena.length() >= 7;
    }

    public static boolean contrasenaHayMayuscula(String _contrasena) {
        for (int i = 0; i < _contrasena.length(); i++) {
            if (Character.isUpperCase(_contrasena.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean contrasenaHayNumeros(String _contrasena) {
        for (int i = 0; i < _contrasena.length(); i++) {
            if (Character.isDigit(_contrasena.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean contrasenaHayCaracteresRaros(String _contrasena) {
        for (int i = 0; i < _contrasena.length(); i++) {
            int ascii = (int) (_contrasena.charAt(i));
            if (ascii > 126 || ascii < 32) {
                return true;
            }
        }
        return false;
    }

    public static boolean IntroduccionContrasena(String _contrasena, String _contrasena2) {
        return _contrasena.equals(_contrasena2);
    }

    public static boolean FechaValida(String _fecha) {
        int _anyo = Integer.parseInt(_fecha.substring(6, 10));
        int[] diasmes = {31, 28 + anyoBisiesto(_anyo), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int _mes = Integer.parseInt(_fecha.substring(3, 5));
        int _dia = Integer.parseInt(_fecha.substring(0, 2));
        return _dia <= diasmes[_mes + 1];
    }

    public static String acontecimientoMasRelevante(int _anyo) {
        switch (_anyo) {
            case 1964:
                return "el Che Guevara diserto su discuso mas importante?";
            case 1965:
                return "fallece, en Londres, el escritor Thomas S. Eliot?";
            case 1966:
                return "el Real Madrid ganó su sexta Copa de Europa?";
            case 1967:
                return "comenzo la guerra de los seis dias?";
            case 1968:
                return "Guinea Ecuatorial se independizo de España?";
            case 1969:
                return "Neil Armstrong piso la Luna?";
            case 1970:
                return "dijeron, en el Apolo 13, 'Houston, tenemos un problema'?";
            case 1971:
                return "se independizo Bangladesh?";
            case 1972:
                return "acercaron posturas para acabar con la Guerra Fria?";
            case 1973:
                return "Reino Unido permitio operar en bolsa a las mujeres?";
            case 1974:
                return "dimitió Richard Nixon, presidente de EEUU?";
            case 1975:
                return "murió Francisco Franco?";
            case 1976:
                return "el Rey nombro a Adolfo Suarez nuevo presidente del gobierno?";
            case 1977:
                return "se legalizó el Partido Comunista?";
            case 1978:
                return "se proclamó la constitucion Española?";
            case 1979:
                return "se celebraron, 4 decadas despues, elecciones municipales?";
            case 1980:
                return "España y el Reino Unido reabrieron la frontera entre Gibraltar y España?";
            case 1981:
                return "falló el intento de golpe de Estado en España(23-F)?";
            case 1982:
                return "se implantó con exito el primer corazón artificial en EEUU?";
            case 1983:
                return "murió el pintor español Joan Miró?";
            case 1984:
                return "nacio Mark Zuckerberg ,creador de Facebook?";
            case 1985:
                return "se encontraron los restos del Titanic?";
            case 1986:
                return "ocurrió la catástrofe nuclear de Chernóbil?";
            case 1987:
                return "nació el futbolista Argentino Lionel Messi?";
            case 1988:
                return "nació la cantante Estadounidense Rihanna?";
            case 1989:
                return "cayó el muro de Berlin?";   
            case 1990:
                return "Se liberó a Nelson Mandela tras 27 años de cautiverio?";
            case 1991:
                return "falleció Freddie Mercury a causa del SIDA?";
            case 1992:
                return "tuvieron lugar en España las Olimpiadas de Barcelona?";
            case 1993:
                return "nació el piloto español Marc Márquez?";
            case 1994:
                return "La seleccion de Futbol de Brasil ganó su cuarto Mundial?";
            case 1995:
                return "Austria,Finlandia y Suecia ingresaron en la Unión Europea?";
            case 1996:
                return "la banda armada ETA secuestró a José Antonio Ortega Lara?";
            case 1997:
                return "nació Dolly, la primera oveja clonada?";
            case 1998:
                return "se inicio el 'boom' de la telefonía movil?";
            case 1999:
                return "tomo posesion el presidente electo de Venezuela Hugo Chavez?";
            case 2000:
                return "Se extendió la crisis de las vacas locas?";
            case 2001:
                return "La banda terrorista Al-Qaeda atacó a las torres Gemelas en EEUU?";
            case 2002:
                return "el petrolero 'Prestige' provocó un desastre naturan en España?";
            case 2003:
                return "tuvo lugar el brote de gripe aviar en Asia?";
            case 2004:
                return "la banda terrorista Al-Qaeda atentó contra 4 trenes en España?";
            case 2005:
                return "el huracan Katrina asoló la ciudad de Nueva Orleans?";
            case 2006:
                return "se realizó el primer transplante de cara?";
            case 2007:
                return "falleció el futbolista Antonio Puerta durante un partido?";
            case 2008:
                return "se inició la crisis económica y financiera mundial?";
            case 2009:
                return "murió Michael Jackson?";
            case 2010:
                return "la selección española de Futbol logró ganar su primer mundial?";
            case 2011:
                return "EEUU capturó y mató al lider de Al-Qaeda Osama Bin Laden?";
            case 2012:
                return "falleció la cantante estadounidense Whitney Houston?";
            case 2013:
                return "cayó un meteorito en Rusia?";
            case 2014:
                return "un grupo Yihadista atacó una redacción en Paris?";
            default:
                return "";
        }
    }

}
