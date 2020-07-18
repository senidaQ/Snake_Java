package GUI;

import javax.swing.JFrame;


/**
 * 
 * @author senida
 * 
 * Zapoèinje igra
 */
public class Frame {

	public Frame() {
    	
    	JFrame frame = new JFrame("Snake");
    	Game game = new Game();
    	
    	frame.add(game);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setTitle("Snake");
    	frame.setResizable(false);
    	frame.pack();
    	frame.setLocationRelativeTo(null);
    	frame.setVisible(true);   

    }
    
    public static void main(String[] args) {
        new Frame();
    }
	
}
