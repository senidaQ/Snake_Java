package Logika;

import java.util.ArrayList;
import java.util.Random;

import GUI.Game;
import Konzola.Console;
import Konzola.ConsoleGame;


/**
 * 
 * @author senida
 * 
 * Glavna klasa logike
 * Sadrži sve potrebne informacije za igranje, u GUI ili konzoli
 *
 */
public class Snake {
		
		//da li traje igra, da li je pauzirana
	 	private static boolean running, paused;
	 	
	 	//snake se sastoji od b-ova (onoliko njih kolika joj je dužina)
		private BodyPart b;
	    public ArrayList<BodyPart> snake;
	 
	    //apples se sastoji od apple
	    public Apple apple;
	    public ArrayList<Apple> apples;
	    
	    //za postavljanje nove apple na random mjesto
	    private Random r;
	    
	    //koordinate na polju, size - velièina zmije
	    private int xCoor, yCoor, size;
	    
	    //koliko je puta pomjereno
	    private int ticks;
	    
	    
	    /**
	     * Konstruktor
	     */
	    public Snake() {
	    	
		    r = new Random();
	        
	        snake = new ArrayList<BodyPart>();
	        apples = new ArrayList<Apple>();  
	        
	        start();
        
	    }
	    
	    /**
	     * Inicijalizira atribute na poèetne vrijednosti
	     * Oèisti nizove snake i apples, što na poèetu nije potrebno, ali ako se ista igra 
	     * 	restartuje potrebne je d aovi nizov budu prazni
	     */
	    public void start() {
	    	
	    	xCoor = 10;
	    	yCoor = 10;
	    	size = 5;
	    	ticks = 0;
	    	
	    	snake.clear();
	    	apples.clear();
	    	
	    	paused = false;
	        running = true;
	    }
	    
	    
	    /**
	     * Koristi se u GUI dijelu
	     * Provjerava da li je potrebno dodati novi BodyPart u niz snake, 
	     * 	da li je potrebno postaviti apple na novu poziciju - u sluèaju da je "pojedena"
	     * 	Doda novi element nizu snake i ako nije potrebno poveæati veièinu niza ukloni prvi element, 
	     *    što ustvari pomjeri zmiju za jednu poziciju u potrebnom smjeru
	     *    A to zna na osnovu xCoor i yCoor, odnosno zavisno od igraèa: da li je promjenio smjer
	     *  
	     *  Zaustavlja igru ako je zmija udarila u zid
	     */
		public void tick_game() {
	    	
	    	if(!paused) {
		        if (snake.size() == 0) {
		            b = new BodyPart(xCoor, yCoor, 10);
		            snake.add(b);
		        }
		        
		        if(apples.size() == 0) {
		        	int xCoor = r.nextInt(Game.WIDTH/10-2);
		        	int yCoor = r.nextInt(Game.HEIGHT/10-2);
		        	
		        	apple = new Apple(xCoor, yCoor, 10);
		        	apples.add(apple);
		        }
		        
		        for(int i = 0; i < apples.size(); i++) {
		        	if(xCoor == apples.get(i).getxCoor() && yCoor == apples.get(i).getyCoor()) {
		        		size++;
		        		apples.remove(i);
		        		i++;
		        	}
		        }
		        
		        for(int i =0; i < snake.size(); i++) {
		        	if(xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
		        		if(i != snake.size() - 1) {
		        			Game.stop();
		        		}
		        	}
		        }
		        
		        if(xCoor < 1 || xCoor > Game.WIDTH/10-2 || yCoor < 1 || yCoor > Game.HEIGHT/10-2) {
		        	Game.br = 1;
		        	Game.stop();
		        }
		        
		        ticks++;
		        
		        if(ticks > 250000) {
		        	if(Game.isRight()) xCoor++;
		        	if(Game.isLeft()) xCoor--;
		        	if(Game.isUp()) yCoor--;
		        	if(Game.isDown()) yCoor++;
		        	
		        	ticks = 0;
		        	
		        	b = new BodyPart(xCoor, yCoor, 10);
		        	snake.add(b);
		        	
		        	if(snake.size() > size) {
		        		snake.remove(0);
		        	}
		        }
	    	}
	    }
		
		/**
		 * Koristi se u konzoli
		 * Postavlja elemente niza field
		 */
		public void fieldInit() {
			
			for(int i = 0; i < ConsoleGame.HEIGHT; i++) {
				for(int j = 0; j < ConsoleGame.WIDTH; j++) {
					if(i == 0 || j == 0 || i == ConsoleGame.HEIGHT-1 || j == ConsoleGame.WIDTH-1)
						ConsoleGame.field[i][j] = '*';
					else 
						ConsoleGame.field[i][j] = ' ';
				}
			}
			
		}
		
		/**
		 * 
		 * @param s
		 * 
		 * Koristi se u konzoli i postavlja niz s na poèetne koordinate
		 * Mijenja i elemente niza field, da sada oznaèavaju da je na tim kooridnatama snake s
		 */
		public void snakeInit(Snake s) {
			
			int x = s.getxCoor();
			for(int i = 0; i < s.getSize(); i++) {	
				s.setxCoor(x+i);
				s.snake.add(new BodyPart(s.getxCoor(), s.getyCoor(), 0));
				ConsoleGame.field[s.getyCoor()][s.getxCoor()] = 'o';
			}
		}
		
		
		/**
		 * 
		 * @param w
		 * @param h
		 * 
		 * U polju širine w i visine h, postavlja elemente xCorra i yCoora na random poziciju
		 * Vrati njihove vrijednosti, što koristimo prilikom postavke apple
		 *	
		 * 
		 * @return
		 */
		public int[] setApple(int w, int h) {
			int xCoora, yCoora;
			do {
				xCoora = r.nextInt(w-2);
	    		yCoora = r.nextInt(h-2);
			}while((xCoora <= snake.get(size-1).getxCoor() && yCoora <= snake.get(size-1).getyCoor())
					&& (xCoora >= snake.get(0).getxCoor() && yCoora >= snake.get(size-1).getyCoor())
					|| (xCoora == 0 || yCoora == 0 || xCoora == w || yCoora == h));
			int[] niz = {xCoora, yCoora};
			return niz;
		}
		
		public static void stop_c() {
			ConsoleGame.stop = true;
			System.out.println("GAME OVER!");
			System.out.println("SCORE: " + (ConsoleGame.s.getSize()-5));
			Console.getCommand();
			if(Console.move == 'r') ConsoleGame.start();
			
		}
		
		/**
		 * Getteri i setteri
		 * @return
		 */
		
		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}
		
		public boolean isRunning() {
			return running;
		}
		
		public void setRunning(boolean running) {
			Snake.running = running;
		}

		public boolean isPaused() {
			return paused;
		}

		public static void setPaused(boolean paused) {
			Snake.paused = paused;
		}
		
		public int getxCoor() {
			return xCoor;
		}

		public void setxCoor(int xCoor) {
			this.xCoor = xCoor;
		}

		public int getyCoor() {
			return yCoor;
		}

		public void setyCoor(int yCoor) {
			this.yCoor = yCoor;
		}

		public int getTicks() {
			return ticks;
		}

		public void setTicks(int ticks) {
			this.ticks = ticks;
		}

}
