/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloDistrito;

import BD.conexionBD;
import ControladorDistrito.Render;
import ControladorDistrito.accesosActivos;
import ControladorDistrito.asignacion;
import ControladorDistrito.sitios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import ControladorDistrito.sitios;
import distritocp.sat.controlAcceso;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DistritoCP
 *
 * Esta clase gestionar todos los querys que existen en el sistema
 */
public class ConsultasDistrito {

    conexionBD con = new conexionBD();
    private Connection conexion = null;
    private Statement st = null;
    private ResultSet rs = null;

    // Connection c = con.conectar();
    public Integer idUsuario;

    public void desconectar() {
        con.desconectar();
    }

    /* Inicio  Consultas  */
    public ResultSet Huellas() {
        try {

            Connection c = con.conectar();
            String sql = ("SELECT  id,nombre,huellas,tipo FROM usuarios");
            PreparedStatement identificarStmt = c.prepareStatement(sql);
            ResultSet rs = identificarStmt.executeQuery();
            return rs;

        } catch (SQLException ex) {
            System.err.println("Error al verificar los datos de la huella.");
        } finally {
            try {
                rs.close();
                con.desconectar();

            } catch (Exception e) {
                System.err.println("Error al cerrar conexion.");
            }
        }
        return null;

    }

    public ResultSet obtenerAcceso(String url, String nombreUsuario, String contrasena) {
        try {
            Connection c = con.conectar();
            String sql = "SELECT accesos.usuario,accesos.contrasena,sitios.url FROM accesos INNER JOIN asignaciones ON "
                    + "accesos.id = asignaciones.accesos_id INNER JOIN sitios "
                    + "ON sitios.id = asignaciones.sitios_id WHERE sitios.url = '" + url + "' "
                    + "AND accesos.usuario = '" + nombreUsuario + "' AND accesos.contrasena = '" + contrasena + "'";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);
            return rs;

        } catch (SQLException ex) {
            Logger.getLogger(controlAcceso.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            con.desconectar();
            con.destruirConsultas(st, rs);

        }
        return null;

    }

    public Integer obtenerID(Integer asignacionID) {
        try {
            Connection c = con.conectar();
            String sql = "SELECT  id FROM usuarios where id = '" + asignacionID + "'";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);
            //String datos[] = new String[6];
            while (rs.next()) {
                Integer id = rs.getInt("id");

                return id;

            }

        } catch (SQLException ex) {
            Logger.getLogger(controlAcceso.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            con.desconectar();
            con.destruirConsultas(st, rs);

        }
        return null;

    }

