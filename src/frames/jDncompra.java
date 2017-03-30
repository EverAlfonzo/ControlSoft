/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jDncompra.java
 *
 * Created on 11/08/2012, 03:36:21 PM
 */
package frames;

import clases.DBConnect;
import java.awt.Image;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Edgar
 */
public class jDncompra extends javax.swing.JDialog {
   
    private DefaultTableModel m;
    private DBConnect db=null;
    private int i=0;
    private ResultSet r;
    private Date a;
    private Integer total=0;
    private Integer nro ;
    private Image imagen = getToolkit().getImage(getClass().getResource("/imagenes/global2.png"));
    jDProductos prod;
    /** Creates new form jDncompra */
    public jDncompra(java.awt.Frame parent, boolean modal,DBConnect con) {
        super(parent, modal);
        initComponents();
        this.setIconImage(imagen);
        db = con;
        setLocationRelativeTo(null);
        setTitle("Nueva Compra");
        String titulos[] = {"Cod. Producto","Número de Fábrica","Código Original","Cantidad","Descripcion","Precio Unitario","Sub-total"};
        m= (DefaultTableModel) jTProducto.getModel();
        m.setColumnIdentifiers(titulos);
        this.jTFecha.setEditable(false);
        this.jBRegCompra.setEnabled(false);
        ResultSet nrovent=db.Listar("Id_compra", "compra", "order by Id_compra ASC");
        try {
            if(nrovent.last()){
                nro = Integer.valueOf(nrovent.getString(1));
                jTNumCompra.setText(String.valueOf(nro+1));
            }else{
                nro=1;
                jTNumCompra.setText(String.valueOf(nro));
            }
            
        }catch(SQLException e){
            Logger.getLogger(jDnventa.class.getName()).log(Level.SEVERE, null, e);
        }
        a = new Date();
        jTFecha.setText(String.valueOf(a.getDate())+"/"+String.valueOf(a.getMonth()+1)+"/"+String.valueOf(a.getYear()+1900));
        prod = new jDProductos(null, true, db,1);
        jBImprimirFactura.setEnabled(false);
    }
   /* public jDncompra(java.awt.Frame parent, boolean modal,String host,String Receptor,String pass,String puerto,String database) {
        super(parent, modal);
        initComponents();
        receptor=Receptor;
        password=pass;
        this.Host=host;
        this.Puerto=puerto;
        this.Database=database;
        db = new DBConnect();
        this.setIconImage(imagen);
        db.conectado(Host, receptor, password, Puerto, Database);
        setLocationRelativeTo(null);
        setTitle("Nueva Compra");
        String titulos[] = {"Cod. Producto","Cantidad","Descripcion","Precio"};
        m= (DefaultTableModel) jTProducto.getModel();
        m.setColumnIdentifiers(titulos);
        this.jTcantdisponible.setEditable(false);
        this.jtPreciocompra.setEditable(false);
        this.jTNomProducto.setEditable(false);
        this.jTFecha.setEditable(false);
        this.jTreceptor.setEditable(false);
        this.jtnumreceptor.setEditable(false);
        this.jTNumCompra.setEditable(false);
        ResultSet nrovent=db.Listar("Id_compra", "compra", "order by Id_compra ASC");
        try {
            if(nrovent.last()){
                nro = Integer.valueOf(nrovent.getString(1)+1);
                jTNumCompra.setText(String.valueOf(nro));
            }else{
                nro=1;
                jTNumCompra.setText(String.valueOf(nro));
            }
            
        }catch(SQLException e){
            Logger.getLogger(jDnventa.class.getName()).log(Level.SEVERE, null, e);
        }
        this.i=1;
        a = new Date();
        jTFecha.setText(String.valueOf(a.getDate())+"/"+String.valueOf(a.getMonth()+1)+"/"+String.valueOf(a.getYear()+1900));
        jTreceptor.setText(this.receptor);
        ResultSet nrovendr = db.Listar("*", "receptor", "where Alias_vendedor='"+jTreceptor.getText()+"'");
        try {
            if(nrovendr.next()){
                jtnumreceptor.setText(nrovendr.getString(1));
                jTreceptor.setText(nrovendr.getString(2)+" "+nrovendr.getString(3));
            }
        } catch (SQLException ex) {
            Logger.getLogger(jDnventa.class.getName()).log(Level.SEVERE, null, ex);
        }
        jBImprimirFactura.setEnabled(false);
        actualizarCombopro();
        calcularexistencia();
    }*/
   
    
   
            
         
     
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMBorrar = new javax.swing.JMenuItem();
        jPcompra = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTNumCompra = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTFecha = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTProducto = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPbotones = new javax.swing.JPanel();
        jBRegCompra = new javax.swing.JButton();
        jBImprimirFactura = new javax.swing.JButton();
        jPcantidad = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTPrecTotal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jBBuscarProducto = new javax.swing.JButton();

        jMBorrar.setText("Quitar de la Lista");
        jMBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMBorrarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMBorrar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPcompra.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setText("Compra N°:");

        jLabel3.setText("Fecha:");

