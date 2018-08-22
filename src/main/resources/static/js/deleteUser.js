function _delete(obj){
    var boo = window.confirm("确定要删除？")
    if(boo){
        while(obj.nodeName!="TR"){
            obj=obj.parentNode;
        }
        var idx = obj.rowIndex;
        var id = obj.getElementsByTagName("td")[1].innerHTML;
        $.ajax({
            type : "DELETE",
            url:"/api/user/delete/id/"+id,
            success : function(data) {
                document.getElementById("tb").deleteRow(idx);
                new $.zui.Messager('用户删除成功！', {
                    icon: 'check',
                    placement: 'center',
                    type: 'success',
                    time:2000
                }).show();
                setTimeout(function () {
                    window.location.href = "updateUser.html"
                }, 2000);
            },
            error:function (data) {
                new $.zui.Messager('用户删除失败！' + data.responseText, {
                    icon: 'warning-sign',
                    placement: 'center',
                    time:2000
                }).show();
            }
        });
    }
}
