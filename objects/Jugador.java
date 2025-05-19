package objects;

import input.Teclado;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Jugador extends ObjetoJuego {
    private int id;
    private int salud = 30;
    private float velocidad = 3.0f;
    private int enemigosDerrotados = 0;

    public Jugador(int id, Vector2D posicion, BufferedImage[] sprites) {
        super(posicion, sprites);
        this.id = id;
    }

    public float getVelocidad() {
        return velocidad;
    }

    public int getSalud(){
        return salud;
    }

    public void incrementarEnemigosDerrotados() {
        enemigosDerrotados++;
    }

    public int getEnemigosDerrotados() {
        return enemigosDerrotados;
    }

    // Actualizar método updateJugador1 usando velocidad
    public void updateJugador1() {
        if (salud < 1) return;
        if (Teclado.atacar || Teclado.bloquear) return;

        if (Teclado.arriba) posicion.setY(posicion.getY() - velocidad);
        if (Teclado.abajo) posicion.setY(posicion.getY() + velocidad);
        if (Teclado.izquierda) posicion.setX(posicion.getX() - velocidad);
        if (Teclado.derecha) posicion.setX(posicion.getX() + velocidad);

        // Limitar en eje X
        if (posicion.getX() < 0) {
            posicion.setX(0);
        } else if (posicion.getX() > 1792) {
            posicion.setX(1792);
        }

// Limitar en eje Y
        if (posicion.getY() < -50) {
            posicion.setY(-50);
        } else if (posicion.getY() > 850) {
            posicion.setY(850);
        }
    }

    // Igual para jugador 2
    public void updateJugador2() {
        if (salud < 1) return;
        if (Teclado.atacar2 || Teclado.bloquear2) return;

        if (Teclado.arriba2) posicion.setY(posicion.getY() - velocidad);
        if (Teclado.abajo2) posicion.setY(posicion.getY() + velocidad);
        if (Teclado.izquierda2) posicion.setX(posicion.getX() - velocidad);
        if (Teclado.derecha2) posicion.setX(posicion.getX() + velocidad);

// Limitar en eje X
        if (posicion.getX() < 0) {
            posicion.setX(0);
        } else if (posicion.getX() > 1792) {
            posicion.setX(1792);
        }

// Limitar en eje Y
        if (posicion.getY() < -50) {
            posicion.setY(-50);
        } else if (posicion.getY() > 850) {
            posicion.setY(850);
        }
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