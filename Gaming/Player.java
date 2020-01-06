package Gaming;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
    
    Random r = new Random();
    Handler handler;
    
    private BufferedImage player_image;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        
        SpriteSheet ss = new SpriteSheet(Game.sprite_sheet);
        
        player_image = ss.grabImage(1, 1, 32, 32);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
    
    public void tick() {
        x += velX;
        y += velY;
        
        x = Game.clamp(x, 0, Game.WIDTH - 38);
        y = Game.clamp(y, 0, Game.HEIGHT - 64);
        
        collision();
    }
    
    private void collision(){
        for(int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            
            if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SmartEnemy){
                if(getBounds().intersects(tempObject.getBounds())){
                    HUD.HEALTH -= 2;
            }
        }
    }
    }
    
    public void render(Graphics g) {
        //g.setColor(Color.white);
        //g.fillRect(x, y, 32, 32);
        g.drawImage(player_image, (int)x, (int)y, null);
        
    }
}
