import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Game implements Runnable {
	public void run() {
		final String PATH_TO_HIGH_SCORES = "files/highscores.txt";
		final JFrame frame = new JFrame("Magic Maze");
		frame.setLocation(100, 100);
		
		displayInstructions(frame);
		String playerName = askForName();
		
		//add panel to display score
		int startScore = 0;
		final JPanel score_panel = new JPanel();
        frame.add(score_panel, BorderLayout.SOUTH);
		final JLabel score = new JLabel("Score: " + startScore);
        score_panel.add(score);
        
        final GameArray game = new GameArray(score, playerName, PATH_TO_HIGH_SCORES);
        frame.add(game, BorderLayout.CENTER);
        
		game.setBounds(0, 0, 400, 400);
		game.setFocusable(true);
		
		//add panel that contains reset button
		final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                game.reset();
            }
        });
        control_panel.add(reset);
		
		frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        score.setVisible(true);
        score_panel.setVisible(true);
        control_panel.setVisible(true);
		game.setVisible(true);
		frame.setVisible(true);
		
		//start the game
		game.reset();
	}
	
	//helper method to ask for player's name
	private static String askForName() {
		String playerName = JOptionPane.showInputDialog("Enter your name:");
		while (playerName == null || playerName.equals("")) {
			playerName = JOptionPane.showInputDialog("You must enter a valid name:");
		}
		return playerName;
	}
	
	//helper method to display instructions to player
	private static void displayInstructions(JFrame frame) {
		String inst = "How to play the Magic Maze:" + 
				"\n1. The goal is to get through the maze (you can't move in the grass),"
				+ "\nfrom the upper left to the bottom right, and also get the highest score"
				+ "possible."
				+ "\nYour character is the ninja warrior in the upper left."
				+ "\n2. Move through the maze using arrow keys."
				+ "\n3. Collect as many coins as you can to get your score up."
				+ "\n4. There are two attackers: if Scar "
				+ "attacks you, your score will go down by 10 points"
				+ "\n(negative scores are possible!) but you will kill him as soon as you touch him."
				+ "\n5. If Ursula attacks you, then you die and the"
				+ " game is over"
				+ "\n6. You can restart the game whenever you want."
				+ "\n7. After playing, you will be able to see all high scores."
				+ "\n8. Good luck!";
		JOptionPane.showMessageDialog(frame, inst, "Instructions", 1);
	}
	
	//main method
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
