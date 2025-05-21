package objects;

import input.Teclado;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Jugador extends ObjetoJuego {
    private int id;
    private int salud = 25;
    private float velocidad = 2.75f;
    private int enemigosDerrotados = 0;

    public Jugador(int id, Vector2D posicion, BufferedImage[] sprites) {
        super(posicion, sprites);
        this.id = id;
    }

    public int getSalud(){
        return salud;
    }

    /**
     * Contador de los enemigos que mueren
     */
    public void incrementarEnemigosDerrotados() {
        enemigosDerrotados++;
    }

    public int getEnemigosDerrotados() {
        return enemigosDerrotados;
    }

    /**
     * Actualizar método updateJugador1 usando velocidad
     */
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

    /**
     * Actualizar método updateJugador2 usando velocidad
     */
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

    /**
     * Metodo que hace que la salud del jugador baje cuando recibe daño
     * @param cantidad cantidad de daño a recibir
     */
    public void recibirDaño(int cantidad) {
        salud -= cantidad;
        System.out.println("Jugador " + id + " recibió " + cantidad + " de daño - Salud restante: " + salud);
        if (salud <= 0) {
            System.out.println("Jugador " + id + " ha muerto.");
        }
    }

    /**
     * Llma a los metodos update de los jugadores por su id
     */
    @Override
    public void update() {
        if (id == 1) updateJugador1();
        else updateJugador2();
    }

    /**
     * Dibuja al jugador
     * @param g esto se utiliza para llamar a graphics
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, (int) posicion.getX(), (int) posicion.getY(), null);
    }
}