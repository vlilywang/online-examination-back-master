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
                new $.zui.Messager('用户增加成功！', {
                    type: 'success',
                    placement: 'center',
                    time: 2000
                }).show();
                setTimeout(function () {
                    window.location.href="updateUser.html"
                }, 2000);
            },
            error:function (data) {
                new $.zui.Messager("用户增加失败！" + data.responseText, {
                    type: 'danger',
                    placement: 'center',
                    time: 2000
                }).show();
            }
        });
    }

}