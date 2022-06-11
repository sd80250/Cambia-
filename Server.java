import java.net.*;
import java.io.*;

public class Server {
	private ServerSocket serverSocket = null;
	private DataInputStream in = null;
	private boolean[] playersStatus;

	public void start(int port) {
		GameState gameState = new GameState();
		gameState.createGame();
		playersStatus = new boolean[gameState.getNumPlayers()];

		try {
			serverSocket = new ServerSocket(port);
			int numClients = 0;
			while (numClients < gameState.getNumPlayers()) {
				new ClientHandler(serverSocket.accept(), gameState.getPlayer(numClients), playersStatus).start();
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
		private Player player;
		private boolean[] playersStatus;

		public ClientHandler(Socket socket, Player player, boolean[] playersStatus) {
			this.clientSocket = socket;
			this.player = player;
			this.playersStatus = playersStatus;
		}

		public void run() {
			try {
				System.out.println("Client connected.");
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				askForName();

				while (notEveryoneIsReady()) {

				}


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

		private void askForName() {
			try {
				out.println("ask What is your name?");
				player.setName(in.readLine()); // sets the name for player
				System.out.println("Name for player " + player.getPlayerID() + " is " + player.getName());
			} catch (IOException e) {
				System.out.println(e);
			}
		}

		private boolean notEveryoneIsReady() { // returns true if all the players are not yet ready
			for (boolean playerStatus : playersStatus) {
				if (!playerStatus) {
					return true;
				}
			}
			return false;
		}
	}


	public static void main(String[] args) {
		Server server = new Server();
		server.start(5000);
	}
}