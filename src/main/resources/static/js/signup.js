const signupButton = document.getElementById("signup-submit");
const inputs = document.getElementsByTagName("input");

signupButton.onclick = function(e) {
    let isOk = true;
    for(let input of inputs) {
        if(input.value === "") {
            isOk = false;
            break;
        }
    }

    if(!isOk) {
        const error = createModal("error", "Please fill all of the information in the form", "Error", "Incorrect Input");
        appendPopup(error)
    };

}