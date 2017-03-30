/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jDnProducto.java
 *
 * Created on 20/09/2012, 01:38:39 PM
 */
package frames;

import clases.DBConnect;
import com.mysql.jdbc.Blob;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileView;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Edgar
 */
public class jDModProducto extends javax.swing.JDialog {
    private String Usuario;
    private String Password;
    private String Host;
    private String Puerto;
    private String Database;
    DBConnect db;
    private int i=0;
    private String proveedor;
    String codigo1;
    double zoom = 1.0;
    Image imagen = getToolkit().getImage(getClass().getResource("/imagenes/global2.png"));
    
     boolean control;
     
     boolean CTRL_PRESED = false;
    private BufferedImage image;
    /** Creates new form jDnProducto */
    public jDModProducto(java.awt.Frame parent, boolean modal,DBConnect con, String codigo) {
        super(parent, modal);
        initComponents();
         setLocationRelativeTo(null);
        setTitle("Modificar Producto");
        this.Usuario=con.getUser();
        this.Password=con.getPass();
        db= con;
        setIconImage(imagen);
        codigo1=codigo;
        actualizarCampoMarca();
        actualizarCombo();
        actualizarComboCategoria();
        llenarCampos();
        
        calcularPuntos(jTPrecVendProd);
        calcularPuntos(jTExistencia);
       
        
    }
   /**
    * Encripta el Parámetro costo con un código que determina una Letra para cada número.
    * @param costo
    * @return Enc
    */
    
   
    
    
    private void calcularPuntos(JTextField campo) {
        String antes="";
        String despues="";
        String ahora = campo.getText();
        campo.setText(quitarPuntos(campo.getText()));
        if(!"".equals(campo.getText())){
            
            int numero = Integer.valueOf(campo.getText());
            
            if(numero>999){
                antes = campo.getText().substring(0, campo.getText().length()-3);
                 despues = campo.getText().substring(campo.getText().length()-3, campo.getText().length());
               
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
            }else{
                ahora = quitarPuntos(campo.getText());
            }
        }
        campo.setText(ahora);
    }
    
    public String quitarPuntos(String campo){
        String ahora="";
        for(int k =0; k<campo.length();k++){
            if(campo.charAt(k)!='.'){
                ahora =ahora + campo.charAt(k);
            }
        }
        return ahora;
        
    }
    
