var putInHistory;
var uploadPhoto;
var getMarkdownFromFile;

function initTextEditor() {
    let textOptions = document.querySelectorAll("[data-text-option]")
    let bold = document.querySelector("#bold")
    let italic = document.querySelector("#italic")
    let strikethrough = document.querySelector("#strikethrough")
    let ul = document.querySelector("#list")
    let ol = document.querySelector("#ordered-list")
    let undo = document.querySelector("#undo")
    let redo = document.querySelector("#redo")
    let link = document.querySelector("#link")
    let codeLine = document.querySelector("#code-line")
    let table = document.querySelector("#table")
    let title = document.querySelector("#title")
    let titleSelect = document.querySelector("#heading")
    let tableau = document.querySelector("#table")
    let citation = document.querySelector("#quote")
    let codeBlock = document.querySelector("#code-block")
    let writingArea = document.getElementById("text-input")

    const middle = 1;
    const id = new URLSearchParams(window.location.search).get("id");


    const uploadMD = () => {
        const formData = new FormData();
        formData.append("contenu", writingArea.value);
        fetch(`/cours/admin/chapitre/contenu?id=${id}`,  {
            method : 'POST',
            body : formData
        }).then((content) => {
            return content.text()
        }).then(html => {
            htmx.find("#markdown").innerHTML = html
            hljs.highlightAll();
        })
    }

    getMarkdownFromFile = (event) => {
        const file = event.target.files[0];
        const reader = new FileReader();

        reader.onload = function(event) {
            const markdownContent = event.target.result;
            writingArea.value = markdownContent;
            const fileName = file.name.toLowerCase();
            if (fileName.endsWith('.md')) {
                const formData = new FormData();
                formData.append("contenu", markdownContent);
                fetch(`/cours/admin/chapitre/contenu?id=${id}`, {
                    method: 'POST',
                    body: formData
                })
                    .then(response => response.text())
                    .then(html => {
                        htmx.find("#markdown").innerHTML = html
                        hljs.highlightAll();
                    })
                    .catch(error => console.error('Error posting markdown content:', error));
            }
        };

    reader.readAsText(file);
}

    uploadPhoto = () => {

        let photo = document.querySelector("#file-input").files[0];
        const formData = new FormData();
        formData.append("file", photo);
        fetch("/cours/admin/photo", {
            method : 'POST',
            body : formData })
            .then((res) => res.json())
            .then((res) => {
                changeSelection(
                    (contentAsArray) => {
                        contentAsArray[middle] = `![${contentAsArray[middle]}](/photo?name=${res.name})`
                    }, () => {
                        return `![your alt text](/photo?name=${res.name})`
                    }
                )
            })
            .then(() => {
                const formDatas = new FormData()
                formDatas.append("contenu", writingArea.value)
                fetch(`/cours/admin/chapitre/contenu?id=${id}`,  {
                    method : 'POST',
                    body : formDatas
                }).then((content) => {
                    return content.text()
                }).then(html => {
                    htmx.find("#markdown").innerHTML = html
                    hljs.highlightAll();
                })
            })
    }

    let textHistory = [writingArea.value]
    let historyPointer = 0;

    textOptions.forEach(option => {
        option.addEventListener("click", ()=>{
            pushHistory(writingArea.value)
            setTimeout(uploadMD, 100)
        })
    })


    putInHistory = () => {
        const areaValue = writingArea.value;
        pushHistory(areaValue)
    }

    bold.addEventListener("click", (e) => {
        changeSelection(
            (contentAsArray) => {
                if(contentAsArray[middle].startsWith("**") && contentAsArray[middle].endsWith("**")) {
                    contentAsArray[middle] = contentAsArray[middle].replace(/\*\*/g, '');
                } else {
                    contentAsArray[middle] = "**" + contentAsArray[middle] + "**"
                }
            }, () => {
                return "**bold**";
            })
    });

    italic.addEventListener("click", (e) => {
        changeSelection(
            (contentAsArray) => {
                if(contentAsArray[middle].startsWith("*") && contentAsArray[middle].endsWith("*")) {
                    contentAsArray[middle] = contentAsArray[middle].replace(/\*/g, '');
                } else {
                    contentAsArray[middle] = "*" + contentAsArray[middle] + "*"
                }
            }, () => {
                return "*italic*";
            })
    })

    strikethrough.addEventListener("click", (e) => {
        changeSelection(
            (contentAsArray) => {
                if(contentAsArray[middle].startsWith("~~") && contentAsArray[middle].endsWith("~~")) {
                    contentAsArray[middle] = contentAsArray[middle].replace(/~~/g, '');
                } else {
                    contentAsArray[middle] = "~~" + contentAsArray[middle] + "~~"
                }
            }, () => {
                return "~~strikethrough~~";
            })
    })

    list.addEventListener("click", (e) => {
        changeLine(
            (content) => {
                let i = 0;
                for(i = 0; i < content.length && content.charAt(i) == " "; i++){}
                content = content.substring(i);
                if(content.startsWith("- ")) {
                    return content.substring(2);
                } 
                return "- " + content;
            },
            () => {
                return "\n- "
            }
        );
    })

    ol.addEventListener("click", (e) => {
        changeLine(
            (content) => {
                let i = 0;
                for(i = 0; i < content.length && content.charAt(i) == " "; i++){}
                content = content.substring(i);
                if(content.startsWith("1. ")) {
                    return content.substring(2);
                } 
                return "1. " + content;
            },
            () => {
                return "\n1. "
            }
        )
    })

    undo.addEventListener("click", () => {
        if(historyPointer > 0) {
            historyPointer--;
            writingArea.value = textHistory[historyPointer];
            uploadMD();
        }
    })

    redo.addEventListener("click", () => {
        if(historyPointer < textHistory.length - 1) {
            historyPointer++;
            writingArea.value = textHistory[historyPointer]
            uploadMD();
        }
    })

    link.addEventListener('click', () => {
        changeSelection(
            (contentAsArray) => {
                contentAsArray[middle] = "(" + contentAsArray[middle] + ")[https://www.example.com]"
            }, () => {
                return "[your link text](https://www.example.com)"
            }
        )
    })

    codeLine.addEventListener("click", () => {
        changeSelection(
            (contentAsArray) => {
                if(contentAsArray[middle].startsWith("`") && contentAsArray[middle].endsWith("`")) {
                    contentAsArray[middle] = contentAsArray[middle].substring(1, contentAsArray[middle].length)
                    contentAsArray[middle] = contentAsArray[middle].substring(0, contentAsArray[middle].length - 1)
                    return;
                }
                contentAsArray[middle] = "`" + contentAsArray[middle] + "`"
            }, () => {
                return '`print("Hello World")`'
            }
        )
    })

    title.addEventListener('click', () => {
        let heading;
        switch (titleSelect.value) {
            case ("h1") : {
                heading = "#"
                break;
            }
            case ("h2") : {
                heading = "##"
                break;
            }
            case ("h3") : {
                heading = "###"
                break;
            }
            case ("h4") : {
                heading = "####"
                break;
            }
        }
        changeLine(
            (content) => {
                let i = 0;
                for(i = 0; i < content.length && content.charAt(i) == " "; i++){}
                content = content.substring(i);
                if(content.startsWith(heading + " ")) {
                    return content.substring(heading.length);
                } 
                return heading + " " + content;
            },
            () => {
                return "\n" + heading + " "
            }
        )
    })

    tableau.addEventListener('click', () => {
        const del1 = writingArea.selectionStart;
        let areaValue = writingArea.value;
        let text = `
| En tête 1 | En tête 2 | En tête 3 |
|-----------|-----------|-----------|
|row 1 col 1|row 1 col 2|row 1 col 3|
            `
        contentAsArray = [
            areaValue.substring(0, del1),
            text,
            areaValue.substring(del1, areaValue.length),
        ]
        writingArea.value = contentAsArray.join("");
    })

    citation.addEventListener('click', () => {
        changeLine(
            (content) => {
                let i = 0;
                for(i = 0; i < content.length && content.charAt(i) == " "; i++){}
                content = content.substring(i);
                if(content.startsWith("> ")) {
                    return content.substring(2);
                } 
                return "> " + content;
            },
            () => {
                return "\n> "
            }
        )
    })

    codeBlock.addEventListener('click', () => {
        changeLine(
            (content) => {
                return '\n```python\n' + content + '\n```\n';
            },
            () => {
                return '\n```python\nprint("hello world")\n```\n';
            }
        )
    })

    const changeLine = (changer, creer) => {
        let text = writingArea.value;
        const areaValue = text;
        const del1 = writingArea.selectionStart;
        const del2 = writingArea.selectionEnd;
        let contentAsArray = []
        if(del1 != del2) {
            let i;
            for(i = del1; i >= 0 && text.charAt(i) != '\n'; i--) {}
            text = text.substring(i+1, del2);
            text = changer(text);
            contentAsArray = [
                areaValue.substring(0, i+1),
                text,
                areaValue.substring(del2, areaValue.length),
            ]
        } else {
            text = creer()
            contentAsArray = [
                areaValue.substring(0, del1),
                text,
                areaValue.substring(del1, areaValue.length)
            ]
        }
        writingArea.value = contentAsArray.join("");



    }

    const changeSelection = (changer, creer) => {
        let text;
        const del1 = writingArea.selectionStart;
        const del2 = writingArea.selectionEnd;
        text = writingArea.value;
        if(del1 !== del2) {
            let innerText = text.substring(del1, del2)
            let contentAsArray = [
                text.substring(0, del1),
                innerText,
                text.substring(del2, text.length),
            ]
            changer(contentAsArray);
            writingArea.value = contentAsArray.join("");
            return;
        } 
        let content = creer();
        let contentAsArray = [
            text.substring(0, del1),
            content,
            text.substring(del2, text.length),
        ]
        writingArea.value = contentAsArray.join("");
    }


    const pushHistory = (content) => {
        if(textHistory.length > historyPointer + 1) {
            textHistory = textHistory.slice(0, historyPointer+1);
            textHistory.push(content);
            historyPointer = textHistory.length - 1;
            return; 
        }
        if(textHistory.length >= 10) {
            textHistory.unshift()
            textHistory.push(content);
            return;
        }
        textHistory.push(content);
        historyPointer++;
    }

}

initTextEditor();
