async function getAllTables() {
    const resp = await fetch("http://localhost:8080/api/table")
        .then(response => response.json())
        .then(data => data);
    return resp;
}

const removeButton = document.getElementById("table-remove-button");

const refreshButton = document.getElementById("refresh-button");
refreshButton.onclick = function() {
    window.location = "/tables";
}

const procSection = document.getElementById("table-controls"); // sideSection


const idField = document.getElementById("idField");
const statusField = document.getElementById("statusField");
const capacityField = document.getElementById("capacityField");
const formTitle = document.getElementById("sideFormTitle");


function createSideSection(tableId) {
    appendPopup(createPopup("info", "Please wait, your request is in process, this may take a few seconds.", "Loading", "Loading.."));

    fetch("/api/table/"+tableId)
        .then(response => response.json())
        .then(table => {
            clearAllPopups();

            procSection.classList.add("expanded");
            formTitle.innerText = "Table " + tableId;
            idField.innerText = table["tableId"];
            statusField.innerText = table["status"];
            capacityField.innerText = table["capacity"];
        });

}

async function setTableStatus(id, status) {
    appendPopup(createPopup("info", "This may take few seconds.", "Loading", "Processing.."));

    await fetch("/api/table/"+id+"/status", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Headers": "*"
        },
        body: status
    })
    .then(response => response.json())
    .then(() => {
        initTables();
        clearAllPopups();
    })
    .catch((error) =>{
        let message = error["response"];
        if(typeof message === "undefined" || typeof message === null) {
            message = "A problem has occurred while setting the table status. Please be sure that the server is online."
        }

        appendPopup(createPopup("error", message, "Error", "A connection error has occurred"));
    });
}


const searchBar = document.getElementById("searchBar");

const addTableButton = document.getElementById("table-add-button");

addTableButton.onclick = function() {
    const tableInputPopup = createAddTablePopup();
    document.body.appendChild(tableInputPopup);
};


function createAddTablePopup() {

}

async function addTable(table) {
    appendPopup(createPopup("info", "This may take few seconds.", "Loading", "Processing.."));
    
    const resp = await fetch("/api/table/save", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            "Access-Control-Allow-Origin": "*",
            "Access-Control-Allow-Headers": "*"
        },
        body: JSON.stringify(table)
    })
    .then(r => r.json())
    .then(data => {
        const message = data["response"];
        if(message !== null){
            appendPopup(createPopup("success", message, "Success", "Completed")); 
        }
    })
    .catch(error => {
        let message = error["response"];
        if(typeof message === "undefined") {
            message = "A problem has occurred while setting the table status. Please be sure that the server is online."
        }

        appendPopup(createPopup("error", message, "Error", "A connection error has occurred"));
    });

    window.location = "/tables";

    return resp;
}