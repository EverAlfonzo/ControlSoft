/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import frames.FramePrincipal;

/**
 *
 * @author Edgar
 */
public class main {
     public static void main(String[] args) {
         ScreenSplash b= new ScreenSplash();
         b.animar(); 
         
         FramePrincipal a = new FramePrincipal();
         
         a.setExtendedState(FramePrincipal.MAXIMIZED_BOTH);
         a.setVisible(true);
         a.toFront();
    }
}
