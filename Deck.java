import java.util.List;

class Deck {
	private List<Card> cardList;
	public Deck(List<Card> cardList) {
		this.cardList = cardList;
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