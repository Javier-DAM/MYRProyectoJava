package Assets;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Assets {

    public static BufferedImage[] monaWalk;
    public static BufferedImage[] monaWalkFlipped;
    public static BufferedImage[] monaIdle;
    public static BufferedImage[] monaIdleFlipped;
    public static BufferedImage[] monaAttack;
    public static BufferedImage[] monaAttackFlipped;
    public static BufferedImage[] monaProtection;
    public static BufferedImage[] monaProtectionFlipped;

    public static void init() {
        BufferedImage idleSheet = Loader.loadImage("Assets/Sprites/Mona/Idle.png");
        BufferedImage walkSheet = Loader.loadImage("Assets/Sprites/Mona/Walk.png");
        BufferedImage attackSheet = Loader.loadImage("Assets/Sprites/Mona/Attack.png");
        BufferedImage protectionSheet = Loader.loadImage("Assets/Sprites/Mona/Protection.png");

        monaIdle = new BufferedImage[7];
        monaWalk = new BufferedImage[12];
        monaAttack = new BufferedImage[9];
        monaProtection = new BufferedImage[2];

        //Dividirá cada Sprite en trozos específicos. Cambia según el Sprite
        for (int i = 0; i < 7; i++) {
            monaIdle[i] = idleSheet.getSubimage(i * 128, 0, 128, 128);
        }

        for (int i = 0; i < 12; i++) {
            monaWalk[i] = walkSheet.getSubimage(i * 128, 0, 128, 128);
        }

        for (int i = 0; i < 9; i++) {
            monaAttack[i] = attackSheet.getSubimage(i * 128, 0, 128, 128);
        }

        for (int i = 0; i < 2; i++) {
            monaProtection[i] = protectionSheet.getSubimage(i * 128, 0, 128, 128);
        }

        // Crear versiones volteadas o espejo
        monaIdleFlipped = voltearSprites(monaIdle);
        monaWalkFlipped = voltearSprites(monaWalk);
        monaAttackFlipped = voltearSprites(monaAttack);
        monaProtectionFlipped = voltearSprites(monaProtection);
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
