
$(function () {
    var table = TableInit();
    table.Init();
    formValidatro();

    $("#search_start,#search_end,#inputSbirthday,#editSbirthday").datetimepicker({
        format:"yyyy-mm-dd",
        autoclose:true,
        minView:2,//最小视图为月视图
        todayBtn:true,
        todayHighlight:true,
        language:"zh-CN"
    }).on("hide",function (e) {
        e.stopPropagation();
    })
    $("#openModal").on('hide.bs.modal',function (e) {
        $('#addForm').bootstrapValidator('resetForm',true);//必须在模态框关闭前
        document.getElementById("addForm").reset();
    })
    $("#editModal").on('hide.bs.modal',function (e) {
        $('#editForm').bootstrapValidator('resetForm',true);//必须在模态框关闭前
        document.getElementById("editForm").reset();
    })

})

function imgCtrl() {
    $("#imgModal").modal("show");
    $.ajax({
        url: '/goods/showImg',
        type: 'post',
        dataType:"json",
        data:{},
        success: function (data) {
            if(data.success){
                let html="";
                for (let i = 0; i < data.data.length-1; i++) {
                    html+="<div class=\"list-group-item\"><img src='"+data.data[i].photo+ "' width=\"180px\"height=\"180px\" style=\"position: relative\">\n"+
                        " <div style=\"position: relative;text-align: center\"><input type=\"checkbox\" style=\"display: inline-block\">&nbsp;选择</div></div>";
                }
                $("#imglist").html(html);
            }
        }
    });
}

let imgUrl="";
function saveSrc(imgUrl) {
    let ckes = $("#imglist input[type=checkbox]:checked");
    for (let i = 0; i < ckes.length; i++) {
        imgUrl = ckes.parent("div").prev().get(i).src;
    }
    $("#upImg").val(imgUrl);
    $("#eupImg").val(imgUrl);
    $("#imgModal").modal("hide");
}

var TableInit = function () {
    var oTableInit = new Object();
    // 初始化Table
    oTableInit.Init = function () {
        $('#table').bootstrapTable({
            url: '/user/queryProduct', // 请求后台的URL（*）
            method: 'post', // 请求方式（*）
            contentType: "application/x-www-form-urlencoded",//post 必须制定类型，否则不能正常传值
            toolbar: '#toolbar', // 工具按钮用哪个容器
            striped: true, // 是否显示行间隔色
            cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, // 是否显示分页（*）
            sortName: "sno",//默认排序列
            sortable: false, // 是否启用排序
            sortOrder: "asc", // 排序方式
            queryParams: oTableInit.queryParams,// 传递参数（*）
            sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1, // 初始化加载第一页，默认第一页
            pageSize: 10, // 每页的记录行数（*）
            pageList: [10, 15, 20], // 可供选择的每页的行数（*）
            search: false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true, // 是否显示所有的列
            showRefresh: true, // 是否显示刷新按钮
            minimumCountColumns: 2, // 最少允许的列数
            clickToSelect: true, // 是否启用点击选中行
            singleSelect: false,//开启单选，默认为多选
            height: 500, // 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "sno", // 每一行的唯一标识，一般为主键列
            showToggle: false, // 是否显示详细视图和列表视图的切换按钮
            cardView: false, // 是否显示详细视图
            detailView: false, // 是否显示父子表
            columns: [{
                checkbox: true,
                align: 'center'
            }, {
                field: 'id_',
                title: '商品id',
                align:'center'
            },  {
                field: 'name_',
                title: '商品名',
                align:'center'
            }, {
                field: 'Price_',
                title: '价格',
                align:'center'
            },{
                field: 'total_',
                title: '库存',
                align:'center'
            },{
                field: 'Freight_',
                title: '快递费',
                align:'center'
            },{
                field: 'Status_',
                title: '状态',
                align:'center'
            },{
                field: 'op',
                title: '操作',
                align:'center',
                formatter:function (value,row,index) {
                    let html=`
                      <i class="glyphicon glyphicon-pencil"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <i class="glyphicon glyphicon-trash"></i>
                    `;
                    return html;
                },
                events:{
                    'click .glyphicon-pencil': function (e, value, row, index){
                        e.stopPropagation();
                        editOpen(row.Sort_);
                    },
                    'click .glyphicon-trash': function (e, value, row, index){
                        e.stopPropagation();
                        let f = confirm("是否删除数据？");
                        if(f){
                            deleteHandler(row.Sort_);
                        }
                    }
                }
            }]
        });
    };
    // 得到查询的参数
    oTableInit.queryParams = function (params) {
        var page = (params.offset / params.limit) + 1;
        var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            size: params.limit, // 页面大小
            page: page, // 第几页
            sno:$("#search_sno").val(),
            sname:$("#search_sname").val(),
            ssex:$("#search_ssex").val(),
            startTime:$("#search_start").val(),
            endTime:$("#search_end").val()
        };
        return temp;
    };
    oTableInit.refresh=function () {
        $('#table').bootstrapTable('refresh');
    }
    return oTableInit;
};

