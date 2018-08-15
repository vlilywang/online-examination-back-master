function _showAnswer() {
    var type = document.getElementById("type").value;
    if (type == 1) {
        $("#tr").append(
            "<td>" +
            "<input type='radio' name='1' value='A' class='answers'>A</td>" +
            "<td>" +
            "<input type='radio' name='1' value='B' class='answers'>B</td>" +
            "<td>" +
            "<input type='radio' name='1' value='C' class='answers'>C</td>" +
            "<td>" +
            "<input type='radio' name='1' value='D' class='answers'>D</td>"
        )
    } else if (type == 2) {
        $("#tr").append(
            "<td>" +
            "<input type='checkbox' value='A' class='answers'>A</td>" +
            "<td>" +
            "<input type='checkbox' value='B' class='answers'>B</td>" +
            "<td>" +
            "<input type='checkbox' value='C' class='answers'>C</td>" +
            "<td>" +
            "<input type='checkbox' value='D' class='answers'>D</td>"
        )
    } else {
        $("#tr").append(
            "<td>" +
            "<input type='radio' name='radio' value='true' class='answers'>&check;</td>" +
            "<td>" +
            "<input type='radio' name='radio' value='false' class='answers'>&times;</td>"
        )
    }
}

function _save() {
    var typeId = $("#type").val();
    var title = $("#title").val();
    var option = $("#option").val();
    var keyword = $("#keyword").val();

    var answers = document.getElementsByClassName("answers");
    var answer = "";
    for (var i = 0; i < answers.length; i++) {
        if (answers[i].checked) {
            answer += answers[i].value + ","
        }
    }
    var answer = answer.substring(0,answer.length-1)
    if (typeId == null || title == "" || option == "" || keyword == "" || answer == "" ) {
        alert("试题内容不能为空");
    } else {
        $.ajax({
            type: "POST",
            url: "/api/question",
            data: JSON.stringify({
                title: title,
                answer: answer,
                typeId: typeId,
                keyword: keyword,
                option: option
            }),
            contentType: "application/json",
            success: function (data) {
                alert("增加成功")
                window.location.href = "questions.html"
            },
            error: function (data) {
                alert("增加失败")
                console.log(JSON.stringify(data.responseText))
            }
        });
    }
}