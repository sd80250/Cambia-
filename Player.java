import java.util.*;
class Player {
	private int id;
	private Deck playerDeck;
	private String name;
	public Player (List<Card> cardList, int id) {
		playerDeck = new Deck(cardList);
		this.id = id;
	}
	public String toString() {
		return "deck: " + playerDeck.toString() + "  name: " + name;
	}
	public void setName(String name) {
		this.name = name;
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

	public void setPlayersID(int id) {
		this.id = id;
	}

	public int getPlayerID() {
		return id;
	}

	public void drawCard(Card card) {
		System.out.println("Player.drawCard");
		System.out.println(playerDeck);
		playerDeck.drawCard(card);
	}
	public void removeCard(int card) {
		playerDeck.remove(card);
	}
}