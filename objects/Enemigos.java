package objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemigos extends ObjetoJuego {
    private float speed = 1.5f;
    private Jugador jugador1, jugador2;

    private int cooldownDelAtaque = 60;
    private int temporizadorDeAtaque = 0;

    private BufferedImage[] walk;
    private BufferedImage[] walkFlipped;
    private boolean orientacionDerecha = true;
    private int walkIndex = 0;
    private long lastTime = System.currentTimeMillis();
    private long delay = 100;

    public Enemigos(Vector2D posicion, BufferedImage[] texture) {
        super(posicion, texture);
    }

    public void setWalkSprites(BufferedImage[] walk, BufferedImage[] walkFlipped) {
        this.walk = walk;
        this.walkFlipped = walkFlipped;
    }

    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }

    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }

    @Override
    public void update() {
        if (jugador1 == null && jugador2 == null) return;

        Random r = new Random();

        double j1X = jugador1.getPosicion().getX();
        double j1Y = jugador1.getPosicion().getY();
        double j2X = jugador2.getPosicion().getX();
        double j2Y = jugador2.getPosicion().getY();

        double x = posicion.getX();
        double y = posicion.getY();

        double dx1 = j1X - x;
        double dy1 = j1Y - y;
        double dx2 = j2X - x;
        double dy2 = j2Y - y;

        double dist1 = Math.hypot(dx1, dy1);
        double dist2 = Math.hypot(dx2, dy2);

        double nx = 0, ny = 0;

        if (jugador1.getSalud() > 0 && jugador2.getSalud() > 0) {
            if (dist1 < dist2) {
                nx = dx1 / dist1;
                ny = dy1 / dist1;
                orientacionDerecha = dx1 > 0;
            } else {
                nx = dx2 / dist2;
                ny = dy2 / dist2;
                orientacionDerecha = dx2 > 0;
            }
        } else if (jugador1.getSalud() > 0) {
            nx = dx1 / dist1;
            ny = dy1 / dist1;
            orientacionDerecha = dx1 > 0;
        } else if (jugador2.getSalud() > 0) {
            nx = dx2 / dist2;
            ny = dy2 / dist2;
            orientacionDerecha = dx2 > 0;
        }

        posicion.setX(x + nx * speed);
        posicion.setY(y + ny * speed);

        int daño = r.nextInt(3) + 1;
        if (dist1 < 30 && temporizadorDeAtaque <= 0 && jugador1.getSalud() > 0) {
            jugador1.recibirDaño(daño);
            temporizadorDeAtaque = cooldownDelAtaque;
        } else if (dist2 < 30 && temporizadorDeAtaque <= 0 && jugador2.getSalud() > 0) {
            jugador2.recibirDaño(daño);
            temporizadorDeAtaque = cooldownDelAtaque;
        }

        if (temporizadorDeAtaque > 0) {
            temporizadorDeAtaque--;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime > delay) {
            walkIndex = (walkIndex + 1) % (walk != null ? walk.length : 1);
            lastTime = currentTime;
        }
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage frame = (walk != null)
                ? (orientacionDerecha ? walk[walkIndex] : walkFlipped[walkIndex])
                : texture;
        g.drawImage(frame, (int) posicion.getX(), (int) posicion.getY(), null);
    }
}
