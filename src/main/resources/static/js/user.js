$(function () {
    tableInit()
    $("#myModal,#addModal").on('hidden.bs.modal',function (e) {
        $("#formId").val("");
        $("#formSub")[0].reset();
        $("#addSub")[0].reset();
    })
})
let uId;
function queryRoleList(id){
    uId = id;
    $("#roleListModal").modal('show')
    $.ajax({
        url: '/role/queryRoleList',
        type: 'post',
        dataType:"json",
        data:{},
        success: function (data) {
            console.log(data);
            if(data.success){
                $('#assRoleName').empty();
                var json = data.data;
                $.each(json,function(index,item){
                    $("#assRoleName").append("<option value="+json[index].id+" >"+json[index].name+"</option>" );
                });
                $('#assRoleName').selectpicker('refresh');//刷新下才会生效
                $.ajax({
                        url: '/user/queryAssRole',
                        type: 'post',
                        dataType:"json",
                        data:{id:uId},
                        success: function (data) {
                          if(data.success){
                              let len = data.data.length;
                              let ck =  $('#assRoleName option');
                              for (let i = 0; i <data.data.length; i++) {
                                  for (let k = 0; k < ck.length; k++) {
                                      let name = data.data[i].roleId;
                                      let val = ck[k].value;
                                      if(name==val){
                                          console.log(name +'-'+val);
                                          ck[k].selected=true;
                                          if(k==len){
                                              break;
                                          }
                                      }
                                  }
                              }
                          }
                        }
                    });
                $('#assRoleName').selectpicker('refresh');//刷新下才会生效
            }
        }
    });
}


function associated(){
    let name = $("#assRoleName").val();
    let upload = new FormData();
    upload.append("userId",uId);
    upload.append("roleName",name);
    $.ajax({
            url: '/user/ass',
            type: 'post',
            dataType:"json",
            data:upload,
            processData:false,
            contentType:false,
            success: function (data) {
                if(data.success){
                    $("#roleListModal").modal('hide');
                    alert(data.msg);
                }
            }
        });
}

let resetId;
function reset(id){
  resetId=id;
  let flag =  confirm("确认是否重置密码？");
  if(flag==true){
      resetPass();
  }
}

function resetPass() {
    $.ajax({
        url:'/user/resetPass',
        type:'post',
        dataType:'json',
        data:{
            id:resetId
        },
        success:function (data) {
            if(data.success){
                alert(data.msg);
            }
        }
    })
}

function openAdd() {
    $("#addModal").modal('show')
}

function addUser() {
    let acc = $("#addName").val();
    let nicK = $("#addNicK").val();
    let pass = $("#addPass").val();
    let birthday = $("#datetimepicker1").val();
    let uploadData = new FormData();
    uploadData.append('account',acc)
    uploadData.append('nickName',nicK)
    uploadData.append('birth',birthday)
    uploadData.append('pass',pass)

    $.ajax({
        url: '/user/addUser',
        type: 'post',
        dataType:"json",
        data:uploadData,
        processData:false,
        contentType:false,
        success: function (data) {
            tableQuery();
            $("#addModal").modal('hide')
            if(data.success){
                alert(data.msg);
            }
            if(data.success==false){
                alert(data.msg);
            }
        }
    });

}

function edit(id) {
    let arr = globalData.rows;
    for (let i=0;i<arr.length;i++){
        if(id==arr[i].id){
            let currentData = arr[i];
            $("#myModal").modal('show');
            $("#formTitle").html('编辑用户');
            $("#formId").val(arr[i].id);
            $("#formName").val(arr[i].name);
            $("#formNicK").val(arr[i].nickName);
            break;
        }
    }
}

function editUser(){
    let acc = $("#formName").val();
    let nicK = $("#formNicK").val();
    let id = $("#formId").val();
    let uploadData = new FormData();
    uploadData.append('account',acc)
    uploadData.append('nickName',nicK)
    uploadData.append('id',id)

    $.ajax({
        url: '/user/editUser',
        type: 'post',
        dataType:"json",
        data:uploadData,
        processData:false,
        contentType:false,
        success: function (data) {
            tableQuery();
            $("#myModal").modal('hide')
            if(data.success){
                alert(data.msg);
            }
            if(data.success==false){
                alert(data.msg);
            }
        }
    });

}

