function openPost(){
    const newPost =  document.querySelector("#post-new");
    newPost.removeAttribute("hidden");
    document.getElementById("message").focus();
}

function closePost(){
    document.querySelector("#post-new").setAttribute("hidden", "true")
}