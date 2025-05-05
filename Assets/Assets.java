package Assets;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Assets {

//Assets de Mona
    public static BufferedImage[] monaIdle;
    public static BufferedImage[] monaIdleFlipped;
    public static BufferedImage[] monaWalk;
    public static BufferedImage[] monaWalkFlipped;
    public static BufferedImage[] monaAttack;
    public static BufferedImage[] monaAttackFlipped;
    public static BufferedImage[] monaProtection;
    public static BufferedImage[] monaProtectionFlipped;

//Assets de Rona
    public static BufferedImage[] ronaIdle;
    public static BufferedImage[] ronaIdleFlipped;
    public static BufferedImage[] ronaWalk;
    public static BufferedImage[] ronaWalkFlipped;
    public static BufferedImage[] ronaAttack;
    public static BufferedImage[] ronaAttackFlipped;
    public static BufferedImage[] ronaProtection;
    public static BufferedImage[] ronaProtectionFlipped;

    public static void init() {
        //Mona
        BufferedImage idleSheetMona = Loader.loadImage("Assets/Sprites/Mona/Idle.png");
        BufferedImage walkSheetMona = Loader.loadImage("Assets/Sprites/Mona/Walk.png");
        BufferedImage attackSheetMona = Loader.loadImage("Assets/Sprites/Mona/Attack.png");
        BufferedImage protectionSheetMona = Loader.loadImage("Assets/Sprites/Mona/Protection.png");

        monaIdle = new BufferedImage[7];
        monaWalk = new BufferedImage[12];
        monaAttack = new BufferedImage[9];
        monaProtection = new BufferedImage[2];

        //Rona
        BufferedImage idleSheetRona = Loader.loadImage("Assets/Sprites/Rona/Idle.png");
        BufferedImage walkSheetRona = Loader.loadImage("Assets/Sprites/Rona/Walk.png");
        BufferedImage attackSheetRona = Loader.loadImage("Assets/Sprites/Rona/Attack.png");
        BufferedImage protectionSheetRona = Loader.loadImage("Assets/Sprites/Rona/Protection.png");

        ronaIdle = new BufferedImage[6];
        ronaWalk = new BufferedImage[12];
        ronaAttack = new BufferedImage[6];
        ronaProtection = new BufferedImage[3];



        //Dividirá cada Sprite en trozos específicos. Cambia según el Sprite
            //Mona
        for (int i = 0; i < 7; i++) {
            monaIdle[i] = idleSheetMona.getSubimage(i * 128, 0, 128, 128);
        }

        for (int i = 0; i < 12; i++) {
            monaWalk[i] = walkSheetMona.getSubimage(i * 128, 0, 128, 128);
        }

        for (int i = 0; i < 9; i++) {
            monaAttack[i] = attackSheetMona.getSubimage(i * 128, 0, 128, 128);
        }

        for (int i = 0; i < 2; i++) {
            monaProtection[i] = protectionSheetMona.getSubimage(i * 128, 0, 128, 128);
        }

        // Crear versiones volteadas o espejo
        monaIdleFlipped = voltearSprites(monaIdle);
        monaWalkFlipped = voltearSprites(monaWalk);
        monaAttackFlipped = voltearSprites(monaAttack);
        monaProtectionFlipped = voltearSprites(monaProtection);

            //Rona
        for (int i = 0; i < 6; i++) {
            ronaIdle[i] = idleSheetRona.getSubimage(i * 128, 0, 128, 128);
        }

        for (int i = 0; i < 12; i++) {
            ronaWalk[i] = walkSheetRona.getSubimage(i * 128, 0, 128, 128);
        }

        for (int i = 0; i < 6; i++) {
            ronaAttack[i] = attackSheetRona.getSubimage(i * 128, 0, 128, 128);
        }

        for (int i = 0; i < 3; i++) {
            ronaProtection[i] = protectionSheetRona.getSubimage(i * 128, 0, 128, 128);
        }

        // Crear versiones volteadas o espejo
        ronaIdleFlipped = voltearSprites(ronaIdle);
        ronaWalkFlipped = voltearSprites(ronaWalk);
        ronaAttackFlipped = voltearSprites(ronaAttack);
        ronaProtectionFlipped = voltearSprites(ronaProtection);
    }

    private static BufferedImage[] voltearSprites(BufferedImage[] original) { //Método que voltea los Sprites
        BufferedImage[] flipped = new BufferedImage[original.length];
        for (int i = 0; i < original.length; i++) {
            flipped[i] = voltearImagen(original[i]);
        }
        return flipped;
    }

    public static BufferedImage voltearImagen(BufferedImage imagenOriginal) {
        int ancho = imagenOriginal.getWidth();
        int alto = imagenOriginal.getHeight();

        BufferedImage imagenVolteada = new BufferedImage(ancho, alto, imagenOriginal.getType());
        Graphics2D g = imagenVolteada.createGraphics();

        AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
        at.translate(-ancho, 0);

        g.drawImage(imagenOriginal, at, null);
        g.dispose();

        return imagenVolteada;
    }
}
