package Logika;

import java.awt.Color;
import java.awt.Graphics;


/**
 * 
 * @author senida
 * Klasa dozvoljenih prepreka, tj. ono što zmija "jede"
 * Postavke koordinata u polju
 * Crtanje oblika na odgovarajuæim koordinatama
 * 
 */
public class Apple {
	
	private int xCoor, yCoor, width, height;
	
	public Apple(int xCoor, int yCoor, int tileSize) {
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		width = tileSize;
		height = tileSize;
	}

	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(xCoor * width , yCoor * height, width, height);
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
	
}
