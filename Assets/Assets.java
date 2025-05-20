package Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

public class Assets {
    //Mona
    public static BufferedImage[] monaIdle, monaIdleFlipped;
    public static BufferedImage[] monaWalk, monaWalkFlipped;
    public static BufferedImage[] monaAttack, monaAttackFlipped;
    public static BufferedImage[] monaProtection, monaProtectionFlipped;
    public static BufferedImage[] monaDialogue, monaDialogueFlipped;

    //Rona
    public static BufferedImage[] ronaIdle, ronaIdleFlipped;
    public static BufferedImage[] ronaWalk, ronaWalkFlipped;
    public static BufferedImage[] ronaAttack, ronaAttackFlipped;
    public static BufferedImage[] ronaProtection, ronaProtectionFlipped;
    public static BufferedImage[] ronaDialogue, ronaDialogueFlipped;

    //Hombre lobo
    public static BufferedImage[] foxyIdle, foxyIdleFlipped;
    public static BufferedImage[] foxyWalk, foxyWalkFlipped;
    public static BufferedImage[] foxyRun, foxyRunFlipped;
    public static BufferedImage[] foxyAttack, foxyAttackFlipped;
    public static BufferedImage[] foxyDead, foxyDeadFlipped;

    //Medusa
    public static BufferedImage[] jellyIdle1, jellyIdle1Flipped, jellyIdle2, jellyIdle2Flipped;
    public static BufferedImage[] jellyWalk, jellyWalkFlipped;
    public static BufferedImage[] jellyRun, jellyRunFlipped;
    public static BufferedImage[] jellyAttack, jellyAttackFlipped;
    public static BufferedImage[] jellyDead, jellyDeadFlipped;
    public static Image fondoImagen;

    /**
     * Inicializa todos los Assets e indica el tamaño total de los assets
     */
    public static void init() {
        fondoImagen = new ImageIcon(Assets.class.getResource("/Assets/Sprites/Fondo/césped.png")).getImage();
        //Mona Assets
        BufferedImage idleSheetMona = Loader.loadImage("Assets/Sprites/Mona/Idle.png");
        BufferedImage walkSheetMona = Loader.loadImage("Assets/Sprites/Mona/Walk.png");
        BufferedImage attackSheetMona = Loader.loadImage("Assets/Sprites/Mona/Attack.png");
        BufferedImage protectionSheetMona = Loader.loadImage("Assets/Sprites/Mona/Protection.png");
        BufferedImage dialogueSheetMona = Loader.loadImage("Assets/Sprites/Mona/Dialogue.png");

        monaIdle = sliceSheet(idleSheetMona, 7);
        monaWalk = sliceSheet(walkSheetMona, 12);
        monaAttack = sliceSheet(attackSheetMona, 9);
        monaProtection = sliceSheet(protectionSheetMona, 2);
        monaDialogue = sliceSheet(dialogueSheetMona, 6);

        monaIdleFlipped = flipSprites(monaIdle);
        monaWalkFlipped = flipSprites(monaWalk);
        monaAttackFlipped = flipSprites(monaAttack);
        monaProtectionFlipped = flipSprites(monaProtection);
        monaDialogueFlipped = flipSprites(monaDialogue);

        //Rona Assets
        BufferedImage idleSheetRona = Loader.loadImage("Assets/Sprites/Rona/Idle.png");
        BufferedImage walkSheetRona = Loader.loadImage("Assets/Sprites/Rona/Walk.png");
        BufferedImage attackSheetRona = Loader.loadImage("Assets/Sprites/Rona/Attack.png");
        BufferedImage protectionSheetRona = Loader.loadImage("Assets/Sprites/Rona/Protection.png");
        BufferedImage dialogueSheetRona = Loader.loadImage("Assets/Sprites/Rona/Dialogue.png");

        ronaIdle = sliceSheet(idleSheetRona, 6);
        ronaWalk = sliceSheet(walkSheetRona, 12);
        ronaAttack = sliceSheet(attackSheetRona, 6);
        ronaProtection = sliceSheet(protectionSheetRona, 3);
        ronaDialogue = sliceSheet(dialogueSheetRona, 5);

        ronaIdleFlipped = flipSprites(ronaIdle);
        ronaWalkFlipped = flipSprites(ronaWalk);
        ronaAttackFlipped = flipSprites(ronaAttack);
        ronaProtectionFlipped = flipSprites(ronaProtection);
        ronaDialogueFlipped = flipSprites(ronaDialogue);

        //Foxy Assets
        BufferedImage idleSheetFoxy = Loader.loadImage("Assets/Sprites/Foxy/Idle.png");
        BufferedImage walkSheetFoxy = Loader.loadImage("Assets/Sprites/Foxy/Walk.png");
        BufferedImage runSheetFoxy = Loader.loadImage("Assets/Sprites/Foxy/Run.png");
        BufferedImage attackSheetFoxy = Loader.loadImage("Assets/Sprites/Foxy/Attack.png");
        BufferedImage deadSheetFocy = Loader.loadImage("Assets/Sprites/Foxy/Dead.png");

        foxyIdle = sliceSheet(idleSheetFoxy, 8);
        foxyWalk = sliceSheet(walkSheetFoxy, 11);
        foxyRun = sliceSheet(runSheetFoxy, 9);
        foxyAttack = sliceSheet(attackSheetFoxy, 6);
        foxyDead = sliceSheet(deadSheetFocy, 2);

        foxyIdleFlipped = flipSprites(foxyIdle);
        foxyWalkFlipped = flipSprites(foxyWalk);
        foxyRunFlipped = flipSprites(foxyRun);
        foxyAttackFlipped = flipSprites(foxyAttack);
        foxyDeadFlipped = flipSprites(foxyDead);

        //Jelly Assets
        BufferedImage idle1SheetJelly = Loader.loadImage("Assets/Sprites/Jelly/Idle_1.png");
        BufferedImage idle2SheetJelly = Loader.loadImage("Assets/Sprites/Jelly/Idle_2.png");
        BufferedImage walkSheetJelly = Loader.loadImage("Assets/Sprites/Jelly/Walk.png");
        BufferedImage runSheetJelly = Loader.loadImage("Assets/Sprites/Jelly/Run.png");
        BufferedImage attackSheetJelly = Loader.loadImage("Assets/Sprites/Jelly/Attack.png");
        BufferedImage deadSheetJelly = Loader.loadImage("Assets/Sprites/Jelly/Dead.png");

        jellyIdle1 = sliceSheet(idle1SheetJelly, 5);
        jellyIdle2 = sliceSheet(idle2SheetJelly, 7);
        jellyWalk = sliceSheet(walkSheetJelly, 13);
        jellyRun = sliceSheet(runSheetJelly, 9);
        jellyAttack = sliceSheet(attackSheetJelly, 7);
        jellyDead = sliceSheet(deadSheetJelly, 3);

        jellyIdle1Flipped = flipSprites(jellyIdle1);
        jellyIdle2Flipped = flipSprites(jellyIdle2);
        jellyWalkFlipped = flipSprites(jellyWalk);
        jellyRunFlipped = flipSprites(jellyRun);
        jellyAttackFlipped = flipSprites(jellyAttack);
        jellyDeadFlipped = flipSprites(jellyDead);
    }

