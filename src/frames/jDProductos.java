/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jDProductos.java
 *
 * Created on 11/08/2012, 07:43:22 AM
 */
package frames;

import clases.DBConnect;
import com.mysql.jdbc.Blob;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Edgar
 */
public class jDProductos extends javax.swing.JDialog {
    private DefaultTableModel m;
    private Image imagen = getToolkit().getImage(getClass().getResource("/imagenes/global2.png"));
    private ResultSet r;
    private DBConnect db=null;
    private double zoom = 1;
    public String Lista[][];
    private boolean ventas=false;
    private int compras = 0;
    public int f=0,c=0;
    private jDnProducto a;
    public int tipoCliente;
    private int contadorVentana = 0; // cuenta la cantidad de veces que se abrió esta ventana
    /** Creates new form jDProductos */
    public jDProductos(java.awt.Frame parent, boolean modal,DBConnect con) {
        super(parent, modal);
        initComponents();
        setIconImage(imagen);
        this.setTitle("Productos");
        db= con;
        
        jButton1.setVisible(false);
        jBNuevo.requestFocus();
        preparartabla();
        cantidad();
    }
    
    public jDProductos(java.awt.Frame parent, boolean modal,DBConnect con,boolean bandera) {
        super(parent, modal);
        initComponents();
        setIconImage(imagen);
        this.setTitle("Productos");
        ventas=bandera;
        db= con;
        Lista = new String[300][5];
        preparartabla();
        cantidad();
        jBNuevo.requestFocus();
    }
    
    public jDProductos(java.awt.Frame parent, boolean modal,DBConnect con,int bandera) {
        super(parent, modal);
        initComponents();
        setIconImage(imagen);
        this.setTitle("Productos");
        compras=bandera;
        db= con;
        Lista = new String[300][5];
        preparartabla();
        cantidad();
        jBNuevo.requestFocus();
    }
    public void buscarSetText(String txt){
        this.jTbuscar.setText(txt);
    }
    public void pdf(ResultSet rs,HashMap HM){
        JasperReport jasperReport;
        JasperPrint jasperPrint;   
        
        try
        {
            r.beforeFirst();
            JRResultSetDataSource jrRS = new JRResultSetDataSource(r);
          //se carga el reporte
          URL  in= getClass().getClassLoader().getResource("reportes/productos.jasper");
          jasperReport=(JasperReport)JRLoader.loadObject(in);
          //se procesa el archivo jasper
          jasperPrint = JasperFillManager.fillReport(jasperReport, HM, jrRS );
          //se crea el archivo PDF
          Date fecha = new Date();
          JasperExportManager.exportReportToPdfFile( jasperPrint,System.getProperties().getProperty("user.home")+ "\\Desktop\\Productos"+fecha.getDate()+(fecha.getMonth()+1)+(fecha.getYear()+1900)+fecha.getHours()+fecha.getMinutes()+".pdf");
        }
        catch (JRException ex)
        {
          System.err.println( "Error iReport: " + ex.getMessage() );
        }
        catch (SQLException ex){
            
        }
  }
    
    public void cantidad(){
        jLcantidad.setText(String.valueOf(jTProductos.getRowCount()));
    }
    
    
    public String EncriptarCosto(String costo){
        String Enc = "";
        
        for(int i=0;i<costo.length(); i++){
            switch(costo.charAt(i)){
                case '1':
                    Enc = Enc +'P';
                    break;
                case '2':
                    Enc = Enc +'E';
                    break;
                case '3':
                    Enc = Enc + 'R';
                    break;
                case '4':
                    Enc = Enc + 'N';
                    break;
                case '5':
                    Enc = Enc + 'A';
                    break;
                case '6':
                    Enc = Enc + 'M';
                    break;
                case '7':
                    Enc = Enc + 'B';
                    break;
                case '8':
                    Enc = Enc + 'U';
                    break;
                case '9':
                    Enc = Enc + 'C';
                    break;
                case '0':
                    Enc = Enc + 'X';
                    break;
                case '.':
                    Enc = Enc + costo.charAt(i);
                    
            }
        }
        return Enc;
    }
    
    public String DesencriptarCosto(String costo){
        String Enc = "";
        
        for(int i=0;i<costo.length(); i++){
            switch(costo.charAt(i)){
                case 'P':
                    Enc = Enc +'1';
                    break;
                case 'E':
                    Enc = Enc +'2';
                    break;
                case 'R':
                    Enc = Enc + '3';
                    break;
                case 'N':
                    Enc = Enc + '4';
                    break;
                case 'A':
                    Enc = Enc + '5';
                    break;
                case 'M':
                    Enc = Enc + '6';
                    break;
                case 'B':
                    Enc = Enc + '7';
                    break;
                case 'U':
                    Enc = Enc + '8';
                    break;
                case 'C':
                    Enc = Enc + '9';
                    break;
                case 'X':
                    Enc = Enc + '0';
                    break;
                case '.':
                    Enc = Enc + costo.charAt(i);
                    
            }
        }
        return Enc;
    }
    
