// Yili Liu
// [Tetris]
// KeyInput.java

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener{

	Tetris frame; // instance of frame
	Panel panel; // instance of panel
	Block cur; // instance of current block

	// SET INSTANCES
	public void setFrame(Tetris t) {
		frame = t;
	}

	public void setPanel(Panel p) {
		panel = p;
	}

	public void setBlock(Block c) {
		cur = c;
	}

	// key inputs
	public void keyTyped(KeyEvent e) { }

	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();

		if (!cur.fixed) {
			// rotate
			if (k == KeyEvent.VK_UP) {
				cur.rotate();

				// left
			} else if (k == KeyEvent.VK_LEFT) {
				cur.cy--;
				cur.erasePrevious();
				boolean reachedLeft = cur.checkLeft();

				if (reachedLeft) {
					System.out.println("reachedLeft");
					cur.cy++;
				}

				// down
			} else if (k == KeyEvent.VK_DOWN) {
				cur.cx++;
				frame.lastMoved = 0; // reset last moved

				// right
			} else if (k == KeyEvent.VK_RIGHT) {
				cur.cy++;
				cur.erasePrevious();
				boolean reachedRight = cur.checkRight();

				if (reachedRight) {
					System.out.println("reachedRight");
					cur.cy--;
				}

				// hard drop
			} else if (k == KeyEvent.VK_SPACE) {
				cur.erasePrevious();
				while (!cur.withinBounds()) {
					cur.cx++;
				}
				
				cur.fixed = true;
				frame.lastMoved = 700;
				
				// hold
			} else if (k == KeyEvent.VK_C || k == KeyEvent.VK_TAB) {
				cur.erasePrevious();
				int held = frame.hold;
				
				if (held == 0) {
					frame.hold = cur.shape; // hold block
					frame.newBlock();
					frame.lastMoved = 700;
					
				} else if (!cur.switched) {
					frame.hold = cur.shape; // hold block
					frame.newBlock(held);
					cur.switched = true;
					frame.lastMoved = 700;
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {}

}
