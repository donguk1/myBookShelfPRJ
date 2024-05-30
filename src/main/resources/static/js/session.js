$(document).ready(function () {
    getSsUserId()

})

function getSsUserId() {

    $.ajax({
        url: "/user/getSsUserId",
        type: "post",
        dataType: "JSON",
        data: $("#f").serialize(),
        success: function (json) {

            if (json.userId.length === 0) {

                location.href = "/user/login"
            }

            ssUserId = json.userId


        }

    });
}