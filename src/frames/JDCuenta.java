/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JDCuenta.java
 *
 * Created on 13/09/2012, 03:52:03 PM
 */
package frames;

import clases.DBConnect;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.net.URL;


/**
 *
 * @author Edgar
 */
public class JDCuenta extends javax.swing.JDialog {
    Image imagen = getToolkit().getImage(getClass().getResource("/imagenes/global2.png"));
    private String Usuario;
    private String Password;
    private String Host;
    private String Puerto;
    private String Database;
    private String cod_cliente;
    ResultSet r;
    DBConnect db;
    DefaultTableModel m;
    private Integer costos=0;
    private Integer entrega=0;
    Double Entregato=0.0;
    /** Creates new form JDCuenta */
    public JDCuenta(java.awt.Frame parent, boolean modal,DBConnect con,String id_cliente) {
        super(parent, modal);
        initComponents();
        setTitle("Cuenta de Cliente");
        setLocationRelativeTo(null);
        setIconImage(imagen);
        db= con;
        this.Usuario=con.getUser();
        this.Password=con.getUser();
        this.cod_cliente=id_cliente;
        actualizarCombo();
        preparartabla();
        try{
            r=null;
            r=db.Listar("*", "cliente", "where Id_cliente='"+cod_cliente+"'");
            if(r.next())
            jtcliente.setText(r.getString("Nom_cliente"));
            
        }catch(Exception e ){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", WIDTH);
        }
        calculartotal();
        jtcostotal.setText(String.valueOf(costos));
        calcularentregasald();
        
         
    }
    
    public void actualizarCombo(){
       ResultSet rs = null;
        try {
            
            rs= db.Listar("Id_cuenta", "cuenta_cliente", " where Cliente_Id_cliente = '"+cod_cliente+"' and Estado_Cuenta = 'no pagado'");
            while(rs.next()){
                jCFactura.addItem(rs.getString("Id_cuenta"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void preparartabla() {
         r=null;
         r=db.Listar("*", "vista_cuenta", "where Id_cuenta = '"+jCFactura.getSelectedItem()+"'");
            m =(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "Cod. Producto", "Descripcion", "Cantidad Vendida", "Fecha Cuenta", "Costo"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            jtcuenta.setModel(m);
            String fila[] = new String[6];
            try {
                
            while(r.next()){
                fila[0]=r.getString("Cod_inter_producto");
                fila[1]=r.getString("Nom_producto")+" "+r.getString("Marca_Producto")+" "+r.getString("detalle_producto");
                fila[2]=r.getString("Cant_cuenta");
                fila[3]=r.getString("Fecha_cuenta");
                fila[4]=r.getString("Pre_venta");
                 m.addRow(fila);
                 
                 
            }
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
            
            jtcliente.setEditable(false);
    } 
    public void calculartotal(){
        
        Integer filas=jtcuenta.getRowCount();
        for(int j=0;j<filas;j++){
            costos+=Integer.valueOf(String.valueOf(jtcuenta.getValueAt(j, 4)));
        }
    }
    public void calcularentregasald(){
        ResultSet rs = null;
        try{
            Entregato= 0.0;
            
            rs= db.Listar("Pagado", "cuenta_cliente", "where Id_cuenta = '"+jCFactura.getSelectedItem()+"'");
            while(rs.next()){
                Entregato+=Double.valueOf(String.valueOf(rs.getString(1)));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        jtsaldo.setText(String.valueOf(costos-Entregato));
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtcuenta = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtcostotal = new javax.swing.JTextField();
        jtEntrega = new javax.swing.JTextField();
        jtsaldo = new javax.swing.JTextField();
        jbPagar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jbaceptar = new javax.swing.JButton();
        jbimprimir = new javax.swing.JButton();
        jbcancelar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jtcliente = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jCFactura = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jtcuenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Producto", "Descripcion", "Cantidad Vendida", "Fecha Compra", "Costo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtcuenta);

        jLabel1.setText("Costo Total:");

        jLabel2.setText("Entrega:");

        jLabel3.setText("Saldo:");

        jtcostotal.setText("0");

        jtEntrega.setText("0");

        jtsaldo.setText("0");

        jbPagar.setText("Pagar");
        jbPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPagarActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jbaceptar.setText("Aceptar");
        jbaceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbaceptarActionPerformed(evt);
            }
        });
        jPanel1.add(jbaceptar);

        jbimprimir.setText("Imprimir");
        jbimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbimprimirActionPerformed(evt);
            }
        });
        jPanel1.add(jbimprimir);

        jbcancelar.setText("Cancelar");
        jbcancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbcancelarActionPerformed(evt);
            }
        });
        jPanel1.add(jbcancelar);

