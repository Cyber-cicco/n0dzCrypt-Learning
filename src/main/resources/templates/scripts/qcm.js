let qcm = undefined;
let index = 0;
let listIndex
async function getQCM() {
    const data = document.querySelector("#chapitre-cours");
    const id = data.getAttribute("data-id")
    const res = await fetch(`/api/v1/qcm/data?id=${id}`);
    const qcmJson = await res.json();
    qcm = qcmJson
    return qcm 
}

function irrigateQCMContent(qcm, index) {
    const content = document.querySelector("#qcm-content");
    content.innerHTML = `
        <div class="">
            ${qcm.questions[index].libelle}
        </div>
    `
}
