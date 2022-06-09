import java.util.List;

class Deck {
	private List<Card> cardList;
	public Deck(List<Card> cardList) {
		this.cardList = cardList;
	}
	public String toString() {
		String text = "";
		for (int i = 0; i < cardList.size(); i++) {
			text = text + cardList.get(i)+ ";";
		}
		return text;
	}
}

class Card {
	private int number;
	private int suite; 

	public Card(int number, int suite) {
		this.number = number;
		this.suite = suite;
	}
	public String toString() {
		return Integer.toString(number)+ ","+Integer.toString(suite);
	}

}