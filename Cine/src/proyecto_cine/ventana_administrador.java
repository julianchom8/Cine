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
        jLabel14 = new javax.swing.JLabel();
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
        jLabel7 = new javax.swing.JLabel();
        atras = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));
        jPanel1.setMaximumSize(new java.awt.Dimension(960, 685));
        jPanel1.setMinimumSize(new java.awt.Dimension(960, 685));
        jPanel1.setPreferredSize(new java.awt.Dimension(960, 685));
        jPanel1.setLayout(null);

        jTabbedPane1.setBackground(new java.awt.Color(153, 153, 153));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 5));

        panelpeliculas.setBackground(new java.awt.Color(0, 102, 102));
        panelpeliculas.setMaximumSize(new java.awt.Dimension(706, 582));
        panelpeliculas.setMinimumSize(new java.awt.Dimension(706, 582));
        panelpeliculas.setPreferredSize(new java.awt.Dimension(706, 582));
        panelpeliculas.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ADMINISTRADOR DE PELICULAS");
        panelpeliculas.add(jLabel1);
        jLabel1.setBounds(235, 12, 464, 34);

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        jPanel4.setMaximumSize(new java.awt.Dimension(921, 568));
        jPanel4.setMinimumSize(new java.awt.Dimension(921, 568));
        jPanel4.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Codigo de pelicula:");
        jPanel4.add(jLabel2);
        jLabel2.setBounds(100, 30, 150, 22);

        codigo_pelicula.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel4.add(codigo_pelicula);
        codigo_pelicula.setBounds(280, 30, 250, 23);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nombre de pelicula:");
        jPanel4.add(jLabel3);
        jLabel3.setBounds(100, 60, 157, 22);

        nombre_pelicula.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nombre_pelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombre_peliculaActionPerformed(evt);
            }
        });
        jPanel4.add(nombre_pelicula);
        nombre_pelicula.setBounds(280, 60, 250, 23);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Sinopsis:");
        jPanel4.add(jLabel5);
        jLabel5.setBounds(100, 90, 70, 22);

        sinopsis.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel4.add(sinopsis);
        sinopsis.setBounds(180, 90, 350, 23);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Imagen:");
        jPanel4.add(jLabel4);
        jLabel4.setBounds(100, 120, 66, 22);

        buscar_foto.setText("Seleccionar imagen");
        buscar_foto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscar_fotoActionPerformed(evt);
            }
        });
        jPanel4.add(buscar_foto);
        buscar_foto.setBounds(180, 120, 123, 23);

        ruta_imagen.setPreferredSize(new java.awt.Dimension(198, 20));
        jPanel4.add(ruta_imagen);
        ruta_imagen.setBounds(320, 120, 210, 20);

        guardar_pelicula.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        guardar_pelicula.setText("Guardar pelicula");
        guardar_pelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardar_peliculaActionPerformed(evt);
            }
        });
        jPanel4.add(guardar_pelicula);
        guardar_pelicula.setBounds(100, 170, 169, 31);

        modificar_pelicula.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        modificar_pelicula.setText("modificar pelicula");
        modificar_pelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificar_peliculaActionPerformed(evt);
            }
        });
        jPanel4.add(modificar_pelicula);
        modificar_pelicula.setBounds(100, 220, 169, 30);

        eliminar_pelicula.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eliminar_pelicula.setText("eliminar pelicula");
        eliminar_pelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminar_peliculaActionPerformed(evt);
            }
        });
        jPanel4.add(eliminar_pelicula);
        eliminar_pelicula.setBounds(100, 270, 169, 31);

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

        jPanel4.add(jScrollPane1);
        jScrollPane1.setBounds(20, 380, 870, 140);

        limpiar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        limpiar.setText("limpiar");
        limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpiarActionPerformed(evt);
            }
        });
        jPanel4.add(limpiar);
        limpiar.setBounds(440, 160, 85, 31);

        imagen.setBackground(new java.awt.Color(2, 119, 119));
        imagen.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        imagen.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel4.add(imagen);
        imagen.setBounds(610, 20, 250, 340);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Lista de peliculas");
        jPanel4.add(jLabel11);
        jLabel11.setBounds(30, 330, 210, 29);

        panelpeliculas.add(jPanel4);
        jPanel4.setBounds(10, 52, 917, 530);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo_phixr.jpg"))); // NOI18N
        jLabel14.setText("jLabel14");
        panelpeliculas.add(jLabel14);
        jLabel14.setBounds(0, 0, 950, 590);

        jTabbedPane1.addTab("Administrar peliculas", panelpeliculas);

        panelsalas.setBackground(new java.awt.Color(0, 102, 102));
        panelsalas.setMaximumSize(new java.awt.Dimension(942, 589));
        panelsalas.setMinimumSize(new java.awt.Dimension(942, 589));
        panelsalas.setPreferredSize(new java.awt.Dimension(942, 589));
        panelsalas.setLayout(null);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("ADMINISTRADOR DE SALAS");
        panelsalas.add(jLabel6);
        jLabel6.setBounds(280, 10, 396, 34);

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        jPanel2.setMaximumSize(new java.awt.Dimension(363, 540));
        jPanel2.setMinimumSize(new java.awt.Dimension(363, 540));
        jPanel2.setRequestFocusEnabled(false);
        jPanel2.setVerifyInputWhenFocusTarget(false);
        jPanel2.setLayout(null);

        numero_sala.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel2.add(numero_sala);
        numero_sala.setBounds(248, 71, 75, 23);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Crear sala");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(140, 20, 118, 29);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Capacidad de la sala:");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(40, 100, 189, 22);

        capacidad_sala.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel2.add(capacidad_sala);
        capacidad_sala.setBounds(247, 101, 75, 23);

        guardar_sala.setText("Guardar sala");
        guardar_sala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardar_salaActionPerformed(evt);
            }
        });
        jPanel2.add(guardar_sala);
        guardar_sala.setBounds(40, 140, 140, 23);

        modificar_sala.setText("Modificar sala");
        modificar_sala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificar_salaActionPerformed(evt);
            }
        });
        jPanel2.add(modificar_sala);
        modificar_sala.setBounds(40, 170, 140, 23);

        eliminar_sala.setText("Eliminar sala");
        eliminar_sala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminar_salaActionPerformed(evt);
            }
        });
        jPanel2.add(eliminar_sala);
        eliminar_sala.setBounds(40, 200, 140, 23);

        limp.setText("Limpiar");
        limp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpActionPerformed(evt);
            }
        });
        jPanel2.add(limp);
        limp.setBounds(240, 140, 90, 23);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Numero de la sala:");
        jPanel2.add(jLabel13);
        jLabel13.setBounds(40, 70, 169, 22);

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

        jPanel2.add(jScrollPane4);
        jScrollPane4.setBounds(40, 250, 310, 160);

        panelsalas.add(jPanel2);
        jPanel2.setBounds(20, 70, 390, 510);

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        jPanel3.setMaximumSize(new java.awt.Dimension(509, 540));
        jPanel3.setMinimumSize(new java.awt.Dimension(509, 540));
        jPanel3.setLayout(null);

        mSalas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        mSalas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSalasActionPerformed(evt);
            }
        });
        jPanel3.add(mSalas);
        mSalas.setBounds(250, 70, 187, 23);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Mantenimiento de sala");
        jPanel3.add(jLabel12);
        jLabel12.setBounds(100, 20, 270, 29);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Seleccione una sala:");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(40, 70, 185, 22);

        man.setBackground(new java.awt.Color(0, 102, 102));
        man.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 2));
        man.setMaximumSize(new java.awt.Dimension(420, 192));
        man.setLayout(null);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Pelicula a transmitir:");
        man.add(jLabel17);
        jLabel17.setBounds(20, 20, 188, 26);

        pelicula_transmitir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pelicula_transmitirActionPerformed(evt);
            }
        });
        man.add(pelicula_transmitir);
        pelicula_transmitir.setBounds(220, 20, 188, 26);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("horario:");
        man.add(jLabel18);
        jLabel18.setBounds(20, 70, 73, 26);

        agregarPelicula.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        agregarPelicula.setText("Agregar pelicula a la sala");
        agregarPelicula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarPeliculaActionPerformed(evt);
            }
        });
        man.add(agregarPelicula);
        agregarPelicula.setBounds(110, 110, 207, 36);
        man.add(peli_transmitir);
        peli_transmitir.setBounds(360, 120, 40, 20);

        horarios.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        horarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una hora", "12 pm -  3  pm", " 3  pm -  5  pm", " 5  pm -  8  pm", " 8  pm - 11 pm" }));
        horarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                horariosActionPerformed(evt);
            }
        });
        man.add(horarios);
        horarios.setBounds(110, 70, 188, 22);

        h.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hActionPerformed(evt);
            }
        });
        man.add(h);
        h.setBounds(370, 60, 39, 20);
        man.add(hora_peli);
        hora_peli.setBounds(320, 60, 40, 20);

        h1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h1ActionPerformed(evt);
            }
        });
        man.add(h1);
        h1.setBounds(370, 90, 39, 20);

        jPanel3.add(man);
        man.setBounds(30, 110, 420, 170);
        man.getAccessibleContext().setAccessibleName("");

        manSala.setBackground(new java.awt.Color(0, 102, 102));
        manSala.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 2));
        manSala.setMaximumSize(new java.awt.Dimension(421, 220));
        manSala.setLayout(null);

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

        manSala.add(jScrollPane3);
        jScrollPane3.setBounds(10, 50, 400, 130);

        nombreS.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        nombreS.setForeground(new java.awt.Color(255, 255, 255));
        manSala.add(nombreS);
        nombreS.setBounds(10, 10, 110, 29);

        jPanel3.add(manSala);
        manSala.setBounds(30, 290, 421, 200);

        panelsalas.add(jPanel3);
        jPanel3.setBounds(430, 70, 490, 510);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo_phixr.jpg"))); // NOI18N
        jLabel7.setText("jLabel7");
        panelsalas.add(jLabel7);
        jLabel7.setBounds(0, 0, 950, 590);

        jTabbedPane1.addTab("Administrar Salas", panelsalas);

        jPanel1.add(jTabbedPane1);
        jTabbedPane1.setBounds(0, 0, 960, 627);

        atras.setText("atras");
        atras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atrasActionPerformed(evt);
            }
        });
        jPanel1.add(atras);
        atras.setBounds(20, 640, 80, 23);

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo_phixr.jpg"))); // NOI18N
        jLabel15.setText("jLabel15");
        jPanel1.add(jLabel15);
        jLabel15.setBounds(0, 60, 960, 630);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void nombre_peliculaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombre_peliculaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombre_peliculaActionPerformed

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
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
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
