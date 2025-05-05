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

    private GameState gameState;
    private Jugador jugador, jugador2;

    private boolean j1orientacion = true, j2orientacion = false;
    private boolean isAttacking1 = false, isBlocking1 = false;
    private boolean isAttacking2 = false, isBlocking2 = false;
    private int attackIndex1 = 0, attackIndex2 = 0;
    private long attackLastTime1 = 0, attackLastTime2 = 0;
    private int walkIndex1 = 0, idleIndex1 = 0;
    private int walkIndex2 = 0, idleIndex2 = 0;
    private long lastTime = System.currentTimeMillis();
    private long delay = 100;
    private int attackDelay = 100;

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

    private void update() {
        teclado.update();
        jugador.updateJugador1();
        jugador2.updateJugador2();
        gameState.update();

        long currentTime = System.currentTimeMillis();

        // MONA (Jugador 1)
        if (Teclado.atacar) {
            if (!isAttacking1) {
                isAttacking1 = true;
                attackIndex1 = 0;
                attackLastTime1 = currentTime;
            }
        } else if (Teclado.bloquear) {
            isBlocking1 = true;
            isAttacking1 = false;
        } else {
            isBlocking1 = false;
            if (!Teclado.atacar) {
                isAttacking1 = false;
                attackIndex1 = 0;
            }
        }

        if (isAttacking1 && currentTime - attackLastTime1 > attackDelay) {
            attackIndex1++;
            if (attackIndex1 >= Assets.monaAttack.length) {
                isAttacking1 = false;
                attackIndex1 = 0;
            }
            attackLastTime1 = currentTime;
        }

        if (Teclado.derecha) j1orientacion = true;
        else if (Teclado.izquierda) j1orientacion = false;

        // RONA (Jugador 2)
        if (Teclado.atacar2) {
            if (!isAttacking2) {
                isAttacking2 = true;
                attackIndex2 = 0;
                attackLastTime2 = currentTime;
            }
        } else if (Teclado.bloquear2) {
            isBlocking2 = true;
            isAttacking2 = false;
        } else {
            isBlocking2 = false;
            if (!Teclado.atacar2) {
                isAttacking2 = false;
                attackIndex2 = 0;
            }
        }

        if (isAttacking2 && currentTime - attackLastTime2 > attackDelay) {
            attackIndex2++;
            if (attackIndex2 >= Assets.ronaAttack.length) {
                isAttacking2 = false;
                attackIndex2 = 0;
            }
            attackLastTime2 = currentTime;
        }

        if (Teclado.derecha2) j2orientacion = true;
        else if (Teclado.izquierda2) j2orientacion = false;

        // ACTUALIZACIÓN DE ANIMACIONES
        if (currentTime - lastTime > delay) {
            if (!isAttacking1 && !isBlocking1 && (Teclado.arriba || Teclado.abajo || Teclado.izquierda || Teclado.derecha)) {
                walkIndex1 = (walkIndex1 + 1) % Assets.monaWalk.length;
            } else {
                idleIndex1 = (idleIndex1 + 1) % Assets.monaIdle.length;
            }

            if (!isAttacking2 && !isBlocking2 && (Teclado.arriba2 || Teclado.abajo2 || Teclado.izquierda2 || Teclado.derecha2)) {
                walkIndex2 = (walkIndex2 + 1) % Assets.ronaWalk.length;
            } else {
                idleIndex2 = (idleIndex2 + 1) % Assets.ronaIdle.length;
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
        g.setColor(new Color(60, 80, 40));
        g.fillRect(0, 0, W, H);

        int x1 = (int) jugador.getPosicion().getPlayer1X();
        int y1 = (int) jugador.getPosicion().getPlayer1Y();
        int x2 = (int) jugador2.getPosicion().getPlayer2X();
        int y2 = (int) jugador2.getPosicion().getPlayer2Y();

        // MONA
        if (isAttacking1)
            g.drawImage(j1orientacion ? Assets.monaAttack[attackIndex1] : Assets.monaAttackFlipped[attackIndex1], x1, y1, null);
        else if (isBlocking1)
            g.drawImage(j1orientacion ? Assets.monaProtection[1] : Assets.monaProtectionFlipped[1], x1, y1, null);
        else if (Teclado.arriba || Teclado.abajo || Teclado.izquierda || Teclado.derecha)
            g.drawImage(j1orientacion ? Assets.monaWalk[walkIndex1] : Assets.monaWalkFlipped[walkIndex1], x1, y1, null);
        else
            g.drawImage(j1orientacion ? Assets.monaIdle[idleIndex1] : Assets.monaIdleFlipped[idleIndex1], x1, y1, null);

        // RONA
        if (isAttacking2)
            g.drawImage(j2orientacion ? Assets.ronaAttack[attackIndex2] : Assets.ronaAttackFlipped[attackIndex2], x2, y2, null);
        else if (isBlocking2)
            g.drawImage(j2orientacion ? Assets.ronaProtection[1] : Assets.ronaProtectionFlipped[1], x2, y2, null);
        else if (Teclado.arriba2 || Teclado.abajo2 || Teclado.izquierda2 || Teclado.derecha2)
            g.drawImage(j2orientacion ? Assets.ronaWalk[walkIndex2] : Assets.ronaWalkFlipped[walkIndex2], x2, y2, null);
        else
            g.drawImage(j2orientacion ? Assets.ronaIdle[idleIndex2] : Assets.ronaIdleFlipped[idleIndex2], x2, y2, null);

        textos();

        gameState.draw(g);
        g.dispose();
        bs.show();
    }

    private void textos() {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Tahoma", Font.BOLD, 10));
        g.drawString("FPS: " + averagefps, 10, 10);
        g.setFont(new Font("Tahoma", Font.BOLD, 15));
        g.drawString("Jugador 1: Caminar: WASD Atacar: V Bloquear: B", 10, 30);
        g.drawString("Jugador 2: Caminar: ↑↓→← Atacar: , Bloquear: .", 10, 50);
    }

    private void init() {
        Assets.init();
        gameState = new GameState();
        jugador = new Jugador(1, new Vector2D(100, 100), Assets.monaIdle);
        jugador2 = new Jugador(2, new Vector2D(500, 500), Assets.ronaIdle);
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