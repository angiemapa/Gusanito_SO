package gusanito_s_0;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Monitor {
    static final int N = 5; // constante que proporciona el tamaño del búfer
    static productor p = new productor(); // crea instancia de un nuevo hilo productor
    static consumidor c = new consumidor(); // crea instancia de un nuevo hilo consumidor
    static super_monitor monitor = new super_monitor(); // crea instancia de un nuevo monitor
    
    public void iniciar(JPanel Pantalla, JFrame Ventana, ArrayList<Manzana> Agregarmanza){
        p.iniciarconsumidor(Pantalla, Ventana, Agregarmanza);
        p.start(); // inicia el hilo productor
    }
    public void Comida(){
        c.comer();
    }
    
    static class productor extends Thread {
        JPanel Pantalla;
        JFrame Ventana;
        ArrayList<Manzana> Lista_Manzanas = new ArrayList<>();
        public void run(){
            JLabel elemento;
            while(true){ // ciclo del productor
                elemento = producir_elemento();
                monitor.insertar(elemento, Pantalla, Ventana, Lista_Manzanas);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        public void iniciarconsumidor(JPanel Pantalla, JFrame Ventana, ArrayList<Manzana> Agregarmanza){
            this.Pantalla = Pantalla;
            this.Ventana = Ventana;
            this.Lista_Manzanas = Agregarmanza;
        }
        private JLabel producir_elemento(){
            JLabel manzana1 = new JLabel();
            return manzana1;
        }
    }
    
    static class consumidor extends Thread{
        public void comer(){
            monitor.eliminar();
        }
    }
    
    static class super_monitor{ // monitor como solución
        private int bufer[] = new int[N];
        private int cuenta=0, inf=0, sup=0; // contadores e índices
        Timer tiempo;
        public synchronized void insertar(JLabel nueva,JPanel Pantalla, JFrame Ventana, ArrayList<Manzana> Agregarmanza){
            if (cuenta==N)
                ir_a_estado_inactivo(); // si el búfer está lleno, pasa al estado inactivo
            bufer[sup]=sup; // inserta un elemento en el búfer
            for (int i = 0; i < N; i++) {
                if(!Agregarmanza.get(i).getEstado()){
                    Agregarmanza.get(i).posicion();
                    Agregarmanza.get(i).getManzana().repaint();
                    Pantalla.add(Agregarmanza.get(i).getManzana(),0);
                    System.out.println("Produciendo manzana"+i);
                    Agregarmanza.get(i).Activar();
                }
                
            }
            sup=(sup+1)%N; // ranura en la que se va a colocar el siguiente elemento
            cuenta=cuenta+1; // ahora hay un elemento más en el búfer  
            if (cuenta==1)
                notify(); // si el consumidor estaba inactivo, lo despierta [signal]
        }
        
        
        public synchronized int eliminar(){
            int val;
            if (cuenta==0)
                ir_a_estado_inactivo(); // si el búfer está vacío, pasa al estado inactivo
            val=bufer[inf]; // obtiene un elemento del búfer
            System.out.println("Consumiendo Manzana"+inf);
            inf = (inf + 1) %N; // ranura en la que se va a colocar el siguiente elemento
            cuenta=cuenta-1; // un elemento menos en el búfer
            if (cuenta==N-1)
                notify(); // si el productor estaba inactivo, lo despierta [signal]
            return val;
        }
        
        private void ir_a_estado_inactivo(){
            try{
                System.out.println("durmiento");
                wait(); // Duerme al proceso en turno
                
            }
            catch(InterruptedException exc){};
        }
    }
    
}