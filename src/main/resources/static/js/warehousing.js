
$(function () {
    tableInit();
    formValidatro();
})


 function tableInit() {
    // 初始化Table
        $('#table').bootstrapTable({
            url: '/warehousing/query', // 请求后台的URL（*）
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
                field: 'type_id_',
                title: '产品',
                align:'center',
                formatter:function (data,row,index) {
                    return "<span>"+row.typeId+"</span>";
                }
            }, {
                field: 'number_',
                title: '数量',
                align:'center',
                formatter:function (data,row,index) {
                    return "<span>"+row.number+"</span>";
                }
            }, {
                field: 'price_',
                title: '单价',
                align:'center',
                formatter:function (data,row,index) {
                    return "<span>"+row.price+"</span>";
                }
            }, {
                field: 'create_date_',
                title: '申请时间',
                align:'center',
                formatter:function (data,row,index) {
                    return "<span>"+row.createTime+"</span>";
                }
            }, {
                field: 'status_',
                title: '状态',
                align:'center',
                formatter:function(data,row,index){
                    if(row.status==1){
                        row.status="在途"
                    }else if(row.status==2){
                        row.status="驳回"
                    }else if(row.status==3){
                        row.status="结束"
                    }
                    return "<span>"+row.status+"</span>"
                }
            },{
                field: 'op',
                title: '操作',
                align:'center',
                formatter:function (value,row,index) {
                    var temp = ""
                    temp = "<a href='javascript:void(0)' onclick=dele('" + row.id + "')>撤销</a>  &nbsp;&nbsp;";
                    temp += "<a href='javascript:void(0)' onclick=edit('" + row.id + "')>修改</a>   &nbsp;&nbsp;";
                    if(row.status=="驳回"){
                        temp +=  "<a  data-toggle='popover' data-container='body' data-content='" +row.cause + "'  data-placement='left' >查看原因</a>";
                    }
                    return temp;
                },
            }],
            onLoadSuccess:function (data) {
                console.log(data);
                globalData=data;
                $("[data-toggle='popover']").popover({
                    trigger:'hover'
                });
            }
        });

}



//搜索
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
        typeId: $("#RoleName").val(),
        order: params.order,
        sort: params.sort
    };
    return temp;
}
let deleId;
function dele(id) {
    deleId=id;
    let flag = confirm("是否删除该订单？");
    if(flag){
        deleteHandler(id)
    }
}
function deleteHandler(id) {
    $.ajax({
        url: '/outbound/delete',
        type: 'post',
        dataType:"json",
        data:{
            id:id
        },
        success: function (data) {
            if(data&&data.success){
                $('#table').bootstrapTable('refresh');
            }
            alert(data.msg);
        }
    });
}

function delAll() {
    let rows = $("#table").bootstrapTable("getSelections");
    if(rows.length==0){
        return
    }
    let f = confirm("是否删除数据");
    if(f){
        let arr=[];
        for (let i = 0; i < rows.length; i++) {
            arr.push(rows[i].id);
        }
        let id = arr.join(",");//讲数组中的数据用逗号连接成一个字符串
        deleteHandler(id);
    }

}



//编辑
function edit(id) {
    let arr = globalData.rows;
    for (let i=0;i<arr.length;i++){
        if(id==arr[i].id){
            let currentData = arr[i];
            $("#editModal").modal('show');
            $("#editId").val(arr[i].id);
            $("#editType").html(arr[i].typeId);
            $("#editNum").val(arr[i].number);
            $("#editPrice").val(arr[i].price);
            break;
        }
    }
}

function editPro(){
    let number = $("#editNum").val();
    let price = $("#editPrice").val();
    let id = $("#editId").val();
    let uploadData = new FormData();
    uploadData.append('number',number)
    uploadData.append('price',price)
    uploadData.append('id',id)

    $.ajax({
        url: '/outbound/edit',
        type: 'post',
        dataType:"json",
        data:uploadData,
        processData:false,
        contentType:false,
        success: function (data) {
            tableQuery()
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

function formValidatro() {
    $('#addForm,#editForm').bootstrapValidator({
        // live: 'disabled',
        message: '这是一个无效的值',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            sno: {
                validators: {
                    notEmpty: {
                        message: '学号不能为空'
                    },
                    stringLength: {
                        min: 4,
                        max: 8,
                        message: '学号不能少于4'
                    }
                }
            },
            sname: {
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 8,
                        message: '姓名不能少于两个字'
                    }
                }
            }
        }
    });
}

