package root;

import root.data.BildManager;
import root.filter.Filter;
import root.filter.PrewittFilter;
import root.filter.SobelFilter;

import java.awt.image.BufferedImage;


public class Main {

    public static void main(String[] args){
        try{
            BufferedImage image = BildManager.loadImage("src/resources/input/Fokus-auf_Australien.jpg");
            Filter filter = new PrewittFilter();//new SobelFilter(1);

            BufferedImage output = filter.wendeFilterAn(image);

            BildManager.saveImage("src/resources/output/fokus_Output.jpg",output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
