import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ObjetoJuego {
    protected BufferedImage texture;
    protected Vector2D posicion;

    public ObjetoJuego(Vector2D texture, BufferedImage[] posicion) {
        texture = texture;


    }
    public abstract void update();
    public abstract void draw(Graphics g);

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public Vector2D getPosicion() {
        return posicion;
    }

    public void setPosicion(Vector2D posicion) {
        this.posicion = posicion;
    }
}
