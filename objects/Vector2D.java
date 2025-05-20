package objects;

public class Vector2D {
    private double x, y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        this(0, 0);
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


    /**
     * Suma este vector con otro vector dado.
     *
     * @param other El vector que se sumará a este.
     * @return Un nuevo Vector2D que representa la suma de ambos vectores.
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /**
     * Resta otro vector de este vector.
     *
     * @param other El vector que se restará de este.
     * @return Un nuevo Vector2D que representa la diferencia entre los vectores.
     */
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /**
     * Escala este vector por un factor escalar.
     *
     * @param factor El valor por el cual se multiplicará cada componente del vector.
     * @return Un nuevo Vector2D escalado.
     */
    public Vector2D scale(double factor) {
        return new Vector2D(this.x * factor, this.y * factor);
    }

    /**
     * Calcula la magnitud (longitud) del vector.
     *
     * @return La magnitud del vector como un valor double.
     */
    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Normaliza el vector, convirtiéndolo en un vector unitario (magnitud igual a 1).
     *
     * @return Un nuevo Vector2D normalizado. Si la magnitud es 0, retorna un vector (0, 0).
     */
    public Vector2D normalize() {
        double mag = getMagnitude();
        return mag == 0 ? new Vector2D(0, 0) : new Vector2D(x / mag, y / mag);
    }

}
