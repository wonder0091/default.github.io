
$(function () {
    let tempAcc = getCookie("account")
    let tempPss = getCookie("password")

    if(tempAcc){
        $("#account").val(tempAcc)
    }
    if(tempPss){
        $("#password").val(tempPss)
    }
})

function setCookie(cname,cvalue,cday) {
    //获取当前时间
    let ct = new Date();
    //在当前时间的基础上，去计算失效之后的时间，单位是毫秒
    let eTime = ct.getTime()+(cday*24*60*60*1000)
    //将当前时间转换成失效时间，类型是日期型
    ct.setTime(eTime);
    //将失效时间变成key-value字符串，以便于拼接到cookie里
    let expires ="expires="+ct.toUTCString()
    document.cookie=cname+'='+cvalue+';'+expires;
}

 function getMenu() {
    $.ajax({
            url: '/menu/queryMenu',
            type: 'post',
            dataType:"json",
            data:{},
            success: function (data) {
                $('#menu').sidebarMenu({
                    data:data
                })
                console.log(data);
            }
        });
}
$(function () {
    getUserName();
    show();
    getMenu();
})


function getCookie(cname) {
    let cArr = document.cookie.split(";")
    for (let i = 0;i<cArr.length;i++){
        let temp = cArr[i].trim();
        //indexof 返回括号内字符串首次出现的位置
        if (temp.indexOf(cname+"=")==0){
            //按等号拆分成数组
            return cArr[i].split("=")[1];
        }
    }
    return "";
}



function getUserName() {
    $.ajax({
               url: '/login/userName',
               type: 'post',
               dataType:"json",
               data:{},
               success: function (data) {
                   $("#spt").text("欢迎 "+data.user.account+"~");
               }
           });
}


function show() {
    let title = $("#ptitle").text();
    let msg = $("#msg").text();
    let content = $("#content").text();
    $.ajax({
               url: '/login/show',
               type: 'post',
               dataType:"json",
               data:{
                   title:title,
                   content:content,
                   msg:msg
               },
               success: function (data) {
                   if(data.success){
                       console.log(data);
                       $("#ptitle").text(data.data.title)
                       $("#content").text(data.data.content)
                       $("#msg").text(data.data.createTime)
                   }
               }
           });
}

function signIn() {
    $.ajax({
               url: '/login/signIn',
               type: 'post',
               dataType:"json",
               data:{
               },
               success: function (data) {
                  if(data.success){
                      console.log(data.data)
                      $("#inBtn").html(data.data)
                      $("#inBtn").css("background","deeppink")
                  }
               }
           });
}

function signOut() {
    let account = getCookie("account")
    $.ajax({
        url: '/login/signOut',
        type: 'post',
        dataType:"json",
        data:{
            account:account
        },
        success: function (data) {
            if(data.success){
                console.log(data.data);
                $("#outBtn").html(data.data);
                $("#outBtn").css("background","orange");
            }
        }
    });

}