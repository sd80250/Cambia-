import java.net.*;
import java.io.*;

public class Server {
	private ServerSocket serverSocket = null;
	private DataInputStream in = null;

	public void start(int port) {
		GameState gameState = new GameState();
		gameState.createGame();

		try {
			serverSocket = new ServerSocket(port);
			int numClients = 0;
			Player[] players = new Player[gameState.getNumPlayers()];
			while (numClients < gameState.getNumPlayers()) {
				new ClientHandler(serverSocket.accept()).start();
				players[numClients].setPlayersID(numClients);

				numClients++;
			}
			System.out.println("No more players can enter");
			System.out.println("Game starting...");
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

	private static class ClientHandler extends Thread {
		private Socket clientSocket;
		private PrintWriter out;
		private BufferedReader in;

		public ClientHandler(Socket socket) {
			this.clientSocket = socket;
		}

		public void run() {
			try {
				System.out.println("Client connected.");
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				out.println("ask What is your name?");
				String name = in.readLine();
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