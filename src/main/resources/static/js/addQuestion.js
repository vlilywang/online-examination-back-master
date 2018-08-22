function _save() {
    var typeId = $("#addType").val();
    var title = $("#addTitle").val();
    var keyword = $("#addKeyword").val();
    if (typeId == 1) {
        var answer = $("input:radio[name=1]:checked").val();
        var option = "";
        for (var i = 1; i < 5; i ++) {
            option += $("#addOption" + i).val() + ",";
        }
        option = option.substring(0, option.length - 1)
    } else if (typeId == 2) {
        var answers = document.getElementsByClassName("answers");
        var answer = "";
        for (var i = 0; i < answers.length; i++) {
            if (answers[i].checked) {
                answer += answers[i].value + ","
            }
        }
        answer = answer.substring(0, answer.length - 1)
        var option = "";
        for (var i = 1; i < 5; i ++) {
            option += $("#addOption" + i).val() + ",";
        }
        option = option.substring(0, option.length - 1)
    } else {
        var answer = $("input:radio[name=radio]:checked").val();
        var option = "正确,错误";
    }
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
                new $.zui.Messager('试题增加成功！', {
                    icon: 'check',
                    type: 'success',
                    placement: 'center',
                    time: 2000
                }).show();
                setTimeout(function () {
                    window.location.href = "questions.html"
                }, 2000)
            },
            error: function (data) {
                new $.zui.Messager('操作失败！', {
                    icon: 'check',
                    placement: 'center',
                    type: 'danger',
                    time: 2000
                }).show();
                setTimeout(function () {
                    window.location.reload();
                }, 2000)
                console.log(JSON.stringify(data))
            }
        });
    }
}

