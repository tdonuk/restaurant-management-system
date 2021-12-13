let removeButton;
let updateButton;
let addButton;
let refreshButton;

initDropdowns();

removeButton = document.getElementById("removeTableButton");
updateButton = document.getElementById("updateTableButton");
addButton = document.getElementById("addTableButton");
refreshButton = document.getElementById("refreshButton");

let tables;

initTables();

function initTables() {
    tables = document.getElementsByClassName("table-box");
    for(let table of tables) {
        table.onclick = function (e) {
            table.classList.toggle("selected");
        }
    }
}

function createUpdateTableModal() {
    let tableIdList = []; // A list of table Id's that currently exists in the document

    for(let i = 1; i <= tables.length; i++) {
        tableIdList[i-1] = ""+i;
    }

    let statusArray = ["Available", "Out of service", "Full"];

    const body = ''+
        '<table>'+
            '<tr>' +
                '<td>' +
                    '<label>Table: </label>'+
                '</td>'+
                '<td>' +
                    '<div class="dropdown">' +
                        '<span class="dropdown-text">'+tableIdList[0]+'</span>'+
                        '<div class="dropdown-content">' +
                            createDropdownContent(tableIdList) +
                        '</div>'+
                    '</div>'+
                '</td>'+
            '</tr>'+
            '<tr>' +
                '<td>' +
                    '<label>Status: </label>'+
                '</td>'+
                '<td>' +
                    '<div class="dropdown">' +
                        '<span class="dropdown-text">'+statusArray[0]+'</span>'+
                        '<div class="dropdown-content">' +
                            createDropdownContent(statusArray) +
                        '</div>'+
                    '</div>'+
                '</td>'+
            '</tr>'+
        '</table>'+
        '<span class="modal-message">Please select the table and new status and click "Accept"</span>';

    const modal = createModalFromBody(body, "Update Table");

    return modal;
}

function createRemoveTableModal() {
    let tableIdList = []; // A list of table Id's that currently exists in the document

    for(let i = 1; i <= tables.length; i++) {
        tableIdList[i] = ""+i;
    }

    const body = ''+
        '<table>'+
            '<tr>' +
                '<td>' +
                    '<label>Table: </label>'+
                '</td>'+
                '<td>' +
                    '<div class="dropdown">' +
                        '<span class="dropdown-text">1</span>'+
                        '<div class="dropdown-content">' +
                            createDropdownContent(tableIdList) +
                        '</div>'+
                    '</div>'+
                '</td>'+
            '</tr>'+
        '</table>'+
        '<span class="modal-message">You are deleting a table entry from database. This will not cause to lose past records for that table.</span>';

    const modal = createModalFromBody(body, "Remove Table");

    return modal;
}

function createSpinner() {
    const bg = document.createElement("div");
    bg.className = "modal-bg";

    const spinner = document.createElement("div");
    spinner.className = "loader";

    bg.appendChild(spinner);

    document.body.appendChild(bg);

    return bg;
}

function refreshTables() {
    const request = new XMLHttpRequest();

    const spinner = createSpinner();

    request.open("GET", "/fragment/content/tables");
    request.onload = function () {
        if(this.status === 200) {
            const tableContent = this.responseText; // Content is going to replace with #tableContent

            const tableContentSection = document.getElementById("tableContent");
            tableContentSection.remove();

            document.body.querySelector("#tableDetailsSection").insertAdjacentHTML("afterbegin", tableContent);

            initTables();
        }
        else {
            createModalMessage("Error", "An error has occurred", "An error has occurred while refreshing the tables. Please be sure that the server is online", "error");
        }
        setTimeout(() => spinner.remove(), 2000);
    }
    request.send();
}

refreshButton.onclick = function() {
    refreshTables();
}

updateButton.onclick = function () {
    const modal = createUpdateTableModal();
    initDropdowns();
    modal.querySelector("#okButton").onclick = function (e) {
        alert("AaaaAAAA");
    };
}

removeButton.onclick = function () {
    const modal = createRemoveTableModal();
    initDropdowns();
    modal.querySelector("#okButton").onclick = function(e) {
        alert("aAAAaaAAA");
    }
}

addButton.onclick = function() {
    const request = new XMLHttpRequest();

    request.open("GET", "/fragment/modal/modal_addTable"); // gets the modal element in HTML form
    request.onload = function () {
        if(this.status === 200) {
            const modal = addModal(this.responseText);

            initDropdowns();

            const okButton = modal.querySelector("#okButton");

            okButton.onclick = function () {
                const id = modal.querySelector("#tableIdField").value;
                const capacity = modal.querySelector("#dropdownText").innerHTML;

                if(id === null || id ==="" || id <= 0 || capacity === null || capacity === "" || capacity <= 0) {
                    modal.remove();
                    createModalMessage("Error", "Incorrect input", "An error has occurred while creating the table. Please check the values (id and capacity must be bigger than 0)", "error");
                    return;
                }

                const table = {
                    tableId: id,
                    capacity: capacity
                };

                addTable(table);
                modal.remove();
            }
        }
        else {
            createModalMessage("Error Code: "+this.status, "An error has occured", "An error has occurred during your proccess.");
        }
    }
    request.onerror = function () {
        createModalMessage("Error Code: "+this.status, "An error has occurred", "An error has occurred during your proccess.");
    }

    request.send();
}

function addTable(table) {
    const request = new XMLHttpRequest();

    request.open("POST", "/api/table/save");
    request.onload = function () {
        if(this.status === 200) {
            createModalMessage("Add Table", "Table Added Successfully", JSON.parse(this.responseText)["response"], "success");
            refreshTables();
        }
        else {
            const cause = JSON.parse(this.responseText)["response"];
            createModalMessage("Error", "An error has occurred", cause, "error")
        }
    }
    request.setRequestHeader("Content-Type", "application/json");
    request.send(JSON.stringify(table));
}



async function setTableStatus(id, status) {

}
