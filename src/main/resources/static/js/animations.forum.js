document.querySelectorAll("#sujet").forEach(el => {
    const rotateButton = el.querySelector('[data-rotatable]')
    const subMenu = el.querySelector(".wrapper")
    rotateButton.addEventListener("click", (e) => {
        console.log("caca")
        if(rotateButton.classList.contains("rotatingButton")){
            rotateButton.classList.remove("rotatingButton")
            rotateButton.classList.add("reverseRotationButton")
            subMenu.classList.remove("display-menu")
            subMenu.classList.add("collapse-menu")
            return
        }
        rotateButton.classList.remove("reverseRotationButton")
        rotateButton.classList.add("rotatingButton")
        subMenu.classList.remove("collapse-menu")
        subMenu.classList.add("display-menu")
    })
})
