/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jDnventa.java
 *
 * Created on 11/08/2012, 03:36:02 PM
 */
package frames;

import clases.DBConnect;
import clases.Numero_a_Letra;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.JOptionPane;
import java.net.URL;
import java.sql.Blob;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import net.sf.jasperreports.engine.JRResultSetDataSource;


/**
 *
 * @author Edgar
 */
public class jDVistaPresupuesto extends javax.swing.JDialog {
    Image imagen = getToolkit().getImage(getClass().getResource("/imagenes/global2.png"));
    DefaultTableModel m;

    private  Numero_a_Letra NL = new  Numero_a_Letra();
    DBConnect db;
    ResultSet r;
    int i=0;
    Date a;
    Integer total=0;
    Integer existencia=0;
    int nro =1;
    String venta;
    /** Creates new form jDnventa */
    public jDVistaPresupuesto(java.awt.Frame parent, boolean modal,DBConnect con,String cod_venta) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        db = con;
         this.venta=cod_venta;
        
        
        setIconImage(imagen);
        this.setTitle("Vista Venta");
        jTFecha.setEditable(false);
     
     
        jTNumFact.setEditable(false);
        jBGuardar.setEnabled(false);
        llenar_campos();
        preparartabla();
        nro = Integer.valueOf(venta);
        if(nro>0 && nro <10){
            jTNumFact.setText("0000000"+nro);
        }
        if(nro>=10 && nro<=99){
            jTNumFact.setText("000000"+nro);
        }
        if(nro>=100 && nro<=999){
            jTNumFact.setText("00000"+nro);
        }
        if(nro>=1000 && nro<=9999){
            jTNumFact.setText("0000"+nro);
        }
        if(nro>=10000 && nro<=99999){
            jTNumFact.setText("000"+nro);
        }
        if(nro>=100000 && nro<=999999){
            jTNumFact.setText("00"+nro);
        }
        if(nro>=1000000 && nro<=9999999){
            jTNumFact.setText("0"+nro);
        }
        if(nro>=10000000 && nro<=99999999){
            jTNumFact.setText(""+nro);
        }
        jtNombre.setEditable(false);
        int fil = jTProducto.getRowCount();
        int total=0;
        for(int c=0; c< fil;c++){
            int totalac= Integer.valueOf(String.valueOf(jTProducto.getValueAt(c, 3)));
            total += totalac;
            jTPrecTotal.setText(String.valueOf(total));
            this.total=total;
        }
        this.prepararCombosEncabezado();
    }
   

    
   
    private void preparartabla(){
        r=null;
        r= db.Listar("*", "vistapresupuesto", "where Id_presupuesto='"+jTNumFact.getText()+"'");
        DefaultTableModel m =(new javax.swing.table.DefaultTableModel(
                                new Object [][] {

                                },
                                new String [] {
                                    "Cod. Producto","Cantidad","Descripcion","Precio"
                                }
                            ) {
                                boolean[] canEdit = new boolean [] {
                                    false, false, false, false
                                };

                                public boolean isCellEditable(int rowIndex, int columnIndex) {
                                    return canEdit [columnIndex];
                                }
                            });
        jTProducto.setModel(m);
        String filas[]= new String[4];
        try {
            while(r.next()){
                filas[0]= r.getString("Cod_inter_producto");
                filas[1]= r.getString("Cantidad_presupuesto");
                filas[2]= r.getString("Nom_producto")+" "+r.getString("Marca_producto")+" "+r.getString("detalle_producto");
                filas[3]= String.valueOf(Integer.valueOf(r.getString("pre_n_presu"))*Integer.valueOf(r.getString("Cantidad_presupuesto")));
                
                m.addRow(filas);
            }
        } catch (SQLException ex) {
            Logger.getLogger(jDVistaPresupuesto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void llenar_campos(){
        r=null;
        r = db.Listar("*", "vistapresupueston", "where Id_presupuesto='"+venta+"'");
        jTNumFact.setText(venta);
        try {
            if(r.next()){
                jtNombre.setText(r.getString("Nom_cliente"));
                jTFecha.setText(r.getString("Fecha_crea_presupuesto"));
                jLidCliente.setText(r.getString("Id_cliente"));
                Date fecha = r.getDate("Fecha_de_entrega");
                jXDatePicker1.setDate(fecha);
                jTConfirmación.setText(r.getString("ConfirmacionOrden"));
                jTConfNro.setText(r.getString("ConfirmacionNro"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(jDVistaPresupuesto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Desde aquí se busca en base de datos cual fue la forma de pago y se muestra al usuario
    }
    private void prepararCombosEncabezado(){
        ResultSet r = null;
        r = db.Listar("*", "empresa", "");
        try{
            while(r.next()){
                jCodEncabezado.addItem(r.getString("id_empresa"));
                jNombreEncabezado.addItem(r.getString("nombre"));
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(rootPane, "Error al consultar la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
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

        jCodEncabezado = new javax.swing.JComboBox();
        jPfactura = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTNumFact = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTFecha = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jTConfNro = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTConfirmación = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jNombreEncabezado = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jPcantidad = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTPrecTotal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jBImprimirFactura = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jBGuardar = new javax.swing.JButton();
        jPbotones = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTProducto = new javax.swing.JTable();
        jPCliente = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLidCliente = new javax.swing.JLabel();
        jtNombre = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPfactura.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Presupuesto Nro:");

        jLabel3.setText("Fecha:");

        jLabel6.setText("Fecha de Entrega:");

        jXDatePicker1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXDatePicker1ActionPerformed(evt);
            }
        });

        jTConfNro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTConfNroKeyPressed(evt);
            }
        });

        jLabel7.setText("Confirmación de Orden:");

        jTConfirmación.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTConfirmaciónKeyPressed(evt);
            }
        });

        jLabel8.setText("Confirmación Nro°:");

        jNombreEncabezado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jNombreEncabezadoItemStateChanged(evt);
            }
        });

        jLabel9.setText("Encabezado:");

        javax.swing.GroupLayout jPfacturaLayout = new javax.swing.GroupLayout(jPfactura);
        jPfactura.setLayout(jPfacturaLayout);
        jPfacturaLayout.setHorizontalGroup(
            jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPfacturaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTNumFact, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jNombreEncabezado, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTConfNro)
                        .addComponent(jTConfirmación, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPfacturaLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(32, 32, 32)
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                    .addGroup(jPfacturaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(32, 32, 32)
                        .addComponent(jTFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPfacturaLayout.setVerticalGroup(
            jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPfacturaLayout.createSequentialGroup()
                .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPfacturaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jTNumFact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jTConfirmación, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel8)
                            .addComponent(jTConfNro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel9)
                            .addComponent(jNombreEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPfacturaLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPfacturaLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel6))
                    .addGroup(jPfacturaLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jTFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPfacturaLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel3)))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPcantidad.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel14.setText("Precio Total:");

        jTPrecTotal.setText("0");

        jLabel11.setText("Gs.");

        jBImprimirFactura.setText("Imprimir Presupuesto");
        jBImprimirFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImprimirFacturaActionPerformed(evt);
            }
        });

        jBCancelar.setText("Cancelar");
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });

        jBGuardar.setText("Guardar Cambios");
        jBGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPcantidadLayout = new javax.swing.GroupLayout(jPcantidad);
        jPcantidad.setLayout(jPcantidadLayout);
        jPcantidadLayout.setHorizontalGroup(
            jPcantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPcantidadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBImprimirFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTPrecTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addContainerGap())
        );
        jPcantidadLayout.setVerticalGroup(
            jPcantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPcantidadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPcantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPcantidadLayout.createSequentialGroup()
                        .addGroup(jPcantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTPrecTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel11)
                            .addComponent(jBImprimirFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jBGuardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPbotones.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPbotones.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jTProducto.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTProducto);

        jPCliente.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nombre del Cliente:");

        jLabel16.setText("ID. Cliente:");

        jLidCliente.setText("Nro");

        javax.swing.GroupLayout jPClienteLayout = new javax.swing.GroupLayout(jPCliente);
        jPCliente.setLayout(jPClienteLayout);
        jPClienteLayout.setHorizontalGroup(
            jPClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLidCliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPClienteLayout.setVerticalGroup(
            jPClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLidCliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPfactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPbotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPcantidad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(jPbotones, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(168, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPfactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
      
    }//GEN-LAST:event_formWindowClosed

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jBImprimirFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImprimirFacturaActionPerformed
    this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try{
           r=null;
           r=db.Listar("*", "vistapresupueston", "where Id_presupuesto='"+
                   jTNumFact.getText()+"' order by nom_producto ASC");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
                                           JOptionPane.ERROR_MESSAGE);
        }
        
        
        BufferedImage img = null;
        ResultSet rs = db.Listar("*", "empresa", "where id_empresa="+
                    jCodEncabezado.getSelectedItem().toString()+";");
        String datos = "";
        Blob blob = null;
        byte [] data = null;
        try{
            rs.next();
            datos = rs.getString("datos");
            blob = (Blob) rs.getBlob("imagen");
            data =  blob.getBytes(1,(int) blob.length());
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(rootPane,
                    "No se pudo obtener la imagen de la empresa", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        JasperReport reporte;
        JasperPrint reporte_view;
        JRResultSetDataSource jrRS = new JRResultSetDataSource(r);
        //Se crea un objeto HashMap
        HashMap parametros = new HashMap();
        parametros.clear();
        //parametros de entrada
        parametros.put("p_imagen",data);
        parametros.put("datos",datos);
        String Total= NL.Convertir(jTPrecTotal.getText(), true);
        String NumFactura = jTNumFact.getText();
        Integer SumaTotal = Integer.valueOf(jTPrecTotal.getText());
        parametros.put( "SUMATOTAL", SumaTotal );     
        try{
            //direccion del archivo JASPER
            URL  in = getClass().getClassLoader().getResource("reportes/PresupuestN.jasper");
            reporte = (JasperReport) JRLoader.loadObject( in );

            //----------------------------------- 
            reporte_view = JasperFillManager.fillReport(reporte, parametros, jrRS);
            JasperViewer ventana = new JasperViewer(reporte_view,false);
            ventana.setTitle("Vista Previa");
            ventana.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            ventana.setVisible(true);


        }catch (JRException E){
            JOptionPane.showMessageDialog(null, E.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jBImprimirFacturaActionPerformed

    private void jBGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBGuardarActionPerformed
        Date fechaEntrega = jXDatePicker1.getDate();
        if(fechaEntrega!=null){
            if(db.actualizarRegistro("presupuesto","ConfirmacionOrden='"+jTConfirmación.getText()+"' ,ConfirmacionNro='"+jTConfNro.getText()+"',Fecha_de_entrega='"+(fechaEntrega.getYear()+1900)+"/"+(fechaEntrega.getMonth()+1)+"/"+fechaEntrega.getDate()+"'","")){
                JOptionPane.showMessageDialog(rootPane, "Los cambios se guardaron exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Seleccione la fecha de la entrega por favor.", "Atención", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jBGuardarActionPerformed

    private void jXDatePicker1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXDatePicker1ActionPerformed
        jBGuardar.setEnabled(true);
    }//GEN-LAST:event_jXDatePicker1ActionPerformed

    private void jTConfirmaciónKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTConfirmaciónKeyPressed
        jBGuardar.setEnabled(true);
    }//GEN-LAST:event_jTConfirmaciónKeyPressed

    private void jTConfNroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTConfNroKeyPressed
        jBGuardar.setEnabled(true);
    }//GEN-LAST:event_jTConfNroKeyPressed

    private void jNombreEncabezadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jNombreEncabezadoItemStateChanged
        int seleccionado =  jNombreEncabezado.getSelectedIndex();
        jCodEncabezado.setSelectedIndex(seleccionado);
    }//GEN-LAST:event_jNombreEncabezadoItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                jDVistaPresupuesto dialog = new jDVistaPresupuesto(new javax.swing.JFrame(), true,null,"");
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
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBGuardar;
    private javax.swing.JButton jBImprimirFactura;
    private javax.swing.JComboBox jCodEncabezado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLidCliente;
    private javax.swing.JComboBox jNombreEncabezado;
    private javax.swing.JPanel jPCliente;
    private javax.swing.JPanel jPbotones;
    private javax.swing.JPanel jPcantidad;
    private javax.swing.JPanel jPfactura;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTConfNro;
    private javax.swing.JTextField jTConfirmación;
    private javax.swing.JTextField jTFecha;
    private javax.swing.JTextField jTNumFact;
    private javax.swing.JTextField jTPrecTotal;
    private javax.swing.JTable jTProducto;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private javax.swing.JTextField jtNombre;
    // End of variables declaration//GEN-END:variables
}
