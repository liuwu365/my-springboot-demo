$(document).ready(function () {
    $('body').attr('id', 'yt');
    $('[data-toggle="tooltip"]').tooltip();
    $('input, textarea').placeholder();
    //导入layui框架
    layui.use(['layer', 'laydate', 'form'], function () {
        var layer = layui.layer
            , laydate = layui.laydate
            , form = layui.form();
    });
    //时间插件
    if ($('.calendar').length > 0) {
        laydate({
            elem: '#yt .calendar',
            istime: true,
            format: 'YYYY-MM-DD hh:mm:ss'
        });
    }
    //分页插件
    var options = {
        bootstrapMajorVersion: 3, //版本
        currentPage: $("[name='page']").val(),
        totalPages: $("[name='totalPage']").val(),
        alignment: "left",
        itemTexts: function (type, page, current) {
            switch (type) {
                case "first":
                    return "首页";
                case "prev":
                    return "上一页";
                case "next":
                    return "下一页";
                case "last":
                    return "末页";
                case "page":
                    return page;
            }
        },
        pageUrl: function (type, page, current) {
            if (page == current) {
                return "javascript:void(0)";
            } else {
                return "javascript:paging('" + page + "');";
            }
        }
    };
    $('#pageLimit').bootstrapPaginator(options);
});

//分页搜索查询
function paging(page) {
    $("#page").val(page);
    $("#searchForm").submit();
}
//解决筛选条件时,分页从第二页起搜索无内容的错误bug;
function resetPage() {
    $("#page").val(1);
}
//取消按钮到上个页面
function cancleGoBack() {
    history.go(-1);
    location.reload();
}
//顶部菜单选中样式
function menuSelect(classEle) {
    $(".menu li").removeClass("active");
    $("." + classEle).addClass("active");
}
//加载验证码
function loadValidateCode() {
    var dateTime = new Date().getTime();
    var osrc = '/validCode/verifycode.json?validateKey=';
    $("#validateKey").val(dateTime);
    $("#codeimg").attr("src", osrc + dateTime).click(function () {
        $(this).attr('src', osrc + dateTime + '&t=' + Math.random());
    });
}
//非空验证或提交验证的红框提示(input)
function tipByEleName(eleName, tip) {
    $("input[name='" + eleName + "']").after('<div class="help-block"><i class="icon-err"></i>' + tip + '</div>');
    $("input[name='" + eleName + "']").parent("div").addClass("has-error");
    return false;
}
function removeTipByEleName(eleName) {
    $("input[name='" + eleName + "']").next().remove();
    $("input[name='" + eleName + "']").parent("div").removeClass("has-error");
}
//非空验证或提交验证的红框提示(标签元素)
function tipByEleClass(eleClass, tip) {
    $("." + eleClass).after('<div class="help-block"><i class="icon-err"></i>' + tip + '</div>');
    $("." + eleClass).parent("div").addClass("has-error");
    return false;
}
function removeTipByEleClass(eleClass) {
    $("." + eleClass).next().remove();
    $("." + eleClass).parent("div").removeClass("has-error");
}
//复选框选中或取消选中(忘记密码，阅读条款等)
function checkHelper(eleId) {
    $("#" + eleId).click(function () {
        if ($("#" + eleId).attr("class").indexOf("in") > 0) {
            $("#" + eleId).removeClass("in");
        } else {
            $("#" + eleId).addClass("in");
        }
    });
}
//跳转到登录页面
function toLoginPage() {
    window.location.href = '/proxy/login.json';
}
//退出登录
function logout() {
    layer.confirm('您确定要退出登录吗？', {
        title: '系统提示',
        btn: ['确定', '取消']
    }, function (i) {
        layer.close(i);
        $.post('/proxy/proxy_logout.json', function (data) {
            if (data.code == 200) {
                window.location.href = '/proxy/login.json';
            } else {
                layer.msg(data.msg, {time: 2000});
            }
        });
    });
}
//加载渠道下拉框列表
function loadChannelList() {
    $.ajax({
        type: "get",
        url: "/proxy/channel/loadChannelsList.json",
        dataType: "json",
        success: function (data) {
            if (data != null && data != null) {
                var res = data.t;
                var channelName = $("input[name='loadChannelName']").val();
                for (var i in res) {
                    if (res[i].channelName != '' && channelName == res[i].channelName) {
                        $(".channelList").append("<option readonly=\"readonly\" selected=\"true\" value=\"" + res[i].channelName + "\">" + res[i].channelName + "</option>");
                    } else {
                        $(".channelList").append("<option readonly=\"readonly\" value=\"" + res[i].channelName + "\">" + res[i].channelName + "</option>");
                    }

                }
            }
        }
    });
}

