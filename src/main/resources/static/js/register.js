
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


function uploadfile(){

    let acc = $("#account").val();
    let pass = $("#password").val();
    let repass = $("#repassword").val();
    let photo = $("#photo").get(0).files[0];
    let uploadData = new FormData();

    if(acc.length<2 || pass.length<6){
        alert("帐号需要两个字符以上，密码最少要六位数[特殊字符+数字+英文字母]混合");
        return;
    }
    if(acc.length>16|| pass.length>16){
        alert("帐号或密码长度超出");
        return;
    }
    if(repass!=pass){
        alert("两次输入的密码不一致");
        return;
    }
    var numasc = 0;
    var charasc = 0;
    var otherasc = 0;

    for (var i = 0; i < pass.length; i++) {
        var asciiNumber = pass.substr(i, 1).charCodeAt();
        if (asciiNumber >= 48 && asciiNumber <= 57) {
            numasc += 1;
        }
        if ((asciiNumber >= 65 && asciiNumber <= 90) || (asciiNumber >= 97 && asciiNumber <= 122)) {
            charasc += 1;
        }
        if ((asciiNumber >= 33 && asciiNumber <= 47) || (asciiNumber >= 58 && asciiNumber <= 64) || (asciiNumber >= 91 && asciiNumber <= 96) || (asciiNumber >= 123 && asciiNumber <= 126)) {
            otherasc += 1;
        }
    }
    if (0 == numasc) {
        alert("密码必须包括数字，符号，字母")
        clear();
        return;
    } else if (0 == charasc) {
        alert("密码必须包括数字，符号，字母")
        clear();
        return;
    } else if (0 == otherasc) {
        alert("密码必须包括数字，符号，字母")
        clear();
        return;
    }
    uploadData.append('account',acc)
    uploadData.append('pass',pass)
    if(photo){
        uploadData.append('photo',photo)
    }else {
        alert("未上传头像");
        return;
    }
    $.ajax({
        url: '/login/register',
        type: 'post',
        dataType:"json",
        data:uploadData,
        processData:false,
        contentType:false,
        success: function (data) {
          if(data.code==0){
              alert(data.msg);
          }
          if(data.code==1){
            $("#photoImg").prop("src",data.photo);
            window.location = '/login';
            }
        }
    });

}


$(function () {
    $("#photo").change(function() {
        var $file = $(this);
        var objUrl = $file[0].files[0];
        //获得一个http格式的url路径:
        var windowURL = window.URL || window.webkitURL;
        //createObjectURL创建一个指向该参数对象(图片)的URL
        var dataURL;
        dataURL = windowURL.createObjectURL(objUrl);
        $("#photoImg").css("background",'url('+dataURL+')').css("backgroundSize","75px 75px");
    });
})

function op() {
    window.location = '/login';
}