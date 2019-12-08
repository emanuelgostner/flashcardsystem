package at.massedterm.massedterm.dao;

import at.massedterm.massedterm.model.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class StackDaoImpl implements StackDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Stack> getAllStacks() {
		String sql = "SELECT * FROM stacks";
		List<Stack> stacks = jdbcTemplate.query(
				sql,
				new BeanPropertyRowMapper(Stack.class)
		);
		return stacks;
	}
	
	@Override
	public Stack findStackById(long id) {
		return null;
	}
	
	@Override
	public void addStack(Stack stack) {
		/*
		String query = "INSERT INTO stack(employee_id, first_name, last_name, email, phone, job_title) VALUES(?, ?, ?, ?, ?, ?)";
		  jdbcTemplate.update(query, employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getPhone(), employee.getJobTitle());
	*/
	}
	
	@Override
	public void updateStack(Stack stack) {
	
	}
	
	@Override
	public void deleteStack(long id) {
	
	}
}
