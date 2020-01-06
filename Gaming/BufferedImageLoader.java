package Gaming;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class BufferedImageLoader
{
   BufferedImage image;
   
   BufferedImage logo;
   
   public BufferedImage loadImage(String path){
       try {
       image = ImageIO.read(getClass().getResource(path));
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    return image;
    }
    
    public BufferedImage loadImage2(String path){
       try {
       logo = ImageIO.read(getClass().getResource(path));
    } catch (IOException e) {
        e.printStackTrace();
    }
        
        return logo;
        
}

}