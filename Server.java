import java.net.*;
import java.io.*;

public class Server {
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
				System.out.println("Client connected.");
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				String inputLine;
				while ((inputLine = in.readLine())!= null) {
					if ("Over".equals(inputLine)) {
						out.println("bye");
						break;
					}
					if ("hi".equals(inputLine)) {
						out.println("shut up");
					}
					System.out.println(inputLine);
				}

				System.out.println("Client disconnected.");

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