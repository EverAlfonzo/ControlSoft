/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jDCompras.java
 *
 * Created on 11/08/2012, 03:09:20 PM
 */
package frames;

import clases.DBConnect;
import java.awt.Image;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.JOptionPane;
import java.net.URL;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Edgar
 */
public class jDCompras extends javax.swing.JDialog {
private static String vend;
Image imagen = getToolkit().getImage(getClass().getResource("/imagenes/global2.png"));
   DBConnect bd;
   DefaultTableModel m;
   ResultSet r;
   private int i=0;
   Date hoy;
   Date fecha;
    /** Creates new form jDCompras */
    public jDCompras(java.awt.Frame parent, boolean modal,DBConnect con) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Compras");
        setIconImage(imagen);
        hoy= new Date();
        fecha = new Date();
        jXhasta.setDate(hoy);
        fecha.setMonth(hoy.getMonth()-1);
        jXdesde.setDate(fecha);
        bd = con;
        
        PrepararTabla();
        this.jmVisualizar.setEnabled(false);
    }
        
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpmVisualizar = new javax.swing.JPopupMenu();
        jmVisualizar = new javax.swing.JMenuItem();
        jXdesde = new org.jdesktop.swingx.JXDatePicker();
        jLabel1 = new javax.swing.JLabel();
        jXhasta = new org.jdesktop.swingx.JXDatePicker();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTCompras = new javax.swing.JTable();
        jPBotones = new javax.swing.JPanel();
        jBNueva = new javax.swing.JButton();
        jBVisualizarreg = new javax.swing.JButton();
        jBSalir = new javax.swing.JButton();

        jmVisualizar.setText("Visualizar Detalles");
        jmVisualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmVisualizarActionPerformed(evt);
            }
        });
        jpmVisualizar.add(jmVisualizar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jXdesde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXdesdeActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Visualizar las compras correspondientes al periodo");

        jXhasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXhastaActionPerformed(evt);
            }
        });

        jLabel3.setText("al:");

        jLabel2.setText("de:");

        jTCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTCompras.setComponentPopupMenu(jpmVisualizar);
        jTCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTComprasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTCompras);

        jPBotones.setLayout(new java.awt.GridLayout(1, 3));

        jBNueva.setText("Nueva Compra");
        jBNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNuevaActionPerformed(evt);
            }
        });
        jPBotones.add(jBNueva);

        jBVisualizarreg.setText("Visualizar Registro");
        jBVisualizarreg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBVisualizarregActionPerformed(evt);
            }
        });
        jPBotones.add(jBVisualizarreg);

        jBSalir.setText("Salir");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });
        jPBotones.add(jBSalir);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXdesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXhasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPBotones, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jXhasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(jXdesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNuevaActionPerformed
        // TODO add your handling code here:
            jDncompra compra= new jDncompra(null, true,bd);
            compra.setVisible(true);
            PrepararTabla();
    }//GEN-LAST:event_jBNuevaActionPerformed

    private void jBSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
}//GEN-LAST:event_jBSalirActionPerformed

    private void jXdesdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXdesdeActionPerformed
        // TODO add your handling code here:
        fecha= jXdesde.getDate();
        PrepararTabla();
        this.jmVisualizar.setEnabled(false);
    }//GEN-LAST:event_jXdesdeActionPerformed

    private void jXhastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXhastaActionPerformed
        // TODO add your handling code here:
        hoy= jXhasta.getDate();
        PrepararTabla();
        this.jmVisualizar.setEnabled(false);
    }//GEN-LAST:event_jXhastaActionPerformed

    private void jBVisualizarregActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBVisualizarregActionPerformed
         try{
           r=null;
           r=bd.Listar("*", "vistacompra", "where `Fecha_compra` >= '"+(fecha.getYear()+1900)+"-"+(fecha.getMonth()+1)+"-"+(fecha.getDate())+"' and Fecha_compra <= '"+(hoy.getYear()+1900)+"-"+(hoy.getMonth()+1)+"-"+(hoy.getDate())+"' order by Id_compra ASC");
          

           
        } catch (Exception ex) {
             //Logger.getLogger(ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }

        JRResultSetDataSource jrRS = new JRResultSetDataSource(r);
        HashMap parameters = new HashMap();

        try{
            URL urlMaestro = getClass().getClassLoader().getResource("reportes/compras.jasper");
            // Cargamos el reporte
            JasperReport masterReport = null;
            masterReport = (JasperReport) JRLoader.loadObject(urlMaestro);
            JasperPrint masterPrint = null;
            masterPrint = JasperFillManager.fillReport(masterReport,parameters,jrRS);

             JasperViewer ventana = new JasperViewer(masterPrint,false);
            ventana.setTitle("Vista Previa");
            ventana.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
            ventana.setVisible(true);

        }catch(JRException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrio un error "+e.toString(),"ATENCION ", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBVisualizarregActionPerformed

    private void jmVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmVisualizarActionPerformed
        String id = String.valueOf(jTCompras.getValueAt(jTCompras.getSelectedRow(), 0));
        new jDDetalleCompra(null, true, bd, id).setVisible(true);
    }//GEN-LAST:event_jmVisualizarActionPerformed

    private void jTComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTComprasMouseClicked
        this.jmVisualizar.setEnabled(true);
    }//GEN-LAST:event_jTComprasMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                jDCompras dialog = new jDCompras(new javax.swing.JFrame(), true,null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBNueva;
    private javax.swing.JButton jBSalir;
    private javax.swing.JButton jBVisualizarreg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPBotones;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTCompras;
    private org.jdesktop.swingx.JXDatePicker jXdesde;
    private org.jdesktop.swingx.JXDatePicker jXhasta;
    private javax.swing.JMenuItem jmVisualizar;
    private javax.swing.JPopupMenu jpmVisualizar;
    // End of variables declaration//GEN-END:variables

    private void PrepararTabla() {
        r= null;
        r = bd.Listar("*", "compra", "where `Fecha_compra` >= '"+(fecha.getYear()+1900)+"-"+(fecha.getMonth()+1)+"-"+(fecha.getDate())+"' and Fecha_compra <= '"+(hoy.getYear()+1900)+"-"+(hoy.getMonth()+1)+"-"+(hoy.getDate())+"' order by Id_compra ASC");
        m = (new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Cod. Compra","Fecha","Observaciones"})
             {
                 boolean[] canEdit = new boolean [] {false, false, false};

                 public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }});
        
        jTCompras.setModel(m);
        String Fila[]= new String[3];
            try {
                while(r.next()){
                    Fila[0]= r.getString("Id_compra");
                    Fila[1]= r.getString("Fecha_compra");
                    Fila[2]= r.getString(3);
                    m.addRow(Fila);

                }
            } catch (SQLException ex) {
               JOptionPane.showMessageDialog(null, ex.getMessage(),"Error 335 : Error SQL",JOptionPane.ERROR_MESSAGE);
            }
    }
}
