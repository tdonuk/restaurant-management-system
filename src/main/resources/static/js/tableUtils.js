let removeButton;
let updateButton;
let addButton;
let refreshButton;
let addOrderButton;

initDropdowns();

removeButton = document.getElementById("removeTableButton");
updateButton = document.getElementById("updateTableButton");
addButton = document.getElementById("addTableButton");
refreshButton = document.getElementById("refreshButton");
addOrderButton = document.getElementById("createOrderButton");

let tables = [];

refreshTableArray();

function refreshTableArray() {
    const url = "/api/table"

    const request = new XMLHttpRequest();
    request.open("get", url);
    request.onload = function () {
        if(this.status === 200) {
            const response = JSON.parse(this.responseText);
            tables = response;
        }
    }

    request.send();
}

function createTableIdArray() {
    refreshTableArray();

    const idArray = [];

    let i = 0;
    for(let table of tables) {
        idArray[i] = table["tableId"];
        i++;
    }

    return idArray;
}

addOrderButton.onclick = function () {
    const request = new XMLHttpRequest();
    request.open("get", "/fragment/content/orderItems");
    request.onload = function () {
        const modalbgText = this.responseText;

        const tempDiv = document.createElement("div");
        tempDiv.insertAdjacentHTML("afterbegin", modalbgText);

        const modalbg = tempDiv.firstElementChild;
        modalbg.onclick = function (e) {
            if(e.target === modalbg) {
                modalbg.remove();
            }
        }

        document.body.appendChild(modalbg);

        initCartUtils();
    }
    request.send();
}

function createUpdateTableModal() {
    let statusArray = ["Available", "Out of service", "Full"];
    let tableList = createTableIdArray();

    const body = ''+
        '<table>'+
            '<tr>' +
                '<td>' +
                    '<label>Table: </label>'+
                '</td>'+
                '<td>' +
                    '<div class="dropdown">' +
                        '<span id="tableIdField" class="dropdown-text">'+tableList[0]+'</span>'+
                        '<div class="dropdown-content">' +
                            createDropdownContent(tableList) +
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
                        '<span id="tableStatusField" class="dropdown-text">'+statusArray[0]+'</span>'+
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
    let tableIdList = createTableIdArray();

    const body = ''+
        '<table>'+
            '<tr>' +
                '<td>' +
                    '<label>Table: </label>'+
                '</td>'+
                '<td>' +
                    '<div class="dropdown">' +
                        '<span id="tableIdField" class="dropdown-text">'+tableIdList[0]+'</span>'+
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
        }
        else {
            createModalMessage("Error", "An error has occurred", "An error has occurred while refreshing the tables. Please be sure that the server is online", "error");
        }
        spinner.remove();
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
        const id = parseInt(modal.querySelector("#tableIdField").innerText);
        const status = modal.querySelector("#tableStatusField").innerText;

        modal.remove();

        sendSetStatusRequest(id, status);
    };
}

removeButton.onclick = function () {
    const modal = createRemoveTableModal();
    initDropdowns();
    modal.querySelector("#okButton").onclick = function(e) {
        const id = parseInt(modal.querySelector("#tableIdField").innerText);

        modal.remove();

        sendDeleteTableRequest(id);
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
            createModalMessage("Error Code: "+this.status, "Transaction failed", "An error has occurred during your proccess.", "error");
        }
    }
    request.onerror = function () {
        createModalMessage("Error Code: "+this.status, "An error has occurred", "An error has occurred during your proccess.", "error");
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
            const cause = JSON.parse(this.responseText);
            createModalMessage("Error", "An error has occurred", cause["response"], "error")
        }
    }
    request.setRequestHeader("Content-Type", "application/json");
    request.send(JSON.stringify(table));
}



function sendSetStatusRequest(id, status) {
    const request = new XMLHttpRequest();
    request.open("post", "/api/table/"+id+"/status");
    request.onload = function () {
        const response = this.responseText;
        if(this.status === 200) {
            refreshTables();
        }
        else {
            const modal = createModalMessage("Error", "Transaction failed", response["response"], "error");
            modal.querySelector("#okButton").onclick = function () {
                modal.remove();
            }
        }
    }
    request.send(status);
}

function sendDeleteTableRequest(id) {
    const request = new XMLHttpRequest();
    request.open("delete", "/api/table/"+id+"/delete");
    request.onload = function () {
        const response = this.responseText;
        if(this.status === 200) {
            refreshTables();
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
