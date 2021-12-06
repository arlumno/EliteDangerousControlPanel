/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.elitedangerous.controlpanel.modulos;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.io.InputStream;
import java.io.OutputStream;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author Ar
 */
public class Conector {

    private OutputStream Output = null;
    private InputStream Input = null;
    SerialPort serialPort;
    private final String PORT_NAME = "COM9";
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;
    PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    SerialPortEventListener in = new SerialPortEventListener() {
        @Override
        public void serialEvent(SerialPortEvent spe) {
            try {

                if (ino.isMessageAvailable()) {
                    //System.out.println(spe.toString());
                    System.out.println(ino.printMessage());
                }
            } catch (ArduinoException se) {
                System.out.println("EXCEPT; " + se.toString());
            } catch (SerialPortException se) {
                System.out.println("EXCEPT; " +se.toString());
            }
        }
    };

    public Conector() {
//        boolean connectar = true;
//        while(connectar){
//            try {
//                ino.arduinoRXTX(PORT_NAME, DATA_RATE, in);
//                connectar = false;
//            } catch (Exception e) {
//                System.out.println(e.toString());
//            }            
//        }
    }
    public void conectar() throws Exception{
           try {
                ino.arduinoRXTX(PORT_NAME, DATA_RATE, in);                
            } catch (Exception e) {
                throw new Exception("Error de conectar(): " + e.toString());
            }            
    }
}
