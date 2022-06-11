import java.net.*;
import java.io.*;

public class Server {
	private Socket socket = null;
	private ServerSocket serverSocket = null;
	private DataInputStream in = null;

	public void start(int port) {
		try {
			serverSocket = new ServerSocket(port);
			while (true) {
				new EchoClientHandler(serverSocket.accept()).start();
			}
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public void stop() {
		try {
			serverSocket.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	private static class EchoClientHandler extends Thread {
		private Socket clientSocket;
		private PrintWriter out;
		private BufferedReader in;

		public EchoClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		public void run() {
			try {
				System.out.println("running");
				out = new PrintWriter(System.out, true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				String inputLine;
				while ((inputLine = in.readLine())!= null) {
					if (".".equals(inputLine)) {
						out.println("bye");
						break;
					}
					out.println(inputLine);
				}

				in.close();
				out.close();
				clientSocket.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		}
	}


	public static void main(String[] args) {
		Server server = new Server();
		server.start(5000);
	}
}