
var setting = {
    async: {
        enable: true,
        url: "/classify/query.do",
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id_",
            pIdKey: "pId",
        }
    },
    check: {
        enable: true
    },
    callback:{
        onRightClick:showMenu
    }
};

$(function () {
    $.fn.zTree.init($("#tree"), setting);
    $("#addModal").on('hidden.bs.modal',function (e) {
        $("#classify")[0].reset();
    })
})
let id;
function showMenu(event,treeId,treeNode) {
    console.log(treeNode);

    $("#menu").css("left",event.clientX).css("top",event.clientY).show();
    $("#epName").val(treeNode.name);
    $("#epSort").val(treeNode.Sort);
    id = treeNode.id_;
}

function editTree(){
    $('#editModal').modal("show");
}

function eSub(){
    let name = $("#epName").val();
    let Sort = $("#epSort").val();
    let pId = $("#epp").find("option:selected").val();

    $.ajax({
        url: '/classify/edit',
        type: 'post',
        dataType:"json",
        data:{
            name:name,
            Sort:Sort,
            pId:pId,
            id:id
        },
        success: function (data) {
            if(data.success){
                alert(data.msg);
                var treeObj = $.fn.zTree.getZTreeObj("tree");
                treeObj.reAsyncChildNodes(null, "refresh");
                $('#editModal').modal("hide");

            }

        }
    });
}

function openAddTree() {
    $('#addModal').modal("show");
}
function Sub() {
  let pId = $("#pp").find("option:selected").val();
  let name = $("#pName").val();
  let Sort = $("#pSort").val();
console.log(pId);
  $.ajax({
             url: '/classify/add',
             type: 'post',
             dataType:"json",
             data:{
                 name:name,
                 Sort:Sort,
                 pId:pId
             },
             success: function (data) {
                if(data.success){
                    var treeObj = $.fn.zTree.getZTreeObj("tree");
                    treeObj.reAsyncChildNodes(null, "refresh");
                    $('#addModal').modal("hide");
                }
             }
         });
}

function delTree(){
    confirm("确定要删除吗");
    $.ajax({
               url: '/classify/delete',
               type: 'post',
               dataType:"json",
               data:{
                   id:id
               },
               success: function (data) {
                   alert(data.msg);
               }
           });
}

$(function () {
    $("body").click(function () {
        $("#menu").hide();
    })
})
