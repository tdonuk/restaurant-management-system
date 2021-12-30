initDropdowns();

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


let itemList = new Array();

let cart;
let itemListElement;
let priceField;
let cartTotalPrice;
let tableField;
let saveOrderButton;

initCartUtils();

function initCartUtils() {
     cart = document.getElementById('cart');
     itemListElement = document.getElementById("itemList");
     priceField = document.getElementById("cartPrice");
     cartTotalPrice = document.getElementById("cartPrice");
     tableField = document.getElementById("tableNumberField");

     saveOrderButton = document.getElementById("saveOrderButton");
     saveOrderButton.onclick = function () {
         saveOrder();
     }
}

function removeItemFromCart(item) {
    const index = itemList.indexOf(item);

    itemList.splice(index, 1);
}

function clearCart() {
    itemList.splice(0, itemList.length);
    itemListElement.innerHTML = "";
    priceField.innerText = '0.00';
}

function createCartItem(item) {
    const cartItem = document.createElement("div");
    cartItem.className = "cart-item";

    const itemId = document.createElement("span");
    itemId.className = "item-id";
    itemId.innerText = item["product"]["productId"];

    const itemName = document.createElement("span");
    itemName.className = "item-name";
    itemName.innerText = item["product"]["name"];

    const itemPrice = document.createElement("span");
    itemPrice.className = "item-price";
    itemPrice.innerText = item["product"]["price"];

    const removeButton = document.createElement("span");
    removeButton.className = "item-remove";
    removeButton.innerText = "X";
    removeButton.onclick = function (e) {
        if (e.target === removeButton) {
            cartItem.remove();
            removeItemFromCart(item);
            updateCartPrice(-1 * item["product"]["price"]);
        }
    }

    cartItem.appendChild(itemId);
    cartItem.appendChild(itemName);
    cartItem.appendChild(itemPrice)
    cartItem.appendChild(removeButton);

    itemListElement.appendChild(cartItem);

    updateCartPrice(item["price"]);
}

function updateCartPrice(price) {
    const currentPrice = parseFloat(cartTotalPrice.innerText);

    cartTotalPrice.innerText = (currentPrice + price).toFixed(2);
}

function saveOrder() {
    if(! Number.isFinite(parseInt(tableField.value)) || tableField.value === "") {
        createModalMessage("Error", "Invalid input", "Please enter a valid table number", "error");
        return;
    }

    if(! itemList.length > 0) {
        createModalMessage("Error", "Invalid input", "Please select at least 1 item to the cart.", "error");
        return;
    }

    const tableId = parseInt(tableField.value);

    const tableControl = new XMLHttpRequest(); // check for if a table is exists with given table id
    tableControl.open("get", "/api/table/"+tableId);

    tableControl.onload = function () {
        const response = JSON.parse(this.responseText);
        if(this.status === 200) {

            const orderObj = {
                items: itemList,
                totalPrice: parseFloat(priceField.innerText),
                table: response,
                orderDate: new Date()
            }

            sendSaveOrderRequest(orderObj);
        }
        else {
            createModalMessage("Error", "Transaction failed", response["response"], "error");
        }
    }
    tableControl.send();

}

function sendSaveOrderRequest(orderObj) {
    const request = new XMLHttpRequest();
    request.open("POST", "/api/order/save");
    request.onload = function () {
        const response = JSON.parse(this.responseText);

        if(this.status === 200) {
            const modal = createModalMessage("Success", "Transaction completed", response["response"], "success");
            modal.querySelector("#okButton").onclick = function () {
                modal.remove();
                window.location = window.location;
                clearCart();
            }
        }
        else {
            createModalMessage("Error", "Transaction failed", response["response"], "error");
        }
    }
    request.setRequestHeader("Content-Type", "application/json");
    request.send(JSON.stringify(orderObj));
}

function handleQuantities(itemObj) {
    for(let item of itemList) {
        if(item["product"]["productId"] === itemObj["product"]["productId"]) {
            item["quantity"] += 1;
            createCartItem(itemObj);
            return;
        }
    }
    itemList.push(itemObj);
    createCartItem(itemObj);
}

function addItemToCart(itemId) {
    const request = new XMLHttpRequest();
    request.open("GET", "/api/product/"+itemId);
    request.onload = function () {
        if(this.status === 200) {
            const product = JSON.parse(this.responseText);

            const itemObj = {
                product: product,
                quantity: 1
            }

            console.log(itemObj);

            handleQuantities(itemObj);
        } else {
            createModalMessage("Error", "Connection Error", "Not able to add item to the cart. Please be sure that the server is online and your connection is ok.", "error");
        }
    }
    request.send();
}

function filterByDateInterval () {
    const interval = {
        startDate: new Date(),
        endDate: new Date()
    }

    interval.startDate = document.querySelector("#startDate").value;
    interval.endDate = document.querySelector("#endDate").value;

    window.location = "/orders/filter/interval"+"?"+"start="+interval.startDate+"&"+"end="+interval.endDate;
}

function filterByItem() {
    const name = document.querySelector("#itemName").innerText;
    window.location = "/orders/filter/item"+"?"+"name="+name;
}