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

    private boolean caminando = true, horientacion = true;

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
        jugador.update();
        gameState.update();

        if (System.currentTimeMillis() - lastTime > delay) {
            index++;

            if (index >= Assets.monaIdle.length) {
                index = 0;
            }

            if (index >= Assets.monaWalk.length) {
                index = 0;
            }

            lastTime = System.currentTimeMillis();
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

        if (Teclado.arriba || Teclado.abajo || Teclado.izquierda || Teclado.derecha) { //Comprueba si está caminando
            caminando = true;
        }

        if (Teclado.derecha) {
            horientacion = true;
        }
        if (Teclado.izquierda) {
            horientacion = false;
        }

        // Selección de sprite
        if (caminando) {
            g.drawImage(Assets.monaWalk[index], x, y, null);
        } else {
            g.drawImage(Assets.monaIdle[index], x, y, null);
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
