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

    private int vidaEnemigo = 10;

    private boolean isAttacking1 = false, isAttacking2 = false, isBlocking1 = false, isBlocking2 = false, recibiendoDaño = false;
    private int temporizadorDeDaño = 0;
    private int cooldownDeDaño = 20; // duración durante la cual el enemigo no puede atacar


    public void setIsAttacking1(boolean attacking) {
        this.isAttacking1 = attacking;
    }

    public void setIsAttacking2(boolean attacking) {
        this.isAttacking2 = attacking;
    }

    public void setIsBlocking1(boolean blocking) {
        this.isBlocking1 = blocking;
    }

    public void setIsBlocking2(boolean blocking) {
        this.isBlocking2 = blocking;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

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

        if (vidaEnemigo <= 0) {
            return;
        }

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

        // Control del estado de daño
        if (temporizadorDeDaño > 0) {
            temporizadorDeDaño--;
            recibiendoDaño = true;
        } else {
            recibiendoDaño = false;
        }

        // Solo atacar si los jugadores NO están atacando
        if (!isAttacking1 && !isAttacking2 && !recibiendoDaño && temporizadorDeAtaque <= 0) {
            if (dist1 < 30 && jugador1.getSalud() > 0) {
                if (isBlocking1) {
                    // Retroceso enemigo - dirección opuesta a jugador 1
                    double retroX = x - (dx1 / dist1) * 60;
                    double retroY = y - (dy1 / dist1) * 60;
                    posicion.setX(retroX);
                    posicion.setY(retroY);
                    System.out.println("Jugador 1 bloqueó el ataque. Enemigo retrocede.");
                } else {
                    jugador1.recibirDaño(daño);
                    temporizadorDeAtaque = cooldownDelAtaque;
                }
            } else if (dist2 < 30 && jugador2.getSalud() > 0) {
                if (isBlocking2) {
                    // Retroceso enemigo - dirección opuesta a jugador 2
                    double retroX = x - (dx2 / dist2) * 60;
                    double retroY = y - (dy2 / dist2) * 60;
                    posicion.setX(retroX);
                    posicion.setY(retroY);
                    System.out.println("Jugador 2 bloqueó el ataque. Enemigo retrocede.");
                } else {
                    jugador2.recibirDaño(daño);
                    temporizadorDeAtaque = cooldownDelAtaque;
                }
            }
        }

        if (temporizadorDeAtaque > 0) {
            temporizadorDeAtaque--;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime > delay) {
            walkIndex = (walkIndex + 1) % (walk != null ? walk.length : 1);
            lastTime = currentTime;
        }

        // Recibir daño de jugador 1
        if (isAttacking1 && dist1 < 30 && jugador1.getSalud() > 0) {
            vidaEnemigo -= 2;
            temporizadorDeDaño = cooldownDeDaño;
            System.out.println("Enemigo recibió daño de Jugador 1. Vida restante: " + vidaEnemigo);

            if (vidaEnemigo <= 0) {
                jugador1.incrementarEnemigosDerrotados();
                System.out.println("Jugador 1 ha derrotado a un enemigo.");
            }

            // Retroceso enemigo
            double retroX = x - (dx1 / dist1) * 60;
            double retroY = y - (dy1 / dist1) * 60;
            posicion.setX(retroX);
            posicion.setY(retroY);
        }


        // Recibir daño de jugador 2
        if (isAttacking2 && dist2 < 30 && jugador2.getSalud() > 0) {
            vidaEnemigo -= 2;
            temporizadorDeDaño = cooldownDeDaño;
            System.out.println("Enemigo recibió daño de Jugador 2. Vida restante: " + vidaEnemigo);

            if (vidaEnemigo <= 0) {
                jugador2.incrementarEnemigosDerrotados();
                System.out.println("Jugador 2 ha derrotado a un enemigo.");
            }

            // Retroceso enemigo
            double retroX = x - (dx2 / dist2) * 60;
            double retroY = y - (dy2 / dist2) * 60;
            posicion.setX(retroX);
            posicion.setY(retroY);
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
