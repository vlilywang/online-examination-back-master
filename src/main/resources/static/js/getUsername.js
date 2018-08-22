$.get("/api/user/current", function (data) {
    $("#currentName").append(
        data.username
    )
})