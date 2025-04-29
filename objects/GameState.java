package objects;

import Assets.Assets;
import input.Teclado;

import javax.swing.*;
import java.awt.*;

public class GameState {
    private Jugador jugador;
    public GameState() {
        jugador = new Jugador(new Vector2D(100,500),Assets.);


    }
    public void update(){
        jugador.update();
    }
    public void draw(Graphics g){
        jugador.draw(g);
    }
}
