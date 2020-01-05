document.addEventListener("DOMContentLoaded", function(event) {
    var allCardsHtml = document.getElementById("stacks-template").innerHTML;
    var cardHtml = document.getElementById("addStack-template").innerHTML;
    var newCardHtml = document.getElementById("addNewStack-template").innerHTML;
    var HandlebarsStacksTemplate = Handlebars.compile(allCardsHtml);
    var HandlebarsCardTemplate = Handlebars.compile(cardHtml);
    var HandlebarsNewCardTemplate = Handlebars.compile(newCardHtml);

    // Fetch Stacks from api and assign them to a handlebars template
    fetch('./webapi/stacks/getAllStacks')
        .then(
            function (response) {
                if (response.status !== 200) {
                    console.log('Looks like there was a problem. Status Code: ' +
                        response.status);
                    return;
                }
                response.json().then(function (data) {
                    document.getElementsByClassName("stackContainer")[0].innerHTML = HandlebarsStacksTemplate(data);
                    document.querySelector(".addNewStack button").addEventListener('click', function(e) {
                        postNewStack(document.querySelector(".addNewStack input").value)
                    })
                });
            }
        )
      .catch(function(err) {
        console.log('Fetch Error :-S', err);
      });

    //Post data to server
    function postNewStack(newStackname) {
        this.newStackname = newStackname;
        fetch('./webapi/stacks/addStack', {
            method: 'post',
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({'stackname': this.newStackname})
        })
            .then(res => res.json())
            .then(function (data) {
                var newCardData = {
                    "stackid": data,
                    "stackname": this.newStackname,
                    "cardCount": 0
                }
                var my_elem = document.getElementsByClassName('addNewStack')[0];
                var newCard = document.createElement('span');
                newCard.innerHTML = HandlebarsCardTemplate(newCardData);
                my_elem.parentNode.insertBefore(newCard, my_elem);
                document.querySelector(".addNewStack input").value = "";
                console.log('Request succeeded with JSON response', data);
            })
            .catch(function (error) {
                console.log('Request failed', error);
            });
    }
});