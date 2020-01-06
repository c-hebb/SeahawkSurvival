package Gaming;

import java.awt.Graphics;
import java.awt.Color;

public class HUD {
    
    public static int HEALTH = 100;
    
    private int greenValue = 255;
    private int redValue = 75;
    
    private int score = 0;
    private int level = 1;
    
    public void tick() {
        HEALTH = Game.clamp(HEALTH, 0, 100);
        greenValue = Game.clamp(greenValue, 0, 255);
        
        greenValue = HEALTH * 2;
        redValue = 75;
        
        score++;
        
        if(HEALTH <= 50){ 
            redValue = 255;
            greenValue = 75;
        }
    }
    
    public void render(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(15, 15, 200, 32);
        g.setColor(new Color(redValue, greenValue, 0));
        g.fillRect(15, 15, HEALTH * 2, 32);
        g.setColor(Color.white);
        g.drawRect(15, 15, 200, 32);
        
        g.drawString("Score: " + score, 10, 64);
        g.drawString("Level: " + level, 10, 80);
        g.drawString("Press SPACE for Shop", 10, 96);
        g.drawString("Press P to Pause", 10, 112);
    }
    
    public void setScore(int score){
        this.score = score;
        
    }
    
    public int getScore(){
        return score;
    }
    
    public int getLevel(){
        return level;
    }
    
    public void setLevel(int level){
        this.level = level;
    }
}
