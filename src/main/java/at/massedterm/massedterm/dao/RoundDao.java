package at.massedterm.massedterm.dao;

import at.massedterm.massedterm.model.Round;
import at.massedterm.massedterm.model.Stack;

import java.util.List;

public interface RoundDao {
	public Round getActRound();
	public Stack getStackFull();
	public Number addRound();
	public void addResponse();
	
}
