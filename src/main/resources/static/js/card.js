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
        "stack": {
            "input": document.getElementsByClassName("updateStackInput")[0],
            "update": document.getElementsByClassName("btnUpdateStack")[0],
            "delete": document.getElementsByClassName("btnDeleteStack")[0]
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
        serverpost("/webapi/cards/getAllCards", {"stackid": stackid}, fetchedCardsFunctionality);
        //fetchAllCards();
        clickHandler();
    }

    function newCardFunctionality() {
        /*
        * Get entered value from input fields and post to server
        * */
        view.newCard.button.addEventListener("click", function () {
            var newCard = {
                "stackid": stackid,
                "question": view.newCard.question.querySelector(".ql-editor").innerHTML,
                "answer": view.newCard.answer.querySelector(".ql-editor").innerHTML
            };
            serverpost('/webapi/cards/addCard', newCard, function (cardid) {
                /*
                * Add newly created card also to the user GUI
                * */
                newCard['cardid'] = cardid;
                var card = document.createElement("div");
                card.innerHTML = HandlebarsNewCardTemplate([newCard]);
                view.cardsContainer.appendChild(card);
                initQuill(card.querySelector(".cardQuestion"));
                initQuill(card.querySelector(".cardAnswer"));
                view.newCard.question.querySelector(".ql-editor").innerHTML = "";
                view.newCard.answer.querySelector(".ql-editor").innerHTML = "";
            });
        })
    }

    function fetchedCardsFunctionality(data) {
        /*
        * create Template for all fetched cards
        * */
        view.cardsContainer.innerHTML = HandlebarsNewCardTemplate(data);
        var cardItems = document.getElementsByClassName("cardItem");

        /*
        * Init Rich-Text-Editor for fetched cards
        * */
        for (var i = 0; cardItems.length; i++) {
            initQuill(cardItems[i].querySelector(".cardQuestion"));
            initQuill(cardItems[i].querySelector(".cardAnswer"));
        }
    }

    function clickHandler() {
        /*
        * Handler for cardContainer
        * */
        view.cardsContainer.addEventListener("click", function (e) {
            /*
            * Click Card Update Button
            * */
            if (e.target.classList.contains("btnUpdateCard")) {
                var card = {
                    "stackid": stackid,
                    "cardid": e.target.dataset.cardid,
                    "question": e.target.closest('.cardItem').querySelector(".cardQuestion .ql-editor").innerHTML,
                    "answer": e.target.closest('.cardItem').querySelector(".cardAnswer .ql-editor").innerHTML
                };
                serverpost("/webapi/cards/updateCard", card)
                /*
                * Click Card Delete Button
                * */
            } else if (e.target.classList.contains("btnDeleteCard")) {
                card = {
                    "cardid": e.target.dataset.cardid
                };
                serverpost("/webapi/cards/deleteCard", card);
                e.target.closest(".card").remove();
            }
        });

        /*
        * Handler for Stack Buttons
        * */
        view.stack.update.addEventListener("click", function () {
            serverpost("/webapi/stacks/updateStack", {'stackid': stackid, 'stackname': view.stack.input.value})
        });
        view.stack.delete.addEventListener("click", function () {
            serverpost("/webapi/stacks/deleteStack", {'stackid': stackid})
        })
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

    /*
    * Server post
    * @param url of the server to which the post request points
    * @param obj which shall be posted to the server
    * @param func function to call after successfull post
    * */
    function serverpost(url, obj, func) {
        fetch(url, {
            method: 'post',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(obj)
        })
            .then(res => res.json())
            .then(function (response) {
                if (typeof func == "function")
                    func(response);
            })
            .catch(function (error) {
                console.log('Request failed', error);
            });
    }
});