package root.data;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class BildManager {
    public static BufferedImage loadImage(String imagePath) {

        File file = new File(imagePath);
        BufferedImage image;

        try{
            image = ImageIO.read(file);
        }
        catch(Exception e){
            image = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
        }

        return image;
    }

    public static void saveImage(String outputPath, BufferedImage image) {
        File output = new File(outputPath);
        try{
            ImageIO.write(image, "jpg", output);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}