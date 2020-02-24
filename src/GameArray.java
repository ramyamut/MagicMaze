import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import java.io.*;

@SuppressWarnings("serial")
public class GameArray extends JPanel{
	//fields
	private GameObj[][] arr;
	private boolean playing;
	private boolean win;
	private boolean lose;
	private int count;
	private JLabel scoreLabel;
	private String pathToHighScores;
	private int[] highScores;
	private String[] highScorers;
	private String playerName;
	private boolean updated;
	private Player player;
	private WeakAttacker weak;
	private StrongAttacker strong;
	
	public static final int INTERVAL = 35;
	
	//constructor
	public GameArray(JLabel label, String name, String path) {
		if (label == null) {
			throw new IllegalArgumentException();
		}
		this.pathToHighScores = path;
		this.highScores = calcHighScores();
		this.highScorers = calcHighScorers();
		this.count = 0;
		this.scoreLabel = label;
		this.playerName = name;
		this.playing = true;
		this.win = false;
		this.lose = false;
		this.updated = false;
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.arr = new GameObj[20][20];
		fillMaze();
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				int k = e.getKeyCode();
				if (k == KeyEvent.VK_LEFT) {
					playerMove(-20, 0);
				}
				else if (k == KeyEvent.VK_RIGHT) {
					playerMove(20, 0);
				}
				else if (k == KeyEvent.VK_UP) {
					playerMove(0, -20);
				}
				else if (k == KeyEvent.VK_DOWN) {
					playerMove(0, 20);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});
        timer.start(); 
	}
	
	//getter methods for testing
	public int getScore() {
		return this.player.getScore();
	}
	
	public boolean isPlaying() {
		return this.playing;
	}
	
	public boolean hasLost() {
		return this.lose;
	}
	
	public boolean hasWon() {
		return this.win;
	}
	
	public boolean weakIsAlive() {
		return this.weak.isAlive();
	}
	
	public int[] getHighScores() {
		return this.highScores;
	}
	
	public String[] getHighScorers() {
		return this.highScorers;
	}
	
	public GameObj getAtPos(int i, int j) {
		return this.arr[i][j];
	}
	
	//setter methods for testing
	void setScore(int newScore) {
		this.player.setScore(newScore);
	}
	
	void setPlayerName(String newName) {
		this.playerName = newName;
	}
	
	void setHighScores(int[] scores, String[] scorers) {
		if(scores.length != 3 || scorers.length != 3) {
			throw new IllegalArgumentException();
		}
		this.highScores = scores;
		this.highScorers = scorers;
	}
	
	/* helper method that reads in the file that contains high scores
	 * and returns those numerical values in an int array */
	int[] calcHighScores() {
		int[] ret = new int[3];
		try {
			BufferedReader b = new BufferedReader(new FileReader(this.pathToHighScores));
			for (int i = 0; i < 3; i++) {
				String scoreInfo = b.readLine();
				if(scoreInfo == null) {
					ret[i] = 0;
				}
				else {
					String[] scoreInfoArray = scoreInfo.split(", ");
					if(scoreInfoArray.length != 2) {
						throw new IllegalArgumentException();
					}
					ret[i] = Integer.parseInt(scoreInfoArray[1]);
				}
			}
			if(b.readLine() != null) {
				throw new IllegalArgumentException();
			}
			for(int i = 0; i < ret.length - 1; i++) {
				if(ret[i] < ret[i+1]) {
					throw new IllegalArgumentException();
				}
			}
			b.close();
			return ret;
		}
		catch (FileNotFoundException e){
			throw new IllegalArgumentException();
		}
		catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}
	
	/* helper method that reads in the file that contains high scores
	 * and return the names of the high scorers in a String array */
	String[] calcHighScorers() {
		String[] ret = new String[3];
		try {
			BufferedReader b = new BufferedReader(new FileReader(this.pathToHighScores));
			for (int i = 0; i < 3; i++) {
				String scoreInfo = b.readLine();
				if(scoreInfo == null) {
					ret[i] = null;
				}
				else {
					String[] scoreInfoArray = scoreInfo.split(", ");
					if(scoreInfoArray.length != 2) {
						throw new IllegalArgumentException();
					}
					ret[i] = scoreInfoArray[0];
				}
			}
			for(int i = 0; i < ret.length - 1; i++) {
				if(ret[i] == null && ret[i+1] != null) {
					throw new IllegalArgumentException();
				}
			}
			if(b.readLine() != null) {
				throw new IllegalArgumentException();
			}
			b.close();
			return ret;
		}
		catch (FileNotFoundException e){
			throw new IllegalArgumentException();
		}
		catch (IOException e) {
			throw new IllegalArgumentException();
		}
	}
	
	/* helper method (called when the game is over) that writes
	 * the updated set of high scores and their associated scorers
	 * back to the file that contains the data*/
	void writeHighScores() {
		try {
			BufferedWriter b = new BufferedWriter(new FileWriter(this.pathToHighScores));
			for(int i = 0; i < 3; i++) {
				if(this.highScorers[i] != null) {
					if(i != 0) {
						b.newLine();
					}
					String strToWrite = this.highScorers[i] + ", " + this.highScores[i];
					b.write(strToWrite);
				}
			}
			b.close();
		}
		catch(IOException e) {
			throw new IllegalArgumentException();
		}
	}
	
	/* helper method that updates the variables containing the
	 * highest scores and their associated scorers */
	void updateHighScores() {
		int index = 3;
		for(int i = 0; i < 3; i++) {
			if(this.player.getScore() >= this.highScores[i]) {
				index = i;
				break;
			}
		}
		for(int i = 2; i > index; i--) {
			this.highScores[i] = this.highScores[i - 1];
			this.highScorers[i] = this.highScorers[i - 1];
		}
		if(index < 3) {
			this.highScores[index] = this.player.getScore();
			this.highScorers[index] = this.playerName;
		}
	}
	
	/* helper function that is called every time the player
	 * presses an arrow key; takes in how much the player wants to move
	 * in GUI coordinates and moves the player in the maze accordingly*/
	void playerMove(int dx, int dy) {
		int playerX = this.player.getX();
		int playerY = this.player.getY();
		int indI = playerY/20;
		int indJ = playerX/20;
		int indDi = dy/20;
		int indDj = dx/20;
		int newI = indI + indDi;
		int newJ = indJ + indDj;
		if (newI == this.arr[0].length - 1 && newJ == this.arr.length - 1) {
			this.playing = false;
			this.win = true;
		}
		if (newI < this.arr.length && newI >= 0 && newJ < this.arr[0].length && newJ >= 0) {
			GameObj obj = this.arr[newI][newJ];
			this.player.move(dx, dy, obj);
			if (obj instanceof Coin || obj instanceof Empty || obj instanceof WeakAttacker) {
				this.arr[newI][newJ] = this.player;
				this.arr[indI][indJ] = new Empty(playerX, playerY);
			}
			if (!this.player.isAlive()) {
				this.playing = false;
				this.lose = true;
			}
		}
	}
	
	/* helper function that moves the strong attacker in the maze
	 * every time the timer "tick" is called*/
	void strongAttackerMove() {
		int strongX = this.strong.getX();
		int strongY = this.strong.getY();
		int indI = strongY/20;
		int indJ = strongX/20;
		if (indI > 15 || indI < 10) {
			this.strong.changeDir();
		}
		int indDi;
		if (this.strong.getDir()) {
			indDi = 2;
		}
		else {
			indDi = -2;
		}
		int indDiHalf = indDi/2;
		int newI = indI + indDi;
		int newIHalf = indI + indDiHalf;
		GameObj temp = this.arr[newI][indJ];
		GameObj temp2 = this.arr[newIHalf][indJ];
		if (temp2 instanceof Player) {
			this.strong.attack((Player) temp2);
			this.playing = false;
			this.lose = true;
		}
		if(this.playing) {
			this.arr[newI][indJ] = this.strong;
			this.arr[indI][indJ] = new Empty(strongX, strongY);
			this.strong.move(temp);
		}
		if(!this.player.isAlive()) {
			this.playing = false;
			this.lose = true;
		}
	}
	
	/* helper function that moves the weak attacker in the maze
	 * every time the timer "tick" is called*/
	void weakAttackerMove() {
		int weakX = this.weak.getX();
		int weakY = this.weak.getY();
		int indI = weakY/20;
		int indJ = weakX/20;
		if (indJ > 15 || indJ < 6) {
			this.weak.changeDir();
		}
		int indDj;
		if (this.weak.getDir()) {
			indDj = 1;
		}
		else {
			indDj = -1;
		}
		int newJ = indJ + indDj;
		GameObj temp = this.arr[indI][newJ];
		this.weak.move(temp);
		if(this.weak.isAlive()) {
			this.arr[indI][newJ] = this.weak;
			this.arr[indI][indJ] = temp;
			temp.setX(weakX);
			temp.setY(weakY);
		}
		else {
			this.arr[indI][indJ] = new Empty(weakX, weakY);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.playing) {
			for(int i = 0; i < this.arr.length; i++) {
				for(int j = 0; j < this.arr[i].length; j++) {
					this.arr[i][j].paintComponent(g);
				}
			}
		}
		else {
			String str1 = "Your score was: " + player.getScore();
			String str2 = "High Scorers:";
			if (this.lose) {
				g.drawString("You lost! " + str1, 100, 100);
			}
			else if (this.win){
				g.drawString("You won! " + str1, 100, 100);
			}
			if(this.lose || this.win) {
				g.drawString(str2, 100, 130);
				int i = 0;
				while( i < 3 && this.highScorers[i] != null) {
					g.drawString("Name: " + this.highScorers[i] + ", Score: " + this.highScores[i],
							100, 160 + (i * 30));
					i++;
				}
			}
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}
	
	void tick() {
		if(this.count % 6 == 0) {
			if(strong.isAlive()) {
				strongAttackerMove();
			}
			if (weak.isAlive()) {
				weakAttackerMove();
			}
		}
		if(!this.playing) {
			if(!this.updated) {
				updateHighScores();
				this.updated = true;
			}
			writeHighScores();
		}
		this.scoreLabel.setText("Score: " + this.player.getScore());
		this.count++;
		repaint();
	}
	
	public void reset() {
		this.count = 0;
		this.playing = true;
		this.win = false;
		this.lose = false;
		this.updated = false;
		this.arr = new GameObj[20][20];
		fillMaze();
		requestFocusInWindow();
	}
	
	
	/* helper function that fills the maze with game objects
	 *  so that they form a maze that the player can complete
	 */
	void fillMaze() {
		for (int i = 0; i < this.arr.length; i++) {
			for (int j = 0; j < this.arr[i].length; j++) {
				int num = (i * 20) + j;
				int x = j * 20;
				int y = i * 20;
				if (num == 0) {
					this.player = new Player(x, y);
					this.arr[i][j] = this.player;
				}
				else if (num == 145) {
					this.weak = new WeakAttacker(x, y, false);
					this.arr[i][j] = this.weak;
				}
				else if (num == 183) {
					this.strong = new StrongAttacker(x, y, false);
					this.arr[i][j] = this.strong;
				}
				else if((num > 31 && num < 35) || num == 52 || num == 54 || num == 72 || num == 74
						|| num == 78 || num == 92 || num == 94 || num == 98 || (num > 108 &&
						num < 113) || (num > 113 && num < 117) || num == 118 || num == 143 ||
						num == 144 || num == 157 || num == 158 || num == 258 || num == 278 ||
						num == 298 || (num > 308 && num < 312) || num == 318 || num == 329 ||
						num == 338 || num == 349 || (num > 362 && num < 366) || (j == 1 &&
						i > 9 && i < 19)) {
					this.arr[i][j] = new Coin(x, y);
				}
				else if ((j == 0 && i != 0) || (j == 19 && i != 19) || num == 4 || num == 5 ||
						(num > 6 && num < 22) || num == 24 || num == 25 || (num > 26 && num < 32) ||
						num == 35 || (num > 38 && num < 42) || num == 50|| (num/10 == 5 && num % 2
						== 1) || (num > 59 && num < 64) || num == 65 || num == 66 || num == 69 ||
						num == 70 || (num/10 == 7 && num % 2 == 1) || num == 85 || num == 86 ||
						(num > 87 && num < 92) || (num/10 == 9 && num % 2 == 1) || num == 113 ||
						num == 117 || (num > 121 && num < 126) || (num > 126 && num < 139) || (i > 6
						&& j == 2) || (num > 162 && num < 179 && num != 169) || (num > 203 &&
						num < 217) || (i > 9 && i < 19 && j == 17) || num == 224 || (i > 11 && j ==
						6) || (num > 246 && num < 255) || (i > 11 && i < 17 && j == 15) || (i>12 &&
						i < 18 && j == 4) || (i > 13 && i < 18 && (j == 8 || j == 12 || j == 13)) ||
						(num > 288 && num<292) || num == 330 || num == 331 || num == 350 ||
						num == 351 || num == 358 || (num>372 && num<379) || (num>380 && num<392)) {
					this.arr[i][j] = new Solid(x, y);
				}
				else {
					this.arr[i][j] = new Empty(x, y);
				}
			}
		}
	}
	
}
