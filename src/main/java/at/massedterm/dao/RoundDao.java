package at.massedterm.dao;

import at.massedterm.model.Response;
import at.massedterm.model.Round;
import at.massedterm.model.Stack;

public interface RoundDao {
	public Round getActRound(long stackid, String user);
	public Stack getStackFull(long stackid, String user);
	public Number addRound(String user, long stackid);
	public void addResponse(String user, Response response);
}
