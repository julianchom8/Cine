/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_cine;

import java.awt.HeadlessException;
import java.io.*;
import java.io.File;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author familia pinillos
 */
public final class ventana_administrador extends javax.swing.JFrame {
    private String rutas_peli = "peliculas.txt"; //asigna nombre el archivo peliculas
    private String rutas_salas = "salas.txt"; //asigna el nombre al archivo salas 
    private String rutasPorpeli ; //asigna el nombre al archivo salas Individualmente
    
    Pelicula peli; //clase pelicula
    Sala sala; //clase sala
    
    Proceso proceso_pelicula; // manejo de archivo pelicula
    Procesos proceso_sala ; //    manejo archivo sala
    String nombre_Sala; //Nombre que se le dara a cada archivo por sala creada 
    String sal ; // variable para guardar la sala seleccionada del combo Salas en mantenimiento de salas
    
    int clic_tabla;      
    public ventana_administrador() {
        initComponents();

        proceso_pelicula = new Proceso(); //nueva varible de tipo clase proceso -----> procesos de peliculas
        proceso_sala = new Procesos(); //nueva varible de tipo clase procesos -----> procesos de sala
        

        try{
          cargar_pelis();
           cargar_sala();
            
            listarRegistro();
            listarSalas();
        }catch(Exception ex){
            mensaje("No existe el archivo txt");
        }
        
        salas();
        man.setVisible(false);
        manSala.setVisible(false);
        peli_transmitir.setVisible(false);
        h.setVisible(false);
        h1.setVisible(false);
        hora_peli.setVisible(false);
        limpiarDatosPeli();
    }
// -------------------------- Manejo archivos peliculas ---------------
    public void cargar_pelis(){ //carga el archivo peliculas
        File pos = new File(rutas_peli);
        try{
            FileReader fi = new FileReader(pos); //se lee el archivo peliculas
            BufferedReader bu = new BufferedReader(fi); // se lee line por linea el archivo peliculas
            String linea = null;
            while((linea = bu.readLine())!=null){ // mientras que no lea una linea vacia
                StringTokenizer st = new StringTokenizer(linea, ",");//obtien la informacion separada por las comas
                peli = new Pelicula(); 
                peli.setCodigo(Integer.parseInt(st.nextToken()));
                peli.setNombre(st.nextToken());
                peli.setSinopsis(st.nextToken());
                peli.setRuta_imagen(st.nextToken());
                proceso_pelicula.agregarRegistro(peli);
            }
            
            bu.close();
        }catch(IOException ex){
            mensaje("Error al cargar archivo: "+ex.getMessage());
            
        }
    }
    
