// Yili Liu
// [Tetris]
// Block.java

import java.util.ArrayList;
import java.util.LinkedList;

public class Block {

	Panel panel;
	int shape;
	int cx; // center x
	int cy; // center y
	boolean fixed; // when hit the bottom, the block cannot move
	boolean switched; // when the block has already been switched with the held block

	// adj blocks
	ArrayList<Integer> adjr;
	ArrayList<Integer> adjc;

	// previous blocks that need to be erased every loop
	LinkedList<Integer> prevr;
	LinkedList<Integer> prevc;

	public Block(int s) {
		switched = false;
		shape = s;
		fixed = false;

		if (shape == 1 || shape == 4 || shape == 5) {
			cx = 1; // centers for 1 4 and 5 are higher up
		} else {
			cx = 2; // centers for 2 3 6 and 7 are one square below
		}
		
		cy = 4; // center always starts at the middle

		// init adj and previous blocks
		adjr = new ArrayList<Integer>();
		adjc = new ArrayList<Integer>();

		prevr = new LinkedList<Integer>();
		prevr.add(cx);
		prevc = new LinkedList<Integer>();
		prevc.add(cy);


		// ` represents adj blocks
		// * represents center block

		// 1
		// ` * ` `
		if (shape == 1) {
			adjr.add(0);
			adjc.add(-1);
			adjr.add(0);
			adjc.add(1);
			adjr.add(0);
			adjc.add(2);
		}

		// 2
		// `
		// ` * `
		else if (shape == 2) {
			adjr.add(-1);
			adjc.add(-1);
			adjr.add(0);
			adjc.add(-1);
			adjr.add(0);
			adjc.add(1);
		}

		// 3
		//     `
		// ` * `
		else if (shape == 3) {
			adjr.add(-1);
			adjc.add(1);
			adjr.add(0);
			adjc.add(-1);
			adjr.add(0);
			adjc.add(1);
		}

		// 4
		//   * `
		// ` `
		else if (shape == 4) {
			adjr.add(0);
			adjc.add(1);
			adjr.add(1);
			adjc.add(-1);
			adjr.add(1);
			adjc.add(0);
		}

		// 5
		// ` *
		//   ` `
		else if (shape == 5) {
			adjr.add(0);
			adjc.add(-1);
			adjr.add(1);
			adjc.add(0);
			adjr.add(1);
			adjc.add(1);
		}

		// 6
		//   `
		// ` * `
		else if (shape == 6) {
			adjr.add(-1);
			adjc.add(0);
			adjr.add(0);
			adjc.add(-1);
			adjr.add(0);
			adjc.add(1);
		}

		// 7
		// ` `
		// * `
		else if (shape == 7) {
			adjr.add(-1);
			adjc.add(0);
			adjr.add(-1);
			adjc.add(1);
			adjr.add(0);
			adjc.add(1);
		}
	}

	public void setPanel(Panel p) {
		panel = p;
	}

	// erase previous
	public void erasePrevious() {
		if (prevr.size() > 0) {
			int pr = prevr.pop();
			int pc = prevc.pop();
			panel.grid[pr][pc] = 0; // reset to white

			if (cx != 0) { // if the block is not at the top
				while (prevr.size() > 0) {
					int r = prevr.pop();
					int c = prevc.pop();

					panel.grid[r][c] = 0; // reset to white
				}
			}
		}
	}

	public boolean checkLeft() {
		int x = cx;
		int y = cy;

		if (y < 0) {
			System.out.println("left out of bounds");
			return true;
		}

		for (int i = 0; i < adjr.size(); i++) {
			int r = adjr.get(i);
			int c = adjc.get(i);

			if (y + c < 0 || panel.grid[x + r][y + c] != 0) {
				System.out.println("left adj has block at " + (x + r) + " " + (y + c));
				return true;
			}
		}

		return false;
	}

	public boolean checkRight() {
		int x = cx;
		int y = cy;

		if (y > 9) {
			System.out.println("right out of bounds");
			return true;
		}

		for (int i = 0; i < adjr.size(); i++) {
			int r = adjr.get(i);
			int c = adjc.get(i);

			if (y + c > 9 || panel.grid[x + r][y + c] != 0) {
				System.out.println("right adj has block at " + (x + r) + " " + (y + c));
				return true;
			}
		}

		return false;
	}

	// makes the block stay within grid
	public boolean withinBounds() {
		boolean in = false;
		boolean reachedBottom = false;

		while (!in) { // keep testing until it stays within bounds	
			in = true;

			int x = cx;
			int y = cy;

			if (y < 0) {
				in = false;
				cy++;
				continue;
			} else if (y > 9) {
				in = false;
				cy--;
				continue;
			} else if (x > 21 || panel.grid[x][y] != 0) {
				reachedBottom = true;
				in = false;
				cx--;
				continue;
			}

			for (int i = 0; i < adjr.size(); i++) {
				int r = adjr.get(i);
				int c = adjc.get(i);

				if (y + c < 0) {
					in = false;
					cy++;
					break;
				} else if (y + c > 9) {
					in = false;
					cy--;
					break;
				} else if (x + r > 21 || panel.grid[x + r][y + c] != 0) {
					reachedBottom = true;
					in = false;
					cx--;
					break;
				}
			}
		}

		return reachedBottom;
	}

	public void rotate() {
		if (shape != 7) { // if shape is o then do not rotate
			int size = adjr.size();

			for (int i = 0; i < size; i++) {
				int r = adjr.remove(0);
				int c = adjc.remove(0);

				// rotate every square
				adjc.add(-r);
				adjr.add(c);
			}
		}
	}

}