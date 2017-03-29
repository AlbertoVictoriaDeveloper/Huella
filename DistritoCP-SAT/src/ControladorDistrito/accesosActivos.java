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
public class accesosActivos {
   private Integer idAcceso;
   private String nombreAcceso; 
 public void accesosActivos(Integer idAcceso , String nombreAcceso){
    this.idAcceso = idAcceso ; 
    this.nombreAcceso = nombreAcceso; 
 
 
 } 
 
 
 public void setIDAcceso (Integer idAcceso){
     this.idAcceso = idAcceso ;
     
 
 }
    
  public Integer getIDAcceso(){
      return  idAcceso;
  }
    
  
  public void setNombreAcceso(String nombreAcceso){
       this.nombreAcceso =  nombreAcceso ; 
   
  }
    
 public  String  toString(){
   return nombreAcceso;
 
  
 
 } 
  
}
