// Yili Liu
// [Tetris]
// Panel.java

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Panel extends JPanel{

	private static final long serialVersionUID = 1L;

	// images of texts and block images
	BufferedImage next;
	BufferedImage hold;
	BufferedImage[] bimgs = new BufferedImage[7];

	Tetris frame; // instance of frame
	Block cur; // instance of current block
	int[][] grid; // tetris grid
	HashMap<Integer, Color> getColor; // used to convert the ints in the grid into colours
	KeyInput keyInput; // used to take keyboard input from user

	// constructor
	public Panel() {
		try {
			// read text images
			next = ImageIO.read(new File("next.png"));
			
			
			// read block images
			bimgs[0] = ImageIO.read(new File("b0.png"));
			bimgs[1] = ImageIO.read(new File("b1.png"));
			bimgs[2] = ImageIO.read(new File("b2.png"));
			bimgs[3] = ImageIO.read(new File("b3.png"));
			bimgs[4] = ImageIO.read(new File("b4.png"));
			bimgs[5] = ImageIO.read(new File("b5.png"));
			bimgs[6] = ImageIO.read(new File("b6.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		setFocusable(true);
		keyInput = new KeyInput(); // used to take keyboard input from user
		addKeyListener(keyInput); // add key listener

		// init getColor map
		getColor = new HashMap<Integer, Color>();
		getColor.put(0, Color.WHITE);
		getColor.put(1, Color.CYAN);
		getColor.put(2, Color.BLUE);
		getColor.put(3, Color.ORANGE);
		getColor.put(4, Color.GREEN);
		getColor.put(5, Color.RED);
		getColor.put(6, Color.MAGENTA);
		getColor.put(7, Color.YELLOW);

		// init grid
		grid = new int[22][10];
	}

	public void setFrame(Tetris t) {
		frame = t;
	}
	
	public void setBlock(Block c) {
		cur = c;
	}

	// DRAWING
	public void drawGrid(Graphics g) {
		// set background white
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 500, 823);

		// draw grid
		g.setColor(Color.GRAY);
		for (int i = 2; i < 22; i++) {
			for (int j = 0; j < 10; j++) {
				g.drawRect(j * 40, (i - 2) * 40, 40, 40);
			}
		}
	}

	public void drawBlocks(Graphics g) {
		for (int i = 2; i < 22; i++) {
			for (int j = 0; j < 10; j++) {
				// get colour for every square
				g.setColor(getColor.get(grid[i][j]));

				// fill the rectangle with the set colour
				g.fillRect(j * 40 + 1, (i - 2) * 40 + 1, 39, 39);
			}
		}
	}

	public void drawNext(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawImage(next, 430, 30, 50, 50, null);
		g.drawImage(bimgs[frame.next - 1], 410, 100, 70, 50, null);
	}

	// paint
	public void paintComponent(Graphics g) {
		drawBlocks(g);
		drawNext(g);
	}

}
