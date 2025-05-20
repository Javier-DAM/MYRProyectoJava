package Assets;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Loader {
    /**
     * Metodo para comprobar si existe la imagen
     * @param path Es la ruta donde está guardada la imagen
     * @return devuelve la ruta de la imagen si se ha encontrado y si no la encuentra devuelve un null
     */
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

