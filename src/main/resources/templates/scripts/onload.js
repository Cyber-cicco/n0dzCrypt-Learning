var loaded = true;

function hideLoad() {
    loaded = true
    let loading = document.querySelector("#loading")
    if(!loading.classList.contains("hidden")) {
        loading.classList.add("hidden")
    }
    
}
function showLoad() {
    loaded = false;
    setTimeout(() => {
        if(!loaded) {
            document.querySelector("#loading").classList.remove("hidden")
        }
    }, 500)
    
}
