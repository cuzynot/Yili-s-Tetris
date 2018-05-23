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

	// makes the block stay within grid
	public void withinGrid() {
		boolean in = false;

		while (!in) { // keep testing until it stays within bounds	
			in = true;

			int x = cur.cx;
			int y = cur.cy;

			if (x > 19) {
				in = false;
				cur.cx--;
				continue;
			} else if (y < 0) {
				in = false;
				cur.cy++;
				continue;
			} else if (y > 9) {
				in = false;
				cur.cy--;
				continue;
			}

			for (int i = 0; i < cur.adjr.size(); i++) {
				int r = cur.adjr.get(i);
				int c = cur.adjc.get(i);

				if (x + r > 19) {
					in = false;
					cur.cx--;
					break;
				} else if (y + c < 0) {
					in = false;
					cur.cy++;
					break;
				} else if (y + c > 9) {
					in = false;
					cur.cy--;
					break;
				}
			}
		}
	}

	public void toGrid() {
		// ERASE PREVIOUS
		int pr = cur.prevr.pop();
		int pc = cur.prevc.pop();
		panel.grid[pr][pc] = 0; // reset to white

		if (cur.cx != 0) { // if the block is not at the top
			while (cur.prevr.size() > 0) {
				int r = cur.prevr.pop();
				int c = cur.prevc.pop();

				panel.grid[r][c] = 0; // reset to white
			}
		}

		// if it's been a sec, block goes down
		if (lastMoved >= 700) {
			lastMoved = 0; // reset last
			cur.cx++;
		}

		withinGrid();
		//		if (!withinGrid()) {
		//			cur.cx = pr;
		//			cur.cy = pc;
		//		}

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

		// testing
		Block b = new Block(1);
		frame.cur = b;

		frame.panel.setBlock(frame.cur);
		frame.panel.keyInput.setBlock(frame.cur);
		frame.panel.keyInput.setPanel(frame.panel);

		// game loop
		frame.loop();
	}
}
