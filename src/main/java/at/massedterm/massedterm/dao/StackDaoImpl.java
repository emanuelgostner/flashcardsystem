package at.massedterm.massedterm.dao;

import at.massedterm.massedterm.model.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.security.Principal;
import java.util.List;

@Transactional
@Repository
public class StackDaoImpl implements StackDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Stack> getAllStacks(String user) {
		String sql = "SELECT stacks.*, COUNT(cards.cardid) as cardCount FROM stacks LEFT JOIN cards ON cards.stackid = stacks.stackid group by 1 HAVING stacks.user="+"'"+user+"'";
		return jdbcTemplate.query(
						sql,
						new BeanPropertyRowMapper(Stack.class)
				);
	}
	
	@Override
	public Stack findStackById(long id) {
		return null;
	}
	
	@Override
	public void addStack(String user, Stack stack) {
		String query = "INSERT INTO stacks(stackname, user) VALUES(?, ?)";
		jdbcTemplate.update(query, stack.getStackname(), user);
		//TODO: create success/fail response
	}
	
	@Override
	public void updateStack(Stack stack) {
	
	}
	
	@Override
	public void deleteStack(long id) {
	
	}
}
