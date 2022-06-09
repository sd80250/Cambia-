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
	private void callCambia() {}
	private void drawFromDeck() {}
	private void drawFromDiscard() {}
	public void turn() {}
}