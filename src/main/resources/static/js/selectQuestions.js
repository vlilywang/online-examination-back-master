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
    var beginTime = $("#beginTime").val()
    var endTime = $("#endTime").val()
    var keyword = $("#keyword").val()
    var typeId = $("#type").val()
    if (beginTime == "" && endTime == "" && keyword == "" && typeId == null) {
        alert("查询内容为空")
    } else {
        if (beginTime != "" || endTime != "") {
            document.getElementById("tb").style.display = "block";
            document.getElementById("pager").style.display = "block";
            $.get("/api/question?page=1&pageSize=6&beginTime=" + beginTime + "&endTime=" + endTime, function (data) {
                $(data.data.content).each(function (index, item) {
                    $("#body").append("<tr>" +
                        "<td>" +
                        "<input type='checkbox' value='1' name='single'>" +
                        "</td>" +
                        "<td>" + item.id + "</td>" +
                        "<td>" + item.createTime + "</td>" +
                        "<td>" + item.type.typeName + "</td>" +
                        "<td>" + item.title + "</td>" +
                        "<td>" + item.answer + "</td>" +
                        "<td>" +
                        "<button class='btn' onclick='_edit(this)' title='编辑试题'>" +
                        "<i class='icon icon-edit' id='edit'></i>" +
                        "</button>" +
                        "<button class='btn' onclick='_delete(this)' title='删除试题'>" +
                        " <i class='icon icon-cut' id='delete'></i>" +
                        "</button>" +
                        "</td>" +
                        " </tr>"
                    )
                })
            })
        }
        if(keyword != "") {
            document.getElementById("tb").style.display = "block";
            document.getElementById("pager").style.display = "block";
            $.get("keyword", function (data) {
                $(data.content).each(function (index, item) {
                    $("#body").append(
                    )
                })
            })
        }
        if(typeId != null) {
            document.getElementById("tb").style.display = "block";
            document.getElementById("pager").style.display = "block";
            $.get("api/question?page=1&pageSize=6&typeId=" + typeId, function (data) {
                $(data.data.content).each(function (index, item) {
                    $("#body").append("<tr>" +
                        "<td>" +
                        "<input type='checkbox' value='1' name='single'>" +
                        "</td>" +
                        "<td>" + item.id + "</td>" +
                        "<td>" + item.createTime + "</td>" +
                        "<td>" + item.type.typeName + "</td>" +
                        "<td>" + item.title + "</td>" +
                        "<td>" + item.answer + "</td>" +
                        "<td>" +
                        "<button class='btn' onclick='_edit(this)' title='编辑试题'>" +
                        "<i class='icon icon-edit' id='edit'></i>" +
                        "</button>" +
                        "<button class='btn' onclick='_delete(this)' title='删除试题'>" +
                        " <i class='icon icon-cut' id='delete'></i>" +
                        "</button>" +
                        "</td>" +
                        " </tr>"
                    )
                })
            })
        }
    }
}
function _show(obj) {
    while (obj.nodeName != "TR") {
        obj = obj.parentNode;
    }
    var idx = obj.rowIndex;
    var id = obj.getElementsByTagName("td")[1].innerHTML;
    $.get("/api/question/" + id, function (data) {
        $(data).each(function (index, item) {
            $("#title").val(item.title)
            $("#option").val(item.option)
            $("#answer").val(item.answer)
        })
    })
    var w = document.body.clientWidth;
    w = w / 2 - 360;
    document.getElementById("conver").style.display = "block";
    document.getElementById("show").style.display = "block";
    document.getElementById("pager").style.display = "none";
    document.getElementById("main").style.display = "none";
    document.getElementById("show").style.left = w + "px";
    document.getElementById("show").style.top = 120 + "px";
    $("#id").append(id)
}

function _close() {
    window.location.href = "questions.html";
}