function _save(){
	var userName = $("#userName").val();
	var realName = $("#realName").val();
	var typeId = $("#type").val();
    if(userName == "" || realName == "" || typeId == null ){
        alert("用户信息不能为空")
    } else {
        var data = JSON.stringify({name:realName,username:userName,roleId:typeId});
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

}