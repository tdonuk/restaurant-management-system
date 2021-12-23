
let itemList = [];

let cart = document.getElementById('cart');
let itemListElement = document.getElementById("itemList");
let priceField = document.getElementById("cartPrice");
let cartTotalPrice = document.getElementById("cartPrice");
let tableField = document.getElementById("tableNumberField");

function removeItemFromCart(item) {
    const index = itemList.indexOf(item);

    itemList.splice(index, 1);
    console.log(itemList);
}

function clearCart() {
    itemList.splice(0, itemList.length);
    itemListElement.innerHTML = "";
    priceField.innerText = '0.00';
}

function createCartItem(item) {
    const cartItem = document.createElement("div");
    cartItem.className = "cart-item";

    itemList.push(item);

    const itemId = document.createElement("span");
    itemId.className = "item-id";
    itemId.innerText = item["itemId"];

    const itemName = document.createElement("span");
    itemName.className = "item-name";
    itemName.innerText = item["name"];

    const itemPrice = document.createElement("span");
    itemPrice.className = "item-price";
    itemPrice.innerText = item["price"];

    const removeButton = document.createElement("span");
    removeButton.className = "item-remove";
    removeButton.innerText = "X";
    removeButton.onclick = function (e) {
        if (e.target === removeButton) {
            cartItem.remove();
            removeItemFromCart(item);
            updateCartPrice(-1 * item["price"]);
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

const saveOrderButton = document.getElementById("saveOrderButton");
saveOrderButton.onclick = function () {
    saveOrder();
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

    const orderObj = {
        items: itemList,
        totalPrice: parseFloat(priceField.innerText),
        tableId: parseInt(tableField.value),
        orderDate: new Date()
    }

    console.log(orderObj.totalPrice);

    const request = new XMLHttpRequest();
    request.open("POST", "/api/order/save");
    request.onload = function () {
        const response = JSON.parse(this.responseText);

        if(this.status === 200) {
            const modal = createModalMessage("Success", "Completed", response["response"], "success");
            modal.querySelector("#okButton").onclick = function () {
                modal.remove();
                window.location = "/orders";
            }
        }
        else {
            createModalMessage("Error", "An error has occurred", response["response"], "error");
        }
    }
    request.setRequestHeader("Content-Type", "application/json");
    request.send(JSON.stringify(orderObj));

    clearCart();
}

async function addItemToCart(itemId) {
    const request = new XMLHttpRequest();
    request.open("GET", "/api/item/"+itemId);
    request.onload = function () {
        if(this.status === 200) {
            const itemObj = JSON.parse(this.responseText);
            createCartItem(itemObj);
        } else {
            createModalMessage("Error", "Connection Error", "Not able to add item to the cart. Please be sure that the server is online and your connection is ok.", "error");
        }
    }
    request.send();
}