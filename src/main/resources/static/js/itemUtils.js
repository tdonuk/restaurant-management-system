
/* DELETE ITEM */
function deleteItem(itemId) {
    const modal = createModalMessage("Warning","Item with id:"+itemId+" is deleting", "To confirm, please click 'Ok'.","warning");
    modal.querySelector("#okButton").onclick = function () {
        modal.remove();
        sendDeleteRequest(itemId);
    }
}


function sendDeleteRequest(itemId) {
    const url = "/api/product/"+itemId;

    function showResult(response) {
        const modal = createModalMessage("Success","Transaction completed", response["response"],"success");
        modal.querySelector("#okButton").onclick = function () {
            modal.remove();
            window.location = "/products";
        }
    }

    createRequest("delete", url, showResult)
}
/* - */

/* SAVE ITEM */
function saveItemFromFormData() {
    const form = document.querySelector("#itemForm");
    const data = new FormData(form);

    const itemObj = {
        name: "",
        description: "",
        type: "",
        price: 0,
        stock: 0,
        isStockRequired: false
    }

    itemObj.name = data.get("name");
    itemObj.description = data.get("description");
    itemObj.type = data.get("type");
    itemObj.price = data.get("price");
    itemObj.stock = data.get("stock");
    itemObj.isStockRequired = document.getElementById("isStockRequired").checked;

    console.log(itemObj);

    const url = "/api/product/save";

    function showResult(response) {
        const modal = createModalMessage("Success", "Transaction completed", response["response"], "success");
        modal.querySelector("#okButton").onclick = function () {
            modal.remove();
            //window.location = "/products";
        }
    }
    createRequest("post", url, showResult, JSON.stringify(itemObj));
}
/* - */

/* UPDATE STOCK */
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
    const params = "?amount="+amount;
    const url = "/api/product/"+itemId+"/stock"+params;

    function showResult(response) {
        const modal = createModalMessage("Success", "Stock updated", response["response"],"success");
    }

    createRequest("get", url, showResult);
}
/* - */

/* UPDATE PRICE */
function updatePrice(itemId) {
    const body = '' +
        '<label>New Price:<input id="priceField" type="number" min="1" step="0.01"></label>';

    const modal = createModalFromBody(body, "Update Price");
    modal.querySelector("#okButton").onclick = function () {
        const newPrice = parseInt(modal.querySelector("#priceField").value);
        modal.remove();
        sendUpdatePriceRequest(itemId, newPrice);
    }
}

function sendUpdatePriceRequest(itemId, newPrice) {
    const url = "/api/product/"+itemId+"/price";

    function showResult(response) {
        const modal = createModalMessage("Success", "Transaction completed", response["response"], "success");
    }

    createRequest("post", url, showResult, newPrice);
}
/* - */

/* UPDATE DESCRIPTION */
function updateDescription(itemId) {
    const body = '' +
        '<label>New Description:<input id="descriptionField" type="text" minlength="1"></label>';

    const modal = createModalFromBody(body, "Update Description");
    modal.querySelector("#okButton").onclick = function () {
        const description = modal.querySelector("#descriptionField").value;
        modal.remove();
        sendUpdateDescriptionRequest(itemId, description);
    }
}

function sendUpdateDescriptionRequest(itemId, description) {
    const url = "/api/product/"+itemId+"/description";

    function showResult(response) {
        const modal = createModalMessage("Success", "Transaction completed", response["response"], "success");
    }

    createRequest("post", url, showResult, description);
}
/* - */

function createRequest(method = "get", url, onloadFunction, body = null) {
    const request = new XMLHttpRequest();
    request.open(method, url);
    request.onload = function () {
        const response = JSON.parse(this.responseText);
        if(this.status === 200) {
            onloadFunction(response);
        }
        else if(this.status === 403) {
            window.location = "/unauthorized";
        }
        else {
            createModalMessage("Error", "Transaction failed", response["response"], "error");
        }
    };

    if(body !== null) {
        request.setRequestHeader("Content-Type", "application/json");
        request.send(body);
    }
    else request.send();
}