        jLabel4.setText("Cliente:");

        jLabel6.setText("Gs.");

        jLabel7.setText("Gs.");

        jLabel8.setText("Gs.");

        jLabel5.setText("Seleccione la Factura a Cobrar: ");

        jCFactura.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCFacturaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(45, 45, 45)
                        .addComponent(jtcliente)
                        .addGap(24, 24, 24)
                        .addComponent(jbPagar)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtsaldo)
                                    .addComponent(jtEntrega))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtcostotal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addGap(1, 1, 1))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jCFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtcliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel1)
                    .addComponent(jtcostotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jbPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtsaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPagarActionPerformed
        Integer entrega=Integer.valueOf(jtEntrega.getText());
        jtsaldo.setText(String.valueOf(Integer.valueOf(jtsaldo.getText())-entrega));
        Integer saldo= Integer.valueOf(jtsaldo.getText());
        if(saldo<0){
           JOptionPane.showMessageDialog(null, "La entrega no debe ser mayor al saldo, por favor cambie el valor de la misma", "Atencion", JOptionPane.WARNING_MESSAGE);
       }

    }//GEN-LAST:event_jbPagarActionPerformed

    private void jbaceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbaceptarActionPerformed
       Integer saldo= Integer.valueOf(jtsaldo.getText());
       if(saldo>0){
            db.actualizarRegistro("cuenta_cliente", "Pagado="+ String.valueOf(Integer.valueOf(jtcostotal.getText())-Integer.valueOf(jtsaldo.getText())), "where Id_cuenta ='"+jCFactura.getSelectedItem()+"'");
            db.actualizarRegistro("cliente", "Deuda_cliente="+jtsaldo.getText(), "where Id_cliente='"+cod_cliente+"'");
        JOptionPane.showMessageDialog(null, " La cuenta se ha actualizado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
       }
       if(saldo==0){
           Integer filas = jtcuenta.getRowCount();
           db.actualizarRegistro("cuenta_cliente", "Pagado="+ String.valueOf(Integer.valueOf(jtcostotal.getText())-Integer.valueOf(jtsaldo.getText()))+", Estado_Cuenta = 'pagado' ", "where Id_cuenta ='"+jCFactura.getSelectedItem()+"'");
           db.actualizarRegistro("cliente", "Deuda_cliente="+jtsaldo.getText(), "where Id_cliente='"+cod_cliente+"'");
           JOptionPane.showMessageDialog(null, "El cliente "+jtcliente.getText()+" ha saldado todas sus cuentas", "Exito", JOptionPane.INFORMATION_MESSAGE);
           
       }
       if(saldo<0){
           JOptionPane.showMessageDialog(null, "La entrega no debe ser mayor al saldo, por favor cambie el valor de la misma", "Atencion", JOptionPane.WARNING_MESSAGE);
       }
       preparartabla();
    }//GEN-LAST:event_jbaceptarActionPerformed

    private void jbcancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbcancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbcancelarActionPerformed

    private void jbimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbimprimirActionPerformed
        
       try{
           r=null;
           r=db.Listar("*", "vistacuenta1", "where Id_cliente='"+cod_cliente+"'");
          

           
        } catch (Exception ex) {
             //Logger.getLogger(ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }

        JRResultSetDataSource jrRS = new JRResultSetDataSource(r);
        HashMap parameters = new HashMap();

        try{
            URL urlMaestro = getClass().getClassLoader().getResource("reportes/Factura_cuenta.jasper");
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
    }//GEN-LAST:event_jbimprimirActionPerformed

    private void jCFacturaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCFacturaItemStateChanged
        this.costos = 0;
        this.entrega = 0;
        this.Entregato=0.0;
        preparartabla();
        calculartotal();
        calcularentregasald();
    }//GEN-LAST:event_jCFacturaItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                JDCuenta dialog = new JDCuenta(new javax.swing.JFrame(), true,null,"");
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
    private javax.swing.JComboBox jCFactura;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbPagar;
    private javax.swing.JButton jbaceptar;
    private javax.swing.JButton jbcancelar;
    private javax.swing.JButton jbimprimir;
    private javax.swing.JTextField jtEntrega;
    private javax.swing.JTextField jtcliente;
    private javax.swing.JTextField jtcostotal;
    private javax.swing.JTable jtcuenta;
    private javax.swing.JTextField jtsaldo;
    // End of variables declaration//GEN-END:variables
}
