/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorDistrito;

/**
 *
 * @author DistritoCP
 */
public class asignacion {
 private Integer idUsuario ;
 private String  nombreUsuario;
 
 public void asignacion(Integer idUsuario, String nombreUsuario){
      
     this.idUsuario = idUsuario ; 
     this.nombreUsuario =  nombreUsuario; 
     
 
 }
 
 public void setIdUsuario(Integer idUsuario){
 
   this.idUsuario = idUsuario ;
 
 }
 
 
 public  Integer  getIdUsuario(){
    return idUsuario; 
}
 
 
 
 public void setNombreUsuario(String nombreUsuario){
   this.nombreUsuario = nombreUsuario;
 
 }
 
 public String  getNombreUsuario (){
 
    return nombreUsuario;
 }

 public String toString(){
    return nombreUsuario;
 
 }
 
 
 
}
