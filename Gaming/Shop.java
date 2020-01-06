package Gaming;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.image.BufferedImage;


public class Shop extends MouseAdapter
{
   
    Handler handler;
    HUD hud;
    
    private int B1 = 1000;
    private int B2 = 1000;
    private int B3 = 2000;
    
    private boolean funds = true;
    
    private BufferedImage shop;
    private BufferedImage uphp;
    private BufferedImage upsp;
    private BufferedImage rfhp;
    private BufferedImage uppt;
    
    public Shop(Handler handler, HUD hud){
        this.handler = handler;
        this.hud = hud;
        
        BufferedImageLoader loader = new BufferedImageLoader();
         try {
        shop = loader.loadImage2("/res/shop.png"); 
        uphp = loader.loadImage2("/res/uphp.png");
        upsp = loader.loadImage2("/res/upsp.png");
        rfhp = loader.loadImage2("/res/rfhp.png");
        uppt = loader.loadImage2("/res/2kpt.png");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void render(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("arial", 0, 48));
        g.drawImage(shop, (Game.WIDTH/2-300), 5, null);
        //g.drawString("SHOP", Game.WIDTH/2-75, 50);
        
        //Box 1
        g.setFont(new Font("arial", 0, 12));
        //g.drawString("Upgrade Health", 110, 120);
        g.drawString("Cost: " + B1, 50, 175); 
        g.drawImage(uphp, 40, 90, null);
        //g.drawRect(80, 100, 100, 80);
        
        //Box 2
        //g.drawString("Upgrade Speed", 260, 120);
        g.drawImage(upsp, 190, 90, null);
        g.drawString("Cost: " + B2, 205, 175); 
        //g.drawRect(190, 100, 100, 80);
        
        //Box 3
        //g.drawString("Refill Health", 410, 120);
        g.drawImage(rfhp, 350, 90, null);
        g.drawString("Cost: " + B3, 370, 175); 
        //g.drawRect(350, 100, 100, 80);
        
        //Give Score
        //g.drawString("+2000 Score", 560, 120);
        g.drawImage(uppt, 510, 90, null);
        g.drawString("FREE", 535, 175); 
        //g.drawRect(510, 100, 100, 80);
        
        g.drawString("POINTS: " + hud.getScore(), Game.WIDTH/2 - 45, 300);
        g.drawString("Press SPACE to go back! ", Game.WIDTH/2 - 78, 330);
        
    }
    
    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        
        if(Game.gameState == STATE.Shop) {
        //Box 1
        if(mx >= 40 && mx <= 140){
            if(my >= 80 && my <= 180){
                //Selected Box 1
                if(hud.getScore() >= B1 && HUD.HEALTH < 100) {
                    hud.setScore(hud.getScore() - B1);
                    B1 += 1000;
                    HUD.HEALTH += 20;
                }
            }
        }
        //Box2
        if(mx >= 190 && mx <= 290){
            if(my >= 80 && my <= 180){
                //Selected Box 1
                if(hud.getScore() >= B2) {
                    hud.setScore(hud.getScore() - B2);
                    B2 += 1000;
                    handler.spd++;
                }
            }
        }
        //Box 3
        if(mx >= 360 && mx <= 460){
            if(my >= 100 && my <= 180){
                //Selected Box 1
                if(hud.getScore() >= B3) {
                    hud.setScore(hud.getScore() - B3);
                    HUD.HEALTH = 100;
                    B3 += 1000;
                }
            }
        }
        //Box 4
        if(mx >= 510 && mx <= 610){
            if(my >= 100 && my <= 180){
                //Free Score
                hud.setScore(hud.getScore() + 2000);
            }
        }
    }
}
}
