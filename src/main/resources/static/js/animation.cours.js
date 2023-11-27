function carousel(){
    const coursDiv = document.getElementById("cours-content")
    const qaDiv = document.getElementById("question-answer")
    if(coursDiv.classList.contains("carouseled")){
        coursDiv.classList.remove("carouseled")
        qaDiv.classList.remove("carouseled")
        coursDiv.classList.add("decarouseled")
        qaDiv.classList.add("decarouseled")
        return
    }
    coursDiv.classList.remove("decarouseled")
    qaDiv.classList.remove("decarouseled")
    coursDiv.classList.add("carouseled")
    qaDiv.classList.add("carouseled")
}