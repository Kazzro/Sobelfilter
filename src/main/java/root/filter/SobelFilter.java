package root.filter;

import java.awt.image.BufferedImage;

public class SobelFilter implements Filter {
    private final int[][] FilterX;
    private final int[][] FilterY;

    public static final int FILTERMODE1 = 1;
    public static final int FILTERMODE2 = 2;
    public static final int FILTERMODE3 = 3;

    public SobelFilter(int filtermode) throws Exception {

        switch(filtermode){
            case FILTERMODE1:
                this.FilterX = new int[][]{{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
                this.FilterY = new int[][]{{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
                break;
            case FILTERMODE2:
                this.FilterX = new int[][]{{-1,-2,0,2,1},{-4,-10,0,10,4},{-7,-17,0,17,7},{-4,-10,0,10,4},{-1,-2,0,2,1}};
                this.FilterY = new int[][]{{1,4,7,4,1},{2,10,17,10,2},{0,0,0,0,0},{-2,-10,-17,-10,-2},{-1,-4,-7,-4,-1}};
                break;
            case FILTERMODE3:
                this.FilterX = new int[][]{{-1,-3,-3,0,3,3,1},{-4,-11,-13,0,13,11,4},{-9,-26,30,0,30,26,9},{-13,-34,-40,0,40,34,13},{-9,-26,30,0,30,26,9},{-4,-11,-13,0,13,11,4},{-1,-3,-3,0,3,3,1}};
                this.FilterY = new int[][]{{1,4,9,13,9,4,1},{3,11,26,34,26,11,3},{3,13,30,40,30,13,3},{0,0,0,0,0,0,0},{-3,-13,-30,-40,-30,-13,-3},{-3,-11,-26,-34,-26,-11,-3},{-1,-4,-9,-13,-9,-4,-1}};
                break;
            default:
                throw new Exception("zahlen zwischen 1-3");
        }

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
