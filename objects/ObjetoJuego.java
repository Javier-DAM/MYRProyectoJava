package objects;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class ObjetoJuego {
    protected BufferedImage texture;
    protected Vector2D posicion;

    public ObjetoJuego(Vector2D posicion, BufferedImage[] texture) {
        this.posicion = posicion;
        this.texture = texture[0]; // Usamos el primer sprite como textura inicial
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
