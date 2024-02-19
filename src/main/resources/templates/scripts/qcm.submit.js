function submitResponse(event) {
    event.preventDefault()
    const chapitreCours = document.querySelector("#chapitre-cours");
    const idQCM = chapitreCours.getAttribute("data-id")
    const idQuestion = chapitreCours.getAttribute("data-id-question")
    const choices = document.querySelectorAll(".rep")
    let requestObject = []
    for(const choice of choices) {
        requestObject.push({
            id : choice.getAttribute("data-id"),
            value : choice.checked
        })
    }
    fetch(`/cours/qcm/response?id=${idQCM}&idQuestion=${idQuestion}`,  {
        method : 'POST',
        body : JSON.stringify(requestObject),
        headers: {
            "Content-Type": "application/json",
            // 'Content-Type': 'application/x-www-form-urlencoded',
        },
    }).then((content) => {
        return content.text()
    }).then(html => {
        htmx.find("#router-cours").innerHTML = html
    })
}
