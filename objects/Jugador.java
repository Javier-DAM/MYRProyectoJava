package objects;

import input.Teclado;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Jugador extends ObjetoJuego {
    private int id;
    private int salud = 10;

    public Jugador(int id, Vector2D posicion, BufferedImage[] sprites) {
        super(posicion, sprites);
        this.id = id;
    }

    public int getSalud(){
        return salud;
    }

    public void updateJugador1() {
        if (Teclado.atacar || Teclado.bloquear) return;
        if (Teclado.arriba) posicion.setY(posicion.getY() - 2);
        if (Teclado.abajo) posicion.setY(posicion.getY() + 2);
        if (Teclado.izquierda) posicion.setX(posicion.getX() - 2);
        if (Teclado.derecha) posicion.setX(posicion.getX() + 2);
    }

    public void updateJugador2() {
        if (Teclado.atacar2 || Teclado.bloquear2) return;
        if (Teclado.arriba2) posicion.setY(posicion.getY() - 2);
        if (Teclado.abajo2) posicion.setY(posicion.getY() + 2);
        if (Teclado.izquierda2) posicion.setX(posicion.getX() - 2);
        if (Teclado.derecha2) posicion.setX(posicion.getX() + 2);
    }

    public void recibirDaño(int cantidad) {
        salud -= cantidad;
        System.out.println("¡Jugador " + id + " recibió " + cantidad + " de daño! Salud restante: " + salud);
        if (salud <= 0) {
            System.out.println("Jugador " + id + " ha muerto.");
        }
    }

    @Override
    public void update() {
        if (id == 1) updateJugador1();
        else updateJugador2();
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, (int) posicion.getX(), (int) posicion.getY(), null);
    }
}