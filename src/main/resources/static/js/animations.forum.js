document.querySelectorAll("#sujet").forEach(el => {
    const rotateButton = el.querySelector('[data-rotatable]')
    const subMenu = el.querySelector(".wrapper")
    const openMenu = (e) => {
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
        for (let i = 0; i < subMenu.children.length; i++) {
            subMenu.children.item(i).setAttribute("tabindex", "1");
        }
    }
    rotateButton.addEventListener("click", openMenu)
    rotateButton.addEventListener("keypress", (e) => {if(e.key === "Enter") openMenu(e)})
})