    /**
     * Metodo que se encarga de dividir los assets recibiendo su tamaño
     * @param sheet indentificador del asset
     * @param frames es el tamaño a diviir del asset
     * @return y devuelve el array de las imagenes ya cortadas
     */
    private static BufferedImage[] sliceSheet(BufferedImage sheet, int frames) {
        BufferedImage[] result = new BufferedImage[frames];
        int frameWidth = sheet.getWidth() / frames;
        int frameHeight = sheet.getHeight();

        for (int i = 0; i < frames; i++) {
            int x = i * frameWidth;

            if (x + frameWidth <= sheet.getWidth()) {
                result[i] = sheet.getSubimage(x, 0, frameWidth, frameHeight);
            } else {
                System.err.println("sliceSheet: fuera de rango en frame " + i + " x=" + x + " ancho=" + frameWidth);
                result[i] = null;
            }
        }
        return result;
    }

    /**
     * Este método se encarga de llamar al metodo de voltar la imagen y cuando la voltea, guarda la imagen en un array
     * @param originals sprite con la orientación original
     * @return devuelve el array ya girado
     */
    private static BufferedImage[] flipSprites(BufferedImage[] originals) {
        BufferedImage[] flipped = new BufferedImage[originals.length];
        for (int i = 0; i < originals.length; i++) {
            flipped[i] = flipImage(originals[i]);
        }
        return flipped;
    }

    /**
     *se va a encargar de voltar imagen por imagen
     * @param img es la imagen que recibe la imagen que declaramos arriba
     * @return devuelve la imagen voltada
     */
    public static BufferedImage flipImage(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage flipped = new BufferedImage(w, h, img.getType());
        Graphics2D g = flipped.createGraphics();
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-w, 0);
        g.drawImage(img, tx, null);
        g.dispose();
        return flipped;
    }
}