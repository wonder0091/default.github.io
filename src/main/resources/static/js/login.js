
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



function login(){
    let acc = $("#account").val();
    let pass = $("#password").val();

    $.ajax({
        url: '/login/login',
        type: 'get',
        dataType:"json",
        data:{
            account:acc,
            pass:pass
        },
        success: function (data) {
            if(data.code==0){
                alert(data.msg);
                return;
            } else{
                location.href=data.nextUrl;
            }

        }
    });
    var numasc = 0;
    var charasc = 0;
    var otherasc = 0;
    if(acc.length===0){
        alert("帐号为空")
        clear();
        return;
    }else if(acc.length < 2 || acc.length > 16){
        alert("帐号或密码错误")
        clear();
        return;
    }
    if(pass.length==0){
        alert("密码为空")
        clear();
        return;
    }else {
        if($("#remember").prop("checked")){
            setCookie("account", $("#account").val(), 7)
            setCookie("password", $("#password").val(), 7)
        }

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
        return true;
    }

}

function clear() {
    $("#account").val("");
    $("#password").val("");
}

// function uploadfile(){
//     let acc = $("#account").val();
//     let pass = $("#password").val();
//     let repass = $("#repassword").val();
//     let photo = $("#photo").get(0).files[0];
//     let uploadData = new FormData();
//     if(acc.length>15|| pass.length>8){
//         alert("帐号或密码超出");
//         return;
//     }
//     if(repass!=pass){
//         alert("两次输入的密码不一致");
//         return;
//     }
//     uploadData.append('account',acc)
//     uploadData.append('pass',pass)
//
//     uploadData.append('repass',repass)
//     if(photo){
//         uploadData.append('photo',photo)
//     }
//     $.ajax({
//         url: '/login/register',
//         type: 'post',
//         dataType:"json",
//         data:uploadData,
//         processData:false,
//         contentType:false,
//         success: function (data) {
//             if(data.code==0){
//                 alert(data.msg);
//             }
//         }
//     });
//
// }

function op(){
    window.location.replace("/register");
}
