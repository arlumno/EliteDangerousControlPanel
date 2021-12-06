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

    GuiPanel panel = new GuiPanel(this);
    Conector conector = new Conector();
    Status status = new Status(this);

    public Aplicacion() {
        status.start();
        //ventana.toConsola("Luces Encendidas");
//        panel.toConsola("Luces Apagadas");

//        status.start();
//           status.actualizar();
//               Status.test();
        /*
        while (true) {
            try {                
                if (status.status.get("LightsOn")) {
                    //   conector.ino.sendData("4");                                
                    panel.toConsola("Luces Encendidas");
                    conector.ino.sendData("Luces Encendidas");
                } else {
                    conector.ino.sendData("Luces APAGADAS");
                    panel.toConsola("Luces APAGADAS");
                    //  conector.ino.sendData("sin datos\n");                                
                }
//                System.out.println("enviando:");
//                conector.ino.sendData("1");            
//                conector.ino.sendData("4");            
            } catch (Exception e) {                
                panel.toConsola("Error: " + e.toString());
                System.out.println("Error: " + e.toString());
            }
            try{
                Thread.sleep(1000);
            }catch(Exception e){                
               panel.toConsola("Error: " + e.toString());
                System.out.println("Error: " + e.toString());
            }
        }
         */
    }

    public void conectar() {
        try {
            conector.conectar();
        } catch (Exception e) {
            panel.toConsola(e.toString());
        }
    }

    public void status() {
        try {
            if (status.isAnalizar()) {
                status.parar();
                panel.toConsola("status stop");
            } else {
                status.continuar();
                panel.toConsola("status start");
            }
        } catch (Exception e) {
            panel.toConsola("status() error: " + e.toString());
        }
    }

    public HashMap<String, Boolean> getStatus() {
        return status.getStatus();
    }

    public void toConsola(String texto) {
        panel.toConsola(texto);
    }

    public void actualizarEstadosMonitor() {
        panel.actualizarEstadosMonitor();
    }
}
