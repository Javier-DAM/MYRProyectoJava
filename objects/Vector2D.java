package objects;
public class Vector2D {

    public double player1X, player1Y; //Jugador 1
    public double player2X, player2Y; //Jugador 2

    public double enemiesX, enemiesY; //Enemigos


    public Vector2D(double x, double y) {
        this.player1X = x;
        this.player1Y = y;

        this.player2X = x;
        this.player2Y = y;

    }
    public Vector2D() {
        player1X = 0;
        player1Y = 0;
        player2X = 0;
        player2Y = 0;
    }

    //Getters and Setter de PLayer 1
    public double getPlayer1X() {
        return player1X;
    }

    public void setPlayer1X(double player1X) {
        this.player1X = player1X;
    }

    public double getPlayer1Y() {
        return player1Y;
    }

    public void setPlayer1Y(double player1Y) {
        this.player1Y = player1Y;
    }

    //Getters and Setters Player 2
    public double getPlayer2X() {
        return player2X;
    }

    public void setPlayer2X(double player2X) {
        this.player2X = player2X;
    }

    public double getPlayer2Y() {
        return player2Y;
    }

    public void setPlayer2Y(double player2Y) {
        this.player2Y = player2Y;
    }

    //Getters and Setter Enemies
}
