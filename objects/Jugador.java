package objects;

import input.Teclado;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Jugador extends ObjetoJuego {
    private int id; // 1 = Mona, 2 = Rona

    public Jugador(int id, Vector2D posicion, BufferedImage[] sprites) {
        super(posicion, sprites);
        this.id = id;
    }

    public void updateJugador1() {
        if (Teclado.atacar || Teclado.bloquear) return;

        if (Teclado.arriba) posicion.setPlayer1Y(posicion.getPlayer1Y() - 2);
        if (Teclado.abajo) posicion.setPlayer1Y(posicion.getPlayer1Y() + 2);
        if (Teclado.izquierda) posicion.setPlayer1X(posicion.getPlayer1X() - 2);
        if (Teclado.derecha) posicion.setPlayer1X(posicion.getPlayer1X() + 2);
    }

    public void updateJugador2() {
        if (Teclado.atacar2 || Teclado.bloquear2) return;

        if (Teclado.arriba2) posicion.setPlayer2Y(posicion.getPlayer2Y() - 2);
        if (Teclado.abajo2) posicion.setPlayer2Y(posicion.getPlayer2Y() + 2);
        if (Teclado.izquierda2) posicion.setPlayer2X(posicion.getPlayer2X() - 2);
        if (Teclado.derecha2) posicion.setPlayer2X(posicion.getPlayer2X() + 2);
    }


    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {

    }
}
