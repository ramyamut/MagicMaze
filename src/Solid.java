import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Solid extends GameObj {
	
	public static final String IMG_FILE = "files/solid.png";
	private static BufferedImage img;
	
	public Solid(int x, int y) {
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
