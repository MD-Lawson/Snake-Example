package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGameScreen  extends JFrame {
		int squareTop[] = new int[1000]; //The array that stores the snakes Top value
	    int squareLeft[] = new int[1000];//Array stores the snakes left value
	    int direction = 0, timerstart = 0;
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int squareWidth = 30, squareHeight = squareWidth, frameHeight = 900, 
	    		frameWidth = 1600, eatLeft = 300, eatTop = 300, score = 0, dots = 20,
	    		programEnd = 0, timerdelay = 100; // Defines the snakes dimensions and size, frame dimensions, first pellet location
	    boolean replay = false;
	    boolean cheat = false;
	    boolean resize = false;
	    Font textFont = new Font("TimesRoman", Font.BOLD + Font.ITALIC, 24);
	    Color snakeBodyColour = Color.gray;
	    Color snakeHeadColour = Color.cyan;
	    Color textColour = Color.white;
	    Color backgroundColour = Color.black;
	    Color eatPelletColour = Color.yellow;
	    //Redraws the screen
	    ActionListener updateDrawingArea = new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                repaint();
	            }
	        };
	    public SnakeGameScreen(){
	    	frameWidth = (int)screenSize.getWidth();
	    	frameHeight = (int)screenSize.getHeight();
	    	System.out.println("Screen width is " + screenSize.getWidth());
	    	System.out.println("Screen height is " + screenSize.getHeight());
	        squareLeft[0] = 180;//This is the snakes starting position with the 0 value being the snakes head.
	        squareLeft[1] = 150;// as above
	        squareLeft[2] = 120;// as above
	        // Initialising the frame
	        this.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setResizable(resize);
	        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	        this.setUndecorated(true);
	        this.setTitle("Matt's Super Awesome Snake Game");
	        this.setLocationRelativeTo(null);
	        KeyBoardListen kl = new KeyBoardListen(); //Listens for keyboard keys
	        this.addKeyListener(kl);
	        this.add(new SnakeGamePanel());
	        this.setVisible(true);
	    }
	    public void eatPosition(){
	        //Randomly sets the pellets location every time it's eaten
	        eatLeft = (int)((Math.random()*(frameWidth-squareWidth-10)/squareWidth)+1)*squareWidth;
	        eatTop = (int)((Math.random()*(frameHeight-squareWidth-30)/squareWidth)+1)*squareWidth;	        
	        //prevents the pellet from spawning on top of the snake
	        for(int a = 0; a < dots; a++){
	            if(eatLeft == squareLeft[a] && eatTop == squareTop[a]){
	                eatPosition();// If on a snake method is called again
	                break;
	            }
	        }
	    }
	    // Keyboard Action Listener
	    private class KeyBoardListen implements KeyListener{
	        public void keyPressed(KeyEvent e){
	            int key = e.getKeyCode();  // keyboard code for the key that was pressed
	            if (key == KeyEvent.VK_LEFT) {
	                direction = 1;
	            }else if (key == KeyEvent.VK_RIGHT) {
	                direction = 2;
	            }
	            else if (key == KeyEvent.VK_UP) {
	                direction = 3;
	            }
	            else if (key == KeyEvent.VK_DOWN) {
	                direction = 4;
	            }
	            if(key == KeyEvent.VK_ENTER){
	                if(cheat){
	                    cheat = false;
	                }else{
	                    cheat = true;
	                }
	            }
	            //The timer to redraw the frame doesn't run from the beginning, this piece of code sets it running on the first button press
	            if(timerstart == 0){
	                new Timer(timerdelay, updateDrawingArea).start();// Speed of the game can be changed by changing timer delay
	                timerstart = 1;
	            }
	        }

	        public void keyReleased(KeyEvent e){
	        }

	        public void keyTyped(KeyEvent e){

	        }
	    }
	    
	    
	    private class SnakeGamePanel extends JPanel{
	        public void paintComponent(Graphics g)
	        {  
	            if(programEnd == 1){
	                //This is for pausing the snakes movement
	                super.paintComponent(g);
	                this.setBackground(backgroundColour);
	                for(int i = dots - 1; i > 0; i--){
	                    g.setColor(snakeBodyColour);
	                    g.fillRoundRect((squareLeft[i]), (squareTop[i]), squareWidth, squareHeight, 15, 15);
	                    squareLeft[i] = squareLeft[i];
	                    squareTop[i] = squareTop[i];
	                }
	                g.setColor(textColour);
	                g.setFont(textFont);
	                g.drawString("Score: " + String.valueOf(score), 20, 20);//draw score
	                g.setColor(snakeHeadColour);
	                g.fillRoundRect(squareLeft[0], squareTop[0], squareWidth, squareHeight, 15 ,15);
	            }
	            else{
	                super.paintComponent(g);
	                
	                this.setSize(frameWidth, frameHeight);
	                this.setBackground(backgroundColour);
	                
	                // This loop checks if the snake is on the same square as the pellet so that it has eaten
	                if(squareLeft[0] == eatLeft && squareTop[0] == eatTop){
	                    eatPosition();// sets the pellet location
	                    score++;// Adds 1 to score if eaten
	                    dots++;// Adds new part to snake
	                }
	                
	                
	                g.setColor(eatPelletColour);// Sets pellet colour
	                g.fillOval(eatLeft, eatTop, squareWidth, squareWidth);//Draws new pellet
	                
	                // This deals with what happens when snake leaves the frame border
	                // If cheat is on the snake simply bounces back. If its off the game ends.
	                if(cheat){
	                    if(squareLeft[0] < 0){
	                         direction = 2; 
	                    }else if((squareLeft[0]+squareWidth) > frameWidth){
	                         direction = 1; 
	                    }else if(squareTop[0] < 0){ 
	                         direction = 4;
	                    }else if((squareTop[0] + squareHeight) > (frameHeight - 25)){
	                         direction = 3;
	                    }
	                }else{
	                    if(squareLeft[0] < 0){
	                         programEnd = 1; 
	                    }else if((squareLeft[0]+squareWidth) > frameWidth){
	                         programEnd = 1;
	                    }else if(squareTop[0] < 0){ 
	                         programEnd = 1;
	                    }else if((squareTop[0] + squareHeight) > (frameHeight - 25)){
	                         programEnd = 1;
	                    }
	                }
	                
	                
	                //This is just for drawing the snakes body
	                for(int i = dots - 1; i > 0; i--){
	                    

	                    g.setColor(snakeBodyColour);
	                    g.fillRoundRect((squareLeft[i-1]), (squareTop[i-1]), squareWidth, squareHeight, 15, 15);
	                    squareLeft[i] = squareLeft[i-1];
	                    squareTop[i] = squareTop[i-1];

	                }
	                
	                //Determines where to draw snake based on direction
	                if(direction == 1){
	                    squareLeft[0] -= squareWidth;
	                    //squareLeft[0] -= 5;
	                }else if(direction == 2){
	                    squareLeft[0] += squareWidth;
	                    //squareLeft[0] += 5;
	                }else if(direction == 3){
	                    squareTop[0] -= squareHeight;
	                    //squareTop[0] -= 5;
	                }else if(direction == 4){
	                    squareTop[0] += squareHeight;
	                    //squareTop[0] += 5;
	                }
	                //Sets colour and actually draws the snake
	                g.setColor(textColour);
	                g.setFont(textFont);
	                g.drawString("Score: " + String.valueOf(score), 20, 20);//draw score
	                g.setColor(snakeHeadColour);
	                g.fillRoundRect(squareLeft[0], squareTop[0], squareWidth, squareHeight, 15 ,15);//draw snakes head
	                //This code only runs if the timer is running
	                if(timerstart == 1){
	                    //Checks for snake collisions
	                    for(int i = dots - 1; i > 0; i--){
	                        //If cheat is on collisions are ignored
	                        if(!cheat){
	                            if(squareLeft[0] == squareLeft[i] && squareTop[0] == squareTop[i]){
	                                programEnd = 1;
	                                break;
	                            }
	                        }else{
	                            g.setColor(textColour);
	                            g.drawString("Cheat on", 500, 20);
	                        }                    
	                    }
	                    
	                    //Ends game, shows message box, deals with replay.
	                    if(programEnd == 1){
	                        direction = 0;
	                        int reply;
	                        reply = JOptionPane.showConfirmDialog(null, "Gameover, would you like to play again?", "Gameover!",  JOptionPane.YES_NO_OPTION);
	                        if (reply == JOptionPane.YES_OPTION)
	                        {
	                            //If replay is chosen creates a new frame instance and ends the current one.
	                            new SnakeGameScreen();// New frame instance
	                            dispose();// Ends current instance
	                        }else{
	                            System.exit(0); // Ends program.
	                        }
	                    }
	                }

	            }
	        }
	    }
}
