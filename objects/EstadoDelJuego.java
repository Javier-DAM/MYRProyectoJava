package objects;

import java.awt.Graphics;
public class EstadoDelJuego {

    private boolean pausado;

    public EstadoDelJuego() {
        this.pausado = false;
    }

    public void update() {
        if (pausado) return;
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