    public void actualizarCampoMarca(){
     
        List<String> lista = new ArrayList<String>();
        try {
           ResultSet rs=null;
            rs= db.Listar("Marca_producto", "producto", "");
            while(rs.next()){
                lista.add(rs.getString(1));
            }
            AutoCompleteDecorator.decorate(jTMarcProducto,lista,false);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void llenarCampos(){
        ResultSet rs = null,r = null;
        String provee;
        String categoria_id;
        String itemComp;
         jtCodInt.setText(codigo1);
        rs = db.Listar("*", "producto", "where Cod_inter_producto = '"+jtCodInt.getText()+"'");
        
       
        
        try{
            rs.next();
            
            r = db.Listar("*", "proveedor", "where Id_proveedor = '"+rs.getString("Proveedor_Id_proveedor")+"'");
            r.next();
            
            jTMarcFabrica.setText(rs.getString("Marca_Fabrica"));
            jtOriginal.setText(rs.getString("Cod_Original"));
            jTNomProd.setText(rs.getString("Nom_Producto"));
            jTMarcProducto.setText(rs.getString("Marca_producto"));
            jtDetProd.setText(rs.getString("detalle_producto"));
            jTUbicacion.setText(rs.getString("ubica_producto"));
            jTCantMin.setText(rs.getString("cant_min"));
            jTExistencia.setText(rs.getString("Cant_producto"));
            jTPrecVendProd.setText(rs.getString("pre_venta1"));
            jTPrecVendProd1.setText(rs.getString("pre_venta2"));
            jTPrecVendProd2.setText(rs.getString("pre_venta3"));
            jTPrecVendProd3.setText(rs.getString("pre_venta4"));
            jTPrecCompProd.setText(rs.getString("pre_compra"));
            jTRuta.setText(rs.getString("ruta_imagen"));
            mostrarImagen();
            
            /**
             * Desde aqui, se determina cual es el proveedor del documento que
             * se desea modificar, se consulta en base de datos, y se compara
             * con los valores presentes en el combo hasta encontrar el que
             * debe ser mostrado.
             */
            
            provee = String.valueOf(r.getString("Nom_proveedor")+" ,"+r.getString("Id_proveedor"));
            categoria_id = String.valueOf(rs.getString("categoria_id"));
            
            int hasta = jCProveedor.getItemCount();
            
            
            
            itemComp ="";
            for(int i = 0; i<hasta; i++){
                jCProveedor.setSelectedIndex(i);
                itemComp = String.valueOf(jCProveedor.getSelectedItem());
                if(itemComp.equals(provee)){
                    break;
                }
            }
            
            hasta = jCBCategoria.getItemCount();
            itemComp ="";
            if(categoria_id != null){
                for(int i = 0; i<hasta; i++){
                    jCBCategoriaId.setSelectedIndex(i);
                    jCBCategoria.setSelectedIndex(i);
                    itemComp = String.valueOf(jCBCategoriaId.getSelectedItem());
                    System.out.println("ItemComp = "+itemComp);
                    System.out.println("Categoria_id = "+ categoria_id);
                    System.out.println("Itemcomp = Categoria_id ->" + String.valueOf(itemComp.equals(categoria_id)));
                    if(itemComp.equals(categoria_id)){
                        break;
                    }else{
                        jCBCategoria.setSelectedIndex(0);
                        jCBCategoriaId.setSelectedIndex(0);
                    }
                }
            }
            
        }catch(SQLException ex){
        }
        
    }
    
    public void mostrarImagen(){
        String codigo;
        codigo = jtCodInt.getText();
        ResultSet rs = db.Listar("*", "producto", "where Cod_inter_producto = '"+codigo+"'");
        
        try{
            rs.next();
            Blob blob = (Blob) rs.getBlob("imagen");
            byte [] data = blob.getBytes(1,(int) blob.length());
            BufferedImage img = null;
            img = ImageIO.read(new ByteArrayInputStream(data));
            jXImageView1.setImage(img);
            
            
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
    
    public String borrarLetra(String Text){
        
        for ( int i = 0 ; i<Text.length();i++){
            if(!isNumber(Text.charAt(i)+"")){
               Text = Text.substring(0,i+1) + Text.substring(i+2);
            }
        }
        return Text;
    }
    
    public void actualizarCombo(){
        try {
            
            ResultSet r=null;
            r= db.Listar("*", "proveedor", "");
            while(r.next()){
                jCProveedor.addItem(r.getString("Nom_proveedor")+" ,"+r.getString("Id_proveedor"));
            }
            AutoCompleteDecorator.decorate(jCProveedor);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void actualizarComboCategoria(){
        try {
            ResultSet r=null;
            r= db.Listar("*", "categoria_producto", "");
            while(r.next()){
                jCBCategoria.addItem(r.getString("nombre_categoria"));
                jCBCategoriaId.addItem(r.getString("codigo_categoria"));
            }
            AutoCompleteDecorator.decorate(jCBCategoria);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
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

        jCBCategoriaId = new javax.swing.JComboBox();
        jPDatos = new javax.swing.JPanel();
        jLCodInt = new javax.swing.JLabel();
        jtCodInt = new javax.swing.JTextField();
        jLNomProd = new javax.swing.JLabel();
        jTNomProd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTPrecCompProd = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTPrecVendProd = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jCProveedor = new javax.swing.JComboBox();
        jBAddProveedor = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jtDetProd = new javax.swing.JTextField();
        jLMarcFabrica = new javax.swing.JLabel();
        jTMarcFabrica = new javax.swing.JTextField();
        jLOriginal = new javax.swing.JLabel();
        jtOriginal = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTRuta = new javax.swing.JTextField();
        jBExaminar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTExistencia = new javax.swing.JTextField();
        jTUbicacion = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTMarcProducto = new org.jdesktop.swingx.JXTextField();
        jLabel11 = new javax.swing.JLabel();
        jTCantMin = new javax.swing.JTextField();
        jTPrecVendProd1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTPrecVendProd2 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTPrecVendProd3 = new javax.swing.JTextField();
        jBExaminar1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jCBCategoria = new javax.swing.JComboBox();
        jBAddProveedor1 = new javax.swing.JButton();
        jPbotones = new javax.swing.JPanel();
        jBAceptar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLNuevo = new javax.swing.JLabel();
        jXImageView1 = new org.jdesktop.swingx.JXImageView();
        jLabel2 = new javax.swing.JLabel();
        jBMas = new javax.swing.JButton();
        jBMenos = new javax.swing.JButton();

        jCBCategoriaId.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
        jCBCategoriaId.setToolTipText("");
        jCBCategoriaId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBCategoriaIdActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPDatos.setFocusCycleRoot(true);

        jLCodInt.setText("Codigo Interno:");

        jtCodInt.setEditable(false);
        jtCodInt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtCodIntKeyReleased(evt);
            }
        });

        jLNomProd.setText("Nombre del Producto:");

        jTNomProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTNomProdKeyReleased(evt);
            }
        });

        jLabel3.setText("Marca del Producto:");

        jLabel4.setText("Precio De Compra:");

        jTPrecCompProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPrecCompProdFocusLost(evt);
            }
        });
        jTPrecCompProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTPrecCompProdKeyReleased(evt);
            }
        });

        jLabel5.setText("Precio De Venta 1:");

        jTPrecVendProd.setText("0");
        jTPrecVendProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPrecVendProdFocusLost(evt);
            }
        });
        jTPrecVendProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPrecVendProdKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTPrecVendProdKeyReleased(evt);
            }
        });

        jLabel6.setText("Proveedor:");

        jCProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCProveedorActionPerformed(evt);
            }
        });

        jBAddProveedor.setText("+");
        jBAddProveedor.setToolTipText("Agregar Proveedor");
        jBAddProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddProveedorActionPerformed(evt);
            }
        });

        jLabel8.setText("Detalle del Producto:");

        jtDetProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtDetProdKeyReleased(evt);
            }
        });

        jLMarcFabrica.setText("Número de Fábrica:");

        jTMarcFabrica.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMarcFabricaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTMarcFabricaKeyReleased(evt);
            }
        });

        jLOriginal.setText("Original:");

        jtOriginal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtOriginalKeyReleased(evt);
            }
        });

        jLabel1.setText("Imagen:");

        jTRuta.setEditable(false);
        jTRuta.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTRutaCaretUpdate(evt);
            }
        });

        jBExaminar.setText("Examinar");
        jBExaminar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jBExaminarStateChanged(evt);
            }
        });
        jBExaminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExaminarActionPerformed(evt);
            }
        });

        jLabel9.setText("Ubicación de Producto:");

        jTExistencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTExistenciaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTExistenciaKeyReleased(evt);
            }
        });

        jTUbicacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTUbicacionKeyReleased(evt);
            }
        });

        jLabel10.setText("Existencia:");

        jTMarcProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTMarcProductoKeyReleased(evt);
            }
        });

        jLabel11.setText("Cantidad mínima:");

        jTCantMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCantMinKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTCantMinKeyReleased(evt);
            }
        });

        jTPrecVendProd1.setText("0");
        jTPrecVendProd1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPrecVendProd1FocusLost(evt);
            }
        });
        jTPrecVendProd1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPrecVendProd1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTPrecVendProd1KeyReleased(evt);
            }
        });

        jLabel12.setText("Precio De Venta 2:");

        jTPrecVendProd2.setText("0");
        jTPrecVendProd2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPrecVendProd2FocusLost(evt);
            }
        });
        jTPrecVendProd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPrecVendProd2KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTPrecVendProd2KeyReleased(evt);
            }
        });

        jLabel13.setText("Precio De Venta 3:");

        jLabel14.setText("Precio De Venta 4:");

        jTPrecVendProd3.setText("0");
        jTPrecVendProd3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPrecVendProd3FocusLost(evt);
            }
        });
        jTPrecVendProd3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPrecVendProd3KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTPrecVendProd3KeyReleased(evt);
            }
        });

        jBExaminar1.setText("Pegar");
        jBExaminar1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jBExaminar1StateChanged(evt);
            }
        });
        jBExaminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBExaminar1ActionPerformed(evt);
            }
        });

        jLabel15.setText("Categoria:");

        jCBCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
        jCBCategoria.setToolTipText("");
        jCBCategoria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCBCategoriaItemStateChanged(evt);
            }
        });
        jCBCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBCategoriaActionPerformed(evt);
            }
        });

        jBAddProveedor1.setText("+");
        jBAddProveedor1.setToolTipText("Agregar Proveedor");
        jBAddProveedor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddProveedor1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPDatosLayout = new javax.swing.GroupLayout(jPDatos);
        jPDatos.setLayout(jPDatosLayout);
        jPDatosLayout.setHorizontalGroup(
            jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDatosLayout.createSequentialGroup()
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDatosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBExaminar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPDatosLayout.createSequentialGroup()
                                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPDatosLayout.createSequentialGroup()
                                        .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPDatosLayout.createSequentialGroup()
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(31, 31, 31)
                                                .addComponent(jTRuta)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jBExaminar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPDatosLayout.createSequentialGroup()
                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(16, 16, 16)
                                                .addComponent(jCProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(jPDatosLayout.createSequentialGroup()
                                        .addGap(141, 141, 141)
                                        .addComponent(jCBCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)))
                                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jBAddProveedor)
                                    .addComponent(jBAddProveedor1)))))
                    .addGroup(jPDatosLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPDatosLayout.createSequentialGroup()
                                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTPrecVendProd1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                                    .addComponent(jTPrecVendProd2)
                                    .addComponent(jTPrecVendProd3)))
                            .addGroup(jPDatosLayout.createSequentialGroup()
                                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLMarcFabrica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLOriginal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLNomProd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLCodInt, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTMarcProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtCodInt)
                                    .addComponent(jTMarcFabrica, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtOriginal, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTNomProd, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDatosLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtDetProd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTExistencia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTCantMin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTPrecCompProd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTPrecVendProd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                .addContainerGap())
        );
        jPDatosLayout.setVerticalGroup(
            jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDatosLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtCodInt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLCodInt, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTMarcFabrica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLMarcFabrica, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtOriginal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLOriginal, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTNomProd)
                    .addComponent(jLNomProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(jTMarcProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtDetProd, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTCantMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTPrecCompProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTPrecVendProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPDatosLayout.createSequentialGroup()
                        .addComponent(jTPrecVendProd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)))
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDatosLayout.createSequentialGroup()
                        .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTPrecVendProd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTPrecVendProd3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCBCategoria)
                            .addComponent(jLabel15)
                            .addComponent(jBAddProveedor1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDatosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addGap(37, 37, 37)))
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBAddProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(jBExaminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBExaminar1))
        );

        jPbotones.setLayout(new java.awt.GridLayout(1, 0));

        jBAceptar.setText("Guardar");
        jBAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAceptarActionPerformed(evt);
            }
        });
        jPbotones.add(jBAceptar);

        jBCancelar.setText("Cancelar");
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });
        jPbotones.add(jBCancelar);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Products_32x32.png"))); // NOI18N

        jLNuevo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLNuevo.setText("Modificar Producto");

        javax.swing.GroupLayout jXImageView1Layout = new javax.swing.GroupLayout(jXImageView1);
        jXImageView1.setLayout(jXImageView1Layout);
        jXImageView1Layout.setHorizontalGroup(
            jXImageView1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 319, Short.MAX_VALUE)
        );
        jXImageView1Layout.setVerticalGroup(
            jXImageView1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 295, Short.MAX_VALUE)
        );

        jLabel2.setText("Vista Previa de Producto:");

        jBMas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lensTool3.png"))); // NOI18N
        jBMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMasActionPerformed(evt);
            }
        });

        jBMenos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lensToolM3.png"))); // NOI18N
        jBMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBMenosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLNuevo))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(jPbotones, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(10, 10, 10)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jBMas, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(jBMenos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jXImageView1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLNuevo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXImageView1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jBMas)
                            .addComponent(jBMenos)))
                    .addComponent(jPDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPbotones, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jBAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAceptarActionPerformed
       FileInputStream foto=null;
       ResultSet r=null;
       
        if (!jtCodInt.getText().equals("")) {
            try {
                 foto = new FileInputStream(jTRuta.getText());
               
            } catch (FileNotFoundException ex) {
                
            }
            jTPrecVendProd.setText(quitarPuntos(jTPrecVendProd.getText()));
            jTPrecVendProd1.setText(quitarPuntos(jTPrecVendProd1.getText()));
            jTPrecVendProd2.setText(quitarPuntos(jTPrecVendProd2.getText()));
            jTPrecVendProd3.setText(quitarPuntos(jTPrecVendProd3.getText()));
            jTExistencia.setText(quitarPuntos(jTExistencia.getText()));
            String [] valores = new String[18];
            valores[0] = jTMarcFabrica.getText();
            valores[1] = jtOriginal.getText();
            valores[2] = jTNomProd.getText();
            valores[3] = jTMarcProducto.getText();
            valores[4] = jtDetProd.getText();
            valores[5] = jTUbicacion.getText();
            valores[6] = jTExistencia.getText();
            valores[7] = jTCantMin.getText();
            valores[8] = jTPrecVendProd.getText();
            valores[9] = jTPrecVendProd1.getText();
            valores[10] = jTPrecVendProd2.getText();
            valores[11] = jTPrecVendProd3.getText();
            valores[12] = jTPrecCompProd.getText();
            if(jCBCategoriaId.getSelectedIndex()>0)
                valores[13] = String.valueOf(jCBCategoriaId.getSelectedItem());
            else
                valores[13] = null;
            int i;
            i =String.valueOf( jCProveedor.getSelectedItem()).indexOf(",");
            valores[14] = String.valueOf(jCProveedor.getSelectedItem()).substring(i+1);
            valores[15] = jTNomProd.getText();
            valores[16] = jTRuta.getText();
            valores[17] = jtCodInt.getText();
             boolean band=false;
            if(control){
                 band = db.actualizarRegistro(valores, foto);
            }else{
                band = db.actualizarRegistro("producto", "Marca_Fabrica= '"+ jTMarcFabrica.getText()+"' , Cod_Original = '"+ jtOriginal.getText()+"',"
                    + "Nom_producto = '"+ jTNomProd.getText() +"', Marca_producto = '"+ jTMarcProducto.getText()+"', detalle_producto = '"+ jtDetProd.getText()+"' , ubica_producto= '"+ jTUbicacion.getText()+"' , cant_producto ="+ jTExistencia.getText()+","
                    + "pre_venta1= "+ jTPrecVendProd.getText()+ " ,pre_venta2= "+ jTPrecVendProd1.getText()+" ,pre_venta3= "+ jTPrecVendProd2.getText()+ " ,pre_venta4= "+ jTPrecVendProd3.getText()+ " , cant_min = "+ jTCantMin.getText()+", pre_compra = '"+ jTPrecCompProd.getText()+"', Proveedor_Id_proveedor = '"+ valores[14] ,"' where Cod_inter_producto = '"+jtCodInt.getText()+"'");
                if(jCBCategoriaId.getSelectedIndex()>0){
                    band = db.actualizarRegistro("producto",
                            "categoria_id= '"+ jCBCategoriaId.getSelectedItem().toString()+"' "
                            ," where Cod_inter_producto = '"+jtCodInt.getText()+"'");
                }else{
                    band = db.actualizarRegistro("producto",
                            "categoria_id= NULL "
                            ," where Cod_inter_producto = '"+jtCodInt.getText()+"'");
                }
            }
            if(band){
                    
                        JOptionPane.showMessageDialog(null, "Producto modificado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                       // new jDProductos(null, true, Usuario, Password).setVisible(true);
                  
                    
            }else{
                JOptionPane.showMessageDialog(null, "No se pudo modificar el Producto", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El campo Codigo del Producto no debe estar vacio","Atencion",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jBAceptarActionPerformed

    private void jBMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMasActionPerformed
        zoom = zoom + 0.1;
        jXImageView1.setScale(zoom);
    }//GEN-LAST:event_jBMasActionPerformed

    private void jBMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMenosActionPerformed
        zoom = zoom - 0.1;
        jXImageView1.setScale(zoom);
    }//GEN-LAST:event_jBMenosActionPerformed

    private void jTPrecVendProd2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProd2KeyReleased
        if(evt.getKeyCode()==evt.VK_ENTER){
            jTPrecVendProd3.requestFocus();
        }
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
        calcularPuntos(jTPrecVendProd2);
    }//GEN-LAST:event_jTPrecVendProd2KeyReleased

    private void jTPrecVendProd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTPrecVendProd2KeyPressed

    private void jTPrecVendProd1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProd1KeyReleased
        if(evt.getKeyCode()==evt.VK_ENTER){
            jTPrecVendProd2.requestFocus();
        }
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
        calcularPuntos(jTPrecVendProd1);
    }//GEN-LAST:event_jTPrecVendProd1KeyReleased

    private void jTPrecVendProd1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProd1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTPrecVendProd1KeyPressed

    private void jTCantMinKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantMinKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jTPrecCompProd.requestFocus();
        }

        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }

        if(isNumber(jTCantMin.getText())){
            calcularPuntos(jTCantMin);
        }
    }//GEN-LAST:event_jTCantMinKeyReleased

    private void jTCantMinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantMinKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTCantMinKeyPressed

    private void jTMarcProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMarcProductoKeyReleased
        if(evt.getKeyCode()==evt.VK_ENTER){
            jtDetProd.requestFocus();
        }
    }//GEN-LAST:event_jTMarcProductoKeyReleased

    private void jTUbicacionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUbicacionKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            jTExistencia.requestFocus();
        }
    }//GEN-LAST:event_jTUbicacionKeyReleased

    private void jTExistenciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExistenciaKeyReleased
        if(evt.getKeyCode()==evt.VK_ENTER){
            jTCantMin.requestFocus();
        }
        if(isNumber(jTExistencia.getText())){
            calcularPuntos(jTExistencia);
            //jTExistencia.setText(jTExistencia.getText().substring(0,jTExistencia.getText().length()-1));\\
        }
    }//GEN-LAST:event_jTExistenciaKeyReleased

    private void jTExistenciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExistenciaKeyPressed

    }//GEN-LAST:event_jTExistenciaKeyPressed

    private void jBExaminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExaminarActionPerformed
        File archive = obtenerArchivo();
        control = true;
        if (archive.exists() && archive != null)
        jTRuta.setText(archive.getPath());
    }//GEN-LAST:event_jBExaminarActionPerformed

    private void jBExaminarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jBExaminarStateChanged

    }//GEN-LAST:event_jBExaminarStateChanged

    private void jTRutaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTRutaCaretUpdate
        Image imagen = getToolkit().getImage(jTRuta.getText());
        File file = new File(jTRuta.getText());
        try {
            jXImageView1.setImage(file);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTRutaCaretUpdate

    private void jtOriginalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtOriginalKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            jTNomProd.requestFocus();
            jtOriginal.setText(jtOriginal.getText().toUpperCase());
        }
    }//GEN-LAST:event_jtOriginalKeyReleased

    private void jTMarcFabricaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMarcFabricaKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            jtOriginal.requestFocus();
            jTMarcFabrica.setText(jTMarcFabrica.getText().toUpperCase());
        }

        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_jTMarcFabricaKeyReleased

    private void jTMarcFabricaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMarcFabricaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_CONTROL){
            this.dispose();
        }
    }//GEN-LAST:event_jTMarcFabricaKeyPressed

    private void jtDetProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtDetProdKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            jTUbicacion.requestFocus();

        }
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
        jtDetProd.setText(jtDetProd.getText().toUpperCase());
    }//GEN-LAST:event_jtDetProdKeyReleased

    private void jBAddProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddProveedorActionPerformed

        jCProveedor.removeAllItems();
        new jDnProveedor(null, true, db, 2).setVisible(true);
        actualizarCombo();
    }//GEN-LAST:event_jBAddProveedorActionPerformed

    private void jCProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCProveedorActionPerformed

    private void jTPrecVendProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProdKeyReleased
        if(evt.getKeyCode()==evt.VK_ENTER){
            jTPrecVendProd1.requestFocus();
            jTPrecVendProd1.selectAll();
        }
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
        calcularPuntos(jTPrecVendProd);
    }//GEN-LAST:event_jTPrecVendProdKeyReleased

    private void jTPrecVendProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProdKeyPressed

    }//GEN-LAST:event_jTPrecVendProdKeyPressed

    private void jTPrecCompProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecCompProdKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }
        if(isNumber(jTPrecCompProd.getText())){
            calcularPuntos(jTPrecCompProd);
        }

        if(evt.getKeyCode()==evt.VK_ENTER){
            jTPrecVendProd.requestFocus();
            jTPrecVendProd.selectAll();
        }
    }//GEN-LAST:event_jTPrecCompProdKeyReleased

    private void jTNomProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomProdKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==evt.VK_ENTER){
            jTMarcProducto.requestFocus();
        }
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_jTNomProdKeyReleased

    private void jtCodIntKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtCodIntKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_jtCodIntKeyReleased

    private void jTPrecVendProd3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProd3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTPrecVendProd3KeyPressed

    private void jTPrecVendProd3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProd3KeyReleased
        if(evt.getKeyCode()==evt.VK_ENTER){
            jCProveedor.requestFocus();
        }
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
        calcularPuntos(jTPrecVendProd3);
    }//GEN-LAST:event_jTPrecVendProd3KeyReleased

    private void jTPrecCompProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPrecCompProdFocusLost
        jTPrecVendProd.requestFocus();
        jTPrecVendProd.selectAll();
    }//GEN-LAST:event_jTPrecCompProdFocusLost

    private void jTPrecVendProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPrecVendProdFocusLost
        jTPrecVendProd1.requestFocus();
        jTPrecVendProd1.selectAll();
    }//GEN-LAST:event_jTPrecVendProdFocusLost

    private void jTPrecVendProd1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPrecVendProd1FocusLost
        jTPrecVendProd2.requestFocus();
        jTPrecVendProd2.selectAll();
    }//GEN-LAST:event_jTPrecVendProd1FocusLost

    private void jTPrecVendProd2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPrecVendProd2FocusLost
        jTPrecVendProd3.requestFocus();
        jTPrecVendProd3.selectAll();
    }//GEN-LAST:event_jTPrecVendProd2FocusLost

    private void jTPrecVendProd3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPrecVendProd3FocusLost
        jCProveedor.requestFocus();
    }//GEN-LAST:event_jTPrecVendProd3FocusLost

    private void jBExaminar1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jBExaminar1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jBExaminar1StateChanged

    private void jBExaminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExaminar1ActionPerformed

        Double random=Math.random();
        jTRuta.setText(System.getProperty("user.home") + File.separator + "tmp"+random+".jpg");

        Clipboard imagenport = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable datos = imagenport.getContents(null);
        try {
            DataFlavor dfImagen  = new DataFlavor("image/x-java-image; class=java.awt.Image");

            if(datos.isDataFlavorSupported(dfImagen)==true){
                image = (BufferedImage) datos.getTransferData(dfImagen);
                File f = new File(System.getProperty("user.home") +  File.separator +"tmp"+random+".jpg");
                ImageIO.write(image, "jpg", f);
                jXImageView1.setImage(image);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(jDnProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedFlavorException ex) {
            Logger.getLogger(jDnProducto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(jDnProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBExaminar1ActionPerformed

    private void jCBCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBCategoriaActionPerformed

    private void jCBCategoriaIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCategoriaIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBCategoriaIdActionPerformed

    private void jBAddProveedor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddProveedor1ActionPerformed
        new jDNewCatProducto(null, true, db).setVisible(true);
        jCBCategoria.removeAllItems();
        jCBCategoriaId.removeAllItems();
        jCBCategoria.addItem("");
        jCBCategoriaId.addItem("");
        actualizarComboCategoria();
    }//GEN-LAST:event_jBAddProveedor1ActionPerformed

    private void jCBCategoriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCBCategoriaItemStateChanged
        int index =  jCBCategoria.getSelectedIndex();
        if(jCBCategoria.getItemCount() == jCBCategoriaId.getItemCount())
            jCBCategoriaId.setSelectedIndex(index);
            System.out.println("HOLA");
        
    }//GEN-LAST:event_jCBCategoriaItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                jDModProducto dialog = new jDModProducto(new javax.swing.JFrame(), true,null,"");
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    public File obtenerArchivo(){
       int resultado = 0;
       JFileChooser elegir = new JFileChooser();
       
       FileView fv = new FileView() {};
       
       elegir.setFileView(fv);
       
       elegir.setFileSelectionMode(JFileChooser.FILES_ONLY);
       resultado = elegir.showOpenDialog(this);
       if(resultado == JFileChooser.CANCEL_OPTION){
           return null;
       }
       File archivo = elegir.getSelectedFile();
       if(archivo == null){
           JOptionPane.showMessageDialog(null, "Error al cargar archivo",
                        "Error", JOptionPane.ERROR_MESSAGE);
       }
       return archivo;
   }
    
    public void abrir(){
       File archive = obtenerArchivo();
       
       if (archive.exists() && archive != null){
           jTRuta.setText(archive.getPath());
           if (archive.isFile()){
               try {
                   BufferedReader input = new BufferedReader (new FileReader(archive));
                   
               } catch (IOException ioException){
                   JOptionPane.showMessageDialog(this, "Error carnal:"+ ioException);
               }
           }
       }
   }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAceptar;
    private javax.swing.JButton jBAddProveedor;
    private javax.swing.JButton jBAddProveedor1;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBExaminar;
    private javax.swing.JButton jBExaminar1;
    private javax.swing.JButton jBMas;
    private javax.swing.JButton jBMenos;
    private javax.swing.JComboBox jCBCategoria;
    private javax.swing.JComboBox jCBCategoriaId;
    private javax.swing.JComboBox jCProveedor;
    private javax.swing.JLabel jLCodInt;
    private javax.swing.JLabel jLMarcFabrica;
    private javax.swing.JLabel jLNomProd;
    private javax.swing.JLabel jLNuevo;
    private javax.swing.JLabel jLOriginal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPDatos;
    private javax.swing.JPanel jPbotones;
    private javax.swing.JTextField jTCantMin;
    private javax.swing.JTextField jTExistencia;
    private javax.swing.JTextField jTMarcFabrica;
    private org.jdesktop.swingx.JXTextField jTMarcProducto;
    private javax.swing.JTextField jTNomProd;
    private javax.swing.JTextField jTPrecCompProd;
    private javax.swing.JTextField jTPrecVendProd;
    private javax.swing.JTextField jTPrecVendProd1;
    private javax.swing.JTextField jTPrecVendProd2;
    private javax.swing.JTextField jTPrecVendProd3;
    private javax.swing.JTextField jTRuta;
    private javax.swing.JTextField jTUbicacion;
    private org.jdesktop.swingx.JXImageView jXImageView1;
    private javax.swing.JTextField jtCodInt;
    private javax.swing.JTextField jtDetProd;
    private javax.swing.JTextField jtOriginal;
    // End of variables declaration//GEN-END:variables
}
