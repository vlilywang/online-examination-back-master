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