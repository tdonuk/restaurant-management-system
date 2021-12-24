
function deleteUser(userId) {
    const modal = createModalMessage("Warning", "Deleting User","You are deleting a user record from system. To confirm, please click 'Ok'","warning");
    modal.querySelector("#okButton").onclick = function () {
        modal.remove();
        sendDeleteRequest(userId);
    }
}

function sendDeleteRequest(userId) {
    const request = new XMLHttpRequest();
    request.open("delete", "/api/user/"+userId+"/delete");
    request.onload = function () {
        const response = JSON.parse(this.responseText);
        if(this.status === 200) {
            const modal = createModalMessage("Success", "Transaction completed", response["response"], "success");
            modal.querySelector("#okButton").onclick = function () {
                modal.remove();
                window.location = "/employees";
            }
        }
        else {
            const modal = createModalMessage("Error", "Transaction failed", response["response"], "error");
            modal.querySelector("#okButton").onclick = function () {
                modal.remove();
            }
        }
    }
    request.send();
}