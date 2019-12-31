package at.massedterm.massedterm;

import at.massedterm.massedterm.model.Card;
import at.massedterm.massedterm.model.Stack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StackTest {

	@Test
	void testAddCard() {
		Stack stack = new Stack();
		Card card = new Card();

		card.setQuestion("Welche Farbe hat der Himmel");
		card.setAnswer("blau");

		int count = stack.getCardCount();
		Assertions.assertEquals(0, count);

		// Karte hinzufügen
		stack.addCard(card);
		Assertions.assertEquals(count +1, stack.getCardCount());

		/**
		 * Überprüfe ob die Karte auf dem ersten Platz ist.
		 */

		Card cardCopy = stack.getCardAt(0);
		Assertions.assertEquals(card, cardCopy);

		/**
		 * erstelle eine neue Karte und überprüfe ob es auf den zweiten Platz kommt.
		 */

		Card card2 = new Card();

		card2.setQuestion("Welche Farbe hat die Wiese?");
		card2.setAnswer("grün");

		count = stack.getCardCount();
		Assertions.assertEquals(1, count);

		// Karte hinzufügen
		stack.addCard(card2);
		Assertions.assertEquals(count +1, stack.getCardCount());

		/**
		 * überprüfe ob die Karte auf dem ersten Paltz ist.
		 */

		cardCopy = stack.getCardAt(1);
		Assertions.assertEquals(card2, cardCopy);
		Assertions.assertNotEquals(card, cardCopy);
	}

	@Test
	void testRemoveCard() {
		Stack stack = new Stack();
		Card card = new Card();

		card.setQuestion("Welche Farbe hat der Himmel");
		card.setAnswer("blau");

		stack.addCard(card);
		int count = stack.getCardCount();
		Card cardCopy = stack.removeCard(0);
		Assertions.assertEquals(card, cardCopy);
		Assertions.assertEquals(count -1,stack.getCardCount());
	}

}
