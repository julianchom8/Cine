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
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author familia pinillos
 */
public final class ventana_usuario extends javax.swing.JFrame {
    private String rutas_peli = "peliculas.txt"; //asigna nombre el archivo peliculas
    private String rutas_salas = "salas.txt"; //asigna el nombre al archivo salas 
    public static int capacidad ; 
    int clic_tabla;
    Pelicula peli; //clase pelicula
    Sala sala; //clase sala
    
    Proceso proceso_pelicula; // 
    Procesos proceso_sala;
    

    /**
     * Creates new form ventana_usuario
     */
    public ventana_usuario() {
        proceso_pelicula = new Proceso(); //nueva varible de tipo clase proceso -----> procesos de peliculas
        proceso_sala = new Procesos(); //nueva varible de tipo clase procesos -----> procesos de sala
        initComponents();
        try{
          cargar_pelis();
          cargar_sala();
          
        }catch(Exception ex){
            mensaje("No existe el archivo txt");
        }
        llenarCombo();
        
    }
    
    public void cargar_pelis(){ //carga el archivo peliculas
        File pos = null;  //apuntar al archivo almancenado DD
        FileReader fi = null;  //acceder a todo el contenido del archivo
        BufferedReader bu = null; //accede linea a linea al contenido
        try{
            pos = new File(rutas_peli);
            fi = new FileReader(pos); //se lee el archivo peliculas
            bu = new BufferedReader(fi); // se lee line por linea el archivo peliculas
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
        
    public void llenarCombo(){//muestra las pleiculas , los horarios y las salas ; 
       
        for(int i = 0; i < proceso_pelicula.cantidadRegistro(); i++){
            peli = proceso_pelicula.obtenerRegistro(i);
            
            seleccion.addItem(peli.getNombre());            
        }
    }
    
    public void mostrar(int n ){
        
        peli = proceso_pelicula.obtenerRegistro(n);
        
        pel3.setText(peli.getNombre());
        imagen.setIcon(new ImageIcon(peli.getRuta_imagen()));
    }

    
    public void buscarEn_salas(String p ){
        int cont = proceso_sala.cantidadRegistro();          
        for (int i = 0 ; i <= cont ; i++ ) {           
            String ruta= "sala"+i+".txt";   
            buscar(p , 0 , ruta);
        }
    }

    public void buscar(String filtro, int index, String ruta ){
        File archivo = null;  //apuntar al archivo almancenado DD
        FileReader contenido = null;  //acceder a todo el contenido del archivo
        BufferedReader linea = null; //accede linea a linea al contenido
        String busqueda = "";
        try{
            archivo = new File(ruta);
            contenido = new FileReader(archivo);
            linea = new BufferedReader(contenido);
            
            String cadena=""; //variable captura los datos del archivo
            while((cadena=linea.readLine()) != null){ //recorre todo el archivo
                String dato[] = cadena.split(",");
                if(dato[index].equals(filtro)){
                    busqueda = dato[0]+","+dato[1]+","+dato[3]+","+dato[4]; 
                    llenar(busqueda);
                    
                }
                
            }
        }catch(IOException e){
           
        }
        finally{
            try{
                if(contenido != null){
                    contenido.close();
                }
            }catch(IOException e1){
                
            }
        }
    }
    
    public void llenar(String linea){
        FileWriter fichero = null;  //objeto principal (archivo)
        PrintWriter l= null;   //objeto de contenido de archivo
        try{
            fichero = new FileWriter("ver.txt",true); //crea el archivo 
            l = new PrintWriter(fichero); //apunta el PrintWriter al archivo creado
            // Inicia captura de datos del usuario
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            l.println(linea); //escribiendo en el archivo
                   
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

    public void listarSalasPeliculas(){
        DefaultTableModel tabla = new DefaultTableModel();
        
        tabla.addColumn("pelicula");
        tabla.addColumn("Horario ");
        tabla.addColumn("sala");
        
        Object fila[] = new Object[tabla.getColumnCount()];
        
        File archivo = null;  //apuntar al archivo almancenado DD
        FileReader contenido = null;  //acceder a todo el contenido del archivo
        BufferedReader linea = null; //accede linea a linea al contenido
        
        try{
            archivo = new File("ver.txt");
            contenido = new FileReader(archivo);
            linea = new BufferedReader(contenido);
            
            String cadena=""; //variable captura los datos del archivo
            while((cadena=linea.readLine()) != null){ //recorre todo el archivo
                String dato[] = cadena.split(",");
                fila[0] = dato[0];
                fila[1] = dato[1];
                fila[2] = dato[2];
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
        lista.setModel(tabla);
        lista.setRowHeight(20);
        
        archivo();
        
    }  
    
    
    public void archivo(){
       
        File f = new File("ver.txt"); 
        
        f.delete();
        try {
            f.createNewFile();
        } catch (IOException ioe) {
        }    
    }
        public void buscarCapacidad(String filtro, int index ){
        File archivo = null;  //apuntar al archivo almancenado DD
        FileReader contenido = null;  //acceder a todo el contenido del archivo
        BufferedReader linea = null; //accede linea a linea al contenido
        String busqueda = "";
        try{
            archivo = new File("salas.txt");
            contenido = new FileReader(archivo);
            linea = new BufferedReader(contenido);
            
            String cadena=""; //variable captura los datos del archivo
            while((cadena=linea.readLine()) != null){ //recorre todo el archivo
                String dato[] = cadena.split(",");
                if(dato[index].equals(filtro)){
                    busqueda = dato[1];       
                }
                
            }
            int c =Integer.parseInt(busqueda); 
           
            capacidad = c; 
            System.out.println(capacidad+"--");
            int g = getCapacidad();
            System.out.println(g+"***-");
        }catch(IOException e){
           
        }
        finally{
            try{
                if(contenido != null){
                    contenido.close();
                }
            }catch(IOException e1){
                
            }
        }
    }

    public void mensaje(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }
    
    public void limp(){
            peliaver.setText(null);
            horaaver.setText(null);
            salaaver.setText(null);
    }
    
    /**
     * @return the capacidad
     */
    public int getCapacidad() {
        return capacidad;
    }
    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        imagen = new javax.swing.JLabel();
        pel3 = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        seleccion = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        lista = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        peliaver = new javax.swing.JLabel();
        horaaver = new javax.swing.JLabel();
        salaaver = new javax.swing.JLabel();
        ver_1 = new javax.swing.JButton();
        capaver = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        factura = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 153, 255));

        imagen.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        imagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/pel.png"))); // NOI18N
        imagen.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        imagen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 5));

        pel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        pel3.setForeground(new java.awt.Color(255, 255, 255));
        pel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        panel.setBackground(new java.awt.Color(0, 153, 255));
        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 204), 3));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Selecione una pelicula:");

        seleccion.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        seleccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione una pelicula" }));
        seleccion.setToolTipText("");
        seleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seleccionActionPerformed(evt);
            }
        });

        lista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "sala", "pelicula", "hora"
            }
        ));
        lista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lista);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Pelicula:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Horario:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Sala:");

        peliaver.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        horaaver.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        salaaver.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        ver_1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ver_1.setText("ver pelicula");
        ver_1.setToolTipText("");
        ver_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ver_1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("capacidad");

        factura.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        factura.setText("Factura");
        factura.setToolTipText("");
        factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facturaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(seleccion, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(horaaver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(peliaver, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(capaver, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(salaaver, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ver_1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                                .addComponent(factura)
                                .addGap(21, 21, 21)))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(seleccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(peliaver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(horaaver, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(ver_1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(salaaver, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(capaver, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(factura)))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        jButton1.setText("atras");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imagen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 736, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(11, 11, 11)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 508, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void ver_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ver_1ActionPerformed
       
        Sillas nj = new Sillas();
        nj.setVisible(true);
        nj.setLocationRelativeTo(null);         
               
    }//GEN-LAST:event_ver_1ActionPerformed

    private void seleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seleccionActionPerformed
        int sele =  seleccion.getSelectedIndex();
        String pel = (String)seleccion.getSelectedItem();
        int aux = sele - 1 ;
        String a= String.valueOf(aux);
        mostrar(aux);
        buscarEn_salas(pel);
        listarSalasPeliculas();
        limp();
    }//GEN-LAST:event_seleccionActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Ventana_inicio ini = new Ventana_inicio();
            ini.setLocationRelativeTo(null);
            ini.setVisible(true);
        ventana_usuario.this.dispose();// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void listaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaMouseClicked
            clic_tabla = lista.rowAtPoint(evt.getPoint());
 
            String nombre = ""+lista.getValueAt(clic_tabla, 0);
            String horario = ""+lista.getValueAt(clic_tabla, 1);
            String s = String.valueOf(lista.getValueAt(clic_tabla, 2));
            
            int sal = Integer.parseInt(s);//sala
            buscarCapacidad(s,0);
            
            peliaver.setText(nombre);
            horaaver.setText(horario);
            salaaver.setText(String.valueOf(sal));
 
    }//GEN-LAST:event_listaMouseClicked

    private void facturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facturaActionPerformed
          recibo r = new recibo();
          r.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_facturaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(ventana_usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventana_usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventana_usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventana_usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventana_usuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JLabel capaver;
    private javax.swing.JButton factura;
    private javax.swing.JLabel horaaver;
    private javax.swing.JLabel imagen;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable lista;
    private javax.swing.JPanel panel;
    private javax.swing.JLabel pel3;
    private javax.swing.JLabel peliaver;
    private javax.swing.JLabel salaaver;
    private javax.swing.JComboBox<String> seleccion;
    private javax.swing.JButton ver_1;
    // End of variables declaration//GEN-END:variables
}
