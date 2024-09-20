import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Random;
 class game extends Frame implements Runnable, KeyListener,WindowListener

{
    class Block
    {
        int x;
        int y;
        int width;
        int height;
        Image img;
        boolean alive=true;//used for aliens
        boolean used=false; //for bullets
        Block(int x,int y, int width,int height,Image img)
        {
            this.x=x;
            this.y=y;
            this.width=width;
            this.height=height;
            this.img=img;
        }        
    }
    Thread th;
    int tilesize=32;
    int rows=16;
    int columns=16;
    int boardheight=rows*tilesize;
    int boardwidth=columns*tilesize;
    int heightcor;
    char key;

    private Image background;

    
    BufferedImage offScreenBuffer;
    Image shipImg;
    Image alienImg;
    Image alienCyanImg;
    Image alienMagentaImg;
    Image alienYellowImg;
    Image alienPurpleImg;
    Image alienRedImg;
    Image alienOrangeImg;
    ArrayList< Image > alienImgArray;

    //ship
    int shipw=tilesize*3;
    int shiph=tilesize*2;
    int xship=tilesize*columns/2-tilesize;
    int yship=tilesize*columns-(tilesize*3); 
    int shipvelox=tilesize;
    Block ship;
   
    //aliens
    ArrayList<Block> alienArray;
    int alienw=tilesize*2;
    int alienh=tilesize;
    int xalien=tilesize;
    int yalien=tilesize;

    int alienrows=2;
    int aliencol=3;
    int aliencount=0;
    int alienvelox=1;

    //bullets 
    ArrayList<Block> bulletArray;
    int bulletw=tilesize/8;
    int bulleth=tilesize/2;
    int bulletveloy=-10;

    int score=0;
    boolean gameover=false;

    game()
    {
    
        background = Toolkit.getDefaultToolkit().getImage("bg.jpg");
        shipImg = Toolkit.getDefaultToolkit().getImage("ship.png");
        alienImg =Toolkit.getDefaultToolkit().getImage("alien.png");
        alienCyanImg = Toolkit.getDefaultToolkit().getImage("alien-cyan.png");
        alienMagentaImg = Toolkit.getDefaultToolkit().getImage("alien-magenta.png");
        alienYellowImg = Toolkit.getDefaultToolkit().getImage("alien-yellow.png");
        alienPurpleImg=Toolkit.getDefaultToolkit().getImage("alien-purple.png");
        alienRedImg=Toolkit.getDefaultToolkit().getImage("alien-red.png");
        alienOrangeImg=Toolkit.getDefaultToolkit().getImage("alien-orange.png");
        alienImgArray =new ArrayList< Image >();
        alienImgArray.add(alienImg);
        alienImgArray.add(alienCyanImg);
        alienImgArray.add(alienMagentaImg);
        alienImgArray.add(alienYellowImg);
        alienImgArray.add(alienPurpleImg);
        alienImgArray.add(alienRedImg);
        alienImgArray.add(alienOrangeImg);

        setFocusable(true);
        requestFocusInWindow();
        setLayout(null);
         
        setTitle("Galaxy Clash");
        setVisible(true);
        addKeyListener(this);
        //System.out.println("Has focus: " + hasFocus());
        revalidate();
        repaint(); 
        

       // setSize(boardwidth, boardheight );
        Insets insets = getInsets();
        heightcor=insets.top + insets.bottom;
        yalien+=heightcor;
        yship+=heightcor;
        setSize(boardwidth, boardheight + heightcor);
       
        
        setResizable(false);
        setLocationRelativeTo(null);
        setBackground(Color.BLACK);
        
        addWindowListener(this);
        offScreenBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        //ship
        ship=new Block(xship,yship,shipw,shiph,shipImg);
        alienArray=new ArrayList< Block >();
        bulletArray=new ArrayList<Block>();

        init();
        createAliens();
   
    }

    public void init()
    {
        th=new Thread(this);
        th.start();
    }
    public void run()
    {
       
        while(!gameover)
        {
            try{th.sleep(16);}
             catch(Exception ex){ }
             move();
             repaint(); 
             
        }
    
       
    }
          
