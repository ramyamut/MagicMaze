import java.awt.*;

import javax.swing.*;
public abstract class GameObj extends JComponent{
	//fields
	private int x;
	private int y;
	
	//methods
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int newX) {
		if (newX < 0) {
			throw new IllegalArgumentException();
		}
		x = newX;
	}
	
	public void setY(int newY) {
		if (newY < 0) {
			throw new IllegalArgumentException();
		}
		y = newY;
	}
	
	@Override
	public void paintComponent(Graphics gc) {
		super.paintComponent(gc);
	};
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(20, 20);
	}
	
}
