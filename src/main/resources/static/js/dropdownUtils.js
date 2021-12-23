let dropdowns;

function initDropdowns() {
    dropdowns = document.getElementsByClassName("dropdown");

    for (let dropdown of dropdowns) {
        const content = dropdown.querySelector(".dropdown-content");

        dropdown.onclick = function (e) {
            content.classList.toggle("dropdown-expanded");
            if(e.target.className === "dropdown-item") {
                dropdown.querySelector(".dropdown-text").innerText = e.target.innerText;
                content.classList.remove("dropdown-expanded")
            }
        }

    }
}

window.onload = function () {
    initDropdowns();
}

window.onclick = function (e) {
    if(!e.target.className.includes("dropdown") && !e.target.parentElement.className.includes("dropdown")) {
        for (let dropdown of dropdowns) {
            dropdown.querySelector(".dropdown-content").classList.remove("dropdown-expanded");
        }
    }
}

function createDropdownContent(list) {
    const div = document.createElement("div");
    div.className = "dropdown-content";

    for (let item of list) {
        let option = document.createElement("p");
        option.className = "dropdown-item";

        option.innerText = item;

        div.appendChild(option);
    }

    return div.innerHTML;
}