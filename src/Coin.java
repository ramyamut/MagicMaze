import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Coin extends GameObj {
	
	public static final String IMG_FILE = "files/coin.jpg";
	private static BufferedImage img;
	
	//constructor
	public Coin(int x, int y) {
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		this.setX(x);
		this.setY(y);
		try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
	}

	@Override
	public void paintComponent(Graphics gc) {
		// TODO Auto-generated method stub
		super.paintComponent(gc);
		gc.drawImage(img, this.getX(), this.getY(), 20, 20, null);
	}

}
