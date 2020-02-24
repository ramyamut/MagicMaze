import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Player extends GameObj {
	private int score;
	private boolean alive;
	
	public static final String IMG_FILE = "files/player.jpg";
	private static BufferedImage img;
	
	//constructor
	public Player(int x, int y) {
		if (x < 0 || y < 0) {
			throw new IllegalArgumentException();
		}
		this.score = 0;
		this.alive = true;
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
	
	public int getScore() {
		return this.score;
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public void setScore(int newScore) {
		this.score = newScore;
	}
	
	public void die() {
		this.alive = false;
	}

	public void move(int dx, int dy, GameObj obj) {
		// TODO Auto-generated method stub
		if (obj instanceof Coin) {
			this.score++;
		}
		else if (obj instanceof WeakAttacker || obj instanceof StrongAttacker) {
			Attacker attacker = (Attacker) obj;
			attacker.attack(this);
		}
		if(this.alive && !(obj instanceof Solid)) {
			setX(getX() + dx);
			setY(getY() + dy);
		}
	}

	@Override
	public void paintComponent(Graphics gc) {
		// TODO Auto-generated method stub
		super.paintComponent(gc);
		gc.drawImage(img, this.getX(), this.getY(), 20, 20, null);
	}

}
