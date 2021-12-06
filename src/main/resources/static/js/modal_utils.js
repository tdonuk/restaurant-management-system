function createPopupHeader(parentPopup, title) {
    const popupHeader = document.createElement("div");
    popupHeader.className = "popup-header";

    const titleWrapper = document.createElement("div");
    titleWrapper.className = "popup-title-wrapper";

    const popupTitle = document.createElement("span");
    popupTitle.className = "popup-title"
    popupTitle.innerText = title;

    const popupCloseButton = document.createElement("span");
    popupCloseButton.className = "popup-close";
    popupCloseButton.innerText = "x";

    popupCloseButton.onclick = function(){
        document.body.removeChild(parentPopup);
    }

    popupHeader.appendChild(popupTitle);
    popupHeader.appendChild(popupCloseButton);

    return popupHeader;
}

function createPopupBody(type, popupMessage, content = null) {
    const body = document.createElement("div");
    body.className = "popup-body";

    const message = document.createElement("span");
    message.className = "popup-message" + " " + "popup-"+type+"-message";
    message.innerText = popupMessage;

    body.appendChild(message);

    if(content != null) {
        body.appendChild(content);
    }

    return body;
}

function createPopup(type, message, title, subtitle = null) {
    let popupContainer = document.createElement("div");
    popupContainer.className = "popup-bg";

    popupContainer.onclick = function(event) {
        if(event.target == popupContainer) {
            document.body.removeChild(popupContainer);
        }
    }

    let popupBody = document.createElement("div");
    popupBody.className = "popup-container";

    let header = createPopupHeader(popupContainer, title);
    let body;
    let footer;
    
    if(subtitle === null) {
        body = createPopupBody(type, message);
    } else {
        let messageElement = document.createElement("span");
        messageElement.className = "popup-message";
        messageElement.innerText = message;

        body = createPopupBody(type, subtitle, messageElement);
    }

    const okButton = document.createElement("button");
    okButton.className = "popup-button";
    okButton.innerText = "Close";
    okButton.onclick = function() {
        document.body.removeChild(popupContainer);
    }

    popupBody.appendChild(header);
    popupBody.appendChild(body);

    popupContainer.appendChild(popupBody);

    return popupContainer;
}

function clearAllPopups() {
    const popups = document.getElementsByClassName("popup-bg");

    for(let popup of popups) {
        document.body.removeChild(popup);
    }
}

function appendPopup(popup) {
    document.body.appendChild(popup);
    setTimeout(function() {
        document.body.removeChild(popup);
    }, 5000);
    return popup;
}

function createModalHeader(titleText) {
    const header = document.createElement("div");
    header.className = "modal-header";

    const title = document.createElement("h2");
    title.className = "modal-title";

    const closeButton = document.createElement("span")
    closeButton.className = "modal-close";

    header.appendChild(title);
    header.appendChild(closeButton);

    return header;
}

function createModalFooter(isOkButtonRequired) {
    const footer = document.createElement("div");
    footer.className = "modal-header";

    const cancelButton = document.createElement("button");
    cancelButton.className = "modal-button";
    cancelButton.innerText = "Cancel";
    cancelButton.onclick = function() {
        cancelButton.parentElement.parentElement.parentElement.remove();
    }

    if(isOkButtonRequired) {
        const okButton = document.createElement("button")
        okButton.className = "modal-close";
        okButton.innerText = "Ok";

        footer.appendChild(okButton);
    }

    footer.appendChild(cancelButton);

    return footer;
}

function createModal(header, footer, body = null) {
    const bg = document.createElement("div");
    bg.className = "modal-bg";

    const container = document.createElement("div");
    container.className = "modal-container";

    container.appendChild(header);
    if(body !== null) bg.appendChild(body);
    container.appendChild(footer);

    bg.appendChild(container);

    document.body.appendChild(bg);

    bg.onclick = function (e) {
        if(e.target === bg) bg.remove();
    }

    return bg;
}

//Standard 2-button modal
function createStandartModal(title="", body) {
    const header = createModalHeader(title);
    const footer = createModalFooter(true);

    return createModal(header, footer, body);
}