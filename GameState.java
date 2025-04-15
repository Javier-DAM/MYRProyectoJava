import Assets.Assets;

import javax.swing.*;
import java.awt.*;

public class GameState {
    private Jugador jugador;
    public GameState() {
        jugador = new Jugador(new Vector2D(100,500), Assets.monaWalk);

    }
    public void update(){

    }
    public void draw(Graphics g){

    }
}
