package at.massedterm.dao;

import at.massedterm.model.Card;
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

@Transactional
@Repository
public class CardDaoImpl implements CardDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Card> getAllCards(long stackid) {
        String sql = "SELECT * from cards WHERE stackid = "+stackid;
        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(Card.class)
        );
    }
    
    @Override
    /**
     * @return Number The card id
     */
    public Number addCard(Card card) {
        String query = "INSERT INTO cards(question, answer, stackid) VALUES(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(query, new String[]{"id"});
                        ps.setString(1, card.getQuestion());
                        ps.setString(2, card.getAnswer());
                        ps.setLong(3, card.getStackid());
                        return ps;
                    }
                },
                keyHolder);
        return keyHolder.getKey();
    }

    @Override
    public void updateCard(Card card) {
        String queryUpdateCard = "UPDATE cards SET question = ?, answer = ? WHERE cardid = ? AND stackid = ?";
        jdbcTemplate.update(queryUpdateCard, card.getQuestion(), card.getAnswer(), card.getCardid(), card.getStackid());
    }

    @Override
    public void deleteCard(Card card) {
        String queryDeleteResponses  = "DELETE FROM responses WHERE cardid = ?";
        jdbcTemplate.update(queryDeleteResponses, card.getCardid());
        String queryDeleteCard = "DELETE FROM cards WHERE cardid = ?";
        jdbcTemplate.update(queryDeleteCard, card.getCardid());
    }
}