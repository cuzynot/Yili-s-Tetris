import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener{

	Panel panel; // instance of panel
	Block cur; // instance of current block

	public void setBlock(Block c) {
		cur = c;
	}

	public void setPanel(Panel p) {
		panel = p;
	}

	// key inputs
	public void keyTyped(KeyEvent e) {
		char key = e.getKeyChar();

		if (!cur.fixed) {
			if (key == 'w') {
				cur.rotate();
			} else if (key == 'a') {
				cur.cy--;
				cur.erasePrevious();
				boolean reachedLeft = cur.checkLeft();
				
				if (reachedLeft) {
					System.out.println("reachedLeft");
					cur.cy++;
				}
			} else if (key == 's') {
				cur.cx++;
			} else if (key == 'd') {
				cur.cy++;
				cur.erasePrevious();
				boolean reachedRight = cur.checkRight();
				
				if (reachedRight) {
					System.out.println("reachedRight");
					cur.cy--;
				}
			} else if (key == ' ') {
				cur.erasePrevious();
				while (!cur.withinBounds()) {
					cur.cx++;
				}
				cur.fixed = true;
			}
		}
	}

	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}





}