$(function () {
    $("#addType").change(function () {
        var typeId = $(this).children('option:selected').val();
        if (typeId == 1) {
            $("#add").html("")
            $("#addAnswer").html("")
            $("#add").append(
                "<thead>" +
                "<tr>" +
                "<td style='font-size: 22px;color: white;' colspan='4'>请输入题目</td>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                "<tr>" +
                "<td colspan='4'>" +
                "<br>" +
                "<textarea class='form-control' rows='4' style='width: 800px;' placeholder='请输入题目' id='addTitle'></textarea>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "<thead>" +
                "<tr>" +
                "<td  style='font-size: 22px;color: white;' colspan='4'>请输入关键字</td>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                "<tr>" +
                "<td colspan='4'>" +
                "<br>" +
                "<textarea class='form-control' rows='4' style='width: 800px;' placeholder='请输入关键字' id='addKeyword'></textarea>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "<thead>" +
                "<tr>" +
                "<td style='font-size: 22px;color: white;' colspan='4'>请输入选项 </td>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                "<tr colspan='4'>" +
                "<td style='font-size: 22px;color: white;'>" +
                "A:<input type='text' id='addOption1' class='form-control' style='width: 800px' placeholder='A选项'>" +
                "</td>" +
                "</tr>" +
                "<tr colspan='4'>" +
                "<td style='font-size: 22px;color: white;'>" +
                "B:<input type='text' id='addOption2' class='form-control' style='width: 800px' placeholder='B选项'>" +
                "</td>" +
                "</tr>" +
                "<tr colspan='4'>" +
                "<td style='font-size: 22px;color: white;'>" +
                "C:<input type='text' id='addOption3' class='form-control' style='width: 800px' placeholder='C选项'>" +
                "</td>" +
                "</tr>" +
                "<tr colspan='4'>" +
                "<td style='font-size: 22px;color: white;'>" +
                "D:<input type='text' id='addOption4' class='form-control' style='width: 800px' placeholder='D选项'>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>"
            );
            $("#addAnswer").append(
                "<thead>" +
                "<tr>" +
                "<td style='font-size: 22px;color: white;' colspan='4'>请输入答案</td>" +
                "</tr>" +
                "</thead>" +
                "<tbody style='width: 800px;' colspan='4'>" +
                "<td style='width: 225px;'>" +
                "<br>" +
                "<input type='radio' name='1' value='A' class='answers' style='font-size: 22px;color: white;' colspan='4'>A</td>" +
                "<td style='width: 225px;'>" +
                "<br>" +
                "<input type='radio' name='1' value='B' class='answers' style='font-size: 22px;color: white;' colspan='4'>B</td>" +
                "<td style='width: 225px;'>" +
                "<br>" +
                "<input type='radio' name='1' value='C' class='answers' style='font-size: 22px;color: white;' colspan='4'>C</td>" +
                "<td style='width: 225px;'>" +
                "<br>" +
                "<input type='radio' name='1' value='D' class='answers' style='font-size: 22px;color: white;' colspan='4'>D</td>" +
                "<tr>" +
                "<td style='position:relative;left: 850px;'>" +
                "<br>" +
                "<input type='button' class='btn' onclick='_save()' value='保存'>" +
                "<br>" +
                "</td>" +
                "</tr>" +
                "</tbody>")
        }
        else if (typeId == 2) {
            $("#add").html("")
            $("#addAnswer").html("")
            $("#add").append(
                "<thead>" +
                "<tr>" +
                "<td style='font-size: 18px;color: white;' colspan='4'>请输入题目</td>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                "<tr>" +
                "<td colspan='4'>" +
                "<br>" +
                "<textarea class='form-control' rows='4' style='width: 800px;' placeholder='请输入题目' id='addTitle'></textarea>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "<thead>" +
                "<tr>" +
                "<td style='font-size: 18px;color: white;' colspan='4'>请输入关键字</td>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                "<tr>" +
                "<td colspan='4'>" +
                "<br>" +
                "<textarea class='form-control' rows='4' style='width: 800px;' placeholder='请输入关键字' id='addKeyword'></textarea>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "<thead>" +
                "<tr>" +
                "<td style='font-size: 18px;color: white;' colspan='4'>请输入选项 </td>" +
                "</tr>" +
                "</thead>" +
                "<tbody>" +
                "<tr colspan='4'>" +
                "<td style='font-size: 18px;color: white;'>" +
                "A:<input type='text' id='addOption1' class='form-control' style='width: 800px' placeholder='A选项'>" +
                "</td>" +
                "</tr>" +
                "<tr colspan='4'>" +
                "<td style='font-size: 18px;color: white;'>" +
                "B:<input type='text' id='addOption2' class='form-control' style='width: 800px' placeholder='B选项'>" +
                "</td>" +
                "</tr>" +
                "<tr colspan='4'>" +
                "<td style='font-size: 18px;color: white;'>" +
                "C:<input type='text' id='addOption3' class='form-control' style='width: 800px' placeholder='C选项'>" +
                "</td>" +
                "</tr>" +
                "<tr colspan='4'>" +
                "<td style='font-size: 18px;color: white;'>" +
                "D:<input type='text' id='addOption4' class='form-control' style='width: 800px' placeholder='D选项'>" +
                "</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>"
            );
            $("#addAnswer").append(
                "<thead>" +
                "<tr>" +
                "<td style='font-size: 18px;color: white;' colspan='4'>请输入答案</td>" +
                "</tr>" +
                "</thead>" +
                "<tbody style='width: 800px;' colspan='4'>" +
                "<td style='width: 225px;'>" +
                "<br>" +
                "<input type='checkbox' name='1' value='A' class='answers' style='font-size: 18px;color: white;' colspan='4'>A</td>" +
                "<td style='width: 225px;'>" +
                "<br>" +
                "<input type='checkbox' name='1' value='B' class='answers' style='font-size: 18px;color: white;' colspan='4'>B</td>" +
                "<td style='width: 225px;'>" +
                "<br>" +
                "<input type='checkbox' name='1' value='C' class='answers' style='font-size: 18px;color: white;' colspan='4'>C</td>" +
                "<td style='width: 225px;'>" +
                "<br>" +
                "<input type='checkbox' name='1' value='D' class='answers' style='font-size: 18px;color: white;' colspan='4'>D</td>" +
                "<tr>" +
                "<td style='position:relative;left: 850px;'>" +
                "<br>" +
                "<input type='button' class='btn' onclick='_save()' value='保存'>" +
                "<br>" +
                "</td>" +
                "</tr>" +
                "</tbody>")
        }
            else {
                $("#add").html("")
                $("#addAnswer").html("")
                $("#add").append(
                    "<thead>" +
                    "<tr>" +
                    "<td style='font-size: 18px;color: white;' colspan='4'>请输入题目</td>" +
                    "</tr>" +
                    "</thead>" +
                    "<tbody>" +
                    "<tr>" +
                    "<td colspan='4'>" +
                    "<br>" +
                    "<textarea class='form-control' rows='4' style='width: 800px;' placeholder='请输入题目' id='addTitle'></textarea>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "<thead>" +
                    "<tr>" +
                    "<td style='font-size: 18px;color: white;' colspan='4'>请输入关键字</td>" +
                    "</tr>" +
                    "</thead>" +
                    "<tbody>" +
                    "<tr>" +
                    "<td colspan='4'>" +
                    "<br>" +
                    "<textarea class='form-control' rows='4' style='width: 800px;' placeholder='请输入关键字' id='addKeyword'></textarea>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "<thead>" +
                    "<tr>" +
                    "<td style='font-size: 18px;color: white;' colspan='4'>请输入答案</td>" +
                    "</tr>" +
                    "</thead>" +
                    "<tbody style='width: 800px;' colspan='4'>" +
                    "<td style='width: 450px;'>" +
                    "<br>" +
                    "<input type='radio' name='radio' value='A'>&check;</td>" +
                    "<td style='width: 450px;'>" +
                    "<br>" +
                    "<input type='radio' name='radio' value='B'>&times;</td>" +
                    "<tr>" +
                    "<td style='position:relative;left: 850px;'>" +
                    "<br>" +
                    "<input type='button' class='btn' onclick='_save()' value='保存'>" +
                    "</td>" +
                    "</tr>" +
                    "<br>" +
                    "</tbody>"
                );
            }
    });
})


