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
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author usuario
 */
public class factura extends javax.swing.JFrame{
    private static Font fontBold = new Font(Font.FontFamily.COURIER, 14, Font.BOLD);
    private static Font fontNormal = new Font(Font.FontFamily.COURIER, 12, Font.NORMAL);
    
     public void generarFactura(String [][]datos, String nombre, String tituloFactura,String rutaImagen) throws IOException, DocumentException {
     
      Document document = getDocument();
      PdfWriter.getInstance(document, new FileOutputStream("factura.pdf"));
      document.open();
     
      PdfPTable table = getTable();
      document.add(getHeader(tituloFactura));
    document.add(getInformation(" "));
          Document doc = new Document(PageSize.A4, 36,36,10,10);
            doc.open();Image imagen = Image.getInstance(rutaImagen);
            imagen.scaleAbsolute(100,100);
            imagen.setAlignment(Element.ALIGN_CENTER);
            doc.add(imagen);
            doc.close();
        table.addCell(getCell("Id"));
        table.addCell(getCell("Producto"));
        table.addCell(getCell("Precio"));
        
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
         
        document.add(table);
        document.add(getInformation(" "));
        document.add(getInformation("Generada a nombre de "+nombre));
        document.add(getInformation(" "));
        document.add(getInformationFooter("Gracias por visitarnos!"));
          
      document.close();
     }
     public void generarpdf(String rutaImagen){
        try {
            Document doc = new Document(PageSize.A4, 36,36,10,10);
            
            doc.open();
         
            Image imagen = Image.getInstance(rutaImagen);
            imagen.scaleAbsolute(100,100);
            imagen.setAlignment(Element.ALIGN_CENTER);
            doc.add(imagen);
         
            doc.close();
        } catch (Exception e) {
        }
     }
    public static void main(String arg[]){
     new Principal() {

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    
    private Document getDocument(){
     Document document = new Document(new Rectangle( getConvertCmsToPoints(13), getConvertCmsToPoints(7)));
       document.setMargins(4, 2, 2, 2);
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
        SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yy '-' hh:mm"+"Recivo");
      return ft.format(dNow);
    }
    
    public void imprimirFactura(){
     
     Desktop c=Desktop.getDesktop();
     try {
      if(Desktop.isDesktopSupported()){
       c.print(new File("factura.pdf"));
      }
  } catch (IOException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
     
    }   
}