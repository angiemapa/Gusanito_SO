package Juego;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Manzana {

    private int posx;
    private int posy;
    private JLabel Manzana;
    boolean activo = false;
    Random r = new Random();

    public JLabel getManzana() {
        return Manzana;
    }

    public void setCrear(JLabel Manzana) {
        this.Manzana = Manzana;
        this.Manzana.setSize(25, 25);
        this.Manzana.setLayout(null);
        this.Manzana.setVisible(true);
        this.Manzana.setLocation(r.nextInt(600) + 1, r.nextInt(600) + 1);
        imagenes("src\\Imagenes\\fruta.png", Manzana);
        activo = true;
    }

    public void Generarvacios() {
        Manzana = new JLabel();
        this.Manzana = Manzana;
        this.Manzana.setSize(0, 0);
        this.Manzana.setLayout(null);
        this.Manzana.setVisible(true);
        this.Manzana.setLocation(800, 800);
    }

    public void posicion() {
        this.Manzana.setSize(25, 25);
        this.Manzana.setLocation(r.nextInt(600) + 1, r.nextInt(600) + 1);
        imagenes("src\\Imagenes\\fruta.png", this.Manzana);
    }
    public void borrar() {
        this.Manzana.setSize(0, 0);
        this.Manzana.setLocation(900, 900);
        this.Manzana.setIcon(null);
        activo = false;
        //imagenes("src\\Imagenes\\fruta.png", this.Manzana);
    }

    public JLabel Cuerpogusano() {
        JLabel nuevo = new JLabel();
        nuevo.setSize(25, 25);
        nuevo.setLayout(null);
        nuevo.setLocation(r.nextInt(580) + 1, r.nextInt(580) + 1);
        imagenes("src\\Imagenes\\c1.png", nuevo);
        return nuevo;
    }

    private void imagenes(String ruta, JLabel lugar) {
        Image img = new ImageIcon(ruta).getImage();
        ImageIcon mostrar = new ImageIcon(img.getScaledInstance(lugar.getWidth(), lugar.getHeight(), Image.SCALE_SMOOTH));
        lugar.setIcon(mostrar);
    }
    public boolean getEstado(){
        return this.activo;
    }
    public void Activar(){
        this.activo = true;
    }
}
