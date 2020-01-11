package at.massedterm.massedterm.dao;

import at.massedterm.massedterm.model.Response;
import at.massedterm.massedterm.model.Round;
import at.massedterm.massedterm.model.Stack;

import java.util.List;

public interface RoundDao {
	public Round getActRound(long stackid, String user);
	public Stack getStackFull(long stackid, String user);
	public Number addRound(String user, long stackid);
	public void addResponse(String user, Response response);
}
