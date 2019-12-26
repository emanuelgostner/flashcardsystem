document.addEventListener("DOMContentLoaded", function (event) {
    document.getElementsByClassName("actLearnCard")[0].addEventListener('click', function (e) {
        document.getElementsByClassName("actLearnCard")[0].classList.toggle("is-flipped");
        if(document.getElementsByClassName("actLearnCard")[0].classList.contains("is-flipped"))
            document.getElementsByClassName("actLearnCard")[0].style.transform = "rotateY(180deg)"
        else
            document.getElementsByClassName("actLearnCard")[0].style.transform = "rotateY(0deg)"
    });
    //document.getElementsByClassName("cardContainer")[0].style.height = (window.innerHeight - document.getElementsByClassName("actLearnCardButtons")[0].clientHeight - document.getElementsByClassName("informationBar")[0].clientHeight)+"px";
    var data;
    var learnQueue = [], cards = [], unlearned = [], learned = [], mastered = [];
    var actCard;
    var maxWrongness = 2;
    var noCards;
    var view = {
        "information": {
            "cards": document.getElementsByClassName("informationCardsLeft")[0],
            "learned": document.getElementsByClassName("informationLearned")[0],
            "mastered": document.getElementsByClassName("informationMastered")[0]
        },
        "specialCard": {
            "title": document.getElementsByClassName("specialCardTitle")[0],
            "button": document.getElementsByClassName("specialCardButton")[0]
        },
        "frontCard": document.querySelector(".card-front .card-face-inner"),
        "backCard": document.querySelector(".card-back .card-face-inner"),
        "actLearnCard": document.getElementsByClassName("actLearnCard")[0]
    }

    var stackid = /stacks\/(\d)/g.exec(window.location.href)[1];

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
            noCards = true;
            document.body.classList.add("showSpecialCardNoCards");
        }
    }

    function buildCards() {
        console.log("call buildCards");
        cards = [], unlearned = [], learned = [], mastered = [];
        for (var i = 0; i < data.cardlist.length; i++) {
            var card = data.cardlist[i];
            var responses = data.round.responseList.filter(function (response) {
                return response.cardid === data.cardlist[i].cardid
            });
            card["wrongness"] = calculateWrongness(maxWrongness, responses);
            card["isCorrect"] = getLastResponse(responses);

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
        console.log("call buildCards 2");
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
        if (learnQueue.length > 0) {
            console.log("call buildCards 3");
            actCard = learnQueue.shift();
            view.frontCard.innerHTML = actCard.question;
            view.backCard.innerHTML = actCard.answer;
        } else {
            console.log("no cards left")
            document.body.classList.add("showSpecialCardAllLearned");
            document.body.classList.remove("showLearnCard");
        }
        var status = {
            "data": data,
            "cards": cards,
            "unlearned": unlearned,
            "learned": learned,
            "mastered": mastered,
            "learnQueue": learnQueue,
            "actCard": actCard
        }
        console.log(status);
    }

    function calculateWrongness(maxWrongness, responses) {
        var wrongness = 1;
        if (responses.length === 0)
            return wrongness;
        else if ((responses.length) < maxWrongness) {
            for (var i = 0; i < (responses.length); i++) {
                if (responses[i].isCorrect)
                    wrongness--;
                else
                    wrongness++;
            }
        } else {
            for (var i = (responses.length - 1); i > ((responses.length - 1) - maxWrongness); i--) {
                if (responses[i].isCorrect)
                    wrongness--;
                else
                    wrongness++;
            }
        }
        if (wrongness < 0)
            return 0;
        else if (wrongness <= maxWrongness)
            return wrongness;
        else if (wrongness > maxWrongness)
            return maxWrongness;
    }

    function getLastResponse(responses) {
        return responses.length > 0 ? responses[responses.length - 1].isCorrect : false;
    }

    function updateView() {
        view.information.cards.innerHTML = cards.length + unlearned.length;
        view.information.learned.innerHTML = learned.length;
        view.information.mastered.innerHTML = mastered.length;
        view.actLearnCard.style.transform = "rotateY(0deg)"
    }

    function cardFunctionality() {
        document.getElementsByClassName("answerIsIncorrect")[0].addEventListener("click", function (e) {
            console.log(actCard)
            postResponse(data.round.roundid, actCard.cardid, false);
            buildCards();
            updateView();

        });
        document.getElementsByClassName("answerIsCorrect")[0].addEventListener("click", function (e) {
            console.log(actCard)
            postResponse(data.round.roundid, actCard.cardid, true);
            buildCards();
            updateView();


        });
        document.getElementsByClassName("specialCardButtonAllLearned")[0].addEventListener('click', function (e) {
            postRound();
            document.body.classList.remove("showSpecialCardAllLearned");
            document.body.classList.add("showLearnCard");
        });
    }

    function postResponse(roundid, cardid, isCorrect) {
        var newResponse = {
            "roundid": roundid,
            "cardid": cardid,
            "isCorrect": isCorrect
        }
        data.round.responseList.push(newResponse);
        /*this.newStackname = newStackname;
           fetch('./webapi/stacks/addStack', {
               method: 'post',
               headers: {
                   'Accept': 'application/json, text/plain, *!/!*',
                   'Content-Type': 'application/json'
               },
               body: JSON.stringify({'stackname': this.newStackname})
           })
               .then(res => res.json())
               .then(function (data) {
                   console.log('Request succeeded with JSON response', data);
               })
               .catch(function (error) {
                   console.log('Request failed', error);
               });*/
    }
    function postRound() {
        data.round.responseList =  [];
        learnQueue = [];
        buildCards();
        updateView();
    }
})