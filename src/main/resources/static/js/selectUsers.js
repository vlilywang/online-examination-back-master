function _select1() {
    var roleId = $("#role").val()
    document.getElementById("tb").style.display = "block";
    document.getElementById("pager").style.display = "block";
    $.get("api/user?page=1&pageSize=6&roleId=" + roleId, function (data) {
        $("#body").html("")
        $(data.data.content).each(function (index, item) {
            $("#body").append("<tr>" +
                "<td>" +
                "<input type='checkbox' value='1' name='single'>" +
                "</td>" +
                "<td>" + item.id + "</td>" +
                "<td>" + item.createTime + "</td>" +
                "<td>" + item.username + "</td>" +
                "<td>" + item.name + "</td>" +
                "<td>" + item.role.name + "</td>" +
                "<td>" +
                "<button class='btn' onclick='_show1(this)' title='编辑用户'>" +
                "<i class='icon icon-edit' id='edit'></i>" +
                "</button>" +
                "<button class='btn' onclick='_delete(this)' title='删除用户'>" +
                "<i class='icon icon-cut' id='delete'></i>" +
                "</button>" +
                "<button class='btn' onclick='_show2(this)' title='修改密码'>" +
                "<i class='icon icon-key'></i>" +
                "</button>" +
                "</td>" +
                "</tr>"
            )
        })
    })
}

function _select2() {
    var keyword = $("#keyword").val()
    document.getElementById("tb").style.display = "block";
    document.getElementById("pager").style.display = "block";
    $.get("keyword", function (data) {
        $("#body").html("")
        $(data.content).each(function (index, item) {
            $("#body").append(
            )
        })
    })
}

function _select3() {
    var beginTime = $("#beginTime").val()
    var endTime = $("#endTime").val()
    document.getElementById("tb").style.display = "block";
    document.getElementById("pager").style.display = "block";
    $.get("/api/user?page=1&pageSize=6&beginTime=" + beginTime + "&endTime=" + endTime, function (data) {
        $("#body").html("")
        $(data.data.content).each(function (index, item) {
            $("#body").append("<tr>" +
                "<td>" +
                "<input type='checkbox' value='1' name='single'>" +
                "</td>" +
                "<td>" + item.id + "</td>" +
                "<td>" + item.createTime + "</td>" +
                "<td>" + item.username + "</td>" +
                "<td>" + item.name + "</td>" +
                "<td>" + item.role.name + "</td>" +
                "<td>" +
                "<button class='btn' onclick='_show1(this)' title='编辑用户'>" +
                "<i class='icon icon-edit' id='edit'></i>" +
                "</button>" +
                "<button class='btn' onclick='_delete(this)' title='删除用户'>" +
                "<i class='icon icon-cut' id='delete'></i>" +
                "</button>" +
                "<button class='btn' onclick='_show2(this)' title='修改密码'>" +
                "<i class='icon icon-key'></i>" +
                "</button>" +
                "</td>" +
                "</tr>"
            )
        })
    })
}
