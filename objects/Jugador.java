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
        if (Teclado.arriba) {
            posicion.setY(posicion.getY() - 2);
        }
        if (Teclado.abajo) {
            posicion.setY(posicion.getY() + 2);
        }
        if (Teclado.izquierda) {
            posicion.setX(posicion.getX() - 2);
        }
        if (Teclado.derecha) {
            posicion.setX(posicion.getX() + 2);
        }
    }

    @Override
    public void draw(Graphics g) {

    }
}
