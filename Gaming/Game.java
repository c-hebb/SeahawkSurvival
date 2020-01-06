package Gaming;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    
    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    private boolean running = false;
    
    public static boolean paused = false;
    public int diff = 0;
    
    //0 = normal
    //1 = hard
    
    private Random r;
    private Handler handler;
    private HUD hud;
    private Spawn spawner;
    private Menu menu;
    private Shop shop;
    
    public static STATE gameState = STATE.Menu;
    
    public static BufferedImage sprite_sheet;
    
    private BufferedImage pausedpic;
    
    
    
    public Game(){
      handler = new Handler();
      hud = new HUD();
      shop = new Shop(handler, hud);
      
      menu = new Menu(this, handler, hud);
      this.addKeyListener(new KeyInput(handler, this));
      this.addMouseListener(menu);
      this.addMouseListener(shop);
      
      new Window(WIDTH, HEIGHT, "Test", this);
      
      BufferedImageLoader loader = new BufferedImageLoader();
      
      sprite_sheet = loader.loadImage("/res/parrot.png");
      
      try {
        pausedpic = loader.loadImage2("/res/paused.png"); 
        }
        catch(Exception e) {
            e.printStackTrace();
        }
      
      
      spawner = new Spawn(handler, hud, this);
      r = new Random();
      
      if(gameState == STATE.Game){
          handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player, handler));
          handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH-50), r.nextInt(Game.HEIGHT-50), ID.BasicEnemy, handler));
        }else{
            for(int i = 0; i < 15; i++){
                handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
            }
        }
    
    }
    
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    
    public synchronized void stop() {
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void run() 
        {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running)
        {
           long now = System.nanoTime();
           delta += (now - lastTime) / ns;
           lastTime = now;
               while(delta >=1)
                 {
                  tick();
                  delta--;
                 }
                 if(running) {
                    render();
                    frames++;
                }
                            
                 if(System.currentTimeMillis() - timer > 1000)
                    {
                     timer += 1000;
                     //System.out.println("FPS: "+ frames);
                     frames = 0;
                     }
        }
        stop();
}
 
    private void tick(){
        if(gameState == STATE.Game){
            
            if(!paused){
            handler.tick();
            hud.tick();
            spawner.tick();
            
            if(HUD.HEALTH <= 0){
                HUD.HEALTH = 100;
                gameState = STATE.End;
                handler.clearEnemys();
              
                for(int i = 0; i < 15; i++){
                    handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
                }
        
            }
           }
        }else if(gameState == STATE.Menu){
            handler.tick();
            menu.tick();
        }else if(gameState == STATE.Help){
            handler.tick();
            menu.tick();
        }else if(gameState == STATE.End){
            handler.tick();
            menu.tick();
        }else if(gameState == STATE.Select){
            handler.tick();
            menu.tick();
        }
    
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        if(paused){
            g.setColor(Color.white);
            g.drawString("PAUSED", 300, 100);
            g.drawImage(pausedpic, (Game.WIDTH/2)-145, 300, null);
        }
        
        if(gameState == STATE.Game){
            hud.render(g);
            handler.render(g);
        }else if(gameState == STATE.Shop){
            shop.render(g);
        }else if(gameState == STATE.Menu){
            menu.render(g);
            handler.render(g);
        }else if(gameState == STATE.Help){
            menu.render(g);
            handler.render(g);
        }else if(gameState == STATE.End){
            menu.render(g);
            handler.render(g);
        }else if(gameState == STATE.Select){
            menu.render(g);
            handler.render(g);
        }
        
        g.dispose();
        bs.show();
    }
    
    public static int clamp(int var, int min, int max){
        if(var >= max) {
            return var = max;
        }
        else if(var <= min) {
            return var = min;
        }
        else {
            return var;
        }
    }
   
    public static void main(String args[]) {
        new Game();
    }
}