
function deleteOrder(orderId) {
    const modal = createModalMessage("Warning", "You are deleting an order", "This operation can not be undone. Click 'Ok' to confirm.", "warning");
    modal.querySelector("#okButton").onclick = function() {
        modal.remove();
        sendDeleteRequest(orderId);
    }

}

function sendDeleteRequest(orderId) {
    const request = new XMLHttpRequest();
    request.open("delete", "/api/order/"+orderId);
    request.onload = function () {
        const response = JSON.parse(this.responseText);

        if(this.status === 200) {
            const modal = createModalMessage("Success","Completed",response["response"], "success");
            const okButton = modal.querySelector("#okButton");
            okButton.onclick = function () {
                modal.remove();
                window.location = "/orders";
            }
        }
        else {
            createModalMessage("Error","An error has occurred",response["response"], "error");
        }
    }
    request.send();
}