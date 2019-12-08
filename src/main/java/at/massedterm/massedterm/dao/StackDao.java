package at.massedterm.massedterm.dao;

import at.massedterm.massedterm.model.Stack;

import java.util.List;

public interface StackDao {
	public List<Stack> getAllStacks();
	public Stack findStackById(long id);
	public void addStack(Stack stack);
	public void updateStack(Stack stack);
	public void deleteStack(long id);
}
