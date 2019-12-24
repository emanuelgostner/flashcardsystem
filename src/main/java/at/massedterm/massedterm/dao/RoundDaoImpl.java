package at.massedterm.massedterm.dao;

import at.massedterm.massedterm.model.Card;
import at.massedterm.massedterm.model.Response;
import at.massedterm.massedterm.model.Round;
import at.massedterm.massedterm.model.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public class RoundDaoImpl {
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
		return stack;
	}
}
