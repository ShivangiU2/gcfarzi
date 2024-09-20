import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GameMenuAWT extends Frame implements ActionListener
 {
    private Image backgroundImage;
    private Image  start, exit,title,shop;
    private ImageButton startButton, Button, exitButton,optionsButton;
    private Font gamingFont;
    int tilesize=32;
    int rows=16;
    int columns=16;
    int boardheight=rows*tilesize;
    int boardwidth=columns*tilesize;


    public GameMenuAWT() 
    {
        setLayout(null);
        setSize(boardwidth, boardheight);
        setResizable(false);
        setTitle("Galaxy Clash");
        setVisible(true);
        setLocationRelativeTo(null);
        
        // background and title
        backgroundImage = Toolkit.getDefaultToolkit().getImage("bg.jpg");
        title= Toolkit.getDefaultToolkit().getImage("Designer-removebg-preview.png");

        //  images for buttons
        start = Toolkit.getDefaultToolkit().getImage("start.png");
        shop = Toolkit.getDefaultToolkit().getImage("shop.png");
        exit = Toolkit.getDefaultToolkit().getImage("exit.png");

        
 
        gamingFont = new Font("Serif", Font.BOLD, 24);  

        // Create image buttons
        startButton = new ImageButton(start, "", gamingFont);
        
        startButton.setBounds(tilesize*6,tilesize*7, tilesize*4, tilesize*1);
        startButton.addActionListener(this);

        optionsButton = new ImageButton(shop, "", gamingFont);
        optionsButton.setBounds(tilesize*6, tilesize*9,tilesize*4, tilesize*1);
        optionsButton.addActionListener(this);

        exitButton = new ImageButton(exit, "", gamingFont);
        exitButton.setBounds(tilesize*6, tilesize*11,tilesize*4, tilesize*1);
        exitButton.addActionListener(this);

        add(startButton);
        add(optionsButton);
        add(exitButton);
        
    }

    @Override
    public void paint(Graphics g) {
      
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(title,tilesize*4,tilesize/2,tilesize*8,tilesize*7,this);
      
      
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            System.out.println("Start Game button clicked");
             dispose();
             new game();
            


        } 
        else if (e.getSource() == optionsButton) 
         {
            System.out.println("Options button clicked");
         } 
        else if (e.getSource() == exitButton) 
        {
            System.out.println("Exit button clicked");
            System.exit(0);
        }
    }

    // Custom ImageButton class extending Canvas
    class ImageButton extends Canvas {
        private Image buttonImage;
        private String buttonText;
        private Font font;
        private ActionListener actionListener;

        public ImageButton(Image img, String text, Font font) {
            this.buttonImage = img;
            this.buttonText = text;
            this.font = font;
            this.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (actionListener != null) {
                        actionListener.actionPerformed(new ActionEvent(ImageButton.this, ActionEvent.ACTION_PERFORMED, buttonText));
                    }
                }
            });
        }

        public void addActionListener(ActionListener listener) {
            this.actionListener = listener;
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(buttonImage, 0, 0, this.getWidth(), this.getHeight(), this);

            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(buttonText);
            int textHeight = fm.getAscent();
            g.setColor(Color.WHITE);  // Set text color (optional, can be customized)
            g.drawString(buttonText, (this.getWidth() - textWidth) / 2, (this.getHeight() + textHeight) / 2 - 4);
        }
    }

    public static void main(String[] args) 
    {
        GameMenuAWT menu=new GameMenuAWT();
    }
}