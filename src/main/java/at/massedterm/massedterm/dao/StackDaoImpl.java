package at.massedterm.massedterm.dao;

import at.massedterm.massedterm.model.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.security.Principal;
import java.sql.*;
import java.util.List;

@Transactional
@Repository
public class StackDaoImpl implements StackDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Stack> getAllStacks(String user) {
		String sql = "SELECT stacks.*, COUNT(cards.cardid) as cardCount FROM stacks LEFT JOIN cards ON cards.stackid = stacks.stackid group by 1 HAVING stacks.user="+"'"+user+"' AND stacks.deleted=0";
		return jdbcTemplate.query(
			sql,
			new BeanPropertyRowMapper(Stack.class)
		);
	}
	
	@Override
	/**
	 * toDo:
	 */
	public Stack findStackById(long id) {
		return null;
	}
	
	@Override
	/**
	 * @return Number The stack id
	 */
	public Number addStack(String user, Stack stack) {
		String query = "INSERT INTO stacks(stackname, user) VALUES(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(query, new String[] {"id"});
		            ps.setString(1, stack.getStackname());
		            ps.setString(2, user);
		            return ps;
		        }
		    },
		    keyHolder);
		return keyHolder.getKey();
		//TODO: create success/fail response
	}
	
	@Override
	public void updateStack(Stack stack) {
	
	}
	
	@Override
	public void deleteStack(String user, Stack stack) {
		String queryDeleteStack = "UPDATE stacks SET deleted = 1 WHERE stackid=? AND USER = ?";
		jdbcTemplate.update(queryDeleteStack, stack.getStackid(), user);
	}
}
