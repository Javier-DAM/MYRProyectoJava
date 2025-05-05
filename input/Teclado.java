package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Teclado implements KeyListener {
    private boolean[] keys = new boolean[256];

    //Jugador 1 :) - Controles WASD JK
    public static boolean arriba = false;
    public static boolean abajo = false;
    public static boolean izquierda = false;
    public static boolean derecha = false;
    public static boolean atacar = false;
    public static boolean bloquear = false;

    //Jugador 2 :( - Controles ↑↓→← .,
    public static boolean arriba2 = false;
    public static boolean abajo2 = false;
    public static boolean izquierda2 = false;
    public static boolean derecha2 = false;
    public static boolean atacar2 = false;
    public static boolean bloquear2 = false;

    //Menus
    public static boolean menuESC = false;


    public Teclado() {
        //Jugador 1
        this.arriba = arriba;
        this.abajo = abajo;
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.atacar = atacar;
        this.bloquear = bloquear;

        //Jugador 2
        this.arriba2 = arriba2;
        this.abajo2 = abajo2;
        this.izquierda2 = izquierda2;
        this.derecha2 = derecha2;
        this.atacar2 = atacar2;
        this.bloquear2 = bloquear2;

        //Menus
        this.menuESC = menuESC;
    }

    public void update(){

        //Jugador 1
        arriba = keys[KeyEvent.VK_W];   //W
        abajo = keys[KeyEvent.VK_S];    //S
        izquierda = keys[KeyEvent.VK_A];//A
        derecha = keys[KeyEvent.VK_D];  //D
        atacar = keys[KeyEvent.VK_V];   //V
        bloquear = keys[KeyEvent.VK_B]; //B

        //Jugador 2
        arriba2    = keys[KeyEvent.VK_UP];      //↑
        abajo2     = keys[KeyEvent.VK_DOWN];    //↓
        izquierda2 = keys[KeyEvent.VK_LEFT];    //←
        derecha2   = keys[KeyEvent.VK_RIGHT];   //→
        atacar2    = keys[KeyEvent.VK_COMMA];   // tecla ,
        bloquear2  = keys[KeyEvent.VK_PERIOD];  // tecla .

        //Menus
        menuESC = keys[KeyEvent.VK_ESCAPE];
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
    
}
