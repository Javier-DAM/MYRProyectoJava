import java.awt.*;
import java.awt.image.BufferedImage;

public class Jugador extends ObjetoJuego {
    public Jugador(Vector2D texture, BufferedImage[] posicion) {
        super(texture, posicion);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, (int) posicion.getX(), (int) posicion.getY(), null);
    }
}
