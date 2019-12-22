package at.massedterm.massedterm.model;

import java.util.List;

public class Round {
	private long roundid;
	private long stackid;
	private String user;
	private int timestamp;
	private List<Response> responseList;
	
	public long getRoundid() {
		return roundid;
	}
	
	public void setRoundid(long roundid) {
		this.roundid = roundid;
	}
	
	public long getStackid() {
		return stackid;
	}
	
	public void setStackid(long stackid) {
		this.stackid = stackid;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public int getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	
	public List<Response> getResponseList() {
		return responseList;
	}
	
	public void setResponseList(List<Response> responseList) {
		this.responseList = responseList;
	}
}
