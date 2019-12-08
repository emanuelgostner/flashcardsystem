package at.massedterm.massedterm.model;

public class Card {
	private long cardid;
	private long stackid;
	private String Question;
	private String Answer;
	private boolean isCorrect;
	private int wrongness;
	
	public void setCardid(long cardid) {
		this.cardid = cardid;
	}
	
	public long getStackid() {
		return stackid;
	}
	
	public void setStackid(long stackid) {
		this.stackid = stackid;
	}
	
	public String getQuestion() {
		return Question;
	}
	
	public void setQuestion(String question) {
		Question = question;
	}
	
	public String getAnswer() {
		return Answer;
	}
	
	public void setAnswer(String answer) {
		Answer = answer;
	}
	
	public boolean isCorrect() {
		return isCorrect;
	}
	
	public void setCorrect(boolean correct) {
		isCorrect = correct;
	}
	
	public int getWrongness() {
		return wrongness;
	}
	
	public void setWrongness(int wrongness) {
		this.wrongness = wrongness;
	}
}