    public void Sitios(DefaultComboBoxModel modeloCombo, JComboBox cboAccesos) {
        try {
            Connection c = con.conectar();
            String query = ("SELECT * FROM sitios");
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query);
            modeloCombo.addElement("Seleccione Una Opcion");
            cboAccesos.setModel(modeloCombo);
            while (rs.next()) {
                sitios paginas = new sitios();
                paginas.setID(rs.getInt("id"));
                paginas.seturl(rs.getString("url"));
                modeloCombo.addElement(paginas);
                cboAccesos.setModel(modeloCombo);

            }
        } catch (Exception e) {
            System.out.println("Error al mostrar los datos");
        } finally {

            con.desconectar();
            con.destruirConsultas(st, rs);

        }
    }

    public void ObtenerUsuarios(DefaultComboBoxModel modeloUsuario, JComboBox cboUsuario) {
        try {
            Connection c = con.conectar();
            String query = ("SELECT id,nombre FROM usuarios where tipo = 1 ");
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query);
            modeloUsuario.addElement("Seleccione un Usuario");
            cboUsuario.setModel(modeloUsuario);
            while (rs.next()) {
                asignacion usuarios = new asignacion();
                usuarios.setIdUsuario(rs.getInt("id"));
                usuarios.setNombreUsuario(rs.getString("nombre"));
                modeloUsuario.addElement(usuarios);
                cboUsuario.setModel(modeloUsuario);

            }
        } catch (Exception e) {
            System.out.println("Error al mostrar los datos");
        } finally {

            con.desconectar();
            con.destruirConsultas(st, rs);

        }
    }

    public void obtenerAcceso(DefaultComboBoxModel modeloAcceso, JComboBox cboAccesos) {
        try {
            Connection c = con.conectar();
            String query = ("SELECT * FROM accesos");
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query);
            modeloAcceso.addElement("Seleccine Un Acceso");
            cboAccesos.setModel(modeloAcceso);
            while (rs.next()) {
                accesosActivos accesos = new accesosActivos();
                accesos.setIDAcceso(rs.getInt("id"));
                accesos.setNombreAcceso(rs.getString("nombre_acceso"));
                modeloAcceso.addElement(accesos);
                cboAccesos.setModel(modeloAcceso);

            }
        } catch (Exception e) {

            System.out.println("Error al mostrar los datos");
        } finally {

            con.desconectar();
            con.destruirConsultas(st, rs);

        }

    }

    public Integer setAccesos() {
        try {
            Connection c = con.conectar();
            String consul = "SELECT id FROM accesos ORDER BY id DESC LIMIT 1";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(consul);
            while (rs.next()) {
                Integer IDAcceso = rs.getInt("id");
                //  DistritoCP   id = new DistritoCP();
                //  id.setIdAcceso(IDAcceso);

                return IDAcceso;
            }
        } catch (SQLException e) {
            System.out.println("Error de base de datos");
        } finally {

            con.desconectar();
            con.destruirConsultas(st, rs);

        }
        return null;
    }

    public Integer setIDAsignacion(Integer idAcceso) {
        String sql = "SELECT asignaciones.id FROM accesos INNER JOIN asignaciones "
                + "ON accesos.id = asignaciones.accesos_id where accesos.id = '" + idAcceso + "'";
        try {
            Connection c = con.conectar();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Integer IDAsignacion = rs.getInt("id");

                return IDAsignacion;
            }
        } catch (SQLException e) {
            System.out.println("Error de base de datos");
        } finally {
            con.desconectar();
            con.destruirConsultas(st, rs);

        }
        return null;
    }

    public void mostrarDatos(String valor, DefaultTableModel modelo, JTable tbDatos) {
        String sql = "";
        if (valor.equals("")) {
            sql = "SELECT  asignaciones.id,accesos.nombre_acceso,sitios.nombre_sitio,accesos.usuario FROM accesos INNER JOIN asignaciones ON accesos.id = asignaciones.accesos_id INNER JOIN sitios ON sitios.id = asignaciones.sitios_id  where estatus = 0";
        } else {
            sql = "SELECT nombre_acceso FROM accesos WHERE nombre_acceso='" + valor + "'";
        }
        tbDatos.setDefaultRenderer(Object.class, new Render());

        JButton btnEliminar = new JButton("Eliminar");

        btnEliminar.setName("eliminar");

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setName("entrar");
        JButton btnModificar = new JButton("Modificar");
        btnModificar.setName("modificar");

        JButton btnVerContrasena = new JButton("Ver");
        btnVerContrasena.setName("verContrasena");

        Object[] datos = new Object[9];
        try {
            Connection c = con.conectar();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {

                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = ("*********");
                datos[5] = btnModificar;
                datos[6] = btnEliminar;
                datos[7] = btnEntrar;
                datos[8] = btnVerContrasena;
                modelo.addRow(datos);
            }
            tbDatos.setModel(modelo);

        } catch (SQLException ex) {
            Logger.getLogger(controlAcceso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            con.desconectar();
            con.destruirConsultas(st, rs);

        }

    }

    public void mostrasDatosCliente(String valor, DefaultTableModel modeloCliente, JTable tbCliente, Integer idUsuario) {
        String sql = "";
        if (valor.equals("")) {
            sql = "SELECT asignaciones.id,accesos.nombre_acceso,sitios.nombre_sitio,accesos.usuario FROM accesos INNER JOIN asignaciones ON accesos.id = asignaciones.accesos_id INNER JOIN sitios "
                    + "ON sitios.id = asignaciones.sitios_id "
                    + "where asignaciones.usuarios_id = '" + idUsuario + "'";

        } else {
            sql = "";
        }

        tbCliente.setDefaultRenderer(Object.class, new Render());
        JButton btnAccesoCliente = new JButton("Entrar Acceso");
        btnAccesoCliente.setName("entrarCliente");
        Object[] datos = new Object[7];
        try {
            Connection c = con.conectar();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = btnAccesoCliente;
                modeloCliente.addRow(datos);
            }
            tbCliente.setModel(modeloCliente);

        } catch (SQLException ex) {
            Logger.getLogger(controlAcceso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            con.desconectar();
            con.destruirConsultas(st, rs);

        }
    }

    public void mostrarAsingaciones(String valor, DefaultTableModel modelo, JTable tbAsignaciones) {
        String sql = "";
        if (valor.equals("")) {
            sql = "SELECT asignaciones.id,usuarios.nombre,accesos.nombre_acceso,sitios.nombre_sitio,accesos.usuario "
                    + "FROM accesos INNER JOIN asignaciones ON accesos.id = asignaciones.accesos_id "
                    + "INNER JOIN sitios ON sitios.id = asignaciones.sitios_id "
                    + "INNER JOIN usuarios ON usuarios.id = asignaciones.usuarios_id where usuarios.tipo = 1";

        } else {
            sql = "";
        }
        tbAsignaciones.setDefaultRenderer(Object.class, new Render());
        JButton btnEliminarAsignacion = new JButton("Eliminar Asignacion");
        btnEliminarAsignacion.setName("eliminarAsignacion");

        Object[] datos = new Object[7];
        try {
            Connection c = con.conectar();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = btnEliminarAsignacion;
                modelo.addRow(datos);
            }
            tbAsignaciones.setModel(modelo);

        } catch (SQLException ex) {
            Logger.getLogger(controlAcceso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            con.desconectar();
            con.destruirConsultas(st, rs);

        }
    }

    public String obtenerContrasena(Integer asignacionID) {
        try {
            Connection c = con.conectar();
            String sql = "SELECT   accesos.contrasena,sitios.url FROM accesos "
                    + "INNER JOIN asignaciones "
                    + "ON accesos.id = asignaciones.accesos_id INNER JOIN sitios ON sitios.id = asignaciones.sitios_id where asignaciones.id ='" + asignacionID + "'";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);
            //String datos[] = new String[6];
            while (rs.next()) {
                String contrasena = rs.getString("contrasena");

                return contrasena;

            }

        } catch (SQLException ex) {
            Logger.getLogger(controlAcceso.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            con.desconectar();
            con.destruirConsultas(st, rs);

        }
        return null;

    }

    public String obtenerURL(Integer asignacionID) {
        try {
            Connection c = con.conectar();
            String sql = "SELECT  sitios.url FROM accesos "
                    + "INNER JOIN asignaciones "
                    + "ON accesos.id = asignaciones.accesos_id INNER JOIN sitios ON sitios.id = asignaciones.sitios_id where asignaciones.id ='" + asignacionID + "'";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);
            //String datos[] = new String[6];
            while (rs.next()) {
                String URL = rs.getString("url");

                return URL;

            }

        } catch (SQLException ex) {
            Logger.getLogger(controlAcceso.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            con.desconectar();
            con.destruirConsultas(st, rs);

        }
        return null;

    }

    public String obtenerCuenta(Integer asignacionID) {
        try {
            Connection c = con.conectar();
            String sql = "SELECT  accesos.acceso FROM accesos "
                    + "INNER JOIN asignaciones "
                    + "ON accesos.id = asignaciones.accesos_id INNER JOIN sitios ON sitios.id = asignaciones.sitios_id where asignaciones.id ='" + asignacionID + "'";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);
            //String datos[] = new String[6];
            while (rs.next()) {
                String acceso = rs.getString("acceso");

                return acceso;

            }

        } catch (SQLException ex) {
            Logger.getLogger(controlAcceso.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            con.desconectar();
            con.destruirConsultas(st, rs);

        }
        return null;

    }

    public void llenarCampo(Integer asignacionID, JTextField txtNombre, JTextField txtUsuario, JTextField txtContrasena, JTextField txtAccesoFie, JComboBox cboAccesos) {
        try {
            Connection c = con.conectar();
            String sql = "SELECT asignaciones.id,accesos.nombre_acceso,sitios.url,accesos.usuario,accesos.contrasena,accesos.acceso FROM accesos "
                    + "INNER JOIN asignaciones "
                    + "ON accesos.id = asignaciones.accesos_id INNER JOIN sitios ON sitios.id = asignaciones.sitios_id where asignaciones.id ='" + asignacionID + "'";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);
            //String datos[] = new String[6];
            while (rs.next()) {
                String acceso = rs.getString("nombre_acceso");
                String usuario = rs.getString("usuario");
                String contrasena = rs.getString("contrasena");
                String cuenta = rs.getString("acceso");
                String sitio = rs.getString("url");
                txtNombre.setText(acceso);
                txtUsuario.setText(usuario);
                txtAccesoFie.setText(cuenta);
                txtContrasena.setText(contrasena);
                if (sitio.equals("https://www.fel.mx/cfdi/presentacion/usuario/ingreso.aspx")) {
                    txtAccesoFie.setEnabled(true);
                } else {
                    txtAccesoFie.setEnabled(false);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(controlAcceso.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            con.desconectar();
            con.destruirConsultas(st, rs);

        }
    }

    public Integer obtenerIDAcceso(Integer asignacionID) {
        try {
            Connection c = con.conectar();
            String sql = "SELECT accesos.id FROM accesos "
                    + "INNER JOIN asignaciones "
                    + "ON accesos.id = asignaciones.accesos_id INNER JOIN sitios ON sitios.id = asignaciones.sitios_id where asignaciones.id ='" + asignacionID + "'";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);
            //String datos[] = new String[6];
            while (rs.next()) {
                Integer id = rs.getInt("id");

                return id;

            }

        } catch (SQLException ex) {
            Logger.getLogger(controlAcceso.class.getName()).log(Level.SEVERE, null, ex);

        } finally {

            con.desconectar();
            con.destruirConsultas(st, rs);

        }
        return null;

    }

    /*Fin consultas */
 /*Inicio Eliminar  */
    public boolean eliminarAsignacion(Integer id) {
        try {
            Connection c = con.conectar();
            PreparedStatement query = c.prepareStatement("DELETE FROM asignaciones where id = ?");
            query.setInt(1, id);
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.print("error de base de datos");
            JOptionPane.showMessageDialog(null, "No se realizo la eliminacion ", "Error", JOptionPane.ERROR_MESSAGE);

        } finally {

            con.desconectar();

        }
        return false;
    }

    public boolean eliminarAcceso(Integer id) {
        try {
            Connection c = con.conectar();
            PreparedStatement query = c.prepareStatement("DELETE FROM accesos where id = ?");
            query.setInt(1, id);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se realizo la eliminacion ", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {

            con.desconectar();

        }
        return false;
    }

    /*Fin Eliminar  */
 /*Inicio  Actualizar */
    public boolean actualizarAcceso(Integer id, String nombre, String usuario, String contrasena) {
        try {
            Connection c = con.conectar();
            PreparedStatement query = c.prepareStatement("UPDATE accesos SET nombre_acceso = '" + nombre + "', usuario = '" + usuario + "', contrasena  = '" + contrasena + "'  where id = ?");
            query.setInt(1, id);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            // JOptionPane.showMessageDialog(null,"No se realizo la actualizacion ","Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error de base de datos");
        } finally {

            con.desconectar();

        }
        return false;
    }

    public boolean actualizarAsignaciones(Integer id, Integer idUsuario, Integer idSitios) {
        try {
            Connection c = con.conectar();
            PreparedStatement query = c.prepareStatement("UPDATE asignaciones SET usuarios_id = '" + idUsuario + "', sitios_id = '" + idSitios + "' where id = ? ");
            query.setInt(1, id);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("error de base de datos  asignaciones");
        } finally {

            con.desconectar();

        }
        return false;
    }

    public boolean actualizarAsignacion(Integer id, Integer idUsuario, Integer idAcceso) {
        try {
            Connection c = con.conectar();
            String sql = "UPDATE asignaciones  SET  usuarios_id = '" + idUsuario + "', accesos_id = '" + idAcceso + "' where id = ?";
            PreparedStatement query = c.prepareStatement(sql);
            query.setInt(1, id);
            query.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println("Error de base datos");

        } finally {

            con.desconectar();

        }
        return false;

    }

    public boolean asignacionesEliminadas(Integer id, Integer idUsuario) {
        try {
            Connection c = con.conectar();
            String sql = "UPDATE asignaciones SET  usuarios_id = '" + idUsuario + "' where id = ?";
            PreparedStatement query = c.prepareStatement(sql);
            query.setInt(1, id);
            query.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Error de base de datos");
            JOptionPane.showMessageDialog(null, "No se logro  eliminar la asignacion", "Ocurrio Un Error", JOptionPane.WARNING_MESSAGE);
        } finally {

            con.desconectar();

        }

        return false;

    }

    /*Fin Actualizar */
 /*Inicio Insertar */
    public boolean GuardarAccesos(String nombre, String usuario, String contrasena, String accesoFiel) {
        try {
            Connection c = con.conectar();
            PreparedStatement conexion = c.prepareStatement("INSERT INTO accesos(nombre_acceso,usuario,contrasena,acceso) VALUES(?,?,?,?)");
            conexion.setString(1, nombre);
            conexion.setString(2, usuario);
            conexion.setString(3, contrasena);
            conexion.setString(4, accesoFiel);
            conexion.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.print("error de base de datos");
            JOptionPane.showMessageDialog(null, "El usuario no logro guardarse ", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {

            con.desconectar();

        }
        return false;
    }

    public boolean GuardarAsignaciones(Integer usuarios_id, Integer sitios_id, Integer accesos_id) {
        try {
            Connection c = con.conectar();
            PreparedStatement conexion = c.prepareStatement("INSERT INTO asignaciones(usuarios_id,sitios_id,accesos_id,estatus)VALUES(?,?,?,?)");
            conexion.setInt(1, usuarios_id);
            conexion.setInt(2, sitios_id);
            conexion.setInt(3, accesos_id);
            conexion.setInt(4, 0);
            conexion.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.print("error de base de datos");
            JOptionPane.showMessageDialog(null, "El usuario no logro guardarse ", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {

            con.desconectar();

        }
        return false;

    }

    /*Fin Insertar */
}
