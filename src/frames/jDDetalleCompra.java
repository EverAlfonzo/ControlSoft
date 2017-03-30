/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * jDDetalleCompra.java
 *
 * Created on 29/09/2012, 03:47:29 PM
 */
package frames;

import clases.DBConnect;
import java.awt.Image;

import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
public class jDDetalleCompra extends javax.swing.JDialog {
    DBConnect db;
    ResultSet r;
    String id_compra;
    DefaultTableModel m;
    int precio =0;
    Image imagen = getToolkit().getImage(getClass().getResource("/imagenes/global2.png"));
    /** Creates new form jDDetalleCompra */
    public jDDetalleCompra(java.awt.Frame parent, boolean modal,DBConnect con,String id_compra) {
        super(parent, modal);
        initComponents();
        setIconImage(imagen);
        this.setLocationRelativeTo(null);
        this.setTitle("Detalle de Compra");
        this.db=con;
        this.id_compra=id_compra;
        jtNumCompra.setText(id_compra);
        preparartabla();
        calcultotal();
        this.jtNumCompra.setEditable(false);
        this.jTPrecioTotal.setEditable(false);
    }
    private void preparartabla() {
         r=null;
         r=db.Listar("*", "vistacompra3", "where Id_compra='"+id_compra+"'");
            //String titulos[]={"Cod. Cliente", "Nombre", "Telefono", "Direccion"};
            m =(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "Cod. Producto", "Cant. Compra", "Descripcion", "Precio Compra", "Precio Total"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            jTableClientes.setModel(m);
            String fila[] = new String[5];
            try {
            while(r.next()){
                fila[0]=r.getString("Cod_inter_producto");
                fila[1]=r.getString("Cant_adquirida");
                fila[2]=r.getString("Nom_producto")+" "+r.getString("Marca_producto")+" "+r.getString("detalle_producto");
                fila[3]=r.getString("Pre_compra");
                fila[4]=calcularPuntos(String.valueOf(Integer.valueOf(quitarPuntos(r.getString("Pre_compra")))*Integer.valueOf(r.getString("Cant_adquirida"))));
                m.addRow(fila);
            }

        } catch (Exception ex) {
           JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
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
    
    public String quitarPuntos(String campo){
        String ahora="";
        for(int k =0; k<campo.length();k++){
            if(campo.charAt(k)!='.'){
                ahora =ahora + campo.charAt(k);
            }
        }
        return (ahora);
        
    }
    
    public void calcultotal(){
        int filas = jTableClientes.getRowCount();
        
        for(int i =0; i<filas; i++){
            int pre= Integer.valueOf(quitarPuntos(String.valueOf(jTableClientes.getValueAt(i, 4))));
            precio+=pre;
        }
        jTPrecioTotal.setText(calcularPuntos(String.valueOf(precio)));
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
        jTableClientes = new javax.swing.JTable();
        jBImprimir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jtNumCompra = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTPrecioTotal = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTableClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cod. Producto", "Cant. Compra", "Descripcion", "Precio Compra", "Precio Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableClientes.setName("jTableClientes"); // NOI18N
        jTableClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableClientesMouseReleased(evt);
            }
        });
        jTableClientes.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTableClientesPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(jTableClientes);

        jBImprimir.setText("Imprimir Listado");
        jBImprimir.setName("jBImprimir"); // NOI18N
        jBImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBImprimirActionPerformed(evt);
            }
        });

        jLabel1.setText("Codigo De Compra:");
        jLabel1.setName("jLabel1"); // NOI18N

        jtNumCompra.setName("jtNumCompra"); // NOI18N

        jLabel2.setText("Total:");
        jLabel2.setName("jLabel2"); // NOI18N

        jTPrecioTotal.setText("0");
        jTPrecioTotal.setName("jTPrecioTotal"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtNumCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTPrecioTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtNumCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jTPrecioTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTableClientesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableClientesMouseReleased
        
}//GEN-LAST:event_jTableClientesMouseReleased

    private void jTableClientesPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTableClientesPropertyChange
        
}//GEN-LAST:event_jTableClientesPropertyChange

    private void jBImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBImprimirActionPerformed
        try{
            r=null;
            r=db.Listar("*", "vistacompra3", "where Id_compra='"+id_compra+"'");
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
         
          Integer SumaTotal = Integer.valueOf(quitarPuntos(jTPrecioTotal.getText()));
          String NumCompra = jtNumCompra.getText();
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
}//GEN-LAST:event_jBImprimirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                jDDetalleCompra dialog = new jDDetalleCompra(new javax.swing.JFrame(), true,null,"");
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTPrecioTotal;
    private javax.swing.JTable jTableClientes;
    private javax.swing.JTextField jtNumCompra;
    // End of variables declaration//GEN-END:variables
}
