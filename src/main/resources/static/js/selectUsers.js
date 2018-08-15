$(".form-date").datetimepicker(
    {
        language:  "zh-CN",
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        forceParse: 0,
        format: "yyyy-mm-dd"
    });
function _select() {
    var startDate = $("#startDate").val()
    var endDate = $("#endDate").val()
    var keyword = $("#keyword").val()
    var roleId = $("#role").val()
    if(startDate != "" && endDate != "") {
        if (startDate > endDate) {
            alert("日期输入错误")
        }
    } else if (startDate == "" && endDate == "" && keyword == "" && roleId == null) {
        alert("查询内容为空")
    } else {
        document.getElementById("tb").style.display = "block";
        document.getElementById("pager").style.display = "block";
        if (startDate != "" || endDate != "") {
            $.get("date", function (data) {
                $(data.content).each(function (index, item) {
                    $("#tbody").append(
                    )
                })
            })
        }
        if(keyword != "") {
            $.get("keyword", function (data) {
                $(data.content).each(function (index, item) {
                    $("#tbody").append(
                    )
                })
            })
        }
        if(roleId != null) {
            $.get("api/user?page=1&pageSize=6&roleId="+roleId, function (data) {
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
    }
}
function _show1(obj) {
    while (obj.nodeName != "TR") {
        obj = obj.parentNode;
    }
    var idx = obj.rowIndex;
    var id = obj.getElementsByTagName("td")[1].innerHTML;
    $.get("/api/user/" + id, function (data) {
        $(data).each(function (index, item) {
            $("#name").val(item.name)
        })
    })
    var w = document.body.clientWidth;
    w = w / 2 - 350;
    document.getElementById("conver").style.display = "block";
    document.getElementById("show1").style.display = "block";
    document.getElementById("pager").style.display = "none";
    document.getElementById("main").style.display = "none";
    document.getElementById("show1").style.left = w + "px";
    document.getElementById("show1").style.top = 120 + "px";
    $("#id1").append(id);
}

function _show2(obj) {
    while (obj.nodeName != "TR") {
        obj = obj.parentNode;
    }
    var idx = obj.rowIndex;
    var id = obj.getElementsByTagName("td")[1].innerHTML;
    var w = document.body.clientWidth;
    w = w / 2 - 300;
    document.getElementById("conver").style.display = "block";
    document.getElementById("show2").style.display = "block";
    document.getElementById("pager").style.display = "none";
    document.getElementById("main").style.display = "none";
    document.getElementById("show2").style.left = w + "px";
    document.getElementById("show2").style.top = 120 + "px";
    $("#id2").append(id);
}

function _close() {
    document.getElementById("conver").style.display = "none";
    document.getElementById("show1").style.display = "none";
    document.getElementById("pager").style.display = "block";
    document.getElementById("main").style.display = "block";
}
function _updUserPass() {
    var id = $("#id2").text();
    var newPass = $("#newPass").val();
    $.ajax({
        type: "PUT",
        url: "/api/user/" + id + "/password",
        data: JSON.stringify({ newPassword: newPass }),
        contentType: "application/json",
        success: function (data) {
            alert("用户密码修改成功！");
            _close();
        },
        error: function (data) {
            console.log(JSON.stringify(data))
        }
    });
}
function _updUser() {
    var id = $("#id1").text();
    var name = $("#name").val();
    var typeId = $("#type").val();
    $.ajax({
        type: "PUT",
        url: "/api/user/" + id,
        data: JSON.stringify({ name: name, roleId: typeId }),
        contentType: "application/json",
        success: function (data) {
            alert("用户信息修改成功！");
            _close();
        },
        error: function (data) {
            console.log(JSON.stringify(data));
        }
    });
}