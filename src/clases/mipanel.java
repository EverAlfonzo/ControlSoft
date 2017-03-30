/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Edgar
 */
public class mipanel extends JPanel{
    int b;
    public mipanel(int w, int h){
        this.setSize(w, h);
    }
    public mipanel(int w, int h, int b){
        this.b=b;
        this.setSize(w, h);
    }
    @Override
     public void paint(Graphics g){
            if(b==0){
                ImageIcon fondo= new ImageIcon(getClass().getResource("/imagenes/prog_princ_img.png"));
                g.drawImage(fondo.getImage(), 0, 0, getWidth(),getHeight(),null);
                 
            }else{
                ImageIcon fondo= new ImageIcon(getClass().getResource("/imagenes/logon.jpg"));
                g.drawImage(fondo.getImage(), 0, 0, getWidth(),getHeight(),null);
            }
            

            setOpaque(false);
            super.paintComponent(g);
    }
}