function search_ok() {
    $('#table').bootstrapTable('refresh',{pageNumber: 1});
}
function search_reset() {
    document.getElementById("search_form").reset();
    $('#table').bootstrapTable('refresh',{pageNumber: 1});
}

function editOpen(Sort) {
    $.ajax({
        url: '/goods/info',
        type: 'post',
        dataType:"json",
        data:{
            Sort:Sort
        },
        success: function (json) {
            if(json&&json.success){
                console.log(json.data);
                $("#epTitle").val(json.data.title)
                $("#eupImg").val(json.data.photo);
                $("#epName").val(json.data.name)
                $("#epPrice").val(json.data.price)
                $("#epTotal").val(json.data.total)
                $("#epSort").val(json.data.sort)
                $("#epFreight").val(json.data.freight)
                $("#epMsg").val(json.data.goods)
                if(json.data.status=="上架"){
                    $("#epYes")[0].checked='true';
                }else {
                    $("#epNo")[0].checked='true';
                }
                $("#editModal").modal("show");
            }else {
                alert(json.msg);
            }

        }
    });

}
function deleteHandler(Sort) {
    $.ajax({
        url: '/goods/delProduct',
        type: 'post',
        dataType:"json",
        data:{
            Sort:Sort
        },
        success: function (json) {
            if(json&&json.success){
                $('#table').bootstrapTable('refresh');
            }
            alert(json.msg);
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
            arr.push(rows[i].Sort_);
        }
        let Sort = arr.join(",");//讲数组中的数据用逗号连接成一个字符串
        deleteHandler(Sort);
    }

}
function openModal() {
    $("#openModal").modal("show")
}

function addProduct() {
    $('#addForm').bootstrapValidator('validate');//手动验证表单
    var flag = $('#addForm').data('bootstrapValidator').isValid()//验证是否通过true/false
    if(!flag){
        return
    }
    let title = $("#pTitle").val();
    let photo = $("#upImg").val();
    let name = $("#pName").val();
    let Price = $("#pPrice").val();
    let total = $("#pTotal").val();
    let Sort = $("#pSort").val();
    let Freight = $("#pFreight").val();
    let Status;
    if(!$("#pNo")[0].checked && !$("#pYes")[0].checked){
        alert("请选择是否上架");
        return;
    }
    if($("#pNo")[0].checked){
        Status="下架";
    }else {
        Status="上架";
    }
    let goods = $("#pMsg").val();
    let upload = new FormData();
    upload.append("title",title);
    upload.append("name",name);
    upload.append("photo",photo);
    upload.append("Price",Price);
    upload.append("total",total);
    upload.append("Sort",Sort);
    upload.append("Freight",Freight);
    upload.append("goods",goods);
    upload.append("Status",Status);

    $.ajax({
        url: '/goods/upload',
        type: 'post',
        dataType:"json",
        data:upload,
        processData:false,
        contentType:false,
        success: function (json) {
            if(json&&json.success){
                $('#table').bootstrapTable('refresh');
                $("#openModal").modal("hide");
            }
            alert(json.msg);
        }
    });
}

function editProduct() {
    $('#editForm').bootstrapValidator('validate');//手动验证表单
    var flag = $('#editForm').data('bootstrapValidator').isValid()//验证是否通过true/false
    if(!flag){
        return
    }
    let title = $("#epTitle").val();
    let photo = $("#eupImg").val();
    let name = $("#epName").val();
    let Price = $("#epPrice").val();
    let total = $("#epTotal").val();
    let Sort = $("#epSort").val();
    let Freight = $("#epFreight").val();
    let Status;
    if($("#epNo")[0].checked){
        Status="下架";
    }else {
        Status="上架";
    }
    let goods = $("#epMsg").val()
    let upload = new FormData();
    upload.append("title",title);
    upload.append("name",name);
    upload.append("photo",photo);
    upload.append("Price",Price);
    upload.append("total",total);
    upload.append("Sort",Sort);
    upload.append("Freight",Freight);
    upload.append("goods",goods);
    upload.append("Status",Status);

    $.ajax({
        url: '/goods/editProduct',
        type: 'post',
        dataType:"json",
        data:upload,
        processData:false,
        contentType:false,
        success: function (json) {
            if(json&&json.success){
                $('#table').bootstrapTable('refresh');
                $("#editModal").modal("hide");
            }
            alert(json.msg);
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

