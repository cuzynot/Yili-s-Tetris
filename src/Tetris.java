// Yili Liu
// [Tetris]
// Frame.java

import javax.swing.JFrame;

public class Tetris extends JFrame{

	private static final long serialVersionUID = 1L;

	Panel panel; // panel obj
	Block cur; // current block
	Block next; // upcoming block
	int lastMoved = 1000; // last time block auto moved

	// constructor
	public Tetris() {
		panel = new Panel();
	}

	public void toGrid() {
		cur.erasePrevious();
		
		// if it's been a sec, block goes down
		if (lastMoved >= 700) {
			lastMoved = 0; // reset last
			cur.cx++;
		}

		// makes sure the block stays within the grid
		boolean reachedBottom = cur.withinBounds();

		int x = cur.cx;
		int y = cur.cy;

		// set center piece to shape
		panel.grid[x][y] = cur.shape;
		cur.prevr.add(x);
		cur.prevc.add(y);

		for (int i = 0; i < cur.adjr.size(); i++) {
			int r = cur.adjr.get(i);
			int c = cur.adjc.get(i);

			// add all adj squares
			panel.grid[x + r][y + c] = cur.shape; // cur.shape corresponds to its colour

			// add coordinates to prevr and prevr lists
			cur.prevr.add(x + r);
			cur.prevc.add(y + c);
		}

		if (reachedBottom) {
			newBlock();
			cur.cx++;
		}
	}
	
	public void newBlock() {
		// create new block
		cur = new Block((int)(Math.random() * 7 + 1));
		panel.setBlock(cur);
		panel.keyInput.setBlock(cur);
		cur.setPanel(panel);
	}

	// game loop
	public void loop() {
		while (true) {
			try {
				Thread.sleep(50);
				lastMoved += 50;
			} catch (InterruptedException e){
				e.printStackTrace();
			}

			// translate to the grid
			toGrid();

			// repaint the panel in the loop
			panel.repaint();
		}
	}



	// MAIN METHOD //
	public static void main (String[] args) {
		Tetris frame = new Tetris();

		frame.setVisible(true); // set visible
		frame.setSize(500, 823); // extra 100 px width for "NEXT"
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(frame.panel);
		frame.panel.keyInput.setPanel(frame.panel);

		// testing
		frame.newBlock();


		// game loop
		frame.loop();
	}
}
