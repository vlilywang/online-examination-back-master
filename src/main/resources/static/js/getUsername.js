$.get("/api/user/current", function (data) {
    $("#username").append(
        data.username
    )
})