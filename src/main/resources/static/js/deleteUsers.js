function _delete(obj){
    var boo = window.confirm("确定要删除？")
    if(boo){
        while(obj.nodeName!="TR"){
            obj=obj.parentNode;
        }
        var idx = obj.rowIndex;
        var id = obj.getElementsByTagName("td")[1].innerHTML;
        document.getElementById("tb").deleteRow(idx);
        $.ajax({
            type : "DELETE",
            url:"api/user/"+id,
            success : function(data) {
                alert("用户删除成功！")
                console.log(JSON.stringify(data))
            },
            error:function (data) {
                alert("用户删除失败！")
                console.log(JSON.stringify(data))
            }
        });
    }
}
