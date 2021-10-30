
const menuBox = document.getElementById("menubox");
const beverageBox = document.getElementById("beveragebox");
   
window.addEventListener("load",  async function(e) {
    const mealsData = await getData("http://localhost:8080/items/type/meal");
    let meals = [];
    meals = JSON.parse(await mealsData.text());

    const beveragesData = await getData("http://localhost:8080/items/type/beverage");
    let beverages = [];
    beverages = JSON.parse(await beveragesData.text());

    meals.forEach(item => {
        const name = document.createElement("h3");
        name.className = "item-name";
        name.innerText = item.name;

        const desc = document.createElement("span");
        desc.className = "item-price";
        desc.innerText = item.description;

        const price = document.createElement("h4");
        price.className = "item-price";
        price.innerText = item.price + "₺";

        const box = this.document.createElement("div");
        box.className = "item-box";

        box.appendChild(name);
        box.appendChild(desc);
        box.appendChild(price);

        menuBox.appendChild(box);
    });

    beverages.forEach(item => {
        const name = document.createElement("h3");
        name.className = "beverage-name";
        name.innerText = item.name;

        const desc = document.createElement("h5");
        desc.className = "beverage-price";
        desc.innerText = item.description;

        const price = document.createElement("h4");
        price.className = "beverage-price";
        price.innerText = item.price + "₺";

        const box = this.document.createElement("div");
        box.className = "item-box";

        box.appendChild(name);
        box.appendChild(desc);
        box.appendChild(price);

        beverageBox.appendChild(box);
    });

});


async function getData(url)
{
    const response = fetch(url);

    return response;
}