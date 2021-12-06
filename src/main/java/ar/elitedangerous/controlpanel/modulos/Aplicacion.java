/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.elitedangerous.controlpanel.modulos;

import java.util.HashMap;

/**
 *
 * @author Ar
 */
public class Aplicacion {
    EDPanel panel = new EDPanel(this);
    Conector conector = new Conector();
    Status status = new Status(this);
    
    public Aplicacion(){
             status.start();                
        //ventana.inConsola("Luces Encendidas");
//        panel.inConsola("Luces Apagadas");
        
       
//        status.start();
//           status.actualizar();
//               Status.test();
        
        /*
        while (true) {
            try {                
                if (status.status.get("LightsOn")) {
                    //   conector.ino.sendData("4");                                
                    panel.inConsola("Luces Encendidas");
                    conector.ino.sendData("Luces Encendidas");
                } else {
                    conector.ino.sendData("Luces APAGADAS");
                    panel.inConsola("Luces APAGADAS");
                    //  conector.ino.sendData("sin datos\n");                                
                }
//                System.out.println("enviando:");
//                conector.ino.sendData("1");            
//                conector.ino.sendData("4");            
            } catch (Exception e) {                
                panel.inConsola("Error: " + e.toString());
                System.out.println("Error: " + e.toString());
            }
            try{
                Thread.sleep(1000);
            }catch(Exception e){                
               panel.inConsola("Error: " + e.toString());
                System.out.println("Error: " + e.toString());
            }
        }
      */
    }
    public void conectar(){
        try{
            conector.conectar();            
        }catch(Exception e){
            panel.inConsola(e.toString());
        }
    }  
    public void status(){
        try{         
            if(status.isAnalizar()){
                status.parar();                
                panel.inConsola("status stop");
            }else{
                status.continuar();                
                panel.inConsola("status start");
            }
           // status.actualizar();
        }catch(Exception e){
            panel.inConsola(e.toString());
        }
    }
     public HashMap<String, Boolean> getStatus(){
         return status.getStatus();
     }
    public void inConsola(String texto){
        panel.inConsola(texto);
    }
    public void cargarMonitor(){
        panel.cargarMonitor();
    }
}
