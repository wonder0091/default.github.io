
var setting = {
    async: {
        enable: true,
        url: "/menu/query.do",
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pId",
        },
        key:{
            name:"text"
        }
    },
    check: {
        enable: true,
        chkboxType: {"Y" : "", "N" : ""}
    },
    callback:{
        onRightClick:showMenu,
        onAsyncSuccess:expandAll,
    },

};
let firstA = 0;
function expandAll(event,treeId,treeNode){
    if(firstA==0){
        var ztree = $.fn.zTree.getZTreeObj("tree");
        let selectedNode = ztree.getSelectedNodes();
        let nodes = ztree.getNodes();
        ztree.expandAll(nodes[0],true);

        let childNodes = ztree.transformToArray(nodes[0]);
        ztree.expandNode(childNodes[1],true);
        ztree.selectNode(childNodes[1]);
        let childNodes1 = ztree.transformToArray(childNodes[1]);
        ztree.checkNode(childNodes1,true,true);
        firstA = 1;
    }
}


$(function () {
    $.fn.zTree.init($("#tree"), setting);
    $("#addModal").on('hidden.bs.modal',function (e) {
        $("#classify")[0].reset();
    })
})

let id;
function showMenu(event,treeId,treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
    $("#menu").css("top",event.clientY).css("left",event.clientX).show();
    if(treeNode){
        $("#menuEdit").show();
        $("#menuDel").show();
        $("#menueName").val(treeNode.text);
        $("#menueNum").val(treeNode.order);
        $("#menueIcon").val(treeNode.icon);
        $("#menueLink").val(treeNode.url);
        id = treeNode.id;
    }else {
        id=null;
        $("#menuEdit").hide();
        $("#menuDel").hide();
    }

}

function editTree(){
    $('#editModal').modal("show");
}

function eSub(){
    let menuName =  $("#menueName").val();
    let menuNum = $("#menueNum").val();
    let menuIcon = $("#menueIcon").val();
    let menuLink = $("#menueLink").val();

    $.ajax({
        url: '/menu/edit',
        type: 'post',
        dataType:"json",
        data:{
            text:menuName,
            order:menuNum,
            icon:menuIcon,
            url:menuLink,
            pId:id,
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
    let menuName =  $("#menuName").val();
    let menuNum = $("#menuNum").val();
    let menuIcon = $("#menuIcon").val();
    let menuLink = $("#menuLink").val();
  $.ajax({
             url: '/menu/add',
             type: 'post',
             dataType:"json",
             data:{
                 text:menuName,
                 order:menuNum,
                 icon:menuIcon,
                 url:menuLink,
                 pId:id,
             },
             success: function (data) {
                if(data.success){
                    alert(data.msg);
                    var treeObj = $.fn.zTree.getZTreeObj("tree");
                    treeObj.reAsyncChildNodes(null, "refresh");
                    $('#addModal').modal("hide");
                }
             }
         });
}

function delTree(){
    let length;
    $.ajax({
        url: '/menu/queryPid',
        type: 'post',
        dataType:"json",
        data:{
            id:id,
        },
        success: function (data) {
            if (data.success) {
                length = data.data.length;
                if (length >= 1) {
                    $('#tipModal').modal("show");
                }else {
                    if(confirm("确定要删除吗")){
                        $.ajax({
                            url: '/menu/delete',
                            type: 'post',
                            dataType:"json",
                            data:{
                                id:id
                            },
                            success: function (data) {
                                alert(data.msg);
                                var treeObj = $.fn.zTree.getZTreeObj("tree");
                                treeObj.reAsyncChildNodes(null, "refresh");
                            }
                        });
                    }
                }
            }
        }
    })

}

$(function () {
    $("body").click(function () {
        $("#menu").hide();
    })
})