function tableInit() {
    $('#table').bootstrapTable({
        url: '/user/query', // 请求后台的URL（*）
        method: 'get', // 请求方式（*）
        contentType: "application/x-www-form-urlencoded",//post 必须制定类型，否则不能正常传值
        toolbar: '#toolbar', // 工具按钮用哪个容器
        striped: true, // 是否显示行间隔色
        cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true, // 是否显示分页（*）
        sortName: "id",//默认排序列
        sortable: true, // 是否启用排序
        sortOrder: "asc", // 排序方式
        queryParams: tableQueryParams,// 传递参数（*）
        sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1, // 初始化加载第一页，默认第一页
        pageSize: 3, // 每页的记录行数（*）
        pageList: [5, 10, 20], // 可供选择的每页的行数（*）
        search: false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: true,
        showColumns: false, // 是否显示所有的列
        showRefresh: true, // 是否显示刷新按钮
        minimumCountColumns: 2, // 最少允许的列数
        clickToSelect: true, // 是否启用点击选中行
        singleSelect: false,//开启单选，默认为多选
        uniqueId: "id", // 每一行的唯一标识，一般为主键列
        showToggle: false, // 是否显示详细视图和列表视图的切换按钮
        cardView: false, // 是否显示详细视图
        detailView: false, // 是否显示父子表
        columns: [
            {
            checkbox: true,
            align: 'center'
        },
            {
            field: 'photo_url',
            title: '头像',
            //参数1，当前属性的值
            //参数2，当前这条数据的所以值，是一个对象，可以通过 对象.属性名 取其他字段的值
            formatter: function (data, row, index) {
                return "<img src='"+row.photoUrl+"' width='80px' height='80px'>";
            }
        },{
            field: 'name',
            title: '账号',

        },{
            field: 'nickName',
            title: '昵称',
            formatter: function (data, row, index) {
                return "<font style='color: deeppink'>"+data+"</font>"
            }
        },{
            field: 'password',
            title: '密码',
            formatter: function (data, row, index) {
                return "****";
            }
        }, {
            field: 'id',
            title: '操作',
            formatter: function (data, row, index) {
                var temp = ""
                temp = "<a href='javascript:void(0)' onclick=edit('" + data + "','" + row.name + "','" + row.account + "','" + row.birthday +"')>编辑</a>  &nbsp;&nbsp;";
                temp += "<a href='javascript:void(0)' onclick=dele('" + data + "')>删除</a>   &nbsp;&nbsp;";
                // temp += "<a href='javascript:void(0)' onclick=reset('" + data + "')>重置密码</a>   &nbsp;&nbsp;";
                temp +=  "<a href='javascript:void(0)'  onclick=queryRoleList('" + data + "')>关联角色</a>";
                return temp;
            }
        }],
        onLoadSuccess:function (data) {
            console.log(data);
            globalData=data;
        }
    });
}


let globalData;
function tableQueryParams(params) {
    var page = (params.offset / params.limit) + 1;
    var temp = {
        size: params.limit, // 页面大小
        page: page, // 第几页
        name: $("#inputName").val(),
        pass: $("#inputPass").val(),
        nickName:$("#inputNickName").val(),
        order: params.order,
        sort: params.sort
    };
    return temp;
}

function tableQuery() {
    $("#table").bootstrapTable("refresh",{query:{page:1}});
}


let deleId;
function dele(id){
    deleId=id;
    $("#delModal").modal('show');
}

function dataDel() {
    $.ajax({
               url: '/user/dele',
               type: 'post',
               dataType:"json",
               data:{
                    id:deleId
               },
               success: function (data) {
                    tableQuery();
                    $("#delModal").modal('hide');
               }
           });
}