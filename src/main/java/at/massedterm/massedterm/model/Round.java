package at.massedterm.massedterm.model;

import java.util.List;

public class Round {

	/**
	 * @var
	 */
	private long roundid;

	/**
	 * @var
	 */
	private long stackid;

	/**
	 * @var
	 */
	private String user;

	/**
	 * @var
	 */
	private int timestamp;

	/**
	 * @var
	 */
	private List<Response> responseList;

	/**
	 * gibt die round ID aus
	 * @return
	 */
	public long getRoundid() {
		return roundid;
	}

	/**
	 * legt die round ID fest
	 * @param roundid
	 */
	public void setRoundid(long roundid) {
		this.roundid = roundid;
	}

	/**
	 * gibt die stack ID aus
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
	 * gibt den User aus
	 * @return
	 */
	public String getUser() {
		return user;
	}

	/**
	 * legt den User fest
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * gibt den timestamp aus
	 * @return
	 */
	public int getTimestamp() {
		return timestamp;
	}

	/**
	 * legt den timestamp fest
	 * @param timestamp
	 */
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * gibt die Responselist aus
	 * @return
	 */
	public List<Response> getResponseList() {
		return responseList;
	}

	/**
	 * legt die responselist fest
	 * @param responseList
	 */
	public void setResponseList(List<Response> responseList) {
		this.responseList = responseList;
	}
}
