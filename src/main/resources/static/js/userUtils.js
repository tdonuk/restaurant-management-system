
/* DELETE USER */
function deleteUser(userId) {
    const modal = createModalMessage("Warning", "Deleting User","You are deleting a user record from system. To confirm, please click 'Ok'","warning");
    modal.querySelector("#okButton").onclick = function () {
        modal.remove();
        sendDeleteRequest(userId);
    }
}

function sendDeleteRequest(userId) {
    const url = "/api/user/"+userId;

    createRequest("delete", url, showResult);
}
/* - */

/* CHANGE NAME */
function changeName(userId) {
    const body = '' +
        '<table>' +
        '    <tr>' +
        '        <th>' +
        '            <label>First Name:</label>' +
        '        </th>' +
        '        <td>' +
        '            <input id="firstNameField" type="text" minlength="1"/>' +
        '        </td>' +
        '    </tr>' +
        '    <tr>' +
        '        <th>' +
        '            <label>Last Name:</label>' +
        '        </th>' +
        '        <td>' +
        '            <input id="lastNameField" type="text" minlength="1"/>' +
        '        </td>' +
        '    </tr>' +
        '</table>';
    const modal = createModalFromBody(body, "Update Name");

    modal.querySelector("#okButton").onclick = function () {
        const nameObj = {
            firstName: "",
            lastName: ""
        }
        nameObj.firstName = modal.querySelector("#firstNameField").value;
        nameObj.lastName = modal.querySelector("#lastNameField").value;

        modal.remove();

        sendChangeNameRequest(userId, nameObj);
    }
}

function sendChangeNameRequest(userId, nameObj) {
    const url = "/user/"+userId+"/name";

    createRequest("post", url, showResult, JSON.stringify(nameObj));
} 
/* - */

/* ASSIGN ROLE */
function assignRole(userId) {
    const roleList = ["user", "employee", "manager", "admin"];
    const body = '' +
        '<table>' +
        '    <tr>' +
        '        <th>' +
        '            <label>Role:</label>' +
        '        </th>' +
        '        <td>' +
        '            <div class="dropdown" type="text" minlength="1">' +
        '               <span class="dropdown-text" id="roleField">'+roleList[0]+'</span>' +
        '               <div class="dropdown-content">' +
        '                   '+createDropdownContent(roleList) +
        '               </div>' +
        '            </div>' +
        '        </td>' +
        '    </tr>' +
        '</table>';

    const modal = createModalFromBody(body, "Assign Role");

    initDropdowns();

    modal.querySelector("#okButton").onclick = function () {
        const role = modal.querySelector("#roleField").innerText;
        modal.remove();
        sendAssignRoleRequest(userId, role);
    }
}
function sendAssignRoleRequest(userId, role) {
    const url = "/api/user/"+userId+"/role";

    createRequest("post", url, showResult, role);
}
/* - */

/* UPDATE PHONE */
function updatePhone(userId) {
    const body = '' +
        '<table>' +
        '    <tr>' +
        '        <th>' +
        '            <label>Phone:</label>' +
        '        </th>' +
        '        <td>' +
        '            <input id="phoneField" type="text" minlength="10"/>' +
        '        </td>' +
        '    </tr>' +
        '</table>';

    const modal = createModalFromBody(body, "Update Phone");

    modal.querySelector("#okButton").onclick = function () {
        const phone = modal.querySelector("#phoneField").value;
        modal.remove();
        sendUpdatePhoneRequest(userId, phone);
    }
}
function sendUpdatePhoneRequest(userId, phone) {
    const url = "/user/"+userId+"/phone";

    createRequest("post", url, showResult, phone);
}
/* - */

