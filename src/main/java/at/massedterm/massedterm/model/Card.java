package at.massedterm.massedterm.model;

public class Card {
	private long cardid;
	private long stackid;
	private String question;
	private String answer;
	
	public void setCardid(long cardid) {
		this.cardid = cardid;
	}
	
	public long getCardid() {
		return cardid;
	}
	
	public long getStackid() {
		return stackid;
	}
	
	public void setStackid(long stackid) {
		this.stackid = stackid;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
