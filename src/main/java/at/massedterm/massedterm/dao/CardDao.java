package at.massedterm.massedterm.dao;

import at.massedterm.massedterm.model.Card;

import java.util.List;

public interface CardDao {
    public List<Card> getAllCards(long stackid);
    public Number addCard(Card card);
    public void updateCard(Card card);
    public void deleteCard(Card card);
}
