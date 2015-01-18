/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendavital.modelo.data;

import agendavital.modelo.excepciones.ConexionBDIncorrecta;
import agendavital.modelo.util.UtilidadesRegistro;
import agendavital.modelo.excepciones.ContrasenaMuyCorta;
import agendavital.modelo.excepciones.ContrasenaSinNumeros;
import agendavital.modelo.excepciones.ContrasenaSinMayuscula;
import agendavital.modelo.excepciones.ContrasenaMalRepetida;
import agendavital.modelo.excepciones.NickMalIntroducido;
import agendavital.modelo.util.ConfigBD;
import agendavital.modelo.util.UtilidadesLogin;
import agendavital.modelo.excepciones.ContrasenaMalIntroducida;
import agendavital.modelo.excepciones.NickYaExiste;
import agendavital.modelo.excepciones.ContrasenaCaracteresRaros;
import agendavital.modelo.excepciones.FechaInvalida;
import agendavital.modelo.excepciones.NickMuyCorto;
import agendavital.modelo.util.UsuarioLogueado;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author Ramón
 */
public class Usuario {


    private String nick;
    private String nickViejo;
    private String nombre;
    private String apellido;
    private String contrasena;
    private String fecha_nac;

    public Usuario(String _nick) throws SQLException, ConexionBDIncorrecta {
        nick = _nick;
        nickViejo = _nick;
        Connection conexion = null;
        ResultSet rs = null;
        try {
            conexion = ConfigBD.conectar();
            rs = conexion.createStatement().executeQuery(String.format("SELECT * FROM usuarios WHERE nick = %s", ConfigBD.String2Sql(nick, false)));
            rs.next();
            this.nombre = rs.getString("nombre");
            this.apellido = rs.getString("apellido");
            this.contrasena = null;
            this.fecha_nac = rs.getString("fecha_nac");
        } catch (SQLException ee) {
            throw new ConexionBDIncorrecta();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }

    /**
     * @return the nick
     */
    public String getNick() {
        return nick;
    }

    /**
     * @param nick the nick to set
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @return the fecha_nac
     */
    public String getFecha_nac() {
        return fecha_nac;
    }

    /**
     * @param fecha_nac the fecha_nac to set
     */
    public void setFecha_nac(String fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public static boolean usuarioExiste(String _nick, String _contrasena) throws SQLException, NickMalIntroducido, ContrasenaMalIntroducida, ConexionBDIncorrecta {

        if (!UtilidadesLogin.nickBienIntroducido(_nick)) {
            throw new NickMalIntroducido();
        }
        if (!UtilidadesLogin.contrasenaBienIntroducida(_nick, _contrasena)) {
            throw new ContrasenaMalIntroducida();
        }
        UsuarioLogueado.setLogueado(new Usuario(_nick));
        UsuarioLogueado.getLogueado().setContrasena(_contrasena);
        return true;
    }

    public void Update() throws ConexionBDIncorrecta  {
        try (Connection conexion = ConfigBD.conectar()) {
            String update = String.format("UPDATE usuarios SET nick = %s, nombre = %s, apellido = %s, contrasena = %s WHERE nick = %s;", ConfigBD.String2Sql(nick, false), ConfigBD.String2Sql(nombre, false), ConfigBD.String2Sql(apellido, false), ConfigBD.String2Sql(UtilidadesRegistro.getStringMessageDigest(getContrasena()), false), ConfigBD.String2Sql(nickViejo, false));
            System.out.println(update);
            conexion.createStatement().executeUpdate(update);
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }

    }

    public static Usuario Insert(String _nick, String _nombre, String _apellido, String _contrasena, String _contrasena2, String _fecha_nac) throws ContrasenaMalRepetida, ContrasenaMuyCorta, ContrasenaCaracteresRaros, ContrasenaSinMayuscula, ContrasenaSinNumeros, NickYaExiste, NickMuyCorto, FechaInvalida, ConexionBDIncorrecta, SQLException {
        Usuario nuevo = null;
        if (!UtilidadesRegistro.nickLongitudValida(_nick)){
            throw new NickMuyCorto();
        }
        if (UtilidadesRegistro.nickYaExiste(_nick)) {
            throw new NickYaExiste();
        }
        if (!UtilidadesRegistro.FechaValida(_fecha_nac)){
            throw new FechaInvalida();
        }
        if (!UtilidadesRegistro.contrasenaLongitudValida(_contrasena)) {
            throw new ContrasenaMuyCorta();
        }
        if (UtilidadesRegistro.contrasenaHayCaracteresRaros(_contrasena)) {
            throw new ContrasenaCaracteresRaros();
        }
        if (!UtilidadesRegistro.contrasenaHayMayuscula(_contrasena)) {
            throw new ContrasenaSinMayuscula();
        }
        if (!UtilidadesRegistro.contrasenaHayNumeros(_contrasena)) {
            throw new ContrasenaSinNumeros();
        }
        if (!UtilidadesRegistro.IntroduccionContrasena(_contrasena, _contrasena2)) {
            throw new ContrasenaMalRepetida();
        }
        try (Connection conexion = ConfigBD.conectar()) {
            String insert = String.format("INSERT INTO usuarios (nick, nombre, apellido, contrasena, fecha_nac) VALUES (%s, %s, %s, %s, %s)", ConfigBD.String2Sql(_nick, false), ConfigBD.String2Sql(_nombre, false), ConfigBD.String2Sql(_apellido, false), ConfigBD.String2Sql(UtilidadesRegistro.getStringMessageDigest(_contrasena), false),ConfigBD.String2Sql(_fecha_nac, false));
            conexion.createStatement().executeUpdate(insert);
           nuevo = new Usuario(_nick);
        } catch (SQLException e) {
            throw new ConexionBDIncorrecta();
        }
        UsuarioLogueado.setLogueado(new Usuario(_nick));
        UsuarioLogueado.getLogueado().setContrasena(_contrasena);
        return nuevo;
    }
}
