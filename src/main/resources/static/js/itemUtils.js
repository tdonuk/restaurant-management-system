function deleteItem(itemId) {
    const modal = createModalMessage("Warning","Item with id:"+itemId+" is deleting", "To confirm, please click 'Ok'.","warning");
    modal.querySelector("#okButton").onclick = function () {
        modal.remove();
        sendDeleteRequest(itemId);
    }
}

function sendDeleteRequest(itemId) {
    const request = new XMLHttpRequest();
    request.open("delete","/api/item/"+itemId);
    request.onload = function () {
        const response = JSON.parse(this.responseText);
        if(this.status === 200) {
            const modal = createModalMessage("Success","Transaction completed", response["response"],"success");
            modal.querySelector("#okButton").onclick = function () {
                modal.remove();
                window.location = "/items";
            }
        }
        else {
            createModalMessage("Error","Transaction failed", response["response"],"error");
        }
    }
    request.send();
}

function saveItemFromFormData() {
    const form = document.querySelector("#itemForm");
    const data = new FormData(form);

    const itemObj = {
        name: "",
        description: "",
        type: "",
        price: 0,
        stock: 0
    }

    itemObj.name = data.get("name");
    itemObj.description = data.get("description");
    itemObj.type = data.get("type");
    itemObj.price = data.get("price");
    itemObj.stock = data.get("stock");

    const request = new XMLHttpRequest();
    request.open("POST", "/api/item/save");
    request.onload = function () {
        const response = JSON.parse(this.responseText);
        if(this.status === 200) {
            const modal = createModalMessage("Success", "Transaction completed", response["response"],"success");
            modal.querySelector("#okButton").onclick = function () {
                modal.remove();
                window.location = "/items";
            }
        }
        else {
            const modal = createModalMessage("Error", "Transaction failed", response["response"],"error");
        }
    }
    request.setRequestHeader("Content-Type","application/json");
    request.send(JSON.stringify(itemObj));
}

const stockAmountField = document.querySelector("#stockAmountField");
function addStock(itemId) {
    const amount = parseInt(stockAmountField.innerText);
}
