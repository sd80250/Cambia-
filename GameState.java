import java.util.*;
import java.util.Map.Entry;
import java.io.PrintWriter;
class GameState {
	private Player[] players;
	private Deck drawPile;
	private Card topDiscardCard;
	private Player cambiaCaller;
	private Player currentPlayer;
	private int numPlayers;
	private int clientID;
	private Map<Integer, Player> idPlayerMap;

	public int getNumPlayers() {
		return numPlayers;
	}
	public Card getDrawPileTopCard() {
		return drawPile.getCard(0);
	}

	public Player getPlayer(int playerID) {
		return players[playerID];
	}
	
	public void createGame() {
		shuffleAndSetDrawPile();
		askNumPlayers();
		createPlayers();
		setRandomCurrentPlayer();
	}

	private void shuffleAndSetDrawPile() {
		// make a deck of cards
		List<Card> deck1 = new ArrayList<>();
		for (int i = 4; i < 56; i+=4) {
			deck1.add(new Card(i/4, 0));
			deck1.add(new Card(i/4, 1));
			deck1.add(new Card(i/4, 2));
			deck1.add(new Card(i/4, 3));
		}
		deck1.add(new Card(0, Card.RED));
		deck1.add(new Card(0, Card.BLACK));
		Collections.shuffle(deck1);
		drawPile = new Deck(deck1);
	}

	private void askNumPlayers() {
		// get number of players
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter number of players");
		numPlayers = scan.nextInt();
		if (numPlayers < 1 || numPlayers > 13) {
			throw new IllegalArgumentException("Invalid number of players.");
		}
	}

	public void createPlayers() {
		// create players
		players = new Player[numPlayers];
		idPlayerMap = new HashMap();
		for (int i = 0; i < players.length; i++) {
			List<Card> playerdeck = new ArrayList<>();
			for (int j = 0; j < 4; j++) {
				playerdeck.add(drawPile.getCard(0));
				drawPile.remove(0);
			}
			players[i] = new Player(playerdeck);
			idPlayerMap.put(i, players[i]);
		}
	}

	private void setRandomCurrentPlayer() {
		// set a random current player
		currentPlayer = players[new Random().nextInt(players.length)];
	}

	public void flipMyCard(int id, int cardFlipped) {
		if (players[id].getPlayerHand().getCard(cardFlipped).getNumber()==topDiscardCard.getNumber()) {
			players[id].replaceCard(cardFlipped, null);
		} else {
			punishDraw(id);
		}
	}

	public void flipOtherCard(int id, int otherId, int cardFlipped) {
		if (players[otherId].getPlayerHand().getCard(cardFlipped).getNumber()==topDiscardCard.getNumber()) {
			players[otherId].replaceCard(cardFlipped, null);
		} else {
			punishDraw(id);
		}
	}

	public void punishDraw(int id) {
		System.out.println("GameState.punishDraw:");
		System.out.println(players[id]);
		players[id].drawCard(drawPile.getCard(0));
		drawPile.remove(0);
	}
	public boolean isCambiaCalled() {
		return cambiaCaller != null;
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
		System.out.println(currentPlayer.getName() + " has called Cambia.");
		cambiaCaller = currentPlayer;
		endTurn();
	}

	private void drawFromDeck() {
		Card card = drawPile.getCard(0);
		System.out.println(card);
		drawPile.remove(0);
		System.out.println("Discard?");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Yes/No");
				String s = scanner.nextLine();
		if (s.equals("Yes") || s.equals("yes")) {
			topDiscardCard = card;
			useAbility();

			
			endTurn();
			return;
		}
		if (s.equals("No") || s.equals("no")) {
			chooseToReplace(card);
			System.out.println(currentPlayer.getPlayerHand());
			endTurn();
			return;
		}
		else {
			throw new IllegalArgumentException("just answer yes or no");
		}
	}

	private void useAbility() {
		switch(topDiscardCard.getNumber()) {
		case 7:
		case 8:
			break;
		case 9:
		case 10:
			break;
		case 11:
		case 12:
			break;
		case 13:
			break;
		}
	}
	private void drawFromDiscard() {
		if (topDiscardCard == null) {
			System.out.println("you can't do that");
			turn();
			return;
		}
		chooseToReplace(topDiscardCard);
		endTurn();
	}
	private void chooseToReplace(Card card) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("which card do you want to replace?");
		int index = 1;
		for (Card card1 : currentPlayer.getPlayerHand().getCardList()) {
			System.out.println(index + ") " + card1);
			index++;
		}
		int discardNumber = scanner.nextInt() - 1;
		currentPlayer.replaceCard(discardNumber, card);
		System.out.println(currentPlayer.getPlayerHand());
	}
	// end of turn methods
	private void endTurn() { // go to the next player
		for (int i = 0; i < players.length; i++) {
			if (players[i].equals(currentPlayer)) {
				currentPlayer = players[(i+1)%players.length];
				break;
			}
		}

		turn();
	}

	// end of game methods
	private void declareWinner() {
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

		//  hand, winner, and ranking by score
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
		
	}

	public void turn() { // TODO: test
		// print the state of the gameS
		System.out.println("Draw Pile Size: " + drawPile.size());
		System.out.println("Discard Pile Top Card: " + topDiscardCard);
		for (Player player : players) {
			System.out.println(player.getName() + ": " + player.getPlayerHand().size() + "cards");
		}
		System.out.println("It's " + currentPlayer.getName() + "'s turn.");

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