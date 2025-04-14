import Assets.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Window extends JFrame implements Runnable {

    public static final int W = 800, H = 600;
    private Canvas canvas;
    private Thread thread;
    private boolean running = false;

    private BufferStrategy bs;
    private Graphics g;

    private final int fps = 60;
    private double targetTime = 1000000000/fps;
    private double delta = 0;
    private int averagefps = fps;

    //Cargar Sprites
    private int index = 0;
    private long lastTime = System.currentTimeMillis();
    private long delay = 100; // Cambiar frame cada 100ms


    public Window() {

        setTitle("RSG");
        setSize(W, H);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        canvas = new Canvas();

        canvas.setPreferredSize(new Dimension(W, H));
        canvas.setMaximumSize(new Dimension(W, H));
        canvas.setMinimumSize(new Dimension(W, H));
        canvas.setFocusable(true);

        add(canvas);

    }

    public static void main(String[] args) {

        new Window().start();

    }

    private void update() {
        if(System.currentTimeMillis() - lastTime > delay) {
            index++;
            if(index >= Assets.monaIdle.length) { //Carga de 0 el Sprite de monaIdle
                index = 0;
            }

            if(index >= Assets.monaWalk.length) { //Carga de 0 el Sprite de monaWalk
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
        //--------------------Dibujo-----------------------

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, W, H);
        g.drawImage(Assets.monaIdle[index], 100, 100, null); // Dibuja frame actual del Sprite
        g.drawImage(Assets.monaWalk[index], 200, 200, null);
        g.drawString("FPS: " + averagefps, 10, 10);

        //-------------------------------------------------

        g.dispose();
        bs.show();

    }


    private void init(){
        Assets.init();
    }

    @Override
    public void run() {

        long now = 0;
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
//                System.out.println("FPS: " + frames);
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
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
