package at.massedterm.massedterm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Stack: Stapel mit Karten
 * ein Stapel gehört genau einem Benutzer
 * ein Stapel besteht aus mehreren Karten
 */
public class Stack {

	/**
	 * @var
	 */
	private long stackid;

	/**
	 * @var
	 */
	private String stackname;

	/**
	 * @var
	 */
	private String user;

	/**
	 * @var
	 * was bedeutet cardCount genau. wird die Kartenanzahl des Stapels insgesamt gezählt oder
	 * die Karten die ich während der Runde gelernt habe?
	 */
	private int cardCount;

	/**
	 * @var
	 * eine round
	 */
	private Round round;

	/**
	 * @var
	 */
	private List<Card> cardlist;

	/**
	 * Konstruktor
	 */
	public Stack(){
		this.cardlist = new ArrayList<>();
	}

	/**
	 * gibt die StackID zurück
	 * @return
	 */
	public long getStackid() {
		return stackid;
	}

	/**
	 * benennt die StackID
	 * @param stackid
	 * todo in DB schreiben
	 */
	public void setStackid(long stackid) {
		this.stackid = stackid;
	}

	/**
	 * gibt den Stacknamen
	 * @return
	 */
	public String getStackname() {
		return stackname;
	}

	/**
	 * benennt den Stacknamen
	 * @param stackname
	 * todo in DB schreiben
	 */
	public void setStackname(String stackname) {
		this.stackname = stackname;
	}

	/**
	 * gibt den Benutzer
	 * @return
	 */
	public String getUser() {
		return user;
	}

	/**
	 * User festlegen
	 * @param user
	 * todo in DB schreiben
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * gibt den cardCount
	 * @return
	 */
	public int getCardCount() {
		return cardCount;
	}

	/**
	 * legt den Card Count fest
	 * @param cardCount
	 * todo in DB schreiben
	 */
	public void setCardCount(int cardCount) {
		this.cardCount = cardCount;
	}

	/**
	 * gibt die Cardlist
	 * @return
	 */
	public List<Card> getCardlist() {
		return cardlist;
	}

	/**
	 * gibt eine bestimmte Karte zurück an der Position Index.
	 * @param index
	 * @return
	 */
	public Card getCardAt(int index){
		return cardlist.get(index);
	}

	/**
	 * setzt die Card list ??? Kartenauswahl festlegen?
	 * wirft die alten existierenden Karten weg.
	 * setzt den CardCount zurück
	 * @param cardlist
	 * TODO: verifiziere ob gebraucht wird.
	 */
	public void setCardlist(List<Card> cardlist) {
		this.cardlist = cardlist;
		this.cardCount = cardlist.size();
	}

	/**
	 * fügt eine Karte zu dem Stack
	 * @param card
	 *
	 */
	public void addCard(Card card){
		this.cardlist.add(card);
		this.cardCount++;
	}

	/**
	 * löscht eine Karte
	 * @param index
	 * @return
	 */
	public Card removeCard(int index){
		Card card = this.cardlist.remove(index);
		this.cardCount--;
		return card;

	}

	/**
	 * gibt die Runde zurück
	 * @return Round
	 */
	public Round getRound() {
		return round;
	}

	/**
	 * legt die Runde fest
	 * @param round
	 */
	public void setRound(Round round) {
		this.round = round;
	}
}