    public void keyPressed(KeyEvent ke)
   {
       
   
   }
public void keyReleased(KeyEvent k)
{
    key= k.getKeyChar();
    if((key=='d'||key=='D')&&(ship.x+ship.width+shipvelox <=(boardwidth)))
    {
     ship.x+=shipvelox;
     repaint();
    }
    else if((key=='a'||key=='A')&&ship.x-shipvelox>=0)
    {
     ship.x-=shipvelox;
     repaint();
    }
    else if(key== ' ')
    {
        Block bullet=new Block(ship.x + shipw*15/32, ship.y, bulletw, bulleth,null);
        bulletArray.add(bullet);
    }
   
}
public void keyTyped(KeyEvent k1)
{
    
}

public void update(Graphics g)
{
   
    paint(g);
}

   public void paint(Graphics g)
    {     super.paint(g);
          draw(g); 
        
    }  
    public void draw(Graphics g)
    {
        
        Graphics bufferGraphics = offScreenBuffer.getGraphics();
        bufferGraphics.clearRect(0, 0, getWidth(), getHeight());
        bufferGraphics.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
        bufferGraphics.drawImage(ship.img, ship.x, ship.y, ship.width,ship.height, null);
        
        for(int i=0;i<alienArray.size();i++)
        {
            Block alien=alienArray.get(i);
            if(alien.alive==true)
            {
                    bufferGraphics.drawImage(alien.img, alien.x, alien.y, alien.width , alien.height, null); // Draw ship image at (x, y)
            }
        }     

        g.setColor(Color.white);
        for(int i=0;i<bulletArray.size();i++)
         {
            Block bullet =bulletArray.get(i);
            if(!bullet.used)
            {
              bufferGraphics.drawRect(bullet.x ,bullet.y ,bullet.width ,bullet.height);
            }
            
         } 
         bufferGraphics.setColor(Color.pink);
         bufferGraphics.setFont(new Font("DialogInput", Font.BOLD,32));

         //score
         
         if(gameover)
         {
           // bufferGraphics.drawString("Game Over:"+ String.valueOf(score),10,65);
            dispose();
            gameover ob=new gameover(score);
            
         }
         else{
            bufferGraphics.drawString("Score:"+String.valueOf(score),10,65);

         }

         g.drawImage(offScreenBuffer, 0, 0, this);
        
        
    }
    public void move()
    {
        for(int i=0;i<alienArray.size();i++)
        {
            Block alien=alienArray.get(i);
            if(alien.alive){
                alien.x+=alienvelox;
                if(alien.x + alien.width>=boardwidth||alien.x <=0)
                {
                    alienvelox*=-1;
                    alien.x+=alienvelox*2;
                    //move one row down
                    for(int j=0;j<alienArray.size();j++)
                    {
                        alienArray.get(j).y +=alienh;
                    }

                }
                if(alien.y >= ship.y)
                {
                    gameover=true;
                }
            }
        }
        //bullets
        for(int i=0;i<bulletArray.size();i++)
        {
            Block bullet=bulletArray.get(i);
            bullet.y+=bulletveloy;

            // collision with aliens 
            for(int j=0;j<alienArray.size();j++)
        {
            Block alien = alienArray.get(j);
            if(!bullet.used && alien.alive && detectCollision(bullet,alien))
            {
                bullet.used=true;
                alien.alive=false;
                aliencount--;
                score+=100;

            }
        }
        
        }

        while(bulletArray.size()>0 &&(bulletArray.get(0).used || bulletArray.get(0).y<0))
        {
            bulletArray.remove(0);
        }
        //next level
        if(aliencount==0)
        {
            //increase the number of aliens in columns and rows by 1
            score+=aliencol*alienrows*100;
            aliencol=Math.min(aliencol+1,columns/2-2);
            alienrows=Math.min(alienrows+1, rows -6);
            alienArray.clear();
            bulletArray.clear();
            createAliens();

        } 
        
        
    }
    public void createAliens()
    {
        Random random= new Random();
        for(int r=0; r<alienrows;r++)
        {
            for(int c=0;c<aliencol;c++)
            {
                int randomImageIndex=random.nextInt(alienImgArray.size());
                Block alien= new Block(xalien+(c*alienw), yalien + (r*alienh), alienw , alienh ,alienImgArray.get(randomImageIndex));
                alienArray.add(alien);
            }
             
        }
        aliencount=alienArray.size(); 
    }
    public boolean detectCollision(Block a, Block b)
    {
        return a.x<b.x+b.width &&
        a.x+a.width>b.x &&
        a.y<b.y + b.height &&
        a.y+a.height>b.y;
    }
    @Override
    public void windowOpened(WindowEvent e) {
       
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
       
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
       
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
       
    }
    
}



public class win2 
{
    public static void main(String[] args) 
    {
        game obj=new game();
        obj.requestFocus();
        
    }

}