import java.util.List;
import java.util.ArrayList;
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
	public void main() {
		List<Card> deck1 = new ArrayList<>();
		for (int i = 0; i < 52; i+=4) {
			deck1.add(new Card(i/4, 0));
			deck1.add(new Card(i/4, 1));
			deck1.add(new Card(i/4, 2));
			deck1.add(new Card(i/4, 3));
		}
		Deck deck = new Deck(deck1);
	}
	private void callCambia() {}
	private void drawFromDeck() {}
	private void drawFromDiscard() {}
	public void turn() {}
}