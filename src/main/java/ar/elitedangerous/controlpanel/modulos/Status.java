/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.elitedangerous.controlpanel.modulos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import peticiones.EntradasGui;

public class Status extends Thread {

//    private String rutaJson = "C:/Users/Ar/Saved Games/Frontier Developments/Elite Dangerous/Status.json";    
    //private String rutaJson = System.getenv("HOMEDRIVE")+System.getenv("HOMEPATH") + "/Saved Games/Frontier Developments/Elite Dangerous/Status.json";
    private String rutaJson = "src/main/java/archivosDemo/Status.json";
    
    
    File archivoJson = new File(rutaJson);
    private JSONParser parser = new JSONParser();
    private HashMap<String, Boolean> status = new HashMap<String, Boolean>();
    private boolean analizar = false;
    private boolean actualizar = true;
    private final int DELAY = 100;
    private final int FLAGS_BINARY_LENGTH = 27; //27 to basic game, 31 to Odyssey
    private long longFlags = 0;
    private Aplicacion aplicacion;

    public Status(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
        
        System.out.println(archivoJson.getAbsoluteFile());
        if (!archivoJson.exists()) {
            System.out.println("---- El archivo no existe. ----");
            //archivoJson = EntradasGui.pedirArchivo();
        }

        status.put("LightsOn", false);
    }

    public void run() {
        long aux;
        actualizar(); //primera actualización
        while (true) {
            try {
                sleep(DELAY);
                while (isAnalizar()) {
                    sleep(DELAY);
                    if (longFlags != (aux = getLongFlagsFromJson())) {
                        longFlags = aux;
                        actualizar();
                    }
                }
            } catch (Exception e) {
                aplicacion.toConsola("Error 01: " + e.toString());
            }
        }
    }

    public void parar() {
        setAnalizar(false);
    }
    
    public void continuar() {
        setAnalizar(true);
        aplicacion.toConsola("continuar.. " + isAnalizar());

    }

    public void actualizar() throws RuntimeException {
        if (longFlags != -1) {
            StringBuilder flags = new StringBuilder(Long.toBinaryString(longFlags));
            flags = flags.reverse();
            if (flags.length() < FLAGS_BINARY_LENGTH) {
                for (int i = flags.length(); i < FLAGS_BINARY_LENGTH ;i++) {
                    flags.append("0");
                }
            }
            /*
            Flags:
            Bit Value Hex Meaning
            0 1 0000 0001 Docked, (on a landing pad)
            1 2 0000 0002 Landed, (on planet surface)
            2 4 0000 0004 Landing Gear Down
            3 8 0000 0008 Shields Up
            4 16 0000 0010 Supercruise
            5 32 0000 0020 FlightAssist Off
            6 64 0000 0040 Hardpoints Deployed
            7 128 0000 0080 In Wing
            8 256 0000 0100 LightsOn
            9 512 0000 0200 Cargo Scoop Deployed
            10 1024 0000 0400 Silent Running,
            11 2048 0000 0800 Scooping Fuel
            12 4096 0000 1000 Srv Handbrake
            13 8192 0000 2000 Srv Turret
            14 16384 0000 4000 Srv UnderShip
            15 32768 0000 8000 Srv DriveAssist
            16 65536 0001 0000 Fsd MassLocked
            17 131072 0002 0000 Fsd Charging
            18 262144 0004 0000 Fsd Cooldown
            19 524288 0008 0000 Low Fuel ( < 25% )
            20 1048576 0010 0000 Over Heating ( > 100% )
            21 2097152 0020 0000 Has Lat Long
            22 4194304 0040 0000 IsInDanger
            23 8388608 0080 0000 Being Interdicted
            24 16777216 0100 0000 In MainShip
            25 33554432 0200 0000 In Fighter
            26 67108864 0400 0000 In SRV
            // Odyssey expansion:
            27 134217728 0800 0000 Hud in Analysis mode
            28 268435456 1000 0000 Night Vision
            29 536870912 2000 0000 Altitude from Average radius
            30 1073741824 4000 0000 fsdJump
            31 2147483648 8000 0000 srvHighBeam
             */
            
            getStatus().clear();
            getStatus().put("Docked", (flags.charAt(0) == '1'));
            getStatus().put("Landed", (flags.charAt(1) == '1'));
            getStatus().put("Landing", (flags.charAt(2) == '1'));
            getStatus().put("ShieldsUp", (flags.charAt(3) == '1'));
            getStatus().put("Supercruise", (flags.charAt(4) == '1'));
            getStatus().put("FlightAssistOff", (flags.charAt(5) == '1'));
            getStatus().put("HardpointsDeployed", (flags.charAt(6) == '1'));
            getStatus().put("InWing", (flags.charAt(7) == '1'));
            getStatus().put("LightsOn", (flags.charAt(8) == '1'));
            getStatus().put("CargoScoopDeployed", (flags.charAt(9) == '1'));
            getStatus().put("SilentRunning", (flags.charAt(10) == '1'));
            getStatus().put("Scooping Fuel", (flags.charAt(11) == '1'));
            getStatus().put("SrvHandbrake", (flags.charAt(12) == '1'));
            getStatus().put("SrvTurret", (flags.charAt(13) == '1'));
            getStatus().put("SrvUnderShip", (flags.charAt(14) == '1'));
            getStatus().put("SrvDriveAssist", (flags.charAt(15) == '1'));
            getStatus().put("FsdMassLocked", (flags.charAt(17) == '1'));
            getStatus().put("FsdCooldown", (flags.charAt(18) == '1'));
            getStatus().put("LowFuel", (flags.charAt(19) == '1'));
            getStatus().put("OverHeating", (flags.charAt(20) == '1'));
            getStatus().put("HasLatLong", (flags.charAt(21) == '1'));
            getStatus().put("IsInDanger", (flags.charAt(22) == '1'));
            getStatus().put("BeingInterdicted", (flags.charAt(23) == '1'));
            getStatus().put("InMainShip", (flags.charAt(24) == '1'));
            getStatus().put("InFighter", (flags.charAt(25) == '1'));
            getStatus().put("InSRV", (flags.charAt(26) == '1'));

            aplicacion.actualizarEstadosMonitor();
        } else {
            aplicacion.toConsola("No hay flags disponibles");
        }
    }

