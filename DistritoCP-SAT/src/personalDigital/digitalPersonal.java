/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personalDigital;

import ControladorDistrito.ControllerDistrito;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import distritocp.sat.menuDistrito;
import distritocp.sat.LoginDistrito;
import java.awt.Image;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import ModeloDistrito.ConsultasDistrito;
import distritocp.Cliente.accesosAsignados;
import distritocp.sat.acceso;
import java.awt.AWTException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author Distrito CP Clase que se encarga de gestionar el sensor de digital
 * personal
 */


public class digitalPersonal {

    private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
    private DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
    private DPFPTemplate template;
    public static String TEMPLATE_PROPERTY = "template";
    public Integer idUsuario;
    public Integer IDUsuarioAcceso;
    public DPFPVerificationResult result;
    public String nombreUsuario; 
    public String contrasena ; 
    public String url; 
    public String acceso;
    public String cuenta;
    ConsultasDistrito datosHuella = new ConsultasDistrito();
    ControllerDistrito DistritoControlador  = new ControllerDistrito();
    JLabel lblHuella;
    JLabel lblEvento;
    JLabel lblEstado;
    JFrame frmLogin;         
    JFrame frmVerificacion;
    JFrame frmActualizacion ;
    JButton btnEntrar; 
   public  void  setHuella (JLabel lblHuella ){
      this.lblHuella  = lblHuella;
    
    }

    public JLabel getHuella(){
        return lblHuella;
    
    }
 
     public  void  setEvento (JLabel lblEvento ){
      this.lblEvento  = lblEvento;
    
    }

    public JLabel getEvento(){
        return lblEvento;
    
    }
    
    
    public void setEstado(JLabel lblEstado){
       this.lblEstado =  lblEstado; 
    }
    
    
    public JLabel getEstado(){
     return lblEstado;   
     
    }
    
    
    
    public void setFrame(JFrame frmLogin){
    this.frmLogin = frmLogin;
    }
    
    
    public JFrame getFrame(){   
      return frmLogin;
    }
   
    
    public void setFrameVerificacion(JFrame frmVerificacion){
    
    this.frmVerificacion = frmVerificacion;
    
    
    }
    
    public void setFrameActualizar(JFrame frmActualizacion){
       this.frmActualizacion = frmActualizacion ; 
    
    }
    
    public JFrame   getFrameActualizar (){
    
  return   frmActualizacion ; 
    }
    
    
    
    public JFrame getFrameVerficacion(){
    
      return frmVerificacion;
    }

    public  void setIDUsuarioAcceso(Integer IDUsuarioAcceso){
    
    this.IDUsuarioAcceso =  IDUsuarioAcceso;
    
    }
    
    public Integer getIDUsuarioAcceso(){
    
     return IDUsuarioAcceso;
    }
    
    public  void setNombreUsuario (String nombreUsuario){
    this.nombreUsuario = nombreUsuario;
    
    }
    
    public String   getNombreUsuario(){
      return nombreUsuario;     
    }
    
  
     public  void setContrasena (String contrasena){
    this.contrasena = contrasena;
    
    }
    
    public String    getContrasena(){
      return contrasena ;     
    }
    
  
   
     public  void setUrl (String url){
    this.url = url;
    
    }
    
    public String    getUrl(){
      return url ;     
    }
    
    
    public void  setAcceso(String acceso){
       this.acceso = acceso ; 
    
    }
    
    public String  getAcceso(){
            return acceso ; 
                     
    }
 
    
     public void  setCuenta(String cuenta){
       this.cuenta = cuenta ; 
    
    }
    
    public String  getCuenta(){
            return cuenta ; 
                     
    }
    
    
    
    
    
    public void Iniciar() {
        Lector.addDataListener(new DPFPDataAdapter() {
            @Override
            public void dataAcquired(final DPFPDataEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/huella.png"));
                     
                        lblHuella.setIcon(icon);
                        lblEvento.setText("La Huella Digital ha sido Capturada");
                        try {
                            // LoginDistrito proceso  = new LoginDistrito();
                            ProcesarCaptura(e.getSample());
                        } catch (AWTException ex) {
                            Logger.getLogger(digitalPersonal.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        });
        Lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            @Override
            public void readerConnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/bloggif_588f697f40bb3.png"));
                        lblEstado.setIcon(icon);
                        lblEstado.setText("Conectado");

                    }
                });
            }

