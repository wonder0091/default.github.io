
var setting = {
    async: {
        enable: true,
        url: "/type/query",
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pId",
        },
        key:{
            name:"name"
        }
    },
    check: {
        enable: true,
        chkboxType: {"Y" : "", "N" : ""}
    },
    callback:{
        onAsyncSuccess:expandAll,
        onRightClick:showMenu
    },

};
function expandAll(event,treeId,treeNode){
    let treeObj = $.fn.zTree.getZTreeObj("tree");
    treeObj.expandAll(true);
}

$(function () {
    $.fn.zTree.init($("#tree"), setting);
    $("#addModal").on('hidden.bs.modal',function (e) {
        $("#classify")[0].reset();
    })
})

let id;
let addId;
let typeName;

function showMenu(event,treeId,treeNode) {
    $("#menu").css("top",event.clientY).css("left",event.clientX).show();
    if(treeNode){
        $("#menuEdit").show();
        $("#menuDel").show();
        $("#menueName").val(treeNode.name);
        id = treeNode.id;
        addId=treeNode.id;
        typeName = treeNode.name;
    }else {
        addId=null;
        $("#menuEdit").hide();
        $("#menuDel").hide();
    }

}

function editTree(){
    $('#editModal').modal("show");
}

function eSub(){
    let menuName =  $("#menueName").val();
    $.ajax({
        url: '/type/edit',
        type: 'post',
        dataType:"json",
        data:{
            name:menuName,
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
    $.ajax({
             url: '/type/add',
             type: 'post',
             dataType:"json",
             data:{
                 name:menuName,
                 pId:addId,
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
            url: '/type/queryPid',
            type: 'post',
            dataType:"json",
            data:{
                id:id,
                name:typeName
            },
            success: function (data) {
                if(data.success){
                    length=data.data.length;
                    if(length>=1){
                        $('#tipModal').modal("show");
                    }else {
                        $.ajax({
                                url: '/products/queryTotal',
                                type: 'post',
                                dataType:"json",
                                data:{typeId:typeName},
                                success: function (data) {
                                  if(data.success){
                                      if(data==null || data.data[0]==null || data.data[0].total<1){
                                          if(confirm("确定要删除吗")){
                                              $.ajax({
                                                  url: '/type/delete',
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
                                      } else {
                                          alert("还有库存，不能删除");
                                      }

                                  }

                                }
                            });
                    }
                }
            }
        });

}

$(function () {
    $("body").click(function () {
        $("#menu").hide();
    })
})
