package at.massedterm.controller;

import at.massedterm.dao.CardDaoImpl;
import at.massedterm.model.Card;
import at.massedterm.model.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CardController {
    @Autowired
    private CardDaoImpl cardDao;

    @PostMapping("/webapi/cards/getAllCards")
    public List<Card> allCards (@RequestBody Stack stack) {
        return cardDao.getAllCards(stack.getStackid());
    }

    @PostMapping("/webapi/cards/addCard")
    public Number newCard(@RequestBody Card newCard) {
        return cardDao.addCard(newCard);
    }

    @PostMapping("/webapi/cards/updateCard")
    public void updateCard(@RequestBody Card card) {
        cardDao.updateCard(card);
    }

    @PostMapping("/webapi/cards/deleteCard")
    public void deleteCard(@RequestBody Card card) {
        cardDao.deleteCard(card);
    }
}