import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
class gameover extends Frame implements KeyListener,WindowListener
{
    int tilesize=32;
    int rows=16;
    int columns=16;
    int boardheight=rows*tilesize;
    int boardwidth=columns*tilesize;

    private Image backgroundImage;
    
    int score;
    public gameover(int s) 
    {
        setLayout(null);
        setSize(boardwidth, boardheight);
        setResizable(false);
        setTitle("Galaxy Clash");
        setVisible(true);
        setLocationRelativeTo(null);
        score=s;
        backgroundImage = Toolkit.getDefaultToolkit().getImage("bg.jpg");
        addKeyListener(this);

    }
    public void paint(Graphics g) {
         g.setColor(Color.pink);
         g.setFont(new Font("DialogInput", Font.BOLD,32));
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawString("Game Over:"+ String.valueOf(score),10,tilesize*4);
        g.drawString("Press 'enter' to restart ",10,tilesize*5);
        g.drawString("Press Q to quit",10,tilesize*6);

      
      
    }
    @Override
    public void keyTyped(KeyEvent e) {
       
        
    }
    @Override
    public void keyPressed(KeyEvent e) 
    {

        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            System.out.println("gji");
            dispose();
            game  obj=new game();
        }   
        else if(e.getKeyCode()==KeyEvent.VK_Q)
        {
            System.exit(0);
        }

    }
    @Override
    public void keyReleased(KeyEvent e) {
        
        
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
        System.exit(0);
    
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
public class win3 {
    public static void main(String[] args) {
        gameover obj=new gameover(100);
    }
    
}