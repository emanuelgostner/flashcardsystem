document.addEventListener("DOMContentLoaded", function (event) {
    /*
    * Current stackid, retrieved from the url
    * */
    var stackid = /stacks\/(\d*)\//g.exec(window.location.href)[1];
    /*
    * response from server. Contains Stack, cards, round, responses
    * */
    var data;
    /*
    * Contains cards
    * learnQue: cards that will show next in view
    * cards: cards that have not been shown yet
    * unlearned: cards that were always answered wrongly
    * learned: cards that were anwered wrongly once/multiple times but correctly the last time
    * mastered: cards that were answered correctly; cards that were answered correctly multiple times after answering them wrong
    * */
    var learnQueue = [], cards = [], unlearned = [], learned = [], mastered = [];
    var actCard;
    var maxWrongness = 2;
    var view = {
        "information": {
            "cards": document.getElementsByClassName("informationCardsLeft")[0],
            "learned": document.getElementsByClassName("informationLearned")[0],
            "mastered": document.getElementsByClassName("informationMastered")[0]
        },
        "cardContainer": document.getElementsByClassName("cardContainer")[0]
    }

    fetch("/webapi/stacks/" + stackid + "/getStackFull")
        .then(
            function (response) {
                if (response.status !== 200) {
                    console.log('Looks like there was a problem. Status Code: ' +
                        response.status);
                    return;
                }
                response.json().then(function (response) {
                    data = response;
                    main();
                });
            }
        )
        .catch(function (err) {
            console.log('Fetch Error :-S', err);
        });


    function main() {
        if (data.cardlist.length > 0) {
            document.body.classList.add("showLearnCard");
            buildCards();
            updateView();
            cardFunctionality()
        } else {
            document.body.classList.add("showSpecialCardNoCards");
        }
    }
    /*
    * Build a javascript object and sort the responses to corresponding cards
    * */
    function buildCards() {
        /*
        * reset cards
        * */
        cards = [], unlearned = [], learned = [], mastered = [];

        /*
        * Loop trough the data:
        * -to assign responses to cards
        * -to calculate how often a card has been answered correctly/wrongly
        * -to get the last user response for each card
        * */
        for (var i = 0; i < data.cardlist.length; i++) {
            var card = data.cardlist[i];
            var responses = data.round.responseList.filter(function (response) {
                return response.cardid === data.cardlist[i].cardid
            });
            card["wrongness"] = calculateWrongness(maxWrongness, responses);
            card["isCorrect"] = getLastResponse(responses);

            /*
            * Assign each card to a specific card container depending of:
            * -total answer
            * -wrongness factor
            * -last answer (correct/incorrect)
            * */
            if (responses.length == 0) {
                cards.push(card);
            } else if (card["wrongness"] === 0 && card["isCorrect"]) {
                mastered.push(card);
            } else if (card["wrongness"] > 0 && card["isCorrect"]) {
                learned.push(card);
            } else {
                unlearned.push(card);
            }
        }

        /*
        * Add Card to leanQueue
        * First: cards that were answered wrongly
        * Second: cards that were answered correct but still have a wrongness factor > 0
        * Third: cards that have not been answered yet
        * */
        if (unlearned.length > 0)
            for (var i = 0; i <= unlearned.length - 1; i++) {
                if (learnQueue.length <= 7) {
                    var found = learnQueue.find(function (element) {
                        return element.cardid === unlearned[i].cardid;
                    });
                    if (found === undefined)
                        learnQueue.push(unlearned[i]);
                } else
                    break;
            }
        if (learned.length > 0)
            for (var i = 0; i <= learned.length - 1; i++) {
                if (learnQueue.length <= 7) {
                    var found = learnQueue.find(function (element) {
                        return element.cardid === learned[i].cardid;
                    });
                    if (found === undefined)
                        learnQueue.push(learned[i]);
                } else
                    break;
            }
        if (cards.length > 0)
            for (var i = 0; i <= cards.length - 1; i++) {
                if (learnQueue.length <= 7) {

                    var found = learnQueue.find(function (element) {
                        return element.cardid === cards[i].cardid;
                    });
                    if (found === undefined)
                        learnQueue.push(cards[i]);
                } else
                    break;
            }
    }
    /*
    * Calculate the wrongness factor of cards
    * @maxWrongness limit of the wrongness factor upwards
    * @responses List of correct/incorrect answers for a specific card
    * @return wrongness factor
    * */
    function calculateWrongness(maxWrongness, responses) {
        /*
        * Default wrongness factor
        * */
        var wrongness = 1;

        /*
        * If a card has not been answered yet, assign the default value
        * */
        if (responses.length === 0)
            return wrongness;
        /*
        * If the amount of responses is lower than the maximum Wrongness factor
        * loop through every response
        * */
        else if ((responses.length) < maxWrongness) {
            for (var i = 0; i < (responses.length); i++) {
                if (responses[i].isCorrect)
                    wrongness--;
                else
                    wrongness++;
            }
        /*
        * If the amount of responses is higher than the maximum Wrongness factor
        * only get the last k responses, whereas k = maxWrongness
        *
        * */
        } else {
            for (var i = (responses.length - 1); i > ((responses.length - 1) - maxWrongness); i--) {
                if (responses[i].isCorrect)
                    wrongness--;
                else
                    wrongness++;
            }
        }
        /*
        * return the wrongness factor
        * */
        if (wrongness < 0)
            return 0;
        else if (wrongness <= maxWrongness)
            return wrongness;
        else if (wrongness > maxWrongness)
            return maxWrongness;
    }
    /*
    * @param responses list of responses for a specific card
    * @return true/false depending on the last answer a user has given for a specific card
    * */
    function getLastResponse(responses) {
        return responses.length > 0 ? responses[responses.length - 1].isCorrect : false;
    }

    function updateView() {
        /*
        * update the HTML information panel view
        * */
        view.information.cards.innerHTML = cards.length + unlearned.length;
        view.information.learned.innerHTML = learned.length;
        view.information.mastered.innerHTML = mastered.length;

        /*
        * Calculate percentage of learned cards and update the HTML loading bar view
        * */
        var total = cards.length + unlearned.length + learned.length + mastered.length;
        var ratio = mastered.length / total * 100;
        document.getElementsByClassName("progress-bar")[0].style.width = ratio + "%";

        /*
        * If there are cards left to learn add a new card to the view
        * */
        if (learnQueue.length > 0) {
            /*
            * Get the first card in the learnQueue and remove it from the array
            * */
            actCard = learnQueue.shift();

            /*
            * Create HTML card object
            * */
            var newCard = document.createElement('div');
            newCard.className = "actLearnCard card";
            newCard.innerHTML = document.getElementById("learnCard-template").innerHTML;
            newCard.querySelector(".card-front .card-face-inner").innerHTML = actCard.question;
            newCard.querySelector(".card-back .card-face-inner").innerHTML = actCard.answer;
            view.cardContainer.insertBefore(newCard, view.cardContainer.firstChild);
            newCard.classList.add("learning");
            newCard.addEventListener('click', function (e) {
                newCard.classList.toggle("is-flipped");
            });
        /*
        * If no cards left, show Button to start a new round
        * */
        } else {
            document.body.classList.add("showSpecialCardAllLearned");
            document.body.classList.remove("showLearnCard");
        }
    }

    /*
    * Add click event listeners for the correct/not correct buttons
    * */
    function cardFunctionality() {
        document.getElementsByClassName("answerIsIncorrect")[0].addEventListener("click", function (e) {
            postResponse(data.round.roundid, actCard.cardid, false);
            view.cardContainer.firstChild.classList.add("nextCard");
            buildCards();
            updateView();

        });
        document.getElementsByClassName("answerIsCorrect")[0].addEventListener("click", function (e) {
            postResponse(data.round.roundid, actCard.cardid, true);
            view.cardContainer.firstChild.classList.add("nextCard");
            buildCards();
            updateView();
        });
        document.getElementsByClassName("specialCardButtonAllLearned")[0].addEventListener('click', function (e) {
            postRound();
            document.body.classList.remove("showSpecialCardAllLearned");
            document.body.classList.add("showLearnCard");
        });
    }

    /*
    * Post user response to server
    * */
    function postResponse(roundid, cardid, isCorrect) {
        var newResponse = {
            "roundid": roundid,
            "cardid": cardid,
            "isCorrect": isCorrect
        };
        data.round.responseList.push(newResponse);
       fetch('/webapi/stacks/'+stackid+'/addResponse', {
           method: 'post',
           headers: {
               'Accept': 'application/json, text/plain, *!/!*',
               'Content-Type': 'application/json'
           },
           body: JSON.stringify(newResponse)
       })
           .then(res => res.json())
           .then(function (data) {
               console.log('Request succeeded with JSON response', data);
           })
           .catch(function (error) {
               console.log('Request failed', error);
           });
    }

    /*
    * Post new round to server
    * */
    function postRound() {
        fetch('/webapi/stacks/'+stackid+'/addRound', {
           method: 'post',
           headers: {
               'Accept': 'application/json, text/plain, *!/!*',
               'Content-Type': 'application/json'
           },
           body: JSON.stringify({'stackid': data.stackid})
       })
           .then(res => res.json())
           .then(function (response) {
               console.log('Request succeeded with JSON response', response);
               data.round.roundid = response;
               data.round.responseList =  [];
               learnQueue = [];
               buildCards();
               updateView();
           })
           .catch(function (error) {
               console.log('Request failed', error);
           });
    }
})