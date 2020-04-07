/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Juego;

import static Juego.Monitor.monitor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Fernando
 */
public class tablero extends javax.swing.JFrame {

    /**
     * Creates new form abc
     */
    public tablero() {
        initComponents();
        llenararray();
        this.setSize(new Dimension(650, 650));
        this.setLocationRelativeTo(null);
        Venta.setSize(this.getSize());
        Venta.setVisible(true);
        Venta.setLayout(null);
        //Colocamos el fondo de pantalla
        Fondo.setSize(663,662);
        Fondo.setVisible(true);
        Fondo.setLayout(null);
        imagenes("src\\Imagenes\\fp2.jpg",Fondo);
        Venta.add(Fondo,0);
        //Colocamos la cabeza de gusanito
        Gusanito.setSize(25, 25);
        Gusanito.setLayout(null);
        Gusanito.setLocation(300, 300);
        imagenes("src\\imagenes\\cabeza.png", Gusanito);
        serpiente.add(Gusanito);
        Venta.add(serpiente.get(0),0);
        this.add(Venta,0);
        monitor.iniciar(Venta, this, manza);
        ser = new Rectangle(serpiente.get(0).getBounds());
        comi = new ArrayList<Rectangle>();
        for (int i = 0; i < manza.size(); i++) {
            comi.add(manza.get(i).getManzana().getBounds());
        }
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent arg0) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
             @Override
            public void keyReleased(KeyEvent arg0) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public void keyPressed(KeyEvent arg0) {
                if(arg0.getKeyCode() == 40){
                    //precionamos flecha hacia abajo
                    if(serpiente.get(0).getY() < 600 ){
                        y = descontar;
                        x = 0;
                    }
                    if(!bandera){
                        tiempo.start();
                        bandera = true;
                    }
                }
                else if (arg0.getKeyCode() == 38){
                    //precionamos flecha hacia arriba
                    if(serpiente.get(0).getY() > 0 ){
                        y = -descontar;
                        x = 0;
                    }
                    if(!bandera){
                        tiempo.start();
                        bandera = true;
                    }
          
                }
                else if(arg0.getKeyCode() == 37){
                    //precionamos flecha hacia la izquierda
                    if(serpiente.get(0).getX() > 0 ){
                        y = 0;
                        x = -descontar;
                    }
                    if(!bandera){
                        tiempo.start();
                        bandera = true;
                    }
                }
                else if(arg0.getKeyCode() == 39){
                    //precionamos flehca hacia la derecha
                    if(serpiente.get(0).getX() < 600 ){
                        y = 0;
                        x = descontar;
                    }
                    if(!bandera){
                        tiempo.start();
                        bandera = true;
                    }
                }
                
            }
        });
         tiempo = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                 for (int i = 0; i < manza.size(); i++) {
                       comi.get(i).setBounds(manza.get(i).getManzana().getBounds());
                       ser.setBounds(serpiente.get(0).getBounds());
                        if(comi.get(i).intersects(ser)){
                            System.out.println("iniactivar"+comi.get(i).intersects(ser));
                            monitor.Comida();
                            manza.get(i).borrar();
                            manza.get(i).getManzana().repaint();
                            Manzana nm = new Manzana();                           
                            serpiente.add(nm.Cuerpogusano());
                            Venta.add(serpiente.get(serpiente.size()-1),0);

                        }
                }
                for(int a = 0; a < serpiente.size(); a++){
                    if(a == 0){
                        xantes = serpiente.get(a).getX();
                        yantes = serpiente.get(a).getY();
                        if(xantes < 0){
                            serpiente.get(a).setLocation(590, serpiente.get(a).getY()+y);
                            serpiente.get(a).repaint();
                        }
                        else if(yantes < 0){
                            serpiente.get(a).setLocation(serpiente.get(a).getX()+x,590);
                            serpiente.get(a).repaint();
                        }
                        else if(xantes > 600){
                            serpiente.get(a).setLocation(0, serpiente.get(a).getY()+y);
                            serpiente.get(a).repaint();
                        }
                        else if(yantes >600){
                            serpiente.get(a).setLocation(serpiente.get(a).getX()+x,0);
                            serpiente.get(a).repaint();
                        }else{
                            serpiente.get(a).setLocation(serpiente.get(a).getX()+x, serpiente.get(a).getY()+y);
                            serpiente.get(a).repaint();
                        }
                     
                    }else{
                        int x1 = serpiente.get(a).getX();
                        int y2 = serpiente.get(a).getY();
                        serpiente.get(a).setLocation(xantes, yantes);
                        serpiente.get(a).repaint();
                        xantes = x1;
                        yantes = y2;
                    }
                }
               
            }
        });
    }
    JPanel Venta = new JPanel();
    JLabel Fondo = new JLabel();
    JLabel Gusanito = new JLabel();
    Monitor monitor = new Monitor();
    Timer tiempo;
    int cont,valor, x, y , xantes, yantes = 0;
    int descontar = 20;
    boolean bandera = false;
    Rectangle ser;
    ArrayList<Rectangle> comi;
    ArrayList<JLabel> serpiente = new ArrayList<>();
    ArrayList<Manzana> manza = new ArrayList<>();
    public  void imagenes(String ruta, JLabel lugar){
        Image  img = new ImageIcon(ruta).getImage();
        ImageIcon mostrar = new ImageIcon(img.getScaledInstance(lugar.getWidth(),lugar.getHeight(), Image.SCALE_SMOOTH));
        lugar.setIcon(mostrar);
    }
    private void llenararray(){
        for (int i = 0; i < 10; i++) {
            Manzana nueva_manzana = new Manzana();
            nueva_manzana.Generarvacios();
            manza.add(nueva_manzana);
            Venta.add(nueva_manzana.getManzana(),0);
        }
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tablero.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tablero().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
