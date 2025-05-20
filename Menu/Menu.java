package Menu;

import input.Teclado;
import objects.Jugador;
import java.awt.*;

public class Menu {

    public static final int width = 1920, height = 1080;
    private boolean startRequests = false;
    private Teclado teclado;
    private boolean pausa = true;
    private boolean enterPresionadoAnteriormente = false;
    private boolean iniciado = false;
    private boolean juegoTerminado = false;
    private boolean reiniciarSolicitado = false;

    public Menu(Teclado teclado) {
        this.teclado = teclado;
    }

    /**
     * Sirve para actualizar el estado del menu
     */
    public void update() {
        if (teclado.enter && !enterPresionadoAnteriormente) {
            if (!juegoTerminado) {
                if (!iniciado) {
                    iniciado = true;
                    pausa = false;  // Empieza el juego al presionar Enter por primera vez
                } else {
                    pausa = !pausa;  // Alterna entre pausa y continuar en posteriores pulsaciones
                }
            }
            enterPresionadoAnteriormente = true;
        } else if (!teclado.enter) {
            enterPresionadoAnteriormente = false;
        }
    }

    /**
     * Sirve para levantar una bandera que reiniciara el juego una vez acabada la partida
     */
    public void updateMuerte() {
        if (teclado.enter) {
            reiniciarSolicitado = true;
        }
    }

    /**
     * Es para saber si el juego esta en pausa
     * @return devuelve un booleano dependiendo si esta en pausa o no
     */
    public boolean isPaused() {
        return pausa;
    }

    /**
     * solicitiud para iniciar el juego
     * @return devuelve un booleano
     */
    public boolean isStartRequested() {
        return startRequests;
    }

    /**
     * Commprueba si el juego ha terminado
     * @return devuelve una bandera que indica que el juego ha terminado
     */
    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }


    public void setJuegoTerminado(boolean juegoTerminado) {
        this.juegoTerminado = juegoTerminado;
    }

    /**
     * Commprueba si el jugador quiere reiniciar
     * @return Una bandera que indica si se va a reiniciar
     */
    public boolean isReiniciarSolicitado() {
        return reiniciarSolicitado;
    }



    public void resetReiniciarSolicitado() {
        this.reiniciarSolicitado = false;
    }

    /**
     * Dibuja el texto que contiene el menu
     * @param g esto se utiliza para llamar a graphics
     */
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

    /**
     * Dibujar el resultado de la partida cuando los dos personajes mueren
     * @param g esto se utiliza para llamar a graphics
     * @param jugador1 es el jugador 1
     * @param jugador2 es el jugador 2
     */

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

        // Obtener la cantidad de enemigos derrotados por cada jugador
        int kills1 = jugador1.getEnemigosDerrotados();
        int kills2 = jugador2.getEnemigosDerrotados();

        // Comparar y determinar el resultado en base a los enemigos derrotados
        if (kills1 > kills2) {
            resultado = "Jugador 1 ha ganado (más enemigos derrotados)";
        } else if (kills2 > kills1) {
            resultado = "Jugador 2 ha ganado (más enemigos derrotados)";
        } else {
            resultado = "Empate";
        }

        g.drawString(resultado, (width - fm.stringWidth(resultado)) / 2, 350);

        // Estadísticas
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        fm = g.getFontMetrics();
        String puntaje1 = "Jugador 1 eliminó: " + jugador1.getEnemigosDerrotados();
        String puntaje2 = "Jugador 2 eliminó: " + jugador2.getEnemigosDerrotados();
        String mensajeReinicio = "Presiona Enter para reiniciar";

        g.drawString(puntaje1, (width - fm.stringWidth(puntaje1)) / 2, 390);
        g.drawString(puntaje2, (width - fm.stringWidth(puntaje2)) / 2, 420);
        g.drawString(mensajeReinicio, (width - fm.stringWidth(mensajeReinicio)) / 2, 460);
    }
}

