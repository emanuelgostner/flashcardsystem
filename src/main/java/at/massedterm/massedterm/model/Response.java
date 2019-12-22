package at.massedterm.massedterm.model;

public class Response {
	private long responseid;
	private long roundid;
	private long cardid;
	private Boolean isCorrect;
	
	public long getResponseid() {
		return responseid;
	}
	
	public void setResponseid(long responseid) {
		this.responseid = responseid;
	}
	
	public long getRoundid() {
		return roundid;
	}
	
	public void setRoundid(long roundid) {
		this.roundid = roundid;
	}
	
	public long getCardid() {
		return cardid;
	}
	
	public void setCardid(long cardid) {
		this.cardid = cardid;
	}
	
	public Boolean getCorrect() {
		return isCorrect;
	}
	
	public void setCorrect(Boolean correct) {
		isCorrect = correct;
	}
}
