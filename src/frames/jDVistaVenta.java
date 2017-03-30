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
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.JOptionPane;
import java.net.URL;
import java.util.Map;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.lang.*;
import net.sf.jasperreports.engine.JRResultSetDataSource;


/**
 *
 * @author Edgar
 */
public class jDVistaVenta extends javax.swing.JDialog {
Image imagen = getToolkit().getImage(getClass().getResource("/imagenes/global2.png"));
DefaultTableModel m;

private  Numero_a_Letra NL = new  Numero_a_Letra();
private  String vendedor;
private  String password;
DBConnect db;
ResultSet r;
int i=0;
Date a;
Integer total=0;
Integer utilidad=0;
Integer existencia=0;
int nro =1;
String venta;
/** Creates new form jDnventa */
    public jDVistaVenta(java.awt.Frame parent, boolean modal,DBConnect con,String cod_venta) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        db = con;
       
         this.venta=cod_venta;
        
        
        setIconImage(imagen);
        this.setTitle("Vista Venta");
        this.vendedor=con.getUser();

     
        
        
        jTFecha.setEditable(false);
     
     
        jTNumFact.setEditable(false);
     
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
        int utilidad=0;
        for(int c=0; c< fil;c++){
            int totalac= Integer.valueOf(String.valueOf(jTProducto.getValueAt(c, 4)));
            int utilidadact=Integer.valueOf(String.valueOf(jTProducto.getValueAt(c, 5)));
            total += totalac;
            utilidad+=utilidadact;
            jTPrecTotal.setText(String.valueOf(total));
            jTPrecTotal1.setText(String.valueOf(utilidad));
            this.total=total;
            this.utilidad=utilidad;
        }
        if(jTTipoFactura.getText().equals("Contado")){
            jButton1.setVisible(false);
        }
        
    }
    /* public jDVistaVenta(java.awt.Frame parent, boolean modal,String host,String vend,String pass,String puerto,String database,String cod_venta) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        db = new DBConnect();
        db.conectado(host,vend,pass,puerto,database);
        jtNombre.setEditable(false);
        this.venta=cod_venta;
        setIconImage(imagen);
        this.setTitle("Vista Venta");
        this.vendedor=vend;
        this.password=pass;
        this.Host=host;
        this.Puerto=puerto;
        this.Database=database;
        jTVendedor.setText(vend);
        jTVendedor.setEditable(false);
        i=1;

        
        a = new Date();
       
        jTFecha.setEditable(false);
        
        
        jTNumFact.setEditable(false);
        
        jtnumvendedor.setEditable(false);
        llenar_campos();
        preparartabla();
        ResultSet nrovendr = db.Listar("*", "vendedor", "where Alias_vendedor='"+jTVendedor.getText()+"'");
        try {
            if(nrovendr.next()){
                jtnumvendedor.setText(nrovendr.getString(1));
                jTVendedor.setText(nrovendr.getString(2)+" "+nrovendr.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(jDnventa.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            
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

        
       
        
        
        int fil = jTProducto.getRowCount();
        int total=0;
        for(int c=0; c< fil;c++){
            int totalac= Integer.valueOf(String.valueOf(jTProducto.getValueAt(c, 3)));
            total += totalac;
            jTPrecTotal.setText(String.valueOf(total));
            this.total=total;
        }
    }
     */

    
    public String quitarPuntos(String campo){
        String ahora="";
        for(int k =0; k<campo.length();k++){
            if(campo.charAt(k)!='.'){
                ahora =ahora + campo.charAt(k);
            }
        }
        return (ahora);
        
    }
    
   
    public void preparartabla(){
        r=null;
        r= db.Listar("*", "vistafactura", "where Id_venta='"+jTNumFact.getText()+"'");
        DefaultTableModel m =(new javax.swing.table.DefaultTableModel(
                                new Object [][] {

                                },
                                new String [] {
                                    "Cod. Producto","Cantidad","Descripcion","Precio","Sub-Total","Utilidad Parcial"
                                }
                            ) {
                                boolean[] canEdit = new boolean [] {
                                    false, false, false, false,false,false
                                };

                                public boolean isCellEditable(int rowIndex, int columnIndex) {
                                    return canEdit [columnIndex];
                                }
                            });
        jTProducto.setModel(m);
        String filas[]= new String[6];
        try {
            while(r.next()){
                filas[0]= r.getString("Cod_inter_producto");
                ResultSet rCostoProd = db.Listar("pre_compra", "producto", "where Cod_inter_producto='"+filas[0]+"'");
                rCostoProd.next();
                filas[1]= r.getString("Cant_vendida");
                filas[2]= r.getString("Nom_producto")+" "+r.getString("Marca_producto")+" "+r.getString("detalle_producto");
                filas[3]=r.getString("Pre_n_venta");
                filas[4]= String.valueOf(Integer.valueOf(filas[3])*Integer.valueOf(filas[1]));
                filas[5]= String.valueOf(Integer.valueOf(filas[4])-Integer.valueOf(filas[1])*Integer.valueOf(quitarPuntos(rCostoProd.getString(1))));
                m.addRow(filas);
            }
         
        } catch (SQLException ex) {
            Logger.getLogger(jDVistaVenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void llenar_campos(){
        r=null;
        r = db.Listar("*", "vistafactura", "where Id_venta='"+venta+"'");
        jTNumFact.setText(venta);
        try {
            if(r.next()){
                jtNombre.setText(r.getString("Nom_cliente"));
                jTTipoFactura.setText(r.getString("Tipo_venta"));
                jTFecha.setText(r.getString("Fecha_venta"));
                jLidCliente.setText(r.getString("Id_cliente"));
                jTextArea1.setText(r.getString("Observacion"));
            }
            jTextArea1.setEditable(false);
        } catch (SQLException ex) {
            Logger.getLogger(jDVistaVenta.class.getName()).log(Level.SEVERE, null, ex);
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

        jPfactura = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTNumFact = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTFecha = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jlcondesc = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTTipoFactura = new javax.swing.JTextField();
        jPcantidad = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTPrecTotal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jBImprimirFactura = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTPrecTotal1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
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

        jLabel2.setText("Factura N°:");

        jLabel3.setText("Fecha:");

        jLabel5.setText("Obs.:");

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextArea1.setDragEnabled(true);
        jTextArea1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextArea1CaretUpdate(evt);
            }
        });
        jTextArea1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea1KeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea1);

        jlcondesc.setText("100");

        jLabel6.setText("Tipo de Factura:");

        javax.swing.GroupLayout jPfacturaLayout = new javax.swing.GroupLayout(jPfactura);
        jPfactura.setLayout(jPfacturaLayout);
        jPfacturaLayout.setHorizontalGroup(
            jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPfacturaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPfacturaLayout.createSequentialGroup()
                        .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jlcondesc))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPfacturaLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(24, 24, 24)
                        .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTTipoFactura, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(jTNumFact, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addGap(51, 51, 51)
                        .addComponent(jTFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPfacturaLayout.setVerticalGroup(
            jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPfacturaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTNumFact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTTipoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jlcondesc, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5))
                .addContainerGap())
        );

        jPcantidad.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel14.setText("Precio Total:");

        jTPrecTotal.setText("0");

        jLabel11.setText("Gs.");

        jBImprimirFactura.setText("Imprimir Factura");
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

        jLabel15.setText("Utilidad Total:");

        jLabel12.setText("Gs.");

        jTPrecTotal1.setText("0");

        jButton1.setText("Registro de Pagos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPcantidadLayout = new javax.swing.GroupLayout(jPcantidad);
        jPcantidad.setLayout(jPcantidadLayout);
        jPcantidadLayout.setHorizontalGroup(
            jPcantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPcantidadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBImprimirFactura)
                .addGap(18, 18, 18)
                .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(179, 179, 179)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTPrecTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTPrecTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addContainerGap())
        );
        jPcantidadLayout.setVerticalGroup(
            jPcantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPcantidadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPcantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15)
                    .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTPrecTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jBImprimirFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14)
                    .addComponent(jTPrecTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLidCliente))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPcantidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPfactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4)
                        .addGap(18, 18, 18)
                        .addComponent(jPbotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(124, 124, 124)
                                .addComponent(jPbotones, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPfactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        r=db.Listar("*", "vistafactura", "where Id_venta='"+jTNumFact.getText()+"'");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
    }

    JasperReport reporte;
    JasperPrint reporte_view;
    JRResultSetDataSource jrRS = new JRResultSetDataSource(r);
     //Se crea un objeto HashMap
          HashMap parametros = new HashMap();
          parametros.clear();
          //parametros de entrada
          String Total= NL.Convertir(jTPrecTotal.getText(), true);
          String NumFactura = jTNumFact.getText();
          Integer SumaTotal = Integer.valueOf(jTPrecTotal.getText());
          parametros.put( "NumFactura",NumFactura );
          parametros.put( "NumLiteral", Total );          
          parametros.put( "SumaTotal", SumaTotal );
     try{
         //direccion del archivo JASPER
         URL in = null;
           
         if("Contado".equals(jTTipoFactura.getText())){
            in = this.getClass().getClassLoader().getResource("reportes/Factura1.jasper");
         }else{
            in = this.getClass().getClassLoader().getResource("reportes/Factura2.jasper");
         }
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

    private void jTextArea1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextArea1CaretUpdate
        
        String desc = jTextArea1.getText();
        if(Integer.valueOf(jlcondesc.getText())>0){
            jlcondesc.setText(String.valueOf(100-desc.length()));
        }
        if (Integer.valueOf(jlcondesc.getText())<=0){
            jTextArea1.setEditable(false);
        }
        jlcondesc.setText(String.valueOf(100-desc.length()));
    }//GEN-LAST:event_jTextArea1CaretUpdate

    private void jTextArea1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea1KeyPressed
          if (evt.getKeyCode() == evt.VK_BACK_SPACE){

                    jTextArea1.setEditable(true);
                    String desc = jTextArea1.getText();
                    if(desc.length()==100){
                        jlcondesc.setText(String.valueOf(101-desc.length()));
                    }else{
                        jlcondesc.setText(String.valueOf(100-desc.length()));
                    }
            }
          if(evt.getKeyCode()== evt.VK_DELETE){
                    String desc = jTextArea1.getText();
                    jlcondesc.setText(String.valueOf(100-desc.length()));
                    jTextArea1.setEditable(true);
                   
          }
                    String desc = jTextArea1.getText();
                    jlcondesc.setText(String.valueOf(100-desc.length()));
    }//GEN-LAST:event_jTextArea1KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int id = Integer.valueOf( this.jTNumFact.getText());
        new jDRecibosPagos(null, true,db,id,jTPrecTotal.getText()).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                jDVistaVenta dialog = new jDVistaVenta(new javax.swing.JFrame(), true,null,"");
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
    private javax.swing.JButton jBImprimirFactura;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLidCliente;
    private javax.swing.JPanel jPCliente;
    private javax.swing.JPanel jPbotones;
    private javax.swing.JPanel jPcantidad;
    private javax.swing.JPanel jPfactura;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTFecha;
    private javax.swing.JTextField jTNumFact;
    private javax.swing.JTextField jTPrecTotal;
    private javax.swing.JTextField jTPrecTotal1;
    private javax.swing.JTable jTProducto;
    private javax.swing.JTextField jTTipoFactura;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel jlcondesc;
    private javax.swing.JTextField jtNombre;
    // End of variables declaration//GEN-END:variables
}
