const boxes = document.getElementsByClassName("nav-item");


Array.prototype.forEach.call(boxes, function(e) {
    e.addEventListener("click", function(event) {
        const element = event.srcElement;

        element.style.color = "blue";
        element.style.border = "1px 0 solid black";

        toDefault(element);
    })
})

function toDefault(selectedElement) {
    Array.prototype.forEach.call(boxes, function(e) {
        if(e != selectedElement) {
            e.style.color = "black";
            e.style.border = "";
        }
    })
}
