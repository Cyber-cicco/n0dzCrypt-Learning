/*Variable contenant les éléments pouvant se drag and drop*/
drake = undefined;

/*Permet d'initialiser les éléments pouvant se drag and drop*/
function reInit() {

    if(drake) drake.destroy();
    const newDragabble1 = Array.prototype.slice.call(document.querySelectorAll("[data-draggable1]"))
    drake = dragula(newDragabble1);
    drakeListen(drake);
}

function drakeListen(drake) {
    drake.on("drop", (el, target, source, sibling) => {
        //convert nodeList to array
        const children = Array.prototype.slice.call(target.children);
        const ordre = children.indexOf(el) + 1;
        const id = el.getAttribute("data-id")
        htmx.ajax('PATCH', '/cours/admin/qcm/question/ordre?ordre=' + ordre + '&id=' + id, '#questions-liste').then(() => {
            reInit();
            return;
        });
    })
}
