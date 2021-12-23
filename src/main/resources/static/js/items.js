
function addItem(itemId) {
    alert(itemId);

    //TODO
}

function createStockUpdatePopup(itemId) {
    const body = '' +
        '<label>Amount:<input id="amountField" type="number" min="1"></label>';

    const modal = createModalFromBody(body, "Add Stock for Item "+itemId);
    modal.querySelector("#okButton").onclick = function () {
        const amount = parseInt(modal.querySelector("#amountField").value);
        sendUpdateStockRequest(itemId, amount);
        modal.remove();
    }
    modal.querySelector("#cancelButton").onclick = function () {
        modal.remove();
    }
}

function sendUpdateStockRequest(itemId, amount) {
    const request = new XMLHttpRequest();
    const params = "?amount="+amount;
    request.open("get", "/api/item/"+itemId+"/stock"+params);
    request.onload = function () {
        const response = JSON.parse(this.responseText);

        if(this.status === 200) {
            const modal = createModalMessage("Success", "Stock updated", response["response"],"success");
            modal.querySelector("#okButton").onclick = function () {
                modal.remove();
                window.location = "/items";
            }
        }
        else {
            const modal = createModalMessage("Fail", "Stock update failed", response["response"],"error");
            modal.querySelector("#okButton").onclick = function () {
                modal.remove();
            }
        }
    }
    request.send();
}