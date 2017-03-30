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
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Edgar
 */
public class jDnProducto extends javax.swing.JDialog {
    private String Usuario;
    private String Password;
    private String Host;
    private String Puerto;
    private String Database;
    DBConnect db;
    private int i=0;
    private String proveedor;
    double zoom = 1.0;
    boolean CTRL_PRESED=false;
    boolean from;
    BufferedImage image;
    Image imagen = getToolkit().getImage(getClass().getResource("/imagenes/global2.png"));
    /** Creates new form jDnProducto */
    public jDnProducto(java.awt.Frame parent, boolean modal,DBConnect con, boolean fromCompras) {
        super(parent, modal);
        initComponents();
         setLocationRelativeTo(null);
         setIconImage(imagen);
        setTitle("Nuevo Producto");
        
        
        
        this.Usuario=con.getUser();
        this.Password=con.getPass();
        db= con;
        
        actualizarCombo();
        actualizarComboCategoria();
        actualizarCampoMarca();
        generaCodigo();
        from = fromCompras;
        Image imagen = getToolkit().getImage(getClass().getResource("/imagenes/defaultlarge.gif"));
        jXImageView1.setImage(imagen);
    }
    
    
    public void vaciar(){
            generaCodigo();
            jTMarcFabrica.setText("");
            jtOriginal.setText("");
            jTNomProd.setText("");
            jTMarcProd.setText("");
            jtDetProd.setText("");
            jTUbicacion.setText("");
            jTExistencia.setText("");
            jTPrecVendProd.setText("");
            jTPrecCompProd.setText("");
            jTRuta.setText("");
            jTCantMin.setText("");
            
            jTMarcFabrica.requestFocus();
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
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error 106",JOptionPane.ERROR_MESSAGE);
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
    
    public void generaCodigo(){
        ResultSet r = null;
        int nro=1;
        r=db.Listar("Cod_inter_producto", "producto", "order by Cod_inter_producto ASC");
        try {
            
            if(r.last())
            nro = Integer.valueOf(r.getString(1).substring(2))+1;
            if(nro>0 && nro <=9){
                jtCodInt.setText("MB0000000"+nro);
            }
            if(nro>=10 && nro<=99){
                jtCodInt.setText("MB000000"+nro);
            }
            if(nro>=100 && nro<=999){
                jtCodInt.setText("MB00000"+nro);
            }
            if(nro>=1000 && nro<=9999){
                jtCodInt.setText("MB0000"+nro);
            }
            if(nro>=10000 && nro<=99999){
                jtCodInt.setText("MB000"+nro);
            }
            if(nro>=100000 && nro<=999999){
                jtCodInt.setText("MB00"+nro);
            }
            if(nro>=1000000 && nro<=9999999){
                jtCodInt.setText("MB0"+nro);
            }
            if(nro>=10000000 && nro<=99999999){
                jtCodInt.setText("MB"+nro);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    /*
     Se debe cambiar el tipo de dato de precio compra en base de datos
     */
    
    /**
    * Encripta el Parámetro costo con un código que determina una Letra para cada número.
    * @param costo
    * @return Enc
    */
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
    
    public void actualizarCampoMarca(){
     
        List<String> lista = new ArrayList<String>();
        try {
           ResultSet rs=null;
            rs= db.Listar("Marca_producto", "producto", "");
            while(rs.next()){
                lista.add(rs.getString(1));
            }
            AutoCompleteDecorator.decorate(jTMarcProd,lista,false);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error 212",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void calcularPuntos(JTextField campo) {
        String antes="";
        String despues="";
        String ahora = campo.getText();
        
        if(!"".equals(campo.getText())){
            
            int numero = Integer.valueOf(quitarPuntos(campo.getText()));
            
            if(numero>999){
                antes = quitarPuntos(campo.getText()).substring(0, quitarPuntos(campo.getText()).length()-3);
                 despues = quitarPuntos(campo.getText()).substring(quitarPuntos(campo.getText()).length()-3,quitarPuntos(campo.getText()).length());
               
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
    
    public String quitarPuntos(String campo){
        String ahora="";
        for(int k =0; k<campo.length();k++){
            if(campo.charAt(k)!='.'){
                ahora =ahora + campo.charAt(k);
            }
        }
        return ahora;
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
        jLabel8 = new javax.swing.JLabel();
        jtDetProd = new javax.swing.JTextField();
        jLMarcFabrica = new javax.swing.JLabel();
        jTMarcFabrica = new javax.swing.JTextField();
        jLOriginal = new javax.swing.JLabel();
        jtOriginal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTExistencia = new javax.swing.JTextField();
        jTUbicacion = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTMarcProd = new org.jdesktop.swingx.JXTextField();
        jLabel11 = new javax.swing.JLabel();
        jTCantMin = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTPrecVendProd1 = new javax.swing.JTextField();
        jTPrecVendProd2 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTPrecVendProd3 = new javax.swing.JTextField();
        jPbotones = new javax.swing.JPanel();
        jBAceptar = new javax.swing.JButton();
        jBCancelar = new javax.swing.JButton();
        jBMenos = new javax.swing.JButton();
        jBMas = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLNuevo = new javax.swing.JLabel();
        jXImageView1 = new org.jdesktop.swingx.JXImageView();
        jLabel2 = new javax.swing.JLabel();
        jCProveedor = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jBAddProveedor = new javax.swing.JButton();
        jTRuta = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jBExaminar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jCBCategoria = new javax.swing.JComboBox();
        jBAddCategoria = new javax.swing.JButton();

        jCBCategoriaId.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
        jCBCategoriaId.setToolTipText("");
        jCBCategoriaId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBCategoriaIdActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLCodInt.setText("Codigo Interno:");

        jtCodInt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtCodIntKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtCodIntKeyReleased(evt);
            }
        });

        jLNomProd.setText("Nombre del Producto:");

        jTNomProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTNomProdKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTNomProdKeyReleased(evt);
            }
        });

        jLabel3.setText("Marca del Producto:");

        jLabel4.setText("Precio De Compra:");

        jTPrecCompProd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTPrecCompProdFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTPrecCompProdFocusLost(evt);
            }
        });
        jTPrecCompProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTPrecCompProdKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTPrecCompProdKeyReleased(evt);
            }
        });

        jLabel5.setText("Precio De Venta1:");

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

        jLabel8.setText("Detalle del Producto:");

        jtDetProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtDetProdKeyPressed(evt);
            }
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
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtOriginalKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtOriginalKeyReleased(evt);
            }
        });

        jLabel9.setText("Ubicación de Producto:");

        jTExistencia.setToolTipText("");
        jTExistencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTExistenciaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTExistenciaKeyReleased(evt);
            }
        });

        jTUbicacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTUbicacionKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTUbicacionKeyReleased(evt);
            }
        });

        jLabel10.setText("Existencia:");

        jTMarcProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTMarcProdKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTMarcProdKeyReleased(evt);
            }
        });

        jLabel11.setText("Cantidad Mínima:");

        jTCantMin.setToolTipText("");
        jTCantMin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTCantMinKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTCantMinKeyReleased(evt);
            }
        });

        jLabel12.setText("Precio De Venta2:");

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

        javax.swing.GroupLayout jPDatosLayout = new javax.swing.GroupLayout(jPDatos);
        jPDatos.setLayout(jPDatosLayout);
        jPDatosLayout.setHorizontalGroup(
            jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPDatosLayout.createSequentialGroup()
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDatosLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLNomProd, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(jLOriginal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLCodInt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPDatosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLMarcFabrica, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTExistencia, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addComponent(jTUbicacion)
                    .addComponent(jtDetProd)
                    .addComponent(jTMarcProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTNomProd)
                    .addComponent(jtOriginal)
                    .addComponent(jTMarcFabrica)
                    .addComponent(jtCodInt))
                .addGap(26, 26, 26))
            .addGroup(jPDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDatosLayout.createSequentialGroup()
                        .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPDatosLayout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTCantMin, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPDatosLayout.createSequentialGroup()
                                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(25, 25, 25)
                                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTPrecVendProd, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTPrecCompProd, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(25, Short.MAX_VALUE))
                    .addGroup(jPDatosLayout.createSequentialGroup()
                        .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPDatosLayout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(jTPrecVendProd1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPDatosLayout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(jTPrecVendProd2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPDatosLayout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(jTPrecVendProd3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPDatosLayout.setVerticalGroup(
            jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDatosLayout.createSequentialGroup()
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPDatosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jtCodInt)
                        .addGap(8, 8, 8)
                        .addComponent(jTMarcFabrica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPDatosLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLCodInt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(8, 8, 8)
                        .addComponent(jLMarcFabrica, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)))
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLOriginal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtOriginal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTNomProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLNomProd, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTMarcProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtDetProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPDatosLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTUbicacion)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDatosLayout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(8, 8, 8))
                    .addGroup(jPDatosLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jTExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPDatosLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addGroup(jPDatosLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jTCantMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTPrecCompProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTPrecVendProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTPrecVendProd1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTPrecVendProd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTPrecVendProd3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPbotones.setLayout(new java.awt.GridLayout(1, 0));

        jBAceptar.setText("Agregar");
        jBAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAceptarActionPerformed(evt);
            }
        });
        jPbotones.add(jBAceptar);

        jBCancelar.setText("Terminar");
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });
        jPbotones.add(jBCancelar);

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

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Products_32x32.png"))); // NOI18N

        jLNuevo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLNuevo.setText("Nuevo Producto");

        javax.swing.GroupLayout jXImageView1Layout = new javax.swing.GroupLayout(jXImageView1);
        jXImageView1.setLayout(jXImageView1Layout);
        jXImageView1Layout.setHorizontalGroup(
            jXImageView1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jXImageView1Layout.setVerticalGroup(
            jXImageView1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 306, Short.MAX_VALUE)
        );

        jLabel2.setText("Vista Previa de Producto:");

        jCProveedor.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCProveedorItemStateChanged(evt);
            }
        });
        jCProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCProveedorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jCProveedorKeyReleased(evt);
            }
        });

        jLabel6.setText("Proveedor:");

        jBAddProveedor.setText("+");
        jBAddProveedor.setToolTipText("Agregar Proveedor");
        jBAddProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddProveedorActionPerformed(evt);
            }
        });
        jBAddProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jBAddProveedorKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jBAddProveedorKeyReleased(evt);
            }
        });

        jTRuta.setEditable(false);
        jTRuta.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTRutaCaretUpdate(evt);
            }
        });
        jTRuta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTRutaKeyReleased(evt);
            }
        });

        jLabel1.setText("Imagen:");

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
        jBExaminar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jBExaminarKeyReleased(evt);
            }
        });

        jButton1.setText("Pegar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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

        jBAddCategoria.setText("+");
        jBAddCategoria.setToolTipText("Agregar Proveedor");
        jBAddCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAddCategoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLNuevo))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 21, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBMas, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBMenos, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(143, 143, 143))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jXImageView1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPbotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(293, 293, 293))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCBCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jCProveedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jBExaminar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jBAddProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                                    .addComponent(jBAddCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLNuevo)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(42, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(5, 5, 5)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jXImageView1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jBMenos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBMas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPDatos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBCategoria)
                    .addComponent(jLabel15)
                    .addComponent(jBAddCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBAddProveedor)
                    .addComponent(jCProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBExaminar)
                    .addComponent(jTRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPbotones, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        this.dispose();
        
    }//GEN-LAST:event_jBCancelarActionPerformed

    private void jBAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAceptarActionPerformed
       FileInputStream foto=null;
       Date hoy = new Date();
       String sHoy = String.valueOf(hoy.getYear()+1900)+"-"+String.valueOf(hoy.getMonth()+1)+"-"+String.valueOf(hoy.getDate());
       ResultSet r=null;
            jTPrecVendProd.setText(quitarPuntos(jTPrecVendProd.getText()));
            jTPrecVendProd1.setText(quitarPuntos(jTPrecVendProd1.getText()));
            jTPrecVendProd2.setText(quitarPuntos(jTPrecVendProd2.getText()));
            jTPrecVendProd3.setText(quitarPuntos(jTPrecVendProd3.getText()));
            jTExistencia.setText(quitarPuntos(jTExistencia.getText()));
        if (!jtCodInt.getText().equals("")) {
            try {
                 foto = new FileInputStream(jTRuta.getText());
               
            } catch (FileNotFoundException ex) {
               // JOptionPane.showMessageDialog(jTPrecCompProd, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            if(!"".equals(jTRuta.getText())){
                String [] valores = new String[17];
                valores[0] = jtCodInt.getText();
                valores[1] = jTMarcFabrica.getText();
                valores[2] = jtOriginal.getText();
                valores[3] = jTNomProd.getText();
                valores[4] = jTMarcProd.getText();
                valores[5] = jtDetProd.getText();
                valores[6] = jTUbicacion.getText();
                valores[7] = jTExistencia.getText();
                valores[8] = jTCantMin.getText();
                valores[9] = jTPrecVendProd.getText();
                valores[10] = jTPrecVendProd1.getText();
                valores[11] = jTPrecVendProd2.getText();
                valores[12] = jTPrecVendProd3.getText();

                valores[13] = jTPrecCompProd.getText();
                int i;
                i =String.valueOf( jCProveedor.getSelectedItem()).indexOf(",");
                valores[14] = String.valueOf(jCProveedor.getSelectedItem()).substring(i+1);
                valores[15] = sHoy;
                valores[16] = jTRuta.getText();
            
               
            
                boolean band = db.Altas(valores, foto);
                if(band){
                        db.actualizarRegistro("producto", "categoria_id="+jCBCategoriaId.getSelectedItem().toString(), "where Cod_inter_producto="+jtCodInt.getText());
                        JOptionPane.showMessageDialog(null, "El producto se agregó exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
                        vaciar();
                }else{
                    JOptionPane.showMessageDialog(null, "No se pudo agregar el Producto", "Error 837", JOptionPane.ERROR_MESSAGE);
                }
            }else{
              
                i =String.valueOf( jCProveedor.getSelectedItem()).indexOf(",");
                boolean band = db.Altas("producto", "", "('"+jtCodInt.getText()+"', '"+jTMarcFabrica.getText()+"','"+jtOriginal.getText()+"' , '"+jTNomProd.getText()
                        +"','"+jTMarcProd.getText()+"','"+jCBCategoriaId.getSelectedItem().toString()+"','"+jtDetProd.getText()+"','"+jTUbicacion.getText()+"',"+jTExistencia.getText()+","+jTCantMin.getText()+","+jTPrecVendProd.getText()
                        +","+jTPrecVendProd1.getText()+","+jTPrecVendProd2.getText()+","+jTPrecVendProd3.getText()+",'"+jTPrecCompProd.getText()+"','"+String.valueOf(jCProveedor.getSelectedItem()).substring(i+1)+"','"+sHoy+"','','')");
                if(band){
                         JOptionPane.showMessageDialog(null, "El producto se agregó exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
                         vaciar();
                }else{
                    JOptionPane.showMessageDialog(null, "No se pudo agregar el Producto", "Error 849", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        } else {
            JOptionPane.showMessageDialog(null, "El campo Codigo del Producto no debe estar vacio","Atencion",JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jBAceptarActionPerformed

    private void jBMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMenosActionPerformed
        zoom = zoom - 0.1;
        jXImageView1.setScale(zoom);
}//GEN-LAST:event_jBMenosActionPerformed

    private void jBMenosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jBMenosFocusLost
        // TODO add your handling code here:
        //     jXImageView1.setImage(getToolkit().getImage(getClass().getResource("/imagenes/defaultlarge.gif")));
}//GEN-LAST:event_jBMenosFocusLost

    private void jBMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBMasActionPerformed
       
        
        zoom = zoom + 0.1;
        jXImageView1.setScale(zoom);
        
}//GEN-LAST:event_jBMasActionPerformed

    private void jBMasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jBMasFocusLost
        // TODO add your handling code here:
        //    jXImageView1.setImage(getToolkit().getImage(getClass().getResource("/imagenes/defaultlarge.gif")));
}//GEN-LAST:event_jBMasFocusLost

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
            jTMarcFabrica.requestFocus();
            
            ResultSet rs = null;
            
            rs = db.Listar("cant_min", "producto", "");
            
            if(rs == null){
                db.consulta("ALTER TABLE `dbeasysoft`.`producto` ADD COLUMN `cant_min` INT NULL  AFTER `cant_producto` ;");
                
                db.actualizarRegistro("producto", "cant_min= 2", "");
            }
                 
            
    }//GEN-LAST:event_formWindowActivated

    private void jTCantMinKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantMinKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER || evt.getKeyCode()==KeyEvent.VK_TAB){
            jTPrecCompProd.requestFocus();
            calcularPuntos(jTCantMin);
        }

        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = false;
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }

        if(isNumber(jTExistencia.getText())){
            calcularPuntos(jTExistencia);
        }
    }//GEN-LAST:event_jTCantMinKeyReleased

    private void jTCantMinKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTCantMinKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = true;
        }
        
        if(CTRL_PRESED&&(evt.getKeyCode()==KeyEvent.VK_G)){
            jBAceptarActionPerformed(null);
        }
    }//GEN-LAST:event_jTCantMinKeyPressed

    private void jTMarcProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMarcProdKeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER || evt.getKeyCode()==KeyEvent.VK_TAB){
            jtDetProd.requestFocus();
            jTMarcProd.setText(jTMarcProd.getText().toUpperCase());
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = false;
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }

    }//GEN-LAST:event_jTMarcProdKeyReleased

    private void jTMarcProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMarcProdKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = true;
        }
        
        if(CTRL_PRESED&&(evt.getKeyCode()==KeyEvent.VK_G)){
            jBAceptarActionPerformed(null);
        }
    }//GEN-LAST:event_jTMarcProdKeyPressed

    private void jTUbicacionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUbicacionKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER || evt.getKeyCode()==KeyEvent.VK_TAB){
            jTExistencia.requestFocus();
        }

        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = false;
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }

        jTUbicacion.setText(jTUbicacion.getText().toUpperCase());
    }//GEN-LAST:event_jTUbicacionKeyReleased

    private void jTUbicacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTUbicacionKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = true;
        }
        
        if(CTRL_PRESED&&(evt.getKeyCode()==KeyEvent.VK_G)){
            jBAceptarActionPerformed(null);
        }
    }//GEN-LAST:event_jTUbicacionKeyPressed

    private void jTExistenciaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExistenciaKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER || evt.getKeyCode()==KeyEvent.VK_TAB){
            jTCantMin.requestFocus();
        }

        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = false;
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }

        if(isNumber(jTExistencia.getText())){
            calcularPuntos(jTExistencia);
        }
    }//GEN-LAST:event_jTExistenciaKeyReleased

    private void jTExistenciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTExistenciaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = true;
        }
        
        if(CTRL_PRESED&&(evt.getKeyCode()==KeyEvent.VK_G)){
            jBAceptarActionPerformed(null);
        }
    }//GEN-LAST:event_jTExistenciaKeyPressed

    private void jBExaminarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBExaminarKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_jBExaminarKeyReleased

    private void jBExaminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBExaminarActionPerformed
        File archive = obtenerArchivo();

        if (archive.exists() && archive != null)
        jTRuta.setText(archive.getPath());
    }//GEN-LAST:event_jBExaminarActionPerformed

    private void jBExaminarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jBExaminarStateChanged

    }//GEN-LAST:event_jBExaminarStateChanged

    private void jTRutaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTRutaKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_jTRutaKeyReleased

    private void jTRutaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTRutaCaretUpdate

        Image imagen = getToolkit().getImage(jTRuta.getText());
        File file = new File(jTRuta.getText());
        try {
            jXImageView1.setImage(file);

        } catch (IOException ex) {
            Logger.getLogger(jDnProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTRutaCaretUpdate

    private void jtOriginalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtOriginalKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER || evt.getKeyCode()==KeyEvent.VK_TAB){
            jTNomProd.requestFocus();
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = false;
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }

        jtOriginal.setText(jtOriginal.getText().toUpperCase());
    }//GEN-LAST:event_jtOriginalKeyReleased

    private void jtOriginalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtOriginalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = true;
        }
        
        if(CTRL_PRESED&&(evt.getKeyCode()==KeyEvent.VK_G)){
            jBAceptarActionPerformed(null);
        }
    }//GEN-LAST:event_jtOriginalKeyPressed

    private void jTMarcFabricaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMarcFabricaKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jtOriginal.requestFocus();
        }

        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = false;
        }
        jTMarcFabrica.setText(jTMarcFabrica.getText().toUpperCase());
    }//GEN-LAST:event_jTMarcFabricaKeyReleased

    private void jTMarcFabricaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTMarcFabricaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = true;
        }
        
        if(CTRL_PRESED&&(evt.getKeyCode()==KeyEvent.VK_G)){
            jBAceptarActionPerformed(null);
        }
    }//GEN-LAST:event_jTMarcFabricaKeyPressed

    private void jtDetProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtDetProdKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER || evt.getKeyCode()==KeyEvent.VK_TAB){
            jTUbicacion.requestFocus();
        }

        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
           CTRL_PRESED = false;
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }
        jtDetProd.setText(jtDetProd.getText().toUpperCase());
    }//GEN-LAST:event_jtDetProdKeyReleased

    private void jtDetProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtDetProdKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = true;
        }
        
        if(CTRL_PRESED&&(evt.getKeyCode()==KeyEvent.VK_G)){
            jBAceptarActionPerformed(null);
        }
    }//GEN-LAST:event_jtDetProdKeyPressed

    private void jBAddProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAddProveedorKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = false;
        }
    }//GEN-LAST:event_jBAddProveedorKeyReleased

    private void jBAddProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddProveedorActionPerformed

        jCProveedor.removeAllItems();
        new jDnProveedor(null, true, db, 2).setVisible(true);
        actualizarCombo();
    }//GEN-LAST:event_jBAddProveedorActionPerformed

    private void jCProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCProveedorKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = false;
        }
    }//GEN-LAST:event_jCProveedorKeyReleased

    private void jCProveedorItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCProveedorItemStateChanged
        //    proveedor = (String) jCProveedor.getSelectedItem();
        //        proveedor = proveedor.substring(proveedor.indexOf(",")+1);
    }//GEN-LAST:event_jCProveedorItemStateChanged

    private void jTPrecVendProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProdKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTPrecVendProd1.requestFocus();
            jTPrecVendProd1.selectAll();
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = false;
        }
        
        calcularPuntos(jTPrecVendProd);
    }//GEN-LAST:event_jTPrecVendProdKeyReleased

    private void jTPrecVendProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProdKeyPressed
        calcularPuntos(jTPrecVendProd);
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = true;
        }
        
        if(CTRL_PRESED&&(evt.getKeyCode()==KeyEvent.VK_G)){
            jBAceptarActionPerformed(null);
        }
    }//GEN-LAST:event_jTPrecVendProdKeyPressed

    private void jTPrecCompProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecCompProdKeyReleased

        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }

     
        if(isNumber(jTPrecCompProd.getText())){
            calcularPuntos(jTPrecCompProd);
        }

        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = false;
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTPrecVendProd.requestFocus();
            jTPrecVendProd.selectAll();
        }

    }//GEN-LAST:event_jTPrecCompProdKeyReleased

    private void jTPrecCompProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecCompProdKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_TAB){
            calcularPuntos(jTPrecCompProd);
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = true;
        }
        
        if(CTRL_PRESED&&(evt.getKeyCode()==KeyEvent.VK_G)){
            jBAceptarActionPerformed(null);
        }
    }//GEN-LAST:event_jTPrecCompProdKeyPressed

    private void jTNomProdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomProdKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER || evt.getKeyCode()==KeyEvent.VK_TAB){
            jTMarcProd.requestFocus();
        }

        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = false;
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }
        jTNomProd.setText(jTNomProd.getText().toUpperCase());
    }//GEN-LAST:event_jTNomProdKeyReleased

    private void jTNomProdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTNomProdKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = true;
        }
        
        if(CTRL_PRESED&&(evt.getKeyCode()==KeyEvent.VK_G)){
            jBAceptarActionPerformed(null);
        }
    }//GEN-LAST:event_jTNomProdKeyPressed

    private void jtCodIntKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtCodIntKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER || evt.getKeyCode()==KeyEvent.VK_TAB){
            jTMarcFabrica.requestFocus();
        }
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = false;
        }
    }//GEN-LAST:event_jtCodIntKeyReleased

    private void jTPrecCompProdFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPrecCompProdFocusGained

    }//GEN-LAST:event_jTPrecCompProdFocusGained

    private void jtCodIntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtCodIntKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = true;
        }
        if(CTRL_PRESED&&(evt.getKeyCode()==KeyEvent.VK_G)){
            jBAceptarActionPerformed(null);
        }
    }//GEN-LAST:event_jtCodIntKeyPressed

    private void jCProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCProveedorKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = true;
        }
        
        if(CTRL_PRESED&&(evt.getKeyCode()==KeyEvent.VK_G)){
            jBAceptarActionPerformed(null);
        }
    }//GEN-LAST:event_jCProveedorKeyPressed

    private void jBAddProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBAddProveedorKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = true;
        }
        
        if(CTRL_PRESED&&(evt.getKeyCode()==KeyEvent.VK_G)){
            jBAceptarActionPerformed(null);
        }
    }//GEN-LAST:event_jBAddProveedorKeyPressed

    private void jTPrecVendProd1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProd1KeyPressed
        
    }//GEN-LAST:event_jTPrecVendProd1KeyPressed

    private void jTPrecVendProd1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProd1KeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTPrecVendProd2.requestFocus();
            jTPrecVendProd2.selectAll();
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = false;
        }
        calcularPuntos(jTPrecVendProd1);
    }//GEN-LAST:event_jTPrecVendProd1KeyReleased

    private void jTPrecVendProd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTPrecVendProd2KeyPressed

    private void jTPrecVendProd2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProd2KeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jTPrecVendProd3.requestFocus();
            jTPrecVendProd3.selectAll();
        }
        
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = false;
        }
        
        calcularPuntos(jTPrecVendProd2);
        
    }//GEN-LAST:event_jTPrecVendProd2KeyReleased

    private void jTPrecVendProd3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProd3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTPrecVendProd3KeyPressed

    private void jTPrecVendProd3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTPrecVendProd3KeyReleased
       if(evt.getKeyCode()==KeyEvent.VK_ENTER){
           jCProveedor.requestFocus();
          
       }
        
        if(evt.getKeyCode()==KeyEvent.VK_CONTROL){
            CTRL_PRESED = false;
        }
        
        calcularPuntos(jTPrecVendProd3);
        
    }//GEN-LAST:event_jTPrecVendProd3KeyReleased

    private void jTPrecCompProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPrecCompProdFocusLost
        int precio = Integer.valueOf(jTPrecCompProd.getText());
        calcularPuntos(jTPrecCompProd);
        jTPrecVendProd.selectAll();
    }//GEN-LAST:event_jTPrecCompProdFocusLost

    private void jTPrecVendProd1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPrecVendProd1FocusLost
        calcularPuntos(jTPrecVendProd1);
        jTPrecVendProd2.selectAll();
    }//GEN-LAST:event_jTPrecVendProd1FocusLost

    private void jTPrecVendProd2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPrecVendProd2FocusLost
        calcularPuntos(jTPrecVendProd2);
        jTPrecVendProd3.selectAll();
    }//GEN-LAST:event_jTPrecVendProd2FocusLost

    private void jTPrecVendProd3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPrecVendProd3FocusLost
        calcularPuntos(jTPrecVendProd3);
    }//GEN-LAST:event_jTPrecVendProd3FocusLost

    private void jTPrecVendProdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTPrecVendProdFocusLost
        calcularPuntos(jTPrecVendProd1);
        jTPrecVendProd1.selectAll();
    }//GEN-LAST:event_jTPrecVendProdFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                
        
        Double random=Math.random();
        jTRuta.setText(System.getProperty("user.home") + File.separator +"tmp"+random+".jpg"); 
        
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
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCBCategoriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCBCategoriaItemStateChanged
        int index =  jCBCategoria.getSelectedIndex();
        if(jCBCategoria.getItemCount() == jCBCategoriaId.getItemCount())
        jCBCategoriaId.setSelectedIndex(index);
        System.out.println("HOLA");

    }//GEN-LAST:event_jCBCategoriaItemStateChanged

    private void jCBCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBCategoriaActionPerformed

    private void jBAddCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAddCategoriaActionPerformed
        new jDNewCatProducto(null, true, db).setVisible(true);
        jCBCategoria.removeAllItems();
        jCBCategoriaId.removeAllItems();
        jCBCategoria.addItem("");
        jCBCategoriaId.addItem("");
        actualizarComboCategoria();
    }//GEN-LAST:event_jBAddCategoriaActionPerformed

    private void jCBCategoriaIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBCategoriaIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBCategoriaIdActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                jDnProducto dialog = new jDnProducto(new javax.swing.JFrame(), true,null,true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    public File obtenerArchivo(){
       int resultado;
       
       JFileChooser elegir = new JFileChooser();
       elegir.setFileSelectionMode(JFileChooser.FILES_ONLY);
       resultado = elegir.showOpenDialog(this);
       if(resultado == JFileChooser.CANCEL_OPTION){
           return null;
       }
       File archivo = elegir.getSelectedFile();
       if(archivo == null){
           JOptionPane.showMessageDialog(null, "Error al cargar archivo",
                        "Error 1318", JOptionPane.ERROR_MESSAGE);
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
    private javax.swing.JButton jBAddCategoria;
    private javax.swing.JButton jBAddProveedor;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JButton jBExaminar;
    private javax.swing.JButton jBMas;
    private javax.swing.JButton jBMenos;
    private javax.swing.JButton jButton1;
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
    private org.jdesktop.swingx.JXTextField jTMarcProd;
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
