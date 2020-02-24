import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class WeakAttacker extends Attacker {
	
	public static final String IMG_FILE = "files/weakattacker.jpg";
	private static BufferedImage img;
	
	private int vx;
	
	public WeakAttacker(int x, int y, boolean dir) {
		this.setX(x);
		this.setY(y);
		this.setDir(dir);
		this.setAlive(true);
		this.vx = 20;
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
		if(this.isAlive()) {
			if(this.getDir()) {
				this.setX(this.getX() + vx);
			}
			else {
				this.setX(this.getX() - vx);
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics gc) {
		super.paintComponent(gc);
		gc.drawImage(img, this.getX(), this.getY(), 20, 20, null);
	}

	@Override
	public void attack(Player p) {
		// TODO Auto-generated method stub
		int score = p.getScore();
		int newScore = score - 10;
		p.setScore(newScore);
		this.die();
	}

}
