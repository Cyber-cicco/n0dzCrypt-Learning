let drake = undefined;
function reInit() {
    setTimeout(() => {
        if(drake) drake.destroy();
        const newDragabble1 = Array.prototype.slice.call(document.querySelectorAll("[data-draggable1]"))
        const newDragabble2 = Array.prototype.slice.call(document.querySelectorAll("[data-draggable2]"))
        drake = dragula(newDragabble1.concat(newDragabble2));
        drakeListen(drake);
    }, 100)
}
function drakeListen(drake) {
        drake.on("drop", (el, target, source, sibling) => {
            if(target.getAttribute("data-draggable2")) {
                const date = target.getAttribute("data-draggable2");
                const id = el.getAttribute("id");
                htmx.ajax('POST', '/agenda/cours?date=' + date + '&id=' + id, '#insert').then(() => {
                    reInit();
                    return;
                });
            }
            console.log(target.getAttribute("data-draggable1"))
            console.log(el.getAttribute("data-dragged"))
            if(target.getAttribute("data-draggable1") && el.getAttribute("data-dragged")) {
                const date = el.getAttribute("data-dragged");
                const id = el.getAttribute("id");
                htmx.ajax('DELETE', '/agenda/cours?date=' + date + '&id=' + id, '#insert').then(() => {
                    reInit();
                    return;
                });
            }
        })
}
