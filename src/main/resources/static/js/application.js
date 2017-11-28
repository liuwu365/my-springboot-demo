/**
 * @author Created by 王亚平 on 2017/6/26.
 * @description 消息格式 {"code":200,"errorMsg":"",
* "result":[{"msgType":2,"from":51,"to":56,"ts":1498529455562,"msgContent":"你有新的订单，快去处理..."}]}
 */

$.app = {
    initIndex: function () {
        var message = $.message.initMessage();
        var pollingUrl = "/async/comet.json";
        var longPolling = function (url, callback) {
            $.ajax({
                url: pollingUrl,
                async: true,
                cache: false,
                global: false,
                timeout: 30 * 1000,
                dataType: 'json',
                success: function (data, status, request) {
                    // console.log("success:" + data);
                    callback(data);
                    data = null, status = null, request = null;
                    setTimeout(function () {
                        longPolling(url, callback)
                    }, 10)
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    // console.log("[jqXHR.status: " + jqXHR.status + "[jqXHR.readyState: " + jqXHR.readyState
                    //     + "[textStatus: " + textStatus + ", error: " + errorThrown + " ]");
                    if (jqXHR.status == 200) {
                        window.location.reload();
                    }
                    jqXHR = null;
                    textStatus = null;
                    errorThrown = null;
                    setTimeout(function () {
                        longPolling(url, callback)
                    }, 30 * 1000)
                }
            });
        };
        longPolling(pollingUrl, function (data) {
            if (data) {
                if (data.code == 200 && data.errorMsg == "") {
                    message.addMsg(data.result[0].msgContent);
                }
            } else {

            }
        });
    }
};

/**
 * 消息处理
 * @type {{initMessage: $.message.initMessage}}
 */
$.message = {
    initMessage: function () {
        var addMsgCount = function () {
            var $admin = $("#admin-notify a span");
            var oldCount = $admin.text();
            $admin.text(parseInt(oldCount) + 1)
        };

        var addMsg = function (msg) {
            addMsgCount();
            $("#admin-notify ul").append($("<li></li>").append($("<a></a>").addClass("glyphicon  glyphicon-comment").text(" " + msg)));
        };

        var haveReadMsg = function () {
            $.ajax({
                url: '/msg/haveReadMsg'
            });
        };
        var loadUnReadMsg = function () {
            $.ajax({
                url: '/msg/haveReadMsg'

            });
        };

        return {
            addMsg: function (msg) {
                addMsg(msg);
            }
        }
    }
};

$.navigation = {
    selectNavigationBar: function (id) {
        $("#" + id).click();
    },
    leftSideBar: function (name) {
        $(function () {
            $(".side .list-unstyled li").each(function () {
                if ($(this).find("c").text() == name) {
                    $(this).addClass("active");
                    return;
                }
            });
        });
    },
    leftSide: function () {
        return {
            rander: function (options) {
                var defaults = {
                    parent: $.constant.topBar,
                    index: 0,
                    child: [],
                    href: null
                };
                if (!options) {
                    options = {};
                }
                options = $.extend({}, defaults, options);
                $.navigation.selectNavigationBar(options.parent);//选中顶部菜单
                var arr = new Array();//侧边栏
                var childs = {};
                $(".side .list-unstyled li:not(:first) a c").each(function (i, e) {
                    arr.push($(e).text());
                });
                $(".side .list-unstyled li:not(:first) a").each(function (i, e) {
                    var key = $(e).attr("href");
                    childs[key] = arr[i];
                });
                options.child = childs;
                var key = window.location.pathname;
                $.navigation.leftSideBar(options.child[key]);//选中侧边栏
            }
        }
    },
    initNav: function (options) {
        if (!options) {
            options = {};
        }
        $.extend($.constant, options);
        $.navigation.leftSide().rander();
    }
};
$.constant = {
    topBar: "base",
    leftSide: "首页"
};

$.tabs = {
    initTab: function () {
        $(".nav-tab").on('click', function () {
            var id = $(this).attr("id");
            var li = $(this).parent();
            var html = li.siblings('div').html();
            $(".navbar-nav li").removeClass("active");
            li.addClass("active");
            $(".list-unstyled").children().remove();
            $(".list-unstyled").append(html);
            $.constant.topBar = _comUtil.notNull(id) ? id : $.constant.topBar;
            // $(".list-unstyled li:eq(1) c").click();
        });
        $("#" + $.constant.topBar).click().parent("li").addClass("active");//默认选中的nav-bar
    }
};
$.validateForm = {}
$.fn.formValidate = function (opts) {
    var rules = $.extend({}, opts ? (opts.rules || {}) : {});
    var messages = $.extend({}, opts ? (opts.messages || {}) : {});
    var defaults = $.extend(opts || {}, {
        rules: rules,
        messages: messages,
        errorElement: opts ? (opts.errorElement || "span") : "span",
        errorClass: opts ? (opts.errorClass || "errorContainer") : "errorContainer"
    });
    //$.extend($.fn.validate.prototype,__defaultOpts);
    $.fn.validate.call(this, defaults);
}


$(function () {
    //global disable ajax cache
    // $.ajaxSetup({ cache: true });

    // $(document).ajaxError(function(event, request, settings) {
    //
    //
    //     if(request.status == 0) {// 中断的不处理
    //         console.log("中断的不处理")
    //         return;
    //     }
    //
    //     top.$.app.alert({
    //         title : "网络故障/系统故障",
    //         //<refresh>中间的按钮在ajax方式中删除不显示
    //         message : request.responseText.replace(/(<refresh>.*<\/refresh>)/g, "")
    //     });
    // });
});