//银行卡号Luhn校验算法
//luhn校验规则：16位银行卡号（19位通用）:
//1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
//2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
//3.将加法和加上校验位能被 10 整除。
//bankno为银行卡号
function luhnCheck(bankno) {
    var lastNum = bankno.substr(bankno.length - 1, 1);//取出最后一位（与luhn进行比较）

    var first15Num = bankno.substr(0, bankno.length - 1);//前15或18位
    var newArr = new Array();
    for (var i = first15Num.length - 1; i > -1; i--) {    //前15或18位倒序存进数组
        newArr.push(first15Num.substr(i, 1));
    }
    var arrJiShu = new Array();  //奇数位*2的积 <9
    var arrJiShu2 = new Array(); //奇数位*2的积 >9

    var arrOuShu = new Array();  //偶数位数组
    for (var j = 0; j < newArr.length; j++) {
        if ((j + 1) % 2 == 1) {//奇数位
            if (parseInt(newArr[j]) * 2 < 9)
                arrJiShu.push(parseInt(newArr[j]) * 2);
            else
                arrJiShu2.push(parseInt(newArr[j]) * 2);
        }
        else //偶数位
            arrOuShu.push(newArr[j]);
    }

    var jishu_child1 = new Array();//奇数位*2 >9 的分割之后的数组个位数
    var jishu_child2 = new Array();//奇数位*2 >9 的分割之后的数组十位数
    for (var h = 0; h < arrJiShu2.length; h++) {
        jishu_child1.push(parseInt(arrJiShu2[h]) % 10);
        jishu_child2.push(parseInt(arrJiShu2[h]) / 10);
    }

    var sumJiShu = 0; //奇数位*2 < 9 的数组之和
    var sumOuShu = 0; //偶数位数组之和
    var sumJiShuChild1 = 0; //奇数位*2 >9 的分割之后的数组个位数之和
    var sumJiShuChild2 = 0; //奇数位*2 >9 的分割之后的数组十位数之和
    var sumTotal = 0;
    for (var m = 0; m < arrJiShu.length; m++) {
        sumJiShu = sumJiShu + parseInt(arrJiShu[m]);
    }

    for (var n = 0; n < arrOuShu.length; n++) {
        sumOuShu = sumOuShu + parseInt(arrOuShu[n]);
    }

    for (var p = 0; p < jishu_child1.length; p++) {
        sumJiShuChild1 = sumJiShuChild1 + parseInt(jishu_child1[p]);
        sumJiShuChild2 = sumJiShuChild2 + parseInt(jishu_child2[p]);
    }
    //计算总和
    sumTotal = parseInt(sumJiShu) + parseInt(sumOuShu) + parseInt(sumJiShuChild1) + parseInt(sumJiShuChild2);

    //计算luhn值
    var k = parseInt(sumTotal) % 10 == 0 ? 10 : parseInt(sumTotal) % 10;
    var luhn = 10 - k;

    if (lastNum == luhn) {
        console.log("验证通过");
        return true;
    } else {
        layer.msg("银行卡号必须符合luhn校验");
        return false;
    }
}
//检查银行卡号
function CheckBankNo(bankno) {
    var bankno = bankno.replace(/\s/g, '');
    if (bankno == "") {
        layer.msg("请填写银行卡号");
        return false;
    }
    /*if(bankno.length < 16 || bankno.length > 19) {
     layer.msg("银行卡号长度必须在16到19之间");
     return false;
     }*/
    var num = /^\d*$/;//全数字
    if (!num.exec(bankno)) {
        layer.msg("银行卡号必须全为数字");
        return false;
    }
    //开头6位
    /*var strBin = "10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";
     if(strBin.indexOf(bankno.substring(0, 2)) == -1) {
     layer.msg("银行卡号开头6位不符合规范");
     return false;
     }*/
    //Luhn校验
    /*if(!luhnCheck(bankno)){
     return false;
     }*/
    return true;
}

/**
 * 项目公用
 */
