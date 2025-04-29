package objects;
public class Vector2D {
    public double x;
    public double y;
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;

    }
    public Vector2D() {
        x = 0;
        y = 0;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
