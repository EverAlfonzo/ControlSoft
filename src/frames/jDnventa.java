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
import java.awt.event.KeyEvent;
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
import java.util.ArrayList;
import java.util.List;
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
public class jDnventa extends javax.swing.JDialog {
Image imagen = getToolkit().getImage(getClass().getResource("/imagenes/global2.png"));
DefaultTableModel m;
private  Numero_a_Letra NL = new  Numero_a_Letra();
private  String vendedor;
private  String password;
private String Host;
private String Puerto;
private String Database;
private jDProductos prod;
DBConnect db;
ResultSet r;
int i=0;
Date a;
Integer total=0;
Integer utilidadTotal=0;
Integer existencia=0;
int nro =1;
int nro2=1;
private boolean ex= true;// controla si la existencia del producto es suficiente
private String Nivel;
/** Creates new form jDnventa */
    public jDnventa(java.awt.Frame parent, boolean modal,DBConnect con) {
        
        super(parent, modal);
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        initComponents();
        this.setLocationRelativeTo(null);
        db = con;
        
        jBRegistrarventa.setEnabled(false);
        this.Nivel=Nivel;
        jcNombre.setEditable(true);
        AutoCompleteDecorator.decorate(jcNombre);
        setIconImage(imagen);
        this.setTitle("Agregar Venta");
        this.vendedor=con.getUser();
        this.password=con.getPass();
        actualizarCombo();
        a = new Date();
        jXDatePicker1.setDate(a);
        String titulos[] = {"Cod. Producto","Descripcion","Cantidad","Precio de Venta","Sub-Total","Utilidad parcial"};
        m= (DefaultTableModel) jTProducto.getModel();
        m.setColumnIdentifiers(titulos);
        r=db.Listar("Id_venta", "venta", " order by Id_venta ASC");
        try {
            
            if(r.last()) nro = Integer.valueOf(r.getString(1))+1;
            
            if(nro>0 && nro <=9){
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
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        jBImprimirFactura.setEnabled(false);
        
        prod = new jDProductos(null, true, db,true);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
   
    public jDnventa(java.awt.Frame parent, boolean modal,DBConnect con,String presupuesto,String Nivel) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        db = con;
        
        jBRegistrarventa.setEnabled(false);
        this.Nivel=Nivel;
        jcNombre.setEditable(true);
        AutoCompleteDecorator.decorate(jcNombre);
        setIconImage(imagen);
        this.setTitle("Agregar Venta");
        this.vendedor=con.getUser();
        this.password=con.getPass();
        actualizarCombo();
        a = new Date();
        jXDatePicker1.setDate(a);
        String titulos[] = {"Cod. Producto","Descripcion","Cantidad","Precio de Venta","Sub-Total","Utilidad Parcial"};
        m= (DefaultTableModel) jTProducto.getModel();
        m.setColumnIdentifiers(titulos);
        r=db.Listar("Id_venta", "venta", " order by Id_venta ASC");
        try {
            
            if(r.last())
            nro = Integer.valueOf(r.getString(1))+1;
            if(nro>0 && nro <=9){
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
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        jBImprimirFactura.setEnabled(false);
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        prod = new jDProductos(null, true, db,true);
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        jTNumFact.requestFocus();
    }
    
     

    public void actualizarCombo(){
        jcNombre.removeAll();
        try {
            ResultSet rs = db.Listar("Nom_cliente,Id_cliente", "cliente", "");
            while(rs.next()){
                jcNombre.addItem(rs.getString("Nom_cliente")+" ,"+rs.getString("Id_cliente"));
            }
            AutoCompleteDecorator.decorate(jcNombre);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error 195",JOptionPane.ERROR_MESSAGE);
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jmborrar = new javax.swing.JMenuItem();
        jPCliente = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jBaddCliente = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLidCliente = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jcNombre = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLTipo = new javax.swing.JLabel();
        jPfactura = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTNumFact = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jPcantidad = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTPrecTotal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jBImprimirFactura = new javax.swing.JButton();
        jBRegistrarventa = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jTUtilidadTotal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTProducto = new javax.swing.JTable();

        jmborrar.setText("Eliminar");
        jmborrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmborrarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jmborrar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Cliente"));
        jPCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPClienteKeyReleased(evt);
            }
        });

        jLabel1.setText("Nombre del Cliente:");

        jBaddCliente.setText("Agregar Cliente");
        jBaddCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBaddClienteActionPerformed(evt);
            }
        });
        jBaddCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jBaddClienteKeyReleased(evt);
            }
        });

        jLabel16.setText("R.U.C.:");

        jLidCliente.setText("Nro");

        jScrollPane2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jScrollPane2KeyReleased(evt);
            }
        });

        jcNombre.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcNombreItemStateChanged(evt);
            }
        });
        jcNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcNombreActionPerformed(evt);
            }
        });
        jcNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jcNombreKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jcNombre);

        jLabel8.setText("[?]");
        jLabel8.setToolTipText("Presione F2 para acceder al campo");

        jLabel4.setText("Tipo:");

        jLTipo.setText("Nro");

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
                .addGroup(jPClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPClienteLayout.createSequentialGroup()
                        .addComponent(jLidCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPClienteLayout.createSequentialGroup()
                        .addComponent(jBaddCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8))
                    .addComponent(jLTipo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPClienteLayout.setVerticalGroup(
            jPClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPClienteLayout.createSequentialGroup()
                .addGroup(jPClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPClienteLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jBaddCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addGroup(jPClienteLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLidCliente)
                    .addComponent(jLabel4)
                    .addComponent(jLTipo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPfactura.setBorder(javax.swing.BorderFactory.createTitledBorder("Cabecera de Factura"));

        jLabel2.setText("Factura N°:");

        jTNumFact.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTNumFact.setText("00000000");
        jTNumFact.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTNumFactKeyReleased(evt);
            }
        });

        jLabel3.setText("Fecha:");

        jLabel5.setText("Cond. de Venta:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Contado", "Crédito" }));
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jComboBox1KeyReleased(evt);
            }
        });

        jXDatePicker1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                jXDatePicker1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        try {
            jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextField1.setText("001-001");
        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPfacturaLayout = new javax.swing.GroupLayout(jPfactura);
        jPfactura.setLayout(jPfacturaLayout);
        jPfacturaLayout.setHorizontalGroup(
            jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPfacturaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(57, 57, 57)
                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTNumFact, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1, 0, 155, Short.MAX_VALUE)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPfacturaLayout.setVerticalGroup(
            jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPfacturaLayout.createSequentialGroup()
                .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPfacturaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTNumFact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPfacturaLayout.createSequentialGroup()
                        .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPfacturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jBRegistrarventa.setMnemonic('R');
        jBRegistrarventa.setText("Registrar Venta");
        jBRegistrarventa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRegistrarventaActionPerformed(evt);
            }
        });

        jBCancelar.setText("Cancelar");
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });

        jLabel15.setText("Utilidad Total:");

        jTUtilidadTotal.setText("0");

        jLabel12.setText("Gs.");

        javax.swing.GroupLayout jPcantidadLayout = new javax.swing.GroupLayout(jPcantidad);
        jPcantidad.setLayout(jPcantidadLayout);
        jPcantidadLayout.setHorizontalGroup(
            jPcantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPcantidadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBRegistrarventa, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBImprimirFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTPrecTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTUtilidadTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12))
        );
        jPcantidadLayout.setVerticalGroup(
            jPcantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPcantidadLayout.createSequentialGroup()
                .addGap(0, 15, Short.MAX_VALUE)
                .addGroup(jPcantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPcantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTUtilidadTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)
                        .addComponent(jLabel12))
                    .addGroup(jPcantidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTPrecTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14)
                        .addComponent(jLabel11)
                        .addComponent(jBImprimirFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBRegistrarventa, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        jLabel18.setText("Para agregar productos registrados a la venta, presionar F4");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/search_icon.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButton1KeyReleased(evt);
            }
        });

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
        jTProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTProductoFocusLost(evt);
            }
        });
        jScrollPane4.setViewportView(jTProducto);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jPcantidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPfactura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jPfactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel18))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBaddClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBaddClienteActionPerformed
            new jDnuevocliente(null, true,db,1).setVisible(true);
            actualizarCombo();
    }//GEN-LAST:event_jBaddClienteActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_formWindowClosed

    private void jcNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcNombreActionPerformed

    private void jcNombreItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcNombreItemStateChanged
        String h = String.valueOf(jcNombre.getSelectedItem());
        jLidCliente.setText(h.substring(h.indexOf(",")+1));
        
        ResultSet Tipo = db.Listar("tipo_cliente", "cliente", "where Id_cliente='"+jLidCliente.getText()+"'");
        try {
            if(Tipo.next()){
               jLTipo.setText(Tipo.getString("tipo_cliente"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jcNombreItemStateChanged

    private void jBRegistrarventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRegistrarventaActionPerformed
        int filas = jTProducto.getRowCount();//obtengo las filas de la tabla jtproducto
        
               if(db.Altas("venta","", "('"+jTNumFact.getText()+"','"+(a.getYear()+1900)+"/"+(a.getMonth()+1)+"/"+a.getDate()+"','"+jLidCliente.getText()+"','"+jComboBox1.getSelectedItem().toString()+"','',0,"+jTPrecTotal.getText()+","+jTUtilidadTotal.getText()+")")){
                   if(jComboBox1.getSelectedItem().toString().equals("Contado")){
                       db.actualizarRegistro("venta", "pagado='"+jTPrecTotal.getText()+"'", " where Id_venta='"+jTNumFact.getText()+"'");
                   }
                    for(int j=0; j<filas; j++){
                            int preVenta = Integer.valueOf(jTProducto.getValueAt(j, 3).toString());
                            db.Altas("detalle_venta", "", "('"+jTNumFact.getText()+"','"+jTProducto.getValueAt(j, 0)+"','"+jTProducto.getValueAt(j, 2)+"','"+String.valueOf(preVenta)+"','"+tipoPrecio(String.valueOf(preVenta),jTProducto.getValueAt(j, 0).toString())+"')");
                            ResultSet rs = db.Listar("Cant_producto", "producto", " where Cod_inter_producto = '"+jTProducto.getValueAt(j, 0).toString()+"'");
                            int cant=0;
                            try {
                                rs.next();
                                cant = rs.getInt("Cant_producto");
                            } catch (SQLException ex) {
                                Logger.getLogger(jDnventa.class.getName()).log(Level.SEVERE, null, ex);
                            } 
                            
                            db.actualizarRegistro("producto", "Cant_producto = "+String.valueOf(cant - Integer.valueOf((String) jTProducto.getValueAt(j, 2)))+ ", ultima_venta='"+(a.getYear()+1900)+"/"+(a.getMonth()+1)+"/"+a.getDate()+"' "," where Cod_Inter_producto = '"+jTProducto.getValueAt(j, 0)+"'");
                        
                    }
                    jBImprimirFactura.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "venta registrada con exito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    if(JOptionPane.showConfirmDialog(null, "¿Imprimir Factura?","Facturar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                        jBImprimirFacturaActionPerformed(evt);
                    }
                    
                    this.dispose();                    

               }else{
                    JOptionPane.showMessageDialog(null, "Ha ocurrido  un error en el momento de registrar la venta", "Atencion", JOptionPane.INFORMATION_MESSAGE);
                }
            jBImprimirFactura.setEnabled(false);
    }//GEN-LAST:event_jBRegistrarventaActionPerformed
    public String tipoPrecio(String precio,String cod){
        String precio1, precio2, precio3, precio4;
        precio1=precio2=precio3=precio4 = "";
        
        ResultSet tipo_precio = db.Listar("pre_venta1, pre_venta2, pre_venta3, pre_venta4", "producto", "where Cod_inter_producto");
    try {
        tipo_precio.next();
        precio1 = tipo_precio.getString("pre_venta1");
        precio2 = tipo_precio.getString("pre_venta2");
        precio3 = tipo_precio.getString("pre_venta3");
        precio4 = tipo_precio.getString("pre_venta4");
    } catch (SQLException ex) {
        Logger.getLogger(jDnventa.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        if(precio.equals(precio1)){
            return "pre_venta1";
        }else if(precio.equals(precio2)){
            return "pre_venta2";
        }else if(precio.equals(precio3)){
            return "pre_venta3";
        }else if(precio.equals(precio4)){
            return "pre_venta4";
        }
        return "pre_n_venta";
    }
    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jBImprimirFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImprimirFacturaActionPerformed
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
         //direccion del arfchivo JASPER
        URL in = null; 
        if("Contado".equals(jComboBox1.getSelectedItem().toString())){
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
          ventana.setVisible(true);
          
          
	  }catch (JRException E){
              JOptionPane.showMessageDialog(null, E.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
    }//GEN-LAST:event_jBImprimirFacturaActionPerformed

    public String quitarPuntos(String campo){
        String ahora="";
        for(int k =0; k<campo.length();k++){
            if(campo.charAt(k)!='.'){
                ahora =ahora + campo.charAt(k);
            }
        }
        return (ahora);
        
    }
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        prod.Lista= new String[300][5];
        prod.tipoCliente=Integer.valueOf(this.jLTipo.getText());
        prod.c=prod.f=0;
        prod.setVisible(true);
        
        //-------------------------------------------------//
        boolean bandera=false;
        int indice=0;
        for(int h=0;h<prod.f;h++){
          bandera = false;
            Integer precio=0;
            Integer cantidad=0;
            String codigo1="";
            String codigo2="";
             indice = 0;
            
            
            jBRegistrarventa.setEnabled(true);
            String vec[]= new String[6];
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
                        
                        vec[0]= prod.Lista[h][0]; // Codigo
                        vec[1]= prod.Lista[h][3]; // Descripcion
                        vec[2]= prod.Lista[h][1]; // Cantidad
                        int cant_vendida = Integer.valueOf(vec[2]);
                        vec[3]= prod.Lista[h][2]; // Precio de Venta unitario
                        precio = Integer.valueOf(prod.Lista[h][2])*Integer.valueOf(prod.Lista[h][1]);
                        vec[4]= String.valueOf(precio);
                        int precio_compra = Integer.valueOf(prod.Lista[h][4]);
                        int sub_costo = precio_compra*cant_vendida;
                        int utilidad = precio - sub_costo;
                        vec[5]= String.valueOf(utilidad);
                        m.addRow(vec);
                        utilidadTotal+=utilidad;
                        total = total + precio;
                        jTPrecTotal.setText(String.valueOf(total));
                        jTUtilidadTotal.setText(String.valueOf(utilidadTotal));
                        
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                
            }else{
                     try {
                            r.next();

                            String canactual=String.valueOf(jTProducto.getValueAt(indice, 1));
                            cantidad=Integer.valueOf(prod.Lista[h][1])+Integer.valueOf(canactual);
                            precio = Integer.valueOf(prod.Lista[h][2])*Integer.valueOf(prod.Lista[h][1]);
                            total=total+precio;
                            jTPrecTotal.setText(String.valueOf(total));
                            jTProducto.setValueAt((Integer.valueOf(prod.Lista[h][2])*cantidad),indice, 3);
                            jTProducto.setValueAt(cantidad, indice, 1);

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error2");
                        }
            }
        }
        
            
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jmborrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmborrarActionPerformed
        int total1 = Integer.valueOf(String.valueOf(jTProducto.getValueAt(jTProducto.getSelectedRow(), 3)));
        int totalto = Integer.valueOf(jTPrecTotal.getText());
        
        jTPrecTotal.setText(String.valueOf(totalto-total1));
        total = Integer.valueOf(jTPrecTotal.getText());
        prod.buscarSetText(jTProducto.getValueAt(jTProducto.getSelectedRow(), 0).toString());
        prod.preparartabla();
        
        int cantdisp = Integer.valueOf(prod.getValueFromTableAt(0, 5).toString());
        int canttabla = Integer.valueOf(String.valueOf(jTProducto.getValueAt(jTProducto.getSelectedRow(), 1)));
        prod.setValueToTableAt(0,5,String.valueOf(cantdisp+canttabla));
        m.removeRow(jTProducto.getSelectedRow());
        
        prod.buscarSetText("");
        prod.preparartabla();
}//GEN-LAST:event_jmborrarActionPerformed

    private void jTProductoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProductoFocusLost
        if(jTProducto.getRowCount()==-1){
            jBRegistrarventa.setEnabled(false);
        }
    }//GEN-LAST:event_jTProductoFocusLost

    private void jTNumFactKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNumFactKeyReleased
        switch(evt.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            case KeyEvent.VK_F2:
                this.jcNombre.requestFocus();
                break;
            case KeyEvent.VK_F4:
                jButton1ActionPerformed(null);
                break;
        }
    }//GEN-LAST:event_jTNumFactKeyReleased

    private void jComboBox1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBox1KeyReleased
                switch(evt.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            case KeyEvent.VK_F2:
                this.jcNombre.requestFocus();
                break;
            case KeyEvent.VK_F4:
                jButton1ActionPerformed(null);
                break;
        }

    }//GEN-LAST:event_jComboBox1KeyReleased

    private void jcNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcNombreKeyReleased
                switch(evt.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            case KeyEvent.VK_F2:
                this.jcNombre.requestFocus();
                break;
            case KeyEvent.VK_F4:
                jButton1ActionPerformed(null);
                break;
        }

    }//GEN-LAST:event_jcNombreKeyReleased

    private void jBaddClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBaddClienteKeyReleased
                switch(evt.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            case KeyEvent.VK_F2:
                this.jcNombre.requestFocus();
                break;
            case KeyEvent.VK_F4:
                jButton1ActionPerformed(null);
                break;
        }

    }//GEN-LAST:event_jBaddClienteKeyReleased

    private void jButton1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyReleased
                switch(evt.getKeyCode()){
            case KeyEvent.VK_F2:
                this.jcNombre.requestFocus();
                break;
            case KeyEvent.VK_F4:
                jButton1ActionPerformed(null);
                break;
        }

    }//GEN-LAST:event_jButton1KeyReleased

    private void jScrollPane2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jScrollPane2KeyReleased
                switch(evt.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            case KeyEvent.VK_F2:
                this.jcNombre.requestFocus();
                break;
            case KeyEvent.VK_F4:
                jButton1ActionPerformed(null);
                break;
        }

    }//GEN-LAST:event_jScrollPane2KeyReleased

    private void jPClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPClienteKeyReleased
                switch(evt.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
                this.dispose();
                break;
            case KeyEvent.VK_F2:
                this.jcNombre.requestFocus();
                break;
            case KeyEvent.VK_F4:
                jButton1ActionPerformed(null);
                break;
        }

    }//GEN-LAST:event_jPClienteKeyReleased

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
                this.setVisible(false);
        }

    }//GEN-LAST:event_jButton1KeyPressed

    private void jXDatePicker1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jXDatePicker1PopupMenuWillBecomeInvisible
        a=jXDatePicker1.getDate();
    }//GEN-LAST:event_jXDatePicker1PopupMenuWillBecomeInvisible

    private void jFormattedTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                jDnventa dialog = new jDnventa(new javax.swing.JFrame(), true,null);
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
    private javax.swing.JButton jBRegistrarventa;
    private javax.swing.JButton jBaddCliente;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLidCliente;
    private javax.swing.JPanel jPCliente;
    private javax.swing.JPanel jPcantidad;
    private javax.swing.JPanel jPfactura;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTNumFact;
    private javax.swing.JTextField jTPrecTotal;
    private javax.swing.JTable jTProducto;
    private javax.swing.JTextField jTUtilidadTotal;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private javax.swing.JComboBox jcNombre;
    private javax.swing.JMenuItem jmborrar;
    // End of variables declaration//GEN-END:variables
}
