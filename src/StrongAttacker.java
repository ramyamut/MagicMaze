import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StrongAttacker extends Attacker {
	
	public static final String IMG_FILE = "files/strongattacker.png";
	private static BufferedImage img;
	private int vy;
	
	public StrongAttacker(int x, int y, boolean dir) {
		this.setX(x);
		this.setY(y);
		this.setDir(dir);
		this.setAlive(true);
		this.vy = 40;
		try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
	}

	@Override
	public void move(GameObj obj) {
		super.move(obj);
		if(this.getDir()) {
			this.setY(this.getY() + vy);
		}
		else {
			this.setY(this.getY() - vy);
		}
	}
	
	@Override
	public void paintComponent(Graphics gc) {
		gc.setColor(Color.WHITE);
		gc.drawImage(img, this.getX(), this.getY(), 20, 20, null);
	}


	@Override
	public void attack(Player p) {
		p.die();
	}

}
