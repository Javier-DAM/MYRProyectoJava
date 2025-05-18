package Menu;

import input.Teclado;
import objects.Jugador;

import javax.swing.*;
import java.awt.*;

public class Menu {

    public static final int width = 1920, height = 1080;
    private boolean startRequests = false;
    private Teclado teclado;
    private boolean pausa = true;
    private boolean enterPresionadoAnteriormente = true;
    private boolean iniciado = false;
    private boolean juegoTerminado = false;
    private boolean reiniciarSolicitado = false;
    private Jugador jugador1, jugador2;

    public Menu(Teclado teclado) {
        this.teclado = teclado;
    }

    public void update() {
        if (teclado.enter && !enterPresionadoAnteriormente) {
            if (!juegoTerminado) {
                pausa = !pausa;

                if (!iniciado) {
                    iniciado = true;
                }

                System.out.println(pausa ? "Juego pausado" : "Juego reanudado");
            }
            enterPresionadoAnteriormente = true;
        } else if (!teclado.enter) {
            enterPresionadoAnteriormente = false;
        }
    }

    public void updateMuerte() {
        if (teclado.enter) {
            reiniciarSolicitado = true;
        }
    }

    public boolean isPaused() {
        return pausa;
    }

    public boolean isStartRequested() {
        return startRequests;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public void setJuegoTerminado(boolean juegoTerminado) {
        this.juegoTerminado = juegoTerminado;
    }

    public boolean isReiniciarSolicitado() {
        return reiniciarSolicitado;
    }

    public void resetReiniciarSolicitado() {
        this.reiniciarSolicitado = false;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, width, height);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics fm = g.getFontMetrics();

        String texto = !iniciado ? "Enter para iniciar el juego" : "Pausa - Enter para continuar";
        int x = (width - fm.stringWidth(texto)) / 2; //fm = Font Metrics
        int y = height / 2;

        g.drawString(texto, x, y);
    }

    public void drawMuerte(Graphics g, Jugador jugador1, Jugador jugador2) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, width, height);

        g.setColor(Color.WHITE);

        // Título
        g.setFont(new Font("Arial", Font.BOLD, 50));
        FontMetrics fm = g.getFontMetrics();
        String titulo = "Fin de la partida";
        g.drawString(titulo, (width - fm.stringWidth(titulo)) / 2, 300);

        // Resultado
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        fm = g.getFontMetrics();
        String resultado;

        if (jugador1.getSalud() <= 0 && jugador2.getSalud() > 0) {
            resultado = "Jugador 2 ha ganado";
        } else if (jugador2.getSalud() <= 0 && jugador1.getSalud() > 0) {
            resultado = "Jugador 1 ha ganado";
        } else {
            int kills1 = jugador1.getEnemigosDerrotados();
            int kills2 = jugador2.getEnemigosDerrotados();
            if (kills1 > kills2) {
                resultado = "Jugador 1 ha ganado (más enemigos derrotados)";
            } else if (kills2 > kills1) {
                resultado = "Jugador 2 ha ganado (más enemigos derrotados)";
            } else {
                resultado = "Empate";
            }
        }

        g.drawString(resultado, (width - fm.stringWidth(resultado)) / 2, 350);

        // Estadísticas
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        fm = g.getFontMetrics();
        String score1 = "Jugador 1 eliminó: " + jugador1.getEnemigosDerrotados();
        String score2 = "Jugador 2 eliminó: " + jugador2.getEnemigosDerrotados();
        String reinicio = "Presiona Enter para reiniciar";

        g.drawString(score1, (width - fm.stringWidth(score1)) / 2, 390);
        g.drawString(score2, (width - fm.stringWidth(score2)) / 2, 420);
        g.drawString(reinicio, (width - fm.stringWidth(reinicio)) / 2, 460);
    }
}

