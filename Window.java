import Assets.Assets;
import input.Teclado;
import objects.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Window extends JFrame implements Runnable {

    private static final int W = 800, H = 600;
    private Canvas canvas;
    private Thread thread;
    private boolean running = false;
    private Graphics g;
    private BufferStrategy bs;
    private Teclado teclado;
    private Jugador jugador1, jugador2;
    private Enemigos enemigo1, enemigo2, enemigo3, enemigo4;
    private List<Enemigos> enemigosList;
    private EstadoDelJuego estadoDelJuego;
    private int averagefps;
    private double delta = 0;
    private double targetTime = 1000000000.0 / 60.0; // 60 FPS

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
        setResizable(false);
        setLocationRelativeTo(null);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(W, H));
        canvas.setMaximumSize(new Dimension(W, H));
        canvas.setMinimumSize(new Dimension(W, H));
        canvas.setFocusable(true);

        teclado = new Teclado();
        canvas.addKeyListener(teclado);
        add(canvas);

        setVisible(true);
    }

    public void iniciarJuego() {
         this.start();
    }

    private void update() {
        teclado.update();
        jugador1.updateJugador1();
        jugador2.updateJugador2();
        estadoDelJuego.update();

        long currentTime = System.currentTimeMillis();

        //Actualizar enemigos
        for (Enemigos enemigo : enemigosList) {
            enemigo.update();
        }

        if (jugador1.getSalud() < 0 && jugador2.getSalud() < 0) {

        }

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

        //Posicones en enteros
        int x1 = (int) jugador1.getPosicion().getX();
        int y1 = (int) jugador1.getPosicion().getY();
        int x2 = (int) jugador2.getPosicion().getX();
        int y2 = (int) jugador2.getPosicion().getY();

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

        //Dibujar Enemigos
        for (Enemigos enemigo: enemigosList){
            enemigo.draw(g);
        }

        //Textos
        textos();
        estadoDelJuego.draw(g);
        g.dispose();
        bs.show();
    }

    private void textos() {
        int vidaP1 = jugador1.getSalud();
        int vidaP2 = jugador2.getSalud();

        //Posicones en enteros
        int x1 = (int) jugador1.getPosicion().getX();
        int y1 = (int) jugador1.getPosicion().getY();
        int x2 = (int) jugador2.getPosicion().getX();
        int y2 = (int) jugador2.getPosicion().getY();

        g.setColor(Color.WHITE);
        g.setFont(new Font("Tahoma", Font.BOLD, 10));
        g.drawString("FPS: " + averagefps, 10, 10);
        g.setFont(new Font("Tahoma", Font.BOLD, 15));
        g.drawString("Jugador 1: Caminar: WASD Atacar: V Bloquear: B", 10, 30);
        g.drawString("Jugador 2: Caminar: ↑↓→← Atacar: , Bloquear: .", 10, 50);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Vida jugador 1: "+ vidaP1+ " - Jugador 2: " + vidaP2, 50, 70);

        if (vidaP1 > 0){
            g.drawString( "Vida"+vidaP1, x1, y1);
        }else {
            g.drawString( "Vida"+0, x1, y1);
        }
        if (vidaP2 > 0){
            g.drawString( "Vida"+vidaP2, x2, y2);
        }else {
            g.drawString( "Vida"+0, x2, y2);
        }
    }

    // Dentro del método init()
    private void init() {
        Assets.init();
        estadoDelJuego = new EstadoDelJuego();
        jugador1 = new Jugador(1, new Vector2D(100, 100), Assets.monaIdle);
        jugador2 = new Jugador(2, new Vector2D(200, 200), Assets.ronaIdle);

        enemigosList = new ArrayList<>();

        enemigo1 = new Enemigos(new Vector2D(300, 200), Assets.foxyIdle);
        enemigo1.setWalkSprites(Assets.foxyWalk, Assets.foxyWalkFlipped);

        enemigo2 = new Enemigos(new Vector2D(600, 300), Assets.jellyIdle1);
        enemigo2.setWalkSprites(Assets.jellyWalk, Assets.jellyWalkFlipped);

        enemigosList.add(enemigo1);
        enemigosList.add(enemigo2);

        enemigo1.setJugador1(jugador1);
        enemigo1.setJugador2(jugador2);
        enemigo2.setJugador1(jugador1);
        enemigo2.setJugador2(jugador2);
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
