import Assets.Assets;
import input.Teclado;
import objects.GameState;
import objects.Jugador;
import objects.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Window extends JFrame implements Runnable {

    public static final int W = 1920, H = 1080;
    private Canvas canvas;
    private Thread thread;
    private boolean running = false;
    private Teclado teclado;
    private BufferStrategy bs;
    private Graphics g;

    private final int fps = 60;
    private double targetTime = 1000000000 / fps;
    private double delta = 0;
    private int averagefps = fps;

    private int index = 0;
    private long lastTime = System.currentTimeMillis();
    private long delay = 100;

    private GameState gameState;
    private Jugador jugador;

    private boolean caminando, orientacion, atacando, bloqueando;

    //Delay tras pulsar hacer el ataque (J)
    private int attackIndex = 0;
    private long attackLastTime = 0;
    private boolean isAttacking = false;
    private int attackDelay = 100; // milisegundos por frame

    //Al pusar Bloqueo (K)
    private boolean isBlocking = false;


    public Window() {
        setTitle("RSG");
        setSize(W, H);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(W, H));
        canvas.setMaximumSize(new Dimension(W, H));
        canvas.setMinimumSize(new Dimension(W, H));
        canvas.setFocusable(true);

        teclado = new Teclado();
        add(canvas);
        canvas.addKeyListener(teclado);
    }

    public static void main(String[] args) {
        new Window().start();
    }

    private int walkIndex = 0;
    private int idleIndex = 0;

    private void update() {
        teclado.update();
        jugador.update();
        gameState.update();

        long currentTime = System.currentTimeMillis();

        // Prioridad: Atacar > Bloquear > Caminar > Idle
        if (Teclado.atacar) {
            if (!isAttacking) {
                isAttacking = true;
                attackIndex = 0;
                attackLastTime = currentTime;
            }
        } else if (Teclado.bloquear) {
            isBlocking = true;
            isAttacking = false;
        } else {
            isBlocking = false;
            if (!Teclado.atacar) {
                isAttacking = false;
                attackIndex = 0;
            }
        }

        if (isAttacking && currentTime - attackLastTime > attackDelay) {
            attackIndex++;
            if (attackIndex >= Assets.monaAttack.length) {
                isAttacking = false;
                attackIndex = 0;
            }
            attackLastTime = currentTime;
        }

        if (currentTime - lastTime > delay) {
            if (!isAttacking && !isBlocking) {
                if (Teclado.arriba || Teclado.abajo || Teclado.izquierda || Teclado.derecha) {
                    walkIndex++;
                    if (walkIndex >= Assets.monaWalk.length) walkIndex = 0;
                } else {
                    idleIndex++;
                    if (idleIndex >= Assets.monaIdle.length) idleIndex = 0;
                }
            }
            lastTime = currentTime;
        }
    }


    private void draw() {
        bs = canvas.getBufferStrategy();

        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();

        // Fondo
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, W, H);

        // Posición actual del jugador
        int x = (int) jugador.getPosicion().getX(); //Se transforma un double a int
        int y = (int) jugador.getPosicion().getY();

        // Detección de movimiento
        caminando = false;
        atacando = false;
        bloqueando = false;

// Prioridad: Atacar > Bloquear > Caminar > Idle
        if (Teclado.atacar) {
            atacando = true;
        } else if (Teclado.bloquear) {
            bloqueando = true;
        } else if (Teclado.arriba || Teclado.abajo || Teclado.izquierda || Teclado.derecha) {
            caminando = true;
        }

// Actualizar orientación
        if (Teclado.derecha) {
            orientacion = true;
        } else if (Teclado.izquierda) {
            orientacion = false;
        }

// Selección de sprite
        if (isAttacking) {
            if (orientacion) {
                g.drawImage(Assets.monaAttack[attackIndex], x, y, null);
            } else {
                g.drawImage(Assets.monaAttackFlipped[attackIndex], x, y, null);
            }

        } else if (isBlocking) {
            if (orientacion) {
                g.drawImage(Assets.monaProtection[1], x, y, null);
            } else {
                g.drawImage(Assets.monaProtectionFlipped[1], x, y, null);
            }

        } else if (Teclado.arriba || Teclado.abajo || Teclado.izquierda || Teclado.derecha) {
            if (orientacion) {
                g.drawImage(Assets.monaWalk[walkIndex], x, y, null);
            } else {
                g.drawImage(Assets.monaWalkFlipped[walkIndex], x, y, null);
            }

        } else {
            if (orientacion) {
                g.drawImage(Assets.monaIdle[idleIndex], x, y, null);
            } else {
                g.drawImage(Assets.monaIdleFlipped[idleIndex], x, y, null);
            }
        }


        // Mostrar FPS
        g.setColor(Color.BLACK);
        g.drawString("FPS: " + averagefps, 10, 10);

        gameState.draw(g);

        g.dispose();
        bs.show();
    }

    private void init() {
        Assets.init();
        gameState = new GameState();
        jugador = new Jugador(new Vector2D(100, 100), Assets.monaIdle);
    }

    @Override
    public void run() {
        long now;
        long lastTime = System.nanoTime();
        int frames = 0;
        int time = 0;

        init();

        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / targetTime;
            time += (now - lastTime);
            lastTime = now;

            if (delta >= 1) {
                update();
                draw();
                delta--;
                frames++;
            }

            if (time >= 1000000000) {
                averagefps = frames;
                frames = 0;
                time = 0;
            }
        }
        stop();
    }

    private void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
