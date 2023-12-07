const dataDragable1 = Array.prototype.slice.call(document.querySelectorAll("[data-draggable1]"))
const dataDragable2 = Array.prototype.slice.call(document.querySelectorAll("[data-draggable2]"))
let drake = dragula(Array.prototype.slice.call(dataDragable1.concat(dataDragable2)));

function reInit() {
    setTimeout(() => {
        drake.destroy();
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
                htmx.ajax('POST', '/agenda/cours?date=' + date + '&id=' + id, '#calendar').then(() => {
                    reInit();
                });
            }
        })
}
drakeListen(drake);
