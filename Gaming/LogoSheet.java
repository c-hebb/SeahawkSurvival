package Gaming;

import java.awt.image.BufferedImage;


public class LogoSheet
{
   private BufferedImage logo;
   
   public LogoSheet(BufferedImage ll){
       this.logo = ll;
   }
   
   public BufferedImage grabImage2(int col, int row, int width, int height){
       BufferedImage img2 = logo.getSubimage(row * 600, col * 100, width, height);
       return img2;
    }
}
