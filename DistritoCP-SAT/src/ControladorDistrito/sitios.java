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
public class sitios {

    private Integer id;
    private String url;
    private Integer idAcceso; 

    public void sitios(Integer id, String url) {
    
        this.id = id;
        this.url = url;
    }

    public Integer getID() {

        return id;
    }
    
    
    public void setID(Integer id){
     this.id = id ; 
     
    }

    public String geturl() {

        return url;

    }
    
    public void setIDAcceso(Integer id ){
        this.idAcceso = id ; 
    }
    
    public Integer getIDAcceso(){
    
             return idAcceso;
    }
    
    
    

    public void seturl(String url){
    
        this.url=url;
    
    }    

    @Override
    public String toString() {
        return   url ;
        
    }


    
}
