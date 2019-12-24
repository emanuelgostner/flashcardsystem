document.addEventListener("DOMContentLoaded", function(event) {
    Promise.all([
      fetch("./webapi/stacks/getAllStacks"),
      fetch("http://localhost:3000/contactlist/get"),
      fetch("http://localhost:3000/itemgroup/get")
    ]).then(([items, contactlist, itemgroup]) => {

    }).catch((err) => {
        console.log(err);
    });
})