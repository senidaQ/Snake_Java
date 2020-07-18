package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Logika.Snake;

/**
 * 
 * @author senida
 * 
 * Èitanje podataka od igraèa, unosa sa tastature
 * Postavljanje ekrana prema proèitanom unosu
 */
public class Game extends JPanel implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 750, HEIGHT = 600;
	
	public static int br;
	
	private static boolean right;

	private static boolean left;

	private static boolean up;

	private static boolean down;
	
	private static Thread thread;
	
	private static boolean start;
	
	public static Snake s;
	
	public Game() {
	
		setFocusable(true);
		
		addKeyListener(this);
	    setPreferredSize(new Dimension(WIDTH, HEIGHT)); 

	    s = new Snake();
	    
	    br = 0;
	    start = false;
	    start();
	    
	}
	
	
	/**
	 * Postavljanje atributa na poèetne vrijednosti
	 */
	public void start() {

		s.start();
		right = true;
    	left = false;
    	up = false;
    	down = false;
        thread = new Thread(this);
        thread.start();
	}
		
	/**
	 * Zaustavljanje thread
	 * Ispis poruke (ako je potrebno)
	 */
	public static void stop() {
		
		if(br == 1) JOptionPane.showMessageDialog(null, "SCORE: " + (s.getSize()-5) + "\n\n"
				+ "Press SPACE to pauze game \n"
				+ "Play with arrow keys \n\n"
				+ "Press ENTER To Start","ENTER", JOptionPane.QUESTION_MESSAGE);
		start = false;
		br++;
    	s.setRunning(false);
    	try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
	}
	
	/**
	 * Iscrtavanje potrebnih oblika na polje igre
	 */
	public void paint(Graphics g) {
    	g.clearRect(0, 0, WIDTH, HEIGHT);
    	g.setColor(Color.ORANGE);
    	g.fillRect(0, 0, WIDTH, HEIGHT);
    	
        g.setColor(Color.BLACK);
        for (int i = 0; i < WIDTH / 10; i++) {
            g.drawLine(i * 10, 0, i * 10, HEIGHT);
        }
        
        for (int i = 0; i < HEIGHT / 10; i++) {
            g.drawLine(0, i * 10, WIDTH, i * 10);
        }
 
        for (int i = 0; i < s.snake.size(); i++) {
            s.snake.get(i).draw(g);
        }
        for(int i = 0; i < s.apples.size(); i++) {
        	s.apples.get(i).draw(g);
        }
 
    }
	
	
	/**
	 * Ako je potrebno ispis poruke - samo kada se tek otvori igra (br == 0)
	 * Igraj
	 * Dok god igra nije prekinuta, od strane korisnika ili ako je izgubio
	 *	pozivaj tick_game() iz logike i ponovi crtanje na ekranu
	 * 
	 */
	@Override
	public void run() {
		if(!start && br == 0) {
			JOptionPane.showMessageDialog(null, "SCORE: " + (s.getSize()-5) + "\n\n"
					+ "Press SPACE to pauze game \n"
					+ "Play with arrow keys \n\n"
					+ "Press ENTER To Start","ENTER", JOptionPane.QUESTION_MESSAGE);
			start = true;
		}
		while (s.isRunning()) {
			s.tick_game();
			repaint();
		}
	}
	
	/**
	 * Prati unose sa tastature i postavlja atribute na potrebne vrijednosti, zaustavlja igru i slièno
	 */

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();  

		if(!start) {
			if(key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) {
				start = true;
				start();
			}
		}else {
		if(key == KeyEvent.VK_RIGHT && !left) {
			up = false;
			down = false;
			right = true;
		}
		if(key == KeyEvent.VK_LEFT && !right) {
			up = false;
			down = false;
			left = true;
		}
		if(key == KeyEvent.VK_UP && !down) {
			left = false;
			right = false;
			up = true;
		}
		if(key == KeyEvent.VK_DOWN && !up) {
			left = false;
			right = false;
			down = true;
		}if(key == KeyEvent.VK_SPACE) Snake.setPaused(!s.isPaused()); 
		}
	}
	
	
	/**
	 * Getteri i setteri potrebni za logiku
	 * @return
	 */
	public static boolean isRight() {
		return right;
	}

	public static void setRight(boolean right) {
		Game.right = right;
	}

	public static boolean isLeft() {
		return left;
	}

	public static void setLeft(boolean left) {
		Game.left = left;
	}

	public static boolean isUp() {
		return up;
	}

	public static void setUp(boolean up) {
		Game.up = up;
	}

	public static boolean isDown() {
		return down;
	}

	public static void setDown(boolean down) {
		Game.down = down;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