    public void grabar_txt(){
        FileWriter fw;
        PrintWriter pw;
        try{
            fw = new FileWriter(rutas_peli); //escribir en el archivo peliculas 
            pw = new PrintWriter(fw); //para escribir line por linea
            
            for(int i = 0; i < proceso_pelicula.cantidadRegistro(); i++){ //lee la cantidad de registros que tenga el archivo , si es que los tiene
                peli = proceso_pelicula.obtenerRegistro(i); // obteien los registros del archivo peliculas
                pw.println(String.valueOf(peli.getCodigo()+","+peli.getNombre()+","+peli.getSinopsis()+","+peli.getRuta_imagen())); // escribe en el archivo peliculas
            }
             pw.close();
            
        }catch(IOException ex){
            mensaje("Error al grabar en el archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    
    public void ingresarPelicula(File ruta){
        try{
            if(Integer.parseInt(codigo_pelicula.getText()) == -666)mensaje("Ingresar codigo entero");//valida si el codigo de pelicula no es entero 
            else if(nombre_pelicula.getText() == null)mensaje("Ingresar Nombre"); //calida que ingrese un nombre 
            else if(sinopsis.getText() == null)mensaje("Ingresar Sinopsis");// valida que ingrese una sinopsis
            else{
                
                peli = new Pelicula(Integer.parseInt(codigo_pelicula.getText()), nombre_pelicula.getText(), sinopsis.getText(),ruta_imagen.getText()); // envia al constructor de la clase pelicula , los datos que recive de los textField
                if(proceso_pelicula.buscaCodigo(peli.getCodigo())!= -1)mensaje("Este codigo ya existe");
                else proceso_pelicula.agregarRegistro(peli); // si el codigo no existe lo agrega los datos al archivo
                
                grabar_txt();
                listarRegistro();
                
            }
        }catch(NumberFormatException ex){
            mensaje("ERROR INGRESE UN NUMERO !!!!");
        }
    } 
      
    public void modificarPelicula(File ruta){
        try{
            if(Integer.parseInt(codigo_pelicula.getText()) == -666)mensaje("Ingresar codigo entero");
            else if(nombre_pelicula.getText() == null)mensaje("Ingresar Nombre");
            else if(sinopsis.getText() == null)mensaje("Ingresar Sinopsis");
            else{
                int codigo = proceso_pelicula.buscaCodigo(Integer.parseInt(codigo_pelicula.getText()));//busca el codigo de la pelicula seleccionada a modificar
                peli = new Pelicula(Integer.parseInt(codigo_pelicula.getText()), nombre_pelicula.getText(), sinopsis.getText(),ruta_imagen.getText()); // envia al constructor de la clase pelicula , los datos  a modificar 
                
                if(codigo == -1)proceso_pelicula.agregarRegistro(peli); //si el codigo no existe lo agrega , si el codigo existe lo remplaza
                else proceso_pelicula.modificarRegistro(codigo, peli);
                
                grabar_txt();
                listarRegistro();
                
            }
        }catch(NumberFormatException ex){
            mensaje("ERROR INGRESE UN NUMERO !!!!");
        }
    }

    public void eliminarPelicula(){
        try{
            if(Integer.parseInt(codigo_pelicula.getText()) == -666) mensaje("Ingrese codigo entero");
            
            else{
                int codigo = proceso_pelicula.buscaCodigo(Integer.parseInt(codigo_pelicula.getText()));
                if(codigo == -1) mensaje("codigo no existe");
                
                else{
                    int confir = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar esta pelicula?","Si/No",0);
                    if(confir  == 0){
                        proceso_pelicula.eliminarRegistro(codigo);
                        
                        grabar_txt();
                        listarRegistro();    
                    }
                }
            }
        }catch(HeadlessException | NumberFormatException ex){
            mensaje(ex.getMessage());
        }
    } 
    
    public void listarRegistro(){
        DefaultTableModel dt = new DefaultTableModel();
        
        dt.addColumn("Codigo");
        dt.addColumn("Nombre");
        dt.addColumn("Sinopsis");
        dt.addColumn("Ruta");
        
        Object fila[] = new Object[dt.getColumnCount()];
        for(int i = 0; i < proceso_pelicula.cantidadRegistro(); i++){
            peli = proceso_pelicula.obtenerRegistro(i);
            fila[0] = peli.getCodigo();
            fila[1] = peli.getNombre();
            fila[2] = peli.getSinopsis();
            fila[3] = peli.getRuta_imagen();

            dt.addRow(fila);
        }
        lista_peliculas.setModel(dt);
        lista_peliculas.setRowHeight(20);
    }    
    
//------------------------ Manejo archivos salas (guarda  codigo y capacidad )   
    public void cargar_sala(){
        File pos = new File(rutas_salas);
        try{
            
            FileReader fi = new FileReader(pos);
            BufferedReader bu = new BufferedReader(fi);
            String linea = null;//obtien l ainformacion separada por las comas
            while((linea = bu.readLine())!=null){
                StringTokenizer st = new StringTokenizer(linea, ",");
                sala = new Sala();
                sala.setCodigoSala(Integer.parseInt(st.nextToken()));
                sala.setCapacidad(Integer.parseInt(st.nextToken()));
                proceso_sala.agregarRegistro(sala);
            }
            bu.close();
        }catch(IOException | NumberFormatException ex){
            mensaje("Error al cargar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
     }   
 
    public void grabar_sala(){
        FileWriter fw;
        PrintWriter pw;
        try{
            fw = new FileWriter(rutas_salas);
            pw = new PrintWriter(fw);
            
            for(int i = 0; i < proceso_sala.cantidadRegistro(); i++){
                sala = proceso_sala.obtenerRegistro(i);
                pw.println(String.valueOf(sala.getCodigoSala()+","+sala.getCapacidad()));
            }
             pw.close();
            
        }catch(IOException ex){
            mensaje("Error al grabar archivo: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }    

    public void ingresarSala(File ruta) throws IOException{
        try{
            if(Integer.parseInt(numero_sala.getText()) == -666)mensaje("Ingresar codigo entero");
            else if(capacidad_sala.getText() == null)mensaje("Ingresar capacidad de la sala");
            else{
                sala = new Sala(Integer.parseInt(numero_sala.getText()),Integer.parseInt(capacidad_sala.getText()));
                if(proceso_sala.buscaCodigo(sala.getCodigoSala())!= -1)mensaje("Este codigo ya existe");
                else proceso_sala.agregarRegistro(sala);
                
                grabar_sala();
                listarSalas();
                creaSalas(numero_sala.getText());
                
            }
        }catch(NumberFormatException ex){
            mensaje("ERROR INGRESE UN NUMERO !!!!");
        }
    }   
    
    public void modificarSala(File ruta){
        try{
            if(Integer.parseInt(numero_sala.getText()) == -666)mensaje("Ingresar codigo entero");
            else if(capacidad_sala.getText() == null)mensaje("Ingresar capacidad de la sala");
            else{
                int codigo = proceso_sala.buscaCodigo(Integer.parseInt(numero_sala.getText()));
               sala = new Sala(Integer.parseInt(numero_sala.getText()),Integer.parseInt(capacidad_sala.getText()));
                
                if(codigo == -1)proceso_sala.agregarRegistro(sala);
                else proceso_sala.modificarRegistro(codigo, sala);
                
                grabar_sala();
                listarSalas();
                
            }
        }catch(NumberFormatException ex){
            mensaje("ERROR INGRESE UN NUMERO !!!!");
        }
    }    
      
    public void eliminarSalas(){
        try{
            if(Integer.parseInt(numero_sala.getText()) == -666) mensaje("Ingrese codigo entero");
            
            else{
                int codigo = proceso_sala.buscaCodigo(Integer.parseInt(numero_sala.getText()));
                if(codigo == -1) mensaje("codigo no existe");
                
                else{
                    int so = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar esta Sala y las peliculas a proyectar en ella ?","Si/No",0);
                    if(so == 0){
                        proceso_sala.eliminarRegistro(codigo);
                        
                        grabar_sala();
                        listarSalas();
                        eliminarSala(numero_sala.getText());
                        
                    }
                }
            }
        }catch(HeadlessException | NumberFormatException ex){
            mensaje(ex.getMessage()+"ERROR AL ELIMINAR");
        }
    }
    
    public void listarSalas(){ // lista sala y capacidad
            
        DefaultTableModel t = new DefaultTableModel();
        
        t.addColumn("Codigo");
        t.addColumn("Capacidad");
        
        Object fila[] = new Object[t.getColumnCount()];
        for(int i = 0; i < proceso_sala.cantidadRegistro(); i++){
            sala = proceso_sala.obtenerRegistro(i);
            fila[0] = sala.getCodigoSala();
            fila[1] = sala.getCapacidad();

            t.addRow(fila);
        }
        lista_sala.setModel(t);
        lista_sala.setRowHeight(20);
    }
    
//------------------Manejo de archivos para salas individual(guarda una pelicula en la sala , hora inicio , hora final)---------- 
    
    public void creaSalas(String n) {//crea archivo por cada sala creada 
        String pos = "sala"+n+".txt";
        File archivo = new File(pos);
       
        try{
        BufferedWriter bw;
        
        if(archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("El fichero de texto ya estaba creado.");
        } else {
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("Acabo de crear el fichero de texto.");
        }
     }catch(IOException ex){
         
     }
        salas();
    } 
    
    public void llenarSala(){
        String dato; //variable para almacenar nombre y apellido
        String cadena = null; //nombre;apellido
        FileWriter fichero = null;  //objeto principal (archivo)
        PrintWriter linea = null;   //objeto de contenido de archivo
        
        try{
            fichero = new FileWriter(rutasPorpeli,true); //crea el archivo 
            linea = new PrintWriter(fichero); //apunta el PrintWriter al archivo creado
            // Inicia captura de datos del usuario
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String c = h.getText();
            System.out.print("-"+c);
            
            dato = peli_transmitir.getText() ;
            cadena = dato + ",";
            dato = hora_peli.getText();
            cadena = cadena + dato + ",";
            dato = h.getText();
            cadena = cadena + dato + ",";
            dato = h1.getText();
            cadena = cadena + dato + ",";
            
            if (  c.equals(validarHorario(c,2))){
            mensaje("este horario ya esta ocupado");
            }else{
            linea.println(cadena); //escribiendo en el archivo
            mensaje("datos ingresados");
            }
            
      }catch(IOException e){
           System.out.print("Error creando archivo");
        }
        finally{
            try{
                if(fichero != null){
                    fichero.close();
                }
            }catch(IOException e1){
                System.out.print("Error cerrando archivo");
            }
        }
        
    }
    
    public void eliminarSala(String n){
       String pos = "sala"+n+".txt"; //si se elimina una sala...0
       File archivo = new File(pos);
       
       archivo.delete();
   }  
    
  public void listarSalasPeliculas(){
        DefaultTableModel tabla = new DefaultTableModel();
        
        tabla.addColumn("pelicula");
        tabla.addColumn("Horario ");
        Object fila[] = new Object[tabla.getColumnCount()];
        
        File archivo = null;  //apuntar al archivo almancenado DD
        FileReader contenido = null;  //acceder a todo el contenido del archivo
        BufferedReader linea = null; //accede linea a linea al contenido
        
        try{
            archivo = new File(rutasPorpeli);
            contenido = new FileReader(archivo);
            linea = new BufferedReader(contenido);
            
            String cadena=""; //variable captura los datos del archivo
            while((cadena=linea.readLine()) != null){ //recorre todo el archivo
                String dato[] = cadena.split(",");
                fila[0] = dato[0];
                fila[1] = dato[1];
                tabla.addRow(fila);  

            }
         }catch(IOException e){
           System.out.print("Error creando archivo");
        }
        finally{
            try{
                if(contenido != null){
                    contenido.close();
                }
            }catch(IOException e1){
                System.out.print("Error cerrando archivo");
            }
        }
        verSala.setModel(tabla);
        verSala.setRowHeight(20);
    }  
  
      public String validarHorario(String filtro, int index){
        String busqueda = null ;
        File archivo = null;  //apuntar al archivo almancenado DD
        FileReader contenido = null;  //acceder a todo el contenido del archivo
        BufferedReader linea = null; //accede linea a linea al contenido
        
        try{
            archivo = new File(rutasPorpeli);
            contenido = new FileReader(archivo);
            linea = new BufferedReader(contenido);
            
            String cadena=""; //variable captura los datos del archivo
            while((cadena=linea.readLine()) != null){ //recorre todo el archivo
                String dato[] = cadena.split(",");
                if(dato[index].equals(filtro)){
                        busqueda= dato[2];
                     
               }
                
            }
         }catch(IOException e){
           System.out.print("Error creando archivo");
        }
        finally{
            try{
                if(contenido != null){
                    contenido.close();
                }
            }catch(IOException e1){
                System.out.print("Error cerrando archivo");
            }
        }
        
       return busqueda;
        
        
    }
  
  

// otros metodos    
    public void salas(){//metodo que llena un comboBox con las salas ingresadas 
       mSalas.removeAllItems();
          for(int i = 0; i < proceso_sala.cantidadRegistro(); i++){
            sala = proceso_sala.obtenerRegistro(i);
            
            mSalas.addItem(String.valueOf(sala.getCodigoSala())); 
            
        }
         
    }
   
    public void pelis(){//metodo que llena un comboBox con las peliculas ingresadas
       pelicula_transmitir.removeAllItems();
          for(int i = 0; i < proceso_pelicula.cantidadRegistro(); i++){
            peli = proceso_pelicula.obtenerRegistro(i);
            
            pelicula_transmitir.addItem(peli.getNombre());            
        }
          
   }
    int i = 1,i2=0,i3=0;
     File f11;
      int nsalasa,ll;
      String nom;
     public void creararchivo()  {
    
 /////////////////////////
                         ////////////////////////////////////
        
      File archivo = null;
      FileReader fr = null;
      BufferedReader br = null;

      try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File ("num_salas.txt");
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
               cadena[0] =  capacidad_sala.getText();              
               
              
               
               fry.write(cadena[0]+";\n");
                 
               
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
 
    private void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }
        
    private void limpiarDatosPeli(){ //limpia las cajas de texto de la venta adminisrar peliculas
        codigo_pelicula.setText(null);
        nombre_pelicula.setText(null);
        sinopsis.setText(null);
        ruta_imagen.setText(null);
        imagen.setIcon(null);
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelsalas = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        numero_sala = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        capacidad_sala = new javax.swing.JTextField();
        guardar_sala = new javax.swing.JButton();
        modificar_sala = new javax.swing.JButton();
        eliminar_sala = new javax.swing.JButton();
        limp = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        lista_sala = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        mSalas = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        man = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        pelicula_transmitir = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        agregarPelicula = new javax.swing.JButton();
        peli_transmitir = new javax.swing.JTextField();
        horarios = new javax.swing.JComboBox<>();
        h = new javax.swing.JTextField();
        hora_peli = new javax.swing.JTextField();
        h1 = new javax.swing.JTextField();
        manSala = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        verSala = new javax.swing.JTable();
        nombreS = new javax.swing.JLabel();
        panelpeliculas = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        codigo_pelicula = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        nombre_pelicula = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        sinopsis = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        buscar_foto = new javax.swing.JButton();
        ruta_imagen = new javax.swing.JTextField();
        guardar_pelicula = new javax.swing.JButton();
        modificar_pelicula = new javax.swing.JButton();
        eliminar_pelicula = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista_peliculas = new javax.swing.JTable();
        limpiar = new javax.swing.JButton();
        imagen = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        atras = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        jTabbedPane1.setBackground(new java.awt.Color(153, 153, 153));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 5));

        panelsalas.setBackground(new java.awt.Color(0, 102, 102));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("ADMINISTRADOR DE SALAS");

        jPanel2.setBackground(new java.awt.Color(2, 119, 119));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));

        numero_sala.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Crear sala");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Capacidad de la sala:");

        capacidad_sala.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        guardar_sala.setText("Guardar sala");
        guardar_sala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardar_salaActionPerformed(evt);
            }
        });

        modificar_sala.setText("Modificar sala");
        modificar_sala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificar_salaActionPerformed(evt);
            }
        });

        eliminar_sala.setText("Eliminar sala");
        eliminar_sala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminar_salaActionPerformed(evt);
            }
        });

        limp.setText("Limpiar");
        limp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Numero de la sala:");

        lista_sala.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Numero de sala", "Capacidad de la sala"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lista_sala.setFocusable(false);
        lista_sala.setGridColor(new java.awt.Color(0, 102, 102));
        lista_sala.setRowHeight(20);
        lista_sala.setRowMargin(5);
        lista_sala.setSelectionBackground(new java.awt.Color(0, 153, 102));
        lista_sala.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lista_salaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(lista_sala);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(39, 39, 39)
                                .addComponent(numero_sala, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addComponent(capacidad_sala, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(modificar_sala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(guardar_sala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(76, 76, 76)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(eliminar_sala)
                                    .addComponent(limp, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numero_sala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(capacidad_sala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guardar_sala)
                    .addComponent(eliminar_sala))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modificar_sala)
                    .addComponent(limp))
                .addGap(63, 63, 63)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(2, 119, 119));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));

        mSalas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        mSalas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSalasActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Mantenimiento de sala");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Seleccione una sala:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setText("Pelicula a transmitir:");

        pelicula_transmitir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pelicula_transmitirActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setText("horario:");

        agregarPelicula.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        agregarPelicula.setText("Agregar pelicula a la sala");
        agregarPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarPeliculaActionPerformed(evt);
            }
        });

        horarios.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        horarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una hora", "12 pm -  3  pm", " 3  pm -  5  pm", " 5  pm -  8  pm", " 8  pm - 11 pm" }));
        horarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                horariosActionPerformed(evt);
            }
        });

        h.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hActionPerformed(evt);
            }
        });

        h1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout manLayout = new javax.swing.GroupLayout(man);
        man.setLayout(manLayout);
        manLayout.setHorizontalGroup(
            manLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manLayout.createSequentialGroup()
                        .addComponent(pelicula_transmitir, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(peli_transmitir))
                    .addGroup(manLayout.createSequentialGroup()
                        .addGroup(manLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(manLayout.createSequentialGroup()
                        .addComponent(horarios, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hora_peli, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(h1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(h, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
            .addGroup(manLayout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(agregarPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        manLayout.setVerticalGroup(
            manLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(manLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(peli_transmitir, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(pelicula_transmitir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(manLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(horarios, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(hora_peli)
                    .addComponent(h)
                    .addComponent(h1))
                .addGap(18, 18, 18)
                .addComponent(agregarPelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        verSala.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Numero de sala", "Capacidad de la sala"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        verSala.setFocusable(false);
        verSala.setGridColor(new java.awt.Color(0, 102, 102));
        verSala.setRowHeight(20);
        verSala.setRowMargin(5);
        verSala.setSelectionBackground(new java.awt.Color(0, 153, 102));
        verSala.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                verSalaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(verSala);

        nombreS.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout manSalaLayout = new javax.swing.GroupLayout(manSala);
        manSala.setLayout(manSalaLayout);
        manSalaLayout.setHorizontalGroup(
            manSalaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manSalaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nombreS, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(136, 136, 136))
        );
        manSalaLayout.setVerticalGroup(
            manSalaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manSalaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nombreS, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel12))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(27, 27, 27)
                                .addComponent(mSalas, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(manSala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(man, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mSalas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addComponent(man, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(manSala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelsalasLayout = new javax.swing.GroupLayout(panelsalas);
        panelsalas.setLayout(panelsalasLayout);
        panelsalasLayout.setHorizontalGroup(
            panelsalasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelsalasLayout.createSequentialGroup()
                .addGroup(panelsalasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelsalasLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelsalasLayout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(jLabel6)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        panelsalasLayout.setVerticalGroup(
            panelsalasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelsalasLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(panelsalasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Administrar Salas", panelsalas);

        panelpeliculas.setBackground(new java.awt.Color(0, 102, 102));
        panelpeliculas.setPreferredSize(new java.awt.Dimension(706, 582));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ADMINISTRADOR DE PELICULAS");

        jPanel4.setBackground(new java.awt.Color(2, 119, 119));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Codigo de pelicula:");

        codigo_pelicula.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nombre de pelicula:");

        nombre_pelicula.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Sinopsis:");

        sinopsis.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Imagen:");

        buscar_foto.setText("Seleccionar imagen");
        buscar_foto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscar_fotoActionPerformed(evt);
            }
        });

        ruta_imagen.setPreferredSize(new java.awt.Dimension(198, 20));

        guardar_pelicula.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        guardar_pelicula.setText("Guardar pelicula");
        guardar_pelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardar_peliculaActionPerformed(evt);
            }
        });

        modificar_pelicula.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        modificar_pelicula.setText("modificar pelicula");
        modificar_pelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificar_peliculaActionPerformed(evt);
            }
        });

        eliminar_pelicula.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eliminar_pelicula.setText("eliminar pelicula");
        eliminar_pelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminar_peliculaActionPerformed(evt);
            }
        });

        lista_peliculas.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lista_peliculas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Codigo", "Nombre pelicula"
            }
        ));
        lista_peliculas.setGridColor(new java.awt.Color(0, 102, 102));
        lista_peliculas.setSelectionBackground(new java.awt.Color(0, 153, 51));
        lista_peliculas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lista_peliculasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lista_peliculas);

        limpiar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        limpiar.setText("limpiar");
        limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarActionPerformed(evt);
            }
        });

        imagen.setBackground(new java.awt.Color(2, 119, 119));
        imagen.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        imagen.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Lista de peliculas");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(codigo_pelicula))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(nombre_pelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(sinopsis, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(195, 195, 195)
                                .addComponent(modificar_pelicula)
                                .addGap(18, 18, 18)
                                .addComponent(eliminar_pelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(guardar_pelicula, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(buscar_foto)
                                .addGap(18, 18, 18)
                                .addComponent(ruta_imagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(limpiar))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(372, 372, 372)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                        .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(codigo_pelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(nombre_pelicula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(sinopsis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(buscar_foto)
                            .addComponent(ruta_imagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(limpiar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(guardar_pelicula)
                            .addComponent(modificar_pelicula)
                            .addComponent(eliminar_pelicula))
                        .addGap(25, 25, 25)
                        .addComponent(jLabel11))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout panelpeliculasLayout = new javax.swing.GroupLayout(panelpeliculas);
        panelpeliculas.setLayout(panelpeliculasLayout);
        panelpeliculasLayout.setHorizontalGroup(
            panelpeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelpeliculasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(246, 246, 246))
            .addGroup(panelpeliculasLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelpeliculasLayout.setVerticalGroup(
            panelpeliculasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelpeliculasLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Administrar peliculas", panelpeliculas);

        atras.setText("atras");
        atras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(atras)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 662, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(atras)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarActionPerformed
        this.limpiarDatosPeli();
    }//GEN-LAST:event_limpiarActionPerformed

    private void lista_peliculasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lista_peliculasMouseClicked

        clic_tabla = lista_peliculas.rowAtPoint(evt.getPoint());
        String c = String.valueOf(clic_tabla);

        try{
            int codigo = (int)lista_peliculas.getValueAt(clic_tabla, 0);
            String nombre = ""+lista_peliculas.getValueAt(clic_tabla, 1);
            String sinop = ""+lista_peliculas.getValueAt(clic_tabla, 2);
            String ruta = ""+lista_peliculas.getValueAt(clic_tabla,3);

            codigo_pelicula.setText(String.valueOf(codigo));
            nombre_pelicula.setText(nombre);
            sinopsis.setText(sinop);

            ruta_imagen.setText(ruta);

            imagen.setIcon(new ImageIcon(ruta_imagen.getText()));

        }catch(Exception ex){

        }
        
    }//GEN-LAST:event_lista_peliculasMouseClicked

    private void eliminar_peliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminar_peliculaActionPerformed
        this.eliminarPelicula();
    }//GEN-LAST:event_eliminar_peliculaActionPerformed

    private void modificar_peliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificar_peliculaActionPerformed
        File ubicacion = new File(rutas_peli);
        this.modificarPelicula(ubicacion);
    }//GEN-LAST:event_modificar_peliculaActionPerformed

    private void guardar_peliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardar_peliculaActionPerformed
        File ubicacion = new File(rutas_peli);
        this.ingresarPelicula(ubicacion);
    }//GEN-LAST:event_guardar_peliculaActionPerformed

    private void buscar_fotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscar_fotoActionPerformed
        JFileChooser jf = new JFileChooser();
        FileNameExtensionFilter fil = new FileNameExtensionFilter("JPG, PNG & GIF","jpg","png","gif");
        jf.setFileFilter(fil);
        jf.setCurrentDirectory(new File("Posters"));
        int el = jf.showOpenDialog(this);
        if(el == JFileChooser.APPROVE_OPTION){
            ruta_imagen.setText(jf.getSelectedFile().getAbsolutePath());
            imagen.setIcon(new ImageIcon(ruta_imagen.getText()));
        }
    }//GEN-LAST:event_buscar_fotoActionPerformed

    private void verSalaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_verSalaMouseClicked

    }//GEN-LAST:event_verSalaMouseClicked

    private void h1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_h1ActionPerformed

    private void hActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hActionPerformed

    private void horariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_horariosActionPerformed
        int hora = horarios.getSelectedIndex();

        if (hora == 0){
            mensaje("selecione un horario");
        }else{

            String hor = (String)horarios.getSelectedItem();
            hora_peli.setText(hor);
            h.setText(String.valueOf(hora));
        }

    }//GEN-LAST:event_horariosActionPerformed

    private void agregarPeliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarPeliculaActionPerformed
        this.llenarSala();

        this.listarSalasPeliculas();
    }//GEN-LAST:event_agregarPeliculaActionPerformed

    private void pelicula_transmitirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pelicula_transmitirActionPerformed
        int seleccion =  pelicula_transmitir.getSelectedIndex();
        String pel = (String)pelicula_transmitir.getSelectedItem();
        int aux = seleccion + 1 ;
        String a= String.valueOf(aux);
        peli_transmitir.setText(pel);

    }//GEN-LAST:event_pelicula_transmitirActionPerformed

    private void mSalasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSalasActionPerformed
        man.setVisible(true);
        manSala.setVisible(true);
        pelis();
        sal = (String)mSalas.getSelectedItem();
        nombreS.setText("Sala: "+sal);
        h1.setText(sal);
        //llama la sala seleccionada para agregar pleiculas y hora
        rutasPorpeli= "sala"+sal+".txt";
        listarSalasPeliculas();
    }//GEN-LAST:event_mSalasActionPerformed

    private void lista_salaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lista_salaMouseClicked
        clic_tabla = lista_sala.rowAtPoint(evt.getPoint());
        try{
            int numero = (int)lista_sala.getValueAt(clic_tabla, 0);
            int capacidad = (int)lista_sala.getValueAt(clic_tabla, 1);

            numero_sala.setText(String.valueOf(numero));
            capacidad_sala.setText(String.valueOf(capacidad));

        }catch(Exception ex){

        }
    }//GEN-LAST:event_lista_salaMouseClicked

    private void limpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpActionPerformed
        numero_sala.setText(null);
        capacidad_sala.setText(null);
    }//GEN-LAST:event_limpActionPerformed

    private void eliminar_salaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminar_salaActionPerformed

        this.eliminarSalas();
        salas();
    }//GEN-LAST:event_eliminar_salaActionPerformed

    private void modificar_salaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificar_salaActionPerformed
        File ubicacion = new File(rutas_salas);
        this.modificarSala(ubicacion);
        salas();
    }//GEN-LAST:event_modificar_salaActionPerformed

    private void guardar_salaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardar_salaActionPerformed
        File ubicacion = new File(rutas_salas);
        try {
            this.ingresarSala(ubicacion);
        } catch (IOException ex) {
            Logger.getLogger(ventana_administrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        salas();
    }//GEN-LAST:event_guardar_salaActionPerformed

    private void atrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atrasActionPerformed
      Ventana_inicio ini = new Ventana_inicio();
            ini.setLocationRelativeTo(null);
            ini.setVisible(true);
        ventana_administrador.this.dispose();// TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_atrasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventana_administrador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarPelicula;
    private javax.swing.JButton atras;
    private javax.swing.JButton buscar_foto;
    private javax.swing.JTextField capacidad_sala;
    private javax.swing.JTextField codigo_pelicula;
    private javax.swing.JButton eliminar_pelicula;
    private javax.swing.JButton eliminar_sala;
    private javax.swing.JButton guardar_pelicula;
    private javax.swing.JButton guardar_sala;
    private javax.swing.JTextField h;
    private javax.swing.JTextField h1;
    private javax.swing.JTextField hora_peli;
    private javax.swing.JComboBox<String> horarios;
    private javax.swing.JLabel imagen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton limp;
    private javax.swing.JButton limpiar;
    private javax.swing.JTable lista_peliculas;
    private javax.swing.JTable lista_sala;
    private javax.swing.JComboBox<String> mSalas;
    private javax.swing.JPanel man;
    private javax.swing.JPanel manSala;
    private javax.swing.JButton modificar_pelicula;
    private javax.swing.JButton modificar_sala;
    private javax.swing.JLabel nombreS;
    private javax.swing.JTextField nombre_pelicula;
    private javax.swing.JTextField numero_sala;
    private javax.swing.JPanel panelpeliculas;
    private javax.swing.JPanel panelsalas;
    private javax.swing.JTextField peli_transmitir;
    private javax.swing.JComboBox<String> pelicula_transmitir;
    private javax.swing.JTextField ruta_imagen;
    private javax.swing.JTextField sinopsis;
    private javax.swing.JTable verSala;
    // End of variables declaration//GEN-END:variables
}
