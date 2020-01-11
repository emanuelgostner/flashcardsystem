document.addEventListener("DOMContentLoaded", function(event) {
    var stackid = /stacks\/(\d*)\//g.exec(window.location.href)[1];
    var view = {
        "cardsContainer": document.getElementsByClassName("cardsContainer")[0],
        "updateStack": {
            "input": document.getElementsByClassName("updateStackInput")[0],
            "button": document.getElementsByClassName("updateStackButton")[0]
        },
        "newCard": {
            "question": document.getElementsByClassName("cardAnswer")[0],
            "answer": document.getElementsByClassName("cardQuestion")[0]
        }
    }


       // function postNewStack(newStackname) {
       //     this.newStackname = newStackname;
       //     fetch('./webapi/stacks/updateStack', {
       //         method: 'post',
       //         headers: {
       //             'Accept': 'application/json, text/plain, */*',
       //             'Content-Type': 'application/json'
       //         },
       //         body: JSON.stringify({
       //                             "stackid": 2,
       //                             "stackname": view.updateStack.input.value
       //                            })
       //     })
       //         .then(res => res.json())
       //         .then(function (data) {
       //             var newCardData = {
       //              "stackid": 2,
       //              "stackname": view.updateStack.input.value
       //             }
       //             var my_elem = document.getElementsByClassName('addNewStack')[0];
       //             var newCard = document.createElement('span');
       //             newCard.innerHTML = HandlebarsCardTemplate(newCardData);
       //             my_elem.parentNode.insertBefore(newCard, my_elem);
       //             document.querySelector(".addNewStack input").value = "";
       //             console.log('Request succeeded with JSON response', data);
       //         })
       //         .catch(function (error) {
       //             console.log('Request failed', error);
       //         });
       // }

})