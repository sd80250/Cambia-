import java.util.*;
import java.util.Map.Entry;
class GameState {
	private Player[] players;
	private Deck drawPile;
	private Card topDiscardCard;
	private Player cambiaCaller;
	private Player currentPlayer;

	public void createGame() {
		//make a deck of cards
		List<Card> deck1 = new ArrayList<>();
		for (int i = 4; i < 56; i+=4) {
			deck1.add(new Card(i/4, 0));
			deck1.add(new Card(i/4, 1));
			deck1.add(new Card(i/4, 2));
			deck1.add(new Card(i/4, 3));
		}
		Collections.shuffle(deck1);
		drawPile = new Deck(deck1);

		//create players
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter number of players");
		int a = scan.nextInt();
		if (a < 1 || a > 13) {
			throw new IllegalArgumentException("f");
		}
		players = new Player[a];
		for (int i = 0; i < players.length; i++) {
			List<Card> playerdeck = new ArrayList<>();
			for (int j = 0; j < 4; j++) {
				playerdeck.add(drawPile.getCard(0));
				drawPile.remove(0);
			}
			players[i] = new Player(playerdeck);
		}

		//show bottom 2 cards
		for (Player player : players) {
			System.out.println(player.getName());
			System.out.println(player.getPlayerHand().getCardList().get(2));
			System.out.println(player.getPlayerHand().getCardList().get(3));
		}


		declareWinner(); //testing purposes
	}

	public boolean isCambiaCalled() {
		return currentPlayer != null;
	}

	public Player whoCalledCambia() {
		return cambiaCaller;
	}
	

	// terminal methods
	private void decideOnMove(boolean isCambiaCalled) {
		Scanner turnScanner = new Scanner(System.in);
		System.out.print("What is your next move? K: Draw from deck. D: Draw from discard pile. ");
		if (isCambiaCalled) {
			System.out.println();
		} else { // if Cambia isn't called, then add this as an option
			System.out.println("C: Call Cambia.");
		}

		String nextMoveInput = turnScanner.nextLine();

		switch (nextMoveInput) {
			case CALL_CAMBIA:
				if (!isCambiaCalled) {
					callCambia();
					break;
				} // if Cambia is already called, then the program should go to the default case.
			case DRAW_FROM_DECK:
				drawFromDeck();
				break;
			case DRAW_FROM_DISCARD:
				drawFromDiscard();
				break;
			default:
				System.out.print("That is not a valid option. ");
				decideOnMove(isCambiaCalled); // recursive	
		}
	}
	private final String CALL_CAMBIA = "C";
	private final String DRAW_FROM_DECK = "K";
	private final String DRAW_FROM_DISCARD = "D";

	// game state methods
	private void callCambia() {

	}
	private void drawFromDeck() {}
	private void drawFromDiscard() {
		
	}

	// end of turn methods
	private void endTurn() {

	}

	// end of game methods
	private void declareWinner() { // TODO: test
		int minPlayerScore = Integer.MAX_VALUE;
		Player winner = null;
		Map<Player, Integer> results = new HashMap();



		// get score from each player
		for (Player player : players) {
			Deck playerHand = player.getPlayerHand();
			int playerScore = 0;
			for (Card card : playerHand.getCardList()) {
				playerScore += card.getValue();
			}
			if (playerScore < minPlayerScore) {
				minPlayerScore = playerScore;
				winner = player;
			}
			results.put(player, playerScore);
		}

		// sort players by score
		List<Entry<Player, Integer>> resultsList = new ArrayList<>(results.entrySet());
		resultsList.sort(Entry.comparingByValue());

		// display hand, winner, and ranking by score
		for (Player player : players) {
			System.out.println(player);
		}
		System.out.println(winner.getName() + " is the winner with a score of " + minPlayerScore + ".");
		System.out.println("Here are the results:");
		for (Entry e : resultsList) {
			System.out.println(((Player) e.getKey()).getName() + "-" + e.getValue());
		}
	}

	private void endGame() {
		System.out.println("Would you like to play another round?");
		// continues onto next round
	}

	public void turn() { // TODO: test
		System.out.println("Draw Pile Size:" + drawPile.size());
		for (Player player : players) {
			System.out.println(player.getName() + ":" + 
							   player.getPlayerHand().size());
		}
		// print the state of the game

		if (isCambiaCalled()) { 
			if (whoCalledCambia().equals(currentPlayer)) {
				declareWinner();
				endGame();
			} else {
				decideOnMove(true);
			}
		} else {
			decideOnMove(false);
		}
	}
}