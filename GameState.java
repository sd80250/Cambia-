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
	
}