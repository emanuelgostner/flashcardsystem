package at.massedterm.massedterm.dao;

import at.massedterm.massedterm.model.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
			
			return round;
	}
}
