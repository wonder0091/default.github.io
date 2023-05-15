
let MenuList;

$(function () {
    tableInit();
})
function tableInit() {
    // 初始化Table
        $('#table').bootstrapTable({
            url: '/role/queryRole', // 请求后台的URL（*）
            method: 'get', // 请求方式（*）
            contentType: "application/x-www-form-urlencoded",//post 必须制定类型，否则不能正常传值
            toolbar: '#toolbar', // 工具按钮用哪个容器
            striped: true, // 是否显示行间隔色
            cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, // 是否显示分页（*）
            sortName: "id_",//默认排序列
            sortable: true, // 是否启用排序
            sortOrder: "asc", // 排序方式
            queryParams: tableQueryParams,// 传递参数（*）
            sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1, // 初始化加载第一页，默认第一页
            pageSize: 5, // 每页的记录行数（*）
            pageList: [10, 15, 20], // 可供选择的每页的行数（*）
            search: false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: false, // 是否显示所有的列
            showRefresh: true, // 是否显示刷新按钮
            minimumCountColumns: 2, // 最少允许的列数
            clickToSelect: true, // 是否启用点击选中行
            singleSelect: false,//开启单选，默认为多选
            uniqueId: "id_", // 每一行的唯一标识，一般为主键列
            showToggle: false, // 是否显示详细视图和列表视图的切换按钮
            cardView: false, // 是否显示详细视图
            detailView: false, // 是否显示父子表
            columns: [
                {
                checkbox: true,
                align: 'center'
            },
                {
                field: 'name_',
                title: '角色名',
                align:'center',
                formatter:function (data,row,index) {
                    return "<span>"+row.name+"</span>";
                }

            }, {
                field: 'flag_',
                title: '标识',
                align:'center',
                formatter:function (data,row,index) {
                    return "<span>"+row.flag+"</span>";
                }

            },{
                field: 'id_',
                title: '操作',
                align:'center',
                formatter:function (data,row,index) {
                    var temp = ""
                    temp = "<a href='javascript:void(0)' onclick=edit('" + row.id + "')>编辑</a>  &nbsp;&nbsp;";
                    temp += "<a href='javascript:void(0)' onclick=dele('" + row.id + "')>删除</a>   &nbsp;&nbsp;";
                    temp +=  "<a href='javascript:void(0)' style='cursor: pointer' onclick=assmenu('"+row.name+"','"+row.id+"')>关联菜单</a>";
                    return temp;
                },
            }],
            onLoadSuccess:function (data) {
                console.log(data);
                globalData=data;
            }
        });

}

let globalData;
function search_ok() {
    $('#table').bootstrapTable('refresh',{pageNumber: 1});
}
function tableQuery() {
    $("#table").bootstrapTable("refresh",{queryRole:{page:1}});
}

function tableQueryParams(params) {
    var page = (params.offset / params.limit) + 1;
    var temp = {
        size: params.limit, // 页面大小
        page: page, // 第几页
        name: $("#RoleName").val(),
        order: params.order,
        sort: params.sort
    };
    return temp;
}


//让已关联的菜单自动勾选
let assRoleId;
function assmenu(name,id){
    assRoleId=id;
    $("#menuListModal").modal('show')
    $("#assRoleName").text(name+'-关联菜单');
    $.ajax({
        url: '/role/queryAssMenu',
        type: 'post',
        dataType:"json",
        data:{roleId:assRoleId},
        success: function (data) {
            MenuList=data.data;
        }
    });

    let setting = {
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
            onAsyncSuccess:expandAll,
            onCheck:onCheck,

        }

    };
    $.fn.zTree.init($("#chooseMenu"), setting);
}


//关联数据库
function subAssMenu(){
    let menuId;
    if(ckn!=null && ckn!=''){
        menuId=ids;
    }else {
        menuId=null;
    }
    let roleId = assRoleId;
    $.ajax({
            url: '/role/assMenu',
            type: 'post',
            dataType:"json",
            data:{
                menuId:menuId,
                roleId:roleId
            },
            success: function (data) {
                alert(data.msg);
                $("#menuListModal").modal('hide');
                clear();
            }
        });


}

let mpId;
let ids;
let ckn;
let nodes;
function expandAll(event,treeId,treeNode){
    var treeObj = $.fn.zTree.getZTreeObj("chooseMenu"),
        Nodes = treeObj.getCheckedNodes(false);
    nodes=Nodes;
    treeObj.expandAll(true);
    console.log(MenuList)
    for (let i = 0; i < MenuList.length; i++) {
        let nodeByParam = treeObj.getNodeByParam("id",MenuList[i].menuId,null);
        treeObj.checkNode(nodeByParam,true,true);
    }
}



function onCheck(e,treeId,treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("chooseMenu"),
        nodes = treeObj.getCheckedNodes(true),
        v = "",
        vv = "";
    ckn = nodes;
    var len = nodes.length-1;
    for (var i = 0; i < nodes.length; i++) {
        if(i<len){
            v += nodes[i].pId + ",";
            vv += nodes[i].id + ",";
        }else {
            v += nodes[i].pId;
            vv += nodes[i].id;
        }
        mpId=v;
        ids=vv;
    }
}



let deleId;
function dele(id) {
    deleId=id;
    let flag = confirm("是否删除该角色？");
    if(flag){
        deleteHandler(id)
    }
}
function deleteHandler(id) {
    $.ajax({
        url: '/role/delRole',
        type: 'post',
        dataType:"json",
        data:{
            id:id
        },
        success: function (json) {
            if(json.success){
                alert(json.msg);
                search_ok();
            }

        }
    });
}


//添加角色
function openModal() {
    $("#openModal").modal("show")
}

function addRole() {
    let name = $("#addName").val();
    let flag = $("#addFlag").val();
    let uploadData = new FormData();
    uploadData.append('name',name)
    uploadData.append('flag',flag)
    $.ajax({
        url: '/role/addRole',
        type: 'post',
        dataType:"json",
        data:uploadData,
        processData:false,
        contentType:false,
        success: function (data) {
            tableQuery();
            $("#openModal").modal('hide')
            if(data.success){
                alert(data.msg);
            }
            if(data.success==false){
                alert(data.msg);
            }
        }
    });

}


//编辑角色
function edit(id) {
    let arr = globalData.rows;
    console.log(arr);
    console.log(id)
    for (let i=0;i<arr.length;i++){
        if(id==arr[i].id){
            let currentData = arr[i];
            $("#editModal").modal('show');
            $("#editId").val(arr[i].id);
            $("#editName").val(arr[i].name);
            $("#editFlag").val(arr[i].flag);
            break;
        }
    }
}

function editRole(){
    let name = $("#editName").val();
    let flag = $("#editFlag").val();
    let id = $("#editId").val();
    let uploadData = new FormData();
    uploadData.append('name',name)
    uploadData.append('flag',flag)
    uploadData.append('id',id)

    $.ajax({
        url: '/role/editRole',
        type: 'post',
        dataType:"json",
        data:uploadData,
        processData:false,
        contentType:false,
        success: function (data) {
            search_ok();
            $("#editModal").modal('hide')
            if(data.success){
                alert(data.msg);
            }
            if(data.success==false){
                alert(data.msg);
            }
        }
    });

}


