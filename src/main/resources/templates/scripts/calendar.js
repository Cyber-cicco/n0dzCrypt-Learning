/*Variable contenant les éléments pouvant se drag and drop*/
drake = undefined;

/*Peut être l'occasion d'obtenir l'un des succès cachés ?*/
/*<![CDATA[*/
admin = /*[[${_user.isAdministrateur()}]]*/ false;
idSession = /*[[${idSession}]]*/ -1;
/*]]>*/

/*Permet d'initialiser les éléments pouvant se drag and drop*/
function reInit() {

    if(drake) drake.destroy();
    const newDragabble1 = Array.prototype.slice.call(document.querySelectorAll("[data-draggable1]"))
    const newDragabble2 = Array.prototype.slice.call(document.querySelectorAll("[data-draggable2]"))
    drake = dragula(newDragabble1.concat(newDragabble2));
    drakeListen(drake);
}

/*Définie les appels AJAX effectués par HTMX lors du drop*/
function drakeListen(drake) {

        drake.on("drop", (el, target, source, sibling) => {
            console.log(admin)

            if(admin) {
                if(target.getAttribute("data-draggable2")) {
                    const date = target.getAttribute("data-draggable2");
                    const id = el.getAttribute("id");
                    htmx.ajax('POST', `/admin/session/cours?date=${date}&id=${id}&idSession=${idSession}`, '#' + target.getAttribute("id")).then(() => {
                        reInit();
                        return;
                    });
                }

                if(target.getAttribute("data-draggable1") && el.getAttribute("data-dragged")) {
                    const date = el.getAttribute("data-dragged");
                    const id = el.getAttribute("id");
                    htmx.ajax('DELETE',  `/admin/session/cours?id=${id}&idSession=${idSession}`, '#cours-a-prevoir').then(() => {
                        reInit();
                        return;
                    });
                }
            } else {
                if(target.getAttribute("data-draggable2")) {
                    const date = target.getAttribute("data-draggable2");
                    const id = el.getAttribute("id");
                    htmx.ajax('POST', '/agenda/cours?date=' + date + '&id=' + id, '#' + target.getAttribute("id")).then(() => {
                        reInit();
                        return;
                    });
                }

                if(target.getAttribute("data-draggable1") && el.getAttribute("data-dragged")) {
                    const date = el.getAttribute("data-dragged");
                    const id = el.getAttribute("id");
                    htmx.ajax('DELETE', '/agenda/cours?date=' + date + '&id=' + id, '#cours-a-prevoir').then(() => {
                        reInit();
                        return;
                    });
                }
            }
        })
}

reInit();
