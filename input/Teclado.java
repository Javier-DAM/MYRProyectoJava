package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Teclado implements KeyListener {
    private boolean[] keys = new boolean[256];

    public static boolean arriba=false ;
    public static boolean abajo=false ;
    public static boolean izquierda=false ;
    public static boolean derecha=false ;



    public Teclado() {
        this.arriba = arriba;
        this.abajo = abajo;
        this.izquierda = izquierda;
        this.derecha = derecha;


    }
    public void update(){
        arriba = keys[KeyEvent.VK_W];
        abajo = keys[KeyEvent.VK_S];
        izquierda = keys[KeyEvent.VK_A];
        derecha = keys[KeyEvent.VK_D];
    }
    @Override
    public void keyPressed(KeyEvent e) {
    keys[e.getKeyCode()] = true;

    }
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    public void movimiento(){
        if(arriba||abajo||izquierda||derecha){


        }
    }


}
