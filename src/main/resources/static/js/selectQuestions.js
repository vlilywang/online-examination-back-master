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
    var typeId = $("#type").val()
    if(startDate != "" && endDate != "") {
        if (startDate > endDate) {
            alert("日期输入错误")
        }
    } else if (startDate == "" && endDate == "" && keyword == "" && typeId == null) {
        alert("查询内容为空")
    } else {
        document.getElementById("tb").style.display = "block";
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
        if(typeId != null) {
            $.get("typeId", function (data) {
                $(data.content).each(function (index, item) {
                    $("#tbody").append(

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