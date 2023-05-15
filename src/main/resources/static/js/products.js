
$(function () {
    tableInit();
    $("#purchaseModal").on('hidden.bs.modal',function (e) {
        $("#addForm")[0].reset();
    })
    getS();
})

function getS() {
    $.ajax({
            url: '/products/getRname',
            type: 'post',
            dataType:"json",
            data:{},
            success: function (data) {
                console.log(data[0]);
                let role = data[0].typeId;

                if(role!='采购' && role!='销售' &&role!='超级管理员'){
                    $("#sale").css("display","none");
                    $("#purchase").css("display","none");
                }else if(role=='采购'){
                    $("#sale").css("display","none");
                }else if(role=='销售'){
                    $("#purchase").css("display","none");
                }
            }
        });
}


 function tableInit() {
    // 初始化Table
        $('#table').bootstrapTable({
            url: '/products/queryPro', // 请求后台的URL（*）
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
                field: 'typeId',
                title: '产品',
                align:'center',
                // formatter: function (data, row, index) {
                //     return "<font style='color: deeppink'>"+row.name+"</font>"
                // }
            }, {
                field: 'total',
                title: '库存',
                align:'center',
                // formatter: function (data, row, index) {
                //     return "<font style='color: deeppink'>"+row.flag+"</font>"
                // }
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
    $("button[name=refresh]").click();
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


//采购入库
function purchaseModal() {
    $("#purchaseModal").modal("show")
}

//打开类别选择
function chooseCategory() {
    $("#category").modal("show")
    $.ajax({
        url: '/type',
        dataType: 'html',
        type: 'get',
        async: true,
        success: function (data) {
            $("#Verify").html(data)
            $("#chooseCategory").click(function () {
                let t = $("#tree").find("span.checkbox_true_full").next().attr('title');
                $("#addName").val(t);
                $("#purchaseModal").modal('hide');
            })
        }, error: function () {
        }
    })
}
//提交审核
function subVerify() {
    let price = $("#addPrice").val();
    let number = $("#addNumber").val();
    let type = $("#addName").val();
    let uploadData = new FormData();
    uploadData.append("price",price);
    uploadData.append("number",number);
    uploadData.append("typeId",type);
    $.ajax({
        url: '/products/add',
        type: 'post',
        dataType:"json",
        data:uploadData,
        processData:false,
        contentType:false,
        success: function (data) {
            alert(data.msg);
        }
    });
}

//销售出库
function saleModal() {
    $("#saleModal").modal("show")
}

//打开类别选择
function chooseType() {
    $("#category").modal("show")
    $.ajax({
        url: '/type',
        dataType: 'html',
        type: 'get',
        async: true,
        success: function (data) {
            $("#Verify").html(data);
            $("#chooseCategory").click(function () {
                let t = $("#tree").find("span.checkbox_true_full").next().attr('title');
                $("#saleType").val(t);
                $("#saleModal").modal('hide');
            })
        }, error: function () {
        }
    })
}
//提交审核
function subSale() {
    let price = $("#salePrice").val();
    let number = $("#saleTotal").val();
    let type = $("#saleType").val();
    let uploadData = new FormData();
    uploadData.append("price",price);
    uploadData.append("number",number);
    uploadData.append("typeId",type);
    $.ajax({
        url: '/products/substract',
        type: 'post',
        dataType:"json",
        data:uploadData,
        processData:false,
        contentType:false,
        success: function (data) {
            alert(data.msg);
        }
    });
}





