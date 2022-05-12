import java.net.*;//networking files
import java.io.*;//i/p,o/p files

public class Server {
	ServerSocket server;// ServerSocket is class and server is object
	Socket socket;// Socket
	BufferedReader br;
	PrintWriter out;

	// constructor
	public Server() {

		try {
			server = new ServerSocket(7008);// creating object of server,specify port
			System.out.println("server is ready to accept connection");
			System.out.println("waiting..");
			socket = server.accept();// when the server will accept request from client object of socket is created

			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out = new PrintWriter(socket.getOutputStream());
			startReading();
			startWriting();
		} catch (Exception e) {
			e.printStackTrace();// to print errors

		}
	}

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

		System.out.println("this is a server..going to start server");
		new Server();
	}

}
