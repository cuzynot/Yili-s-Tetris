// Yili Liu
// [Tetris]
// Frame.java

import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.JFrame;

public class Tetris extends JFrame{

	private static final long serialVersionUID = 1L;

	Panel panel; // panel obj
	Block cur; // current block
	Server server; // server to communicate to client
	Opponent opp;

	int next; // upcoming block
	int hold = 0; // held block
	int lastMoved = 800; // last time block auto moved
	boolean game_over = false; // game over

	Thread input;

	// constructor
	public Tetris() {
		panel = new Panel();
	}

	public void toGrid() {
		cur.erasePrevious();

		// if it's been a sec, block goes down
		if (lastMoved >= 800) {
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

			// check for cleared lines
			clear();

			if (gameover()) {
				game_over = true;
				panel.repaint();

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {}


				System.out.println("GAME OVER");
				System.exit(0);
			}
		}
	}

	public boolean gameover() {
		for (int i = 0; i < 10; i++) {
			if (panel.grid[2][i] != 0) { // search through the top row
				return true;
			}
		}

		return false;
	}

	public void clear() {
		LinkedList<Integer> uncleared = new LinkedList<Integer>();

		int firstCleared = -1;
		for (int i = 21; i >= 2; i--) {
			boolean empty = true;
			boolean full = true;

			for (int j = 0; j < 10; j++) {
				if (panel.grid[i][j] == 0) {
					full = false;
					// break;
				} else {
					empty = false;
				}
			}

			if (empty) break;
			else if (full) {
				System.out.println("line cleared at " + i);

				// erase line
				Arrays.fill(panel.grid[i], 0);

				if (firstCleared == -1){
					firstCleared = i;
				}
			} else if (firstCleared != -1 && !empty) { // a line is already cleared
				uncleared.add(i);
			}
		}

		// move uncleared lines down
		while (uncleared.size() > 0) {
			int row = uncleared.pop();

			panel.grid[firstCleared] = panel.grid[row].clone();
			Arrays.fill(panel.grid[row], 0);

			firstCleared--;
		}
	}

	// when new block is used
	public void newBlock() {
		// create new block
		cur = new Block(next);
		panel.setBlock(cur);
		panel.keyInput.setBlock(cur);
		cur.setPanel(panel);

		next = (int)(Math.random() * 7 + 1);
	}

	// when switched with hold
	public void newBlock(int shape) {
		// create new block
		cur = new Block(shape);
		panel.setBlock(cur);
		panel.keyInput.setBlock(cur);
		cur.setPanel(panel);
	}

	// send info through server
	void sendInfo() {
		server.ps.println(next);
		server.ps.println(hold);
		
		for (int i = 0; i < 22; i++) {
			for (int j = 0; j < 10; j++) {
				server.ps.println(panel.grid[i][j]);
			}
		}
	}

	// GAME LOOP
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

			// send next, hold, and grid info to opponent
			sendInfo();

			// repaint the panel in the loop
			panel.repaint();
		}
	}



	// MAIN METHOD //
	public static void main (String[] args) {
		Tetris frame = new Tetris();

		frame.opp = new Opponent();
		
		// init frame 
		frame.setTitle("Tetris");
		frame.setVisible(true); // set visible
		frame.setSize(1000, 823); // extra 100 px width for "NEXT"
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(frame.panel);

		// set up instances in panel and keyInput
		frame.panel.setFrame(frame);
		frame.panel.keyInput.setPanel(frame.panel);
		frame.panel.keyInput.setFrame(frame);

		// setup next block
		frame.next = (int)(Math.random() * 7 + 1); // first block
		frame.newBlock();
		frame.next = (int)(Math.random() * 7 + 1); // next block

		

		frame.server = new Server(true, frame);

		// constant check of the opponent
		frame.input = new Thread(new Runnable(){
			public void run(){
				while (frame.server.sc.hasNextLine() && !frame.game_over){

					// receive info of next, hold and opponent's 2d array
					frame.opp.next = frame.server.sc.nextInt();
					frame.opp.next = frame.server.sc.nextInt();
					
					for (int i = 0; i < 22; i++) {
						for (int j = 0; j < 10; j++) {
							frame.opp.grid[i][j] = frame.server.sc.nextInt();
						}
					}
				}
			}
		});

		synchronized(frame.input) {
			try {
				frame.input.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		frame.input.start();
		
		
		// after server is connected, draw panel
		
		

		// game loop
		frame.loop();
	}
}
