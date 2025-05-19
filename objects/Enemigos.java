package objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemigos extends ObjetoJuego {
    private float speed = 1.5f;
    private Jugador jugador1, jugador2;
    private String tipo; // "foxy" o "jelly"

    private int cooldownDelAtaque = 100;
    private int temporizadorDeAtaque = 0;

    private BufferedImage[] walk;
    private BufferedImage[] walkFlipped;
    private BufferedImage[] attack;
    private BufferedImage[] attackFlipped;
    private BufferedImage[] dead;
    private BufferedImage[] deadFlipped;
    private boolean orientacionDerecha = true;
    private int walkIndex = 0;
    private int attackIndex = 0;
    private long lastTime = System.currentTimeMillis();
    private long delay = 90;

    private int vidaEnemigo = 7;

    private boolean isAttacking1 = false, isAttacking2 = false, isBlocking1 = false, isBlocking2 = false, recibiendoDaño = false;
    private int temporizadorDeDaño = 0;
    private int cooldownDeDaño = 20;

    // Variables para animación de muerte
    private int currentDeadFrame = 0;
    private long deadFrameStartTime;
    private final long deadFrameDuration = 300; // 300ms por frame
    private boolean deathAnimationComplete = false;
    private boolean deathAnimationStarted = false;

    // Estados del enemigo
    private enum Estado {
        CAMINANDO, ATACANDO, MUERTO
    }
    private Estado estadoActual = Estado.CAMINANDO;

    public Enemigos(Vector2D posicion, BufferedImage[] texture, String tipo) {
        super(posicion, texture);
        this.tipo = tipo;
    }

    public void setWalkSprites(BufferedImage[] walk, BufferedImage[] walkFlipped) {
        this.walk = walk;
        this.walkFlipped = walkFlipped;
    }

    public void setAttackSprites(BufferedImage[] attack, BufferedImage[] attackFlipped) {
        this.attack = attack;
        this.attackFlipped = attackFlipped;
    }

    public void setDeadSprites(BufferedImage[] dead, BufferedImage[] deadFlipped) {
        this.dead = dead;
        this.deadFlipped = deadFlipped;
    }

    public void setJugador1(Jugador jugador1) {
        this.jugador1 = jugador1;
    }

    public void setJugador2(Jugador jugador2) {
        this.jugador2 = jugador2;
    }

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

    @Override
    public void update() {
        if (jugador1 == null && jugador2 == null) return;

        if (vidaEnemigo <= 0) {
            estadoActual = Estado.MUERTO;
            updateDeathAnimation();
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
                estadoActual = Estado.ATACANDO;
                if (isBlocking1) {
                    // Retroceso enemigo - dirección opuesta a jugador 1
                    double retroX = x - (dx1 / dist1) * 60;
                    double retroY = y - (dy1 / dist1) * 60;
                    posicion.setX(retroX);
                    posicion.setY(retroY);
                } else {
                    jugador1.recibirDaño(r.nextInt(3) + 1);
                    temporizadorDeAtaque = cooldownDelAtaque;
                }
            } else if (dist2 < 30 && jugador2.getSalud() > 0) {
                estadoActual = Estado.ATACANDO;
                if (isBlocking2) {
                    // Retroceso enemigo - dirección opuesta a jugador 2
                    double retroX = x - (dx2 / dist2) * 60;
                    double retroY = y - (dy2 / dist2) * 60;
                    posicion.setX(retroX);
                    posicion.setY(retroY);
                } else {
                    jugador2.recibirDaño(r.nextInt(3) + 1);
                    temporizadorDeAtaque = cooldownDelAtaque;
                }
            } else {
                estadoActual = Estado.CAMINANDO;
                posicion.setX(x + nx * speed);
                posicion.setY(y + ny * speed);
            }
        } else if (temporizadorDeAtaque > 0) {
            temporizadorDeAtaque--;
            estadoActual = Estado.ATACANDO;
        } else {
            estadoActual = Estado.CAMINANDO;
            posicion.setX(x + nx * speed);
            posicion.setY(y + ny * speed);
        }

        // Actualizar animaciones
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime > delay) {
            if (estadoActual == Estado.CAMINANDO) {
                walkIndex = (walkIndex + 1) % (walk != null ? walk.length : 1);
            } else if (estadoActual == Estado.ATACANDO && attack != null) {
                attackIndex = (attackIndex + 1) % attack.length;
            }
            lastTime = currentTime;
        }

        // Recibir daño de jugador 1
        if (isAttacking1 && dist1 < 30 && jugador1.getSalud() > 0) {
            vidaEnemigo = vidaEnemigo - (r.nextInt(5) + 1);
            temporizadorDeDaño = cooldownDeDaño;

            if (vidaEnemigo <= 0) {
                jugador1.incrementarEnemigosDerrotados();
                estadoActual = Estado.MUERTO;
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

            if (vidaEnemigo <= 0) {
                jugador2.incrementarEnemigosDerrotados();
                estadoActual = Estado.MUERTO;
            }

            // Retroceso enemigo
            double retroX = x - (dx2 / dist2) * 60;
            double retroY = y - (dy2 / dist2) * 60;
            posicion.setX(retroX);
            posicion.setY(retroY);
        }
    }

    private void updateDeathAnimation() {
        if (!deathAnimationStarted) {
            deathAnimationStarted = true;
            deadFrameStartTime = System.currentTimeMillis();
            currentDeadFrame = 0;
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - deadFrameStartTime >= deadFrameDuration) {
            currentDeadFrame++;
            deadFrameStartTime = currentTime;

            int maxFrames = "jelly".equals(tipo) ? 3 : 2;
            if (currentDeadFrame >= maxFrames) {
                deathAnimationComplete = true;
                currentDeadFrame = maxFrames - 1;
            }
        }
    }

    private BufferedImage getCurrentDeathFrame() {
        if (dead == null || dead.length == 0) return null;

        int frameToShow = Math.min(currentDeadFrame, dead.length - 1);
        return orientacionDerecha ? dead[frameToShow] : deadFlipped[frameToShow];
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage frame = null;

        switch (estadoActual) {
            case ATACANDO:
                if (attack != null && attack.length > 0) {
                    frame = orientacionDerecha ? attack[attackIndex] : attackFlipped[attackIndex];
                } else {
                    frame = orientacionDerecha ? walk[walkIndex] : walkFlipped[walkIndex];
                }
                break;

            case MUERTO:
                frame = getCurrentDeathFrame();
                break;

            case CAMINANDO:
            default:
                if (walk != null && walk.length > 0) {
                    frame = orientacionDerecha ? walk[walkIndex] : walkFlipped[walkIndex];
                } else {
                    frame = texture;
                }
                break;
        }

        if (frame != null) {
            g.drawImage(frame, (int) posicion.getX(), (int) posicion.getY(), null);
        }
    }

    public boolean isDeathAnimationComplete() {
        return deathAnimationComplete;
    }
}