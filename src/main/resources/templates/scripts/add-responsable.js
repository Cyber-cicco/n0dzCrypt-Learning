/*<![CDATA[*/
const idSession = /*[[${idSession}]]*/ -1;

const form = document.querySelector("#form-responsable");
const dataList = form.querySelector("datalist");
const options = dataList.querySelectorAll("option");
const respontable = document.querySelector("#respontable");
const responListe = document.querySelector("#responsables-liste")

form.addEventListener("submit", (e) => {
    e.preventDefault();
    const choice = form.querySelector("#responsables-choice").value;
    let responsableId = -1; 
    for(const option of options) {
        if(choice === option.value) {
            responsableId = option.getAttribute("data-value");
        }
    }
    if (responsableId !== -1) {
        htmx.ajax(
            'POST',
            `/admin/responsables?idSession=${idSession}&idResponsable=${responsableId}`,
            {
                handler : swapTable
            }
        );
    }
})

function swapTable(target, response) {
    const responseHtml = response.xhr.responseText;
    const doc = document.createElement("div");
    doc.innerHTML = responseHtml;
    const table = doc.querySelector("#respontable");
    const liste = doc.querySelector("#responsables-liste");
    respontable.innerHTML = table.innerHTML;
    responListe.innerHTML = liste.innerHTML;
}
