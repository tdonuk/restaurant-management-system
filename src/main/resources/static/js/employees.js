
let detailLinks;

function getUserDetails(userId) {
    const request = new XMLHttpRequest();

    request.open("GET", "api/user/"+userId);

    request.onload = function () {
        if(this.status === 200) {

        }
    }
}

function createUserDetailsModal(user) {

}