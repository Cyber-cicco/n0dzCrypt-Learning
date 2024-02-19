document.querySelectorAll('[data-openModal]').forEach(el => {
    el.addEventListener("click", openModal);
    el.addEventListener("keydown", (e) => {
        if(e.key === "Enter") openModal()
    })
})

function openModal(){
    document.querySelector('#modal-right').classList.add("modal");
    document.querySelector('#modal-bg').removeAttribute("hidden")
}

function closeModal(){
    document.querySelector('#modal-right').classList.remove("modal");
    document.querySelector('#modal-bg').setAttribute("hidden", "true")
}

function openModalBot() {
    document.querySelector('#modal-bot').classList.add("modal-bot");
    document.querySelector('#modal-bg').removeAttribute("hidden")
}
function closeModalBot(){
    document.querySelector('#modal-bot').classList.remove("modal-bot");
    document.querySelector('#modal-bg').setAttribute("hidden", "true")
}
