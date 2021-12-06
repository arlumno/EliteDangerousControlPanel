/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.elitedangerous.controlpanel.modulos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;

/**
 *
 * @author Ar
 */
public class Monitor extends Canvas {
    HashMap<String, Boolean> estados = new HashMap<String, Boolean>();
    
    public void setEstados(HashMap<String, Boolean> estados){
        this.estados = estados;
        repaint();
    }
    
    public void paint(Graphics g) {
//        estados.put("Texto 1", true);
//        estados.put("Texto 2", false);
//        estados.put("Texto 3", true);
        int diametro = 14;
        int sizeTexto= diametro;
        int margen = 8;
        int xCirculo = 0;
        int yCirculo = 10;
        int xTexto = diametro + margen + xCirculo;
        int yTexto = yCirculo + sizeTexto;
                
        Graphics2D g2 = (Graphics2D) g;
        g.setFont(new java.awt.Font("Square721 BT", 1, sizeTexto));
        
        for (String k : estados.keySet()) {
            g2.setColor(Color.GRAY);
            g2.drawString(k, xTexto, yTexto);
            
            if(estados.get(k)){
                g2.setColor(Color.green);                
            }else{
                g2.setColor(Color.red);                
            }
            g2.fill(new Ellipse2D.Double(xCirculo, yCirculo, diametro, diametro));
            
            yCirculo += diametro + margen;
            yTexto = yCirculo + sizeTexto;
        }
    }
}
