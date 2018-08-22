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
    if (beginTime == "" && endTime == "" && keyword == "") {
        alert("查询内容为空")
    } else {
        if (beginTime != "" || endTime != "") {
            document.getElementById("tb").style.display = "block";
            document.getElementById("pager").style.display = "block";
            $.get("/api/exampaper?page=1&pageSize=6&beginTime=" + beginTime + "&endTime=" + endTime, function (data) {
                $(data.data.content).each(function (index, item) {
                    $("#tbody").append("<tr>" +
                        "<td>" +
                        "<input type='checkbox' value='1' name='single'>" +
                        "</td>" +
                        "<td>" + item.id + "</td>" +
                        "<td>" + item.createTime + "</td>" +
                        "<td>" + item.keyword + "</td>" +
                        "<td>" + item.singleAmount + "</td>" +
                        "<td>" + item.multiAmount + "</td>" +
                        "<td>" + item.judgeAmount + "</td>" +
                        "<td>" +
                        "<button class='btn' onclick='_show(this)' title='预览试卷'>" +
                        "<i class='icon icon-file-text'></i>" +
                        "</button>" +
                        "<button class='btn' onclick='_delete(this)'' title='删除试卷'>" +
                        "<i class='icon icon-cut' id='delete'></i>" +
                        "</button>" +
                        "</td>" +
                        "</tr>"
                    )
                })
            });
        }
        if(keyword != "") {
            $.get("keyword", function (data) {
                $(data.content).each(function (index, item) {
                    $("#tbody").append(

                    )
                })
            })
        }
    }
}