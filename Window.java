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

        //-------------------------------------------------

        g.dispose();
        bs.show();

    }

    @Override
    public void run() {

        while (running) {
            update();
            draw();
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
