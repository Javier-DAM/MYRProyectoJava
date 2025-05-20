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

    /**
     * Dibuja que el juego está en pausa
     * @param g esto se utiliza para llamar a graphics
     */
    public void draw(Graphics g) {
        if (pausado) {
            g.drawString("Juego en Pausa", 800, 400);
        }
    }


}

