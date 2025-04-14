package Assets;

import java.awt.image.BufferedImage;

public class Assets {

    public static BufferedImage[] monaWalk, monaIdle; // Arreglo para los 12 frames

    private static final int width128 = 128, height128 = 128;

    public static void init(){

        //Mona Caminando
            BufferedImage monaWalkSheet = Loader.loadImage("Assets/Sprites/Mona/Walk.png"); //Imagen de Mona caminando
            monaWalk = new BufferedImage[12];//Cuenta con 12 imagenes el Spite

            for(int i = 0; i < 12; i++){
                monaWalk[i] = monaWalkSheet.getSubimage(i * width128, 0, width128, height128); //Waljk.png Cuenta con 12 sprites hecho de cuadrados de 128px
            }
        //Mona quieta
            BufferedImage monaWalkIdle = Loader.loadImage("Assets/Sprites/Mona/Idle.png"); // Imagen de Mona quieta
            monaIdle = new BufferedImage[7];//Cuenta con 7 imagenes el Spite

            for (int i = 0; i < 7; i++) {
                monaIdle[i] = monaWalkIdle.getSubimage(i * width128, 0, width128, height128);
            }

    }
}