        javax.swing.GroupLayout jPcompraLayout = new javax.swing.GroupLayout(jPcompra);
        jPcompra.setLayout(jPcompraLayout);
        jPcompraLayout.setHorizontalGroup(
            jPcompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPcompraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jTNumCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPcompraLayout.setVerticalGroup(
            jPcompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPcompraLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPcompraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTNumCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(54, 54, 54))
        );

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
        jTProducto.setComponentPopupMenu(jPopupMenu1);
        jScrollPane4.setViewportView(jTProducto);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jPbotones.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jBRegCompra.setText("Registrar Compra");
        jBRegCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRegCompraActionPerformed(evt);
            }
        });
        jPbotones.add(jBRegCompra);

        jBImprimirFactura.setText("Visualizar Compra");
        jBImprimirFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImprimirFacturaActionPerformed(evt);
            }
        });
        jPbotones.add(jBImprimirFactura);

        jPanel1.add(jPbotones);

        jLabel14.setText("Precio Total:");

        jTPrecTotal.setText("0");

        jLabel11.setText("Gs.");

        javax.swing.GroupLayout jPcantidadLayout = new javax.swing.GroupLayout(jPcantidad);
        jPcantidad.setLayout(jPcantidadLayout);
        jPcantidadLayout.setHorizontalGroup(
            jPcantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPcantidadLayout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTPrecTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(16, 16, 16))
        );
        jPcantidadLayout.setVerticalGroup(
            jPcantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPcantidadLayout.createSequentialGroup()
                .addGroup(jPcantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jTPrecTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPcantidad);

        jLabel6.setText("Para agregar productos registrados a la compra, presionar F4");

        jBBuscarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search_icon.png"))); // NOI18N
        jBBuscarProducto.setToolTipText("Buscar Producto");
        jBBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarProductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(235, 235, 235)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPcompra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jBBuscarProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarProductoActionPerformed
        prod.Lista= new String[300][5];
        prod.c=prod.f=0;
        prod.setVisible(true);
        //-------------------------------------------------//
        boolean bandera = false;
        Integer precio=0;
        Integer cantidad=0;
        String codigo1="";
        String codigo2="";
        int indice = 0;
        for(int h=0;h<prod.f;h++){
            jBRegCompra.setEnabled(true);
            r=null;
            r = db.Listar("*", "producto", "where Cod_inter_producto='"+prod.Lista[h][0]+"'");
            String vec[]= new String[7];
            int hasta= jTProducto.getRowCount();
            
            for (int j=0;j<hasta;j++){
                bandera=false;
                codigo1 = String.valueOf(jTProducto.getValueAt(j, 0));
                codigo2 = String.valueOf(prod.Lista[h][0]);
                if(codigo1.equals(codigo2)){
                    bandera=true;
                    indice = j;
                    break;
                }
                
            }
            
            
            if(bandera==false){
                try {
                    r.next();
                    vec[0]= r.getString("Cod_inter_producto");
                    vec[1] = r.getString("Marca_Fabrica");
                    vec[2] = r.getString("Cod_Original");
                    vec[3]= prod.Lista[h][1];
                    vec[4]= r.getString("Nom_producto")+" "+r.getString("Marca_producto")+" "+r.getString("detalle_producto");
                    precio = Integer.valueOf(quitarPuntos(prod.Lista[h][2]));
                    vec[5]= String.valueOf(precio);
                    vec[6] = String.valueOf(precio*(Integer.valueOf(vec[3])));
                    m.addRow(vec);
                    total = total + precio;
                    jTPrecTotal.setText(String.valueOf(total));
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                try {
                    r.next();
                    
                    String canactual = String.valueOf(jTProducto.getValueAt(indice,3));
                    cantidad = Integer.valueOf(prod.Lista[h][1]) + Integer.valueOf (canactual);
                    precio = Integer.valueOf(r.getString(8)) * Integer.valueOf(prod.Lista[h][1]);
                    total=total+precio;
                    jTPrecTotal.setText(String.valueOf(total));
                    jTProducto.setValueAt((Integer.valueOf(r.getString(8))*cantidad),indice, 5);
                    jTProducto.setValueAt(cantidad, indice, 3);
                    
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
    }//GEN-LAST:event_jBBuscarProductoActionPerformed
    
    public String quitarPuntos(String campo){
        String ahora="";
        for(int k =0; k<campo.length();k++){
            if(campo.charAt(k)!='.'){
                ahora =ahora + campo.charAt(k);
            }
        }
        return (ahora);
        
    }
    
    private void jBRegCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRegCompraActionPerformed
         boolean bandera= false;//controla que la existencia del producto sea mayor o igual a cero
       int ctrl_false = 0;//controla si uno de los booleanos dio false
        int filas = jTProducto.getRowCount();//obtengo las filas de la tabla jtproducto
        for(int j=0; j<filas; j++){
            ResultSet resultado = null;
            String id_producto = String.valueOf(jTProducto.getValueAt(j, 0));
            resultado = db.Listar("Cant_producto,Nom_producto", "producto", "where Cod_inter_producto='"+id_producto+"'");
            
            Integer cant_producto=0;
            try {
                Integer cant= Integer.valueOf(String.valueOf(jTProducto.getValueAt(j, 3)));
                resultado.next();
                cant_producto = Integer.valueOf(resultado.getString("Cant_producto"));
                cant_producto= (cant_producto)+(cant);
                db.actualizarRegistro("producto","Cant_producto="+cant_producto+", pre_compra='"+jTProducto.getValueAt(j, 5)+"'","where Cod_inter_producto='"+id_producto+"';");                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                bandera=false;
            }
        }
                boolean exito = false;
                if(jTProducto.getRowCount()>0){
                    if(db.Altas("compra","", "('"+jTNumCompra.getText()+"','"+(a.getYear()+1900)+"/"+(a.getMonth()+1)+"/"+a.getDate()+"','"+"')")){
                        for(int j=0; j<filas; j++){
                            if(db.Altas("detalle_compra", "", "('"+jTProducto.getValueAt(j, 3)+"','"+jTNumCompra.getText()+"','"+jTProducto.getValueAt(j, 0) +"','"+jTProducto.getValueAt(j, 5) +"')")){
                                exito= true;
                            }else{
                                exito = false;
                                break;
                            }

                        }
                        if(exito){
                            JOptionPane.showMessageDialog(null, "compra registrada con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
                            int respuest = JOptionPane.showConfirmDialog(null, "¿Desea ralizar otra compra?");
                            if(respuest==JOptionPane.YES_OPTION){
                                this.dispose();
                                new jDncompra(null, true,db).setVisible(true);
                            }else{
                                jBImprimirFactura.setEnabled(true);
                            }
                        }
                   }else{
                        JOptionPane.showMessageDialog(null, "Ha ocurrido  un error en el momento de registrar la venta", "Atencion", JOptionPane.INFORMATION_MESSAGE);
                   }  
                }else{
                    JOptionPane.showMessageDialog(jBRegCompra, "Debe agregar por lo menos un producto a la compra", "Atención", JOptionPane.WARNING_MESSAGE);
                }
                
    }//GEN-LAST:event_jBRegCompraActionPerformed

    private void jBImprimirFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImprimirFacturaActionPerformed
                try{
            r=null;
            r=db.Listar("*", "vistacompra3", "where Id_compra='"+jTNumCompra.getText()+"'");
        } catch (Exception ex) {
            //Logger.getLogger(ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         JasperReport reporte;
     JasperPrint reporte_view;
     JRResultSetDataSource jrRS = new JRResultSetDataSource(r);
     
     //Se crea un objeto HashMap
          HashMap parametros = new HashMap();
          parametros.clear();
          //parametros de entrada
         
          Integer SumaTotal = Integer.valueOf(jTPrecTotal.getText());
          String NumCompra = jTNumCompra.getText();
          parametros.put( "SumTotal", SumaTotal+" Gs" );
          parametros.put("NumCompra",NumCompra);
     try{
         //direccion del archivo JASPER
          URL  in = this.getClass().getClassLoader().getResource("reportes/DetalleCompra.jasper");
          reporte = (JasperReport) JRLoader.loadObject( in );
          
          
          //-----------------------------------
          reporte_view = JasperFillManager.fillReport(reporte, parametros, jrRS);
          JasperViewer ventana = new JasperViewer(reporte_view,false);
          ventana.setTitle("Vista Previa");
          ventana.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
          ventana.setVisible(true);
          
          
	  }catch (JRException E){
              JOptionPane.showMessageDialog(null, E.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
    }//GEN-LAST:event_jBImprimirFacturaActionPerformed

    private void jMBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMBorrarActionPerformed
         int total1 = Integer.valueOf(String.valueOf(jTProducto.getValueAt(jTProducto.getSelectedRow(), 5)));
        int totalto = Integer.valueOf(jTPrecTotal.getText());
        
        jTPrecTotal.setText(String.valueOf(totalto-total1));
        total = Integer.valueOf(jTPrecTotal.getText());
        prod.buscarSetText(jTProducto.getValueAt(jTProducto.getSelectedRow(), 0).toString());
        prod.preparartabla();
        
        int cantdisp = Integer.valueOf(prod.getValueFromTableAt(0, 5).toString());
        int canttabla = Integer.valueOf(String.valueOf(jTProducto.getValueAt(jTProducto.getSelectedRow(), 3)));
        prod.setValueToTableAt(0,5,String.valueOf(cantdisp-canttabla));
        m.removeRow(jTProducto.getSelectedRow());
        
        prod.buscarSetText("");
        prod.preparartabla();
    }//GEN-LAST:event_jMBorrarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                jDncompra dialog = new jDncompra(new javax.swing.JFrame(), true,null);
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
    private javax.swing.JButton jBBuscarProducto;
    private javax.swing.JButton jBImprimirFactura;
    private javax.swing.JButton jBRegCompra;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuItem jMBorrar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPbotones;
    private javax.swing.JPanel jPcantidad;
    private javax.swing.JPanel jPcompra;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTFecha;
    private javax.swing.JTextField jTNumCompra;
    private javax.swing.JTextField jTPrecTotal;
    private javax.swing.JTable jTProducto;
    // End of variables declaration//GEN-END:variables
}