    private String mostrarStatus() {
        String resultado = "";
        for (String flag : getStatus().keySet()) {
            if (getStatus().get(flag)) {
                resultado += flag + ": " + getStatus().get(flag) + "/n";
            }
        }
        return resultado;
    }

    private long getLongFlagsFromJson() {
        JSONObject json = new JSONObject();
        long resultado = -1;

        File archivo = new File(rutaJson);
        try ( BufferedReader bf = new BufferedReader(new FileReader(archivo))) {
//            if (archivo.exists() && archivo.isFile()) {
//            json = (JSONObject) parser.parse(new FileReader(archivo));
            try {
                json = (JSONObject) parser.parse(bf);
                resultado = (long) json.get("Flags");
            } catch (Exception e) {
                aplicacion.toConsola("Error Json: " + e.getClass() + " " + e.toString());
                aplicacion.toConsola("  ----  " + bf.toString());
            }
        } catch (Exception e) {
            aplicacion.toConsola("Error al leer el archivo: " + e.getClass() + " " + e.toString());
        }
        return resultado;
    }

    public static String listarBinario(String binario) {
        StringBuilder texto = new StringBuilder();
        for (int i = 0; i < binario.length(); i++) {
            texto.append("i " + i + " = " + binario.charAt((binario.length() - i - 1)) + "/n");
        }

        return texto.toString();
    }

    /**
     * @return the analizar
     */
    public boolean isAnalizar() {
        return analizar;
    }

    /**
     * @return the actualizar
     */
    public boolean isActualizar() {
        return actualizar;
    }

    /**
     * @param analizar the analizar to set
     */
    public void setAnalizar(boolean analizar) {
        this.analizar = analizar;
    }

    /**
     * @param actualizar the actualizar to set
     */
    public void setActualizar(boolean actualizar) {
        this.actualizar = actualizar;
    }

    /**
     * @return the status
     */
    public HashMap<String, Boolean> getStatus() {
        return status;
    }

}
