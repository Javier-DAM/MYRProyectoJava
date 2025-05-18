import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Window ventana = new Window();
        Scanner sc = new Scanner(System.in);
        ventana.iniciarJuego();
    }
}

/*
*                           -Cosas por arreglar-
*
* 1 - Que se muestra un Sprite por encima de otro si su posición en el ejeY es menor.
* 2 - No dejar que se muevan los jugadores cuando estén muertos.
* 3 - Al pulsar el botón de atacar, que la animación se haga completamente y no se corte.
* 4 - Que los enemigos reciban daño.
* 5 - Que los enemigos hagan correctamente el cambio de Sprites dependiendo de su acción. Jelly y Foxy.
* 6 - Que al pulsar ESC se pause el juego (No prioritario).
*
*                           -Cosas por añadir-
* 1 - Menus.
*   A - Menú de Start
*   B - Menú de Pausa
*   C - Menú de muerte
* 2 - Más Mob (bichos)
* 3 -
* */