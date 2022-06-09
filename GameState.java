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
		if isCambiaCalled() return currentPlayer;
		return null;
	}
	public void main() {
		List<Card> deck1 = new ArrayList<>();
		for (int i = 0; i < 52; i+=4) {
			deck1.add(new Card(i, 0));
			deck1.add(new Card(i, 1));
			deck1.add(new Card(i, 2));
			deck1.add(new Card(i, 3));
		}
		Deck deck = new Deck(deck1);
	}
}