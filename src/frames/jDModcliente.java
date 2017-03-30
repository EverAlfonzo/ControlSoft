/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jDnuevocliente.java
 *
 * Created on 18/08/2012, 02:36:35 PM
 */
package frames;

import clases.DBConnect;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Edgar
 */
public class jDModcliente extends javax.swing.JDialog {
    DBConnect db;
    int i=0;
    String cod;
    ResultSet r;
Image imagen = getToolkit().getImage(getClass().getResource("/imagenes/global2.png"));

    /** Creates new form jDnuevocliente */
    public jDModcliente(java.awt.Frame parent, boolean modal,DBConnect con,String a) {
        super(parent, modal);
        initComponents();
        this.setIconImage(imagen);
        this.setTitle("Nuevo Cliente");
        this.setLocationRelativeTo(null);
        db= con;
        setIconImage(imagen);
        cod=a;
        this.jTRUC.setEditable(false);
        llenar();
    }
    
    
    public void llenar(){
        
            r=null;
            r = db.Listar("*","cliente","where Id_cliente= '"+cod+"'");
        try {
            r.next();
            jTRUC.setText(r.getString(1));
            JTNombre.setText(r.getString(2));
            jTDireccion.setText(r.getString(3));
            jTTelefono.setText(r.getString(4));
            jTDireccionEnv.setText(r.getString(5));
            jTPais.setText(r.getString(6));
            jTEmail.setText(r.getString(8));
            jTContacto.setText(r.getString(9));
            jCBTipCliente.setSelectedIndex(Integer.valueOf(r.getString(10))-1);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
     }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXLabel1 = new org.jdesktop.swingx.JXLabel();
        jXLabel2 = new org.jdesktop.swingx.JXLabel();
        jBAceptar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jPformulario1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTRUC = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        JTNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTDireccion = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTTelefono = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTDireccionEnv = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTPais = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTEmail = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTContacto = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jCBTipCliente = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jXLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Clientes_32x32.png"))); // NOI18N

        jXLabel2.setText("Modificar Cliente");

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

        jPformulario1.setBorder(javax.swing.BorderFactory.createTitledBorder("Nuevo Cliente"));
        jPformulario1.setLayout(new java.awt.GridLayout(9, 2, 10, 15));

        jLabel6.setText("C.I O RUC :");
        jPformulario1.add(jLabel6);
        jPformulario1.add(jTRUC);

        jLabel2.setText("Nombre :");
        jPformulario1.add(jLabel2);
        jPformulario1.add(JTNombre);

        jLabel7.setText("Direccion :");
        jPformulario1.add(jLabel7);
        jPformulario1.add(jTDireccion);

        jLabel8.setText("Telefono :");
        jPformulario1.add(jLabel8);
        jPformulario1.add(jTTelefono);

        jLabel9.setText("Direccion de Envío:");
        jPformulario1.add(jLabel9);
        jPformulario1.add(jTDireccionEnv);

        jLabel10.setText("País:");
        jPformulario1.add(jLabel10);
        jPformulario1.add(jTPais);

        jLabel11.setText("E-Mail:");
        jPformulario1.add(jLabel11);
        jPformulario1.add(jTEmail);

        jLabel12.setText("Contacto :");
        jPformulario1.add(jLabel12);
        jPformulario1.add(jTContacto);

        jLabel13.setText("Tipo Cliente:");
        jPformulario1.add(jLabel13);

        jCBTipCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4" }));
        jPformulario1.add(jCBTipCliente);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jXLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jXLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBCancelar))
                    .addComponent(jPformulario1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jXLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jXLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPformulario1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBAceptar)
                    .addComponent(jBCancelar))
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jBAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAceptarActionPerformed
        if(!jTRUC.equals("")){
            if(!JTNombre.equals("")){
                db.actualizarRegistro("cliente", "Nom_cliente='"+JTNombre.getText()+"',Tel_cliente='"+jTTelefono.getText()+"',Direc_cliente='"+jTDireccion.getText()+"',Direc_env_cliente='"+jTDireccionEnv.getText()+"',Pais='"+jTPais.getText()+"',`e-mail_cliente`='"+jTEmail.getText()+"',Contacto_cliente='"+jTContacto.getText()+"',tipo_cliente='"+jCBTipCliente.getSelectedItem()+"'", "where Id_cliente='"+cod+"'");
                this.dispose();

            }else{
                JOptionPane.showMessageDialog(null, "El campo Nombre no debe estar vacio","Atencion",JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(null, "El campo Ruc no debe estar vacio","Atencion",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jBAceptarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                jDModcliente dialog = new jDModcliente(null, true,null,"");
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
    private javax.swing.JComboBox jCBTipCliente;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPformulario1;
    private javax.swing.JTextField jTContacto;
    private javax.swing.JTextField jTDireccion;
    private javax.swing.JTextField jTDireccionEnv;
    private javax.swing.JTextField jTEmail;
    private javax.swing.JTextField jTPais;
    private javax.swing.JTextField jTRUC;
    private javax.swing.JTextField jTTelefono;
    private org.jdesktop.swingx.JXLabel jXLabel1;
    private org.jdesktop.swingx.JXLabel jXLabel2;
    // End of variables declaration//GEN-END:variables
}
