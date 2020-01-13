package at.massedterm.model;

/**
 * Klasse Response
 *
 * gibt die Rückmeldung ob die Antwort richtig ist
 */
public class Response {

	/**
	 * @var
	 */
	private long responseid;

	/**
	 * @var
	 */
	private long roundid;

	/**
	 * @var
	 */
	private long cardid;

	/**
	 * @var
	 */
	private boolean isCorrect;

	/**
	 *gibt die Response ID zurück
	 * @return
	 */
	
	public long getResponseid() {
		return responseid;
	}

	/**
	 * legt die Response ID fest
	 * @param responseid
	 */
	public void setResponseid(long responseid) {
		this.responseid = responseid;
	}

	/**
	 * gibt die Round ID zurück
	 * @return
	 */
	public long getRoundid() {
		return roundid;
	}

	/**
	 * legt die Round ID fest
	 * @param roundid
	 */
	public void setRoundid(long roundid) {
		this.roundid = roundid;
	}

	/**
	 * gibt die Card ID zurück
	 * @return
	 */
	public long getCardid() {
		return cardid;
	}

	/**
	 * legt die Card ID fest
	 * @param cardid
	 */
	public void setCardid(long cardid) {
		this.cardid = cardid;
	}

	/**
	 * Überprüft ob Antwort richtig ist
	 * @return
	 */
	public boolean getIsCorrect() {
		return isCorrect;
	}

	/**
	 *
	 * @param correct
	 */
	public void setIsCorrect(boolean correct) {
		isCorrect = correct;
	}
}
