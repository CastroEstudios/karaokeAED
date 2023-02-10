/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package danielcastro.karaokeaed.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Anima
 */
public class PantallaInicio extends javax.swing.JFrame {

    /**
     * Creates new form PantallaInicio
     */
    public PantallaInicio() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        styles();
    }
    
    private void styles() {
        labelInicio.setText("BIENVENIDO");
        labelTextoOpcion.setText("ELIJA UNA OPCIÓN:");
        initBGImage(".\\src\\main\\java\\danielcastro\\karaokeaed\\img\\BG_Inicio.png", labelIntoJPanel(jPanel2));
    }
    
    public static void initBGImage(String url, JLabel label) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(label.getWidth(), label.getHeight(),
        Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(dimg); 
        label.setIcon(icon);
    }
        
    public static void initBGImage(String url, JButton boton) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(boton.getWidth(), boton.getHeight(),
        Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(dimg); 
        boton.setIcon(icon);
    }
        
    //Creates a JLabel inside a JPanel in order to set the background image for that JPanel. 
    public static JLabel labelIntoJPanel(JPanel panel) {
        JLabel label = new JLabel();
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        label.setBounds(0, 0, panel.getWidth(), panel.getHeight());
        return label;
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
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        labelTextoOpcion = new javax.swing.JLabel();
        labelInicio = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setLayout(new java.awt.BorderLayout(20, 0));

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Microsoft JhengHei", 0, 18)); // NOI18N
        jButton1.setText("CRUD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 470, 120));

        jButton3.setFont(new java.awt.Font("Microsoft JhengHei", 0, 18)); // NOI18N
        jButton3.setText("INFORMES");
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 470, 120));

        jButton2.setFont(new java.awt.Font("Microsoft JhengHei", 0, 18)); // NOI18N
        jButton2.setText("CONFIGURACIÓN");
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, 470, 120));

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelTextoOpcion.setFont(new java.awt.Font("Microsoft JhengHei", 1, 28)); // NOI18N
        labelTextoOpcion.setForeground(new java.awt.Color(51, 51, 51));
        labelTextoOpcion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(labelTextoOpcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 500, 170));

        labelInicio.setFont(new java.awt.Font("Microsoft JhengHei", 1, 28)); // NOI18N
        labelInicio.setForeground(new java.awt.Color(51, 51, 51));
        labelInicio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(labelInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 500, 110));

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel5.setOpaque(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 65, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        PantallaCRUD pc = new PantallaCRUD();
        this.setVisible(false);
        pc.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(PantallaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        FlatDarkLaf.setup();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel labelInicio;
    private javax.swing.JLabel labelTextoOpcion;
    // End of variables declaration//GEN-END:variables
}