import java.util.List;

class Deck {
	private List<Card> cardList;
	public Deck(List<Card> cardList) {
		this.cardList = cardList;
	}
	public List<Card> getCardList() {
		return cardList;
	}
	public String toString() {
		String text = "";
		for (int i = 0; i < cardList.size(); i++) {
			text = text + cardList.get(i) + ";";
		}
		return text;
	}
	public Card getCard(int index) {
		return cardList.get(index);
	}
	public void remove(int index) {
		cardList.remove(index);
	}
}

class Card {
	private int number;
	private int suite; // 0 is diamond; 1 is club; 2 is heart; 3 is spades

	public static final int DIAMONDS = 0;
	public static final int CLUBS = 1;
	public static final int HEARTS = 2;
	public static final int SPADES = 3;

	// for jokers
	public static final int RED = 0;
	public static final int BLACK = 1;

	public Card(int number, int suite) {
		this.number = number;
		this.suite = suite;
	}

	public int getValue() {
		if (number == 13) {
			if (suite == Card.DIAMONDS || suite == Card.HEARTS) {
				return -1;
			} else {
				return 21;
			}
		}
		return number;
	}

	public String toString() {
		return Integer.toString(number) + "," + Integer.toString(suite);
	}

}