var _CommonJS = {
    /**
     * 特殊字符过滤[把特殊字符踢掉]
     * @param {Object} s
     */
    checkCharFilter: function (s) {
        //var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？+%]")
        var pattern = new RegExp("[`~!@#$^*()=|{}:;,\\[\\]<>/?~！@#￥……*（）——|{}【】‘；：”“。，、？+%]");
        var rs = "";
        for (var i = 0; i < s.length; i++) {
            rs = rs + s.substr(i, 1).replace(pattern, '');
        }
        return rs;
    },
    /**
     * 把英文单词的首字母大写
     */
    replaceFirstUper: function (str) {
        if (str.length <= 0 || str == "") {
            return;
        }
        str = str.toLowerCase();
        return str.replace(/\b(\w)|\s(\w)/g, function (m) {
            return m.toUpperCase();
        });
    },
    /**
     * 转换成数组，去掉重复，再组合好。
     * 需 jquery支持
     */
    removeRepeat: function (str) {
        var strArr = str.split(",");
        strArr.sort(); //排序
        var result = $.unique(strArr);
        return result;
    },
    /**
     * 如果字符串是null返回空字符,否则返回空str
     */
    checkNull: function (str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    },
    /**
     * 验证输入内容是否为数字
     */
    isDigit: function (value) {
        console.log(value);
        var patrn = /^[0-9]*$/;
        if (!patrn.test(value) || value == "" || value == null || value == "null") {
            return false;
        } else {
            return true;
        }
    },
    /**
     * 验证输入内容是否为双精度
     */
    isDouble: function (value) {
        if (value.length != 0) {
            var reg = /^[-\+]?\d+(\.\d+)?$/;
            if (reg.test(value)) {
                return true;
            } else {
                return false;
            }
        }
    },
    /**
     * 比较时间大小
     * 0:data1>data2
     * 1:data1<data2
     * 2:data1=data2
     */
    dateCompare: function (data1, data2) {
        var d1 = new Date(data1.replace(/\-/g, '/'));
        var d2 = new Date(data2.replace(/\-/g, '/'));
        if (d1 > d2) {
            return 0;
        } else if (d1 < d2) {
            return 1;
        } else if (d1 - d2 == 0) {
            return 2;
        }
    },
    /**
     * 获取当前时间,格式[2015-08-18 12:00:00]
     */
    currentTime: function () {
        var d = new Date();
        return d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + " " + d.getHours() + ':' + d.getMinutes() + ":" + d.getSeconds();
    },
    /**
     * 提取字符串中的所有数字
     */
    getNum: function (text) {
        var spaceInputStr = text.replace(/\s+/g, ""); //先去掉空格
        var filterStr = spaceInputStr.replace(/[^0-9]/ig, " ");  //提取数字,并以空格分割
        var value = filterStr.replace(/\s+/g, ' '); //剔除多余的空格[多个空格转为1个空格]
        return value;
    },
    /**
     * 分割标题得到标签,小于1个字符的将被过滤掉
     */
    splitStrTolabel: function (str) {
        if (str == null || str == "") {
            return "";
        } else {
            var spaceStr = str.replace(/\s+/g, ' '); //剔除多余的空格[多个空格转为1个空格]
            var arrayStr = spaceStr.split(" ");
            var newStr = "";
            for (var i = 0; i < arrayStr.length; i++) {
                if (arrayStr[i].length < 2) {
                    continue;
                }
                newStr.append(arrayStr[i])
            }
            return newStr;
        }
    },
    /**
     * 验证网址
     */
    checkUrl: function (str) {
        if (str == null || str == "") {
            return "";
        } else {
            var RegUrl = new RegExp();
            RegUrl.compile("^[A-Za-z]+://[A-Za-z0-9-_]+\\.[A-Za-z0-9-_%&\?\/.=]+$");
            if (!RegUrl.test(str)) {
                alert("网址格式错误");
            }
        }
    },
    /**
     * 验证邮箱
     */
    checkEmail: function (str) {
        var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        if (!myreg.test(str)) {
            return false;
        }
        return true;
    },
    /**
     * 验证手机号
     */
    checkMobile: function (str) {
        var myreg = /^1[3|4|5|7|8][0-9]{9}$/;
        if (!myreg.test(str)) {
            return false;
        }
        return true;
    },
    /*
     * 鼠标悬浮显示大图
     */
    showLargeImg: function () {
        var x = 3;
        var y = 5; //设置提示框相对于偏移位置，避免遮挡鼠标
        $(".prompt").hover(function (e) {  //鼠标移上事件
            this.myTitle = this.title; //把title的赋给自定义属性 myTilte ，屏蔽自带提示
            this.title = "";
            var tooltipHtml = "<div id='tooltip'><img src='" + this.myTitle + "' /></div>"; //创建提示框
            $("body").append(tooltipHtml); //添加到页面中
            $("#tooltip").css({
                "top": (e.pageY + y) + "px",
                "left": (e.pageX + x) + "px"
            }).show("fast"); //设置提示框的坐标，并表现
        }, function () {  //鼠标移出事件
            this.title = this.myTitle;  //重新设置title
            $("#tooltip").remove();  //移除弹出框
        }).mousemove(function (e) {   //跟随鼠标挪动事件
            $("#tooltip").css({
                "top": (e.pageY + y) + "px",
                "left": (e.pageX + x) + "px"
            });
        });
    },
    /**
     * 鼠标悬浮显示信息
     */
    showHoverInfo: function () {
        var x = 3;
        var y = 5; //设置提示框相对于偏移位置，避免遮挡鼠标
        $(".prompt").hover(function (e) {  //鼠标移上事件
            alert(11);
            this.myTitle = this.title; //把title的赋给自定义属性 myTilte ，屏蔽自带提示
            this.title = "";
            var tooltipHtml = "<div id='tooltip2'>" + this.myTitle + "</div>"; //创建提示框
            console.log(tooltipHtml);
            $("body").append(tooltipHtml); //添加到页面中
            $("#tooltip2").css({
                "top": (e.pageY + y) + "px",
                "left": (e.pageX + x) + "px"
            }).show("fast"); //设置提示框的坐标，并表现
        }, function () {  //鼠标移出事件
            this.title = this.myTitle;  //重新设置title
            $("#tooltip2").remove();  //移除弹出框
        }).mousemove(function (e) {   //跟随鼠标挪动事件
            $("#tooltip2").css({
                "top": (e.pageY + y) + "px",
                "left": (e.pageX + x) + "px"
            });
        });
    }
}
