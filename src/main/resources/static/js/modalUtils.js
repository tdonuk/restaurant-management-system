function addModal(containerText) {
    const modalBg = document.createElement("div");
    modalBg.className = "modal-bg";
    modalBg.insertAdjacentHTML("afterbegin", containerText);

    modalBg.onclick = function(e) {
        if(e.target === modalBg) modalBg.remove();
    }

    document.body.appendChild(modalBg);

    return modalBg;
}

function createModalFromBody(bodyText, title="Message") {
    const modalElement = ''+
        '<div class="modal-container">' +
            '<div class="modal-header">' +
                '<h2 class="modal-title">'+title+'</h2>'+
                '<span class="modal-close">X</span>'+
            '</div>'+
            '<div class="modal-body">' +
                bodyText +
            '</div>'+
            '<div class="modal-footer">' +
                '<button class="modal-button" id="okButton">Accept</button>'+
                '<button class="modal-button" id="cancelButton">Cancel</button>'+
            '</div>'+
        '</div>';

    const createdModal = addModal(modalElement);

    createdModal.querySelector("#cancelButton").onclick = function () {
        createdModal.remove();
    }

    return createdModal;
}

function createModalMessage(title="Message", subtitle="", message="", type="info") {
    const modalElement = ''+
        '<div class="modal-container">' +
            '<div class="modal-header">' +
                '<h2 class="modal-title">'+title+'</h2>'+
                '<span class="modal-close">X</span>'+
            '</div>'+
            '<div class="modal-body">' +
                '<h3 class="modal-subtitle '+type+'">'+subtitle+'</h3>'+
                '<p class="modal-message">'+message+'</p>'+
            '</div>'+
            '<div class="modal-footer">' +
                '<button class="modal-button" id="okButton">Dismiss</button>'+
            '</div>'+
        '</div>';

    const createdModal = addModal(modalElement);

    createdModal.querySelector("#okButton").onclick = function () {
        createdModal.remove();
    };

    return createdModal;
}