package at.massedterm.massedterm.model;

import java.util.List;

public class Stack {
	private long stackid;
	private String stackname;
	private String user;
	private int cardCount;
	private List<Card> cardlist;
	
	public void setStackid(long stackid) {
		this.stackid = stackid;
	}
	
	public long getStackid() {
			return stackid;
		}
		
	public String getStackname() {
		return stackname;
	}
	
	public void setStackname(String stackname) {
		this.stackname = stackname;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public int getCardCount() {
		return cardCount;
	}
	
	public void setCardCount(int cardCount) {
		this.cardCount = cardCount;
	}
	
	public List<Card> getCardlist() {
		return cardlist;
	}
	
	public void setCardlist(List<Card> cardlist) {
		this.cardlist = cardlist;
	}
}