     public void preparartabla() {
        int fil, col=1;
        int iOrderBy=1;
        String sOrderBy="Nombre";
        //Limpia selección de la tabla
        jTProductos.clearSelection();
        String titulos[]={"Cod. Producto","Número de Fábrica","Código Original", "Nombre", "Marca", "Existencia", "Precio Venta", "Precio Compra"};
        m = (new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Producto", "Número de Fábrica", "Codigo Original", "Nombre", "Marca", "Existencia", "Precio Venta 1", "Precio Venta 2", "Precio Venta 3", "Precio Venta 4", "Precio Compra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTProductos.getColumnModel().getColumn(0).setPreferredWidth(20);
        jTProductos.getColumnModel().getColumn(1).setPreferredWidth(30);
        jTProductos.getColumnModel().getColumn(2).setPreferredWidth(30);
        jTProductos.getColumnModel().getColumn(3).setPreferredWidth(200);
        jTProductos.getColumnModel().getColumn(4).setPreferredWidth(10);
        jTProductos.getColumnModel().getColumn(5).setPreferredWidth(10);
        jTProductos.getColumnModel().getColumn(6).setPreferredWidth(30);
        jTProductos.getColumnModel().getColumn(7).setPreferredWidth(30);
        jTProductos.getColumnModel().getColumn(8).setPreferredWidth(30);
        jTProductos.getColumnModel().getColumn(9).setPreferredWidth(30);
        jTProductos.getColumnModel().getColumn(10).setPreferredWidth(30);
        jTProductos.setModel(m); 
        
        ResultSet rs=null;
        ResultSet Total;
        
        
        if(!jCheckBox1.isSelected()){
            Total=db.Listar("pre_compra,Cant_producto","producto", " where Cod_inter_producto like \"%"+ jTbuscar.getText()+"%\" or ubica_producto like \"%"+ jTbuscar.getText()+"%\" or Marca_fabrica like \"%"+ jTbuscar.getText()+"%\" or Cod_original like \"%"+ jTbuscar.getText()+"%\" or Nom_producto like \"%"+ jTbuscar.getText()+"%\" or Marca_producto like \"%"+ jTbuscar.getText()+"%\"");
            rs=db.Listar("*", "producto", " where Cod_inter_producto like \"%"+ jTbuscar.getText()+"%\" or ubica_producto like \"%"+ jTbuscar.getText()+"%\" or Marca_fabrica like \"%"+ jTbuscar.getText()+"%\" or Cod_original like \"%"+ jTbuscar.getText()+"%\" or Nom_producto like \"%"+ jTbuscar.getText()+"%\" or Marca_producto like \"%"+ jTbuscar.getText()+"%\" order by Nom_producto ASC");
        }else{
            
            Total=db.Listar("pre_compra,Cant_producto", "producto", " where (Cod_inter_producto like \"%"+ jTbuscar.getText()+"%\" or Marca_fabrica like \"%"+ jTbuscar.getText()+"%\" or Cod_original like \"%"+ jTbuscar.getText()+"%\" or Nom_producto like \"%"+ jTbuscar.getText()+"%\" or Marca_fabrica like \"%"+ jTbuscar.getText()+"%\" or ubica_producto like \"%"+ jTbuscar.getText()+"%\") && (Cant_producto<=cant_min)");
            rs=db.Listar("*", "producto", " where (Cod_inter_producto like \"%"+ jTbuscar.getText()+"%\" or Marca_fabrica like \"%"+ jTbuscar.getText()+"%\" or Cod_original like \"%"+ jTbuscar.getText()+"%\" or Nom_producto like \"%"+ jTbuscar.getText()+"%\" or Marca_fabrica like \"%"+ jTbuscar.getText()+"%\" or ubica_producto like \"%"+ jTbuscar.getText()+"%\") && (Cant_producto<=cant_min) order by Nom_producto ASC");
            
        }
        int total=0;
        String valor;
        try {
            while(Total.next()){
                valor=(quitarPuntos(Total.getString(1)));
                if(!valor.equals("")){
                    int cant = Total.getInt("Cant_producto");
                    total+=Integer.valueOf(valor)*cant;
                }
                
            }
            jTtotalCompra.setText(calcularPuntos(String.valueOf(total)));
        } catch (SQLException ex) {
            Logger.getLogger(jDProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        String fila[] = new String[11];
        try {
            while(rs.next()){
                fila[0]=rs.getString("Cod_inter_producto");
                fila[1]=rs.getString("Marca_Fabrica");
                fila[2]=rs.getString("Cod_Original");
                fila[3]=rs.getString("Nom_producto");
                fila[4]=rs.getString("Marca_producto");
                fila[5]=calcularPuntos(rs.getString("Cant_producto"));
                fila[6]=calcularPuntos(rs.getString("pre_venta1"));
                fila[7]=calcularPuntos(rs.getString("pre_venta2"));
                fila[8]=calcularPuntos(rs.getString("pre_venta3"));
                fila[9]=calcularPuntos(rs.getString("pre_venta4"));
                fila[10]=rs.getString("pre_compra");
                
                
                
                 m.addRow(fila);
            }
           
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        cantidad();
        
            jTProductos.getColumnModel().getColumn(0).setPreferredWidth(20);
            jTProductos.getColumnModel().getColumn(1).setPreferredWidth(30);
            jTProductos.getColumnModel().getColumn(2).setPreferredWidth(30);
            jTProductos.getColumnModel().getColumn(3).setPreferredWidth(200);
            jTProductos.getColumnModel().getColumn(4).setPreferredWidth(10);
            jTProductos.getColumnModel().getColumn(5).setPreferredWidth(10);
            jTProductos.getColumnModel().getColumn(6).setPreferredWidth(30);
            jTProductos.getColumnModel().getColumn(7).setPreferredWidth(30);
            jTProductos.getColumnModel().getColumn(8).setPreferredWidth(30);
            jTProductos.getColumnModel().getColumn(9).setPreferredWidth(30);
            jTProductos.getColumnModel().getColumn(10).setPreferredWidth(30);

    }
     public void vaciar(){
        jTProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Cod. Producto","Número de Fábrica","Código Original", "Nombre", "Marca", "Existencia", "Precio Venta", "Precio Compra"})
             {
                 boolean[] canEdit = new boolean [] {false, false, false, false, false, false,false};

                 public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }});

     }
     
     
     
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPmenu = new javax.swing.JPopupMenu();
        jMModif = new javax.swing.JMenuItem();
        jMBorrar = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTProductos = new javax.swing.JTable();
        jXImageView1 = new org.jdesktop.swingx.JXImageView();
        jLabel3 = new javax.swing.JLabel();
        jPBusqueda = new javax.swing.JPanel();
        jTbuscar = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPBotones = new javax.swing.JPanel();
        jBNuevo = new javax.swing.JButton();
        jBModif = new javax.swing.JButton();
        jBSalir = new javax.swing.JButton();
        jBImprimir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLUbicacion = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLDetalle = new javax.swing.JLabel();
        jBMenos = new javax.swing.JButton();
        jBMas = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLcantidad = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jTtotalCompra = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        jPmenu.setComponentPopupMenu(jPmenu);
        jPmenu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jMModif.setText("Modificar Producto");
        jMModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMModifActionPerformed(evt);
            }
        });
        jMModif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMModifKeyPressed(evt);
            }
        });
        jPmenu.add(jMModif);

        jMBorrar.setText("Borrar Producto");
        jMBorrar.setName(""); // NOI18N
        jMBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMBorrarActionPerformed(evt);
            }
        });
        jMBorrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMBorrarKeyPressed(evt);
            }
        });
        jPmenu.add(jMBorrar);

        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        jTProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Producto", "Número de Fábrica", "Codigo Original", "Nombre", "Marca", "Existencia", "Precio Venta 1", "Precio Venta 2", "Precio Venta 3", "Precio Venta 4", "Precio Compra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTProductos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        jTProductos.setComponentPopupMenu(jPmenu);
        jTProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTProductos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTProductosMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTProductosMousePressed(evt);
            }
        });
        jTProductos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTProductosFocusLost(evt);
            }
        });
        jTProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTProductosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTProductosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTProductos);
        jTProductos.getColumnModel().getColumn(0).setPreferredWidth(20);
        jTProductos.getColumnModel().getColumn(1).setPreferredWidth(30);
        jTProductos.getColumnModel().getColumn(2).setPreferredWidth(30);
        jTProductos.getColumnModel().getColumn(3).setPreferredWidth(200);
        jTProductos.getColumnModel().getColumn(4).setPreferredWidth(10);
        jTProductos.getColumnModel().getColumn(5).setPreferredWidth(10);
        jTProductos.getColumnModel().getColumn(6).setPreferredWidth(30);
        jTProductos.getColumnModel().getColumn(7).setPreferredWidth(30);
        jTProductos.getColumnModel().getColumn(8).setPreferredWidth(30);
        jTProductos.getColumnModel().getColumn(9).setPreferredWidth(30);
        jTProductos.getColumnModel().getColumn(10).setPreferredWidth(30);

        jXImageView1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jXImageView1Layout = new javax.swing.GroupLayout(jXImageView1);
        jXImageView1.setLayout(jXImageView1Layout);
        jXImageView1Layout.setHorizontalGroup(
            jXImageView1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 339, Short.MAX_VALUE)
        );
        jXImageView1Layout.setVerticalGroup(
            jXImageView1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel3.setText("Imagen de Producto:");

        jTbuscar.setToolTipText("");
        jTbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTbuscar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTbuscarCaretUpdate(evt);
            }
        });
        jTbuscar.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jTbuscarMouseWheelMoved(evt);
            }
        });
        jTbuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTbuscarMouseClicked(evt);
            }
        });
        jTbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTbuscarActionPerformed(evt);
            }
        });
        jTbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTbuscarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTbuscarKeyTyped(evt);
            }
        });

        jLabel1.setText("Buscar:");

        jLabel2.setText("[?]");
        jLabel2.setToolTipText("Presione F3 para acceder al campo");

        javax.swing.GroupLayout jPBusquedaLayout = new javax.swing.GroupLayout(jPBusqueda);
        jPBusqueda.setLayout(jPBusquedaLayout);
        jPBusquedaLayout.setHorizontalGroup(
            jPBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        jPBusquedaLayout.setVerticalGroup(
            jPBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTbuscar)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)))
        );

        jPBotones.setLayout(new java.awt.GridLayout(2, 2, 100, 0));

        jBNuevo.setText("Agregar Producto (F4)");
        jBNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBNuevoActionPerformed(evt);
            }
        });
        jBNuevo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jBNuevoKeyReleased(evt);
            }
        });
        jPBotones.add(jBNuevo);

        jBModif.setText("Modificar Producto (F5)");
        jBModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBModifActionPerformed(evt);
            }
        });
        jBModif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jBModifKeyReleased(evt);
            }
        });
        jPBotones.add(jBModif);

        jBSalir.setText("Salir (Esc)");
        jBSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBSalirActionPerformed(evt);
            }
        });
        jPBotones.add(jBSalir);

        jBImprimir.setText("Imprimir Listado");
        jBImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImprimirActionPerformed(evt);
            }
        });
        jPBotones.add(jBImprimir);

        jLabel4.setText("Ubicación:");

        jLUbicacion.setText("Muestra la ubicación del producto");
        jLUbicacion.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel6.setText("Detalle del Producto:");

        jLDetalle.setText("Muestra los detalles del producto");
        jLDetalle.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLUbicacion, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(jLDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBMenos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lensToolM3.png"))); // NOI18N
        jBMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMenosActionPerformed(evt);
            }
        });
        jBMenos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jBMenosFocusLost(evt);
            }
        });

        jBMas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lensTool3.png"))); // NOI18N
        jBMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMasActionPerformed(evt);
            }
        });
        jBMas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jBMasFocusLost(evt);
            }
        });
        jBMas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jBMasKeyReleased(evt);
            }
        });

        jLabel5.setText("Total de productos:");

        jLcantidad.setText("xxx");

        jCheckBox1.setText("Visualizar los productos cuya existencia esté por debajo del mínimo.");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jButton1.setText("Corrección (presionar sólo una vez)");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTtotalCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTtotalCompraActionPerformed(evt);
            }
        });

        jLabel7.setText("Total Costo Compra:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTtotalCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1010, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBMas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBMenos, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(jXImageView1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLcantidad)
                            .addComponent(jButton1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jPBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTtotalCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7))
                            .addComponent(jCheckBox1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jXImageView1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jBMas, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(jBMenos, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private String calcularPuntos(String campo) {
        String antes="";
        String despues="";
        String ahora = campo;
        int numero=0;
        campo= quitarPuntos(campo);
        if(!"".equals(campo)){
            
            numero = Integer.valueOf(campo);
            
            if(numero>999){
                antes = campo.substring(0, campo.length()-3);
                 despues = campo.substring(campo.length()-3, campo.length());
               
                 ahora =antes+"."+despues ;
               /* campo.setText(ahora);*/
                if(numero>999999){
                    antes = ahora.substring(0,ahora.length()-7);
                    despues = ahora.substring(ahora.length()-7,ahora.length());
                    
                    ahora =antes+"."+despues ;
                    
                    if(numero>999999999){
                        antes = ahora.substring(0,ahora.length()-11);
                        despues = ahora.substring(ahora.length()-11,ahora.length());
                        
                        ahora = antes+"."+despues;
                    }
                }
            }
        }
        
        return(ahora);
    }
    
    
    
    private void jTbuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTbuscarKeyTyped
        // TODO add your handling code here:
         
    }//GEN-LAST:event_jTbuscarKeyTyped

    private void jTbuscarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTbuscarCaretUpdate
//        preparartabla();
    }//GEN-LAST:event_jTbuscarCaretUpdate

    private void jTProductosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTProductosFocusLost
