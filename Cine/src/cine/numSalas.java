/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_cine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author usuario
 */
public class numSalas {
    int i = 1,i2=0,i3=0;
     File f11;
      int nsalasa,ll;
      String nom;
    /**
     * Creates new form Nsillas_sala
     */
    
        
        
    
    ////////////////////
    ///////////////////////////////
 
        
        
        /////////////////////+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  

    
    
   
      
      //////////////////////////
      /////////////////
      
    ////////////////////////////////////
    public void creararchivo()  {
    
 /////////////////////////
                         ////////////////////////////////////
        
      File archivo = null;
      FileReader fr = null;
      BufferedReader br = null;

      try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File ("numero_salas.txt");
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);
 
         // Lectura del fichero
         String linea;
       
         linea=br.readLine();
         
         String  nsal[]=linea.split(";");
        
          nsalasa = Integer.parseInt(nsal[0]);
         
         nom = Integer.toString(nsalasa);
         //	if(cads[1].equals("535353"))
            	
        
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta 
         // una excepcion.
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }
      ////
      ////
      nsalasa++;
      
      ///
       File fw;
        fw = new File("numero_salas.txt");
           FileWriter fc = null;
      try {
            fc = new FileWriter(fw);
        } catch (IOException ex) {
            Logger.getLogger(numSalas.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            
          
            String ing = Integer.toString(nsalasa);
            fc.write(ing+";");
            
           //
            //
            //
            
            
            
            
            fc.close();
            
        } catch (Exception ex) {
            
            ex.printStackTrace();
            
        }
    
  /////////////////////
        ///////////////////////
        ////////////
        
        String cadena[]= new String[10]; 
     int as= nsalasa;
     System.out.println(as);
        try {
            
          File  fj = new File("sala"+as+".txt");
            
        } catch (Exception ex) {
            
            ex.printStackTrace();
            
        }
       
     
   File f = new File("sala"+as+".txt");
   PrintWriter pw = null;
   
   
        try {
           try (FileWriter fry = new FileWriter(f)) {
                pw = new PrintWriter(fry);
/*                cadena[0] =  NFILAS.getText();              
               cadena[1] =  SPRE.getText();
               cadena[2] =  TIEMPO.getText();
               cadena[3] =  PELICULA.getText();
               cadena[4] =  NOMSALA.getText();
               cadena[5] =  HORARIO.getText();
              
               */
               fry.write(cadena[0]+";\n");
                 fry.write(cadena[1]+";\n");
                  fry.write(cadena[2]+";\n");
                   fry.write(cadena[3]+";\n");
                    fry.write(cadena[4]+";\n");
                      fry.write(cadena[5]+";\n");
               
                      pw.println("");

                      fry.close();
           }
            
        } catch (Exception ex) {
            
            ex.printStackTrace();
            
        }
 
    };
   
   
   public void contansalacre(){
  
       
       
     int as= nsalasa;
   File fd = new File("n_salaabrir.txt");
   PrintWriter pw = null;
   
   
        try {
           try (FileWriter frd = new FileWriter(fd)) {
                pw = new PrintWriter(frd);
                              
             
               
            String ingw = Integer.toString(nsalasa);
               
               frd.write(ingw+";\n");
                
               
                      pw.println("");

                      frd.close();
           }
            
        } catch (Exception ex) {
            
            ex.printStackTrace();
            
        }
        ///////////////////////////////
        //////////////
        ////////

    
    }
   
}
