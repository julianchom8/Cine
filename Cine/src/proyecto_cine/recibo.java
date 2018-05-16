/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_cine;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author usuario
 */
public class recibo extends javax.swing.JFrame {

    /**
     * Creates new form recibo
     */
    private static Font fontBold = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
    private static Font fontNormal = new Font(Font.FontFamily.COURIER, 11, Font.NORMAL);
    
    public recibo() {
        initComponents();
    }
public void crearpdf(){
    
     String datos[][]=new String[3][3];
     datos[0][0]="";
     datos[0][1]="";
     datos[0][2]="$ 7.000";
     datos[1][0]="";
     datos[1][1]="";
     datos[1][2]="";
     
     String rutaimagen;
     
     try {
   generarFactura(datos, "", "CineUDEC |  |   ");
  } catch (IOException | DocumentException e) {
   e.printStackTrace();
  }
     JOptionPane.showMessageDialog(null, "PDF Generado!");
     
    
    
    }
    
    public void generarFactura(String [][]datos, String nombre, String tituloFactura) throws IOException, DocumentException {
     String rutaImagen=null;
      Document document = getDocument();
      PdfWriter.getInstance(document, new FileOutputStream("factura.pdf"));
      document.open();
      
      PdfPTable table = getTable();
      factura img = new factura();
      //img.generarpdf("C:\\Users\\usuario\\Documents\\NetBeansProjects\\proyecto cine\\proyecto cine\\Proyecto_cine\\src\\imagenes");
      
    document.add(getHeader(tituloFactura));
    document.add(getInformation(" "));
        
        table.addCell(getCell("SALA"));
        table.addCell(getCell("HORARIO"));
        table.addCell(getCell("PRECIO"));
        
        for(int i=0;i<datos.length;i++){
         for(int j=0;j<datos[0].length;j++){
          if(datos[i][j]!=null){
           table.addCell(getCell(datos[i][j]));
          }
         }
        }
        
        table.addCell(getCell(" "));
        table.addCell(getCell(" "));
        table.addCell(getCell(" "));
         String impr = this.nombre.getText();
         String impr2 = telefono.getText();
        String impr3 = documento.getText(); 
         
         
        document.add(table);
        document.add(getInformation("Nombre:"+impr));
        document.add(getInformation("Telefono:"+impr2));
        document.add(getInformation("Documento"+impr3));
        document.add(getInformationFooter("Gracias por visitarnos!"));
        document.add(getInformationFooter("velvas prontos!"));
        
            document.close();
      
     }
    public void generarpdf(String header,String info,String footer,String rutaImagen,String salida){
        try {
            Document doc = new Document(PageSize.A4, 36,36,10,10);
            PdfWriter.getInstance(doc,new FileOutputStream(salida));
            doc.open();
            doc.add(getHeader(header));
            Image imagen = Image.getInstance(rutaImagen);
            imagen.scaleAbsolute(100,100);
            imagen.setAlignment(Element.ALIGN_CENTER);
            doc.add(imagen);
            doc.add(getInformation(info));
            doc.add(getInformationFooter(footer));
            doc.close();
        } catch (Exception e) {
        }
    }
     
     private Document getDocument(){
     Document document = new Document(new Rectangle( getConvertCmsToPoints(13), getConvertCmsToPoints(7)));
       document.setMargins(0, 0, 1, 1);
       return document;
     }
     
     private Paragraph getHeader(String header) {
     Paragraph paragraph = new Paragraph();
    Chunk chunk = new Chunk();
  paragraph.setAlignment(Element.ALIGN_CENTER);
    chunk.append( header + getCurrentDateTime() + "\n");
    chunk.setFont(fontBold);
    paragraph.add(chunk);
    return paragraph;
     }
     
     private Paragraph getInformation(String informacion) {
     Paragraph paragraph = new Paragraph();
     Chunk chunk = new Chunk();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    chunk.append(informacion);
    chunk.setFont(fontNormal);
    paragraph.add(chunk);
     return paragraph;
      }
     
     private Paragraph getInformationFooter(String informacion) {
      Paragraph paragraph = new Paragraph();
      Chunk chunk = new Chunk();
     paragraph.setAlignment(Element.ALIGN_CENTER);
     chunk.append(informacion);
     chunk.setFont(new Font(Font.FontFamily.COURIER, 8, Font.NORMAL));
     paragraph.add(chunk);
      return paragraph;
       }
  
     private PdfPTable getTable() throws DocumentException {
      PdfPTable table = new PdfPTable(3);
      table.setWidths(new int[]{5, 17, 17});
  return table;
     }
     
     private PdfPCell getCell(String text) throws DocumentException, IOException {
      Chunk chunk = new Chunk();
      chunk.append(text);
      chunk.setFont(fontNormal);
      PdfPCell cell = new PdfPCell(new Paragraph(chunk));
   cell.setHorizontalAlignment(Element.ALIGN_LEFT);
   cell.setBorder(Rectangle.NO_BORDER);
   return cell;
     }
     
     private float getConvertCmsToPoints(float cm) {
      return cm * 28.4527559067f;
     }
     
     private String getCurrentDateTime() {
      Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yy '-' hh:mm");
      return ft.format(dNow);
    }
    
    public void imprimirFactura(){
     
     Desktop d=Desktop.getDesktop();
     try {
      if(Desktop.isDesktopSupported()){
       d.print(new File("factura.pdf"));
      }
  } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        JT2 = new javax.swing.JTextField();
        JT1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        JT3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Crear = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        telefono = new javax.swing.JTextField();
        nombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        documento = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        imprimir = new javax.swing.JButton();

        jButton1.setText("CREAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Traditional Arabic", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("APELLIDOS:");

        jLabel2.setFont(new java.awt.Font("Traditional Arabic", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("NOMBRES :");

        jLabel4.setFont(new java.awt.Font("Traditional Arabic", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("DOCUMENTO :");

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Crear.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Crear.setForeground(new java.awt.Color(255, 255, 0));
        Crear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo.jpg"))); // NOI18N
        Crear.setText("CREAR");
        Crear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Traditional Arabic", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setText("TELEFONO:");

        jLabel6.setBackground(new java.awt.Color(51, 51, 51));
        jLabel6.setFont(new java.awt.Font("Traditional Arabic", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("NOMBRES :");

        jLabel7.setFont(new java.awt.Font("Traditional Arabic", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("DOCUMENTO :");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondoTaquilla.jpg"))); // NOI18N

        imprimir.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        imprimir.setForeground(new java.awt.Color(255, 255, 0));
        imprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo.jpg"))); // NOI18N
        imprimir.setText("Imprimir");
        imprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(149, 149, 149)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(127, 127, 127)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(documento, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Crear, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(78, 78, 78)
                                .addComponent(imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(135, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(205, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(documento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Crear, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 486, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton1ActionPerformed

    private void CrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearActionPerformed
        // TODO add your handling code here:

        crearpdf();
        try {
            File path = new File ("factura.pdf");
            Desktop.getDesktop().open(path);
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_CrearActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        factura i = new factura();
        i.generarpdf("C:\\Users\\usuario\\Documents\\NetBeansProjects\\proyecto cine\\proyecto cine\\Proyecto_cine\\src\\imagenes");
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(recibo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(recibo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(recibo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(recibo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new recibo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Crear;
    private javax.swing.JTextField JT1;
    private javax.swing.JTextField JT2;
    private javax.swing.JTextField JT3;
    private javax.swing.JTextField documento;
    private javax.swing.JButton imprimir;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField nombre;
    private javax.swing.JTextField telefono;
    // End of variables declaration//GEN-END:variables
}
