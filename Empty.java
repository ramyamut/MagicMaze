import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Empty extends GameObj {
	
	public Empty(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	@Override
	public void paintComponent(Graphics gc) {
		// TODO Auto-generated method stub
		super.paintComponent(gc);
		gc.setColor(Color.WHITE);
		gc.fillRect(this.getX(), this.getY(), 20, 20);
	}

}
