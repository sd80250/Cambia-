import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	private ServerSocket serverSocket = null;
	private DataInputStream in = null;
	private boolean[] playersStatus;
	private Player player;
	private GameState gameState;

	public void start(int port) {
		gameState = new GameState();
		gameState.createGame();
		playersStatus = new boolean[gameState.getNumPlayers()];

		try {
			serverSocket = new ServerSocket(port);
			int numClients = 0;
			while (numClients < gameState.getNumPlayers()) {
				new ClientHandler(serverSocket.accept(), gameState.getPlayer(numClients), playersStatus, gameState).start();
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
		private GameState gameState;
		private int id;

		public ClientHandler(Socket socket, Player player, boolean[] playersStatus, GameState gameState) {
			this.clientSocket = socket;
			this.player = player;
			this.playersStatus = playersStatus;
			this.gameState = gameState;
			id =  player.getPlayerID();
		}

		public void run() {
			try {
				System.out.println("Client connected.");
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

				askForName();

				/*while (notEveryoneIsReady()) {

				}*/


				String inputLine;
				while ((inputLine = in.readLine())!= null) {
					if ("Over".equals(inputLine)) {
						out.println("bye");
						break;
					}
					if ("hi".equals(inputLine)) {
						out.println("shut up");
					}
					if ("flipMyCard".equals(inputLine)) {
						out.println("Ask which card do you want to flip");
						int cardFlipped = Integer.parseInt(in.readLine());
						out.println(id);
						try {
							gameState.flipMyCard(id, cardFlipped);
						} catch (NullPointerException i){
							out.println("lol retart");
							out.println("guys this dude tried to flip on a null card");
							out.println(id);
							//player.drawCard(gameState.getDrawPileTopCard());
							gameState.punishDraw(id);
						}
					}
					if ("flipOtherCard".equals(inputLine)) {
						out.println("Ask which player's card do you want to flip");
						int otherDude = Integer.parseInt(in.readLine());
						out.println("Ask which card do you want to flip");
						int cardFlipped = Integer.parseInt(in.readLine());

						try {
							gameState.flipOtherCard(player.getPlayerID(), otherDude, cardFlipped);
						} catch (NullPointerException i){
							out.println("Sorry can't grab null cards");
							gameState.punishDraw(id);
						} catch (ArrayIndexOutOfBoundsException i) {
							out.println("u stupid");
							gameState.punishDraw(id);
						}
					}
					if ("displayMyDeck".equals(inputLine)) {
						out.println(player);
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
				playersStatus[player.getPlayerID()]=true;
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