//        jXImageView1.setImage(getToolkit().getImage(getClass().getResource("/imagenes/defaultlarge.gif")));
    }//GEN-LAST:event_jTProductosFocusLost

    private void jTbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTbuscarActionPerformed
        // TODO add your handling code here:
         jXImageView1.setImage(getToolkit().getImage(getClass().getResource("/imagenes/defaultlarge.gif")));
    }//GEN-LAST:event_jTbuscarActionPerformed

    private void jBNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBNuevoActionPerformed
        a= new jDnProducto(null, true, db,false);
        a.setVisible(true);
        jTbuscarCaretUpdate(null);
    }//GEN-LAST:event_jBNuevoActionPerformed

    private void jBModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBModifActionPerformed
        // TODO add your handling code here:
        if(jTProductos.getSelectedRow()!= -1){
            
            new jDModProducto(null, true, db, (String) jTProductos.getValueAt(jTProductos.getSelectedRow(), 0)).setVisible(true);
            jTbuscarCaretUpdate(null);
        }else{
            JOptionPane.showMessageDialog(rootPane, "Seleccione un producto" , "Atención",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jBModifActionPerformed

    private void jBSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBSalirActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jBSalirActionPerformed

    private void jMModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMModifActionPerformed
        // TODO add your handling code here:
        new jDModProducto(null, true, db, (String) jTProductos.getValueAt(jTProductos.getSelectedRow(), 0)).setVisible(true);
    }//GEN-LAST:event_jMModifActionPerformed

    private void jMBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMBorrarActionPerformed
        // TODO add your handling code here:
        
        int opcion = JOptionPane.showConfirmDialog(rootPane, "¿Desea Borrar el elemento seleccionado?", "Atención", JOptionPane.YES_NO_OPTION);
        
        if(opcion==JOptionPane.YES_OPTION){
            String codigo= String.valueOf(jTProductos.getValueAt(jTProductos.getSelectedRow(), 0));
            if(db.Bajas("producto", "where Cod_inter_producto = '"+codigo+"'")){
                JOptionPane.showMessageDialog(rootPane, "El producto se ha eliminado exitosamente","Exito",JOptionPane.INFORMATION_MESSAGE);
                jTbuscarCaretUpdate(null);
            }else{
                JOptionPane.showMessageDialog(rootPane, "El producto no se ha podido eliminar","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jMBorrarActionPerformed

    public String quitarPuntos(String campo){
        String ahora="";
        for(int k =0; k<campo.length();k++){
            if(campo.charAt(k)!='.'){
                ahora =ahora + campo.charAt(k);
            }
        }
        return (ahora);
        
    }
    private void jTProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTProductosMouseClicked
        mostrarImagen();
        
        if(evt.getButton()==evt.BUTTON1){
            
            jMBorrar.setEnabled(true);
            jMModif.setEnabled(true);
        }
        if(evt.getClickCount()==2){
            SolicitudDeProducto();
        }
    }//GEN-LAST:event_jTProductosMouseClicked
    public void SolicitudDeProducto(){
        
        /**
         * En la matriz.
         * Lista, se guardan los datos importantes del producto
         * cada fila es un producto
         * la columna
         * 0: Codigo del producto
         * 1: Cantidad del producto
         * 2: Precio de Venta del producto
         * 3: Descripcion del producto
         * 4: Precio de compra del producto
         */
        
        /**
         * Recordatorio...
         * En la tabla jTProductos las columnas
         * 0: Codigo de producto
         * 1: Numero de Fabrica
         * 2: Codigo Original
         * 3: Nombre del producto
         * 4: Marca del producto
         * 5: Existencia del producto
         * 6: Precio venta 1
         * 7: Precio Venta 2
         * 8: Precio Venta 3
         * 9: Precio venta 4
         * 10: Precio Compra
         */
        String codigo = "";
        String descripcion = "";
        String precio_compra = "";
        String cant = "";
        String exist = "";
        int productoSeleccionado = jTProductos.getSelectedRow();

        if(ventas || compras>0){
            codigo= String.valueOf(jTProductos.getValueAt(productoSeleccionado, 0));
            descripcion = String.valueOf(jTProductos.getValueAt(productoSeleccionado, 3))+ " " + String.valueOf(jTProductos.getValueAt(productoSeleccionado, 4));
            precio_compra = quitarPuntos(String.valueOf(jTProductos.getValueAt(productoSeleccionado, 10)));
            cant= JOptionPane.showInputDialog(rootPane, "Cantidad requerida", "Cantidad", JOptionPane.QUESTION_MESSAGE);
            exist = String.valueOf(jTProductos.getValueAt(productoSeleccionado, 5));
        }
        
        if(ventas){
                
                Lista[f][0]=codigo;
                while(Integer.valueOf(cant)>Integer.valueOf(quitarPuntos(exist))){
                    JOptionPane.showMessageDialog(rootPane, " Introduzca una cantidad menor o igual a la existencia de producto ", "Atención",JOptionPane.WARNING_MESSAGE);
                    cant= JOptionPane.showInputDialog(rootPane, "Cantidad requerida", "Cantidad", JOptionPane.QUESTION_MESSAGE);
                }
                String precio = (String) JOptionPane.showInputDialog(rootPane, "Precio de Venta", "Precio", JOptionPane.QUESTION_MESSAGE,null,null,String.valueOf(jTProductos.getValueAt(jTProductos.getSelectedRow(), 5+this.tipoCliente)));
                exist = String.valueOf(Integer.valueOf(quitarPuntos(exist)) - Integer.valueOf(cant));
                jTProductos.setValueAt(exist, productoSeleccionado, 5);
                Lista[f][1] = cant;
                Lista[f][2] = quitarPuntos(precio);
                Lista[f][3] = descripcion;
                Lista[f][4] = precio_compra;
                f++;
            }else if(compras==1){
                Lista[f][0]=codigo;
                String precio = (String) JOptionPane.showInputDialog(rootPane, "Precio de Compra", "Costo", JOptionPane.QUESTION_MESSAGE,null,null,String.valueOf(jTProductos.getValueAt(jTProductos.getSelectedRow(),10)));
                exist = String.valueOf(Integer.valueOf(exist) + Integer.valueOf(cant));
                jTProductos.setValueAt(exist, productoSeleccionado, 5);
                Lista[f][1] = cant;
                Lista[f][2] = quitarPuntos(precio);
                Lista[f][3] = descripcion;
                Lista[f][4] = quitarPuntos(String.valueOf(jTProductos.getValueAt(productoSeleccionado, 10)));
                f++;
            }else if(compras == 2){
                String precio = (String) JOptionPane.showInputDialog(rootPane, "Precio de Venta", "Precio", JOptionPane.QUESTION_MESSAGE, null, null, String.valueOf(jTProductos.getValueAt(jTProductos.getSelectedRow(), 5+this.tipoCliente)));
                Lista[f][0] = codigo;
                Lista[f][1] = quitarPuntos(precio);
                Lista[f][2] = cant;
                Lista[f][3] = descripcion;
                Lista[f][4] = quitarPuntos(String.valueOf(jTProductos.getValueAt(productoSeleccionado, 10)));;
                f++;
            }
    }
    private void jMModifKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMModifKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMModifKeyPressed

    private void jMBorrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMBorrarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMBorrarKeyPressed

    private void jBMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMenosActionPerformed
        mostrarImagen();
        zoom = zoom - 0.1;
        jXImageView1.setScale(zoom);
}//GEN-LAST:event_jBMenosActionPerformed

    private void jBMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMasActionPerformed
        mostrarImagen();
               
        zoom = zoom + 0.1;
        jXImageView1.setScale(zoom);
}//GEN-LAST:event_jBMasActionPerformed

    private void jBMasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jBMasFocusLost
        // TODO add your handling code here:
     //    jXImageView1.setImage(getToolkit().getImage(getClass().getResource("/imagenes/defaultlarge.gif")));
    }//GEN-LAST:event_jBMasFocusLost

    private void jBMenosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jBMenosFocusLost
        // TODO add your handling code here:
    
    }//GEN-LAST:event_jBMenosFocusLost

    private void jTbuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTbuscarMouseClicked
        // TODO add your handling code here:
        jXImageView1.setImage(getToolkit().getImage(getClass().getResource("/imagenes/defaultlarge.gif")));
    }//GEN-LAST:event_jTbuscarMouseClicked

    private void jTProductosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTProductosMousePressed
        // TODO add your handling code here:
                mostrarImagen();
    }//GEN-LAST:event_jTProductosMousePressed

    private void jTProductosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProductosKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_DELETE){
            jMBorrarActionPerformed(null);
        }
        if(evt.getKeyCode()== KeyEvent.VK_ESCAPE){
            this.hide();
        }
        mostrarImagen();
        if(evt.getKeyCode()==KeyEvent.VK_F4 && jBNuevo.isEnabled()){
            jBNuevoActionPerformed(null);
        }
        if(evt.getKeyCode()==KeyEvent.VK_F3){
            this.jTbuscar.requestFocus();
        }
        if(evt.getKeyCode()==KeyEvent.VK_F5 && jBModif.isEnabled()){
            jBModifActionPerformed(null);
        }
    }//GEN-LAST:event_jTProductosKeyReleased

    private void jBImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImprimirActionPerformed
                 try{
        
        r=null;
      
        if(!jCheckBox1.isSelected())
            r=db.Listar("*", "producto,proveedor", " where `producto`.`Proveedor_Id_proveedor`=`proveedor`.`Id_proveedor` and (Cod_inter_producto like \"%"+ jTbuscar.getText()+"%\" or Marca_fabrica like \"%"+ jTbuscar.getText()+"%\" or Cod_original like \"%"+ jTbuscar.getText()+"%\" or Nom_producto like \"%"+ jTbuscar.getText()+"%\" or Marca_producto like \"%"+ jTbuscar.getText()+"%\")");
        else
            r=db.Listar("*", "producto,proveedor", " where `producto`.`Proveedor_Id_proveedor`=`proveedor`.`Id_proveedor` and (Cod_inter_producto like \"%"+ jTbuscar.getText()+"%\" or Marca_fabrica like \"%"+ jTbuscar.getText()+"%\" or Cod_original like \"%"+ jTbuscar.getText()+"%\" or Nom_producto like \"%"+ jTbuscar.getText()+"%\" or Marca_producto like \"%"+ jTbuscar.getText()+"%\") and (Cant_producto<=cant_min)");
       
           
        } catch (Exception ex) {
             //Logger.getLogger(ciudad.class.getName()).log(Level.SEVERE, null, ex);
        }

        JRResultSetDataSource jrRS = new JRResultSetDataSource(r);
        HashMap parameters = new HashMap();
        parameters.put("titulo", "Listado de Productos");
        try{
            int resp = JOptionPane.showConfirmDialog(rootPane, "¿Deséa que el listado de productos contenga imágenes?", "Productos con su imagen", JOptionPane.YES_NO_OPTION);
            URL urlMaestro;
            if(resp==JOptionPane.YES_OPTION)
                urlMaestro = getClass().getClassLoader().getResource("reportes/productosconimagen.jasper");
            else
                urlMaestro = getClass().getClassLoader().getResource("reportes/productos.jasper");
            // Cargamos el reporte
            JasperReport masterReport = null;
            masterReport = (JasperReport) JRLoader.loadObject(urlMaestro);
            JasperPrint masterPrint = null;
            masterPrint = JasperFillManager.fillReport(masterReport,parameters,jrRS);
               
                
             JasperViewer ventana = new JasperViewer(masterPrint,false);
             pdf(r,parameters);
            ventana.setTitle("Vista Previa");
            ventana.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
            ventana.setVisible(true);

        }catch(JRException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrio un error "+e.toString(),"ATENCION ", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_jBImprimirActionPerformed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_formKeyReleased

    private void jBNuevoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBNuevoKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.setVisible(false);
        }
        if(evt.getKeyCode()==KeyEvent.VK_F3){
            this.jTbuscar.requestFocus();
        }
        if(evt.getKeyCode()==KeyEvent.VK_F4 && jBNuevo.isEnabled()){
            jBNuevoActionPerformed(null);
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_F5 && jBModif.isEnabled()){
            jBModifActionPerformed(null);
        }
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            jTProductos.requestFocus();
            jTProductos.selectAll();
        }
    }//GEN-LAST:event_jBNuevoKeyReleased

    private void jTbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTbuscarKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            jTProductos.requestFocus();
            jTProductos.selectAll();
        }
        if(evt.getKeyCode()==KeyEvent.VK_F3){
            this.jTbuscar.requestFocus();
        }
        if(evt.getKeyCode()==KeyEvent.VK_F4 && jBNuevo.isEnabled()){
            jBNuevoActionPerformed(null);
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_F5 && jBModif.isEnabled()){
            jBModifActionPerformed(null);
        }
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.setVisible(false);
        }
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            preparartabla();
        }
    }//GEN-LAST:event_jTbuscarKeyReleased

    private void jBMasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBMasKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_F4 && jBNuevo.isEnabled()){
            jBNuevoActionPerformed(null);
        }
        if(evt.getKeyCode()==KeyEvent.VK_F3){
            this.jTbuscar.requestFocus();
        }
        if(evt.getKeyCode()==KeyEvent.VK_F5 && jBModif.isEnabled()){
            jBModifActionPerformed(null);
        }
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.setVisible(false);
        }
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            jTProductos.requestFocus();
            jTProductos.selectAll();
        }
    }//GEN-LAST:event_jBMasKeyReleased

    private void jBModifKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBModifKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_F4 && jBNuevo.isEnabled()){
            jBNuevoActionPerformed(null);
        }
        if(evt.getKeyCode()==KeyEvent.VK_F3){
            this.jTbuscar.requestFocus();
        }
        if(evt.getKeyCode()==KeyEvent.VK_F5 && jBModif.isEnabled()){
            jBModifActionPerformed(null);
        }
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.setVisible(false);
        }
        if(evt.getKeyCode()==KeyEvent.VK_DOWN || evt.getKeyCode()==KeyEvent.VK_UP){
            jTProductos.requestFocus();
            jTProductos.selectAll();
        }
    }//GEN-LAST:event_jBModifKeyReleased
    public Object getValueFromTableAt(int row,int column){
        return jTProductos.getValueAt(row, column);
    }
    public void setValueToTableAt(int row,int column,Object value){
        jTProductos.setValueAt(value, row, column);
    }
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        jButton1.setVisible(false);
        contadorVentana++;
    }//GEN-LAST:event_formWindowOpened

    private void jTProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTProductosKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
                jTbuscar.requestFocus();   
        }
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            SolicitudDeProducto();
        }
    }//GEN-LAST:event_jTProductosKeyPressed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        preparartabla();
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jTbuscarMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jTbuscarMouseWheelMoved
         if(evt.getPreciseWheelRotation()>0){
            zoom-=0.1;
            jXImageView1.setScale(zoom);
        }else{
            zoom+=0.1;
            jXImageView1.setScale(zoom);
        }
        
    }//GEN-LAST:event_jTbuscarMouseWheelMoved

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ResultSet rs= db.Listar("pre_venta2,pre_venta3,pre_venta4,cod_inter_producto", "producto", "");
        try {
            while(rs.next()){
                db.actualizarRegistro("producto", "pre_venta2='"+rs.getInt(1)*1000+"', pre_venta3='"+rs.getInt(2)*1000+"',pre_venta4='"+rs.getInt(3)*1000+"'", " where cod_inter_producto='"+rs.getString(4)+"'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(jDProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTbuscarCaretUpdate(null);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTtotalCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTtotalCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTtotalCompraActionPerformed

    public void mostrarImagen(){ 
        String codigo;
        codigo = (String) jTProductos.getValueAt(jTProductos.getSelectedRow(), 0);
        ResultSet rs = db.Listar("*", "producto", "where Cod_inter_producto = '"+codigo+"'");
        
        try{
            rs.next();
            Blob blob = (Blob) rs.getBlob("imagen");
            byte [] data = blob.getBytes(1,(int) blob.length());
            BufferedImage img = null;
            img = ImageIO.read(new ByteArrayInputStream(data));
            jXImageView1.setImage(img);
            jXImageView1.setScale(zoom);
            jLUbicacion.setText(rs.getString("ubica_producto"));
            jLDetalle.setText(rs.getString("detalle_producto"));
            
        } catch ( Exception ex){
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public boolean isNumber(String Campo){
        String Text = Campo;
        final String Numb = "1234567890.";
        boolean band=false;
        for ( int i = 0; i<Text.length();i++){
            for(int j = 0; j<Numb.length(); j++ ){
                if(Text.charAt(i)==Numb.charAt(j)){
                    band = band || true;
                    break;
                }else{
                    band = band || false;
                }
                
            }
            if(!band){
                    return false;
                }else{
                    band = true;
                }
        }
        return band;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                jDProductos dialog = new jDProductos(new javax.swing.JFrame(), true,null);
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
    private javax.swing.JButton jBImprimir;
    private javax.swing.JButton jBMas;
    private javax.swing.JButton jBMenos;
    private javax.swing.JButton jBModif;
    private javax.swing.JButton jBNuevo;
    private javax.swing.JButton jBSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLDetalle;
    private javax.swing.JLabel jLUbicacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLcantidad;
    private javax.swing.JMenuItem jMBorrar;
    private javax.swing.JMenuItem jMModif;
    private javax.swing.JPanel jPBotones;
    private javax.swing.JPanel jPBusqueda;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPmenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTProductos;
    private javax.swing.JTextField jTbuscar;
    private javax.swing.JTextField jTtotalCompra;
    private org.jdesktop.swingx.JXImageView jXImageView1;
    // End of variables declaration//GEN-END:variables

   
}
