import java.util.*;
class Player {
	private int id; // is this needed? well, we need something i think
	private Deck playerDeck;
	private String name;
	public Player (List<Card> cardList) {
		playerDeck = new Deck(cardList);
		Scanner scan = new Scanner(System.in);
		System.out.println("name:");
		name = scan.next();
	}
	public String toString() {
		return "deck" + playerDeck.toString() + "name" + name;
	}
	public String getName() {
		return name;
	}
	public Deck getPlayerHand() {
		return playerDeck;
	}
}