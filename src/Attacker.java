import java.awt.*;

public abstract class Attacker extends GameObj {
	private boolean dir;
	private boolean alive;
	
	public boolean getDir() {
		return dir;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean b) {
		alive = b;
	}
	
	public void die() {
		alive = false;
	}
	
	public void changeDir() {
		dir = !dir;
	}
	
	public void setDir(boolean d) {
		dir = d;
	}
	
	public void move(GameObj obj) {
		if(obj instanceof Player) {
			attack((Player)obj);
		}
	}
	
	public abstract void attack(Player p);

	@Override
	public void paintComponent(Graphics gc) {
		// TODO Auto-generated method stub
		super.paintComponent(gc);
	}

}
