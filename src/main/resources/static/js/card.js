document.addEventListener("DOMContentLoaded", function (event) {
    /*
    * Retrieve stackid from page-url
    * */
    var stackid = /stacks\/(\d*)\//g.exec(window.location.href)[1];

    /*
    * Define common GUI elements
    * */
    var view = {
        "cardsContainer": document.getElementsByClassName("cardsContainer")[0],
        "updateStack": {
            "input": document.getElementsByClassName("updateStackInput")[0],
            "button": document.getElementsByClassName("updateStackButton")[0]
        },
        "newCard": {
            "question": document.getElementsByClassName("cardQuestion")[0],
            "answer": document.getElementsByClassName("cardAnswer")[0],
            "button": document.getElementsByClassName("btnAddNewCard")[0]
        }
    };
    /*
    * Create Handlebars templates
    * */
    var newCardHtml = document.getElementById("newCard-template").innerHTML;
    var HandlebarsNewCardTemplate = Handlebars.compile(newCardHtml);

    /*
    * Initialization
    * */
    init();
    function init() {
        initQuill(view.newCard.question);
        initQuill(view.newCard.answer);
        newCardFunctionality();
        fetchAllCards();
    }
    function newCardFunctionality() {
        view.newCard.button.addEventListener("click", function() {
            var newCard = {
            	"stackid":stackid,
            	"question": view.newCard.question.querySelector(".ql-editor").innerHTML,
            	"answer": view.newCard.answer.querySelector(".ql-editor").innerHTML
            };
            postNewCard(newCard);
            var card = document.createElement("div");
            card.innerHTML = HandlebarsNewCardTemplate([newCard]);
            view.cardsContainer.appendChild(card);
            initQuill(card.querySelector(".cardQuestion"));
            initQuill(card.querySelector(".cardAnswer"));
            view.newCard.question.querySelector(".ql-editor").innerHTML = "";
            view.newCard.answer.querySelector(".ql-editor").innerHTML = "";
        })

    }
    function fetchedCardsFunctionality(data) {
        view.cardsContainer.innerHTML = HandlebarsNewCardTemplate(data);
        var cardItems = document.getElementsByClassName("cardItem");
        for(var i = 0; cardItems.length; i++) {
            initQuill(cardItems[i].querySelector(".cardQuestion"));
            initQuill(cardItems[i].querySelector(".cardAnswer"));
        }
    }
    /*
    * Create new Rich-text-editor instance
    * */
    function initQuill(domelement) {
        new Quill(domelement, {
            modules: {
                toolbar: [
                    [{header: [1, 2, false]}],
                    ['bold', 'italic', 'underline'],
                    [{'list': 'bullet'}],
                ]
            },
            placeholder: 'Compose an epic...',
            theme: 'snow'  // or 'bubble'
        });
    }
    function fetchAllCards() {
        fetch('/webapi/cards/getAllCards', {
            method: 'post',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({"stackid": stackid})
        })
            .then(res => res.json())
            .then(function (data) {
                console.log('Request succeeded with JSON response', data);
                fetchedCardsFunctionality(data);
            })
            .catch(function (error) {
                console.log('Request failed', error);
            });
    }
    function postNewCard(newCard) {
        fetch('/webapi/cards/addCard', {
                   method: 'post',
                   headers: {
                       'Accept': 'application/json, text/plain, */*',
                       'Content-Type': 'application/json'
                   },
                   body: JSON.stringify(newCard)
               })
                   .then(res => res.json())
                   .then(function (response) {

                   })
                   .catch(function (error) {
                       console.log('Request failed', error);
                   });
    }
})