import java.util.List;

class Deck {
	private List<Card> deck;
	public Deck(List<Card> deck) {
		this.deck = deck;
	}
}

class Card {
	private int number;
	private int suite; 

	public Card(int number, int suite) {
		this.number = number;
		this.suite = suite;
	}
}