package Konzola;

import Logika.Apple;
import Logika.BodyPart;
import Logika.Snake;


/**
 * 
 * @author senida
 * 
 * Glavne postavke igre u konzoli
 *
 */
public class ConsoleGame {
	
	public static final int WIDTH = 60, HEIGHT = 30;
	
	private static boolean right;

	private static boolean left;

	private static boolean up;

	private static boolean down;
	
	public static boolean stop;
	
	public static Snake s;
	
	private static Apple apple;
	
	public static char [][] field;
	
	
	/**
	 * Konstruktor
	 */
	public ConsoleGame() {
		
		s = new Snake();
		start();
	}
	
	/**
	 * Ispisuje poèetnu poruku sa instrukcijama igre
	 * Postavlja atribute na poèetne vrijednosti
	 * Poziva paint() za prvo iscrtavanje polja
	 * Poziva play() za dalje igranje
	 */
	public static void start() {
		
		System.out.println("Play with: w | a | s | d , \n "
				+ "Or press ENTER to play same move in same direction again!"
				+ "\n\n Press r to start again");
		
		s.start();
		
		field = new char[HEIGHT][WIDTH];
		
		right = true;
    	left = false;
    	up = false;
    	down = false;
    	stop = false;
    	
    	s.fieldInit();
    	s.snakeInit(s);
    	int[] niz = s.setApple(WIDTH, HEIGHT);
    	
    	apple = new Apple(niz[0], niz[1], 0);
    	field[niz[1]][niz[0]] = 'A';
    	
    	paint();
    	play();
		
	}

	/**
	 * Iscrtava polje igre
	 */
	static void paint() {
		
		if(!stop) {
			for(int i = 0; i < HEIGHT; i++) {
				for(int j = 0; j < WIDTH; j++) 
					System.out.print(field[i][j]);
				System.out.println();
			}
		}
 
    }

	/**
	 * Ureðuje elemente niza field, zavisno od unosa igraèa pomjera zmiju, postavlja novu apple
	 *   poziva stop() ako je potrebno završiti igru
	 */
	public static void move() {
		
		if(right) s.setxCoor(s.getxCoor()+1);
		else if(left) s.setxCoor(s.getxCoor()-1);
		else if(up) s.setyCoor(s.getyCoor()-1);
		else if(down) s.setyCoor(s.getyCoor()+1);
		
		if(s.getxCoor() >= WIDTH-1 || s.getyCoor() >= HEIGHT-1 || s.getxCoor() <= 0 || s.getyCoor() <= 0)
			Snake.stop_c();
		
		s.snake.add(new BodyPart(s.getxCoor(), s.getyCoor(), 0));
		field[s.getyCoor()][s.getxCoor()] = 'o';
		
		if(s.snake.get(s.getSize()-1).getxCoor() == apple.getxCoor() && s.snake.get(s.getSize()-1).getyCoor() == apple.getyCoor()) {
			s.setSize(s.getSize()+1);
			int[] niz = s.setApple(WIDTH, HEIGHT);
			apple = new Apple(niz[0], niz[1], 0);
			field[niz[1]][niz[0]] = 'A';
		}else {
			field[s.snake.get(0).getyCoor()][s.snake.get(0).getxCoor()] = ' ';
			s.snake.remove(0);
		}
	}
	
	/**
	 * Sve dok nije kraj igre traži unos od igraèa i zavisno od unosa postavi atribute
	 * 	pozove move() za postavku elemenata polja field i pozove paint() za iscrtavanje polja
	 */
	public static void play() {
		
		do {
		
			Console.getCommand();
			
			if(Console.move == 'w' && down == false) {
				right = false;
				left = false;
				up = true;
			}else if(Console.move == 's' && up == false) {
				right = false;
				left = false;
				down = true;
			}else if(Console.move == 'a' && right == false) {
				up = false;
				down = false;
				left = true;
			}else if(Console.move == 'd' && left == false) {
				up = false;
				down = false;
				right = true;
			}else if(Console.move == 'q') Snake.stop_c();
			else if(Console.move == 'r') start();
			else {
				Console.move = Console.prevMove;
			}
			
			move();
			paint();
			
			
		}while(Console.move != 'q' && !stop);
		
	}


}
