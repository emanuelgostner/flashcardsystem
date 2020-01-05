package at.massedterm.massedterm.model;

/**
 * Klasse Card
 */
public class Card {

	/**
	 * @var
	 */
	private long cardid;

	/**
	 * @var
	 */
	private long stackid;

	/**
	 * @var
	 */
	private String question;

	/**
	 * @var
	 */
	private String answer;

	/**
	 * legt die card ID fest
	 * @param cardid
	 */
	public void setCardid(long cardid) {
		this.cardid = cardid;
	}

	/**
	 * gibt die card ID zurück
	 * @return
	 */
	public long getCardid() {
		return cardid;
	}

	/**
	 * gibt die stack ID zurück
	 * @return
	 */
	public long getStackid() {
		return stackid;
	}

	/**
	 * legt die stack ID fest
	 * @param stackid
	 */
	public void setStackid(long stackid) {
		this.stackid = stackid;
	}

	/**
	 * gibt die Frage aus
	 * @return
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * legt die Frage fest
	 * @param question
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * gibt die Antwort aus
	 * @return
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * legt die Antwort fest
	 * @param answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
