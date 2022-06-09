import java.util.*;
class GameState {
	private Deck[] playerHands;
	private Deck drawPile;
	private Card topDiscardCard;
	private Player cambiaCaller;
	private Player currentPlayer;

	public boolean isCambiaCalled() {
		return currentPlayer != null;
	}

	public Player whoCalledCambia() {
		return currentPlayer;
	}
	public static void main(String[] args) {
		List<Card> deck1 = new ArrayList<>();
		for (int i = 4; i < 56; i+=4) {
			deck1.add(new Card(i/4, 0));
			deck1.add(new Card(i/4, 1));
			deck1.add(new Card(i/4, 2));
			deck1.add(new Card(i/4, 3));
		}
		Collections.shuffle(deck1);
		Deck deck = new Deck(deck1);
		System.out.println(deck);
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
			if whoCalledCambia().equals(currentPlayer()) {
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