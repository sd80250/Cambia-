import java.util.List;

class Deck {
	private List<Card> cardList;
	public Deck(List<Card> cardList) {
		this.cardList = cardList;
	}
	public List<Card> getCardList() {
		return cardList;
	}
	public int size() {
		return cardList.size();
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
	public void replaceCard(int index, Card card) {
		cardList.set(index, card);
	}
	public void remove(int index) {
		cardList.remove(index);
	}
	public void drawCard(Card card) {
		cardList.add(card);
		System.out.println("deck.drawCard");
		System.out.println(cardList);
	}
}

class Card {
	private int number;
	private int suite; // 0 is diamond; 1 is club; 2 is heart; 3 is spades

	public static final int CLUBS = 0;
	public static final int DIAMONDS = 1;
	public static final int HEARTS = 2;
	public static final int SPADES = 3;

	// for jokers
	public static final int RED = 0;
	public static final int BLACK = 1;

	public Card(int number, int suite) {
		this.number = number;
		this.suite = suite;
	}

	public int getNumber() {
		return number;
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
		String displayNumber;
		String displaySuite;

		if (number == 0) {
			if (suite == Card.RED) {
				return "RED JOKER";
			} else {
				return "BLACK JOKER";
			}
		} else if (number == 1) {
			displayNumber = "A";
		} else if (number >= 2 && number <= 10) {
			displayNumber = Integer.toString(number);
		} else if (number == 11) {
			displayNumber = "J";
		} else if (number == 12) {
			displayNumber = "Q";
		} else if (number == 13) {
			displayNumber = "K";
		} else {
			displayNumber = "ERROR";
		}

		if (suite == Card.CLUBS) {
			//displaySuite = "♣";
			displaySuite = "clubs";
		} else if (suite == Card.DIAMONDS) {
			//displaySuite = "♢";
			displaySuite = "diamonds";
		} else if (suite == Card.HEARTS) {
			//displaySuite = "♡";
			displaySuite = "hearts";
		} else if (suite == Card.SPADES) {
			//displaySuite = "♠";
			displaySuite = "spades";
		} else {
			displaySuite = "ERROR";
		}

		return displayNumber + displaySuite;
	}

}




