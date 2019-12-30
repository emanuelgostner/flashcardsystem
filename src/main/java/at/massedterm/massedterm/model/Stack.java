package at.massedterm.massedterm.model;

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
	 */
	private int cardCount;
	private Round round;

	/**
	 * @var
	 */
	private List<Card> cardlist;

	/**
	 * gibt den StackID zurück
	 * @return
	 */
	public long getStackid() {
		return stackid;
	}

	/**
	 * setzt  die StackID
	 * @param stackid
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
	 * setzt den Stacknamen
	 * @param stackname
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
	 * setzt den User
	 * @param user
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
	 * setzt den CardCount
	 * @param cardCount
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
	 * setzt die CardList
	 * @param cardlist
	 */
	public void setCardlist(List<Card> cardlist) {
		this.cardlist = cardlist;
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}
}
