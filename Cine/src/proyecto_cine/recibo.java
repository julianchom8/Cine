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
   public static String SalaI ;  
   public static String PeliculaI ; 
   public static String HoraI ;
   public int numero_asientos=0;
   public int total;
    
    Sillas data;
    
    /**
     * Creates new form recibo
     */
    private static Font fontBold = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
    private static Font fontNormal = new Font(Font.FontFamily.COURIER, 11, Font.NORMAL);
    
    public recibo() {
        data = new Sillas();
        
        initComponents();
    }
public void crearpdf(){
    
     SalaI = data.getSala();
     PeliculaI = data.getPelicula();
     HoraI = data.getHora();

     
     total= numero_asientos*7000;
     
    
     String datos[][]=new String[3][3];
     datos[0][0]=PeliculaI+" ";
     datos[0][1]=SalaI+" ";
     datos[0][2]=HoraI+" ";
     datos[1][0]="$ 7.000 ";
     
     String rutaimagen;
     
     try {
   generarFactura(datos, "", " CineUDEC                  ");
  } catch (IOException | DocumentException e) {
   e.printStackTrace();
  }
     JOptionPane.showMessageDialog(null, "se ha generado su recibo !");
     
    
    
    }
    
    public void generarFactura(String [][]datos, String nombre, String tituloFactura) throws IOException, DocumentException {
        String rutaImagen=null;
        Document document = getDocument();
        PdfWriter.getInstance(document, new FileOutputStream("factura.pdf"));
        document.open();
      
        PdfPTable table = new PdfPTable(4); 
    //Datos del ancho de cada columna.
        table.setWidths(new float[] {15,10,20,10});
        factura img = new factura();
        //img.generarpdf("C:\\Users\\usuario\\Documents\\NetBeansProjects\\proyecto cine\\proyecto cine\\Proyecto_cine\\src\\imagenes");
      
        document.add(getHeader(tituloFactura));
    
        String impr = this.nombre.getText();
        String impr2 = telefono.getText();
        String impr3 = documento.getText();
        document.add(getInformation(" "));
        document.add(getInformation("      Nombre: "+impr));
        document.add(getInformation("      Telefono: "+impr2));
        document.add(getInformation("      Documento: "+impr3));
        document.add(getInformation(" "));
        table.addCell(getCell("Pelicula "));
        table.addCell(getCell("Sala "));
        table.addCell(getCell(" Hora "));
        table.addCell(getCell("Precio "));
        
        for(int i=0;i<datos.length;i++){
         for(int j=0;j<datos[0].length;j++){
          if(datos[i][j]!=null){
           table.addCell(getCell(datos[i][j]));
          }
         }
        }
        
        table.addCell(getCell(" "));
        document.add(table);
        document.add(getInformation(" "));
        document.add(getInformationFooter("Gracias por visitarnos!"));
        document.add(getInformationFooter("vuelve pronto!")); 
        
        document.close();
      
     }
    public void generarpdf(String header,String info,String footer,String rutaImagen,String salida){
        try {
            Document doc = new Document(PageSize.LETTER, 36,36,25,25);
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
    paragraph.setAlignment(Element.ALIGN_LEFT);
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
        setMaximumSize(new java.awt.Dimension(624, 483));
        setMinimumSize(new java.awt.Dimension(624, 483));
        setPreferredSize(new java.awt.Dimension(624, 483));
        setResizable(false);
        getContentPane().setLayout(null);

        Crear.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Crear.setForeground(new java.awt.Color(255, 255, 0));
        Crear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo.jpg"))); // NOI18N
        Crear.setText("Ver factura");
        Crear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearActionPerformed(evt);
            }
        });
        getContentPane().add(Crear);
        Crear.setBounds(240, 360, 130, 38);

        jLabel5.setFont(new java.awt.Font("Traditional Arabic", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 0));
        jLabel5.setText("TELEFONO:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(120, 260, 137, 44);
        getContentPane().add(telefono);
        telefono.setBounds(270, 270, 210, 30);
        getContentPane().add(nombre);
        nombre.setBounds(270, 221, 210, 30);

        jLabel6.setBackground(new java.awt.Color(51, 51, 51));
        jLabel6.setFont(new java.awt.Font("Traditional Arabic", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("NOMBRE:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(120, 220, 124, 41);

        jLabel7.setFont(new java.awt.Font("Traditional Arabic", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("DOCUMENTO :");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(120, 310, 170, 40);

        documento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                documentoActionPerformed(evt);
            }
        });
        getContentPane().add(documento);
        documento.setBounds(300, 320, 180, 30);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondoTaquilla.jpg"))); // NOI18N
        jLabel8.setText("jLabel8");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(0, -10, 630, 490);

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
        
        
                gracias ini = new gracias();
            ini.setLocationRelativeTo(null);
            ini.setVisible(true);
        recibo.this.dispose();// TODO add your handling code here:
    }//GEN-LAST:event_CrearActionPerformed

    private void documentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_documentoActionPerformed

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
