$(function () {
    tableInit()
    $("#myModal").on('hidden.bs.modal',function (e) {
        $("#formId").val("");
        $("#formSub").val("");
        // $("#formSub")[0].reset();
    })
})

function tableInit() {
    $('#table').bootstrapTable({
        url: '/user/queryA', // 请求后台的URL（*）
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
        pageSize: 5, // 每页的记录行数（*）
        pageList: [10, 15, 20], // 可供选择的每页的行数（*）
        search: false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: true,
        showColumns: true, // 是否显示所有的列
        showRefresh: true, // 是否显示刷新按钮
        minimumCountColumns: 2, // 最少允许的列数
        clickToSelect: true, // 是否启用点击选中行
        singleSelect: true,//开启单选，默认为多选
        uniqueId: "id", // 每一行的唯一标识，一般为主键列
        showToggle: false, // 是否显示详细视图和列表视图的切换按钮
        cardView: false, // 是否显示详细视图
        detailView: false, // 是否显示父子表
        columns: [ {
            field: 'title_',
            title: '标题',

        },{
            field: 'content',
            title: '内容',
            //参数1，当前属性的值
            //参数2，当前这条数据的所以值，是一个对象，可以通过 对象.属性名 取其他字段的值
            formatter: function (data, row, index) {
                return "<font style='color: deeppink;display: block;width: 35em;word-break:keep-all;" +
                    "white-space:nowrap;text-overflow: ellipsis;overflow:hidden'>"+data+"</font>"
            }
        },{
            field: 'create_time',
            title: '发布时间',
            formatter: function (data, row, index) {
                let d = new Date(data);
                let year = d.getFullYear();
                let month = d.getMonth()+1;
                let day = d.getDate();
                let hour = d.getHours();
                let min = checked(d.getMinutes());
                let time = year+"/"+month+"/"+day+" "+hour+":"+min;

                function checked(j) {
                    if(j<10){
                        j='0'+j;
                        return j;
                    }else {
                        return j;
                    }
                }
                return time;
            }
        }, {
            field: 'id_',
            title: '操作',
            formatter: function (data, row, index) {
                var temp = ""
                temp = "<a href='javascript:void(0)' onclick=edit('" + data + "')>详情</a>  &nbsp;&nbsp;";
                // temp += "<a href='javascript:void(0)' onclick=dele('" + data + "')>删除</a>   &nbsp;&nbsp;";
                // temp += "<a href='javascript:void(0)' onclick=roleJoin('" + data + "')>关联角色</a>  ";
                // temp +=  "<i class='icon-ok' data-icon1='icon-ok' data-icon2='icon-double-angle-right'></i>";

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
        content: $("#inputName").val(),
        order: params.order,
        sort: params.sort
    };
    return temp;
}

function tableQuery() {
    $("#table").bootstrapTable("refresh",{query:{page:1}});
}
function openAdd() {
    $("#myModal").modal('show')
}

function edit(id) {
    let arr = globalData.rows;
    for (let i=0;i<arr.length;i++){
        if(id==arr[i].id_){
            let currentData = arr[i];
            $("#myModal").modal('show');
            $("#formTitle").html(arr[i].title_);
            $("#formContent").html(arr[i].content);
            $("#formContent").css("font-size","16px").css("font-family","宋体");
            break;
        }
    }
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

