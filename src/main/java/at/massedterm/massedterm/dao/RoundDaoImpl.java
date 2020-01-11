package at.massedterm.massedterm.dao;

import at.massedterm.massedterm.model.Card;
import at.massedterm.massedterm.model.Response;
import at.massedterm.massedterm.model.Round;
import at.massedterm.massedterm.model.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/*
* Transactional: all operations should happen entirely or not at all
* */
@Transactional
/*
* Repository annotation: indicates that the class provides mechanisms CRUD operations on objects
* */
@Repository
public class RoundDaoImpl implements RoundDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Round getActRound(long stackid, String user) {
		String sqlRound = "SELECT roundid, stackid, UNIX_TIMESTAMP(TIMESTAMP) as timestamp, user FROM rounds WHERE stackid = "+stackid+" AND user = '"+user+"' ORDER BY TIMESTAMP DESC LIMIT 1";
		Round round =  (Round) jdbcTemplate.queryForObject(
				sqlRound,
				new BeanPropertyRowMapper(Round.class)
		);
		
		String sqlResponses = "SELECT responseid, roundid, cardid, isCorrect, UNIX_TIMESTAMP(TIMESTAMP) as timestamp FROM responses WHERE roundid = "+round.getRoundid()+" AND USER = '"+user+"'";
		List<Response> responses =  jdbcTemplate.query(
				sqlResponses,
				new BeanPropertyRowMapper(Response.class)
		);
		
		round.setResponseList(responses);
		return round;
	}
	public Stack getStackFull(long stackid, String user){
		String sqlStack = "SELECT stackid, stackname, user FROM stacks where stackid = "+stackid+" AND user="+"'"+user+"'";
		
		Stack stack =  (Stack) jdbcTemplate.queryForObject(
				sqlStack,
				new BeanPropertyRowMapper(Stack.class)
		);
		
		String sqlCards = "SELECT cardid, stackid, question, answer  FROM cards where stackid = "+stackid+" AND (SELECT user FROM stacks WHERE stackid = "+stackid+")="+"'"+user+"'";
				List<Card> cards = jdbcTemplate.query(
						sqlCards,
						new BeanPropertyRowMapper(Card.class)
				);
				
		stack.setCardlist(cards);
		String sqlRound = "SELECT roundid, stackid, UNIX_TIMESTAMP(TIMESTAMP) as timestamp, user FROM rounds WHERE stackid = "+stackid+" AND user = '"+user+"' ORDER BY TIMESTAMP DESC LIMIT 1";
		Round round;
		
		try {
			round = (Round) jdbcTemplate.queryForObject(
					sqlRound,
					new BeanPropertyRowMapper(Round.class)
			);
		}catch(Exception e) {
			String sqlInitialRound = "INSERT INTO rounds(stackid, user) VALUES( "+stackid+",'"+user+"' )";
			jdbcTemplate.update(sqlInitialRound);
			round = (Round) jdbcTemplate.queryForObject(
					sqlRound,
					new BeanPropertyRowMapper(Round.class)
			);
		}
		
		String sqlResponses = "SELECT responseid, roundid, cardid, isCorrect, UNIX_TIMESTAMP(TIMESTAMP) as timestamp FROM responses WHERE roundid = " + round.getRoundid() + " AND USER = '" + user + "'";
		List<Response> responses = jdbcTemplate.query(
				sqlResponses,
				new BeanPropertyRowMapper(Response.class)
		);
		
		round.setResponseList(responses);
		stack.setRound(round);
		return stack;
	}
	
	public Number addRound(String user, long stackid) {
		
		String query = "INSERT INTO rounds(stackid, user) VALUES(?, ?)";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(query, new String[] {"id"});
		            ps.setLong(1, stackid);
		            ps.setString(2, user);
		            return ps;
		        }
		    },
		    keyHolder);
		return keyHolder.getKey();
		//TODO: create success/fail response
	}
	public void addResponse(String user, Response response) {
		String query = "INSERT INTO responses(roundid, cardid, isCorrect, user) VALUES(?, ?, ?,? )";
		jdbcTemplate.update(query, response.getRoundid(), response.getCardid(), response.getIsCorrect(), user);
	}
}
