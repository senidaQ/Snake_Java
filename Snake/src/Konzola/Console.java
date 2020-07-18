package Konzola;

import java.util.Scanner;

/**
 * 
 * @author senida
 * Zapo�inje igru u konzoli
 * 
 * Sadr�i osnovne atribute koji �uvaju ono �to igra� unese
 * Sadr�i jednu funkciju koja uzima podatke od igra�a, a dalje se koristi
 *  u klasi ConsoleGame i na osnovu tih podataka postavlja se polje igre
 *
 */

public class Console {
	
	public static char move;

	static char prevMove;
	
	ConsoleGame cg;
		
	public Console() {
		
		move = 'w';
		prevMove = 'w';
		cg = new ConsoleGame();
	}
	
	public static void getCommand() {
		
		Scanner in = new Scanner(System.in);
	   
		//ako je unos ne�to �to ne mo�e pretvoriti u znak, poput ENTER
	    try {

	      move =  in.nextLine().charAt(0);
	      
	      if(move =='\n') move = prevMove;

	      else prevMove = move;

	    } catch (StringIndexOutOfBoundsException e) {
	    	
	    	move = prevMove;
	    	
	    }
		
	}

	public static void main(String[] args) {
        new Console();
    }	
	
}
