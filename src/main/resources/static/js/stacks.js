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
                    document.getElementsByClassName("addNewStack")[0].addEventListener('click', function(e) {
                        data = {
                            'stackname': '23123',
                            'stackid':20,
                            'cardCount':2
                        }
                        console.log(e);
                        target.parentNode.insertBefore(HandlebarsCardTemplate(data))
                        postNewStack(document.getElementsByClassName("input")[0].innerHTML())
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
                console.log('Request succeeded with JSON response', data);
            })
            .catch(function (error) {
                console.log('Request failed', error);
            });
    }
});