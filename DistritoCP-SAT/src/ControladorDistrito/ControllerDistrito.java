/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorDistrito;

//import distritocp.sat.acceso;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.net.URL;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author DistritoCP
 */
public class ControllerDistrito {
    //   acceso    verificacionAcceso = new acceso();

    public boolean validacionCampos(String nombre, String usuario, String contrasena, String accesoFiel, JTextField txtAccesoFielCaja) {
        if (txtAccesoFielCaja.isEnabled() == false) {
            if (nombre.equals("") || usuario.equals("") || contrasena.equals("")) {
                return true;

            } else {
                return false;

            }

        } else {
            if (nombre.equals("") || usuario.equals("") || contrasena.equals("") || accesoFiel.equals("")) {
                return true;

            } else {

                return false;
            }

        }

    }

    public void acessoPlataformas(String usuario, String contrasena, String acceso, String accesoFiel) throws AWTException {
        try {
            System.setProperty("webdriver.gecko.driver", "C:\\acceso\\geckodriver.exe");
            WebDriver driver = new FirefoxDriver();
            Robot robot = new Robot();
            if (acceso.equals("https://portalsat.sat.gob.mx/AutorizacionCiec.aspx")) {
                System.out.println("SAT");
                driver.get(acceso);
                driver.findElement(By.id("txtRfc")).sendKeys(usuario);
                driver.findElement(By.id("txtPssword")).sendKeys(contrasena);
                driver.findElement(By.id("btnAceptar")).click();
            } else if (acceso.equals("https://www.fel.mx/cfdi/presentacion/usuario/ingreso.aspx")) {
                robot.mouseMove(520, 270);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                driver.get("https://www.fel.mx/cfdi/presentacion/usuario/ingreso.aspx");
                driver.findElement(By.id("ctl00_ContentPlaceHolder1_TextBoxUsuario")).sendKeys(usuario);
                WebElement cuentaUsuario = driver.findElement(By.id("password_ctl00_ContentPlaceHolder1_TextboxCuenta"));
                cuentaUsuario.sendKeys(accesoFiel);
                cuentaUsuario.click();
                cuentaUsuario.click();
                cuentaUsuario.sendKeys(Keys.TAB);
                WebElement password = driver.findElement(By.id("password_ctl00_ContentPlaceHolder1_TextBoxPassword"));
                password.sendKeys(contrasena);
                password.click();
                password.click();
                password.sendKeys(Keys.TAB);
                //password.sendKeys(Keys.ENTER);   
                driver.findElement(By.id("ctl00_ContentPlaceHolder1_ButtonEntrar")).click();

            } else if (acceso.equals("http://e-paf.com/login/")) {
                driver.get(acceso);
                driver.findElement(By.id("user_login")).sendKeys(usuario);
                driver.findElement(By.id("user_pass")).sendKeys(contrasena);
                driver.findElement(By.id("wp-submit")).click();

            } else if (acceso.equals("https://davinci.oficinasibs.mx/login")) {
                driver.get(acceso);
                driver.findElement(By.name("email")).sendKeys(usuario);
                WebElement contrasenaIBS = driver.findElement(By.id("password"));
                contrasenaIBS.sendKeys(contrasena);
                contrasenaIBS.sendKeys(Keys.ENTER);

            } else if (acceso.equals("http://www.idconline.com.mx/login.php")) {
               driver.get(acceso);
                driver.findElement(By.id("cl_exp_usr")).sendKeys(usuario);
                 driver.findElement(By.id("cl_exp_pwd")).sendKeys(contrasena);
               driver.findElement(By.id("cl_exp_btn")).click();

            }

        } catch (AWTException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error en el acceso ", "Informacion", JOptionPane.INFORMATION_MESSAGE);

        }
    }

}
