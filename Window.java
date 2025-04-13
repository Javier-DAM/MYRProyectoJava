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

    int x = 0;
    private void update() {

        x++;

    }

    private void draw() {

        bs = canvas.getBufferStrategy();

        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        //--------------------Dibujo-----------------------
        g.clearRect(0, 0, W, H);
        g.drawRect(x, 0, 50, 50);
        g.drawString("FPS: " + averagefps, 10, 10);
        g.setColor(Color.RED);

        //-------------------------------------------------

        g.dispose();
        bs.show();

    }

    @Override
    public void run() {

        long now = 0;
        long lastTime = System.nanoTime();
        int frames = 0;
        int time = 0;

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
                System.out.println("FPS: " + frames);
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
