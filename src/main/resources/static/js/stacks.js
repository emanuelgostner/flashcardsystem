document.addEventListener("DOMContentLoaded", function(event) {
    // Fetch Stacks from api and assign them to a handlebars template
    fetch('./webapi/stacks/getAllStacks')
      .then(
        function(response) {
          if (response.status !== 200) {
            console.log('Looks like there was a problem. Status Code: ' +
              response.status);
            return;
          }
          response.json().then(function(data) {
              var source = document.getElementById("stacks-template").innerHTML;
              var template = Handlebars.compile(source);
              var html = template(data);
              document.getElementsByClassName("stackContainer")[0].innerHTML = html;
          });
        }
      )
      .catch(function(err) {
        console.log('Fetch Error :-S', err);
      });
    //Post data to server
    fetch('./webapi/stacks/addStack', {
        method: 'post',
        headers: {
          "Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
        },
        body: 'foo=bar&lorem=ipsum'
      })
      .then(json)
      .then(function (data) {
        console.log('Request succeeded with JSON response', data);
      })
      .catch(function (error) {
        console.log('Request failed', error);
      });
});