            @Override
            public void readerDisconnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/bloggif_588f68c7e4992.png"));
                        lblEstado.setIcon(icon);
                        lblEstado.setText("Desconectado");
                    }
                });
            }
        });
        Lector.addSensorListener(new DPFPSensorAdapter() {
            @Override
            public void fingerTouched(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/huellas.png"));
                        lblHuella.setIcon(icon);
                        lblEvento.setText("El dedo ha sido colocado sobre el Lector de Huella");
                    }
                });
            }

            @Override
            public void fingerGone(final DPFPSensorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/quitar.png"));
                        lblHuella.setIcon(icon);
                         lblEvento.setText("El dedo ha sido quitado del Lector de Huella");
                    }
                });
            }
        });
        Lector.addErrorListener(new DPFPErrorAdapter() {
            public void errorReader(final DPFPErrorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/error.png"));
                         lblHuella.setIcon(icon);
                          lblEvento .setText("Error:" + e.getError());
                    }
                });
            }
        });
        Lector.addErrorListener(new DPFPErrorAdapter() {
            public void errorReader(final DPFPErrorEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/error.png"));
                       lblHuella.setIcon(icon);
                       lblEvento.setText("Error:" + e.getError());
                    }
                });
            }
        });
    }

    public DPFPFeatureSet featuresinscripcion;
    public DPFPFeatureSet featuresverificacion;

    public DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose) {
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try {
            return extractor.createFeatureSet(sample, purpose);
        } catch (DPFPImageQualityException e) {
            return null;
        }
    }

    public Image CrearImagenHuella(DPFPSample sample) {
        return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    }

    public void EstadoHuellas() {
        System.out.println("Muestra de Huellas Necesarias para Guardar Template " + Reclutador.getFeaturesNeeded());
    }

    public void start() {
        Lector.startCapture();
    }

    public void stop() {
        Lector.stopCapture();
       
    }

    public DPFPTemplate getTemplate() {
        return template;
    }

    public void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.template;
        this.template = template;
    }
     
    public void accesoUsuario() {
        try {
            ResultSet huellasDatos = datosHuella.Huellas();
            while (huellasDatos.next()) {
                byte templateBuffer[] = huellasDatos.getBytes("huellas");
                idUsuario = huellasDatos.getInt("id");
                String nombre = huellasDatos.getString("nombre");
                Integer tipo = huellasDatos.getInt("tipo");
                DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
                setTemplate(referenceTemplate);
                DPFPVerificationResult result = Verificador.verify(featuresverificacion, getTemplate());
                if (result.isVerified()) {
                    if (tipo == 0) {
                        menuDistrito formulario = new menuDistrito();
                        formulario.setVisible(true); 
                        frmLogin.setVisible(false);
                        stop();
                        formulario.setID(idUsuario);
                        return;
                    } else if (tipo == 1) {
                       accesosAsignados formularioCliente = new accesosAsignados();
                       formularioCliente.setVisible(true);
                       frmLogin.setVisible(false);
                       stop();  
                       formularioCliente.setId(idUsuario);
                       return;
                    }
                }
            }    
            JOptionPane.showMessageDialog(null, "No existe ningún registro que coincida con la huella", "Verificacion de Huella", JOptionPane.ERROR_MESSAGE);
            setTemplate(null);
        } catch (SQLException ex) {
            System.err.println("Error al verificar los datos de la huella.");
        }
    }
    
  public void verificarActualizacion(){
  System.out.println("hola");
  
  
  }
    
    
    public void verficarUsuario() throws AWTException{
     try {
            ResultSet huellasDatos = datosHuella.Huellas();
            while (huellasDatos.next() ) {
                byte templateBuffer[] = huellasDatos.getBytes("huellas");
                idUsuario = huellasDatos.getInt("id");
                String nombre = huellasDatos.getString("nombre");
                Integer tipo = huellasDatos.getInt("tipo");
                DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
                setTemplate(referenceTemplate);
                DPFPVerificationResult result = Verificador.verify(featuresverificacion, getTemplate());
                if (result.isVerified()) {
                     if(IDUsuarioAcceso == idUsuario){
                      System.out.println("autorizado");
                     if(acceso == "accesoAdmin"){
                      JOptionPane.showMessageDialog(null, "Ejecutando Acceso", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                      frmVerificacion.setVisible(false);
                      String accesoFiel = cuenta;
                      DistritoControlador.acessoPlataformas(nombreUsuario,contrasena,url,accesoFiel);
                      JOptionPane.showMessageDialog(null, "Acceso Ejecutado", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                       stop();   
                      return ;                 
                     }
                     if(acceso == "verContrasenas"){
                            stop();   
                          JOptionPane.showMessageDialog(null, "La Contraseña es :"  +contrasena, "Informacion", JOptionPane.INFORMATION_MESSAGE);
                          return;
                     }

                     }else{
                     JOptionPane.showMessageDialog(null, "Usted no tiene autorización para realizar esta acción", "Verificacion de Huella", JOptionPane.ERROR_MESSAGE);    
                     frmVerificacion.setVisible(false);
                     stop();
                     return ;
                     }
                }
            }    
            JOptionPane.showMessageDialog(null, "No existe ningún registro que coincida con la huella", "Verificacion de Huella", JOptionPane.ERROR_MESSAGE);
            setTemplate(null);
        } catch (SQLException ex) {
            System.err.println("Error al verificar los datos de la huella.");
        }    
    }

      public void ProcesarCaptura(DPFPSample sample) throws AWTException {
        featuresinscripcion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
        featuresverificacion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
        if (featuresinscripcion != null) {
            try {
                System.out.println("Las Caracteristicas de la Huella han sido creada");
                Reclutador.addFeatures(featuresinscripcion);
                
                if (frmLogin !=null){
                System.out.println("Formulario acceso"); 
                accesoUsuario();
                }
                
                if(frmVerificacion !=null){
                  verficarUsuario();
                }
                
                if(frmActualizacion !=null){
                  verificarActualizacion();
                
                }
                
            } catch (DPFPImageQualityException ex) {
                System.err.println("Error: " + ex.getMessage());
            } finally {
                
                Reclutador.clear();
                datosHuella.desconectar();
                ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/bloggif_588f8648a7d00.png"));
                lblHuella.setIcon(icon);
                lblEvento.setText("Verifique su Identidad");              
            }
        }
    }
 
}
