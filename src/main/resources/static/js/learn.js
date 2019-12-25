document.addEventListener("DOMContentLoaded", function(event) {
    document.getElementsByClassName("actLearnCard")[0].addEventListener('click', function(e) {
        document.getElementsByClassName("actLearnCard")[0].classList.toggle("is-flipped");
    });
    //document.getElementsByClassName("cardContainer")[0].style.height = (window.innerHeight - document.getElementsByClassName("actLearnCardButtons")[0].clientHeight - document.getElementsByClassName("informationBar")[0].clientHeight)+"px";

    var stackid = /stacks\/(\d)/g.exec(window.location.href)[1];

    fetch("/webapi/stacks/" + stackid + "/getStackFull")
        .then(
            function (response) {
                if (response.status !== 200) {
                    console.log('Looks like there was a problem. Status Code: ' +
                        response.status);
                    return;
                }
                response.json().then(function (data) {
                    console.log(data);
                    main(data);
                });
            }
        )
        .catch(function (err) {
            console.log('Fetch Error :-S', err);
        });

    var learnQueue = [];
    var unlearned = [];
    var cards = [];
    var learned = [];

    function main(data) {
          buildCards(data);

    }
    function buildCards(data) {
        var maxWrongness = 2;
        if(data.cardlist.length > 0) {
            for (var i=0; i < data.cardlist.length;i++) {
                var card = data.cardlist[i];
                var responselist = data.round.responseList;
                var responses = responselist.filter(function(response) {
                    return response.cardid === data.cardlist[i].cardid
                });
                card["responses"] = responses;
                card["wrongness"] = calculateWrongness(maxWrongness, responses);
                card["isCorrect"] = getLastResponse(responses);

                if (responses.length == 0) {
                    console.log("card");
                    console.log(card);
                    cards.push(card);
                }

                else if (card["wrongness"] === 0 && card["isCorrect"]) {
                   console.log("learned");
                   console.log(card);
                   learned.push(card);
               }
                else {
                    console.log("unlearned");
                    console.log(card);
                    unlearned.push(card);
                }


            }
        } else {
            console.log("no cards in Stack");
        }
        console.log(cards);
        console.log(unlearned);
        console.log(learned);
    }
    function calculateWrongness(maxWrongness, responses){
        var wrongness = 1;
        if (responses.length === 0 )
            return wrongness;
        else if ((responses.length) < maxWrongness) {
            for (var i = 0; i < (responses.length); i++) {
                if (responses[i].isCorrect)
                    wrongness--;
                else
                    wrongness++;
            }
        }
        else {
            for (var i = (responses.length-1); i > ((responses.length-1)-maxWrongness); i--) {
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
        return responses.length > 0 ? responses[responses.length-1].isCorrect : false;
    }
})