/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_cine;

/**
 *
 * @author familia pinillos
 */
public class Ventana_inicio extends javax.swing.JFrame {

    /**
     * Creates new form Ventana_inicio
     */
    public Ventana_inicio() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Administrador = new javax.swing.JButton();
        Usuario = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(600, 376));
        setMinimumSize(new java.awt.Dimension(600, 376));
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setLayout(null);

        Administrador.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Administrador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/78948.png"))); // NOI18N
        Administrador.setText("Administrador");
        Administrador.setMaximumSize(new java.awt.Dimension(1783, 1609));
        Administrador.setMinimumSize(new java.awt.Dimension(1783, 1609));
        Administrador.setPreferredSize(new java.awt.Dimension(1783, 1609));
        Administrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdministradorActionPerformed(evt);
            }
        });
        jPanel1.add(Administrador);
        Administrador.setBounds(30, 140, 271, 111);

        Usuario.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/administrator-male.png"))); // NOI18N
        Usuario.setText("Usuario");
        Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsuarioActionPerformed(evt);
            }
        });
        jPanel1.add(Usuario);
        Usuario.setBounds(320, 140, 251, 111);

        jLabel1.setBackground(new java.awt.Color(255, 0, 0));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("CINEUDEC");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(170, 50, 260, 58);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondo.jpg"))); // NOI18N
        jPanel1.add(jLabel8);
        jLabel8.setBounds(0, 0, 600, 350);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 600, 350);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsuarioActionPerformed
        ventana_usuario usuario = new ventana_usuario();
        usuario.setLocationRelativeTo(null);
        usuario.setVisible(true);
        Ventana_inicio.this.dispose();
    }//GEN-LAST:event_UsuarioActionPerformed

    private void AdministradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdministradorActionPerformed
        ventana_administrador admin = new ventana_administrador();
        admin.setLocationRelativeTo(null);
        admin.setVisible(true);
        Ventana_inicio.this.dispose();
    }//GEN-LAST:event_AdministradorActionPerformed

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
            java.util.logging.Logger.getLogger(Ventana_inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana_inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana_inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana_inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana_inicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Administrador;
    private javax.swing.JButton Usuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
