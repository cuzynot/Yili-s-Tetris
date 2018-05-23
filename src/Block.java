// Yili Liu
// [Tetris]
// Block.java

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Block {

	int[][] filled;
	int shape;
	int cx; // center x
	int cy; // center y

	// adj blocks
	ArrayList<Integer> adjr;
	ArrayList<Integer> adjc;

	// previous blocks that need to be erased every loop
	LinkedList<Integer> prevr;
	LinkedList<Integer> prevc;

	public Block(int s) {
		shape = s;

		cx = 0; // center always starts from the top
		cy = 4; // center always starts at the middle

		// init adj and previous blocks
		adjr  = new ArrayList<Integer>();
		adjc  = new ArrayList<Integer>();

		prevr  = new LinkedList<Integer>();
		prevr.add(cx);
		prevc  = new LinkedList<Integer>();
		prevc.add(cy);


		// ` represents adj blocks
		// * represents center block

		// 1
		// `
		// *
		// `
		// `
		if (shape == 1) {
			adjr.add(-1);
			adjc.add(0);
			adjr.add(1);
			adjc.add(0);
			adjr.add(2);
			adjc.add(0);
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
		// `
		// * `
		//   `
		else if (shape == 4) {
			adjr.add(-1);
			adjc.add(0);
			adjr.add(0);
			adjc.add(1);
			adjr.add(1);
			adjc.add(1);
		}

		// 5
		//   `
		// * `
		// `
		else if (shape == 5) {
			adjr.add(-1);
			adjc.add(1);
			adjr.add(0);
			adjc.add(1);
			adjr.add(1);
			adjc.add(0);
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

	public void testRotate() {
		int[][] alt = new int[filled[0].length][filled.length];

		if (shape % 2 == 1) { // if shape is 1, 3, or 5, turn clockwise
			for (int i = 0; i < filled[0].length; i++) {
				for (int j = filled.length - 1; j >= 0; j--) {
					alt[i][j] = filled[j][i];
				}
			}

		} else { // turn back to 1, 3, or 5
			for (int i = 0; i < filled[0].length; i++) {
				for (int j = filled.length - 1; j >= 0; j--) {
					alt[i][j] = filled[j][i];
				}
			}
		}

		for (int i = 0; i < filled.length; i++) {
			System.out.println(Arrays.toString(filled[i]));
		}
		System.out.println();
		for (int i = 0; i < alt.length; i++) {
			System.out.println(Arrays.toString(alt[i]));
		}

		// reset
		for (int i = 0; i < filled.length; i++) {
			Arrays.fill(filled[i], 0);
		}

		// set filled to alt
		for (int i = 0; i < filled.length; i++) {
			for (int j = 0; j < filled[0].length; j++) {
				filled[i][j] = alt[j][i];
			}
		}


	} //

}