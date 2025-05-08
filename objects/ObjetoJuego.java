package objects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ObjetoJuego {
    protected Vector2D posicion;
    protected BufferedImage texture;

    public ObjetoJuego(Vector2D posicion, BufferedImage[] textures) {
        this.posicion = posicion;
        this.texture = textures[0];
    }

    public abstract void update();
    public abstract void draw(Graphics g);

    public Vector2D getPosicion() {
        return posicion;
    }

    public void setPosicion(Vector2D posicion) {
        this.posicion = posicion;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }
}
