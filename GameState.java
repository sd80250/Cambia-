import java.util.*;
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
	}

	public boolean isCambiaCalled() {
		return currentPlayer != null;
	}

	public Player whoCalledCambia() {
		return cambiaCaller;
	}
	

	// terminal methods
	private int decideOnMove() {}
	private final int CALL_CAMBIA = 0;
	private final int DRAW_FROM_DECK = 1;
	private final int DRAW_FROM_DISCARD = 2;

	// game state methods
	private void callCambia() {}
	private void drawFromDeck() {}
	private void drawFromDiscard() {}

	// end of game methods
	private void countScore() {}
	private void declareWinner() {}
	private void endGame() {}

	public void turn() {
		if (isCambiaCalled()) {
			if (cambiaCaller.equals(currentPlayer)) {
				countScore();
				declareWinner();
				endGame();
			} else {
				switch (decideOnMove()) {
				case CALL_CAMBIA:
					callCambia();
				case DRAW_FROM_DECK:
					drawFromDeck();
				case DRAW_FROM_DISCARD:
					drawFromDiscard();
				}
			}
		}
	}
}