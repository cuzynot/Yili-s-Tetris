import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Server {

	Scanner in = new Scanner(System.in);
	ServerSocket serverSocket;
	Socket client;
	Scanner sc;
	PrintStream ps;

	// user input
	String ip;
	int port;
	boolean established = false;

	public Server (boolean serverMaker, Tetris t) {

		// GUI
		JFrame sf = new JFrame(); // server frame
		JPanel sp = new JPanel(); // server panel
		JLabel l1 = new JLabel(); // label 1
		JLabel l2 = new JLabel(); // label 2

		// init sf
		sf.setTitle("testing server");
		sf.setVisible(true);
		sf.setSize(500, 300);
		sf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// if player is the server maker
		if (serverMaker){
			l1.setText("Please input the port number");
			JTextField pt = new JTextField("0000", 4); // port text field

			// add components to panel
			sp.add(l1);
			sp.add(pt);
			sp.add(l2);

			System.out.println("added comps");

			pt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					port = Integer.parseInt(pt.getText());

					if (!established) {
						try {
							serverSocket = new ServerSocket(port);
							// established = true;

							l2.setText("port established");

							// accept client connection
							client = serverSocket.accept();
							l2.setText("ACCEPTED");

							established = true;
							
							sc = new Scanner(client.getInputStream());
							ps = new PrintStream(client.getOutputStream());

							try {
								Thread.sleep(1000);
							} catch (InterruptedException i) { }

							// dispose frame when established connection with client
							sf.dispose();

							synchronized(t.input) {
								// notify input thread
								t.input.notify();
								System.out.println("SERVER ESTABLISHED");
							}

						} catch (IOException i){
							l2.setText("could not listen on port " + port);
						}
					}
				}
			});

			// add panel to frame
			sf.add(sp);
			System.out.println("added panel");

			// if player is joining the server
		} else {
			l1.setText("Please input the ip address");
			JTextField it = new JTextField("----", 30); // ip text field
			l2.setText("Please input the port number");
			JTextField pt = new JTextField("0000", 4); // port text field

			// add components to panel
			sp.add(l1);
			sp.add(it);
			sp.add(l2);
			sp.add(pt);

			it.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ip = it.getText();

					if (!established) {
						try {
							client = new Socket(ip, port);
							established = true;

							l1.setText("client established");
							l2.setText("client established");

							sc = new Scanner(client.getInputStream());
							ps = new PrintStream(client.getOutputStream());

							try {
								Thread.sleep(1000);
							} catch (InterruptedException i) { }

							// dispose frame when established connection with server
							sf.dispose();

							synchronized(t.input) {
								// notify input thread
								t.input.notify();
								System.out.println("SERVER ESTABLISHED");
							}

						} catch (IOException i){
							l2.setText("incorrect ip address or port number");
						}
					}
				}
			});

			pt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					port = Integer.parseInt(pt.getText());

					if (!established) {
						try {
							client = new Socket(ip, port);
							established = true;

							l1.setText("client established");
							l2.setText("client established");

							sc = new Scanner(client.getInputStream());
							ps = new PrintStream(client.getOutputStream());

							try {
								Thread.sleep(1000);
							} catch (InterruptedException i) { }

							// dispose frame when established connection with server
							sf.dispose();

							synchronized(t.input) {
								// notify input thread
								t.input.notify();
								System.out.println("SERVER ESTABLISHED");
							}

						} catch (IOException i){
							l2.setText("incorrect ip address or port number");
						}
					}
				}
			});

			// add panel to frame
			sf.add(sp);
		}

	}

	// testing
	public static void main (String[] args) throws IOException, InterruptedException{
		// new Server(false);
	}
}