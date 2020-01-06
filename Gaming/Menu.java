package Gaming;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Color;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Toolkit;

public class Menu extends MouseAdapter
{
  
    private Game game;
    private Handler handler;
    private HUD hud;
    private Random r = new Random();
    
    private BufferedImage logo;
    private BufferedImage play;
    private BufferedImage help;
    private BufferedImage quit;
    private BufferedImage select;
    private BufferedImage back;
    private BufferedImage normal;
    private BufferedImage hard;
    private BufferedImage restart;
    private BufferedImage gameover;

    
    public Menu(Game game, Handler handler, HUD hud){
        this.game = game;
        this.hud = hud;
        this.handler = handler;
        
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
        logo = loader.loadImage2("/res/logo.png"); 
        play = loader.loadImage("/res/play.png"); 
        help = loader.loadImage("/res/help.png"); 
        quit = loader.loadImage("/res/quit.png"); 
        select = loader.loadImage("/res/select.png");
        back = loader.loadImage("/res/back.png");
        normal = loader.loadImage("/res/normal.png");
        hard = loader.loadImage("/res/hard.png");
        restart = loader.loadImage("/res/restart.png");
        gameover = loader.loadImage("/res/gameover.png");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        
        if(game.gameState == STATE.Menu){
        if(mouseOver(mx, my, 210, 150, 200, 64)){
            game.gameState = STATE.Select;
            return;
        }
        
        if(mouseOver(mx, my, 210, 250, 200, 64)){
            game.gameState = STATE.Help;
        }
        
        if(mouseOver(mx, my, 210, 350, 200, 64)) {
            System.exit(1);
        }
     }
     
        if(game.gameState == STATE.Select){
        if(mouseOver(mx, my, 210, 150, 200, 64)){
            game.gameState = STATE.Game;
            handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
            handler.clearEnemys();
            handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler));
            
            game.diff = 0;
        }
        
        if(mouseOver(mx, my, 210, 250, 200, 64)){
            game.gameState = STATE.Game;
            handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
            handler.clearEnemys();
            handler.addObject(new HardEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler));
            
            game.diff = 1;
        }
        
        if(mouseOver(mx, my, 210, 350, 200, 64)) {
            game.gameState = STATE.Menu;
            return;
        }
     }
     
     
     if(game.gameState == STATE.Help){
            if(mouseOver(mx, my, 50, 290, 200, 64)) {
                game.gameState = STATE.Menu;
            }
        }
     
     if(game.gameState == STATE.End){
            if(mouseOver(mx, my, 210, 350, 200, 64)) {
            game.gameState = STATE.Select;
            hud.setLevel(1);
            hud.setScore(0);
            //handler.addObject(new Player(Game.WIDTH/2-32, Game.HEIGHT/2-32, ID.Player, handler));
            //handler.clearEnemys();
            //handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler));
            }
     }
    }
    
    public void mouseReleased(MouseEvent e){
        
    }
    
    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if(mx > x && mx < x + width){
            if(my > y && my < y + height){
                return true;
            }
            else{
                return false;
            }
        }else{
            return false;
        }
    }
    
    public void tick(){
        
    }
    
    public void render(Graphics g){
        if(game.gameState == STATE.Menu){
        
        Font fnt = new Font("arial", 1, 50);
        Font fnt2 = new Font("arial", 1, 30);
        
        g.setFont(fnt);
        g.setColor(Color.white);
        g.drawImage(logo, 10, 20, null);
        //g.drawString("Caleb's Wave Game", 90, 70);
        
        g.setFont(fnt2);
        //g.drawRect(210, 150, 200, 64);
        g.drawImage(play, ((Game.WIDTH/2)-100), 150, null);
        //g.drawString("Play", 275, 190);
        
        //g.drawRect(210, 250, 200, 64);
        g.drawImage(help, ((Game.WIDTH/2)-100), 260, null);
        //g.drawString("Help", 275, 290);
        
        //g.drawRect(210, 350, 200, 64);
        g.drawImage(quit, ((Game.WIDTH/2)-100), 350, null);
        //g.drawString("Quit", 275, 390);
        }else if(game.gameState == STATE.Help){
        Font fnt = new Font("arial", 1, 50);
        Font fnt2 = new Font("arial", 1, 30);
        Font fnt3 = new Font("arial", 1, 18);
        
        g.setFont(fnt);
        g.setColor(Color.white);
        g.drawImage(help, ((Game.WIDTH/2)-100), 20, null);
        //g.drawString("Help", 240, 70);
        
        g.setFont(fnt2);
        //g.drawRect(50, 290, 200, 64);
        g.drawImage(back, 40, 290, null);
        //g.drawString("Back", 115, 330);
        
        g.setFont(fnt3);
        g.drawString("Use WASD to move your player. Avoid the enemies! ", 110, 390);
        
    }else if(game.gameState == STATE.End){
        Font fnt = new Font("arial", 1, 50);
        Font fnt2 = new Font("arial", 1, 30);
        Font fnt3 = new Font("arial", 1, 18);
        
        g.setFont(fnt);
        g.setColor(Color.white);
        g.drawImage(gameover, ((Game.WIDTH/2)-130), 20, null);
        //g.drawString("Game Over!", 175, 70);
        
        g.setFont(fnt3);
        g.drawString("You lose with a score of " + hud.getScore(), 200, 290);
        
        g.setFont(fnt2);
        //g.drawRect(210, 350, 200, 64);
        g.drawImage(restart, ((Game.WIDTH/2)-100), 350, null);
        //g.drawString("Restart", 250, 390);
        
    }else if(game.gameState == STATE.Select){
        Font fnt = new Font("arial", 1, 50);
        Font fnt2 = new Font("arial", 1, 30);
        
        g.setFont(fnt);
        g.setColor(Color.white);
        g.drawImage(select, ((Game.WIDTH/2)-300), 20, null);
        //g.drawString("SELECT DIFFICULTY", 70, 70);
        
        g.setFont(fnt2);
        //g.drawRect(210, 150, 200, 64);
        g.drawImage(normal, ((Game.WIDTH/2)-100), 150, null);
        //g.drawString("Normal", 270, 190);
        
        //g.drawRect(210, 250, 200, 64);
        g.drawImage(hard, ((Game.WIDTH/2)-100), 250, null);
        //g.drawString("Hard", 275, 290);
        
        //g.drawRect(210, 350, 200, 64);
        g.drawImage(back, ((Game.WIDTH/2)-100), 350, null);
        //g.drawString("Back", 275, 390);
        
    }
    
}

}
