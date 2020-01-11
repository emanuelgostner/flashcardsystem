package at.massedterm.massedterm.dao;

import at.massedterm.massedterm.model.Stack;

import java.util.List;

public interface StackDao {
	public List<Stack> getAllStacks(String user);
	public Number addStack(String user, Stack stack);
	public void updateStack(String user, Stack stack);
	public void deleteStack(String user, Stack stack);
}
