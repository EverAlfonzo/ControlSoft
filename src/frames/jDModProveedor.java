/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jDnProveedor.java
 *
 * Created on 15/09/2012, 04:15:12 PM
 */
package frames;

import clases.DBConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Edgar
 */
public class jDModProveedor extends javax.swing.JDialog {
    DBConnect db;
    private int control=0;
    private int i=0;
    private String cod_prov;
    ResultSet r;
    /** Creates new form jDnProveedor */
    public jDModProveedor(java.awt.Frame parent, boolean modal,int a,String quien,DBConnect con) {
        super(parent, modal);
        initComponents();
        this.db = con;
        setLocationRelativeTo(null);
        setTitle("Modificar Proveedor");
        this.control=a;
        this.cod_prov=quien;
        actualizarCombo();
        llenar();
        this.jTRUC.setEditable(false);
    }
    
    public void actualizarCombo(){
        try {
            
            
            ResultSet r=null;
            r= db.Listar("*", "banco", "");
            while(r.next()){
                jXComboBox1.addItem(r.getString("nombre_banco")+" ,"+r.getString("Id_banco"));
            }
            AutoCompleteDecorator.decorate(jXComboBox1);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error 106",JOptionPane.ERROR_MESSAGE);
        }
    }   
    
    private void llenar(){
            ResultSet rs = null;
           
            
        
            r=null;
            r = db.Listar("*","proveedor","where Id_proveedor= '"+cod_prov+"'");
        try {
            
            
            r.next();
            rs= db.Listar("*", "banco", "where Id_banco ='"+r.getString("Id_banco")+"'");
            rs.next();
            
            jTRUC.setText(r.getString(1));
            jTContacto.setText(r.getString(2));
            jTMarca.setText(r.getString(3));
            jTPaís.setText(r.getString(4));
            JTNombre.setText(r.getString(5));
            jTEMail.setText(r.getString(6));
            jTTelefono.setText(r.getString(3));
            jTDireccion.setText(r.getString(4));
            jTnroCuenta.setText(r.getString("nro_cuenta"));
            
            String banco = String.valueOf(rs.getString("nombre_banco")+" ,"+rs.getString("Id_banco"));
            
            
            
            int hasta = jXComboBox1.getItemCount();
            
            
            
            String itemComp ="";
            for(int i = 0; i<hasta; i++){
                jXComboBox1.setSelectedIndex(i);
                itemComp = String.valueOf(jXComboBox1.getSelectedItem());
                if(itemComp.equals(banco)){
                    break;
                }
            }
            
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
     }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
      */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXLabel1 = new org.jdesktop.swingx.JXLabel();
        jXLabel2 = new org.jdesktop.swingx.JXLabel();
        jBAceptar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jPformulario = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTRUC = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        JTNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTContacto = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTMarca = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTPaís = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTEMail = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTTelefono = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTDireccion = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jXComboBox1 = new org.jdesktop.swingx.JXComboBox();
        jLabel11 = new javax.swing.JLabel();
        jTnroCuenta = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jXLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/clipboard.png"))); // NOI18N

        jXLabel2.setText("Modificar Proveedor");

        jBAceptar.setText("Aceptar");
        jBAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAceptarActionPerformed(evt);
            }
        });

        jBCancelar.setText("Cancelar");
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });

        jLabel3.setText(" RUC :");

        jLabel1.setText("Nombre :");

        jLabel6.setText("Contacto:");

        jLabel7.setText("Marca:");

        jLabel8.setText("País:");

        jLabel9.setText("E-Mail:");

        jLabel4.setText("Telefono :");

        jLabel5.setText("Direccion :");

        jLabel10.setText("Banco:");

        jLabel11.setText("Nro. de Cuenta:");

        jButton1.setText("+");
        jButton1.setToolTipText("Agregar Banco");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPformularioLayout = new javax.swing.GroupLayout(jPformulario);
        jPformulario.setLayout(jPformularioLayout);
        jPformularioLayout.setHorizontalGroup(
            jPformularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPformularioLayout.createSequentialGroup()
                .addGroup(jPformularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPformularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTnroCuenta)
                    .addGroup(jPformularioLayout.createSequentialGroup()
                        .addComponent(jXComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jTDireccion)
                    .addComponent(jTTelefono)
                    .addComponent(jTEMail)
                    .addComponent(jTPaís)
                    .addComponent(jTMarca)
                    .addComponent(jTContacto)
                    .addComponent(JTNombre)
                    .addComponent(jTRUC))
                .addGap(2, 2, 2))
        );
        jPformularioLayout.setVerticalGroup(
            jPformularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPformularioLayout.createSequentialGroup()
                .addGroup(jPformularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPformularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JTNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPformularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPformularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPformularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTPaís, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPformularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTEMail, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPformularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPformularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPformularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPformularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jXComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1)))
                .addGap(9, 9, 9)
                .addGroup(jPformularioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTnroCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jXLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jBAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 223, Short.MAX_VALUE)
                        .addComponent(jBCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jPformulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jXLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPformulario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBCancelar)
                    .addComponent(jBAceptar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAceptarActionPerformed
        // TODO add your handling code here:
        int i;
            i =String.valueOf( jXComboBox1.getSelectedItem()).indexOf(",");
            String banco = String.valueOf(jXComboBox1.getSelectedItem()).substring(i+1);
        if(!jTRUC.equals("")){
            if(!JTNombre.equals("")){
                if(db.actualizarRegistro("Proveedor","Nom_proveedor='"+JTNombre.getText()+"',Tel_proveedor='"+jTTelefono.getText()+"',Direc_proveedor='"+jTDireccion.getText()+"',Contacto_proveedor='"+jTContacto.getText()+"',"
                        + "Marca_proveedor='"+jTMarca.getText()+"',Pais_proveedor='"+jTPaís.getText()+"',`E-mail_proveedor`='"+jTEMail.getText()+"'"
                        +",Id_banco='"+banco+"',nro_cuenta='"+jTnroCuenta.getText()+"'", "where Id_proveedor='"+jTRUC.getText()+"'")){
                    JOptionPane.showMessageDialog(rootPane, "Exito", "Los cambios se han guardado correctamente", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Error 256", "Ha ocurrido un error inesperado y no se han guardado los cambios. Por favor pongase en contacto"
                            + " con el administrador del sistema", JOptionPane.ERROR_MESSAGE);
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "El campo Nombre no debe estar vacio","Atencion",JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null, "El campo Ruc no debe estar vacio","Atencion",JOptionPane.ERROR_MESSAGE);
        }
}//GEN-LAST:event_jBAceptarActionPerformed

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        
        this.dispose();
          
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new JDNewBank(null, true, db).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                jDModProveedor dialog = new jDModProveedor(new javax.swing.JFrame(), true,0,"",null);
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
    private javax.swing.JTextField JTNombre;
    private javax.swing.JButton jBAceptar;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPformulario;
    private javax.swing.JTextField jTContacto;
    private javax.swing.JTextField jTDireccion;
    private javax.swing.JTextField jTEMail;
    private javax.swing.JTextField jTMarca;
    private javax.swing.JTextField jTPaís;
    private javax.swing.JTextField jTRUC;
    private javax.swing.JTextField jTTelefono;
    private javax.swing.JTextField jTnroCuenta;
    private org.jdesktop.swingx.JXComboBox jXComboBox1;
    private org.jdesktop.swingx.JXLabel jXLabel1;
    private org.jdesktop.swingx.JXLabel jXLabel2;
    // End of variables declaration//GEN-END:variables
}
