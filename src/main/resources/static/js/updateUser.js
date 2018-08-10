function _save(){
	var userName = document.getElementById("userName").value;
	var realName = document.getElementById("realName").value;
	var typeId = document.getElementById("type").value;
    if (typeId == "学生") {
        roleId = 2;
    } else {
        roleId = 1;
    }
	var data = JSON.stringify({name:realName,username:userName,roleId:roleId});
    $.ajax({
        type : "POST",
        url:"/api/user",
        data:data,
        contentType : "application/json",
        success : function(data) {
            alert("增加成功")
            window.location.href="updateUser.html"
        },
        error:function (data) {
            alert("增加失败，用户名已存在")
            console.log(JSON.stringify(data.responseText))
        }
    });
}