/* UPDATE EMAIL */
function updateEmail(userId) {
    const body = '' +
        '<table>' +
        '    <tr>' +
        '        <th>' +
        '            <label>Email:</label>' +
        '        </th>' +
        '        <td>' +
        '            <input id="emailField" type="text" minlength="1"/>' +
        '        </td>' +
        '    </tr>' +
        '</table>';

    const modal = createModalFromBody(body, "Update Email");

    modal.querySelector("#okButton").onclick = function () {
        const email = modal.querySelector("#emailField").value;
        modal.remove();
        sendUpdateEmailRequest(userId, email);
    }
}
function sendUpdateEmailRequest(userId, email) {
    const url = "/user/"+userId+"/email";

    createRequest("post", url, showResult, email);
}
/* - */

/* UPDATE SALARY */
function updateSalary(userId) {
    const body = '' +
        '<table>' +
        '    <tr>' +
        '        <th>' +
        '            <label>Salary:</label>' +
        '        </th>' +
        '        <td>' +
        '            <input id="salaryField" type="number" min="1" step="0.01"/>' +
        '        </td>' +
        '    </tr>' +
        '</table>';

    const modal = createModalFromBody(body, "Update Email");

    modal.querySelector("#okButton").onclick = function () {
        const salary = modal.querySelector("#salaryField").value;
        modal.remove();
        sendUpdateSalaryRequest(userId, salary);
    }
}
function sendUpdateSalaryRequest(userId, salary) {
    const url = "/user/"+userId+"/salary";

    createRequest("post", url, showResult, salary);
}
/* - */

/* UPDATE SALARY */
function changePassword(userId) {
    const body = '' +
        '<table>' +
        '    <tr>' +
        '        <th>' +
        '            <label>Password:</label>' +
        '        </th>' +
        '        <td>' +
        '            <input id="passwordField" type="password"/>' +
        '        </td>' +
        '    </tr>' +
        '</table>';

    const modal = createModalFromBody(body, "Update Email");

    modal.querySelector("#okButton").onclick = function () {
        const password = modal.querySelector("#passwordField").value;
        modal.remove();
        sendChangePasswordRequest(userId, password);
    }
}
function sendChangePasswordRequest(userId, password) {
    const url = "/user/"+userId+"/password";

    createRequest("post", url, showResult, password);
}
/* - */

/* CHANGE NAME */
function updateAddress(userId) {
    const body = '' +
        '<table>' +
        '    <tr>' +
        '        <th>' +
        '            <label>Street:</label>' +
        '        </th>' +
        '        <td>' +
        '            <input id="streetField" type="text" minlength="1"/>' +
        '        </td>' +
        '    </tr>' +
        '    <tr>' +
        '        <th>' +
        '            <label>Detail:</label>' +
        '        </th>' +
        '        <td>' +
        '            <input id="detailField" type="text" minlength="1"/>' +
        '        </td>' +
        '    </tr>' +
        '</table>';

    const modal = createModalFromBody(body, "Update Address");

    modal.querySelector("#okButton").onclick = function () {
        const address = {
            street: "",
            apartment: ""
        }
        address.street = modal.querySelector("#streetField").value;
        address.apartment = modal.querySelector("#detailField").value;

        modal.remove();

        sendUpdateAddressRequest(userId, address);
    }
}

function sendUpdateAddressRequest(userId, address) {
    const url = "/user/"+userId+"/address";

    createRequest("post", url, showResult, JSON.stringify(address));
}
/* - */

function createRequest(method = "get", url, onloadFunction, body = null) {
    const request = new XMLHttpRequest();
    request.open(method, url);
    request.onload = function () {
        const response = JSON.parse(this.responseText);
        if(this.status === 200) {
            onloadFunction(response);
        } else {
            createModalMessage("Error", "Transaction failed", response["response"], "error");
        }
    };

    if(body !== null) {
        request.setRequestHeader("Content-Type", "application/json");
        request.send(body);
    }
    else request.send();
}

function showResult(response) {
    const modal = createModalMessage("Success", "Transaction completed", response["response"], "success");
    modal.querySelector("#okButton").onclick = function () {
        modal.remove();
        window.location = window.location;
    }
}