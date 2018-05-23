// Yili Liu
// [Tetris]
// Frame.java

import javax.swing.JFrame;

public class Tetris extends JFrame{

	private static final long serialVersionUID = 1L;

	Panel panel;
	Block cur; // current block
	Block next; // upcoming block

	int lastMoved = 1000; // last time block auto moved

	// constructor
	public Tetris(Panel p) {
		panel = p;
	}

	public void toGrid() {

		if (cur.cx != 0) { // if the block is not at the top
			while (cur.prevr.size() > 0) {
				int r = cur.prevr.pop();
				int c = cur.prevc.pop();
				
				panel.grid[r][c] = 0; // reset to white
			}
			
//			// set center piece to white
//			panel.grid[x][y] = 0;
//			for (int i = 0; i < cur.adjr.size(); i++) {
//				int r = cur.adjr.get(i);
//				int c = cur.adjc.get(i);
//
//				// add all adj squares
//				panel.grid[x + r][y + c] = 0; // reset to white
//			}
		}

		// if it's been a sec, block goes down
		if (lastMoved >= 700) {
			lastMoved = 0; // reset last
			cur.cx++;
		}

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
		Panel panel = new Panel();
		Tetris frame = new Tetris(panel);

		frame.setVisible(true); // set visible
		frame.setSize(500, 823); // extra 100 px width for "NEXT"
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel); // add panel

		// testing
		Block l = new Block(3);
		frame.cur = l;

		panel.setBlock(frame.cur);
		panel.keyInput.setBlock(frame.cur);
		panel.keyInput.setPanel(panel);

		// game loop
		frame.loop();
	}
}
