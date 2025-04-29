package objects;
import input.Teclado;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Jugador extends ObjetoJuego {
    public Jugador(Vector2D posicion, BufferedImage[] sprites) {
        super(posicion, sprites);
    }

    @Override
    public void update() {
        if(Teclado.arriba){
            posicion.setY(posicion.getY()-2);
            System.out.println("w");
        }
        if(Teclado.abajo){
            posicion.setY(posicion.getY()+2);
            System.out.println("s");
        }
        if(Teclado.izquierda){
            posicion.setX(posicion.getX()-2);
            System.out.println("a");
        }
        if(Teclado.derecha){
            posicion.setX(posicion.getX()+2);
            System.out.println("d");
        }

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, (int) posicion.getX(), (int) posicion.getY(), null);
    }
}
