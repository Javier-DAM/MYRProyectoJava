package objects;

import java.awt.Graphics;

/**
 * Clase base para representar el estado general del juego.
 * Aquí se podrían manejar cosas como lógica de colisiones,
 * detección de victoria, UI, pausa, etc.
 */
public class GameState {

    private boolean pausado;

    public GameState() {
        this.pausado = false;
    }

    public void update() {
        if (pausado) return;

        // Aquí iría lógica global del juego, como detectar colisiones,
        // actualizar temporizadores, etc.
    }

    public void draw(Graphics g) {
        if (pausado) {
            g.drawString("Juego en Pausa", 800, 400);
        }
    }

    public void pausar() {
        pausado = !pausado;
    }

    public boolean isPausado() {
        return pausado;
    }
}

