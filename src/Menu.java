import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu {
	
	Server server;

	Menu(Tetris t){
		JFrame menu = new JFrame();
		JPanel panel = new JPanel();
		JButton makeServer = new JButton("Make a Server");
		JButton joinServer = new JButton("Join a Server");
		
		menu.setVisible(true);
		menu.setSize(200, 150);
		menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		makeServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.server = new Server(true, t);
			}
		});
		
		joinServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				t.server = new Server(false, t);
			}
		});
		
		panel.add(makeServer);
		panel.add(joinServer);
		menu.add(panel);
	}
}
