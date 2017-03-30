/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;
import java.awt.*;
import java.awt.SplashScreen;
import javax.swing.UIManager;
/**
 *
 * @author Edgar
 */
public final class ScreenSplash {
    final SplashScreen splash ;
  //texto que se muestra a medida que se va cargando el screensplah
  final String[] texto = {"Gimme the power" ,"configuration", "library",
                          "files XYZ","forms","iconos","properties",
                          "XML files", "X-files", "anonymous",
                          "database" ,"server","wtf!",
                          ""};

  public ScreenSplash() {
  splash = SplashScreen.getSplashScreen();
 
  }

   public void animar()
   {
        if (splash != null)
        {
            Graphics2D g = splash.createGraphics();
            for(int i=1; i<295; i+=20)
            {                       
                //se pinta texto del array
                g.setColor( new Color(255,255,255));//color de fondo
                g.fillRect(203, 268,280,9);//para tapar texto anterior
               // g.setColor(Color.BLACK);//color de texto 
                //g.drawString("Loading "+texto[i-1]+"...", 203, 338);                
                g.setColor(Color.BLACK);//color de barra de progeso
                g.fillRect(5, 285,(i*307/295), 9);//la barra de progreso
                //se pinta una linea segmentada encima de la barra verde
                float dash1[] = {2.0f};
                BasicStroke dashed = new BasicStroke(9.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,5.0f, dash1, 5.0f);
                g.setStroke(dashed);
                g.setColor(Color.RED);//color de barra de progeso
                g.setColor(Color.BLACK);
                //g.drawLine(205,304, 510, 314);                
                //se actualiza todo
                splash.update();
                try {
                    Thread.sleep(400);
                } catch(InterruptedException e) { }
            }
            splash.close();
        }
        //una vez terminada la animación se muestra la aplicación principal
        try {
             try{
                 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
             
             }catch(Exception e){}
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
         
   }
   
}
