package root.filter;

import java.awt.image.BufferedImage;

public class PrewittFilter implements Filter{

    int[][] FilterX = {{-1,0,1},{-1,0,1},{-1,0,1}};
    int[][] FilterY = {{-1,-1,-1},{0,0,0},{1,1,1}};
    public PrewittFilter(){

    }
    @Override
    public BufferedImage wendeFilterAn(BufferedImage original) {

        int width = original.getWidth();
        int height = original.getHeight();

        BufferedImage output = new BufferedImage(width,height, BufferedImage.TYPE_BYTE_GRAY);

        int offset = FilterX.length/2;
        for (int y = offset; y < height-offset ; y++) {
            for (int x = offset; x < width-offset; x++) {
                int gx = applyFilter(original,x,y,FilterX);
                int gy = applyFilter(original,x,y,FilterY);
                int magnitude = (int) Math.min(255, Math.sqrt(gx*gx + gy*gy));
                int color = (magnitude << 16) | (magnitude << 8) | magnitude;
                output.setRGB(x,y,color);
            }
        }

        return output;
    }

    private int applyFilter(BufferedImage image, int x, int y, int[][] filter){
        int sum = 0;
        int offset = filter.length/2;
        for (int ky = -offset; ky <= offset; ky++) {
            for (int kx = -offset; kx <= offset; kx++) {
                int pixel = getGrayScale(image.getRGB(x+kx,y+ky));
                sum += pixel*filter[ky+offset][kx+offset];
            }
        }
        return sum;
    }

    private int getGrayScale(int rgb){
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        return (r + g + b) / 3;
    }
}
