import java.io.*;
import java.net.*;

//import java.awt.*;
//import javax.swing.*;
//
public class Client // extends JFrame {
{
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	// declare components
	// 192.168.1.9
	// private JLabel heading = new JLabel("Client Area");
	// private JTextArea messageArea = new JTextArea();
	// private JTextField messageInput = new JTextField();
	// 192.168.60.120

	public Client() {
		try {
			System.out.println("Sending request to server");
			socket = new Socket("localhost", 7008);
			System.out.println("Connection Done");

			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out = new PrintWriter(socket.getOutputStream());
			// createGUI();
			startReading();
			startWriting();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// private void createGUI() {
	// this.setTitle("Client Massanger[END]");
	// this.setSize(500, 500);
	// this.setLocationRelativeTo(null);
	// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// this.setVisible(true);
	// }

	// start reading
	public void startReading() {
		// thread-data ko read karke deta rahega
		// lambda expression
		Runnable r1 = () -> {
			System.out.println("reader started");
			while (true) {
				try {
					String msg = br.readLine();
					if (msg.equals("exit")) {
						System.out.println("Client terminated the chat");
						break;
					}
					System.out.println("Client:" + msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(r1).start();
	}

	// start writing
	public void startWriting() {
		// thread-data user se lega or usko send karega client tak
		Runnable r2 = () -> {
			System.out.println("Writer started");
			while (true)
				try {
					BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
					String content = br1.readLine();
					out.println(content);
					out.flush();

				} catch (Exception e) {
					e.printStackTrace();
				}
		};
		new Thread(r2).start();
	}

	public static void main(String[] args) {

		System.out.println("This is client...");
		new Client();

	}

}