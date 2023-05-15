
$(function () {
    tableInit();
})


 function tableInit() {
    // 初始化Table
        $('#table').bootstrapTable({
            url: '/examine/queryEx', // 请求后台的URL（*）
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
                field: 'order_type_',
                title: '单据类型',
                align:'center',
                formatter:function(data,row,index){
                    if(row.orderType==1){
                        row.orderType="入库单"
                    }else {
                        row.orderType="出库单"
                    }
                    return "<span>"+row.orderType+"</span>"
                }
            }, {
                field: 'status_',
                title: '状态',
                align:'center',
                formatter:function(data,row,index){
                    if(row.status==1){
                        row.status="在途"
                    }else if(data==2){
                        row.status="驳回"
                    }else{
                        row.status="结束"
                    }
                    return "<span>"+row.status+"</span>"
                }
            }, {
                field: 'creater_',
                title: '申请人',
                align:'center',
                formatter:function (data,row,index) {
                    return "<span>"+row.creator+"</span>";
                }

            }, {
                field: 'op',
                title: '操作',
                align:'center',
                formatter:function (data,row,index) {
                    var temp = ""
                    temp = "<a href='javascript:void(0)' onclick=adopt('" + row.id + "')>通过</a>  &nbsp;&nbsp;";
                    temp += "<a href='javascript:void(0)' onclick=opoverrule('" + row.id + "')>驳回</a>   &nbsp;&nbsp;";
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
    $("#table").bootstrapTable("refresh",{query:{page:1}});
}

function tableQueryParams(params) {
    var page = (params.offset / params.limit) + 1;
    var temp = {
        size: params.limit, // 页面大小
        page: page, // 第几页
        typeId: $("#RoleName").val(),
        orderType:$("#orderType option:selected").val(),
        order: params.order,
        sort: params.sort
    };
    return temp;
}
//审核通过
function adopt(id) {
    let tid,type,tnum,orderType;
    let rows = globalData.rows;
    for (let i = 0; i < rows.length; i++) {
       if(id==rows[i].id){
           tid = rows[i].id;
           type = rows[i].typeId;
           tnum = rows[i].number;
           orderType = rows[i].orderType;
       }
    }
    let upload = new FormData();
    upload.append("id",tid);
    upload.append("typeId",type);
    upload.append("total",tnum);
    if(orderType=="入库单"){
        $.ajax({
            url: '/products/adoptIn',
            type: 'post',
            dataType:"json",
            data:upload,
            processData:false,
            contentType:false,
            success: function (data) {
                if(data&&data.success){
                    $('#table').bootstrapTable('refresh');
                }
                alert(data.msg);
            }
        });
    }else {
        $.ajax({
            url: '/products/adoptOut',
            type: 'post',
            dataType:"json",
            data:upload,
            processData:false,
            contentType:false,
            success: function (data) {
                if(data&&data.success){
                    $('#table').bootstrapTable('refresh');
                }
                alert(data.msg);
            }
        });
    }

}

//打开驳回框
let overruleId;
function opoverrule(id) {
    overruleId = id;
    let arr = globalData.rows;
    for (let i=0;i<arr.length;i++){
        if(id==arr[i].id){
            let currentData = arr[i];
            $("#overruleModal").modal('show');
            $("#editId").val(arr[i].id);
            break;
        }
    }
}
//驳回
function overrule(){
    let id = $("#editId").val();
    let cause = $("#cause").val();
    let upload = new FormData();
    upload.append("id",id);
    upload.append("cause",cause);
    $.ajax({
        url: '/products/cause',
        type: 'post',
        dataType:"json",
        data:upload,
        processData:false,
        contentType:false,
        success: function (data) {
            tableQuery()
            $("#overruleModal").modal('hide')
            alert(data.msg);
        }
    });

}



