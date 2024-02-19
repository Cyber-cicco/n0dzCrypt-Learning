const modalSide = document.querySelector("#modal-changement");
const modalBg = document.querySelector("#modal-bg");

const openBtns = Array.prototype.slice.call(document.querySelectorAll("[data-open-modal]"));
const closeBtns = Array.prototype.slice.call(modalSide.querySelectorAll("[data-close-modal]"));

for(const btn of openBtns) {
    btn.addEventListener("click", () => {
        modalSide.classList.add("modal");
        modalBg.removeAttribute("hidden")
    })
}

for (const btn of closeBtns) {
    btn.addEventListener("click", () => {
        modalSide.classList.remove("modal");
        modalBg.setAttribute("hidden", "true");
    })
}

