/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import clases.DBConnect;
import com.mysql.jdbc.Blob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXImageView;

/**
 *
 * @author Ever
 */
public class jDListEmpresa extends javax.swing.JDialog {

    /**
     * Creates new form jDListEmpresa
     */
    
    private DBConnect con;
    private DefaultTableModel m;
    public jDListEmpresa(java.awt.Frame parent, boolean modal, DBConnect db) {
        super(parent, modal);
        initComponents();
        this.con = db;
        this.setLocationRelativeTo(null);
        PrepararTabla();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPmenuEmpresa = new javax.swing.JPopupMenu();
        ModificarVend = new javax.swing.JMenuItem();
        Eliminar_Vend = new javax.swing.JMenuItem();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTEmpresas = new javax.swing.JTable();
        jPBotones = new javax.swing.JPanel();
        jBNuevoVEnd = new javax.swing.JButton();
        jBSalir = new javax.swing.JButton();
        jBModVenta = new javax.swing.JButton();
        jXVentaImage = new org.jdesktop.swingx.JXImageView();
        jLabel1 = new javax.swing.JLabel();

        ModificarVend.setText("Modificar Vendedor");
        ModificarVend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ModificarVendMouseClicked(evt);
            }
        });
        ModificarVend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarVendActionPerformed(evt);
            }
        });
        jPmenuEmpresa.add(ModificarVend);

        Eliminar_Vend.setText("Eliminar Vendedor");
        Eliminar_Vend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Eliminar_VendMouseClicked(evt);
            }
        });
        Eliminar_Vend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Eliminar_VendActionPerformed(evt);
            }
        });
        jPmenuEmpresa.add(Eliminar_Vend);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTEmpresas.setModel(new javax.swing.table.DefaultTableModel(
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
        jTEmpresas.setComponentPopupMenu(jPmenuEmpresa);
        jTEmpresas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTEmpresasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTEmpresas);

        jPBotones.setLayout(new java.awt.GridLayout(1, 3));

        jBNuevoVEnd.setText("Nueva Empresa");
        jBNuevoVEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNuevoVEndActionPerformed(evt);
            }
        });
        jPBotones.add(jBNuevoVEnd);

        jBSalir.setText("Salir");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });
        jPBotones.add(jBSalir);

        jBModVenta.setText("Modificar Datos");
        jBModVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBModVentaActionPerformed(evt);
            }
        });
        jPBotones.add(jBModVenta);

        javax.swing.GroupLayout jXVentaImageLayout = new javax.swing.GroupLayout(jXVentaImage);
        jXVentaImage.setLayout(jXVentaImageLayout);
        jXVentaImageLayout.setHorizontalGroup(
            jXVentaImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );
        jXVentaImageLayout.setVerticalGroup(
            jXVentaImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 275, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("EMPRESAS");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXVentaImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXVentaImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTEmpresasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTEmpresasMouseClicked
        if(evt.getButton()==evt.BUTTON1){
            ModificarVend.setEnabled(true);
            Eliminar_Vend.setEnabled(true);
        }

        mostrarImagen(jXVentaImage,jTEmpresas);
    }//GEN-LAST:event_jTEmpresasMouseClicked

    private void jBNuevoVEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNuevoVEndActionPerformed
        new jDEmpresaForm(null, true, con).setVisible(true);
        PrepararTabla();
        mostrarImagen(jXVentaImage, jTEmpresas);
               
    }//GEN-LAST:event_jBNuevoVEndActionPerformed

    private void jBSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBSalirActionPerformed

    private void jBModVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBModVentaActionPerformed
        if(jTEmpresas.getSelectedRow()>(-1)){
            String cod= String.valueOf(jTEmpresas.getValueAt(jTEmpresas.getSelectedRow(), 0));
            System.out.println(cod);
            new jDEmpresaForm(null, true, con, cod).setVisible(true);
            PrepararTabla();
            mostrarImagen(jXVentaImage, jTEmpresas);
        }else{
            JOptionPane.showMessageDialog(null, "Por favor seleccione una Empresa", "Atencion", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jBModVentaActionPerformed

    private void PrepararTabla() {
          try {
                ResultSet r= null;
                r = con.Listar("*", "empresa", "");
                String titulos[]={"Numero","Nombre"};
                    m = (new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                        },
                        titulos
                    ) {
                        boolean[] canEdit = new boolean [] {
                            false, false
                        };

                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return canEdit [columnIndex];
                        }
                    });
                    jTEmpresas.setModel(m);
                    
                String Fila[]= new String[2];
           
                while(r.next()){
                    Fila[0]= r.getString("id_empresa");
                    Fila[1]= r.getString("nombre");
                    m.addRow(Fila);
                }
            } catch (Exception ex) {
               JOptionPane.showMessageDialog(null, ex.getMessage(),"Error 233",JOptionPane.ERROR_MESSAGE);
               
            }
    }

    public void mostrarImagen(JXImageView visor, JTable tabla){ 
        if( tabla.getSelectedRow()>-1){
            String codigo;
            codigo = (String) tabla.getValueAt(tabla.getSelectedRow(), 0);
            ResultSet rs = con.Listar("*", "empresa", "where id_empresa= '"+codigo+"'");

            try{
                rs.next();
                Blob blob = (Blob) rs.getBlob("imagen");
                byte [] data = blob.getBytes(1,(int) blob.length());
                BufferedImage img = null;
                img = ImageIO.read(new ByteArrayInputStream(data));
                visor.setImage(img);
            } catch ( SQLException ex){
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error 127", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex){
                JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error 129", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void ModificarVendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ModificarVendMouseClicked

    }//GEN-LAST:event_ModificarVendMouseClicked

    private void ModificarVendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarVendActionPerformed
        if(jTEmpresas.getSelectedRow()>(-1)){
            String cod= String.valueOf(jTEmpresas.getValueAt(jTEmpresas.getSelectedRow(), 0));
            this.dispose();
            new jDEmpresaForm(null, true,con, cod).setVisible(true);
        }else{
            JOptionPane.showMessageDialog(null, "Por favor seleccione una Empresa", "Atencion", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_ModificarVendActionPerformed

    private void Eliminar_VendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Eliminar_VendMouseClicked

    }//GEN-LAST:event_Eliminar_VendMouseClicked

    private void Eliminar_VendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Eliminar_VendActionPerformed
        if(jTEmpresas.getSelectedRow()>(-1)){
            int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro de que desea eliminar al vendedor?");
            if(resp==JOptionPane.YES_OPTION){ 
                String cod= String.valueOf(jTEmpresas.getValueAt(jTEmpresas.getSelectedRow(), 0));
                if(con.Bajas("empresa","where id_empresa='"+cod+"'")){
                    JOptionPane.showMessageDialog(null, "Empresa "+cod+" eliminada", "Exito", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(jPmenuEmpresa, "Error Al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                PrepararTabla();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Por favor seleccione un personal", "Atencion", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_Eliminar_VendActionPerformed

    /**
     * @param args the command line arguments
     */
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Eliminar_Vend;
    private javax.swing.JMenuItem ModificarVend;
    private javax.swing.JButton jBModVenta;
    private javax.swing.JButton jBNuevoVEnd;
    private javax.swing.JButton jBSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPBotones;
    private javax.swing.JPopupMenu jPmenuEmpresa;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTEmpresas;
    private org.jdesktop.swingx.JXImageView jXVentaImage;
    // End of variables declaration//GEN-END:variables
}
