/*
 * DistritoCP 
 */
package BD;


import distritocp.sat.controlAcceso;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import javax.swing.JOptionPane;


/**
 *
 * @author DistritoCP
 *
 * Esta clase hace la conexion a base de datos.
 */
public class conexionBD {

    Connection conectar = null;
    private Statement st = null;
    private ResultSet rs = null;

    public Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection("jdbc:mysql://localhost/control_acceso", "root", "");
           //conectar = DriverManager.getConnection("jdbc:mysql://sql3.freesqldatabase.com:3306/sql3159970", "sql3159970", "6LrntwQFsr");
            st = conectar.createStatement();

        } catch (Exception e) {
            System.out.print(e.getMessage());
            System.out.println("error de base de datos ");
        }
        return conectar;
    }

    public void desconectar() {
        try {
            conectar.close();
            System.out.println("Desconexion a base de datos listo...");

        } catch (SQLException e) {
            System.out.println("Error de base de datos");
            java.util.logging.Logger.getLogger(conexionBD.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void destruirConsultas(Statement st,ResultSet rs) {
        {
            try {
                if (rs != null) {
                    rs.close();

                }

                if (st != null) {
                    st.close();

                }
                
                if( conectar !=null){
                   conectar.close();
                }

            } catch (SQLException e) {
                System.out.println("Error de base de datos");
                java.util.logging.Logger.getLogger(conexionBD.class.getName()).log(Level.SEVERE, null, e);

            }

        }

    }
}
  
