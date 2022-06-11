import java.util.*;
class Player {
	private int id; // is this needed? well, we need something i think
	private Deck playerDeck;
	private String name;
	public Player (List<Card> cardList) {
		playerDeck = new Deck(cardList);
	}
	public String toString() {
		return "deck: " + playerDeck.toString() + "  name: " + name;
	}
	public String getName() {
		return name;
	}
	public Deck getPlayerHand() {
		return playerDeck;
	}
	public void replaceCard(int discardNumber, Card card) {
		playerDeck.replaceCard(discardNumber, card);
	}
	/*public Player[] getPlayers() {
		return players;
	}*/
	public void setPlayersID(int id) {
		this.id = id;
	}
}