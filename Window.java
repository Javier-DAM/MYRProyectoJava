import javax.swing.*;

public class Window extends JFrame {
    public static final int W = 800, H = 600;

    public Window() {
        setTitle("RSG");
        setSize(W, H);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Window();
    }
}
